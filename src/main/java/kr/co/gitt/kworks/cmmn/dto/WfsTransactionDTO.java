package kr.co.gitt.kworks.cmmn.dto;

import kr.co.gitt.kworks.model.KwsEditHist.EditType;

/////////////////////////////////////////////
/// @class WfsTransactionDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ WfsTransactionDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 22. 오후 2:29:46 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 WFS 트랜잭션 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class WfsTransactionDTO {

	/// 시스템 아이디
	private Long sysId;
	
	/// 편집 타입
	private EditType editType;
	
	/// 사용자 아이디
	private String userId;
	
	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public EditType getEditType() {
		return editType;
	}

	public void setEditType(EditType editType) {
		this.editType = editType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
