package kr.co.gitt.kworks.projects.sunchang.service.iprvar;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.projects.sunchang.mappers.IprvarMapper;
import kr.co.gitt.kworks.projects.sunchang.model.KwsIprvarDt;
import kr.co.gitt.kworks.projects.sunchang.model.KwsIprvarDtFile;
import kr.co.gitt.kworks.service.file.FileService;

@Service("iprvarDtService")
@Profile({"sunchang_dev","sunchang_oper"})
public class IprvarServiceImpl implements IprvarService{
	
	public static String FOLDER_NAME = "iprvar";
	
	public static String FILE_NAME_PREFIX = "IPRVAR_";
	
	@Resource
	private IprvarMapper iprvarMapper;
	
	@Resource
	EgovIdGnrService kwsIprvarDtIdGnrService;
	
	@Resource
	EgovIdGnrService kwsIprvarDtFileIdGnrService;
	
	@Resource
	FileService fileService;
	
	@Override
	public List<KwsIprvarDt> selectList(SearchDTO searchDTO) {
		return iprvarMapper.selectList(searchDTO);
	}

	@Override
	public List<KwsIprvarDt> selectList(String pnu, SearchDTO searchDTO, PaginationInfo paginationInfo) {
		searchDTO.setSearchKeyword(pnu);
		
		Integer totalRecordCount = iprvarMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0){
			return selectList(searchDTO);
		}else{
			return new ArrayList<KwsIprvarDt>();
		}
	}

	@Override
	public KwsIprvarDt selectOne(Long iprvarNo) {
		return iprvarMapper.selectOne(iprvarNo);
	}

	@Override
	public Integer insert(KwsIprvarDt kwsIprvarDt) throws FdlException {
		Long iprvarNo = kwsIprvarDtIdGnrService.getNextLongId();
		kwsIprvarDt.setIprvarNo(iprvarNo);
		
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		kwsIprvarDt.setWrterId(userDTO.getUserId());
		kwsIprvarDt.setUpdusrId(userDTO.getUserId());
		
		return iprvarMapper.insert(kwsIprvarDt);
	}

	@Override
	public Integer update(KwsIprvarDt kwsIprvarDt) {
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		kwsIprvarDt.setUpdusrId(userDTO.getUserId());
		return iprvarMapper.update(kwsIprvarDt);
	}

	@Override
	public Integer delete(Long iprvarNo) {
		
		// 부속자료 삭제
		List<KwsIprvarDtFile> fileList = fileSelectList(iprvarNo);
		
		if(fileList.size() > 0){
			for(int i = 0; i < fileList.size(); i++){
				iprvarMapper.fileDelete(fileList.get(i).getIprvarFileNo());
				fileService.removeFile(fileList.get(i).getFileNo());
			}
		}
		
		// 테이블 삭제
		return iprvarMapper.delete(iprvarNo);
	}

	@Override
	public Integer chkPnu(String pnu) {
		return iprvarMapper.chkPnu(pnu);
	}

	@Override
	public List<KwsIprvarDtFile> fileSelectList(Long iprvarNo) {
		return iprvarMapper.fileSelectList(iprvarNo);
	}

	@Override
	public Integer fileInsert(KwsIprvarDtFile kwsIprvarDtFile, MultipartRequest mr) throws FdlException, IOException {
		
		kwsIprvarDtFile.setIprvarFileNo(kwsIprvarDtFileIdGnrService.getNextLongId());
		
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		kwsIprvarDtFile.setWrterId(userDTO.getUserId());
		
		for(Entry<String, MultipartFile> entry : mr.getFileMap().entrySet()){
			MultipartFile mf = entry.getValue();
			if(!mf.isEmpty()){
				KwsFile kwsFile = fileService.addFile(mf, FOLDER_NAME, FILE_NAME_PREFIX);
				Long fileNo = kwsFile.getFileNo();
				
				kwsIprvarDtFile.setFileNo(fileNo);
			}
		}
		
		return iprvarMapper.fileInsert(kwsIprvarDtFile);
	}

	@Override
	public Integer fileDelete(String[] iprvarFileNos, String[] fileNos) {
		int fileResult = 0;
		
		
		for(int i = 0; i < fileNos.length; i++){
			Long fileNo = Long.parseLong(fileNos[i]);
			Long iprvarFileNo = Long.parseLong(iprvarFileNos[i]);
			
			int rs = iprvarMapper.fileDelete(iprvarFileNo);
			
			if(rs > 0){
				int deleteCount = fileService.removeFile(fileNo);
				if(deleteCount > 0){
					fileResult++;
				}
			}
		}
		
		return fileResult;
	}

	@Override
	public KwsFile iprvarFileChk(Long fileNo) {
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		return kwsFile;
	}

	// pdf 미리보기
	@Override
	public void iprvarPdfPreview(Long fileNo, HttpServletResponse response, String userAgent) throws IOException {
		
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		
		String path = kwsFile.getFileStreCours();
		File file = new File(path + kwsFile.getStreFileNm());
		
		if(file.exists()){
			InputStream is = new FileInputStream(file.getPath());
			
			String fileName = kwsFile.getOrignlFileNm();
			String docName = null;
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0")||StringUtils.contains(userAgent, "Chrome")){
				docName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20")+";";
			}else{
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"";
			}
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename="+docName);
			
			OutputStream os = response.getOutputStream();
			IOUtils.copy(is, os);
			
			is.close();
			os.close();
		}
		
	}
	
	// 압축 파일 다운로드
	@Override
	public void iprvarFileDownload(String chkArr, HttpServletResponse response, String userAgent) throws IOException {
		
		String[] fileNos = chkArr.split(",");
		
		List<String> docNames = new ArrayList<String>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		
		UUID random = UUID.randomUUID();
		
		//String zipName = random.toString() + format.format(date) + FilenameUtils.EXTENSION_SEPARATOR_STR + "zip";
		String zipName = "정비보류지역 관리조서 부속자료"+ FilenameUtils.EXTENSION_SEPARATOR_STR + "zip";
		String zipDocName = "";
		
		zipDocName = nameEncoding(zipName, userAgent);
		
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipDocName));
		
		byte[] buffer = new byte[1024];
				
		
		for(int i = 0; i < fileNos.length; i++){
			
			Long fileNo = Long.parseLong(fileNos[i]);
			KwsFile kwsFile = fileService.selectOneFile(fileNo);
			
			String docName = kwsFile.getOrignlFileNm();
			
			int nameSort = 0;
			
			for(int j = 0; j < docNames.size(); j++){
				if(docNames.get(j).equals(docName)){
					nameSort++ ;
				}
			}
			
			docNames.add(docName);
			
			FileInputStream fis = new FileInputStream(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
			
			if(nameSort > 0){
				
				String[] splDocName = docName.split("\\.");
				
				splDocName[splDocName.length -2] = splDocName[splDocName.length -2] + "(" + nameSort + ")";
				
				String entryName = splDocName[0];
				
				for(int k = 1; k < splDocName.length; k++){
					entryName += FilenameUtils.EXTENSION_SEPARATOR_STR + splDocName[k];
				}
				
				zout.putNextEntry(new ZipEntry(entryName));
			}else{
				zout.putNextEntry(new ZipEntry(docName));
			}
			
			int length = 0;
			
			while((length = fis.read(buffer)) > 0){
				zout.write(buffer, 0, length);
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
		
		if(bos !=null) bos.close();
		if(bis !=null) bis.close();
		if(so != null) so.close();
		if(fis != null) fis.close();
		
		if(zout != null) zout.close();
	}
	
	@Override
	public String nameEncoding(String fileName, String userAgent) throws UnsupportedEncodingException {
		String docName = "";
		
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")){
			docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
		}else{
			docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+ "\"";
		}
		
		return docName;
	}

}
