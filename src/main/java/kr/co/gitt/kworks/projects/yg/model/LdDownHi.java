package kr.co.gitt.kworks.projects.yg.model;

/////////////////////////////////////////////
/// @class LdDownHi
/// kr.co.gitt.kworks.projects.yg.model \n
///   ㄴ LdDownHi.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2019. 4. 18. 오후 5:35:50 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 양구 다운로드 이력정보 모델 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class LdDownHi {
	
	//이력번호
	private Long hisNo;
	
	//공사번호
	private Long cntIdn;
	
	//공사명
	private String cntNam;
	
	//토지사용정보 번호
	private Long luiIdn;
	
	//다운로드 일시
	private String dwYmd;
	
	//증명서 코드
	private String docCde;
	
	//부서 코드
	private String deptCde;
	
	//사용자 아이디
	private String userId;

	public Long getHisNo() {
		return hisNo;
	}

	public void setHisNo(Long hisNo) {
		this.hisNo = hisNo;
	}

	public Long getCntIdn() {
		return cntIdn;
	}

	public void setCntIdn(Long cntIdn) {
		this.cntIdn = cntIdn;
	}

	public String getCntNam() {
		return cntNam;
	}

	public void setCntNam(String cntNam) {
		this.cntNam = cntNam;
	}

	public Long getLuiIdn() {
		return luiIdn;
	}

	public void setLuiIdn(Long luiIdn) {
		this.luiIdn = luiIdn;
	}

	public String getDwYmd() {
		return dwYmd;
	}

	public void setDwYmd(String dwYmd) {
		this.dwYmd = dwYmd;
	}

	public String getDocCde() {
		return docCde;
	}

	public void setDocCde(String docCde) {
		this.docCde = docCde;
	}

	public String getDeptCde() {
		return deptCde;
	}

	public void setDeptCde(String deptCde) {
		this.deptCde = deptCde;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
