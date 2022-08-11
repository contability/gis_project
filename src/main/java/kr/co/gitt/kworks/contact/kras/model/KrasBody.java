package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/////////////////////////////////////////////
/// @class KrasBody
/// kworks.itf.vo \n
///   ㄴ KrasBody.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 4:59:01 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 부동산종합공부시스템 연계 응답 BODY 정보 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class KrasBody {
	
	/// 부동산종합공부시스템 서비스 응답 정보
	private Object data;

	@XmlElements({
		@XmlElement(name="LAND_INFO", type=LandInfo.class),		/// 토지(임야) 대장
		@XmlElement(name="LAND_MOV_HIST_SET", type=LandMovHistSet.class),	///토지이동연혁
		@XmlElement(name="JIGA_LIST", type=JigaList.class),		/// 개별공시지가확인서
		@XmlElement(name="LAND_USE_PLAN_CNF_INFO_SET", type=LandUsePlanCnfInfoSet.class),	/// 토지이용계획확인서
		@XmlElement(name="BLDG_DONG_LIST", type=BldgDongList.class),						/// 건물 동 목록
		@XmlElement(name="BLDG_LEDG_GEN_HDS_INFO", type=BldgLedgGenHdsInfo.class),			/// 건축물대장(총괄 표제부)
		@XmlElement(name="BLDG_HDS_INFO_LIST", type=BldgHdsInfoList.class)					/// 일반건축물 or 집합건축물(표제부)
	})
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
