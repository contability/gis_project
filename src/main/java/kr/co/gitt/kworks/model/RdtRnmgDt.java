package kr.co.gitt.kworks.model;

import java.util.List;

/////////////////////////////////////////////
/// @class RdtRnmgDt
/// kr.co.gitt.kworks.model \n
///   ㄴ RdtRnmgDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 12. 오후 3:37:55 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 도로명 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class RdtRnmgDt {

	/// 도로명
	private String rdnNam;
	
	/// 도로명 코드
	private String rdnCde;
	
	/// 도로명 연결 목록
	private List<RdlCtlrLs> rdlCtlrLss;

	public String getRdnNam() {
		return rdnNam;
	}

	public void setRdnNam(String rdnNam) {
		this.rdnNam = rdnNam;
	}

	public String getRdnCde() {
		return rdnCde;
	}

	public void setRdnCde(String rdnCde) {
		this.rdnCde = rdnCde;
	}

	public List<RdlCtlrLs> getRdlCtlrLss() {
		return rdlCtlrLss;
	}

	public void setRdlCtlrLss(List<RdlCtlrLs> rdlCtlrLss) {
		this.rdlCtlrLss = rdlCtlrLss;
	}
	
}
