package kr.co.gitt.kworks.cmmn.dto.topographic;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 지형데이터 조회 결과
 * @author kwangsu.lee
 *
 */
public class TopographicSearchResultDTO {

	/**
	 *  데이터 아이디
	 */
	private String dataId;

	/**
	 *  데이터 별칭
	 */
	private String dataAlias;
	
	/**
	 * 결과
	 */
	private EgovMap data;

	
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

	public EgovMap getData() {
		return data;
	}

	public void setData(EgovMap data) {
		this.data = data;
	}
	
}