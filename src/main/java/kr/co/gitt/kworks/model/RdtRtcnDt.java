package kr.co.gitt.kworks.model;

import java.util.List;

/////////////////////////////////////////////
/// @class RdtRtcnDt
/// kr.co.gitt.kworks.model \n
///   ㄴ RdtRtcnDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 12. 오후 3:42:02 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 노선 연결 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class RdtRtcnDt {

	/// 관리 번호
	private Long ftrIdn;
	
	/// 도로관리선 관리번호
	private Long secIdn;
	
	/// 도로관리선 목록
	private List<RdtAlcnDt> rdtAlcnDts; 

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public Long getSecIdn() {
		return secIdn;
	}

	public void setSecIdn(Long secIdn) {
		this.secIdn = secIdn;
	}

	public List<RdtAlcnDt> getRdtAlcnDts() {
		return rdtAlcnDts;
	}

	public void setRdtAlcnDts(List<RdtAlcnDt> rdtAlcnDts) {
		this.rdtAlcnDts = rdtAlcnDts;
	}
	
}
