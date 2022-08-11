package kr.co.gitt.kworks.service.domn;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsDomnMapper;
import kr.co.gitt.kworks.model.KwsDomn;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class DomnServiceImpl
/// kr.co.gitt.kworks.service.domn \n
///   ㄴ DomnServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 23. 오후 4:11:19 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("domnService")
public class DomnServiceImpl implements DomnService{
	
	/// 부서 맵퍼
	@Resource
	KwsDomnMapper kwsDomnMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnService#listDomn(kr.co.gitt.kworks.model.KwsDomn)
	/////////////////////////////////////////////
	@Override
	public List<KwsDomn> listDomn(SearchDTO searchDTO){
		return kwsDomnMapper.list(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnService#selectOneDomn(java.lang.String)
	/////////////////////////////////////////////
	public KwsDomn selectOneDomn(String domnId){
		return kwsDomnMapper.selectOne(domnId);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnService#addDomn(kr.co.gitt.kworks.model.KwsDomn)
	/////////////////////////////////////////////
	public Integer addDomn(KwsDomn kwsDomn){
		Integer rowCount = kwsDomnMapper.insert(kwsDomn);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnService#modifyDomn(kr.co.gitt.kworks.model.KwsDomn)
	/////////////////////////////////////////////
	public Integer modifyDomn(KwsDomn KwsDomn){
		Integer rowCount = kwsDomnMapper.update(KwsDomn);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.domn.DomnService#removeDomn(java.lang.String)
	/////////////////////////////////////////////
	public Integer removeDomn(String domnId){
		return  kwsDomnMapper.delete(domnId);
	}
}
