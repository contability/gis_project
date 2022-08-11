package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsUserBkmk
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsUserBkmk.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 23. 오후 3:04:31 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 북마크 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsUserBkmk {

	/// 북마크 번호
	private Long bkmkNo;
	
	/// 사용자 아이디
	private String userId;
	
	/// 북마크 명
	private String bkmkNm;
	
	/// 중심 X
	private Double centerX;
	
	/// 중심 Y
	private Double centerY;
	
	/// 축척
	private Long sc;

	public Long getBkmkNo() {
		return bkmkNo;
	}

	public void setBkmkNo(Long bkmkNo) {
		this.bkmkNo = bkmkNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBkmkNm() {
		return bkmkNm;
	}

	public void setBkmkNm(String bkmkNm) {
		this.bkmkNm = bkmkNm;
	}

	public Double getCenterX() {
		return centerX;
	}

	public void setCenterX(Double centerX) {
		this.centerX = centerX;
	}

	public Double getCenterY() {
		return centerY;
	}

	public void setCenterY(Double centerY) {
		this.centerY = centerY;
	}

	public Long getSc() {
		return sc;
	}

	public void setSc(Long sc) {
		this.sc = sc;
	}
	
}
