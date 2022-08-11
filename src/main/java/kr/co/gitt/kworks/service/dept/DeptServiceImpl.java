package kr.co.gitt.kworks.service.dept;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsDeptMapper;
import kr.co.gitt.kworks.model.KwsDept;

import org.springframework.stereotype.Service;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class DeptServiceImpl
/// kr.co.gitt.kworks.service.dept \n
///   ㄴ DeptServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 16. 오후 4:11:19 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 부서 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("deptService")
public class DeptServiceImpl implements DeptService{
	
	/// 부서 맵퍼
	@Resource
	KwsDeptMapper kwsDeptMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.dept.DeptService#listDept(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsDept> listDept(SearchDTO searchDTO){
		return kwsDeptMapper.list(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// - 페이징 없이 현재 사용되고 있는 부서 목록 검색 
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @작업자
	/// - 이승재, 2020.12.07
	/// @see kr.co.gitt.kworks.service.dept.DeptService#listDept()
	@Override
	public List<KwsDept> listDept(String sortField) {
		return kwsDeptMapper.listNoPaging(sortField);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.dept.DeptService#listDept(kr.co.gitt.kworks.cmmn.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsDept> listDept(SearchDTO searchDTO, PaginationInfo paginationInfo){
		Integer totalRecordCount = kwsDeptMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listDept(searchDTO);
		}
		else {
			return new ArrayList<KwsDept>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.dept.DeptService#listAllDept()
	/////////////////////////////////////////////
	@Override
	public List<KwsDept> listAllDept(){
		return kwsDeptMapper.listAll();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.dept.DeptService#selectOneDept(java.lang.String)
	/////////////////////////////////////////////
	public KwsDept selectOneDept(String deptCode){
		return kwsDeptMapper.selectOne(deptCode);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.dept.DeptService#addDept(kr.co.gitt.kworks.model.KwsDept)
	/////////////////////////////////////////////
	public Integer addDept(KwsDept KwsDept){
		Integer rowCount = kwsDeptMapper.insert(KwsDept);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.dept.DeptService#modifyDept(kr.co.gitt.kworks.model.KwsDept)
	/////////////////////////////////////////////
	public Integer modifyDept(KwsDept KwsDept){
		Integer rowCount = kwsDeptMapper.update(KwsDept);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.dept.DeptService#removeDept(java.lang.String)
	/////////////////////////////////////////////
	public Integer removeDept(String deptCode){
		return kwsDeptMapper.delete(deptCode);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.dept.DeptService#listDeptExcel(kr.co.gitt.kworks.dto.UserSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsDept> listDeptExcel(SearchDTO searchDTO) {
		return kwsDeptMapper.listDeptExcel(searchDTO);
	}
}
