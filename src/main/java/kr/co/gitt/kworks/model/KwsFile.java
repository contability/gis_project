package kr.co.gitt.kworks.model;

import java.io.File;

/////////////////////////////////////////////
/// @class KwsFile
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsFile.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 1. 오전 10:16:41 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 파일 모델입니다.
///   -# 
/////////////////////////////////////////////
public class KwsFile {
	
	/// 파일 번호
	private Long fileNo;

	/// 파일 저장 경로
	private String fileStreCours;
	
	/// 저장 파일 명
	private String streFileNm;
	
	/// 원본 파일명
	private String orignlFileNm;
	
	/// 파일 확장자
	private String fileExtsn;
	
	/// 파일 크기
	private Long fileSize;

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	public String getFileStreCours() {
		return fileStreCours;
	}

	public void setFileStreCours(String fileStreCours) {
		this.fileStreCours = fileStreCours;
	}
	
	public void setFileStreCours(String uploadPath, String folderName) {
		this.fileStreCours = uploadPath + folderName + File.separator;
	}

	public String getStreFileNm() {
		return streFileNm;
	}

	public void setStreFileNm(String streFileNm) {
		this.streFileNm = streFileNm;
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

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

}
