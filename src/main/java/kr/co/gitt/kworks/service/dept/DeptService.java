package kr.co.gitt.kworks.service.dept;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsDept;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class DeptService
/// kr.co.gitt.kworks.service.dept \n
///   ㄴ DeptService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 16. 오후 4:14:40 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 부서 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface DeptService {
	
	/////////////////////////////////////////////
	/// @fn listDept
	/// @brief 함수 간략한 설명 : 부서 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDept> listDept(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listDept
	/// @brief 함수 간략한 설명 : 부서 목록 검색(페이징 없음)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param 
	/// @return List<KwsDept>
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/// @작업자
	/// - 이승재, 2020.12.07
	/////////////////////////////////////////////
	public List<KwsDept> listDept(String sortField);
	
	/////////////////////////////////////////////
	/// @fn listDept
	/// @brief 함수 간략한 설명 : 부서 목록 검색 (페이징 포함)
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
	public List<KwsDept> listDept(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn selectOneDept
	/// @brief 함수 간략한 설명 : 부서 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param deptCode
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsDept selectOneDept(String deptCode);
	
	/////////////////////////////////////////////
	/// @fn addDept
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsDept
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addDept(KwsDept KwsDept);
	
	/////////////////////////////////////////////
	/// @fn modifyDept
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param KwsDept
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyDept(KwsDept KwsDept);
	
	/////////////////////////////////////////////
	/// @fn removeDept
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param deptNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeDept(String deptNo);
	
	/////////////////////////////////////////////
	/// @fn listAlldept
	/// @brief 함수 간략한 설명 :부서 리스트 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDept> listAllDept();
	
	/////////////////////////////////////////////
	/// @fn listDeptExcel
	/// @brief 함수 간략한 설명 : 엑셀 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsDept> listDeptExcel(SearchDTO searchDTO);
	
}
