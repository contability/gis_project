package kr.co.gitt.kworks.projects.ss.web.clipreport;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.clipsoft.clipreport.oof.OOFDocument;
import com.clipsoft.clipreport.server.service.ReportUtil;

/////////////////////////////////////////////
/// @class ClipReportController
/// kr.co.gitt.kworks.web.clipreport \n
///   ㄴ ClipReportController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:28:19 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 클립리포트 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller("ssClipReportController")
@RequestMapping("/clipreport/")
@Profile({"ss_dev", "ss_oper"})
public class ClipReportController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	
	/////////////////////////////////////////////
	/// @fn ctrlpntWtnnc
	/// @brief 함수 간략한 설명 : 도시 기준점 점의조서 인쇄
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrCde
	/// @param ftrIdn
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="ctrlPntWtnnc.do", method=RequestMethod.GET)
	public String ctrlpntWtnnc(@RequestParam("ftrCde") String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
	OOFDocument oof = OOFDocument.newOOF();
	
	StringBuffer path = new StringBuffer();
	path.append("%root%/crf/cntrPnt/RDL_PCBS_PS_CTRLPNT_WTNNC.crf");
	
	oof.addFile("crf.root", path.toString());
	oof.addField("FTR_CDE", ftrCde);
	oof.addField("FTR_IDN", ftrIdn);
	oof.addConnectionData("*", "dataconnection1");
	
	String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
	ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
	
	String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
	model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
	
	return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn ctrlpntRegstr
	/// @brief 함수 간략한 설명 : 도시기준점 관리대장 인쇄
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrCde
	/// @param ftrIdn
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="ctrlPntRegstr.do", method=RequestMethod.GET)
	public String ctrlpntRegstr(@RequestParam("ftrCde") String ftrCde, @RequestParam("serIdn") String serIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
	OOFDocument oof = OOFDocument.newOOF();
	
	StringBuffer path = new StringBuffer();
	path.append("%root%/crf/cntrPnt/RDL_PCBS_PS_CTRLPNT_REGSTR.crf");
	
	oof.addFile("crf.root", path.toString());
	oof.addField("FTR_CDE", ftrCde);
	oof.addField("SER_IDN", serIdn);
	oof.addConnectionData("*", "dataconnection1");
	
	String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
	ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
	
	String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
	model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
	
	return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn ctrlpntWrinvstg
	/// @brief 함수 간략한 설명 : 도시기준점 조사서 인쇄
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrCde
	/// @param ftrIdn
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="ctrlPntWrinvstg.do", method=RequestMethod.GET)
	public String ctrlpntWrinvstg(@RequestParam("ftrCde") String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/cntrPnt/RDL_PCBS_PS_CTRLPNT_WRINVSTG.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn undergroundWater
	/// @brief 함수 간략한 설명 : 지하수관정 출력 서산
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param permNtNo
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="undergroundWater.do", method=RequestMethod.GET)
	public String undergroundWater(@RequestParam("permNtNo") String permNtNo, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/UNDERGROUND_WATER/BML_WELL_PS.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("PERM_NT_NO", permNtNo);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
}
