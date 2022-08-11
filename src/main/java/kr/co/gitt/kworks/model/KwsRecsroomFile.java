package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsRecsroomFile
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsRecsroomFile.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 5. 오후 3:35:25 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 자료실 파일 모델입니다. 
///   -# 
/////////////////////////////////////////////
public class KwsRecsroomFile {
	
	/// 자료실 번호
	private Long recsroomNo;
	
	/// 파일 번호
	private Long fileNo;
	
	/// 파일 순서
	private Integer fileOrdr;
	
	/// 파일
	private KwsFile kwsFile;

	
	public Long getRecsroomNo() {
		return recsroomNo;
	}

	public void setRecsroomNo(Long recsroomNo) {
		this.recsroomNo = recsroomNo;
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
