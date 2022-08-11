package kr.co.gitt.kworks.model;

import java.util.Date;
import java.util.List;

/////////////////////////////////////////////
/// @class KwsExport
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExport.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 6. 오전 10:36:33 |
///    | Class Version | v1.0 |
///    | 작업자 | 윤중근, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class KwsExport {
	
	/// 내보내기 타입
	public enum ExportTy {
		DXF("DXF"),
		EXCEL("엑셀"),
		SHAPE("SHAPE"),
		IMAGE("이미지"),
		OUTPUT("고급 출력");
		
		private String value;
		
		private ExportTy(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	/// 내보내기 필터 타입
	public enum ExportFilterTy {
		NONE, FIDS, DONG, BBOX, IMAGE, OUTPUT, POLYGON;
	}
	
	/////////////////////////////////////////////
	/// @class ExportDataSe
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsExport.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 11. 23. 오전 10:48:05 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 내보내기 데이터 구분 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum ExportDataSe {
		CLOSED_DOOR ("비공개"),
		OPEN("공개"),
		OPEN_LIMITATION ("공개제한");
		
		private String value;
		
		private ExportDataSe(String value) {
			this.value = value;
		}
			
		public String getValue() {
			return value;
		}
		
	}
	
	/// 진행 상태
	public enum ProgrsSttus {
		EXPORT_REQUEST("배포 요청"),
		CONSENT_WAITING("승인 대기 중"),
		EXPORT_FAILURE("배포 실패"),
		DATA_NONE("데이터 없음"),
		CONSENT_COMPLETION("승인 완료"),
		RETURN("반려"),
		PERIOD_END("기간 만료");
		
		private String value;
		
		private ProgrsSttus(String value) {
			this.value = value;
		}
			
		public String getValue() {
			return value;
		}
	}

	/// 내보내기 번호
	private Long exportNo;
	
	/// 내보내기 명
	private String exportNm;
	
	/// 내보내기 타입
	private ExportTy exportTy;
	
	/// 내보내기 필터 타입
	private ExportFilterTy exportFilterTy;
	
	/// 내보내기 좌표계 아이디
	private String exportCntmId;
	
	/// 내보내기 데이터 구분
	private ExportDataSe exportDtaSe;
	
	/// 내보내기 사유
	private String exportResn;
	
	/// 중심점 경도
	private Double centerLon;
	
	/// 중심전 위도
	private Double centerLat;
	
	/// 진행 상태
	private ProgrsSttus progrsSttus;
	
	/// 요청자 아이디
	private String rqesterId;
	
	/// 요청 일시
	private Date requstDt;
	
	/// 배포 일시
	private Date wdtbDt;
	
	/// 삭제 예정 일시
	private Date deletePrearngeDt;
	
	/// 사용자
	private KwsUser kwsUser;
	
	/// 내보내기 승인 
	private KwsExportConfm kwsExportConfm;
	
	/// 내보내기 파일 목록
	private List<KwsExportFile> kwsExportFiles;
	
	/// 내보내기 레이어 목록
	private List<KwsExportData> kwsExportDatas;
	
	/// 내보내기 엑셀 옵션
	private KwsExportExcelOptn kwsExportExcelOptn;
	
	/// 내보내기 필터
	private List<KwsExportFilterFid> kwsExportFilterFids;
	
	/// 내보내기 필터 BBOX
	private KwsExportFilterBbox kwsExportFilterBbox;
	
	/// 내보내기 필터 동
	private KwsExportFilterDong kwsExportFilterDong;
	
	/// 내보내기 필터 이미지
	private KwsExportFilterImage kwsExportFilterImage;
	
	/// 내보내기 필터 출력
	private KwsExportFilterOutpt kwsExportFilterOutpt;
	
	/// 내보내기 필터 다각형
	private KwsExportFilterPolygon kwsExportFilterPolygon;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

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

	public ExportFilterTy getExportFilterTy() {
		return exportFilterTy;
	}

	public void setExportFilterTy(ExportFilterTy exportFilterTy) {
		this.exportFilterTy = exportFilterTy;
	}

	public String getExportCntmId() {
		return exportCntmId;
	}

	public void setExportCntmId(String exportCntmId) {
		this.exportCntmId = exportCntmId;
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

	public Double getCenterLon() {
		return centerLon;
	}

	public void setCenterLon(Double centerLon) {
		this.centerLon = centerLon;
	}

	public Double getCenterLat() {
		return centerLat;
	}

	public void setCenterLat(Double centerLat) {
		this.centerLat = centerLat;
	}

	public ProgrsSttus getProgrsSttus() {
		return progrsSttus;
	}

	public void setProgrsSttus(ProgrsSttus progrsSttus) {
		this.progrsSttus = progrsSttus;
	}

	public String getRqesterId() {
		return rqesterId;
	}

	public void setRqesterId(String rqesterId) {
		this.rqesterId = rqesterId;
	}

	public Date getRequstDt() {
		return requstDt;
	}

	public void setRequstDt(Date requstDt) {
		this.requstDt = requstDt;
	}

	public Date getWdtbDt() {
		return wdtbDt;
	}

	public void setWdtbDt(Date wdtbDt) {
		this.wdtbDt = wdtbDt;
	}

	public Date getDeletePrearngeDt() {
		return deletePrearngeDt;
	}

	public void setDeletePrearngeDt(Date deletePrearngeDt) {
		this.deletePrearngeDt = deletePrearngeDt;
	}

	public KwsExportConfm getKwsExportConfm() {
		return kwsExportConfm;
	}

	public void setKwsExportConfm(KwsExportConfm kwsExportConfm) {
		this.kwsExportConfm = kwsExportConfm;
	}

	public List<KwsExportFile> getKwsExportFiles() {
		return kwsExportFiles;
	}

	public void setKwsExportFiles(List<KwsExportFile> kwsExportFiles) {
		this.kwsExportFiles = kwsExportFiles;
	}

	public List<KwsExportData> getKwsExportDatas() {
		return kwsExportDatas;
	}

	public void setKwsExportDatas(List<KwsExportData> kwsExportDatas) {
		this.kwsExportDatas = kwsExportDatas;
	}

	public KwsExportExcelOptn getKwsExportExcelOptn() {
		return kwsExportExcelOptn;
	}

	public void setKwsExportExcelOptn(KwsExportExcelOptn kwsExportExcelOptn) {
		this.kwsExportExcelOptn = kwsExportExcelOptn;
	}

	public List<KwsExportFilterFid> getKwsExportFilterFids() {
		return kwsExportFilterFids;
	}

	public void setKwsExportFilterFids(List<KwsExportFilterFid> kwsExportFilterFids) {
		this.kwsExportFilterFids = kwsExportFilterFids;
	}

	public KwsExportFilterBbox getKwsExportFilterBbox() {
		return kwsExportFilterBbox;
	}

	public void setKwsExportFilterBbox(KwsExportFilterBbox kwsExportFilterBbox) {
		this.kwsExportFilterBbox = kwsExportFilterBbox;
	}

	public KwsExportFilterDong getKwsExportFilterDong() {
		return kwsExportFilterDong;
	}

	public void setKwsExportFilterDong(KwsExportFilterDong kwsExportFilterDong) {
		this.kwsExportFilterDong = kwsExportFilterDong;
	}

	public KwsExportFilterImage getKwsExportFilterImage() {
		return kwsExportFilterImage;
	}

	public void setKwsExportFilterImage(KwsExportFilterImage kwsExportFilterImage) {
		this.kwsExportFilterImage = kwsExportFilterImage;
	}

	public KwsExportFilterOutpt getKwsExportFilterOutpt() {
		return kwsExportFilterOutpt;
	}

	public void setKwsExportFilterOutpt(KwsExportFilterOutpt kwsExportFilterOutpt) {
		this.kwsExportFilterOutpt = kwsExportFilterOutpt;
	}

	public KwsExportFilterPolygon getKwsExportFilterPolygon() {
		return kwsExportFilterPolygon;
	}

	public void setKwsExportFilterPolygon(
			KwsExportFilterPolygon kwsExportFilterPolygon) {
		this.kwsExportFilterPolygon = kwsExportFilterPolygon;
	}

	public KwsUser getKwsUser() {
		return kwsUser;
	}

	public void setKwsUser(KwsUser kwsUser) {
		this.kwsUser = kwsUser;
	}
	
}
