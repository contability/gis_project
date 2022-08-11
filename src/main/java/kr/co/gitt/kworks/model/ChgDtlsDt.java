package kr.co.gitt.kworks.model;

import java.sql.Date;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class ChgDtlsDt
/// kr.co.gitt.kworks.model \n
///   ㄴ ChgDtlsDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | yun4810 |
///    | Date | 2018. 10. 1. 오후 12:28:47 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 변화 탐지 내역 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class ChgDtlsDt extends SearchDTO{
	
	/// 변화 탐지 내역 번호
	private Long chngeDetctNo;
	
	/// 작성년도
	private String wrterYear;
	
	/// 제목
	private String chngeDetctSj;
	
	/// 개요
	private String chngeDetctSy;
	
	/// 작성자
	private String wrterId;
	
	/// 최초등록일
	private Date frstRgsde;
	
	/// 수정자
	private String updusrId; 
	
	/// 마지막 수정일
	private Date lastUpdde;

	public Long getChngeDetctNo() {
		return chngeDetctNo;
	}

	public void setChngeDetctNo(Long chngeDetctNo) {
		this.chngeDetctNo = chngeDetctNo;
	}

	public String getWrterYear() {
		return wrterYear;
	}

	public void setWrterYear(String wrterYear) {
		this.wrterYear = wrterYear;
	}

	public String getChngeDetctSj() {
		return chngeDetctSj;
	}

	public void setChngeDetctSj(String chngeDetctSj) {
		this.chngeDetctSj = chngeDetctSj;
	}

	public String getChngeDetctSy() {
		return chngeDetctSy;
	}

	public void setChngeDetctSy(String chngeDetctSy) {
		this.chngeDetctSy = chngeDetctSy;
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
