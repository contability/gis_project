package kr.co.gitt.kworks.model;

import java.sql.Date;

/////////////////////////////////////////////
/// @class KwsImage
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsImage.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 7. 오후 12:13:43 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 이미지 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsImage {
	
	/////////////////////////////////////////////
	/// @class ImageTy
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsImage.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 11. 21. 오후 2:53:32 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 이미지 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum ImageTy {
		
		// 측량기준점관리서비스 도시기준점 사전 분류
		OUTLINE_MAP("약도"),
		CLOSE_RANGE_VIEW("근경"),
		DISTANT_VIEW("원경"),
		VISIBILITY_MAP("시통도"),
		
		// 그 외
		TEMP_VIEW("기타"),
		GPS_OBSERVATION_MAP("GPS 관측도"),
		ABANDONED_WELL("폐공"),
		OBSERVATION_MAP("관측도"),
		CONSTRUCTION_PHOTO("시공사진"),
		ROAD_CNTRWK_PHOTO("도로공사사진"),
		WRPP_CNTRWK_PHOTO("상수공사사진"),
		WSP_CNTRWK_PHOTO("급수공사사진"),
		SWGE_CNTRWK_PHOTO("하수공사사진"),
		LWL_CNTRWK_PHOTO("침출수공사사진"),
		WIDTH_SECTIONAL_VIEW("횡단면도"),
		CHANGE_DETECTION_AREA_PHOTO("변화탐지지역사진"),
		OCPAT_PHOTO("점용허가 자료관리사진"),
		POLICY_PHOTO("정책지도 자료관리사진"),
		SURVEY_BASE_POINT("측량기준점사진"),
		CITY_PLAN_ROAD("도시계획도로"),
		EXPLANT_PHOTO("외래식물현지조사사진"),
		REPLANT_PHOTO("외래식물제거작업사진"),
		FIRST_CONVERSION("1차변환"),
		SECOND_CONVERSION("2차변환"),
		INTERPRETATION("영상판독"),
		BASEMAP("항공사진"),
		LAND_REGISTERMAP("지적도");
		
		/// 이미지 타입
		private String value;
		
		/////////////////////////////////////////////
		/// @fn 
		/// @brief 생성자 간략 설명 : 생성자
		/// @remark
		/// - 생성자 상세 설명 : 
		/// @param value 
		///~~~~~~~~~~~~~{.java}
		/// // 핵심코드
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		private ImageTy(String value) {
			this.value = value;
		}
		
		/////////////////////////////////////////////
		/// @fn getValue
		/// @brief 함수 간략한 설명 : 이미지 타입 반환
		/// @remark
		/// - 함수의 상세 설명 : 
		/// @return 
		///
		///~~~~~~~~~~~~~{.java}
		/// // 핵심코드
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		public String getValue() {
			return value;
		}
	}

	/// 이미지 번호
	private Long imageNo;
	
	/// 이미지 제목
	private String imageSj;
	
	/// 이미지 내용
	private String imageCn;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 이미지 타입
	private ImageTy imageTy;
	
	/// 이미지 파일 번호
	private Long imageFileNo;
	
	/// 썸네일 파일 번호
	private Long thumbFileNo;
	
	/// 출력 파일 번호
	private Long outptFileNo;
	
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
	
	//  비고
	private String rmkExp;
	
	/// 이미지 파일
	private KwsFile imageFile;
	
	/// 썸네일 파일
	private KwsFile thumbFile;
	
	/// 출력 파일
	private KwsFile outptFile;

	/// 파일 타입
	private String fileExtsn;

	public Long getImageNo() {
		return imageNo;
	}

	public void setImageNo(Long imageNo) {
		this.imageNo = imageNo;
	}

	public String getImageSj() {
		return imageSj;
	}

	public void setImageSj(String imageSj) {
		this.imageSj = imageSj;
	}

	public String getImageCn() {
		return imageCn;
	}

	public void setImageCn(String imageCn) {
		this.imageCn = imageCn;
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

	public ImageTy getImageTy() {
		return imageTy;
	}

	public void setImageTy(ImageTy imageTy) {
		this.imageTy = imageTy;
	}

	public Long getImageFileNo() {
		return imageFileNo;
	}

	public void setImageFileNo(Long imageFileNo) {
		this.imageFileNo = imageFileNo;
	}

	public Long getThumbFileNo() {
		return thumbFileNo;
	}

	public void setThumbFileNo(Long thumbFileNo) {
		this.thumbFileNo = thumbFileNo;
	}

	public Long getOutptFileNo() {
		return outptFileNo;
	}

	public void setOutptFileNo(Long outptFileNo) {
		this.outptFileNo = outptFileNo;
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
	
	public String getRmkExp() {
		return rmkExp;
	}
	
	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}

	public KwsFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(KwsFile imageFile) {
		this.imageFile = imageFile;
	}

	public KwsFile getThumbFile() {
		return thumbFile;
	}

	public void setThumbFile(KwsFile thumbFile) {
		this.thumbFile = thumbFile;
	}

	public KwsFile getOutptFile() {
		return outptFile;
	}

	public void setOutptFile(KwsFile outptFile) {
		this.outptFile = outptFile;
	}

	public String getFileExtsn() {
		return fileExtsn;
	}

	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}
}
