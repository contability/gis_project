package kr.co.gitt.kworks.rest.layer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.LayerSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.service.layer.LayerService;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class LayerCategoryController
/// kr.co.gitt.kworks.rest.layer \n
///   ㄴ LayerCategoryController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 11. 오후 6:02:27 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 레이어 카테고리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/lyrCtgry/")
public class LayerCategoryController {
	
	/// 레이어 서비스
	@Resource
	LayerService layerService;

	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAll.do", method=RequestMethod.GET)
	public String listAll(Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		LayerSearchDTO layerSearchDTO = new LayerSearchDTO();
		List<Long> authorGroupNos = new ArrayList<Long>();
		for(KwsAuthorGroup kwsAuthorGroup : userDTO.getKwsAuthorGroups()) {
			authorGroupNos.add(kwsAuthorGroup.getAuthorGroupNo());
		}
		layerSearchDTO.setAuthorGroupNos(authorGroupNos);
		try {
			model.addAttribute("rows", layerService.listAllLayerCategory(layerSearchDTO));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "jsonView";
	}
}
