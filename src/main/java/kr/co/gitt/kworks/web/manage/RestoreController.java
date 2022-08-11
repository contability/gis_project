package kr.co.gitt.kworks.web.manage;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.restore.RestoreService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class RestoreController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ RestoreController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 19. 오후 4:58:28 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 복원 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/restore/")
public class RestoreController {

	/// 복원 서비스 
	@Resource
	RestoreService restoreService;
	
	/////////////////////////////////////////////
	/// @fn listBackupData
	/// @brief 함수 간략한 설명 : 연계 데이터 전체 목록
	/// @remark
	/// - 함수의 상세 설명 : 
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
	public String listBackupData(Model model) {
		model.addAttribute("rows", restoreService.listData());
		return "/manage/restore/listRestoreData";
	}
	
	/////////////////////////////////////////////
	/// @fn listBackupData
	/// @brief 함수 간략한 설명 : 백업 데이터 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAllBackupData.do", method=RequestMethod.GET)
	public String listBackupData(String dataId, Model model) {
		model.addAttribute("rows", restoreService.listAllBackupData(dataId));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn restore
	/// @brief 함수 간략한 설명 : 복원
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param backupId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="restore.do", method=RequestMethod.POST)
	public String restore(Long backupId, Model model) {
		model.addAttribute("rowCount", restoreService.restore(backupId));
		return "jsonView";
	}
	
}
