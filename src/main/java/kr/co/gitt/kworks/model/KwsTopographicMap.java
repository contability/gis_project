package kr.co.gitt.kworks.model;



/**
 * 지형지도 모델
 * @author kwangsu.lee
 *
 */
public class KwsTopographicMap {

	/**
	 * 데이터 타입
	 * @author kwangsu.lee
	 *
	 */
	public enum DataType {
		DEM("표고"),
		SLOPE("경사도"),
		ASPECT("향");
		
		// 데이터 타입
		private String value;
		
		private DataType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}		
	}
	
	/**
	 * 관리 번호
	 */
	private Long seqNo;
	
	/**
	 * 그룹
	 */
	private String groupName;
	
	/**
	 * 데이터 타입
	 */
	private DataType dataType;
	
	/**
	 * 제목
	 */
	private String title;
	
	/**
	 * 레이어 명칭
	 */
	private String layerName;
	
	/**
	 * 설명
	 */
	private String description;
	
	/**
	 * 좌표계
	 */
	private String srs;

	/**
	 * 최소값
	 */
	private Double minValue;

	/**
	 * 최대값
	 */
	private Double maxValue;
	
	/**
	 * 요청 포맷
	 */
	private String requestFormat;
	
	/**
	 * 제작년도 
	 */
	private String makeYear;
	
	/**
	 * 제작기관
	 */
	private String makeInstitution;
	
	/**
	 * 아이콘 경로
	 */
	private String iconPath;
	
	
	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public String getRequestFormat() {
		return requestFormat;
	}

	public void setRequestFormat(String requestFormat) {
		this.requestFormat = requestFormat;
	}

	public String getMakeYear() {
		return makeYear;
	}

	public void setMakeYear(String makeYear) {
		this.makeYear = makeYear;
	}

	public String getMakeInstitution() {
		return makeInstitution;
	}

	public void setMakeInstitution(String makeInstitution) {
		this.makeInstitution = makeInstitution;
	}
	
	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
}
