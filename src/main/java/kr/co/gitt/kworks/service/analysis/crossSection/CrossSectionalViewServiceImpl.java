package kr.co.gitt.kworks.service.analysis.crossSection;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsCrsScnvewMapper;
import kr.co.gitt.kworks.model.KwsCrsScnvew;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class CrossSectionalViewServiceImpl
/// kr.co.gitt.kworks.service.analysis.crossSection \n
///   ㄴ CrossSectionalViewServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 1. 오전 11:24:35 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 횡단면도 서비스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("crossSectionalViewService")
public class CrossSectionalViewServiceImpl implements CrossSectionalViewService {

	/// 횡단면도 맵퍼
	@Resource
	KwsCrsScnvewMapper kwsCrsScnvewMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.analysis.crossSection.CrossSectionalViewService#listAllCrossSection()
	/////////////////////////////////////////////
	@Override
	public List<KwsCrsScnvew> listAllCrossSection() {
		return kwsCrsScnvewMapper.listAll();
	}

}
