package kr.co.gitt.kworks.cmmn.dto;

import java.util.Date;

import kr.co.gitt.kworks.model.KwsImage.ImageTy;

/////////////////////////////////////////////
/// @class ImageDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ ImageDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 7. 오후 5:38:53 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 이미지 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class ImageDTO {
	
	/////////////////////////////////////////////
	/// @class ImageType
	/// kr.co.gitt.kworks.cmmn.dto \n
	///   ㄴ ImageDTO.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 11. 8. 오후 2:55:00 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 이미지 타입 입니다. (원본 이미지, 썸네일 이미지, 출력용 이미지)
	///   -# 
	/////////////////////////////////////////////
	public enum ImageType {
		IMAGE, THUMBNAIL, OUTPUT
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
	
	/// 위치 X
	private Double lcX;
	
	/// 위치 Y
	private Double lcY;
	
	/// 작성자 아이디
	private String wrterId;
	
	/// 수정자 아이디
	private String updusrId;
	
	/// 최초 작성일
	private Date frstRgsde;
	
	/// 수정자 아이디
	private Date lastUpdde;
	
	/// 이미지 파일 번호
	private Long imageFileNo;
	
	/// 썸네일 파일 번호
	private Long thumbFileNo;
	
	/// 내려받기 파일 번호
	private Long outptFileNo;
	
	//  비고
	private String rmkExp;

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

	public String getUpdusrId() {
		return updusrId;
	}

	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
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

	public Date getFrstRgsde() {
		return frstRgsde;
	}

	public void setFrstRgsde(Date frstRgsde) {
		this.frstRgsde = frstRgsde;
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
}
