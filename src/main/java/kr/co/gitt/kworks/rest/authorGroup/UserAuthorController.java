package kr.co.gitt.kworks.rest.authorGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class userAuthorController
/// kr.co.gitt.kworks.rest.authorGroup \n
///   ㄴ DataAuthorController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:05:27 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 권한 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/userAuthor/")
public class UserAuthorController {

	/////////////////////////////////////////////
	/// @fn listAllDataAuthor
	/// @brief 함수 간략한 설명 : 사용자 데이터 권한 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	/*
	 * 이승재, 2021.09.16
	 * 권한그룹정보도 제공하도록 수정
	 * 클래스명 변경(DataAuthorController -> UserAuthorcontroller)
	 * UserAuthorGroupController 삭제(기능 통합)
	 * */
	@RequestMapping(value="/listAll.do", method=RequestMethod.GET)
	public String listAllDataAuthor(Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		List<String> dataIds = new ArrayList<String>();
		List<KwsDataAuthor> kwsDataAuthors = new ArrayList<KwsDataAuthor>();
		List<KwsAuthorGroup> userAuthorGroups = new ArrayList<KwsAuthorGroup>();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			//권한그룹
			KwsAuthorGroup userAuthorGroup = new KwsAuthorGroup();
			userAuthorGroup.setAuthorGroupNm(kwsAuthorGroup.getAuthorGroupNm());
			userAuthorGroup.setAuthorGroupNo(kwsAuthorGroup.getAuthorGroupNo());
			userAuthorGroups.add(userAuthorGroup);
			
			//데이터 권한
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				String dataId = kwsDataAuthor.getDataId();
				if(dataIds.contains(dataId)) {
					for(KwsDataAuthor dataAuthor : kwsDataAuthors) {
						if(StringUtils.equals(dataAuthor.getDataId(), dataId)) {
							if(kwsDataAuthor.getIndictAt().equals("Y")) {
								dataAuthor.setIndictAt("Y");
							}
							if(kwsDataAuthor.getEditAt().equals("Y")) {
								dataAuthor.setEditAt("Y");
							}
							if(kwsDataAuthor.getExportAt().equals("Y")) {
								dataAuthor.setExportAt("Y");
							}
							if(kwsDataAuthor.getPrntngAt().equals("Y")) {
								dataAuthor.setPrntngAt("Y");
							}
						}
					}
				}
				else {
					dataIds.add(dataId);
					kwsDataAuthors.add(kwsDataAuthor);
				}
			}
			
		}
		model.addAttribute("userAuthorGroups", userAuthorGroups);
		model.addAttribute("userDataAuthors", kwsDataAuthors);
		return "jsonView";
	}
	
}
