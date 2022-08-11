package kr.co.gitt.kworks.web.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ThemamapSearchDTO;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.model.KwsThemamap.ThemamapTy;
import kr.co.gitt.kworks.service.sys.SysService;
import kr.co.gitt.kworks.service.themamap.ThemamapService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class BassThemamapManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ BassThemamapManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 29. 오전 8:30:12 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 주제도 관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/bassThemamap/")
public class BassThemamapManageController {
	
	// 시스템 서비스
	@Resource
	SysService sysService;
	
	// 주제도 서비스
	@Resource
	ThemamapService themamapService;

	/////////////////////////////////////////////
	/// @fn listBassThemamapPage
	/// @brief 함수 간략한 설명 : 목록 전체 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listBassThemamapPage(Model model) {
		ThemamapSearchDTO themamapSearchDTO = new ThemamapSearchDTO();
		List<ThemamapTy> themamapTys = new ArrayList<ThemamapTy>();
		themamapTys.add(ThemamapTy.SYS);
		themamapSearchDTO.setThemamapTys(themamapTys);
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		model.addAttribute("rows", sysService.listAllSys(kwsSys));
		model.addAttribute("themamapList", themamapService.listAllThemamap(themamapSearchDTO));
		return "/manage/bassThemamap/listBassThemamap";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneBassThemamap
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sysId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{sysId}/select.do", method=RequestMethod.GET)
	public String selectOneBassThemamap(@PathVariable("sysId") Long sysId, Model model) {
		model.addAttribute("data", sysService.selectOneSys(sysId));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyBassThemamap
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sysId
	/// @param kwsSys
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{sysId}/modify.do", method=RequestMethod.POST)
	public String modifyBassThemamap(@PathVariable("sysId") Long sysId, KwsSys kwsSys, Model model) throws FdlException {
		kwsSys.setSysId(sysId);
		
		model.addAttribute("rowCount", sysService.modifyBassThemamap(kwsSys));
		return "jsonView";
	}
	
}
