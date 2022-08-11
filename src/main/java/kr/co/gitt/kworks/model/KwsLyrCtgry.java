package kr.co.gitt.kworks.model;

import java.util.List;

/////////////////////////////////////////////
/// @class KwsLyrCtgry
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsLyrCtgry.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 19. 오전 11:30:29 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 레이어 카테고리 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsLyrCtgry {

	/// 레이어 카테고리 아이디
	private String lyrCtgryId;
	
	/// 레이어 카테고리 명
	private String lyrCtgryNm;
	
	/// 정렬 순서
	private Long sortOrdr;
	
	/// 사용 여부
	private String useAt;
	
	/// 레이어 목록
	private List<KwsLyr> kwsLyrs;

	public String getLyrCtgryId() {
		return lyrCtgryId;
	}

	public void setLyrCtgryId(String lyrCtgryId) {
		this.lyrCtgryId = lyrCtgryId;
	}

	public String getLyrCtgryNm() {
		return lyrCtgryNm;
	}

	public void setLyrCtgryNm(String lyrCtgryNm) {
		this.lyrCtgryNm = lyrCtgryNm;
	}

	public Long getSortOrdr() {
		return sortOrdr;
	}

	public void setSortOrdr(Long sortOrdr) {
		this.sortOrdr = sortOrdr;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public List<KwsLyr> getKwsLyrs() {
		return kwsLyrs;
	}

	public void setKwsLyrs(List<KwsLyr> kwsLyrs) {
		this.kwsLyrs = kwsLyrs;
	}
	
}
