package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsDataCtgry
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsDataCtgry.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 22. 오후 3:06:07 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 카테고리 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsDataCtgry {

	/// 데이터 카테고리 아이디
	private String dataCtgryId;
	
	// 데이터 카테고리 명
	private String dataCtgryNm;
	
	// 정렬 순서
	private String sortOrdr;
	
	/// 사용 여부
	private String useAt;

	public String getDataCtgryId() {
		return dataCtgryId;
	}

	public void setDataCtgryId(String dataCtgryId) {
		this.dataCtgryId = dataCtgryId;
	}

	public String getDataCtgryNm() {
		return dataCtgryNm;
	}

	public void setDataCtgryNm(String dataCtgryNm) {
		this.dataCtgryNm = dataCtgryNm;
	}

	public String getSortOrdr() {
		return sortOrdr;
	}

	public void setSortOrdr(String sortOrdr) {
		this.sortOrdr = sortOrdr;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	
}
