package kr.co.gitt.kworks.model;

import java.util.Date;


/////////////////////////////////////////////
/// @class KwsExportConfm
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportConfm.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 6. 오전 10:39:24 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 승인 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class KwsExportConfm {
	
	/// 내보내기 번호
	private Long exportNo;
	
	/// 승인자 아이디
	private String confmerId;
	
	/// 승인 일시
	private Date confmDt;
	
	/// 반려 사유
	private String returnPrvonsh;
	
	/// 사용자
	private KwsUser kwsUser;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public String getConfmerId() {
		return confmerId;
	}

	public void setConfmerId(String confmerId) {
		this.confmerId = confmerId;
	}

	public Date getConfmDt() {
		return confmDt;
	}

	public void setConfmDt(Date confmDt) {
		this.confmDt = confmDt;
	}

	public String getReturnPrvonsh() {
		return returnPrvonsh;
	}

	public void setReturnPrvonsh(String returnPrvonsh) {
		this.returnPrvonsh = returnPrvonsh;
	}

	public KwsUser getKwsUser() {
		return kwsUser;
	}

	public void setKwsUser(KwsUser kwsUser) {
		this.kwsUser = kwsUser;
	}
	
}
