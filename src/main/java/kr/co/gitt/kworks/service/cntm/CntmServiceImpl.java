package kr.co.gitt.kworks.service.cntm;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsCntmMapper;
import kr.co.gitt.kworks.model.KwsCntm;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class CntmServiceImpl
/// kr.co.gitt.kworks.service.cntm \n
///   ㄴ CntmServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 11. 오후 3:58:07 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 좌표계 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("cntmService")
public class CntmServiceImpl implements CntmService {
	
	/// 좌표계 맵퍼
	@Resource
	KwsCntmMapper kwsCntmMapper;

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cntm.CntmService#listAllCntm()
	/////////////////////////////////////////////
	@Override
	public List<KwsCntm> listAllCntm() {
		return kwsCntmMapper.listAll();
	}

}
