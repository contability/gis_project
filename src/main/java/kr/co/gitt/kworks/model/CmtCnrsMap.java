package kr.co.gitt.kworks.model;

import java.sql.Date;

/////////////////////////////////////////////
/// @class CmtCnrsMap
/// kr.co.gitt.kworks.model \n
///   ㄴ CmtCnrsMap.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2019. 05. 15. |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 공유지도 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class CmtCnrsMap {
	
	/// POI 제목
	private String poiSj;
		
	/// POI 내용
	private String poiCn;
		
	/// 지형지물부호
	private String ftrCde;
		
	/// 관리번호
	private Long ftrIdn;
		
	/// 위치 X
	private Double lcX;
		
	/// 위치 Y
	private Double lcY;
		
	/// 작성자 아이디
	private String wrterId;
		
	/// 최초 등록 일
	private Date frstRgsde;
		
	/// 수정자 아이디
	private String updusrId;
		
	/// 최종수정일
	private Date lastUpdde;
		
	public String getPoiSj() {
		return poiSj;
	}

	public void setPoiSj(String poiSj) {
		this.poiSj = poiSj;
	}

	public String getPoiCn() {
		return poiCn;
	}

	public void setPoiCn(String poiCn) {
		this.poiCn = poiCn;
	}

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

	public Double getLcX() {
		return lcX;
	}

	public void setLcX(Double lcX) {
		this.lcX = lcX;
	}

	public Double getLcY() {
		return lcY;
	}

	public void setLcY(Double lcY) {
		this.lcY = lcY;
	}

	public String getWrterId() {
		return wrterId;
	}

	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}

	public Date getFrstRgsde() {
		return frstRgsde;
	}

	public void setFrstRgsde(Date frstRgsde) {
		this.frstRgsde = frstRgsde;
	}

	public String getUpdusrId() {
		return updusrId;
	}

	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
	}

	public Date getLastUpdde() {
		return lastUpdde;
	}

	public void setLastUpdde(Date lastUpdde) {
		this.lastUpdde = lastUpdde;
	}

}
