package kr.co.gitt.kworks.service.domnCode;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsDomnCodeMapper;
import kr.co.gitt.kworks.model.KwsDomnCode;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class DomnCodeServiceImpl
/// kr.co.gitt.kworks.service.domn \n
///   ㄴ DomnCodeServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 16. 오후 4:11:19 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 코드 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("domnCodeService")
public class DomnCodeServiceImpl implements DomnCodeService{
	
	/// 부서 맵퍼
	@Resource
	KwsDomnCodeMapper kwsDomnCodeMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnCodeService#listDomnCode(kr.co.gitt.kworks.model.KwsDomn)
	/////////////////////////////////////////////
	@Override
	public List<KwsDomnCode> listDomnCode(KwsDomnCode kwsDomnCode){		
		return kwsDomnCodeMapper.list(kwsDomnCode);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnCodeService#selectOneDomnCode(java.lang.String)
	/////////////////////////////////////////////
	public KwsDomnCode selectOneDomnCode(String domnId){
		return kwsDomnCodeMapper.selectOne(domnId);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnCodeService#selectCodeIdByName(java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	public KwsDomnCode selectCodeIdByName(String domnId, String codeNm){
		return kwsDomnCodeMapper.selectCodeIdByName(domnId, codeNm);
	}
		
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnCodeService#addDomnCode(kr.co.gitt.kworks.model.KwsDomnCode)
	/////////////////////////////////////////////
	public Integer addDomnCode(KwsDomnCode kwsDomnCode){
		Integer rowCount = kwsDomnCodeMapper.insert(kwsDomnCode);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnCodeService#modifyDomnCode(kr.co.gitt.kworks.model.KwsDomnCode)
	/////////////////////////////////////////////
	public Integer modifyDomnCode(KwsDomnCode KwsDomnCode){
		Integer rowCount = kwsDomnCodeMapper.update(KwsDomnCode);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnCodeService#removeDomnCode(java.lang.String)
	/////////////////////////////////////////////
	public Integer removeDomnCode(KwsDomnCode kwsDomnCode){
		return kwsDomnCodeMapper.delete(kwsDomnCode);
	}

	@Override
	public KwsDomnCode selectOneById(String domnId, String codeId) {
		return kwsDomnCodeMapper.selectOneById(domnId, codeId);
	}
}
