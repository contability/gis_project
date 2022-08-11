package kr.co.gitt.kworks.cmmn.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/////////////////////////////////////////////
/// @class RequestWrapperForSecurity
/// kr.co.gitt.kworks.cmmn.login \n
///   ㄴ RequestWrapperForSecurity.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:59:19 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 스프링 시큐리티 요청 랩퍼 입니다.
///   -# 
/////////////////////////////////////////////
public class RequestWrapperForSecurity extends HttpServletRequestWrapper {

	/// 사용자 아이디
	private String userId = null;
	
	/// 비밀번호
	private String password = null;
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief 생성자 간략 설명 : 스프링시큐리티 랩퍼 생성자
	/// @remark
	/// - 생성자 상세 설명 : 
	/// @param request
	/// @param userId 
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RequestWrapperForSecurity(HttpServletRequest request, String userId, String password) {
		super(request);
		this.userId = userId;
		this.password = password;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see javax.servlet.http.HttpServletRequestWrapper#getRequestURI()
	/////////////////////////////////////////////
	@Override
	public String getRequestURI() {
		return ((HttpServletRequest)super.getRequest()).getContextPath() + "/j_spring_security_check";
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String getParameter(String name) {
        if (name.equals("j_username")) {
        	return userId;
        }

        if (name.equals("j_password")) {
        	return password;
        }

        return super.getParameter(name);
    }

}
