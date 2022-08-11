package kr.co.gitt.kworks.service.cmmn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.KwsAtchMapper;
import kr.co.gitt.kworks.model.KwsAtch;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.file.FileService;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

// 부속 자료 서비스
// 2021.08.24 정신형
@Service("atchFileService")
public class AtchFileServiceImpl implements AtchFileService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	KwsAtchMapper kwsAtchMapper;
	
	@Resource
	DataService dataService;
	
	@Resource
	EgovIdGnrService kwsAtchIdGnrService;
	
	@Resource
	FileService fileService;

	@Override
	public List<KwsAtch> listAtchFileByFtf(KwsAtch kwsAtch) {
		return kwsAtchMapper.listAtchFileByFtf(kwsAtch);
	}
	
	@Override
	public Integer insertAtchFile(KwsAtch kwsAtch, MultipartRequest mr) throws FdlException, IOException {
		kwsAtch.setAtchNo(kwsAtchIdGnrService.getNextLongId());
		
		setUserInfo(kwsAtch);
		setDateTime(kwsAtch);
		
		for(Entry<String, MultipartFile> entry : mr.getFileMap().entrySet()){
			MultipartFile mf = entry.getValue();
			if(!mf.isEmpty()){
				KwsFile kwsFile = fileService.addFile(mf, FOLDER_NAME, FILE_NAME_PREFIX);
				kwsAtch.setAtchFileNo(kwsFile.getFileNo());
			}
		}
		
		return kwsAtchMapper.insertAtchFile(kwsAtch);
	}

	@Override
	public Boolean deleteAtchFiles(Long[] fileNos) {
		boolean isDelete = true;
		
		for(int i = 0; i < fileNos.length; i++){
			Long fileNo = fileNos[i];
			
			int atchDeleteResult = kwsAtchMapper.deleteAtchFile(fileNo);
			
			if(atchDeleteResult > 0){
				int fileDeleteResult = fileService.removeFile(fileNo);
				
				if(fileDeleteResult == 0){
					isDelete = false;
				}
			}
		}
		
		return isDelete;
	}
	
	public KwsAtch setUserInfo(KwsAtch kwsAtch){
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		kwsAtch.setWrterId(userDTO.getUserId());
		kwsAtch.setUpdusrId(userDTO.getUserId());
		
		return kwsAtch;
	}
	
	public KwsAtch setDateTime(KwsAtch kwsAtch){
		Calendar cal = new GregorianCalendar();
		Date date = new Date(cal.getTimeInMillis());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		String dateToStr = dateFormat.format(date);
		
		kwsAtch.setFrstRgsde(dateToStr);
		kwsAtch.setLastUpdde(dateToStr);
		
		return kwsAtch;
	}

	@Override
	public void downloadAtchFiles(Long[] fileNos, String dataId, HttpServletResponse response, String userAgent) throws IOException {
		
		List<String> docNames = new ArrayList<String>();
		
		KwsData kwsData = dataService.selectOneData(dataId);
		
		Calendar cal = new GregorianCalendar();
		Date date = new Date(cal.getTimeInMillis());
		
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateToStr = format.format(date);
		
		String zipName = kwsData.getDataAlias() + "_부속자료_"+ dateToStr + FilenameUtils.EXTENSION_SEPARATOR_STR + "zip";
		String zipDocName = "";
		
		
		
		zipDocName = nameEncoding(zipName, userAgent);
		
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipDocName));
		
		byte[] buffer = new byte[1024];
		
		for(int i = 0; i < fileNos.length; i++){
 			Long fileNo = fileNos[i];
			KwsFile kwsFile = fileService.selectOneFile(fileNo);
			
			String docName = kwsFile.getOrignlFileNm();
			
			int nameSort = 0;
			int docNamesLen = docNames.size();
			
			if(docNamesLen > 0){
				for(int j = 0; j <docNames.size(); j++){
					if(docNames.get(j).equals(docName)){
						nameSort++;
					}
				}
			}
			
			docNames.add(docName);
			
			FileInputStream fis = new FileInputStream(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
			
			if(nameSort > 0){
				String[] splDocName = docName.split("\\.");
				
				splDocName[splDocName.length -2] = splDocName[splDocName.length -2] + "(" + nameSort + ")";
				
				String entryName = "";
				
				for(int k = 0; k < splDocName.length; k++){
					if(k == 0){
						entryName += splDocName[0];
					}else{
						entryName += FilenameUtils.EXTENSION_SEPARATOR_STR + splDocName[k];
					}
				}
				
				zout.putNextEntry(new ZipEntry(entryName));
			}else{
				zout.putNextEntry(new ZipEntry(docName));
			}
			
			
			int length = 0;
			
			while((length = fis.read(buffer)) > 0){
				zout.write(buffer, 0 ,length);
			}
			
			zout.closeEntry();
			fis.close();
		}
		
		zout.close();
		
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=" + zipDocName);
		
		FileInputStream fis = new FileInputStream(zipDocName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		ServletOutputStream so = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(so);
		
		int n = 0;
		byte[] data = new byte[2048];
		
		while(( n = bis.read(data)) > 0){
			bos.write(data, 0, n);
			bos.flush();
		}
		
		if(bos != null) bos.close();
		if(bis != null) bis.close();
		if(so != null) so.close();
		if(fis != null) fis.close();
		if(zout != null) zout.close();
	}
	
	public String nameEncoding(String fileName, String userAgent) throws UnsupportedEncodingException{
		String docName = "";
		
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")){
			docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
		}else{
			docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		
		return docName;
	}

	@Override
	public void previewAtchFile(Long fileNo, String userAgent, HttpServletResponse response) throws IOException {
		
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		
		String path = kwsFile.getFileStreCours();
		
		File file = new File(path + kwsFile.getStreFileNm());
		
		if(file.exists()){
			InputStream	is = new FileInputStream(file.getPath());
			
			String fileName = kwsFile.getOrignlFileNm();
			String docName = null;
			
			
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")){
					docName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + ";";
			}else{
					docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-88859-1") + "\"";
			}
			
			String extsn = kwsFile.getFileExtsn();
			
			if(extsn.equals("pdf")){										
				response.setContentType("application/pdf");
			}
			
			response.setHeader("Content-Disposition", "inline; filename=" + docName);
			
			OutputStream os = response.getOutputStream();
			
			IOUtils.copy(is, os);
			
			is.close();
			os.close();
		}
	}

}
