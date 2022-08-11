package kr.co.gitt.kworks.rest.export;

import java.util.List;

import kr.co.gitt.kworks.model.KwsExport.ExportDataSe;
import kr.co.gitt.kworks.model.KwsExport.ExportTy;

/////////////////////////////////////////////
/// @class ExportCommonDTO
/// kr.co.gitt.kworks.rest.export \n
///   ㄴ ExportCommonDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:57:48 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 공통 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class ExportCommonDTO {

	/// 내보내기 명 
	private String exportNm;
	
	/// 내보내기 타입
	private ExportTy exportTy;
	
	/// 내보내기 데이터 구분
	private ExportDataSe exportDtaSe;
	
	/// 내보내기 사유
	private String exportResn;
	
	/// 데이터 아이디 목록
	private List<String> dataIds;
	
	public String getExportNm() {
		return exportNm;
	}

	public void setExportNm(String exportNm) {
		this.exportNm = exportNm;
	}

	public ExportTy getExportTy() {
		return exportTy;
	}

	public void setExportTy(ExportTy exportTy) {
		this.exportTy = exportTy;
	}

	public ExportDataSe getExportDtaSe() {
		return exportDtaSe;
	}

	public void setExportDtaSe(ExportDataSe exportDtaSe) {
		this.exportDtaSe = exportDtaSe;
	}

	public String getExportResn() {
		return exportResn;
	}

	public void setExportResn(String exportResn) {
		this.exportResn = exportResn;
	}

	public List<String> getDataIds() {
		return dataIds;
	}

	public void setDataIds(List<String> dataIds) {
		this.dataIds = dataIds;
	}
	
}
