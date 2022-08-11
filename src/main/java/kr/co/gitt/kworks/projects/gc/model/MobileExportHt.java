package kr.co.gitt.kworks.projects.gc.model;

import java.util.Date;

/**
 * 재난현장 모바일 내보내기 이력
 * @author kwangsu.lee
 *
 */
public class MobileExportHt {

	/**
	 * 내보내기 번호
	 */
	private Long extNo;
	
	/**
	 * 사용자 아이디
	 */
	private String extUser;
	
	/**
	 * 사용자 명
	 */
	private String extName;
	
	/**
	 * 내보내기 일자
	 */
	private Date extDate;

	
	public Long getExtNo() {
		return extNo;
	}

	public void setExtNo(Long extNo) {
		this.extNo = extNo;
	}

	public String getExtUser() {
		return extUser;
	}

	public void setExtUser(String extUser) {
		this.extUser = extUser;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public Date getExtDate() {
		return extDate;
	}

	public void setExtDate(Date extDate) {
		this.extDate = extDate;
	}
}
