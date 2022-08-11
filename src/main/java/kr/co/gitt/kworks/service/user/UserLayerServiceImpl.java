package kr.co.gitt.kworks.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.gitt.kworks.mappers.KwsUserLyrMapper;
import kr.co.gitt.kworks.model.KwsUserLyr;

/////////////////////////////////////////////
/// @class UserLayerServiceImpl
/// kr.co.gitt.kworks.service.user \n
///   ㄴ UserLayerServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 18. 오후 12:07:21 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 
///   -# 
/////////////////////////////////////////////
@Service("userLayerService")
public class UserLayerServiceImpl implements UserLayerService {
	
	/// 사용자 레이어 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsUserLyrIdGnrService;
	
	/// 사용자 레이어 매퍼
	@Resource
	KwsUserLyrMapper kwsUserLyrMapper;

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserLayerService#listAllUserLayer(kr.co.gitt.kworks.model.KwsUserLyr)
	/////////////////////////////////////////////
	@Override
	public List<KwsUserLyr> listAllUserLayer(KwsUserLyr kwsUserLyr) {
		return kwsUserLyrMapper.listAll(kwsUserLyr);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserLayerService#addUserLayer(kr.co.gitt.kworks.model.KwsUserLyr)
	/////////////////////////////////////////////
	@Override
	public Integer addUserLayer(KwsUserLyr kwsUserLyr) throws FdlException {
		kwsUserLyr.setLyrNo(kwsUserLyrIdGnrService.getNextLongId());
		return kwsUserLyrMapper.insert(kwsUserLyr);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserLayerService#removeUserLayer(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeUserLayer(Long lyrNo) {
		return kwsUserLyrMapper.delete(lyrNo);
	}

}
