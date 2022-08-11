package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsSysAuthor
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsSysAuthor.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:48:47 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 시스템 권한 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsSysAuthor {

	// 시스템 아이디
	private Long sysId;
	
	// 권한 그룹 번호
	private Long authorGroupNo;
	
	// 권한 여부
	private String authorAt;

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public Long getAuthorGroupNo() {
		return authorGroupNo;
	}

	public void setAuthorGroupNo(Long authorGroupNo) {
		this.authorGroupNo = authorGroupNo;
	}

	public String getAuthorAt() {
		return authorAt;
	}

	public void setAuthorAt(String authorAt) {
		this.authorAt = authorAt;
	}
	
}
