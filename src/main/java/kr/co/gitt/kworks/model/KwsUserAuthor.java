package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsUserAuthor
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsUserAuthor.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 7. 27. 오전 10:46:29 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 권한 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsUserAuthor {
	
	/// 사용자 아이디
	private String userId;

	/// 권한 번호
	private Long authorGroupNo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getAuthorGroupNo() {
		return authorGroupNo;
	}

	public void setAuthorGroupNo(Long authorGroupNo) {
		this.authorGroupNo = authorGroupNo;
	}

}
