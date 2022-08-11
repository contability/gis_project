package kr.co.gitt.kworks.service.edit;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.DataCtgrySearchDTO;
import kr.co.gitt.kworks.mappers.KwsEditMapper;
import kr.co.gitt.kworks.model.KwsEdit;

import org.springframework.stereotype.Service;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class EditServiceImpl
/// kr.co.gitt.kworks.service.edit \n
///   ㄴ EditServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdAhn |
///    | Date | 2017. 12. 20. 오후 6:18:48 |
///    | Class Version | v1.0 |
///    | 작업자 | jdAhn, Others... |
/// @section 상세설명
/// - 이 클래스는  편집여부 관리 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("editService")
public class EditServiceImpl implements EditService {

	/// 편집여부 관리 맵퍼
	@Resource
	KwsEditMapper kwsEditMapper;
	

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.edit.EditService#listEdit(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsEdit> listEdit(DataCtgrySearchDTO dataCtgrySearchDTO) {
		System.out.println("list1" + dataCtgrySearchDTO);
		return kwsEditMapper.list(dataCtgrySearchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.edit.EditService#listEdit(kr.co.gitt.kworks.cmmn.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsEdit> listEdit(DataCtgrySearchDTO dataCtgrySearchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsEditMapper.listCount(dataCtgrySearchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			System.out.println("list2" + dataCtgrySearchDTO);
			return listEdit(dataCtgrySearchDTO);
		} else {
			return new ArrayList<KwsEdit>();
		}
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.edit.EditService#selectOneEdit(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsEdit selectOneEdit(String dataId) {
		return kwsEditMapper.selectOne(dataId);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.edit.EditService#modifyEdit(kr.co.gitt.kworks.model.KwsEdit)
	/////////////////////////////////////////////
	@Override
	public Integer modifyEdit(KwsEdit kwsEdit) {
		return kwsEditMapper.update(kwsEdit);
	}

}
