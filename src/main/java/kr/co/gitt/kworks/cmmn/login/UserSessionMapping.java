package kr.co.gitt.kworks.cmmn.login;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsUser.UserGrad;
import egovframework.rte.fdl.security.userdetails.EgovUserDetails;
import egovframework.rte.fdl.security.userdetails.jdbc.EgovUsersByUsernameMapping;

/////////////////////////////////////////////
/// @class UserSessionMapping
/// kr.co.gitt.kworks.cmmn.login \n
///   ㄴ UserSessionMapping.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 12. 오후 4:40:14 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 세션 맵핑 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class UserSessionMapping extends EgovUsersByUsernameMapping {

	/////////////////////////////////////////////
	/// @fn 
	/// @brief 생성자 간략 설명 : 생성자
	/// @remark
	/// - 생성자 상세 설명 : 
	/// @param ds
	/// @param usersByUsernameQuery 
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public UserSessionMapping(DataSource ds, String usersByUsernameQuery) {
		super(ds, usersByUsernameQuery);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see egovframework.rte.fdl.security.userdetails.jdbc.EgovUsersByUsernameMapping#mapRow(java.sql.ResultSet, int)
	/////////////////////////////////////////////
	@Override
	protected EgovUserDetails mapRow(ResultSet rs, int rownum) throws SQLException {
		String userId = rs.getString("USER_ID");
		String userNm = rs.getString("USER_NM");
		String deptCode = rs.getString("DEPT_CODE");
		String deptNm = rs.getString("DEPT_NM");
		String password = rs.getString("PASSWORD");
		String userGrad = rs.getString("USER_GRAD");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setUserNm(userNm);
		userDTO.setDeptCode(deptCode);
		userDTO.setDeptNm(deptNm);
		userDTO.setUserGrad(UserGrad.valueOf(userGrad));
		return new EgovUserDetails(userId, password, true, userDTO);
	}
	
}
