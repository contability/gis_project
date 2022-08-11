package kr.co.gitt.kworks.projects.gn.model;

/////////////////////////////////////////////
/// @class CtyRegisterInquiry
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ CtyRegisterInquiry.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 2. 26. 오후 12:30:27 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 대장조회 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class CtyRegisterInquiry {
	
	/// OBJECTID
	private String objectid;
	
	/// 공원관리번호
	private Long ftrIdn;
	
	/// 공원구역관리번호
	private Long ftfIdn;
	
	/// 운동시설종류
	private String athCde;
	
	/// 유희시설종류
	private String plyCde;
	
	/// 휴게시설종류
	private String rstCde;
	
		
	public String getObjectid() {
		return objectid;
	}

	public void setObjectId(String objectid) {
		this.objectid = objectid;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}


	public Long getFtfIdn() {
		return ftfIdn;
	}

	public void setFtfIdn(Long ftfIdn) {
		this.ftfIdn = ftfIdn;
	}

	public String getAthCde() {
		return athCde;
	}

	public void setAthCde(String athCde) {
		this.athCde = athCde;
	}

	public String getPlyCde() {
		return plyCde;
	}

	public void setPlyCde(String plyCde) {
		this.plyCde = plyCde;
	}

	public String getRstCde() {
		return rstCde;
	}

	public void setRstCde(String rstCde) {
		this.rstCde = rstCde;
	}
	
}
