package kr.co.gitt.kworks.projects.yg.model;

/////////////////////////////////////////////
/// @class LdDocMa
/// kr.co.gitt.kworks.projects.yg.model \n
///   ㄴ LdDocMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 4. 12. 오후 2:59:57 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 양구 토지사용증명서 모델 클레스입니다.
///   -# 
/////////////////////////////////////////////
public class LdDocMa extends LdUseMa {
	
	// 공사번호
	private Long cntIdn;
	
	// 토지사용정보 번호
	private Long luiIdn;
	
	// 증명서 코드
	private String docCde;
	
	// 파일명
	private String docFile;
	
	// 문서번호
	private Long docNo;
	
	// 파일번호
	private Long fileNo;
	
	// 주소
	private String addr;
	
	public Long getCntIdn() {
		return cntIdn;
	}

	public void setCntIdn(Long cntIdn) {
		this.cntIdn = cntIdn;
	}

	public Long getLuiIdn() {
		return luiIdn;
	}

	public void setLuiIdn(Long luiIdn) {
		this.luiIdn = luiIdn;
	}

	public String getDocCde() {
		return docCde;
	}

	public void setDocCde(String docCde) {
		this.docCde = docCde;
	}

	public String getDocFile() {
		return docFile;
	}

	public void setDocFile(String docFile) {
		this.docFile = docFile;
	}

	public Long getDocNo() {
		return docNo;
	}

	public void setDocNo(Long docNo) {
		this.docNo = docNo;
	}

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
}
