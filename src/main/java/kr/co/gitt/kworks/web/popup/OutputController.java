package kr.co.gitt.kworks.web.popup;

import java.util.Calendar;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.model.KwsExport;
import kr.co.gitt.kworks.model.KwsExportFilterOutpt;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.service.dept.DeptService;
import kr.co.gitt.kworks.service.export.ExportService;
import kr.co.gitt.kworks.service.user.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class OutputController
/// kr.co.gitt.kworks.web.popup \n
///   ㄴ OutputController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:47:35 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 출력 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/popup/output/")
public class OutputController {
	
	/// 사용자 서비스
	@Resource
	UserService userService;
	
	/// 부서 서비스
	@Resource
	DeptService deptService;
	
	/// 내보내기 관리 서비스
	@Resource
	ExportService exportService;
	
	/////////////////////////////////////////////
	/// @fn exportOutput
	/// @brief 함수 간략한 설명 : 출력 - 내보내기 요청 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param layoutId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("exportOutput.do")
	public String exportOutput(String layoutId, Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		String deptName = null;
		String deptCode = userDTO.getDeptCode();
		if(StringUtils.isNotBlank(deptCode)) {
			KwsDept kwsDept = deptService.selectOneDept(deptCode);
			if(kwsDept != null) {
				deptName = kwsDept.getDeptNm();
			}
		}
		
		model.addAttribute("userName", userDTO.getUserNm());
		model.addAttribute("deptName", deptName);
		model.addAttribute("writeDate", Calendar.getInstance().getTime());
		model.addAttribute("layoutId", layoutId);
		
		return "popup/exportOutput";
	}
	
	/////////////////////////////////////////////
	/// @fn highRankConfirm
	/// @brief 함수 간략한 설명 : 출력 - 내보내기 승인 화면
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
	@RequestMapping("highRankConfirm.do")
	public String highRankConfirm(Long exportNo, Model model) {
		KwsExport kwsExport = exportService.selectOneExport(exportNo);
		
		KwsUser kwsUser = userService.selectOneUser(kwsExport.getRqesterId());
		String deptName = null;
		if(kwsUser != null) {
			String deptCode = kwsUser.getDeptCode();
			if(StringUtils.isNotBlank(deptCode)) {
				KwsDept kwsDept = deptService.selectOneDept(deptCode);
				if(kwsDept != null) {
					deptName = kwsDept.getDeptNm();
				}
			}
		}
		
		KwsExportFilterOutpt kwsExportFilterOutpt = kwsExport.getKwsExportFilterOutpt();
		
		model.addAttribute("exportNo", kwsExport.getExportNo());
		model.addAttribute("exportCntmId", kwsExport.getExportCntmId());
		model.addAttribute("progrsSttus", kwsExport.getProgrsSttus());
		
		model.addAttribute("userName", kwsExport.getKwsUser().getUserNm());
		model.addAttribute("deptName", deptName);
		model.addAttribute("writeDate", kwsExport.getRequstDt());
		
		model.addAttribute("layoutId", kwsExportFilterOutpt.getLayoutId());
		model.addAttribute("centerX", kwsExportFilterOutpt.getCenterX());
		model.addAttribute("centerY", kwsExportFilterOutpt.getCenterY());
		model.addAttribute("sc", kwsExportFilterOutpt.getSc());
		model.addAttribute("viewId", kwsExportFilterOutpt.getViewId());
		model.addAttribute("tmsType", kwsExportFilterOutpt.getTmsType());
		model.addAttribute("sld", kwsExportFilterOutpt.getSld());
		model.addAttribute("baseMaps", kwsExportFilterOutpt.getBaseMaps());
		model.addAttribute("userGraphics", kwsExportFilterOutpt.getUserGraphics());
		
		return "popup/highRankConfirm";
	}

	/////////////////////////////////////////////
	/// @fn highRankOutput
	/// @brief 함수 간략한 설명 : 고급 출력 화면
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
	@RequestMapping("highRankOutput.do")
	public String highRankOutput(Long exportNo, Model model) {
		KwsExport kwsExport = exportService.selectOneExport(exportNo);
		
		KwsUser kwsUser = userService.selectOneUser(kwsExport.getRqesterId());
		String deptName = null;
		if(kwsUser != null) {
			String deptCode = kwsUser.getDeptCode();
			if(StringUtils.isNotBlank(deptCode)) {
				KwsDept kwsDept = deptService.selectOneDept(deptCode);
				if(kwsDept != null) {
					deptName = kwsDept.getDeptNm();
				}
			}
		}
		
		KwsExportFilterOutpt kwsExportFilterOutpt = kwsExport.getKwsExportFilterOutpt();
		
		model.addAttribute("exportNo", kwsExport.getExportNo());
		model.addAttribute("exportCntmId", kwsExport.getExportCntmId());
		
		model.addAttribute("userName", kwsExport.getKwsUser().getUserNm());
		model.addAttribute("userGrad", kwsExport.getKwsUser().getUserGrad());
		model.addAttribute("deptName", deptName);
		model.addAttribute("writeDate", kwsExport.getRequstDt());
		
		model.addAttribute("layoutId", kwsExportFilterOutpt.getLayoutId());
		
		model.addAttribute("sc", kwsExportFilterOutpt.getSc());
		model.addAttribute("viewId", kwsExportFilterOutpt.getViewId());
		model.addAttribute("tmsType", kwsExportFilterOutpt.getTmsType());
		model.addAttribute("sld", kwsExportFilterOutpt.getSld());
		model.addAttribute("baseMaps", kwsExportFilterOutpt.getBaseMaps());
		model.addAttribute("userGraphics", kwsExportFilterOutpt.getUserGraphics());
		// Lks : 항공영상 원점 정보
		model.addAttribute("originX", kwsExportFilterOutpt.getOriginX());
		model.addAttribute("originY", kwsExportFilterOutpt.getOriginY());
		
		
		model.addAttribute("centerX", kwsExportFilterOutpt.getCenterX());
		model.addAttribute("centerY", kwsExportFilterOutpt.getCenterY());
		
		model.addAttribute("minX", kwsExportFilterOutpt.getMinX());
		model.addAttribute("minY", kwsExportFilterOutpt.getMinY());
		model.addAttribute("maxX", kwsExportFilterOutpt.getMaxX());
		model.addAttribute("maxY", kwsExportFilterOutpt.getMaxY());
		
		model.addAttribute("flag", kwsExportFilterOutpt.getFlag());

		// Lks : End
		return "popup/highRankOutput";
	}
	
	/////////////////////////////////////////////
	/// @fn highRankOutput
	/// @brief 함수 간략한 설명 : 고급 출력 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="selectOneHighRankOutput.do", method=RequestMethod.GET)
	public String selectOneHighRankOutput(Model model) {
		KwsExport kwsExport = exportService.selectOneExportNo();
		model.addAttribute("exportNo", kwsExport.getExportNo());
		return "jsonView";
	}
}
	
