package kr.co.gitt.kworks.projects.gn.dto;

import kr.co.gitt.kworks.projects.gn.model.SwtSpmtAtchmnflDt;

/////////////////////////////////////////////
/// @class DrngEqpCnfmPrmisnAtchmnflDTO
/// kr.co.gitt.kworks.projects.gn.dto \n
///   ㄴ DrngEqpCnfmPrmisnAtchmnflDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | 이승재 |
///    | Date | 2019. 12. 17. |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 첨부파일 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class DrngEqpCnfmPrmisnAtchmnflDTO extends SwtSpmtAtchmnflDt {
	private Integer rownum;
	
	private String orignlFileNm;
	
	private String fileExtsn;

	public Integer getRownum() {
		return rownum;
	}

	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}

	public String getOrignlFileNm() {
		return orignlFileNm;
	}

	public void setOrignlFileNm(String orignlFileNm) {
		this.orignlFileNm = orignlFileNm;
	}

	public String getFileExtsn() {
		return fileExtsn;
	}

	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}
}
