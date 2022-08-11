package kr.co.gitt.kworks.service.authorGroup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsAuthorGroupMapper;
import kr.co.gitt.kworks.mappers.KwsBaseMapAuthorMapper;
import kr.co.gitt.kworks.mappers.KwsDataAuthorMapper;
import kr.co.gitt.kworks.mappers.KwsSysAuthorMapper;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsBaseMapAuthor;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsSysAuthor;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class AuthorGroupServiceImpl
/// kr.co.gitt.kworks.service.authorGroup \n
///   ㄴ AuthorGroupServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 25. 오후 3:11:47 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 권한 그룹 관리 서비스 구현 클래스입니다.
///   -# 
/////////////////////////////////////////////
@Service("authorGroupService")
public class AuthorGroupServiceImpl implements AuthorGroupService {

	/// 권한 그룹 관리 맵퍼
	@Resource
	KwsAuthorGroupMapper kwsAuthorGroupMapper;
	
	/// 데이터 권한 관리 맵퍼
	@Resource
	KwsDataAuthorMapper kwsDataAuthorMapper;
	
	/// 데이터 권한 관리 맵퍼
	@Resource
	KwsSysAuthorMapper kwsSysAuthorMapper;
	
	@Resource
	KwsBaseMapAuthorMapper kwsBaseMapAuthorMapper;
	
	/// 권한 그룹 관리 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsAuthorGroupIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.authorGroup.AuthorGroupService#listAuthorGroup(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsAuthorGroup> listAuthorGroup(SearchDTO searchDTO) {
		return kwsAuthorGroupMapper.list(searchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.authorGroup.AuthorGroupService#listAuthorGroup(kr.co.gitt.kworks.cmmn.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsAuthorGroup> listAuthorGroup(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsAuthorGroupMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listAuthorGroup(searchDTO);
		}
		else {
			return new ArrayList<KwsAuthorGroup>();
		}
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.authorGroup.AuthorGroupService#selectOneAuthorGroup(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsAuthorGroup selectOneAuthorGroup(Long authorGroupNo) {
		return kwsAuthorGroupMapper.selectOne(authorGroupNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.authorGroup.AuthorGroupService#addAuthorGroup(kr.co.gitt.kworks.model.KwsAuthorGroup)
	/////////////////////////////////////////////
	@Override
	public Integer addAuthorGroup(KwsAuthorGroup kwsAuthorGroup) throws FdlException {
		Long authorGroupNo = kwsAuthorGroupIdGnrService.getNextLongId();
		kwsAuthorGroup.setAuthorGroupNo(authorGroupNo);
		
		// 권한 그룹 관리 등록
		Integer rowCount = kwsAuthorGroupMapper.insert(kwsAuthorGroup);
		
		// 시스템 권한 등록
		addSysAuthors(kwsAuthorGroup);
		
		// 데이터 권한 등록
		addDataAuthors(kwsAuthorGroup);
		
		// 기본지도 권한 등록
		addBaseMapAuthors(kwsAuthorGroup);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.authorGroup.AuthorGroupService#modifyAuthorGroup(kr.co.gitt.kworks.model.KwsAuthorGroup)
	/////////////////////////////////////////////
	@Override
	public Integer modifyAuthorGroup(KwsAuthorGroup kwsAuthorGroup) throws FdlException {
		// 권한 관리 번호
		Long authorGroupNo = kwsAuthorGroup.getAuthorGroupNo();
		
		// 권한 그룹 정보 수정
		Integer rowCount = kwsAuthorGroupMapper.update(kwsAuthorGroup);
		
		// 서비스 접근 권한 정보 삭제 후 등록
		removeSysAuthors(authorGroupNo);
		addSysAuthors(kwsAuthorGroup);
		
		// 데이터 접근 권한 정보 삭제 후 등록
		removeDataAuthors(authorGroupNo);
		addDataAuthors(kwsAuthorGroup);
		
		// 기본지도 권한 정보 삭제 후 등록
		removeBaseMapAuthors(authorGroupNo);
		addBaseMapAuthors(kwsAuthorGroup);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.authorGroup.AuthorGroupService#removeAuthorGroup(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeAuthorGroup(Long authorGroupNo) {
		// 시스템 권한 삭제
		removeSysAuthors(authorGroupNo);
		// 데이터 권한 삭제
		removeDataAuthors(authorGroupNo);
		// 기본지도 권한 정보 삭제
		removeBaseMapAuthors(authorGroupNo);
		return kwsAuthorGroupMapper.delete(authorGroupNo);
	}
	
	/////////////////////////////////////////////
	/// @fn addSysAuthors
	/// @brief 함수 간략한 설명 : 시스템 권한 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAuthorGroup
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addSysAuthors(KwsAuthorGroup kwsAuthorGroup) throws FdlException {
		Long authorGroupNo = kwsAuthorGroup.getAuthorGroupNo();
		List<KwsSysAuthor> kwsSysAuthors = kwsAuthorGroup.getKwsSysAuthors();
		for(KwsSysAuthor kwsSysAuthor : kwsSysAuthors) {
			kwsSysAuthor.setAuthorGroupNo(authorGroupNo);
			kwsSysAuthorMapper.insert(kwsSysAuthor);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addDataAuthors
	/// @brief 함수 간략한 설명 : 데이터 권한 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAuthorGroup
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addDataAuthors(KwsAuthorGroup kwsAuthorGroup) throws FdlException {
		Long authorGroupNo = kwsAuthorGroup.getAuthorGroupNo();
		List<KwsDataAuthor> kwsDataAuthors = kwsAuthorGroup.getKwsDataAuthors();
		for(KwsDataAuthor kwsDataAuthor : kwsDataAuthors) {
			kwsDataAuthor.setAuthorGroupNo(authorGroupNo);
			kwsDataAuthorMapper.insert(kwsDataAuthor);
		}
	}
	
	protected void addBaseMapAuthors(KwsAuthorGroup kwsAuthorGroup) throws FdlException {
		Long authorGroupNo = kwsAuthorGroup.getAuthorGroupNo();
		List<KwsBaseMapAuthor> kwsBaseMapAuthors = kwsAuthorGroup.getKwsBaseMapAuthors();
		for(KwsBaseMapAuthor kwsBaseMapAuthor : kwsBaseMapAuthors) {
			kwsBaseMapAuthor.setAuthorGroupNo(authorGroupNo);
			kwsBaseMapAuthorMapper.insert(kwsBaseMapAuthor);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn removeSysAuthors
	/// @brief 함수 간략한 설명 : 시스템 권한 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param authorGroupNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void removeSysAuthors(Long authorGroupNo) {
		KwsSysAuthor kwsSysAuthor = new KwsSysAuthor();
		kwsSysAuthor.setAuthorGroupNo(authorGroupNo);
		kwsSysAuthorMapper.delete(authorGroupNo);
	}
	
	/////////////////////////////////////////////
	/// @fn removeDataAuthors
	/// @brief 함수 간략한 설명 : 데이터 권한 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param authorGroupNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void removeDataAuthors(Long authorGroupNo) {
		kwsDataAuthorMapper.delete(authorGroupNo);
	}
	
	protected void removeBaseMapAuthors(Long authorGroupNo) {
		kwsBaseMapAuthorMapper.delete(authorGroupNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.authorGroup.AuthorGroupService#listAllAuthorGroup()
	/////////////////////////////////////////////
	@Override
	public List<KwsAuthorGroup> listAllAuthorGroup(){
		return kwsAuthorGroupMapper.listAll();
	}
	
}
