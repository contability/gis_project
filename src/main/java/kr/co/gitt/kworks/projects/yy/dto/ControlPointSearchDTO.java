package kr.co.gitt.kworks.projects.yy.dto;

import java.util.List;

/**
 * 측량기준점 통합검색 DTO
 * @author kwangsu.lee
 *
 */
public class ControlPointSearchDTO {

	/**
	 * 데이터 목록
	 */
	private List<String> dataIds;
	
	/**
	 * 기준점명
	 */
	private String bseNam;
	
	/**
	 * 설치일자 - 시작
	 */
	private String strYmd;

	/**
	 * 설치일자 - 종료
	 */
	private String endYmd;

	/**
	 * 토지소재지
	 */
	private String setLoc;

	
	public List<String> getDataIds() {
		return dataIds;
	}

	public void setDataIds(List<String> dataIds) {
		this.dataIds = dataIds;
	}

	public String getBseNam() {
		return bseNam;
	}

	public void setBseNam(String bseNam) {
		this.bseNam = bseNam;
	}

	public String getStrYmd() {
		return strYmd;
	}

	public void setStrYmd(String strYmd) {
		this.strYmd = strYmd;
	}

	public String getEndYmd() {
		return endYmd;
	}

	public void setEndYmd(String endYmd) {
		this.endYmd = endYmd;
	}

	public String getSetLoc() {
		return setLoc;
	}

	public void setSetLoc(String setLoc) {
		this.setLoc = setLoc;
	}
	
}
