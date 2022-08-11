package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsDataFieldCtgry
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsDataFieldCtgry.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 12. 오후 2:10:31 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 필드 카테고리 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsDataFieldCtgry {
	
	/// 필드 카테고리 아이디
	private String fieldCtgryId;
	
	/// 필드 카테고리 명
	private String fieldCtgryNm;
	
	/// 정렬 순서
	private Long sortOrdr;
	
	/// 사용 여부
	private String useAt;

	public String getFieldCtgryId() {
		return fieldCtgryId;
	}

	public void setFieldCtgryId(String fieldCtgryId) {
		this.fieldCtgryId = fieldCtgryId;
	}

	public String getFieldCtgryNm() {
		return fieldCtgryNm;
	}

	public void setFieldCtgryNm(String fieldCtgryNm) {
		this.fieldCtgryNm = fieldCtgryNm;
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
	
}
