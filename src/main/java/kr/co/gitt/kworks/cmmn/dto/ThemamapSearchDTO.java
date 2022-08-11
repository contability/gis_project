package kr.co.gitt.kworks.cmmn.dto;

import java.util.List;

import kr.co.gitt.kworks.model.KwsThemamap.ThemamapTy;

/////////////////////////////////////////////
/// @class ThemamapSearchDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ ThemamapSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:58:29 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 주제도 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class ThemamapSearchDTO {

	/// 주제도 아이디
	private Long themamapId;
	
	/// 권한 그룹 번호
	private List<Long> authorGroupNos;
	
	/// 주제도 타입 목록
	private List<ThemamapTy> themamapTys;
	
	/// 작성자
	private String wrterId;

	public Long getThemamapId() {
		return themamapId;
	}

	public void setThemamapId(Long themamapId) {
		this.themamapId = themamapId;
	}

	public List<Long> getAuthorGroupNos() {
		return authorGroupNos;
	}

	public void setAuthorGroupNos(List<Long> authorGroupNos) {
		this.authorGroupNos = authorGroupNos;
	}

	public List<ThemamapTy> getThemamapTys() {
		return themamapTys;
	}

	public void setThemamapTys(List<ThemamapTy> themamapTys) {
		this.themamapTys = themamapTys;
	}

	public String getWrterId() {
		return wrterId;
	}

	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}
	
}
