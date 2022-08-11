package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsCntm
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsCntm.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 11. 오후 3:52:51 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 좌표계 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsCntm {

	/// 좌표계 아이디
	private String cntmId;
	
	/// 좌표계 명
	private String cntmNm;
	
	/// 좌표계 내용
	private String cntmCn;
	
	/// 좌표계 비고
	private String cntmRm;

	public String getCntmId() {
		return cntmId;
	}

	public void setCntmId(String cntmId) {
		this.cntmId = cntmId;
	}

	public String getCntmNm() {
		return cntmNm;
	}

	public void setCntmNm(String cntmNm) {
		this.cntmNm = cntmNm;
	}

	public String getCntmCn() {
		return cntmCn;
	}

	public void setCntmCn(String cntmCn) {
		this.cntmCn = cntmCn;
	}

	public String getCntmRm() {
		return cntmRm;
	}

	public void setCntmRm(String cntmRm) {
		this.cntmRm = cntmRm;
	}
	
}
