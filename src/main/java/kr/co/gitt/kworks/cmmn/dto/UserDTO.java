package kr.co.gitt.kworks.cmmn.dto;

import java.util.List;

import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsUser.UserGrad;

/////////////////////////////////////////////
/// @class UserDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ UserDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 12. 오후 4:34:52 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class UserDTO {

	/// 사용자 아이디
	private String userId;
	
	/// 사용자 명
	private String userNm;
	
	/// 부서 코드
	private String deptCode;
	
	// 부서 명
	private String deptNm; 
	
	/// 사용자 등급
	private UserGrad userGrad;
	

	/// 권한 그룹
	private List<KwsAuthorGroup> kwsAuthorGroups;
	
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

	public List<KwsAuthorGroup> getKwsAuthorGroups() {
		return kwsAuthorGroups;
	}

	public void setKwsAuthorGroups(List<KwsAuthorGroup> kwsAuthorGroups) {
		this.kwsAuthorGroups = kwsAuthorGroups;
	}


}
