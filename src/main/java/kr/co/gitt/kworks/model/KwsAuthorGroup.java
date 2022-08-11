package kr.co.gitt.kworks.model;

import java.sql.Date;
import java.util.List;

/////////////////////////////////////////////
/// @class KwsAuthor
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsAuthor.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 17. 오후 3:56:27 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 권한 그룹 관리 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class KwsAuthorGroup {

	/// 권한 번호
	private Long authorGroupNo;
	
	/// 권한 명
	private String authorGroupNm; 
	
	/// 작성자 아이디
	private String wrterId;
	
	/// 최초작성일	 
	private Date frstRgsde;
	
	/// 수정자 아이디
	private String updusrId;
	
	/// 최종 수정일
	private Date lastUpdde;
	
	// 시스템 권한
	private List<KwsSysAuthor> kwsSysAuthors;
	
	// 데이터 권한
	private List<KwsDataAuthor> kwsDataAuthors;
	
	// 기본지도 권한
	private List<KwsBaseMapAuthor> kwsBaseMapAuthors;
	
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

	public String getWrterId() {
		return wrterId;
	}

	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}

	public Date getFrstRgsde() {
		return frstRgsde;
	}

	public void setFrstRgsde(Date frstRgsde) {
		this.frstRgsde = frstRgsde;
	}

	public String getUpdusrId() {
		return updusrId;
	}

	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
	}

	public Date getLastUpdde() {
		return lastUpdde;
	}

	public void setLastUpdde(Date lastUpdde) {
		this.lastUpdde = lastUpdde;
	}

	public List<KwsSysAuthor> getKwsSysAuthors() {
		return kwsSysAuthors;
	}

	public void setKwsSysAuthors(List<KwsSysAuthor> kwsSysAuthors) {
		this.kwsSysAuthors = kwsSysAuthors;
	}

	public List<KwsDataAuthor> getKwsDataAuthors() {
		return kwsDataAuthors;
	}

	public void setKwsDataAuthors(List<KwsDataAuthor> kwsDataAuthors) {
		this.kwsDataAuthors = kwsDataAuthors;
	}

	public List<KwsBaseMapAuthor> getKwsBaseMapAuthors() {
		return kwsBaseMapAuthors;
	}

	public void setKwsBaseMapAuthors(List<KwsBaseMapAuthor> kwsBaseMapAuthors) {
		this.kwsBaseMapAuthors = kwsBaseMapAuthors;
	}

}
