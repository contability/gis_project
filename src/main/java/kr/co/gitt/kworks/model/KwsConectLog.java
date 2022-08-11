package kr.co.gitt.kworks.model;

import java.util.Date;

import kr.co.gitt.kworks.cmmn.dto.DayOfWeek;

/////////////////////////////////////////////
/// @class KwsConectLog
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsConectLog.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 2:52:00 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 접속 로그 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsConectLog {
	
	/// 로그 번호
	private Long logNo;
	
	// 사용자 ID
	private String userId;
	
	// 사용자 NM
	private String userNm;
	
	// 부서 CODE
	private String deptCode;
	
	// 사용자IP
	private String userIp;
	
	// 로그인날짜
	private Date loginDt;
	
	// 로그아웃 날짜
	private Date logoutDt;
	
	// 세션ID
	private String sessionId;

	// 시간 
	private String hour;
	
	// 카운터
	private Integer cnt;

	// 날짜
	private String day;
	
	// 요일
	private String week;
	
	/// 요일
	private DayOfWeek dayOfWeek;
	
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

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Date getLoginDt() {
		return loginDt;
	}

	public void setLoginDt(Date loginDt) {
		this.loginDt = loginDt;
	}

	public Date getLogoutDt() {
		return logoutDt;
	}

	public void setLogoutDt(Date logoutDt) {
		this.logoutDt = logoutDt;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
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

}
