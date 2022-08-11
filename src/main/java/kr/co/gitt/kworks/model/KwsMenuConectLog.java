package kr.co.gitt.kworks.model;

import java.util.Date;

import kr.co.gitt.kworks.cmmn.dto.DayOfWeek;

/////////////////////////////////////////////
/// @class KwsMenuConectLog
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsMenuConectLog.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 21. 오전 9:33:47 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 메뉴 접속 로그 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsMenuConectLog {
	
	/// 로그 번호
	private Long logNo;

	/// 사용자 아이디
	private String userId;
	
	/// 사용자 이름
	private String userNm;
	
	/// 부서 코드
	private String deptCode;
	
	/// 부서 명
	private String deptNm;
	
	/// 메뉴 아이디
	private Long menuId;
	
	/// 메뉴 명
	private String menuNm;
	
	/// 접속 일시
	private Date conectDt;

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
	
	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	
	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
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
	
}
