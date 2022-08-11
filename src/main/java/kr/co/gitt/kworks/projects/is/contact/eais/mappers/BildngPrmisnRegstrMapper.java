package kr.co.gitt.kworks.projects.is.contact.eais.mappers;

import java.util.List;

import kr.co.gitt.kworks.contact.eais.model.BildngPrmisnRegstr;
import kr.co.gitt.kworks.contact.eais.model.DjrBldRgst;
import kr.co.gitt.kworks.contact.eais.model.DjrChangItem;
import kr.co.gitt.kworks.contact.eais.model.DjrFlrouln;
import kr.co.gitt.kworks.contact.eais.model.DjrTitle;



/// @class BildngPrmisnRegstrMapper
/// kr.co.gitt.kworks.cntc.eais.mappers \n
///   ㄴ BildngPrmisnRegstrMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 1. 2. 오후 2:54:39 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 건축허가대장 맵퍼 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public interface BildngPrmisnRegstrMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bildngPrmisnRegstr
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<BildngPrmisnRegstr> list(BildngPrmisnRegstr bildngPrmisnRegstr);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pmsrgstPk
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public BildngPrmisnRegstr selectOne(BildngPrmisnRegstr bildngPrmisnRegstr);
}
