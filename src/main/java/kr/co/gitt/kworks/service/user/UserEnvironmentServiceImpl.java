package kr.co.gitt.kworks.service.user;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsUserEnvrnMapper;
import kr.co.gitt.kworks.model.KwsUserEnvrn;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class UserEnvironmentServiceImpl
/// kr.co.gitt.kworks.service.user \n
///   ㄴ UserEnvironmentServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오후 5:58:58 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 환경 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("userEnvironmentService")
public class UserEnvironmentServiceImpl implements UserEnvironmentService {

	/// 사용자 환경 맵퍼
	@Resource
	KwsUserEnvrnMapper kwsUserEnvrnMapper;

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserEnvironmentService#selectOneUserEnvironment(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsUserEnvrn selectOneUserEnvironment(String userId) {
		return kwsUserEnvrnMapper.selectOne(userId);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserEnvironmentService#persistUserEnvironment(kr.co.gitt.kworks.model.KwsUserEnvrn)
	/////////////////////////////////////////////
	@Override
	public Integer persistUserEnvironment(KwsUserEnvrn selectUserEnvrn, KwsUserEnvrn kwsUserEnvrn) {
		Integer rowCount;
		if(selectUserEnvrn == null) {
			rowCount = kwsUserEnvrnMapper.insert(kwsUserEnvrn);
		}
		else {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.map(kwsUserEnvrn, selectUserEnvrn);
			rowCount = kwsUserEnvrnMapper.update(selectUserEnvrn);
		}
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.user.UserEnvironmentService#removeUserEnvironment(kr.co.gitt.kworks.model.KwsUserEnvrn)
	/////////////////////////////////////////////
	public Integer removeUserEnvironment(KwsUserEnvrn kwsUserEnvrn){
		return kwsUserEnvrnMapper.delete(kwsUserEnvrn.getUserId());
	}

}
