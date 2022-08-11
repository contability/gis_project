package kr.co.gitt.kworks.projects.gn.web.mesrSgnal;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.gn.model.EttCpsvDt;
import kr.co.gitt.kworks.projects.gn.service.mesrSgnal.MesrSgnalSttusExaminHistService;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartRequest;

/////////////////////////////////////////////
/// @class MeasurementSignalStatusExaminationHistoryController
/// kr.co.gitt.kworks.projects.gn.web.mesrSgnal \n
///   ㄴ MeasurementSignalStatusExaminationHistoryController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 4. 26. 오후 12:14:39 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 측량표지현황조사 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/mesrSgnalSttusExaminHist/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class MeasurementSignalStatusExaminationHistoryController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 측량표지현황조사 서비스
	@Resource
	MesrSgnalSttusExaminHistService mesrSgnalSttusExaminHistService;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn listMesrSgnalSttusExaminHistPage
	/// @brief 함수 간략한 설명 : 측량표지현황조사 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftfCde
	/// @param ftfIdn
	/// @param ettCpsvDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftfCde}/{ftfIdn}/listMesrSgnalSttusExaminHistPage.do", method=RequestMethod.GET)
	public String listMesrSgnalSttusExaminHistPage(@PathVariable("ftfCde") String ftfCde, @PathVariable("ftfIdn") Long ftfIdn, EttCpsvDt ettCpsvDt, Model model) {
		
		ettCpsvDt.setFtfCde(ftfCde);
		ettCpsvDt.setFtfIdn(ftfIdn);
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("cpsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", mesrSgnalSttusExaminHistService.listMesrSgnalSttusExaminHist(ettCpsvDt));
		
		return "/projects/gn/job/mesrSgnal/listMesrSgnalSttusExaminHist";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneMesrSgnalSttusExaminHistPage
	/// @brief 함수 간략한 설명 : 측량표지현황조사 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param ettCpsvDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOneMesrSgnalSttusExaminHistPage.do", method=RequestMethod.GET)
	public String selectOneMesrSgnalSttusExaminHistPage(@PathVariable("ftrIdn") Long ftrIdn, EttCpsvDt ettCpsvDt, Model model) {
		
		ettCpsvDt.setFtrIdn(ftrIdn);
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 지형지물부호
		kwsDomnCode.setDomnId("KWS-000");
		model.addAttribute("ftrCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점 지형지물부호
		kwsDomnCode.setDomnId("KWS-000");
		model.addAttribute("ftfCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점표석상태
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("cpsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 상부표석상태
		kwsDomnCode.setDomnId("KWS-0668");
		model.addAttribute("spsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 하부표석상태
		kwsDomnCode.setDomnId("KWS-0668");
		model.addAttribute("sbsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 조사자판단표석상태
		kwsDomnCode.setDomnId("KWS-223");
		model.addAttribute("dcsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", mesrSgnalSttusExaminHistService.selectOneMesrSgnalSttusExaminHist(ettCpsvDt));

		return "/projects/gn/job/mesrSgnal/viewMesrSgnalSttusExaminHist";
	}
	
	/////////////////////////////////////////////
	/// @fn addMesrSgnalSttusExaminHistPage
	/// @brief 함수 간략한 설명 : 측량표지현황조사 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftfCde}/{ftfIdn}/addMesrSgnalSttusExaminHistPage.do", method=RequestMethod.GET)
	public String addMesrSgnalSttusExaminHistPage(@PathVariable("ftfCde") String ftfCde, @PathVariable("ftfIdn") Long ftfIdn, EttCpsvDt ettCpsvDt, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 지형지물부호
		kwsDomnCode.setDomnId("KWS-000");
		model.addAttribute("ftrCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점 지형지물부호
		kwsDomnCode.setDomnId("KWS-000");
		model.addAttribute("ftfCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점표석상태
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("cpsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 상부표석상태
		kwsDomnCode.setDomnId("KWS-0668");
		model.addAttribute("spsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 하부표석상태
		kwsDomnCode.setDomnId("KWS-0668");
		model.addAttribute("sbsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 조사자판단표석상태
		kwsDomnCode.setDomnId("KWS-223");
		model.addAttribute("dcsCde", domnCodeService.listDomnCode(kwsDomnCode));

		ettCpsvDt.setFtfCde(ftfCde);
		ettCpsvDt.setFtfIdn(ftfIdn);
		model.addAttribute("result", ettCpsvDt);
		
		return "/projects/gn/job/mesrSgnal/addMesrSgnalSttusExaminHist";
	}
	
	/////////////////////////////////////////////
	/// @fn addMesrSgnalSttusExaminHist
	/// @brief 함수 간략한 설명 : 측량표지현황조사 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettCpsvDt
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addMesrSgnalSttusExaminHist.do", method=RequestMethod.POST)
	public String addMesrSgnalSttusExaminHist(EttCpsvDt ettCpsvDt, MultipartRequest multipartRequest, Model model) throws Exception {
		model.addAttribute("rowCount", mesrSgnalSttusExaminHistService.addMesrSgnalSttusExaminHist(ettCpsvDt, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyMesrSgnalSttusExaminHistPage
	/// @brief 함수 간략한 설명 : 측량표지현황조사 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param ettCpsvDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyMesrSgnalSttusExaminHistPage.do", method=RequestMethod.GET)
	public String modifyMesrSgnalSttusExaminHistPage(@PathVariable("ftrIdn") Long ftrIdn, EttCpsvDt ettCpsvDt, Model model) {
		
		ettCpsvDt.setFtrIdn(ftrIdn);
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 지형지물부호
		kwsDomnCode.setDomnId("KWS-000");
		model.addAttribute("ftrCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점 지형지물부호
		kwsDomnCode.setDomnId("KWS-000");
		model.addAttribute("ftfCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점표석상태
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("cpsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 상부표석상태
		kwsDomnCode.setDomnId("KWS-0668");
		model.addAttribute("spsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 하부표석상태
		kwsDomnCode.setDomnId("KWS-0668");
		model.addAttribute("sbsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 조사자판단표석상태
		kwsDomnCode.setDomnId("KWS-223");
		model.addAttribute("dcsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", mesrSgnalSttusExaminHistService.selectOneMesrSgnalSttusExaminHist(ettCpsvDt));
		
		return "/projects/gn/job/mesrSgnal/modifyMesrSgnalSttusExaminHist";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyMesrSgnalSttusExaminHist
	/// @brief 함수 간략한 설명 : 측량표지현황조사 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param ettCpsvDt
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyMesrSgnalSttusExaminHist.do", method=RequestMethod.POST)
	public String modifyMesrSgnalSttusExaminHist(@PathVariable("ftrIdn") Long ftrIdn, EttCpsvDt ettCpsvDt, MultipartRequest multipartRequest, Model model) throws Exception {
	
		ettCpsvDt.setFtrIdn(ftrIdn);
		
		model.addAttribute("rowCount", mesrSgnalSttusExaminHistService.modifyMesrSgnalSttusExaminHist(ettCpsvDt, multipartRequest));
		
		return "jsonViewTextPlain";
	}
	
}
