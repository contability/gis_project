package kr.co.gitt.kworks.web.cmmn;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.layer.LayerService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class AuthorConfirmController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ AuthorConfirmController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:31:38 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 권한 확인 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cmmn/authorConfirm/")
public class AuthorConfirmController {
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 레이어 서비스
	@Resource
	LayerService layerService;
	
	/////////////////////////////////////////////
	/// @fn confirmIndication
	/// @brief 함수 간략한 설명 : 표시 확인 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param kwsMenu
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/indict.do", method=RequestMethod.GET)
	public String confirmIndication(@PathVariable("dataId") String dataId, KwsMenu kwsMenu, Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		boolean isAuthor = false;
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		model.addAttribute("dataId", dataId);
		model.addAttribute("isAuthor", isAuthor);
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn confirmEdit
	/// @brief 함수 간략한 설명 : 편집 권한 확인
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param kwsMenu
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/edit.do", method=RequestMethod.GET)
	public String confirmEdit(@PathVariable("dataId") String dataId, KwsMenu kwsMenu, Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isEdit = false;
		boolean isAuthor = false;
		
		KwsData kwsData = dataService.selectOneData(dataId);
		if(kwsData != null && StringUtils.equals("Y", kwsData.getEditAt())) {
			List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getEditAt())) {
						isAuthor = true;
						break;
					}
				}
			}
			isEdit = true;
		}
		model.addAttribute("isEdit", isEdit);
		model.addAttribute("isAuthor", isAuthor);
		model.addAttribute("data", dataService.selectOneData(dataId));
		model.addAttribute("layer", layerService.selectOneLayerByDataId(dataId));
		return "jsonView";
	}
	
	//차단제수변 분석 권한 확인
	@RequestMapping(value="{dataId}/wtrCntlVlAnal.do", method=RequestMethod.GET)
	public String wtrCntlVlAnal(@PathVariable("dataId") String dataId, KwsMenu kwsMenu, Model model){
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		
		KwsData kwsData = dataService.selectOneData(dataId);
		if(kwsData != null) {
			List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(dataId, kwsDataAuthor.getDataId())) {
						isAuthor = true;
						break;
					}
				}
			}
		}
		model.addAttribute("isAuthor", isAuthor);
		model.addAttribute("data", dataService.selectOneData(dataId));
		model.addAttribute("layer", layerService.selectOneLayerByDataId(dataId));
		return "jsonView";
	}
	
}
