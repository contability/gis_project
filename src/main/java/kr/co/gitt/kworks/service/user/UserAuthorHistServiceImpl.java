package kr.co.gitt.kworks.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsUserAuthorHistMapper;
import kr.co.gitt.kworks.model.KwsUserAuthorHist;

import org.springframework.stereotype.Service;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class UserAuthorHistServiceImpl
/// kr.co.gitt.kworks.service.user \n
///   ㄴ UserAuthorHistServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 10. 오전 11:37:57 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 권한부여 이력 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("userAuthorHistService")
public class UserAuthorHistServiceImpl implements UserAuthorHistService {
	
	/// 사용자 권한부여 이력 관리 맵퍼
	@Resource
	KwsUserAuthorHistMapper kwsUserAuthorHistMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserAuthorHistService#listUserAuthorHist(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsUserAuthorHist> listUserAuthorHist(SearchDTO searchDTO) {
		return kwsUserAuthorHistMapper.list(searchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserAuthorHistService#listUserAuthorHist(kr.co.gitt.kworks.cmmn.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsUserAuthorHist> listUserAuthorHist(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsUserAuthorHistMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listUserAuthorHist(searchDTO);
		}
		else {
			return new ArrayList<KwsUserAuthorHist>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserAuthorHistService#addUserAuthorHist(kr.co.gitt.kworks.model.KwsUserAuthorHist)
	/////////////////////////////////////////////
	@Override
	public Integer addUserAuthorHist(KwsUserAuthorHist kwsUserAuthorHist) {
		return kwsUserAuthorHistMapper.insert(kwsUserAuthorHist);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserAuthorHistService#listUserAuthorHistExcel(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsUserAuthorHist> listUserAuthorHistExcel(SearchDTO searchDTO) {
		return kwsUserAuthorHistMapper.listUserAuthorHistExcel(searchDTO);
	}

}
