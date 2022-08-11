package kr.co.gitt.kworks.rest.sharingMap;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.dto.SharingMapDTO;
import kr.co.gitt.kworks.service.sharingMap.SharingMapService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class SharingMapController
/// kr.co.gitt.kworks.rest.sharingMap \n
///   ㄴ SharingMapController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author |   이승재       |
///    | Date | 2019. 05. 10. |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 공유지도 컨트롤러 입니다. 
///   -# 
/////////////////////////////////////////////

@Controller
@RequestMapping("/rest/sharingMap/")
@Profile({"yg_dev", "yg_oper"})
public class SharingMapController {
	
	/// 공유지도 서비스
	@Resource
	SharingMapService sharingMapService;

	/////////////////////////////////////////////
	/// @fn importKml
	/// @brief 함수 간략한 설명 : KML파일 임포트 
	/// @remark
	/// - 함수의 상세 설명 : KML 파일 데이터를 공유지도 데이터에 저장한다.
	/// @param SharingMapDTO
	/// @param model
	/// @param multipartRequest
	/// @return jsonView 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="importKml.do", method=RequestMethod.POST)
	public String importKml(SharingMapDTO sharingMapDTO, MultipartRequest multipartRequest, Model model) {
		String userId = ((UserDTO)EgovUserDetailsHelper.getAuthenticatedUser()).getUserId();
		sharingMapDTO.setWrterId(userId);
		sharingMapDTO.setUpdusrId(userId);
		
		boolean isSuccess = true;
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			try {
				isSuccess = sharingMapService.importKml(multipartFile, sharingMapDTO);
			} catch (Exception e) {
				isSuccess = false;
			}
		}
		model.addAttribute("isSuccess", isSuccess);
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addPhotoManage
	/// @brief 함수 간략한 설명 : 등록 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageDTO
	/// @param model
	/// @param multipartFile
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addSharingMap(SharingMapDTO sharingMapDTO, Model model, MultipartRequest multipartRequest) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		sharingMapDTO.setWrterId(userDTO.getUserId());
		sharingMapDTO.setUpdusrId(userDTO.getUserId());
		
		Iterator<String> fileNmList = multipartRequest.getFileNames();
		if (fileNmList.hasNext()) {	//사진이 포함된 경우
			MultipartFile multipartFile = multipartRequest.getFile(fileNmList.next());
			model.addAttribute("data", sharingMapService.addSharingMap(sharingMapDTO, multipartFile, 270, 210));
		} else {	//사진이 포함되지 않은 경우
			model.addAttribute("data", sharingMapService.addSharingMap(sharingMapDTO));
		}

		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="listAllPoi.do", method=RequestMethod.GET)
	public String listAllPoi(Model model) {
		model.addAttribute("rows", sharingMapService.listAll());
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneSharingMap
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn, ftrCde
	/// @param model
	/// @return jsonView
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrCde}/{ftrIdn}/select.do", method=RequestMethod.GET)
	public String selectOneSharingMap(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, SharingMapDTO sharingMapDTO, Model model) {
		model.addAttribute("data", sharingMapService.selectOne(sharingMapDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifySharingMap
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageDTO
	/// @param model
	/// @param multipartRequest
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="modify.do", method=RequestMethod.POST)
	public String modifySharingMap(SharingMapDTO sharingMapDTO, Model model, MultipartRequest multipartRequest) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		sharingMapDTO.setUpdusrId(userDTO.getUserId());
		
		MultipartFile multipartFile = null;
		if(multipartRequest.getFileMap().size() > 0) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
			}
		}
		
		model.addAttribute("data", sharingMapService.modifySharingMap(sharingMapDTO, multipartFile, 270, 210));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeSharingMap
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 공유지도 삭제
	/// @param FtrCde
	/// @param ftrIdn
	/// @param model
	/// @return jsonView
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrCde}/{ftrIdn}/remove.do", method=RequestMethod.GET)
	public String removeSharingMap(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, SharingMapDTO sharingMapDTO, Model model) {
		model.addAttribute("data", sharingMapService.removeSharingMap(sharingMapDTO));
		return "jsonView";
	}
}
