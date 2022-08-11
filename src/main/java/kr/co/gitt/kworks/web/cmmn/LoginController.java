package kr.co.gitt.kworks.web.cmmn;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.login.RequestWrapperForSecurity;
import kr.co.gitt.kworks.cmmn.util.MessageUtils;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.model.KwsUser.UserGrad;
import kr.co.gitt.kworks.model.KwsUser.UserType;
import kr.co.gitt.kworks.service.user.UserService;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;

/////////////////////////////////////////////
/// @class LoginController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ LoginController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | libraleo |
///    | Date | 2016. 9. 14. 오후 12:42:56 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, sjlee, Others... |
/// @section 상세설명
/// - 이 클래스는 로그인 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
public class LoginController {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 사용자 서비스
	@Resource
	UserService userService;
	
	/// 메세지 소스
	@Resource(name="messageSource")
	private MessageSource messageSource;

	/////////////////////////////////////////////
	/// @fn login
	/// @brief 함수 간략한 설명 : 로그인 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/login.do")
	public String login(Model model) {
		return "cmmn/login";
	}
	
	/////////////////////////////////////////////
	/// @fn getUserType
	/// @brief 함수 간략한 설명 : 사용자 종류 확인
	/// @remark
	/// - 이승재, 2022.02.28
	/// - 함수의 상세 설명 : 로그인 창에서 유관기관 사용자만 로그인 허용하도록 인증 전 사용자 종류 확인
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/getUserType.do")
	public String getUserType(@RequestParam("userId") String userId, Model model) {
		String userType = "";
		KwsUser kwsUser = userService.selectOneUser(userId);
		if(kwsUser != null) {
			userType = kwsUser.getUserType().name();
			if (!userType.equals("CRDNS_USER")) {
				userType = "";
			}
		}
		model.addAttribute("userType", userType);
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn kcoComSsoLoginTest
	/// @brief 함수 간략한 설명 : SSO 로그인 테스트 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("/ssoLoginTest.do")
	public String kcoComSsoLoginTest() throws Exception {
		return "cmmn/ssoLoginTest";
	}
	
	/////////////////////////////////////////////
	/// @fn kcoComSsoLoginTestAction
	/// @brief 함수 간략한 설명 : SSO 로그인 테스트
	/// @remark
	/// - 함수의 상세 설명 : 타 서비스의 SSO연계를 경유할 경우 로그인 처리
	/// @param userDTO
	/// @param request
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("/ssoLoginTestAction.do")
	public String kcoComSsoLoginTestAction(UserDTO userDTO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("userId", userDTO.getUserId());
		session.setAttribute("userNam", userDTO.getUserNm());
		session.setAttribute("deptCode", userDTO.getDeptCode());
		return "redirect:/ssoLoginAction.do";
	}
	
	/////////////////////////////////////////////
	/// @fn ssoLoginAction
	/// @brief 함수 간략한 설명 : SSO 로그인
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 수정 : 이승재, 2021.02.08, userId, deptCode, userNam 값이 모두 전달되었을때 SSO로그인 프로세스 진행
	/// @param request
	/// @param response
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("/ssoLoginAction.do")
	public void ssoLoginAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 세션에서 값 받는 방식 (Java 버전 SSO)
		HttpSession session = request.getSession();
		Object userId = session.getAttribute("userId");
		Object deptCode = session.getAttribute("deptCode");
		Object userNam = session.getAttribute("userNam");

		logger.info("session에서 넘어온 사용자 ID : " + (String)userId);
		logger.info("session에서 넘어온 사용자 DEPTCODE : " + (String)deptCode);
		logger.info("session에서 넘어온 사용자 이름 : " + (String)userNam);
				
		if (userId != null && deptCode != null && userNam != null) {
			KwsUser kwsUser = null;
			if(userId != null) {
				kwsUser = userService.selectOneUser((String)userId);

				// 신규 사용자
				if(kwsUser == null) {
					//새올사용자 자동추가 옵션 여부
					String isAutomaticAddition = MessageUtils.getMessage("Contact.Seaoll.UserAutomaticAddition");
					if (StringUtils.equals(isAutomaticAddition, "true")) {
						kwsUser = new KwsUser();
						kwsUser.setUserId((String)userId);
						kwsUser.setDeptCode((String)deptCode);
						kwsUser.setUserNm((String)userNam);
						kwsUser.setUserGrad(UserGrad.ROLE_USER);
						kwsUser.setPassword("NONE");
						kwsUser.setUserType(UserType.SEAOLL_USER);
						kwsUser.setGradUserId("SYSTEM");
						kwsUser.setGradUserNm("SYSTEM");
						
						List<Long> authorGroups = new ArrayList<Long>();
						authorGroups.add(1L);
						
						userService.addUser(kwsUser, authorGroups);
					}
				}
				else {
					if(deptCode == null || !StringUtils.equals(kwsUser.getDeptCode(), (String)deptCode)) {
						kwsUser.setDeptCode((String)deptCode);
						kwsUser.setUserGrad(UserGrad.ROLE_USER);
						kwsUser.setGradUserId("SYSTEM");
						kwsUser.setGradUserNm("SYSTEM");
						userService.modifyUserBySso(kwsUser);
					}
					
					////////////////////////////////////////////////////////////////
					/// 이승재, 2021.02.22
					/// 임시 - 강릉시 사용자성명이 깨지는 현상 처리 후 기존 깨져서 들어간 성명을 바로 잡기 위하여 임시로 저장
					String city = messageSource.getMessage("Com.City", null, Locale.getDefault());
					if (StringUtils.equals(city, "강릉시")) {
						if(userNam == null || !StringUtils.equals(kwsUser.getUserNm(), (String)userNam)) {
							logger.info("기 저장된 이름 : " + kwsUser.getUserNm());
							logger.info("새로 저장할 이름 : " + (String)userNam);
							kwsUser.setUserNm((String)userNam);
							userService.modifyUserInfo(kwsUser);
						}
					}
					/////////////////////////////////////////////////////////////////////////////////////////
				}
			}
		}

		//isAutomaticAddition가 false일 경우 401에러발생
		ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		Map<String, UsernamePasswordAuthenticationFilter> beans = act.getBeansOfType(UsernamePasswordAuthenticationFilter.class);
		UsernamePasswordAuthenticationFilter springSecurity = (UsernamePasswordAuthenticationFilter)beans.values().toArray()[0];
		springSecurity.setContinueChainBeforeSuccessfulAuthentication(false);
		springSecurity.doFilter(new RequestWrapperForSecurity(request, (String)userId, "NONE"), response, null);
	}

	/////////////////////////////////////////////
	/// @fn loginFailure
	/// @brief 함수 간략한 설명 : 로그인 실패
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/loginFailure.do")
	public String loginFailure(Model model) {
		return "cmmn/login";
	}
	
	/////////////////////////////////////////////
	/// @fn logout
	/// @brief 함수 간략한 설명 : 로그 아웃
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/logout.do")
	public String logout(Model model) {
		model.addAttribute("code", "MSG001");
		return "cmmn/logout";
	}

	/////////////////////////////////////////////
	/// @fn accessDenied
	/// @brief 함수 간략한 설명 : 접근 거절
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/accessDenied.do")
	public String accessDenied(Model model) {
		model.addAttribute("code", "MSG002");
		return "cmmn/logout";
	}

	/////////////////////////////////////////////
	/// @fn concurrentExpired
	/// @brief 함수 간략한 설명 : 연결 만료
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/concurrentExpired.do")
	public String concurrentExpired(Model model) {
		model.addAttribute("code", "MSG003");
		return "cmmn/logout";
	}
	
}