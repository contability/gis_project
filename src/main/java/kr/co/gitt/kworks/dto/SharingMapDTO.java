package kr.co.gitt.kworks.dto;

import kr.co.gitt.kworks.model.CmtCnrsMap;

public class SharingMapDTO extends CmtCnrsMap {
	
	//이미지번호
	private Long imageNo;
	
	//이미지파일번호
	private Long imageFileNo;
	
	//썸네일파일번호
	private Long thumbFileNo;
	
	//출력대장용파일번호
	private Long outptFileNo;
	
	/// 파일 타입
	private String fileExtsn;
	
	public Long getImageNo() {
		return imageNo;
	}

	public void setImageNo(Long imageNo) {
		this.imageNo = imageNo;
	}
	
	public void setImageFileNo(Long imageFileNo) {
		this.imageFileNo = imageFileNo;
	}
	
	public Long getImageFileNo() {
		return imageFileNo;
	}
	
	public void setThumbFileNo(Long thumbFileNo) {
		this.thumbFileNo = thumbFileNo;
	}
	
	public Long getThumbFileNo() {
		return thumbFileNo;
	}
	
	public void setOutptFileNo(Long outptFileNo) {
		this.outptFileNo = outptFileNo;
	}
	
	public Long getOutptFileNo() {
		return outptFileNo;
	}
	
	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}
	
	public String getFileExtsn() {
		return fileExtsn;
	}
}
