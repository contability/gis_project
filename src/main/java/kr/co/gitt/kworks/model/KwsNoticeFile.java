package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsNoticeFile
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsNoticeFile.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 1. 오전 10:16:32 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 공지사항 파일 모델입니다.
///   -# 
/////////////////////////////////////////////
public class KwsNoticeFile {
	
	/// 공지사항 번호
	private Long noticeNo;
	
	/// 파일 번호
	private Long fileNo;
	
	/// 파일 순서
	private Integer fileOrdr;
	
	/// 파일
	private KwsFile kwsFile;

	public Long getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(Long noticeNo) {
		this.noticeNo = noticeNo;
	}

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	public Integer getFileOrdr() {
		return fileOrdr;
	}

	public void setFileOrdr(Integer fileOrdr) {
		this.fileOrdr = fileOrdr;
	}

	public KwsFile getKwsFile() {
		return kwsFile;
	}

	public void setKwsFile(KwsFile kwsFile) {
		this.kwsFile = kwsFile;
	}

}
