package kr.co.gitt.kworks.cmmn.dto;

import java.util.Date;

import kr.co.gitt.kworks.model.KwsExport.ProgrsSttus;

/////////////////////////////////////////////
/// @class ExportSearchDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ ExportSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 7. 오후 4:58:51 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class ExportSearchDTO extends SearchDTO {

	/// 요청자 아이디
	private String rqesterId;
	
	/// 진행 상태
	private ProgrsSttus progrsSttus;
	
	/// 부서 코드
	private String deptCode;
	
	/// 삭제 예정 일
	private Date deletePrearngeDt;
	
	/// 출력 시작일
	private Date searchStartOutptDt;
	
	/// 출력 종료일
	private Date searchEndOutptDt;
	
	/// 요청 일시
	private Date requstDt;

	public String getRqesterId() {
		return rqesterId;
	}

	public void setRqesterId(String rqesterId) {
		this.rqesterId = rqesterId;
	}

	public ProgrsSttus getProgrsSttus() {
		return progrsSttus;
	}

	public void setProgrsSttus(ProgrsSttus progrsSttus) {
		this.progrsSttus = progrsSttus;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Date getDeletePrearngeDt() {
		return deletePrearngeDt;
	}

	public void setDeletePrearngeDt(Date deletePrearngeDt) {
		this.deletePrearngeDt = deletePrearngeDt;
	}

	public Date getSearchStartOutptDt() {
		return searchStartOutptDt;
	}

	public void setSearchStartOutptDt(Date searchStartOutptDt) {
		this.searchStartOutptDt = searchStartOutptDt;
	}

	public Date getSearchEndOutptDt() {
		return searchEndOutptDt;
	}

	public void setSearchEndOutptDt(Date searchEndOutptDt) {
		this.searchEndOutptDt = searchEndOutptDt;
	}

	public Date getRequstDt() {
		return requstDt;
	}

	public void setRequstDt(Date requstDt) {
		this.requstDt = requstDt;
	}
	
}
