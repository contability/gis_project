package kr.co.gitt.kworks.projects.yg.web.landCenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.projects.yg.dto.LandCenterCntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.yg.model.LdlConsPs;
import kr.co.gitt.kworks.projects.yg.service.ladCntr.LadCntrwkRegstrService;
import kr.co.gitt.kworks.service.dept.DeptService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;
import kr.co.gitt.kworks.service.file.FileService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class LandCenterCntrwkRegstrManageController
/// kr.co.gitt.kworks.projects.yg.web.landCenter \n
///   ㄴ LandCenterCntrwkRegstrManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 4. 4. 오후 4:36:00 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 토지중심공사대장 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/landCenter/")
@Profile({"yg_dev", "yg_oper"})
public class LandCenterCntrwkRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	/// 부서관리 서비스
	@Resource
	DeptService deptService;
	
	// 토지중심공사대장 서비스
	@Resource
	LadCntrwkRegstrService ladCntrwkRegstrService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	/////////////////////////////////////////////
	/// @fn searchLandCenterCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 토지중심공사대장 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchLandCenterCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String searchLandCenterCntrwkRegstrPage(Model model) {

		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0004");
		model.addAttribute("selDong", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/yg/job/landCenter/listLandCenterCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn listAllPoi
	/// @brief 함수 간략한 설명 : 토지중심 공사위치의 지도 표시를 위해 GEOM 전체 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAllPoi.do", method=RequestMethod.GET)
	public String listAllPoi(Model model) {
		model.addAttribute("rows", ladCntrwkRegstrService.listAlllandCenterCntrwkPoi());
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{objectid}/selectOneLandCenterCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String selectOneLandCenterCntrwkRegstr(@PathVariable("objectid") Long objectid, Model model){
		LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO = new LandCenterCntrwkRegstrDTO();
		landCenterCntrwkRegstrDTO.setObjectid(objectid);
		LdlConsPs ldlConsPs = ladCntrwkRegstrService.selectOneLandCenterCetrwkRegstr(landCenterCntrwkRegstrDTO);
		Long cntIdn = ldlConsPs.getCntIdn();
		
		model.addAttribute("result", ldlConsPs);
		model.addAttribute("useResult", ladCntrwkRegstrService.listLandUseInfoRegstr(cntIdn));
		
		return "/projects/yg/job/landCenter/viewLandCenterCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addLandCenterCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 토지중심공사대장 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addLandCenterCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String addLandCenterCntrwkRegstrPage(Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0004");
		model.addAttribute("selDong", domnCodeService.listDomnCode(kwsDomnCode));
		model.addAttribute("cntDeptList", deptService.listAllDept());
		
		return "/projects/yg/job/landCenter/addLandCenterCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param landCenterCntrwkRegstrDTO
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addLandCenterCntrwkRegstr.do", method=RequestMethod.POST)
	public String addLandCenterCntrwkRegstr(LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO, Model model, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException{
		
		model.addAttribute("rowCount", ladCntrwkRegstrService.addLandCenterCetrwkRegstr(landCenterCntrwkRegstrDTO, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyLandCenterCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 토지중심공사대장 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{cntIdn}/modifyLandCenterCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String modifyLandCenterCntrwkRegstrPage(@PathVariable("cntIdn") Long cntIdn, Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		kwsDomnCode.setDomnId("KWS-0004");
		
		LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO = new LandCenterCntrwkRegstrDTO();
		landCenterCntrwkRegstrDTO.setCntIdn(cntIdn);
		
		model.addAttribute("selDong", domnCodeService.listDomnCode(kwsDomnCode));
		model.addAttribute("cntDeptList", deptService.listAllDept());
		model.addAttribute("result", ladCntrwkRegstrService.selectOneLandCenterCetrwkRegstr(landCenterCntrwkRegstrDTO));
		
		return "/projects/yg/job/landCenter/modifyLandCenterCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param landCenterCntrwkRegstrDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="modifyLandCenterCntrwkRegstr.do", method=RequestMethod.POST)
	public String modifyLandCenterCntrwkRegstr(LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO, Model model, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException{
		model.addAttribute("rowCount", ladCntrwkRegstrService.modifyLandCenterCetrwkRegstr(landCenterCntrwkRegstrDTO, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 삭제 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{cntIdn}/removeLandCenterCntrwkRegstr.do", method=RequestMethod.POST)
	public String removeLandCenterCntrwkRegstr(@PathVariable("cntIdn") Long cntIdn, Model model) throws FdlException {
		model.addAttribute("rowCount", ladCntrwkRegstrService.removeLandCenterCetrwkRegstr(cntIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeAtcFile
	/// @brief 함수 간략한 설명 : 토지공사대장 붙임문서 선택삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{fileNo}/removeAtcFile.do", method=RequestMethod.POST)
	public String removeAtcFile(@PathVariable("fileNo") Long fileNo, Model model) throws FdlException{
		model.addAttribute("rowCount", ladCntrwkRegstrService.removeAtcFile(fileNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn findAtcFileNo
	/// @brief 함수 간략한 설명 : 붙임문서번호 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{cntIdn}/selectFile.do", method=RequestMethod.GET)
	public String findAtcFileNo(@PathVariable("cntIdn")Long cntIdn, Model model){
		model.addAttribute("result", ladCntrwkRegstrService.findAtcFile(cntIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn downloadPdf
	/// @brief 함수 간략한 설명 : 붙임문서 파일 열기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param streFileNm
	/// @param response
	/// @param userAgent
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="pdf.do", method=RequestMethod.GET)
	public void downloadPdf(Long fileNo, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		String path = kwsFile.getFileStreCours();
		File file = new File(path + kwsFile.getStreFileNm());
		if(file.exists()){
			InputStream is = new FileInputStream(file.getPath());
			
			String fileName = kwsFile.getOrignlFileNm();
			String docName = null;
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
				docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
			}
			else {
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
			}
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename="+docName);
			
			OutputStream os = response.getOutputStream();
			IOUtils.copy(is, os);
			is.close();
			os.close();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn selectSeldong
	/// @brief 함수 간략한 설명 : 도메인코드 (법정읍면동) 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param codeId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{splitSelDong}/selectSeldong.do", method=RequestMethod.GET)
	public String selectSeldong(@PathVariable("splitSelDong")String codeId, Model model){
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		kwsDomnCode.setDomnId("KWS-0004");
		kwsDomnCode.setCodeId(codeId);
		
		model.addAttribute("result", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "jsonView";
	}
}
