package kr.co.gitt.kworks.projects.gc.dto;

import java.util.Date;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/**
 * 재난현장 모바일 내보내기 이력 검색
 * @author kwangsu.lee
 *
 */
public class MobileExportSearchDTO extends SearchDTO {

	/**
	 * 내보내기 일자
	 */
	private Date extDate;

	
	public Date getExtDate() {
		return extDate;
	}

	public void setExtDate(Date extDate) {
		this.extDate = extDate;
	}
}
