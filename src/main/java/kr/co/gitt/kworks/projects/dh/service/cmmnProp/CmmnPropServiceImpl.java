package kr.co.gitt.kworks.projects.dh.service.cmmnProp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.dh.mappers.BmlAcinAsMapper;
import kr.co.gitt.kworks.projects.dh.mappers.BmlBuidAsMapper;
import kr.co.gitt.kworks.projects.dh.mappers.BmlLoanAsMapper;
import kr.co.gitt.kworks.projects.dh.mappers.BmlOccpAsMapper;
import kr.co.gitt.kworks.projects.dh.mappers.BmlPropAsMapper;
import kr.co.gitt.kworks.projects.dh.model.BmlAcinAs;
import kr.co.gitt.kworks.projects.dh.model.BmlBuidAs;
import kr.co.gitt.kworks.projects.dh.model.BmlLoanAs;
import kr.co.gitt.kworks.projects.dh.model.BmlOccpAs;
import kr.co.gitt.kworks.projects.dh.model.BmlPropAs;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("cmmnPropService")
@Profile({"dh_dev", "dh_oper"})
public class CmmnPropServiceImpl implements CmmnPropService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	BmlPropAsMapper bmlPropAsMapper;
	
	@Resource
	BmlBuidAsMapper bmlBuidAsMapper;
	
	@Resource
	BmlLoanAsMapper bmlLoanAsMapper;
	
	@Resource
	BmlOccpAsMapper bmlOccpDtMapper;
	
	@Resource
	BmlAcinAsMapper bmlAcinAsMapper;
	
	@Resource
	MessageSource messageSource;
	
	//토지재산 리스트조회
	@Override
	public List<BmlPropAs> listProp(BmlPropAs bmlPropDt) throws Exception {
		return bmlPropAsMapper.list(bmlPropDt);
	}
	
	//토지재산 단건조회
	@Override
	public BmlPropAs selectProp(Long prtIdn) throws Exception {
		return bmlPropAsMapper.selectOne(prtIdn);
	}

	//건물재산 리스트조회
	@Override
	public List<BmlBuidAs> listBuid(BmlBuidAs bmlBuidDt) throws Exception {
		return bmlBuidAsMapper.list(bmlBuidDt);
	}
	
	//건물재산 단건조회
	@Override
	public BmlBuidAs selectBuid(Long prtIdn) throws Exception {
		return bmlBuidAsMapper.selectOne(prtIdn);
	}

	//대부내역 리스트
	@Override
	public List<BmlLoanAs> listLoan(BmlLoanAs bmlLoanDt) throws Exception {
		return bmlLoanAsMapper.list(bmlLoanDt);
	}
	
	//무단점유 리스트
	@Override
	public List<BmlOccpAs> listOccp(BmlOccpAs bmlOccpDt) throws Exception {
		return bmlOccpDtMapper.list(bmlOccpDt);
	}

	//대부내역 단건조회
	@Override
	public BmlLoanAs selectLoan(Long lonIdn, Long prtIdn) throws Exception {
		BmlLoanAs bmlLoanAs = new BmlLoanAs();
		bmlLoanAs.setLonIdn(lonIdn);
		bmlLoanAs.setPrtIdn(prtIdn);
		return bmlLoanAsMapper.selectOne(bmlLoanAs);
	}

	//무단점유 단건조회
	@Override
	public BmlOccpAs selectOccp(Long ocpIdn, Long prtIdn) throws Exception {
		BmlOccpAs bmlOccpDt = new BmlOccpAs();
		bmlOccpDt.setOcpIdn(ocpIdn);
		bmlOccpDt.setPrtIdn(prtIdn);
		return bmlOccpDtMapper.selectOne(bmlOccpDt);
	}
	
	// 실태조사 단건조회
	@Override
	public BmlAcinAs selectAcin(Long prtIdn) {
		return bmlAcinAsMapper.selectOne(prtIdn);
	}

	// 실태조사 pdf 다운로드
	@Override
	public void pdfDownload(String fileKnd, String prtIdn, String agent, HttpServletResponse rs) throws IOException {
		String fileName = fileKnd + "_" + prtIdn + FilenameUtils.EXTENSION_SEPARATOR + "pdf";
		
		StringBuffer pathSb = new StringBuffer();
		pathSb.append(messageSource.getMessage("Com.cmmnAcin.Path", null, Locale.getDefault()));
		pathSb.append(File.separator);
		pathSb.append(fileName);
		
		File file = new File(pathSb.toString());
		
		if(file.exists()){
			InputStream is = new FileInputStream(file);
			
			String docName = null;
			
			if(StringUtils.contains(agent, "MSIE") || StringUtils.contains(agent, "rv:11.0") || StringUtils.contains(agent, "Chrome")){
				docName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
			}else{
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-88859-1") + "\"";
			}
			
			rs.setContentType("application/pdf");
			rs.setHeader("Content-Disposition", "inline; filename=" + docName);
			
			OutputStream os = rs.getOutputStream();
			
			IOUtils.copy(is, os);
			
			os.close();
			is.close();
		}
	}

	@Override
	public void thumbnailImage(File file, HttpServletRequest rq, HttpServletResponse rs, String userAgent) throws IOException {
		InputStream is = new FileInputStream(file);
		
		rs.reset();
		
		String fileName = null;
		
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")){
			fileName = URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20") + ";";
		}else{
			fileName = "\"" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		
		rs.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		
		OutputStream os = rs.getOutputStream();
		IOUtils.copy(is, os);
		
		os.close();
		is.close();
		
	}

	@Override
	public void previewFile(KwsFile kwsFile, String userAgent, HttpServletResponse rs) throws IOException {
		String path = kwsFile.getFileStreCours();
		File file = new File(path + kwsFile.getStreFileNm());
		
		if(file.exists()){
			InputStream is = new FileInputStream(file.getPath());
			
			String fileName = kwsFile.getOrignlFileNm();
			String docName = null;
			
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")){
				docName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + ";";
			}else{
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
			}
			
			String extsn = kwsFile.getFileExtsn();
			
			if(extsn.equals("pdf")){
				rs.setContentType("application/pdf");
			}
			
			rs.setHeader("Content-Disposition", "inline; filename=" + docName);
			
			OutputStream os = rs.getOutputStream();
			
			IOUtils.copy(is, os);
			
			os.close();
			is.close();
		}
	}
}