package kr.co.gitt.kworks.rest.analysis.crossSection;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsCrsScnvew;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.service.analysis.crossSection.CrossSectionalViewService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class CrossSectionalViewController
/// kr.co.gitt.kworks.rest.analysis.crossSection \n
///   ㄴ CrossSectionalViewController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 1. 오전 11:14:57 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 횡단면도 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/analysis/crossSection/")
public class CrossSectionalViewController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	/// 횡단면도 서비스
	@Resource
	CrossSectionalViewService crossSectionalViewService;
	
	/////////////////////////////////////////////
	/// @fn listAllCrossSectionalView
	/// @brief 함수 간략한 설명 : 횡단면도 목록 검색 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("listAll.do")
	public String listAllCrossSectionalView(KwsMenu kwsMenu, Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		List<KwsCrsScnvew> kwsCrsScnvews = crossSectionalViewService.listAllCrossSection();
		for(int i=kwsCrsScnvews.size()-1; i >= 0; i--) {
			KwsCrsScnvew kwsCrsScnvew = kwsCrsScnvews.get(i);
			String dataId = kwsCrsScnvew.getDataId();
			boolean isAuthor = false;
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
						isAuthor = true;
						break;
					}
				}
			}
			if(!isAuthor) {
				kwsCrsScnvews.remove(kwsCrsScnvew);
			}
		}
		model.addAttribute("rows", kwsCrsScnvews);
		return "jsonView";
	}
	
}
