package kr.co.gitt.kworks.cmmn.dto;

import kr.co.gitt.kworks.model.KwsUser.UserGrad;

/////////////////////////////////////////////
/// @class SearchDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ SearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 12. 오후 4:31:49 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 검색 공통 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class SearchDTO {
	
	/// 검색조건
    private String searchCondition = "";
    
    /// 검색 Keyword
    private String searchKeyword = "";
    
    /// 검색 사용 여부
    private String searchUseYn = "";

    /// 현재 페이지
    private int pageIndex = 1;
    
    /// 페이지 갯수
    private int pageUnit = 10;

    /// 페이지 사이즈
    private int pageSize = 10;

    /// 첫번째 인덱스
    private int firstIndex = 1;

    /// 마지막 인덱스
    private int lastIndex = 1;

    /// 한 페이지에 표시할 수 
    private int recordCountPerPage = 10;
    
    /// 날짜 검색 시작일
    private String searchStartDt = "";
    
    /// 날짜검색 종료일
    private String searchEndDt = "";
    
    /// 사용자 아이디
    private String userId = "";
    
    /// 사용자 권한
    private UserGrad userGrad;

    /// 권한 그룹 명
    private String authorGroupNm;
    
    /// 시스템 아이디
    private String sysId;
    
    /// 편집종류
    private String editType;

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchUseYn() {
		return searchUseYn;
	}

	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public String getSearchStartDt() {
		return searchStartDt;
	}

	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}

	public String getSearchEndDt() {
		return searchEndDt;
	}

	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserGrad getUserGrad() {
		return userGrad;
	}

	public void setUserGrad(UserGrad userGrad) {
		this.userGrad = userGrad;
	}

	public String getAuthorGroupNm() {
		return authorGroupNm;
	}

	public void setAuthorGroupNm(String authorGroupNm) {
		this.authorGroupNm = authorGroupNm;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}
	
}
