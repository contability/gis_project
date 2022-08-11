package kr.co.gitt.kworks.model;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class KwsDomnCode
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsDomnCode.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 24. 오전 10:10:51 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 코드 모델 입니다.
///   -# 
/////////////////////////////////////////////

public class KwsDomnCode extends SearchDTO {
	
	/// 도메인 ID
	private String domnId = "";
	
	/// 코드 ID
	private String codeId = "";
	
	/// 코드명
	private String codeNm = "";
	
	/// 사용여부
	private String useAt = "";

	public String getDomnId() {
		return domnId;
	}

	public void setDomnId(String domnId) {
		this.domnId = domnId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeNm() {
		return codeNm;
	}

	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
}
