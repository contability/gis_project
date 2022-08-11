package kr.co.gitt.kworks.rest.export;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsExport;
import kr.co.gitt.kworks.model.KwsExport.ExportFilterTy;
import kr.co.gitt.kworks.model.KwsExport.ExportTy;
import kr.co.gitt.kworks.model.KwsExportConfm;
import kr.co.gitt.kworks.model.KwsExportData;
import kr.co.gitt.kworks.model.KwsExportExcelOptn;
import kr.co.gitt.kworks.model.KwsExportFilterBbox;
import kr.co.gitt.kworks.model.KwsExportFilterDong;
import kr.co.gitt.kworks.model.KwsExportFilterFid;
import kr.co.gitt.kworks.model.KwsExportFilterImage;
import kr.co.gitt.kworks.model.KwsExportFilterOutpt;
import kr.co.gitt.kworks.model.KwsExportFilterPolygon;
import kr.co.gitt.kworks.model.KwsExportOutpt;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.export.ExportHistoryService;
import kr.co.gitt.kworks.service.export.ExportService;
import kr.co.gitt.kworks.service.file.FileService;
import kr.co.gitt.kworks.service.user.UserService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class ExportController
/// kr.co.gitt.kworks.rest.export \n
///   ㄴ ExportController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 13. 오후 9:01:15 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/export/")
public class ExportController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 내보내기 서비스
	@Resource
	ExportService exportService;
	
	/// 내보내기 출력 서비스
	@Resource
	ExportHistoryService exportHistoryService;
	
	/// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/// 사용자 서비스
	@Resource
	UserService userService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	/// 내보내기 권한 모드
	public enum ExportAuthorMode {
		VIEW, AUTHOR
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneExport
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/select.do", method=RequestMethod.GET)
	public String selectOneExport(@PathVariable("exportNo") Long exportNo, Model model) {
		KwsExport kwsExport = exportService.selectOneExport(exportNo);
		if(authorConfirm(kwsExport, ExportAuthorMode.VIEW)) {
			model.addAttribute("data", exportService.selectOneExport(exportNo));
			return "jsonView";
		}
		else {
			return "redirect:/accessDenied.do";
		}
	}
	
	/////////////////////////////////////////////
	/// @fn consentExport
	/// @brief 함수 간략한 설명 : 승인
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @param kwsExport
	/// @param kwsExportConfm
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/consent.do", method=RequestMethod.POST)
	public String consentExport(@PathVariable("exportNo") Long exportNo, KwsExport kwsExport, KwsExportConfm kwsExportConfm, Model model) throws Exception {
		KwsExport selectModel = exportService.selectOneExport(exportNo);
		if(authorConfirm(selectModel, ExportAuthorMode.AUTHOR)) {
			kwsExport.setExportNo(exportNo);
			kwsExportConfm.setExportNo(exportNo);
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			kwsExportConfm.setConfmerId(userDTO.getUserId());

			kwsExport.setKwsExportConfm(kwsExportConfm);
			
			// 승인 - 진행상태 변경으로 Service 로 이동 (Transaction 적용 필요)
			model.addAttribute("rowCount", exportService.consentExport(kwsExport));
			return "jsonView";
		}
		else {
			return "redirect:/accessDenied.do";
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addReturn
	/// @brief 함수 간략한 설명 : 반려
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @param kwsExport
	/// @param kwsExportConfm
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/return.do", method=RequestMethod.POST)
	public String addReturn(@PathVariable("exportNo") Long exportNo, KwsExport kwsExport, KwsExportConfm kwsExportConfm, Model model) throws Exception {
		KwsExport selectModel = exportService.selectOneExport(exportNo);
		if(authorConfirm(selectModel, ExportAuthorMode.AUTHOR)) {
			kwsExport.setExportNo(exportNo);
			kwsExportConfm.setExportNo(exportNo);
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			kwsExportConfm.setConfmerId(userDTO.getUserId());
			
			kwsExport.setKwsExportConfm(kwsExportConfm);
			
			// 반려 - 진행상태 변경으로 Service 로 이동 (Transaction 적용 필요)
			model.addAttribute("rowCount", exportService.returnExport(kwsExport));
			return "jsonView";
		}
		else {
			return "redirect:/accessDenied.do";
		}
		
		
	}
	
	/////////////////////////////////////////////
	/// @fn addByOutput
	/// @brief 함수 간략한 설명 : 출력 내보내기 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportDTO
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addByOutput.do", method=RequestMethod.POST)
	public String addByOutput(ExportOutputDTO exportDTO, Model model) throws Exception {
		
		// 내보내기
		ModelMapper modelMapper = new ModelMapper();
		KwsExport kwsExport = modelMapper.map(exportDTO, KwsExport.class);
		kwsExport.setExportFilterTy(ExportFilterTy.OUTPUT);
		
		// 필터 등록
		KwsExportFilterOutpt kwsExportFilterOutpt = modelMapper.map(exportDTO, KwsExportFilterOutpt.class);
		kwsExport.setKwsExportFilterOutpt(kwsExportFilterOutpt);
		
		// 내보내기 데이터
		List<KwsExportData> kwsExportDatas = new ArrayList<KwsExportData>();
		for(String dataId : exportDTO.getDataIds()) {
			KwsExportData kwsExportData = new KwsExportData();
			kwsExportData.setDataId(dataId);
			kwsExportDatas.add(kwsExportData);
		}
		kwsExport.setKwsExportDatas(kwsExportDatas);
		
		// 요청자
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsExport.setRqesterId(userDTO.getUserId());
		
		model.addAttribute("rowCount", exportService.addExport(kwsExport));
		return "jsonView";		
	}
	
	/////////////////////////////////////////////
	/// @fn addByData
	/// @brief 함수 간략한 설명 : 데이터 (DXF, EXCEL, SHAPE) 내보내기 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportDTO
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addByData.do", method=RequestMethod.POST)
	public String addByData(ExportDataDTO exportDTO, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		// 내보내기
		ModelMapper modelMapper = new ModelMapper();
		KwsExport kwsExport = modelMapper.map(exportDTO, KwsExport.class);
		
		// 요청자
		kwsExport.setRqesterId(userDTO.getUserId());
		
		// 내보내기 데이터
		if(kwsExport.getExportTy().equals(ExportTy.EXCEL)) {
			
			List<KwsExportData> kwsExportDatas = new ArrayList<KwsExportData>();
			for(String dataId : exportDTO.getDataIds()){
				KwsExportData kwsExportData = new KwsExportData();
				kwsExportData.setDataId(dataId);
				
				kwsExportDatas.add(kwsExportData);
			}
			kwsExport.setKwsExportDatas(kwsExportDatas);
			
			KwsExportExcelOptn kwsExportExcelOptn = new KwsExportExcelOptn();
			kwsExportExcelOptn.setFieldNmIndictAt(exportDTO.getFieldNmIndictAt()?"Y":"N");
			kwsExport.setKwsExportExcelOptn(kwsExportExcelOptn);
			
		}else{
			
			List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
			List<KwsExportData> kwsExportDatas = new ArrayList<KwsExportData>();
			for(String dataId : exportDTO.getDataIds()) {
				KwsExportData kwsExportData = new KwsExportData();
				kwsExportData.setDataId(dataId);
				boolean isAuthor = false;
				for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
					for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
						if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getExportAt())) {
							isAuthor = true;
							break;
						}
					}
				}
				if(isAuthor) {
					kwsExportDatas.add(kwsExportData);
				}
			}
			kwsExport.setKwsExportDatas(kwsExportDatas);
			
			if(kwsExportDatas.size() <= 0) {
				logger.warn("데이터 권한이 없습니다.");
				return "redirect:/accessDenied.do";
			}
		}
		
		// 내보내기 필터
		if(kwsExport.getExportFilterTy().equals(ExportFilterTy.BBOX)) {
			KwsExportFilterBbox kwsExportFilterBbox = modelMapper.map(exportDTO, KwsExportFilterBbox.class);
			kwsExport.setKwsExportFilterBbox(kwsExportFilterBbox);
		}
		else if(kwsExport.getExportFilterTy().equals(ExportFilterTy.DONG)) {
			KwsExportFilterDong kwsExportFilterDong = modelMapper.map(exportDTO, KwsExportFilterDong.class);
			kwsExport.setKwsExportFilterDong(kwsExportFilterDong);
		}
		else if(kwsExport.getExportFilterTy().equals(ExportFilterTy.FIDS)) {
			List<KwsExportFilterFid> kwsExportFilterFids = new ArrayList<KwsExportFilterFid>();
			List<Long> ids = exportDTO.getIds();
			for(Long id : ids) {
				KwsExportFilterFid kwsExportFilterFid = new KwsExportFilterFid();
				kwsExportFilterFid.setFid(id);
				kwsExportFilterFids.add(kwsExportFilterFid);
			}
			
			kwsExport.setKwsExportFilterFids(kwsExportFilterFids);
		}
		else if(kwsExport.getExportFilterTy().equals(ExportFilterTy.POLYGON)) {
			KwsExportFilterPolygon kwsExportFilterPolygon = new KwsExportFilterPolygon();
			kwsExportFilterPolygon.setWkt(exportDTO.getWkt());
			kwsExport.setKwsExportFilterPolygon(kwsExportFilterPolygon);
		}
		else {
			throw new Exception("정의되지 않은 내보내기 필터 타입입니다.");
		}
		
		model.addAttribute("rowCount", exportService.addExport(kwsExport));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addByImage
	/// @brief 함수 간략한 설명 : 이미지 내보내기 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportDTO
	/// @param model
	/// @return
	/// @throws IOException
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addByImage.do", method=RequestMethod.POST)
	public String addByImage(ExportImageDTO exportDTO, Model model) throws IOException, FdlException {
		String data = exportDTO.getData();
		String base64Str = null;
		String[] split = data.split(",");
		if(split.length == 2) {
			base64Str = split[1];
		}
		
		BufferedImage bufferedImage = imageService.getBufferdImageByBase64String(base64Str);
		
		ModelMapper modelMapper = new ModelMapper();
		KwsExport kwsExport = modelMapper.map(exportDTO, KwsExport.class);
		kwsExport.setExportFilterTy(ExportFilterTy.IMAGE);
		
		// 내보내기 데이터
		//Vector 없이 baseMap만 이미지 내보내기 했을 경우
		//dataId에 대한 처리 생략
		if (exportDTO.getDataIds() != null) {
			List<KwsExportData> kwsExportDatas = new ArrayList<KwsExportData>();
			for(String dataId : exportDTO.getDataIds()) {
				KwsExportData kwsExportData = new KwsExportData();
				kwsExportData.setDataId(dataId);
				kwsExportDatas.add(kwsExportData);
			}
			kwsExport.setKwsExportDatas(kwsExportDatas);
		}
	
		// 내보내기 필터 이미지
		kwsExport.setKwsExportFilterImage(modelMapper.map(exportDTO, KwsExportFilterImage.class));
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsExport.setRqesterId(userDTO.getUserId());
		
		model.addAttribute("rowCount", exportService.addExport(kwsExport, bufferedImage));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn download
	/// @brief 함수 간략한 설명 : 내보내기 파일 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @param fileNo
	/// @param response
	/// @param userAgent
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/{fileNo}/download.do")
	public void download(@PathVariable("exportNo") Long exportNo, @PathVariable("fileNo") Long fileNo, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		KwsExport selectModel = exportService.selectOneExport(exportNo);
		if(authorConfirm(selectModel, ExportAuthorMode.VIEW)) {
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			KwsExportOutpt kwsExportOutpt = new KwsExportOutpt();
			kwsExportOutpt.setExportNo(exportNo);
			kwsExportOutpt.setUserId(userDTO.getUserId());
			exportHistoryService.insertExportOutput(kwsExportOutpt);
			
			KwsFile kwsFile = fileService.selectOneFile(fileNo);
			InputStream is = new FileInputStream(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
			
			String fileName = kwsFile.getOrignlFileNm();
			String docName = null;
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
				docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
			}
			else {
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
			}
			response.setHeader("Content-Disposition", "attachment; filename="+docName);
			
			OutputStream os = response.getOutputStream();
			IOUtils.copy(is,  os);
			is.close();
			os.close();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addExportOutput
	/// @brief 함수 간략한 설명 : 내보내기 출력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/addOutput.do", method=RequestMethod.POST)
	public String addExportOutput(@PathVariable("exportNo") Long exportNo, Model model) throws FdlException {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		KwsExportOutpt kwsExportOutpt = new KwsExportOutpt();
		kwsExportOutpt.setExportNo(exportNo);
		kwsExportOutpt.setUserId(userDTO.getUserId());
		model.addAttribute("rowCount", exportHistoryService.insertExportOutput(kwsExportOutpt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn authorConfirm
	/// @brief 함수 간략한 설명 : 내보내기 권한 체크
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @param exportAuthorMode
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private boolean authorConfirm(KwsExport kwsExport, ExportAuthorMode exportAuthorMode) {
		boolean isAuthor = false;
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		switch (userDTO.getUserGrad()) {
			// 익명 권한 없음
			case ROLE_ANONYMOUS:
				isAuthor = false;
				break;
			// 부서 관리자의 경우 내보내기 요청자와 같은 부서 관리자만 권한 있음
			case ROLE_DEPT_MNGR:
				String userId = kwsExport.getRqesterId();
				KwsUser kwsUser = userService.selectOneUser(userId);
				if(StringUtils.equals(userDTO.getDeptCode(), kwsUser.getDeptCode())) {
					isAuthor = true;
				}
				break;
			/// 관리자는 모든 권한 있음
			case ROLE_MNGR:
				isAuthor = true;
				break;
			/// 사용자는 자기가 요청한 것만 권한 있음
			case ROLE_USER:
				if(exportAuthorMode.equals(ExportAuthorMode.VIEW) && StringUtils.equals(kwsExport.getRqesterId(), userDTO.getUserId())) {
					isAuthor = true;
				}
				break;
			default:
				break;
		}
		return isAuthor;
	}
	
	/////////////////////////////////////////////
	/// @fn getUserGraphics
	/// @brief 함수 간략한 설명 : 사용자 그래픽 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("getUserGraphics.do")
	public void getUserGraphics(Long exportNo, HttpServletResponse response) throws IOException {
		response.setHeader("content-type", "application/json; charset=UTF-8;");
		String userGraphics = exportService.getUserGraphics(exportNo);
		if(StringUtils.isNotBlank(userGraphics)) {
			response.getWriter().write(userGraphics);
		}
		response.getWriter().flush();
		response.getWriter().close();
	}
}
