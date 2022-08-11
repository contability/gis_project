package kr.co.gitt.kworks.model;

import java.util.List;

/////////////////////////////////////////////
/// @class RdtRoutDt
/// kr.co.gitt.kworks.model \n
///   ㄴ RdtRoutDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 12. 오후 3:40:59 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 노선 대장 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class RdtRoutDt {

	/// 지형 지물 부호
	private String ftrCde;
	
	/// 관리 번호
	private Long ftrIdn;
	
	/// 노선 명
	private String rutNam;
	
	/// 노선 연결 목록
	private List<RdtRtcnDt> rdtRtcnDts;

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getRutNam() {
		return rutNam;
	}

	public void setRutNam(String rutNam) {
		this.rutNam = rutNam;
	}

	public List<RdtRtcnDt> getRdtRtcnDts() {
		return rdtRtcnDts;
	}

	public void setRdtRtcnDts(List<RdtRtcnDt> rdtRtcnDts) {
		this.rdtRtcnDts = rdtRtcnDts;
	}
	
}
