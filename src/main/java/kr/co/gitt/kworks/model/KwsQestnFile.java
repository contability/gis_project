package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsQestnFile
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsQestnFile.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 10. 오후 12:12:01 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 묻고답하기 질문게시판 파일 모델입니다. 
///   -# 
/////////////////////////////////////////////
public class KwsQestnFile {
	
	/// 게시물 번호
	private Long qestnNo;
	
	/// 파일 번호
	private Long fileNo;
	
	/// 파일 순서
	private Integer fileOrdr;
	
	/// 파일
	private KwsFile kwsFile;

	public Long getQestnNo() {
		return qestnNo;
	}

	public void setQestnNo(Long qestnNo) {
		this.qestnNo = qestnNo;
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
