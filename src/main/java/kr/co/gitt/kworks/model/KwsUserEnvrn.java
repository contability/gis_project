package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsUserEnvrn
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsUserEnvrn.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 17. 오전 3:09:27 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 환경 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsUserEnvrn {
	
	/////////////////////////////////////////////
	/// @class LocationType
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsUserEnvrn.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 9. 17. 오후 4:15:57 |
	///    | Class Version | v1.0 |
	///    | 작업자 | libraleo, Others... |
	/// @section 상세설명
	/// - 이 클래스는 위치 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum LocationType {
		LT, RT, LB, RB, NONE
	}
	
	/////////////////////////////////////////////
	/// @class CoordinateIndicationType
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsUserEnvrn.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 9. 17. 오후 4:16:01 |
	///    | Class Version | v1.0 |
	///    | 작업자 | libraleo, Others... |
	/// @section 상세설명
	/// - 이 클래스는 좌표 표시 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum CoordinateIndicationType {
		TM, LONLAT, DMS
	}
	
	/// 사용자 아이디
	private String userId;
	
	/// 지도 툴 위치
	private LocationType mapToolLc;
	
	/// 인덱스 윈도우 위치
	private LocationType indexWindowLc;
	
	/// 인덱스 윈도우 사이즈
	private Integer indexWindowSize;
	
	/// 축척 바 위치
	private LocationType scBarLc;
	
	/// 중심 점 타입
	private CoordinateIndicationType centerPointTy;
	
	// 중심 점 위치
	private LocationType centerPointLc;
	
	/// 줌 렌즈 사이즈
	private Integer zoomLensSize;
	
	/// X-Ray 렌즈 사이즈
	private Integer xrayLensSize;
	
	/// 주제도 아이디
	private Long themamapId;
	
	/// 초기 중심 X
	private Double initCenterX;
	
	/// 초기 중심 Y
	private Double initCenterY;
	
	/// 초기 축척
	private Integer initSc;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocationType getMapToolLc() {
		return mapToolLc;
	}

	public void setMapToolLc(LocationType mapToolLc) {
		this.mapToolLc = mapToolLc;
	}

	public LocationType getIndexWindowLc() {
		return indexWindowLc;
	}

	public void setIndexWindowLc(LocationType indexWindowLc) {
		this.indexWindowLc = indexWindowLc;
	}

	public Integer getIndexWindowSize() {
		return indexWindowSize;
	}

	public void setIndexWindowSize(Integer indexWindowSize) {
		this.indexWindowSize = indexWindowSize;
	}

	public LocationType getScBarLc() {
		return scBarLc;
	}

	public void setScBarLc(LocationType scBarLc) {
		this.scBarLc = scBarLc;
	}

	public CoordinateIndicationType getCenterPointTy() {
		return centerPointTy;
	}

	public void setCenterPointTy(CoordinateIndicationType centerPointTy) {
		this.centerPointTy = centerPointTy;
	}

	public LocationType getCenterPointLc() {
		return centerPointLc;
	}

	public void setCenterPointLc(LocationType centerPointLc) {
		this.centerPointLc = centerPointLc;
	}

	public Integer getZoomLensSize() {
		return zoomLensSize;
	}

	public void setZoomLensSize(Integer zoomLensSize) {
		this.zoomLensSize = zoomLensSize;
	}

	public Integer getXrayLensSize() {
		return xrayLensSize;
	}

	public void setXrayLensSize(Integer xrayLensSize) {
		this.xrayLensSize = xrayLensSize;
	}

	public Long getThemamapId() {
		return themamapId;
	}

	public void setThemamapId(Long themamapId) {
		this.themamapId = themamapId;
	}

	public Double getInitCenterX() {
		return initCenterX;
	}

	public void setInitCenterX(Double initCenterX) {
		this.initCenterX = initCenterX;
	}

	public Double getInitCenterY() {
		return initCenterY;
	}

	public void setInitCenterY(Double initCenterY) {
		this.initCenterY = initCenterY;
	}

	public Integer getInitSc() {
		return initSc;
	}

	public void setInitSc(Integer initSc) {
		this.initSc = initSc;
	}

}
