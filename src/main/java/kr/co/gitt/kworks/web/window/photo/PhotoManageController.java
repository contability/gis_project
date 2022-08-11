package kr.co.gitt.kworks.web.window.photo;

import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.service.cmmn.ImageService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class PhotoManageController
/// kr.co.gitt.kworks.web.window.photo \n
///   ㄴ PhotoManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 11. 23. 오후 3:07:17 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 사진관리 컨트롤러 입니다. 
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/photoManage/")
public class PhotoManageController {
	
	/// 사진관리 서비스
	@Resource
	ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn listPhotoManageByFtrCde
	/// @brief 함수 간략한 설명 : 사진 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrCde
	/// @param kwsImage
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrCde}/list.do", method=RequestMethod.GET)
	public String listPhotoManageByFtrCde(@PathVariable("ftrCde") String ftrCde, KwsImage kwsImage, Model model) {
		kwsImage.setFtrCde(ftrCde);
		model.addAttribute("rows", imageService.listAllImage(kwsImage));
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrCde}/listImageFile.do", method=RequestMethod.GET)
	public String listPhotoManageByFtrCdeImageFile(@PathVariable("ftrCde") String ftrCde, KwsImage kwsImage, Model model) {
		kwsImage.setFtrCde(ftrCde);
		model.addAttribute("rows", imageService.listAllImageFile(kwsImage));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listPhotoManage
	/// @brief 함수 간략한 설명 : 사진 리스트 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrCde
	/// @param ftrIdn
	/// @param kwsImage
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrCde}/{ftrIdn}/list.do", method=RequestMethod.GET)
	public String listPhotoManageByFtrCdeFtrIdn(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, KwsImage kwsImage, Model model) {
		kwsImage.setFtrCde(ftrCde);
		kwsImage.setFtrIdn(ftrIdn);
		model.addAttribute("rows", imageService.listAllImage(kwsImage));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOnePhotoManage
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{imageNo}/select.do", method=RequestMethod.GET)
	public String selectOnePhotoManage(@PathVariable("imageNo") Long imageNo, Model model) {
		model.addAttribute("data", imageService.selectOneImage(imageNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removePhotoManage
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param imageDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{imageNo}/remove.do", method=RequestMethod.GET)
	public String removePhotoManage(@PathVariable("imageNo") Long imageNo, ImageDTO imageDTO, Model model) {
		imageDTO.setImageNo(imageNo);
		model.addAttribute("data", imageService.removeImage(imageDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addPhotoManage
	/// @brief 함수 간략한 설명 : 등록 
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
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addPhotoManage(ImageDTO imageDTO, Model model, MultipartRequest multipartRequest) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		imageDTO.setWrterId(userDTO.getUserId());
		imageDTO.setUpdusrId(userDTO.getUserId());
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				model.addAttribute("data", imageService.addImage(imageDTO, multipartFile, 270, 210));
			}
		}
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyPhotoManage
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
	public String modifyPhotoManage(ImageDTO imageDTO, Model model, MultipartRequest multipartRequest) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		imageDTO.setUpdusrId(userDTO.getUserId());
		
		MultipartFile multipartFile = null;
		if(multipartRequest.getFileMap().size() > 0) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
			}
		}
		model.addAttribute("data", imageService.modifyImage(imageDTO, multipartFile, 270, 210));
		return "jsonViewTextPlain";
	}
	
}