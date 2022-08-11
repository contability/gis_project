package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsExportData
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportData.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 8. 오후 5:37:18 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 데이터 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsExportData {
	
	/// 내보내기 번호
	private Long exportNo;
	
	/// 데이터 아이디
	private String dataId;
	
	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

}
