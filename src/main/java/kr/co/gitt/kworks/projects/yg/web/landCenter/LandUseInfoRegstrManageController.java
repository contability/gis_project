package kr.co.gitt.kworks.projects.yg.web.landCenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.LpPaCbnd;
import kr.co.gitt.kworks.projects.yg.dto.LandCenterCntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.yg.mappers.LdDocMaMapper;
import kr.co.gitt.kworks.projects.yg.model.LdDocMa;
import kr.co.gitt.kworks.projects.yg.model.LdUseMa;
import kr.co.gitt.kworks.projects.yg.service.ladCntr.LadCntrwkRegstrService;
import kr.co.gitt.kworks.projects.yg.service.ladCntr.LandCenterUseDownLogService;
import kr.co.gitt.kworks.projects.yg.service.ladCntr.LandCntrwkUseFileService;
import kr.co.gitt.kworks.service.cmmn.AddressService;
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

/////////////////////////////////////////////
/// @class LandUseInfoRegstrManageController
/// kr.co.gitt.kworks.projects.yg.web.landCenter \n
///   ㄴ LandUseInfoRegstrManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 4. 4. 오후 4:35:38 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 토지사용정보대장 컨트롤러입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/landUseInfo/")
@Profile({"yg_dev", "yg_oper"})
public class LandUseInfoRegstrManageController {

	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 토지대장 파일 관리 맵퍼
	@Resource
	LdDocMaMapper ldDocMaMapper;
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 주소조회 서비스
	@Resource
	AddressService addressService;
	
	// 토지중심공사대장 서비스
	@Resource
	LadCntrwkRegstrService ladCntrwkRegstrService;
	
	// 토지사용정보대장 파일 서비스
	@Resource
	LandCntrwkUseFileService landCntrwkUseFileService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	// 토지사용증명서 관리 서비스 
	@Resource
	LandCenterUseDownLogService landCenterUseDownLogService;
	
