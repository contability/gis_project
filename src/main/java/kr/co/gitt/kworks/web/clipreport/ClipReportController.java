package kr.co.gitt.kworks.web.clipreport;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsMenu;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.clipsoft.clipreport.oof.OOFDocument;
import com.clipsoft.clipreport.server.service.ReportUtil;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

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
@Controller
@RequestMapping("/clipreport/")
public class ClipReportController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/////////////////////////////////////////////
	/// @fn stats
	/// @brief 함수 간략한 설명 : 시설물 서비스 통계 연결 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sysNm
	/// @param crf
	/// @param kwsMenu
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{sysNm}/{crf}/stats.do", method=RequestMethod.GET)
	public String stats(@PathVariable("sysNm") String sysNm, @PathVariable("crf") String crf, KwsMenu kwsMenu, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/stats/").append(sysNm).append("/").append(crf).append(".crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn report
	/// @brief 함수 간략한 설명 : FTR_CDE, FTR_IDN 으로 대장 인쇄 (지하시설물 제외)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataCtgryId
	/// @param crf
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
	@RequestMapping(value="{dataCtgryId}/{dataId}/prntng.do", method=RequestMethod.GET)
	public String report(@PathVariable("dataCtgryId") String dataCtgryId, @PathVariable("dataId") String crf, @RequestParam("ftrCde") String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(crf, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getPrntngAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/").append(dataCtgryId).append("/").append(crf).append(".crf");
		
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
	/// @fn unitedRoadRegstrPrint
	/// @brief 함수 간략한 설명 : ROT_NAM, ROT_IDN, SEC_IDN 으로 대장 인쇄
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param roadRegAt
	/// @param crf
	/// @param rotNam
	/// @param rotIdn
	/// @param secIdn
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{roadRegAt}/unitedRoadRegstrPrint.do", method=RequestMethod.GET)
	public String unitedRoadRegstrPrint(@PathVariable("roadRegAt") String roadRegAt, @RequestParam("rotNam") String rotNam, @RequestParam("rotIdn") String rotIdn, @RequestParam("secIdn") String secIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/Y/ROAD_REGISTER.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("ROT_NAM", rotNam);
		oof.addField("ROT_IDN", rotIdn);
		oof.addField("SEC_IDN", secIdn);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn report
	/// @brief 함수 간략한 설명 : OBJECTID 로 대장 인쇄 (지하시설물)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param objectId
	/// @param crf
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/{objectId}/uflPrntng.do", method=RequestMethod.GET)
	public String report(@PathVariable("objectId") String objectId, @PathVariable("dataId") String crf, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/UFL/").append(crf).append(".crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("OBJECTID", objectId);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn statsMap
	/// @brief 함수 간략한 설명 : 통계지도 인쇄
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param iemNo
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{iemNo}/statsMap.do", method=RequestMethod.GET)
	public String statsMap(@PathVariable("iemNo") String iemNo, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/statsMap/statsMap.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addField("IEM_No", iemNo);
		oof.addConnectionData("*", "dataconnection1");
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn exportHistory
	/// @brief 함수 간략한 설명 : 내려받기 관리이력 출력
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @param model
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="exportHistory.do", method=RequestMethod.GET)
	public String exportHistory(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/manage/KWS_EXPORT_EXPORT_HIST.crf");
		
		oof.addFile("crf.root", path.toString());
		oof.addConnectionData("*", "dataconnection1");
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		if(userDTO.getUserGrad().name() != "ROLE_MNGR"){
			oof.addField("DEPT_CODE", userDTO.getDeptCode());
		}
		
		String propertyPath  = request.getSession().getServletContext().getRealPath("/") +  "WEB-INF" + File.separator + "clipreport4" + File.separator + "clipreport4.properties";
		ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		
		String reportkey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
		model.addAttribute("reportkey", URLEncoder.encode(reportkey, "UTF-8"));
		
		return "clipreport/report";
	}
	
	/////////////////////////////////////////////
	/// @fn roadRegstrPrint
	/// @brief 함수 간략한 설명 : FTR_CDE, FTR_IDN 으로 대장 인쇄
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param roadRegAt
	/// @param crf
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
	@RequestMapping(value="{roadRegAt}/{dataId}/roadRegstrPrint.do", method=RequestMethod.GET)
	public String roadRegstrPrint(@PathVariable("roadRegAt") String roadRegAt, @PathVariable("dataId") String crf, @RequestParam("ftrCde") String ftrCde, @RequestParam("ftrIdn") String ftrIdn, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(crf, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getPrntngAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		OOFDocument oof = OOFDocument.newOOF();
		
		StringBuffer path = new StringBuffer();
		path.append("%root%/crf/prntng/").append(roadRegAt).append("/").append(crf).append(".crf");
		
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
	
}
