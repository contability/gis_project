package kr.co.gitt.kworks.model;

import java.sql.Date;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class ChgAreaDt
/// kr.co.gitt.kworks.model \n
///   ㄴ ChgAreaDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | yun4810 |
///    | Date | 2018. 10. 5. 오후 2:40:44 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 변화 탐지 지역 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class ChgAreaDt extends SearchDTO {
	
	/// 변화 탐지 지역 번호
	private Long chngeAreaNo;
	
	/// 변화 탐지 내역 번호
	private Long chngeDetctNo;

	/// 위치
	private String chngeAreaLc;

	/// 설명
	private String chngeAreaDc;

	/// 비고/특이사항
	private String chngeAreaRm;

	/// 그리기
	private String geojson;

	/// 분면코드
	private String partitnCd;

	/// 작성자
	private String wrterId;
	
	/// 최초등록일
	private Date frstRgsde;
	
	/// 수정자
	private String updusrId; 
	
	/// 마지막 수정일
	private Date lastUpdde;
	
	// 1분면 지도
	private String partitnMap1;
	
	// 2분면 지도
	private String partitnMap2;
	
	// 3분면 지도
	private String partitnMap3;
	
	// 4분면 지도
	private String partitnMap4;

	public Long getChngeAreaNo() {
		return chngeAreaNo;
	}

	public void setChngeAreaNo(Long chngeAreaNo) {
		this.chngeAreaNo = chngeAreaNo;
	}

	public Long getChngeDetctNo() {
		return chngeDetctNo;
	}

	public void setChngeDetctNo(Long chngeDetctNo) {
		this.chngeDetctNo = chngeDetctNo;
	}

	public String getChngeAreaLc() {
		return chngeAreaLc;
	}

	public void setChngeAreaLc(String chngeAreaLc) {
		this.chngeAreaLc = chngeAreaLc;
	}

	public String getChngeAreaDc() {
		return chngeAreaDc;
	}

	public void setChngeAreaDc(String chngeAreaDc) {
		this.chngeAreaDc = chngeAreaDc;
	}

	public String getChngeAreaRm() {
		return chngeAreaRm;
	}

	public void setChngeAreaRm(String chngeAreaRm) {
		this.chngeAreaRm = chngeAreaRm;
	}

	public String getGeojson() {
		return geojson;
	}

	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}

	public String getPartitnCd() {
		return partitnCd;
	}

	public void setPartitnCd(String partitnCd) {
		this.partitnCd = partitnCd;
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

	public String getPartitnMap1() {
		return partitnMap1;
	}

	public void setPartitnMap1(String partitnMap1) {
		this.partitnMap1 = partitnMap1;
	}

	public String getPartitnMap2() {
		return partitnMap2;
	}

	public void setPartitnMap2(String partitnMap2) {
		this.partitnMap2 = partitnMap2;
	}

	public String getPartitnMap3() {
		return partitnMap3;
	}

	public void setPartitnMap3(String partitnMap3) {
		this.partitnMap3 = partitnMap3;
	}

	public String getPartitnMap4() {
		return partitnMap4;
	}

	public void setPartitnMap4(String partitnMap4) {
		this.partitnMap4 = partitnMap4;
	}
	
}
