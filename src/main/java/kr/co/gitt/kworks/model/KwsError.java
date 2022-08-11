package kr.co.gitt.kworks.model;

import java.util.Date;

/////////////////////////////////////////////
/// @class KwsError
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsError.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 9. 오후 4:53:16 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 에러 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsError {
	
	/// 에러 번호
	private Long errorNo;
	
	/// 에러 메세지
	private String errorMssage;
	
	/// 에러 추척
	private String errorTrace;
	
	/// 에러 발생 일자
	private Date errorDt;

	public Long getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(Long errorNo) {
		this.errorNo = errorNo;
	}

	public String getErrorMssage() {
		return errorMssage;
	}

	public void setErrorMssage(String errorMssage) {
		this.errorMssage = errorMssage;
	}

	public String getErrorTrace() {
		return errorTrace;
	}

	public void setErrorTrace(String errorTrace) {
		this.errorTrace = errorTrace;
	}
	
	public Date getErrorDt() {
		return errorDt;
	}

	public void setErrorDt(Date errorDt) {
		this.errorDt = errorDt;
	}
}
