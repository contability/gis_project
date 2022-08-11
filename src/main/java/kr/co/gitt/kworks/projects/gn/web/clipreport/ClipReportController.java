package kr.co.gitt.kworks.projects.gn.web.clipreport;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
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
@Controller("gnClipReportController")
@RequestMapping("/clipreport/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
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
	/// @brief 함수 간략한 설명 : 도시 기준점 점의조서 인쇄(일괄출력) / 강릉
	/// @수정자
	///   - 문세준, 2020.02.25, 점의조서 일괄출력 추가
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
	public String ctrlpnt(@RequestParam("ftrCde") String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/gn/cntrPnt/ETL_RGCP_PS.crf");
		
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
	
	/////////////////////////////////////////////
	/// @fn changeDetectionPnt
	/// @brief 함수 간략한 설명 : 변화탐지 출력
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="changeDetectionPnt.do", method=RequestMethod.GET)
	public String changeDetectionPnt(@RequestParam("chngeDetctNo") String chngeDetctNo, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/CHN/CHG_AREA_DT.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("CHNGE_DETCT_NO", chngeDetctNo);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn ocpatRdtOcpeDt
	/// @brief 함수 간략한 설명 : 점용허가 / 도로점용허가
	/// @제작자
	///   - 조원기, 2020.04.06, 점용허가 // 도로점용/건축허가/개발행위/사설안내
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
	@RequestMapping(value="/ocpatRdtOcpeDt.do", method=RequestMethod.GET)
	public String ocpatRdtOcpeDtpnt(@RequestParam("ftrCde") String layerId, String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/").append("RDT_OCPE_DT_2.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("keyStr", keyString);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	
	/////////////////////////////////////////////
	/// @fn ocpatRdtOcpeDt
	/// @brief 함수 간략한 설명 : 점용허가 / 도로점용허가
	/// @제작자
	///   - 조원기, 2020.04.06, 점용허가 // 변경허가
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
	@RequestMapping(value="/ocpatRdtOccnDt.do", method=RequestMethod.GET)
	public String ocpatRdtOccnDt(@RequestParam("ftrCde") String layerId, String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/").append("RDT_OCCN_DT_2.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("keyStr", keyString);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	
	/////////////////////////////////////////////
	/// @fn ocpatRdtOcpeDt
	/// @brief 함수 간략한 설명 : 점용허가 / 도로점용허가
	/// @제작자
	///   - 조원기, 2020.04.06, 점용허가 // 원상회복공사
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
	@RequestMapping(value="/ocpatRdtOcreDt.do", method=RequestMethod.GET)
	public String ocpatRdtOcreDtpnt(@RequestParam("ftrCde") String layerId, String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/").append("RDT_OCRE_DT_2.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("keyStr", keyString);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	
	/////////////////////////////////////////////
	/// @fn ocpatRdtOcpeDt
	/// @brief 함수 간략한 설명 : 점용허가 / 도로점용허가
	/// @제작자
	///   - 조원기, 2020.04.06, 점용허가 // 불허가
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
	@RequestMapping(value="/ocpatRdtOcdsDt.do", method=RequestMethod.GET)
	public String ocpatRdtOcdsDtpnt(@RequestParam("ftrCde") String layerId, String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/").append("RDT_OCDS_DT_2.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("keyStr", keyString);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn ocpatRdtOcpeDt
	/// @brief 함수 간략한 설명 : 점용허가 / 도로점용허가
	/// @제작자
	///   - 조원기, 2020.04.06, 점용허가 // 점용취소
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
	
	/**
	 * 메세지 소스
	 */
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	@RequestMapping(value="/ocpatRdtOccaDt.do", method=RequestMethod.GET)
	public String ocpatRdtOccaDtpnt(@RequestParam("ftrCde") String layerId, String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		
		OOFDocument oof = OOFDocument.newOOF();
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/").append("RDT_OCCA_DT_2.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("keyStr", keyString);
		
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	
	
	/////////////////////////////////////////////
	/// @fn ocpatRdtOcpeDt
	/// @brief 함수 간략한 설명 : 점용허가 / 도로점용허가
	/// @제작자
	///   - 조원기, 2020.04.06, 점용허가 // 취하원
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
	@RequestMapping(value="/ocpatRdtOcdrDt.do", method=RequestMethod.GET)
	public String ocpatRdtOcdrDtpnt(@RequestParam("ftrCde") String layerId, String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/").append("RDT_OCDR_DT_2.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("keyStr", keyString);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn ocpatRdtOcpeDt
	/// @brief 함수 간략한 설명 : 점용허가 / 도로점용허가
	/// @제작자
	///   - 조원기, 2020.04.06, 점용허가 // 취하원
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
	@RequestMapping(value="/{ftrCde}/ocpatPrint.do", method=RequestMethod.GET)
	public String ocpatPrint(@RequestParam("ftrCde") String layerId, String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/").append(ftrCde).append(".crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("FTR_CDE", ftrCde);
		oof.addField("FTR_IDN", ftrIdn);
		
		oof.addField("keyStr", keyString);
		
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn ocpatRdtOcpeDt
	/// @brief 함수 간략한 설명 : 점용허가 / 도로점용허가
	/// @제작자
	///   - 조원기, 2020.04.28, 점용허가 // 통게
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
	@RequestMapping(value="ocpatStats.do", method=RequestMethod.GET)
	public String ocpatStatsPnt(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/Ocpat_Stats.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	@RequestMapping(value="ocpatBjdStats.do", method=RequestMethod.GET)
	public String ocpatBjdStats(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/OCPAT/Ocpat_Bjd_Stats.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	@RequestMapping(value="urbPlanMapnt.do", method=RequestMethod.GET)
	public String urbPlanMapnt(@RequestParam("ftrIdn") String ftrIdn, @RequestParam("fileName") String fileName, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/PLAN/URB_PLAN_MA.crf");
		
		oof.addField("FTR_IDN", ftrIdn);
		oof.addField("FULLPATH", fileName);
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	
	@RequestMapping(value="manage/mngUserAuthorHist.do", method=RequestMethod.GET)
	public String mngUserAuthorHist(SearchDTO searchDTO, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		
		path.append("%root%/crf/manage/MNG_USER_AUTHOR_HIST.crf");
		
		oof.addField("SEARCH_CONDITION", searchDTO.getSearchCondition());
		
		if(searchDTO.getSearchKeyword() != null && searchDTO.getSearchKeyword() != ""){
			oof.addField("SEARCH_KEYWORD", searchDTO.getSearchKeyword());
		}
		
		if(searchDTO.getSearchStartDt() != null && searchDTO.getSearchStartDt() != "" && searchDTO.getSearchEndDt() != null && searchDTO.getSearchEndDt() != ""){
			oof.addField("START_DATE", searchDTO.getSearchStartDt());
			oof.addField("END_DATE", searchDTO.getSearchEndDt());
		}
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath = request.getSession().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportKey = ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportKey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	@RequestMapping(value="manage/spatialInfoData.do", method=RequestMethod.GET)
	public String spatialInfoData(@RequestParam(required=false) String searchCondition, @RequestParam(required=false) String startDate, @RequestParam(required=false) String endDate, @RequestParam(required=false) String searchKeyword, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		
		//path.append("%root%/crf/prntng/SPATIALINFODATA/SPATIAL_INFO_DATA.crf");
		path.append("%root%/crf/manage/SPATIAL_INFO_DATA.crf");
		
		if(searchCondition != null){
			oof.addField("SEARCH_CONDITION", searchCondition);
		}
		
		if(startDate != null){
			oof.addField("START_DATE", startDate);
			oof.addField("END_DATE", endDate);
		}
		
		if(searchKeyword != null){
			oof.addField("SEARCH_KEYWORD", searchKeyword);
		}
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath = request.getSession().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportKey = ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportKey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	@RequestMapping(value="manage/mngGrphinfoManageRegstr.do", method=RequestMethod.GET)
	public String mngGrphinfoManageRegstr(@RequestParam("regstrSn") String regstrSn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		
		path.append("%root%/crf/manage/MNG_GRPHINFO_MANAGE_REGSTR.crf");
		
		oof.addField("REGSTR_SN", regstrSn);
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath = request.getSession().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportKey = ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportKey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	@RequestMapping(value="manage/mngGrphinfoUploadDtls.do", method=RequestMethod.GET)
	public String mngGrphinfoUploadDtls(@RequestParam("regstrSn") String regstrSn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		
		path.append("%root%/crf/manage/MNG_GRPHINFO_UPLOAD_DTLS.crf");
		
		oof.addField("REGSTR_SN", regstrSn);
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath = request.getSession().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportKey = ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportKey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	@RequestMapping(value="manage/krasDataPvsnRegstr.do", method=RequestMethod.GET)
	public String mngKrasDataPvsnRegstr(@RequestParam(value="searchCondition", required=false) String searchCondition, 
			@RequestParam(value="searchStartDt", required=false) String searchStartDt, 
			@RequestParam(value="searchEndDt", required=false) String searchEndDt,
			@RequestParam(value="searchKeyword", required=false) String searchKeyword,
			HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		
		path.append("%root%/crf/manage/MNG_KRAS_DATA_PVSN_REGSTR.crf");
		
		oof.addField("SEARCHCONDITION", searchCondition);
		oof.addField("SEARCHSTARTDT", searchStartDt);
		oof.addField("SEARCHENDDT", searchEndDt);
		oof.addField("SEARCHKEYWORD", searchKeyword);
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath = request.getSession().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportKey = ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportKey, "UTF-8"));
		
		return "clipreport/report";
	}
}
