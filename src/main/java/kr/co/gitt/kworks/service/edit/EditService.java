package kr.co.gitt.kworks.service.edit;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.DataCtgrySearchDTO;
import kr.co.gitt.kworks.model.KwsEdit;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class EditService
/// kr.co.gitt.kworks.service.edit \n
///   ㄴ EditService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdAhn |
///    | Date | 2017. 12. 20. 오후 6:17:16 |
///    | Class Version | v1.0 |
///    | 작업자 | jdAhn, Others... |
/// @section 상세설명
/// - 이 클래스는 편집여부 관리 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface EditService {
	
	/////////////////////////////////////////////
	/// @fn listEdit
	/// @brief 함수 간략한 설명 : 편집여부 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsEdit> listEdit(DataCtgrySearchDTO dataCtgrySearchDTO);
	
	/////////////////////////////////////////////
	/// @fn listEdit
	/// @brief 함수 간략한 설명 : 편집여부 목록 검색(페이징 포함)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsEdit> listEdit(DataCtgrySearchDTO dataCtgrySearchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn selectOneEdit
	/// @brief 함수 간략한 설명 : 편집여부 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsEdit selectOneEdit(String dataId);
	
	/////////////////////////////////////////////
	/// @fn modifyEdit
	/// @brief 함수 간략한 설명 : 편집여부 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsEdit
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyEdit(KwsEdit kwsEdit);

}
