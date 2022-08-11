package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsExportExcelOptn
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportExcelOptn.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:44:09 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 엑셀 옵션 모델입니다.
///   -# 
/////////////////////////////////////////////
public class KwsExportExcelOptn {
	
	/// 내보내기 번호 
	private Long exportNo;
	
	/// 필드 명 표시 여부 
	private String fieldNmIndictAt;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public String getFieldNmIndictAt() {
		return fieldNmIndictAt;
	}

	public void setFieldNmIndictAt(String fieldNmIndictAt) {
		this.fieldNmIndictAt = fieldNmIndictAt;
	}

	
}
