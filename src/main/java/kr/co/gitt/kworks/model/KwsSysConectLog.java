package kr.co.gitt.kworks.model;

import java.util.Date;

import kr.co.gitt.kworks.cmmn.dto.DayOfWeek;

/////////////////////////////////////////////
/// @class KwsSysConectLog
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsSysConectLog.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | nam |
///    | Date | 2016. 9. 5. 오후 4:40:04 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 모델 입니다.
///   -# 
/////////////////////////////////////////////

public class KwsSysConectLog {
	
	/// 로그 번호
	private Long logNo;
	
	/// 유저 ID
	private String userId;
	
	/// 유저 이름
	private String userNm;
	
	/// 부서코드
	private String deptCode;
	
	/// 부서이름
	private String deptNm;
	
	/// 시스템 ID
	private Long sysId;
	
	/// 시스템 이름
	private String sysNm;
	
	/// 시스템 별칭
	private String sysAlias;

	/// 접속일자
	private Date conectDt;
	
	/// 접속요일
	private DayOfWeek dayOfWeek;
	
	/// 기준월
	private String month;
	
	/// 기준일
	private String day;

	/// 카운트
	private Integer cnt;

	/// 시간 
	private String hour;
	
	/// 요일
	private String week;

	public Long getLogNo() {
		return logNo;
	}

	public void setLogNo(Long logNo) {
		this.logNo = logNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	
	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getSysNm() {
		return sysNm;
	}

	public void setSysNm(String sysNm) {
		this.sysNm = sysNm;
	}
	
	public String getSysAlias() {
		return sysAlias;
	}

	public void setSysAlias(String sysAlias) {
		this.sysAlias = sysAlias;
	}

	public Date getConectDt() {
		return conectDt;
	}

	public void setConectDt(Date conectDt) {
		this.conectDt = conectDt;
	}
	
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	
	public void setDayOfWeek(int dayOfWeek) {
		switch(dayOfWeek) {
			case 1 :
				this.dayOfWeek = DayOfWeek.SUNDAY;
				break;
			case 2 :
				this.dayOfWeek = DayOfWeek.MONDAY;
				break;
			case 3 :
				this.dayOfWeek = DayOfWeek.TUESDAY;
				break;
			case 4 :
				this.dayOfWeek = DayOfWeek.WEDNESDAY;
				break;
			case 5 :
				this.dayOfWeek = DayOfWeek.THURSDAY;
				break;
			case 6 :
				this.dayOfWeek = DayOfWeek.FRIDAY;
				break;
			case 7 :
				this.dayOfWeek = DayOfWeek.SATURDAY;
				break;
		}
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
}
