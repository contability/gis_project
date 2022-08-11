package kr.co.gitt.kworks.projects.yg.model;

/////////////////////////////////////////////
/// @class LdConsAtchmnfl
/// kr.co.gitt.kworks.projects.yg.model \n
///   ㄴ LdConsAtchmnfl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2019. 4. 18. 오후 5:40:37 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 양구 토지공사대장 첨부파일 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class LdConsAtchmnfl {

	//파일 번호
	private Long fileNo;
	
	//공사 번호
	private Long cntIdn;
	
	//증명서 코드
	private String docCde;
	
	//저장 파일명
	private String streFileNm;
	
	//원본 파일명
	private String orignlFileNm;
	
	//파일 확장자
	private String fileExtsn;
	
	//등록자 아이디
	private String registerId;
	
	//자료등록일
	private String lodYmd;

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	public Long getCntIdn() {
		return cntIdn;
	}

	public void setCntIdn(Long cntIdn) {
		this.cntIdn = cntIdn;
	}

	public String getDocCde() {
		return docCde;
	}

	public void setDocCde(String docCde) {
		this.docCde = docCde;
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

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getLodYmd() {
		return lodYmd;
	}

	public void setLodYmd(String lodYmd) {
		this.lodYmd = lodYmd;
	}
	
}