	/////////////////////////////////////////////
	/// @fn searchLandUseInfoRestrPage
	/// @brief 함수 간략한 설명 : 토지사용정보대장 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldUseMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchLandUseInfoRegstrPage.do", method=RequestMethod.GET)
	public String searchLandUseInfoRestrPage(LdUseMa ldUseMa, Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0004");
		model.addAttribute("selDong", domnCodeService.listDomnCode(kwsDomnCode));
		
		
		return "/projects/yg/job/landCenter/listLandUseInfoRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn listAllLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldUseMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAllLandUseInfoRegstr.do", method=RequestMethod.GET)
	public String listAllLandUseInfoRegstr(LdUseMa ldUseMa, Model model){
		
		model.addAttribute("result", ladCntrwkRegstrService.listAllLandUseInfoRegstr(ldUseMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param luiIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{luiIdn}/selectOneLandUseInfoRegstrPage.do", method=RequestMethod.GET)
	public String selectOneLandUseInfoRegstr(@PathVariable("luiIdn") Long luiIdn, Model model) throws Exception{
		LdUseMa ldUseMa = ladCntrwkRegstrService.selectOneLandUseInfoRegstr(luiIdn);
		
		model.addAttribute("result", ldUseMa);
		
		return "/projects/yg/job/landCenter/viewLandUseInfoRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn pnuMatching
	/// @brief 함수 간략한 설명 : 토지사용정보대장 주소매칭
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param LuiIdn
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{luiIdn}/pnuMatch.do", method=RequestMethod.GET)
	public String pnuMatching(@PathVariable("luiIdn")Long luiIdn, Model model){
		LdUseMa ldUseMa = ladCntrwkRegstrService.selectOneLandUseInfoRegstr(luiIdn);
		String pnu = ldUseMa.getPnu();
		String address = "";
		
		if(StringUtils.isNotBlank(pnu)) {
			try {
				address = addressService.getFullAddress("BML_BADM_AS", "LP_PA_CBND", pnu);
			} catch (Exception e) {
				address = "";
			} finally {
				model.addAttribute("result", address);
			}

		}
		return "jsonView";
		
	}
	
	/////////////////////////////////////////////
	/// @fn modifyLandUseInfoRegstrPage
	/// @brief 함수 간략한 설명 : 토지사용정보대장 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param luiIdn
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{luiIdn}/{cntIdn}/modifyLandUseInfoRegstrPage.do", method=RequestMethod.GET)
	public String modifyLandUseInfoRegstrPage(@PathVariable("luiIdn") Long luiIdn,@PathVariable("cntIdn") Long cntIdn, Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		kwsDomnCode.setDomnId("KWS-0004");
		
		
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setLuiIdn(luiIdn);
		ldDocMa.setCntIdn(cntIdn);
		
		LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO = new LandCenterCntrwkRegstrDTO();
		landCenterCntrwkRegstrDTO.setCntIdn(cntIdn);
		
		model.addAttribute("selDong", domnCodeService.listDomnCode(kwsDomnCode));
		model.addAttribute("cntNamList", ladCntrwkRegstrService.selectOneLandCenterCetrwkRegstr(landCenterCntrwkRegstrDTO));
		model.addAttribute("result", ladCntrwkRegstrService.selectOneLandUseInfoRegstr(luiIdn));
		model.addAttribute("file", landCntrwkUseFileService.listLandCntrwkUseFile(ldDocMa));
		
		return "/projects/yg/job/landCenter/modifyLandUseInfoRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyLandUseInfoFile
	/// @brief 함수 간략한 설명 : 토지사용대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param luiIdn
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{luiIdn}/{cntIdn}/modifyLandUseInfoFile.do", method=RequestMethod.GET)
	public String modifyLandUseInfoFile(@PathVariable("luiIdn") Long luiIdn, @PathVariable("cntIdn") Long cntIdn, Model model){
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setLuiIdn(luiIdn);
		ldDocMa.setCntIdn(cntIdn);
		model.addAttribute("data", landCntrwkUseFileService.listLandCntrwkUseFile(ldDocMa));

		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param luiIdn
	/// @param ldUseMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{luiIdn}/{cntIdn}/modifyLandUseInfoRegstr.do", method=RequestMethod.POST)
	public String modifyLandUseInfoRegstr(@PathVariable("luiIdn") Long luiIdn, @PathVariable("cntIdn") Long cntIdn, LdUseMa ldUseMa, MultipartRequest multipartRequest, Model model) throws Exception {
		ldUseMa.setLuiIdn(luiIdn);
		ldUseMa.setCntIdn(cntIdn);
		
		model.addAttribute("rowCount", ladCntrwkRegstrService.modifyLandUseInfoRegstr(ldUseMa, multipartRequest));
		
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addLandUseInfoRegstrPage
	/// @brief 함수 간략한 설명 : 토지사용정보대장 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldUseMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addLandUseInfoRegstrPage.do", method=RequestMethod.GET)
	public String addLandUseInfoRegstrPage(LdUseMa ldUseMa, Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0004");
		model.addAttribute("selDong", domnCodeService.listDomnCode(kwsDomnCode));
		model.addAttribute("cntNam", ladCntrwkRegstrService.listAllLandCenterCetrwkRegstr());
		
		return "/projects/yg/job/landCenter/addLandUseInfoRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addLandUseInfoRegstr
	/// @brief 함수 간략한 설명 : 토지사용정보대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldUseMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addLandUseInfoRegstr.do", method=RequestMethod.POST)
	public String addLandUseInfoRegstr(LdUseMa ldUseMa, MultipartRequest multipartRequest, Model model) throws Exception {
		
			model.addAttribute("rowCount", ladCntrwkRegstrService.addLandUseInfoRegstr(ldUseMa, multipartRequest));
			return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeLandUseInfoRegstrPage
	/// @brief 함수 간략한 설명 : 토지사용정보대장 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param luiIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{cntIdn}/{luiIdn}/removeLandUseInfoRegstr.do", method=RequestMethod.POST)
	public String removeLandUseInfoRegstrPage(@PathVariable("cntIdn") Long cntIdn, @PathVariable("luiIdn") Long luiIdn, LdUseMa ldUseMa, Model model) throws Exception {
		
		ldUseMa.setCntIdn(cntIdn);
		ldUseMa.setLuiIdn(luiIdn);
		
		model.addAttribute("rowCount", ladCntrwkRegstrService.removeLandUseInfoRegstr(ldUseMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn searchSpatial
	/// @brief 함수 간략한 설명 : 공사위치
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{luiIdn}/searchSpatial.do", method=RequestMethod.GET)
	public String searchSpatial(@PathVariable("luiIdn") Long luiIdn, Model model){
		model.addAttribute("wkts", ladCntrwkRegstrService.searchSpatial(luiIdn));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn landDocMaFile
	/// @brief 함수 간략한 설명 : 토지사용증명서 파일명 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param luiIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{luiIdn}/selectFile.do", method=RequestMethod.GET)
	public String landDocMaFile( @PathVariable("luiIdn") Long luiIdn, Model model){
		model.addAttribute("result", ladCntrwkRegstrService.findAgrFile(luiIdn));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn downloadPdf
	/// @brief 함수 간략한 설명 : 토지사용증명서 파일 열기
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
			//InputStream is = new FileInputStream(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
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
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setFileNo(fileNo);
		List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);	
		ldDocMa.setLuiIdn(ldDocMaData.get(0).getLuiIdn());
		ldDocMa.setDocCde(ldDocMaData.get(0).getDocCde());
		ldDocMa.setDocFile(ldDocMaData.get(0).getDocFile());
		ldDocMa.setPnu(ldDocMaData.get(0).getPnu());
		landCenterUseDownLogService.insertLog(ldDocMa);
	}
	
	/////////////////////////////////////////////
	/// @fn searchNullPnu
	/// @brief 함수 간략한 설명 : 연속지적도 조회및 주소 매핑
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{pnuCode}/searchNullPnu.do", method=RequestMethod.GET)
	public String searchNullPnu(@PathVariable("pnuCode") String pnu, Model model) {		
		LpPaCbnd lpPaCbnd = new LpPaCbnd();
		lpPaCbnd.setPnu(pnu);
		model.addAttribute("result", ladCntrwkRegstrService.searchPnuList(lpPaCbnd));
		return "jsonView";
	}
}
