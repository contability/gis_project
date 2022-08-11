package kr.co.gitt.kworks.web.manage;

import javax.annotation.Resource;

import kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO;
import kr.co.gitt.kworks.model.KwsBaseMap;
import kr.co.gitt.kworks.model.KwsBaseMap.MapType;
import kr.co.gitt.kworks.service.baseMap.BaseMapService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class BcrnMapManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ BcrnMapManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:45:11 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 배경 지도 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/bcrnMap/")
public class BcrnMapManageController {
	
	/// 배경지도 서비스
	@Resource
	BaseMapService baseMapService;

	/////////////////////////////////////////////
	/// @fn listBcrnMapPage
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 배경 지도 페이지 연결
	/// @param searchDTO
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listBcrnMapPage(Model model) {
		BaseMapSearchDTO searchDTO = new BaseMapSearchDTO();
		searchDTO.setMapType(MapType.FLIGHT);
		model.addAttribute("rows", baseMapService.listAllBaseMap(searchDTO));
		return "/manage/bcrnMap/listBcrnMap";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneBcrnMap
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param mapNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{mapNo}/select.do", method=RequestMethod.GET)
	public String selectOneBcrnMap(@PathVariable("mapNo") Long mapNo, Model model) {
		model.addAttribute("data", baseMapService.selectOneBaseMap(mapNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyBcrnMap
	/// @brief 함수 간략한 설명 : 배경지도 정보 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param mapNo
	/// @param kwsBaseMap
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{mapNo}/modifyBcrnMapInfo.do", method=RequestMethod.POST)
	public String modifyBcrnMapInfo(@PathVariable("mapNo") Long mapNo, KwsBaseMap kwsBaseMap, Model model) {
		kwsBaseMap.setMapNo(mapNo);
		model.addAttribute("rowCount", baseMapService.modifyBcrnMapInfo(kwsBaseMap));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listAerialPhotoPage
	/// @brief 함수 간략한 설명 : 항공사진 관리 페이지 연결
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAerialPhoto.do", method=RequestMethod.GET)
	public String listAerialPhotoPage(Model model) {
		BaseMapSearchDTO searchDTO = new BaseMapSearchDTO();
		searchDTO.setMapType(MapType.FLIGHT);
		model.addAttribute("rows", baseMapService.listAllBaseMap(searchDTO));
		return "/manage/bcrnMap/listAerialPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneAerialPhotoPage
	/// @brief 함수 간략한 설명 : 항공사진 메타데이터 조회 페이지
	/// @remark
	/// - 함수의 상세 설명 :
	/// @param mapNo
	/// @param kwsBaseMap
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{mapNo}/selectOneAerialPhotoPage.do", method=RequestMethod.GET)
	public String selectOneAerialPhotoPage(@PathVariable("mapNo") Long mapNo, KwsBaseMap kwsBaseMap, Model model) {
	kwsBaseMap.setMapNo(mapNo);
	model.addAttribute("result", baseMapService.selectOneBaseMap(mapNo));
	return "/projects/dh/job/aerialPhoto/viewAerialPhoto";
	}
	
}
