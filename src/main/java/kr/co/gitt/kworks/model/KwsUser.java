package kr.co.gitt.kworks.model;

import java.util.List;

import kr.co.gitt.kworks.dto.UserSearchDTO;


/////////////////////////////////////////////
/// @class KwsUser
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsUser.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 16. 오후 12:06:47 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 관리 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsUser extends UserSearchDTO {
	
	/////////////////////////////////////////////
	/// @class UserGrad
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsUser.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 8. 19. 오후 5:38:25 |
	///    | Class Version | v1.0 |
	///    | 작업자 | libraleo, Others... |
	/// @section 상세설명
	/// - 이 클래스는 유저 등급 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum UserGrad {

		ROLE_ANONYMOUS("익명사용자"),
		ROLE_USER("일반사용자"),
		ROLE_DEPT_MNGR("부서관리자"),
		ROLE_MNGR("관리자");
		
		/// 유저 등급 값
		private String value;
		
		/////////////////////////////////////////////
		/// @fn 
		/// @brief 생성자 간략 설명 : 생성자
		/// @remark
		/// - 생성자 상세 설명 : 
		/// @param value 
		///~~~~~~~~~~~~~{.java}
		/// // 핵심코드
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		private UserGrad(String value) {
			this.value = value;
		}
		
		/////////////////////////////////////////////
		/// @fn getValue
		/// @brief 함수 간략한 설명 : 유저 등급 값 반환
		/// @remark
		/// - 함수의 상세 설명 : 
		/// @return 
		///
		///~~~~~~~~~~~~~{.java}
		/// // 핵심코드
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		public String getValue() {
			return value;
		}
	}
	
	/////////////////////////////////////////////
	/// @class UserType
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsUser.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | GittGDS_YJG |
	///    | Date | 2016. 12. 13. 오전 10:36:34 |
	///    | Class Version | v1.0 |
	///    | 작업자 | GittGDS_YJG, Others... |
	/// @section 상세설명
	/// - 이 클래스는 유저 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum UserType {

		SEAOLL_USER("새올 사용자"),
		CRDNS_USER("유관기관 사용자");
		
		/// 유저 등급 값
		private String value;
		
		/////////////////////////////////////////////
		/// @fn 
		/// @brief 생성자 간략 설명 : 생성자
		/// @remark
		/// - 생성자 상세 설명 : 
		/// @param value 
		///~~~~~~~~~~~~~{.java}
		/// // 핵심코드
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		private UserType(String value) {
			this.value = value;
		}
		
		/////////////////////////////////////////////
		/// @fn getValue
		/// @brief 함수 간략한 설명 : 유저 등급 값 반환
		/// @remark
		/// - 함수의 상세 설명 : 
		/// @return 
		///
		///~~~~~~~~~~~~~{.java}
		/// // 핵심코드
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		public String getValue() {
			return value;
		}
	}
	
	/// 사용자 아이디
	private String userId;
	
	/// 사용자 명
	private String userNm;
	
	/// 부서 코드
	private String deptCode;
	
	/// 부서명
	private String deptNm;
	
	/// 사용자 등급
	private UserGrad userGrad;
	
	/// 사용자 종류
	private UserType userType;
	
	/// 권한그룹
	private List<KwsAuthorGroup> kwsAuthorGroups;

	/// 패스워드
	private String password;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public UserGrad getUserGrad() {
		return userGrad;
	}

	public void setUserGrad(UserGrad userGrad) {
		this.userGrad = userGrad;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public List<KwsAuthorGroup> getKwsAuthorGroups() {
		return kwsAuthorGroups;
	}

	public void setKwsAuthorGroups(List<KwsAuthorGroup> kwsAuthorGroups) {
		this.kwsAuthorGroups = kwsAuthorGroups;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
