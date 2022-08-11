package kr.co.gitt.kworks.cmmn.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import kr.co.gitt.kworks.model.KwsConectLog;
import kr.co.gitt.kworks.service.log.ConnectLogService;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

/////////////////////////////////////////////
/// @class SessionListner
/// kworks.co.web \n
///   ㄴ SessionListner.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 2. 3. 오후 2:52:51 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 세션 리스너 입니다. 세션 종료 시 로그아웃 시간을 기록합니다.
///   -# 
/////////////////////////////////////////////
public class SessionListner implements HttpSessionListener {
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	/////////////////////////////////////////////
	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	/////////////////////////////////////////////
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		/// 접속 로그 수정 (접속 종료)
		HttpSession session = event.getSession();
		KwsConectLog kwsConectLog = new KwsConectLog();
		kwsConectLog.setSessionId(session.getId());
		ConnectLogService connectLogService = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext(), FrameworkServlet.SERVLET_CONTEXT_PREFIX+"action").getBean("connectLogService", ConnectLogService.class);
		connectLogService.logout(kwsConectLog);
	}

}
