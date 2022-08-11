package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsExportFilterDong
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportFilterDong.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:45:46 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 필터 동 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsExportFilterDong {

	/// 내보내기 번호 
	private Long exportNo;
	
	/// 동 아이디
	private Long dongId;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public Long getDongId() {
		return dongId;
	}

	public void setDongId(Long dongId) {
		this.dongId = dongId;
	}
	
}
