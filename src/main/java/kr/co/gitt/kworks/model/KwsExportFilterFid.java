package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsExportFilterFid
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportFilterFid.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 9. 오전 9:39:59 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 필드 FID 모델 입니다. 
///   -# 
/////////////////////////////////////////////
public class KwsExportFilterFid {

	/// 내보내기 번호
	private Long exportNo;
	
	/// 객체아이디
	private Long fid;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}
	
	
}
