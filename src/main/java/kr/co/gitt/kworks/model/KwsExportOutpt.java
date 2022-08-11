package kr.co.gitt.kworks.model;

import java.util.Date;

/////////////////////////////////////////////
/// @class KwsExportOutpt
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportOutpt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 23. 오전 11:48:02 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 출력 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsExportOutpt {

	/// 출력 번호
	private Long outptNo;
	
	/// 내보내기 번호
	private Long exportNo;
	
	/// 사용자 아이디
	private String userId;
	
	/// 출력 일시
	private Date outptDt;
	
	/// 내보내기
	private KwsExport kwsExport;
	
	/// 내보내기 승인
	private KwsExportConfm kwsExportConfm;
	
	public Long getOutptNo() {
		return outptNo;
	}

	public void setOutptNo(Long outptNo) {
		this.outptNo = outptNo;
	}

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getOutptDt() {
		return outptDt;
	}

	public void setOutptDt(Date outptDt) {
		this.outptDt = outptDt;
	}

	public KwsExport getKwsExport() {
		return kwsExport;
	}

	public void setKwsExport(KwsExport kwsExport) {
		this.kwsExport = kwsExport;
	}

	public KwsExportConfm getKwsExportConfm() {
		return kwsExportConfm;
	}

	public void setKwsExportConfm(KwsExportConfm kwsExportConfm) {
		this.kwsExportConfm = kwsExportConfm;
	}
	
}
