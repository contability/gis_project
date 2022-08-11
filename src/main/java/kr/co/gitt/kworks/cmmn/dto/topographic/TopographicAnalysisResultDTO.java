package kr.co.gitt.kworks.cmmn.dto.topographic;

import java.util.List;

/**
 * 지형단면 분석 결과
 * @author kwangsu.lee
 *
 */
public class TopographicAnalysisResultDTO {

	/**
	 *  데이터 아이디
	 */
	private String dataId;

	/**
	 *  데이터 별칭
	 */
	private String dataAlias;
	
	/**
	 * 최소값
	 */
	private Double min;
	
	/**
	 * 최대값
	 */
	private Double max;
	
	/**
	 * 좌표계
	 */
	private String srs;
	
	/**
	 * 표고 데이터
	 */
	private List<ElevationDTO> datas;

	
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDataAlias() {
		return dataAlias;
	}

	public void setDataAlias(String dataAlias) {
		this.dataAlias = dataAlias;
	}
	
	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}
	
	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}
	
	public List<ElevationDTO> getDatas() {
		return datas;
	}
	
	public void setDatas(List<ElevationDTO> datas) {
		this.datas = datas;
	}
}
