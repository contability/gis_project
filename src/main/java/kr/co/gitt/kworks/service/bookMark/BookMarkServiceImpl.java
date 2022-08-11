package kr.co.gitt.kworks.service.bookMark;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsUserBkmkMapper;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.model.KwsUserBkmk;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class BookMarkServiceImpl
/// kr.co.gitt.kworks.service.bookMark \n
///   ㄴ BookMarkServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오후 5:34:15 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 북마크 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("bookMarkService")
public class BookMarkServiceImpl implements BookMarkService {

	/// 북마트 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsUserBkmkIdGnrService;
	
	/// 사용자 북마크 맵퍼 
	@Resource
	KwsUserBkmkMapper kwsUserBkmkMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.bookMark.BookMarkService#listAllBookMark(kr.co.gitt.kworks.model.KwsUserBkmk)
	/////////////////////////////////////////////
	@Override
	public List<KwsUserBkmk> listAllBookMark(KwsUserBkmk kwsUserBkmk) {
		return kwsUserBkmkMapper.listAll(kwsUserBkmk);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.bookMark.BookMarkService#selectOneUserBkmk(kr.co.gitt.kworks.model.KwsUserBkmk)
	/////////////////////////////////////////////
	public KwsUserBkmk selectOneUserBkmk(KwsUserBkmk kwsUserBkmk){
		return kwsUserBkmkMapper.selectOne(kwsUserBkmk);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.bookMark.BookMarkService#addBookMark(kr.co.gitt.kworks.model.KwsUserBkmk)
	/////////////////////////////////////////////
	@Override
	public Integer addBookMark(KwsUserBkmk kwsUserBkmk) throws FdlException {
		Long bkmkNo = kwsUserBkmkIdGnrService.getNextLongId();
		kwsUserBkmk.setBkmkNo(bkmkNo);
		return kwsUserBkmkMapper.insert(kwsUserBkmk);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.bookMark.BookMarkService#removeBookMark(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeBookMark(Long bkmkNo) {
		return kwsUserBkmkMapper.delete(bkmkNo);
	}
	
}
