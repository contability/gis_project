package kr.co.gitt.kworks.web.window.hist;


import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.CmtPrsvHt;
import kr.co.gitt.kworks.model.RdtPrsvHt;
import kr.co.gitt.kworks.model.SwtPrsvHt;
import kr.co.gitt.kworks.model.WttPrsvHt;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;
import kr.co.gitt.kworks.service.hist.ManageHistService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class manageHistoryController
/// kr.co.gitt.kworks.web.window.hist \n
///   ㄴ manageHistoryController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 17. 오전 10:46:48 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 관리이력 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manageHist/")
public class ManageHistoryController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 관리이력 서비스 
	@Resource
	ManageHistService manageHistService;
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;

	/////////////////////////////////////////////
	/// @fn selectOneFtrCde
	/// @brief 함수 간략한 설명 : 지형지물부호 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="selectOneFtrCde.do", method=RequestMethod.POST)
	public String selectOneFtrCde(RdtPrsvHt rdtPrsvHt, Model model) {
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
//		KwsDomnCode kwsDomnCode = new KwsDomnCode();
//		kwsDomnCode.setSearchCondition("2");
//		
//		kwsDomnCode.setDomnId("KWS-0034");
//		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 유지보수사유
		if(prjCode.equals("ss")) {
			kwsDomnCode.setDomnId("KWS-014");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gn")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("dh")) {
			kwsDomnCode.setDomnId("KWS-0943");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gc")) {
			kwsDomnCode.setDomnId("KWS-014");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("sc")) {
			kwsDomnCode.setDomnId("KWS-0024");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("yy")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gs")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}else if(prjCode.equals("sunchang")){
			kwsDomnCode.setDomnId("OGC-032");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}else if(prjCode.equals("is")){
			kwsDomnCode.setDomnId("OGC-032");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneFtrCde(rdtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 도로
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/listRoad.do", method=RequestMethod.GET)
	public String listManageHistRoad(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("rows", manageHistService.listManageHistRoad(ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 조회 - 도로
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/selectRoad.do", method=RequestMethod.GET)
	public String selectOneManageHistRoad(@PathVariable("shtIdn") Long shtIdn, Model model) {
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		
		kwsDomnCode.setDomnId("KWS-0330");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneManageHistRoad(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 수정 - 도로
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param rdtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/modifyRoad.do", method=RequestMethod.POST)
	public String modifyManageHistRoad(@PathVariable("shtIdn") Long shtIdn, RdtPrsvHt rdtPrsvHt, Model model) {
		rdtPrsvHt.setShtIdn(shtIdn);
		
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0330");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.modifyManageHistRoad(rdtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn swtManageHistCode
	/// @brief 함수 간략한 설명 : 도로 관리이력 등록 관련 코드 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="rdtManageHistCode.do", method=RequestMethod.POST)
	public String rdtManageHistCode(RdtPrsvHt rdtPrsvHt, Model model) {
	
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0330");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";

		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}

		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneFtrCde(rdtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addManageHistRoad
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 도로
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addRoad.do", method=RequestMethod.POST)
	public String addManageHistRoad(RdtPrsvHt rdtPrsvHt, Model model) throws FdlException {
		model.addAttribute("data", manageHistService.addManageHistRoad(rdtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/listWater.do", method=RequestMethod.GET)
	public String listManageHistWater(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("rows", manageHistService.listManageHistWater(ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 조회 - 상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/selectWater.do", method=RequestMethod.GET)
	public String selectOneManageHistWater(@PathVariable("shtIdn") Long shtIdn, Model model) {
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0034");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneManageHistWater(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 수정 - 상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param rdtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/modifyWater.do", method=RequestMethod.POST)
	public String modifyManageHistWater(@PathVariable("shtIdn") Long shtIdn, WttPrsvHt wttPrsvHt, Model model) {
		wttPrsvHt.setShtIdn(shtIdn);
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0034");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.modifyManageHistWater(wttPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn wttManageHistCode
	/// @brief 함수 간략한 설명 : 상수 관리이력 등록 관련 코드 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="wttManageHistCode.do", method=RequestMethod.POST)
	public String wttManageHistCode(RdtPrsvHt rdtPrsvHt, Model model) {
	
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0034");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneFtrCde(rdtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addManageHistWater
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 상수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWater.do", method=RequestMethod.POST)
	public String addManageHistWater(WttPrsvHt wttPrsvHt, Model model) throws FdlException {
		model.addAttribute("data", manageHistService.addManageHistWater(wttPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 하수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/listSewage.do", method=RequestMethod.GET)
	public String listManageHistSewage(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("rows", manageHistService.listManageHistSewage(ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 조회 - 하수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/selectSewage.do", method=RequestMethod.GET)
	public String selectOneManageHistSewage(@PathVariable("shtIdn") Long shtIdn, Model model) {
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0225");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneManageHistSewage(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 수정 - 하수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param rdtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/modifySewage.do", method=RequestMethod.POST)
	public String modifyManageHistSewage(@PathVariable("shtIdn") Long shtIdn, SwtPrsvHt swtPrsvHt, Model model) {
		swtPrsvHt.setShtIdn(shtIdn);
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0225");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.modifyManageHistSewage(swtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn swtManageHistCode
	/// @brief 함수 간략한 설명 : 하수 관리이력 등록 관련 코드 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="swtManageHistCode.do", method=RequestMethod.POST)
	public String swtManageHistCode(RdtPrsvHt rdtPrsvHt, Model model) {
	
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0225");
		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";

		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}

		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneFtrCde(rdtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addManageHistSewage
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 하수
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addSewage.do", method=RequestMethod.POST)
	public String addManageHistSewage(SwtPrsvHt swtPrsvHt, Model model) throws FdlException {
		model.addAttribute("data", manageHistService.addManageHistSewage(swtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{ftrCde}/listCommon.do", method=RequestMethod.GET)
	public String listManageHistCommon(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("ftrCde") String ftrCde, CmtPrsvHt cmtPrsvHt, Model model) {
		model.addAttribute("rows", manageHistService.listManageHistCommon(cmtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listRoadCntrwkRegstrManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 도로공사대장 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{ftrCde}/listRoadCntrwkRegstr.do", method=RequestMethod.GET)
	public String listRoadCntrwkRegstrManageHistCommon(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("ftrCde") String ftrCde, CmtPrsvHt cmtPrsvHt, Model model) {
		String dataId = "RDT_CONS_MA";
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : userDTO.getKwsAuthorGroups()) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("도로공사대장에 대한 권한이 없습니다.");
			model.addAttribute("rows", manageHistService.listManageHistCommon(cmtPrsvHt));
			return "jsonView";
		}
		model.addAttribute("rows", manageHistService.listRoadCntrwkRegstrManageHistCommon(cmtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listWrppCntrwkRegstrManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 상수공사대장 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{ftrCde}/listWrppCntrwkRegstr.do", method=RequestMethod.GET)
	public String listWrppCntrwkRegstrManageHistCommon(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("ftrCde") String ftrCde, CmtPrsvHt cmtPrsvHt, Model model) {
		String dataId = "WTT_CONS_MA";
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : userDTO.getKwsAuthorGroups()) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("상수공사대장에 대한 권한이 없습니다.");
			model.addAttribute("rows", manageHistService.listManageHistCommon(cmtPrsvHt));
			return "jsonView";
		}
		model.addAttribute("rows", manageHistService.listWrppCntrwkRegstrManageHistCommon(cmtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listSwgeCntrwkRegstrManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 리스트 조회 - 하수공사대장 연계
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{ftrCde}/listSwegCntrwkRegstr.do", method=RequestMethod.GET)
	public String listSwgeCntrwkRegstrManageHistCommon(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("ftrCde") String ftrCde, CmtPrsvHt cmtPrsvHt, Model model) {
		String dataId = "SWT_CONS_MA";
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : userDTO.getKwsAuthorGroups()) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("하수공사대장에 대한 권한이 없습니다.");
			model.addAttribute("rows", manageHistService.listManageHistCommon(cmtPrsvHt));
			return "jsonView";
		}
		model.addAttribute("rows", manageHistService.listSwgeCntrwkRegstrManageHistCommon(cmtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 조회 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/selectCommon.do", method=RequestMethod.GET)
	public String selectOneManageHistCommon(@PathVariable("shtIdn") Long shtIdn, Model model) {
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		//KwsDomnCode kwsDomnCode = new KwsDomnCode();
		//kwsDomnCode.setSearchCondition("2");
		//
		//kwsDomnCode.setDomnId("KWS-0034");
		//model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 유지보수사유
		if(prjCode.equals("ss")) {
			kwsDomnCode.setDomnId("KWS-014");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gn")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("dh")) {
			kwsDomnCode.setDomnId("KWS-0943");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gc")) {
			kwsDomnCode.setDomnId("KWS-014");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("sc")) {
			kwsDomnCode.setDomnId("KWS-0024");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("yy")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gs")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}else if(prjCode.equals("sunchang")){
			kwsDomnCode.setDomnId("OGC-032");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}else if(prjCode.equals("is")){
			kwsDomnCode.setDomnId("OGC-032");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneManageHistCommon(shtIdn));
		return "jsonView";
	}
		
	/////////////////////////////////////////////
	/// @fn modifyManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 수정 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param rdtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/modifyCommon.do", method=RequestMethod.POST)
	public String modifyManageHistCommon(@PathVariable("shtIdn") Long shtIdn, CmtPrsvHt cmtPrsvHt, Model model) {
		cmtPrsvHt.setShtIdn(shtIdn);
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
//		KwsDomnCode kwsDomnCode = new KwsDomnCode();
//		kwsDomnCode.setSearchCondition("2");
//		
//		kwsDomnCode.setDomnId("KWS-0034");
//		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 유지보수사유
		if(prjCode.equals("ss")) {
			kwsDomnCode.setDomnId("KWS-014");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gn")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("dh")) {
			kwsDomnCode.setDomnId("KWS-0943");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gc")) {
			kwsDomnCode.setDomnId("KWS-014");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("sc")) {
			kwsDomnCode.setDomnId("KWS-0024");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("yy")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gs")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}else if(prjCode.equals("sunchang")){
			kwsDomnCode.setDomnId("OGC-032");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}else if(prjCode.equals("is")){
			kwsDomnCode.setDomnId("OGC-032");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.modifyManageHistCommon(cmtPrsvHt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn cmtManageHistCode
	/// @brief 함수 간략한 설명 : 공통 관리이력 등록 관련 코드 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cmtPrsvHt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="cmtManageHistCode.do", method=RequestMethod.POST)
	public String cmtManageHistCode(RdtPrsvHt rdtPrsvHt, Model model) {
	
//		KwsDomnCode kwsDomnCode = new KwsDomnCode();
//		kwsDomnCode.setSearchCondition("2");
//		
//		kwsDomnCode.setDomnId("KWS-0034");
//		model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		String domnId = "";
		
		// 시설물관리이력구분
		if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gn")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("dh")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("ss")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("gc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("sc")) {
			domnId = "KWS-215";
		}
		else if(prjCode.equals("yy")) {
			domnId = "KWS-0306";
		}
		else if(prjCode.equals("gs")) {
			domnId = "KWS-0306";
		}else if(prjCode.equals("sunchang")){
			domnId = "KWS-0004";
		}else if(prjCode.equals("is")){
			domnId = "KWS-0004";
		}
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 유지보수사유
		if(prjCode.equals("ss")) {
			kwsDomnCode.setDomnId("KWS-014");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gn")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("dh")) {
			kwsDomnCode.setDomnId("KWS-0943");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gc")) {
			kwsDomnCode.setDomnId("KWS-014");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("sc")) {
			kwsDomnCode.setDomnId("KWS-0024");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("yy")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		else if(prjCode.equals("gs")) {
			kwsDomnCode.setDomnId("KWS-0034");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}else if(prjCode.equals("sunchang")){
			kwsDomnCode.setDomnId("OGC-032");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}else if(prjCode.equals("is")){
			kwsDomnCode.setDomnId("OGC-032");
			model.addAttribute("sbjCde", domnCodeService.listDomnCode(kwsDomnCode));
		}
		
		model.addAttribute("mnhList", manageHistService.listMnh(domnId));
		model.addAttribute("data", manageHistService.selectOneFtrCde(rdtPrsvHt));
		return "jsonView";
	}

	/////////////////////////////////////////////
	/// @fn addManageHistCommon
	/// @brief 함수 간략한 설명 : 관리이력 등록 - 공통
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPrsvHt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addCommon.do", method=RequestMethod.POST)
	public String addManageHistCommon(CmtPrsvHt cmtPrsvHt, Model model) throws FdlException {
		model.addAttribute("data", manageHistService.addManageHistCommon(cmtPrsvHt));
		return "jsonView";
	}

}
