package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class RdtAlCnDt
/// kr.co.gitt.kworks.model \n
///   ㄴ RdtAlCnDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 12. 오후 3:35:47 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 구간 연결 모델입니다.
///   -# 
/////////////////////////////////////////////
public class RdtAlcnDt {

	/// 도로관리선 관리번호
	private Long secIdn;
	
	/// 도로구간 관리번호
	private Long rdlIdn;

	public Long getSecIdn() {
		return secIdn;
	}

	public void setSecIdn(Long secIdn) {
		this.secIdn = secIdn;
	}

	public Long getRdlIdn() {
		return rdlIdn;
	}

	public void setRdlIdn(Long rdlIdn) {
		this.rdlIdn = rdlIdn;
	}
	
}
