package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsExportFile
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportFile.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 6. 오전 10:50:51 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 파일 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class KwsExportFile {
	
	/// 내보내기 번호
	private Long exportNo;
	
	/// 파일 번호
	private Long fileNo;
	
	// 파일 순서
	private Integer fileOrdr;
	
	/// 파일
	private KwsFile kwsFile;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
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
