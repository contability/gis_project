package kr.co.gitt.kworks.projects.gn.dto;

/////////////////////////////////////////////
/// @class CityParkChangeDetailsResultDTO
/// kr.co.gitt.kworks.projects.gn.dto \n
///   ㄴ CityParkChangeDetailsResultDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 26. 오후 6:20:34 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 
///   -# 도시공원 변천내역 결과 DTO 입니다.
/////////////////////////////////////////////
public class CityParkChangeDetailsResultDTO {

	/// 공원명
	private String parkName;
	
	/// 위치
	private String location;
	
	/// 면적
	private Double area;
	
	/// 고시번호
	private String notificationNo;
	
	/// 고시일자
	private String notificationDate;
	
	/// 고시구분
	private String notificationType;
	
	/// 비고
	private String remark;

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getNotificationNo() {
		return notificationNo;
	}

	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}

	public String getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
