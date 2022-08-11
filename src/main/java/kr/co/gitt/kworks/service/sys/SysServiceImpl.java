package kr.co.gitt.kworks.service.sys;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsSysMapper;
import kr.co.gitt.kworks.model.KwsSys;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class SysServiceImpl
/// kr.co.gitt.kworks.service.sys \n
///   ㄴ SysServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 9. 오후 4:15:11 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 시스템 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("sysService")
public class SysServiceImpl implements SysService {
	
	/// 시스템 맵퍼
	@Resource
	KwsSysMapper kwsSysMapper;

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.sys.SysService#listAllSys()
	/////////////////////////////////////////////
	@Override
	public List<KwsSys> listAllSys(KwsSys kwsSys) {
		return kwsSysMapper.listAll(kwsSys);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.sys.SysService#selectOneSys(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsSys selectOneSys(Long sysId) {
		return kwsSysMapper.selectOne(sysId);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.sys.SysService#modifyBassThemamap(kr.co.gitt.kworks.model.KwsSys)
	/////////////////////////////////////////////
	@Override
	public Integer modifyBassThemamap(KwsSys kwsSys) throws FdlException {
		
		Integer rowCount = kwsSysMapper.updateBassThemamap(kwsSys);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.sys.SysService#listSys()
	/////////////////////////////////////////////
	@Override
	public List<KwsSys> listSys(){
		return kwsSysMapper.listSys();
	}
	
}
