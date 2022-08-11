package kr.co.gitt.kworks.projects.yy.web.clipreport;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import kr.co.gitt.kworks.model.KwsMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
@Controller("yyClipReportController")
@RequestMapping("/clipreport/")
@Profile({"yy_dev", "yy_oper"})
public class ClipReportController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/////////////////////////////////////////////
	/// @fn undergroundWater
	/// @brief 함수 간략한 설명 : 지하수관정 출력 // 강릉
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
	@RequestMapping(value="ugrwtr.do", method=RequestMethod.GET)
	public String ugrwtr(@RequestParam("ftrCde") String ftrCde, @RequestParam("ftrIdn") String objectid, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/UNDRWTR/BML_WELL_PS.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", objectid);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn ctrlpnt
	/// @brief 함수 간략한 설명 : 도시 기준점 점의조서 인쇄 / 강릉
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
	@RequestMapping(value="ctrlPnt.do", method=RequestMethod.GET)
	public String ctrlpnt(@RequestParam("ftrCde") String ftrCde, @RequestParam("ftrIdn") String ftrIdn, @RequestParam("dataId") String dataId, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/cntrPnt/yy/yy_ctrlpnt.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("DATA_ID", dataId);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn mesrSgnalSttusExaminHist
	/// @brief 함수 간략한 설명 : 측량표지현황조사
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftfCde
	/// @param ftfIdn
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="mesrSgnalSttusExaminHist.do", method=RequestMethod.GET)
	public String mesrSgnalSttusExaminHist(@RequestParam("ftfCde") String ftfCde, @RequestParam("ftfIdn") String ftfIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/ETL/ETT_CPSV_DT.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTF_CDE", ftfCde);
		oof.addField("FTF_IDN", ftfIdn);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	@RequestMapping(value="ETL_RGCP_PS_stats.do", method=RequestMethod.GET)
	public String stats(KwsMenu kwsMenu, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/stats/ETL/ETL_RGCP_PS_STAT.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	
	@RequestMapping(value="plcyStatAspnt.do", method=RequestMethod.GET)
	public String plcyStatAspnt(@RequestParam("fileName") String fileName, @RequestParam("ftrIdn") String ftrIdn, @RequestParam("ftrCde") String ftrCde, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/plcy/PLCY_STAT_AS.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FULLPATH", fileName);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("FTR_CDE", ftrCde);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	
}
