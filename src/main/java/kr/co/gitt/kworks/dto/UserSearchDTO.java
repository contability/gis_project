package kr.co.gitt.kworks.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class UserSearchDTO
/// kr.co.gitt.kworks.dto \n
///   ㄴ UserSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 23. 오후 4:35:13 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class UserSearchDTO extends SearchDTO {

	/// 권한 그룹 번호 
	private Long authorGroupNo;
	
	/// 권한 그룹 명
	private String authorGroupNm;
	
	/// 권한 부여 아이디
	private String gradUserId;
	
	// 권한 부여 명
	private String gradUserNm;
	
	public Long getAuthorGroupNo() {
		return authorGroupNo;
	}

	public void setAuthorGroupNo(Long authorGroupNo) {
		this.authorGroupNo = authorGroupNo;
	}

	public String getAuthorGroupNm() {
		return authorGroupNm;
	}

	public void setAuthorGroupNm(String authorGroupNm) {
		this.authorGroupNm = authorGroupNm;
	}

	public String getGradUserId() {
		return gradUserId;
	}

	public void setGradUserId(String gradUserId) {
		this.gradUserId = gradUserId;
	}

	public String getGradUserNm() {
		return gradUserNm;
	}

	public void setGradUserNm(String gradUserNm) {
		this.gradUserNm = gradUserNm;
	}
}
