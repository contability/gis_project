package kr.co.gitt.kworks.projects.gn.service.cmmnProp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO.ImageType;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.gn.dto.UseAreaSearchDTO;
import kr.co.gitt.kworks.projects.gn.mappers.BmlAcexAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.BmlAcinAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.BmlBuidAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.BmlLoanAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.BmlLousAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.BmlOccpAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.BmlOousAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.BmlPropAsMapper;
import kr.co.gitt.kworks.projects.gn.model.BmlAcexAs;
import kr.co.gitt.kworks.projects.gn.model.BmlAcinAs;
import kr.co.gitt.kworks.projects.gn.model.BmlBuidAs;
import kr.co.gitt.kworks.projects.gn.model.BmlLoanAs;
import kr.co.gitt.kworks.projects.gn.model.BmlLousAs;
import kr.co.gitt.kworks.projects.gn.model.BmlOccpAs;
import kr.co.gitt.kworks.projects.gn.model.BmlPropAs;
import kr.co.gitt.kworks.service.cmmn.ImageService;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

@Service("cmmnPropService")
@Profile({"gn_dev", "gn_oper"})
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
	BmlAcexAsMapper bmlAcexAsMapper;
	
	@Resource
	BmlAcinAsMapper bmlAcinAsMapper;
	
	@Resource
	BmlLousAsMapper bmlLousAsMapper;
	
	@Resource
	BmlOousAsMapper bmlOousAsMapper;
	
	@Resource
	MessageSource messageSource;

	@Resource
	ImageService imageService;
	
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

	//산출내역 리스트
	/*@Override
	public List<BmlCompDt> listComp(BmlCompDt bmlCompDt) throws Exception {
		return bmlCompDtMapper.list(bmlCompDt);
	}*/
	
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

	//산출내역 단건조회
	/*@Override
	public BmlCompDt selectComp(Long cmpIdn) throws Exception {
		return bmlCompDtMapper.selectOne(cmpIdn);
	}*/
	
	//무단점유 단건조회
	@Override
	public BmlOccpAs selectOccp(Long ocpIdn, Long prtIdn) throws Exception {
		BmlOccpAs bmlOccpDt = new BmlOccpAs();
		bmlOccpDt.setOcpIdn(ocpIdn);
		bmlOccpDt.setPrtIdn(prtIdn);
		return bmlOccpDtMapper.selectOne(bmlOccpDt);
	}

	// 실태조사 단건 조회
	@Override
	public BmlAcexAs selectAcex(Long prtIdn) {
		return bmlAcexAsMapper.selectOne(prtIdn);
	}
	
	@Override
	public BmlAcinAs selectAcin(Long prtIdn) {
		return bmlAcinAsMapper.selectOne(prtIdn);
	}
	
	// 포털 알림
	@Override
	public ObjectNode portalNotification() {
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		Map<String, String> map = new HashMap<String, String>();
		
		String deptCode = userDTO.getDeptCode() + "0000";
		
		map.put("deptCode", deptCode);
		
		int mismatchProp = bmlPropAsMapper.mismatchProp(map);
		int mismatchBuid = bmlBuidAsMapper.mismatchBuid(map);
		int loanImmCount = bmlLoanAsMapper.loanImmCount(map);
		
		ObjectMapper om = new ObjectMapper();
		ObjectNode result = om.createObjectNode();
		result.put("mismatchProp", mismatchProp);
		result.put("mismatchBuid", mismatchBuid);
		result.put("loanImmCount", loanImmCount);
		
		return result;
	}
	
	@Override
	public void pdfDownload(String fileKnd, String prtIdn, String agent, HttpServletResponse rs) throws IOException {
		String fileName = fileKnd + "_" + prtIdn + FilenameUtils.EXTENSION_SEPARATOR + "pdf";
		
		StringBuffer pathSb = new StringBuffer();
		
		pathSb.append(messageSource.getMessage("Com.cmmnAcin.Path", null,Locale.getDefault()));
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
			rs.setHeader("Content-Disposition", "inline; filename = " + docName);
			
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

	@Override
	public List<BmlBuidAs> useAreaBuidSearch(UseAreaSearchDTO useAreaSearchDTO) {
		return bmlBuidAsMapper.selectListArea(useAreaSearchDTO);
	}

	@Override
	public List<BmlPropAs> useAreaPropSearch(UseAreaSearchDTO useAreaSearchDTO) {
		return bmlPropAsMapper.selectListArea(useAreaSearchDTO);
	}

	@Override
	public List<BmlLoanAs> useAreaLoanSearch(UseAreaSearchDTO useAreaSearchDTO) {
		return bmlLoanAsMapper.areaList(useAreaSearchDTO);
	}

	@Override
	public List<BmlAcexAs> useAreaAcexSearch(UseAreaSearchDTO useAreaSearchDTO) {
		return bmlAcexAsMapper.areaList(useAreaSearchDTO);
	}

	@Override
	public Long maxPk() {
		return bmlLousAsMapper.maxPk();
	}

	@Override
	public Integer lousDelete(Long lonIdn) {
		return bmlLousAsMapper.delete(lonIdn);
	}

	@Override
	public Long oousMaxPk() {
		return bmlOousAsMapper.maxPk();
	}

	@Override
	public Integer oousDelete(Long ocpIdn) {
		return bmlOousAsMapper.delete(ocpIdn);
	}

	@Override
	public List<BmlPropAs> selectAccGroupList() {
		return bmlPropAsMapper.selectAccGroupList();
	}

	@Override
	public List<BmlLoanAs> selectPrsGroupList() {
		return bmlLoanAsMapper.selectPrsGroupList();
	}

	@Override
	public Object selectManGroupList(String dataId) {
		if(dataId.equals("BML_PROP_AS")) {
			return bmlPropAsMapper.selectManGroupList();
		}else {
			return bmlLoanAsMapper.selectManGroupList();
		}
		
	}
}
