package kr.co.gitt.kworks.contact.eais.model;

/////////////////////////////////////////////
/// @class BildngPrmisnRegstr
/// kr.co.gitt.kworks.cntc.eais.model \n
///   ㄴ BildngPrmisnRegstr.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 12. 28. 오후 4:27:23 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 건축허가대장 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class BildngPrmisnRegstr {
	
	///	허가대장_pk
	private	Long pmsrgstPk;
	
	/// 허가번호_년
	private	String pmsnoYear;	
	
	///	허가번호_기관_코드
	private	String pmsnoKikCd;
	
	///	허가번호_기관_코드명
	private	String pmsnoKikCdNm;
	
	/// 허가번호_구분_코드
	private	String pmsnoGbCd;
	
	/// 허가번호_구분_코드명
	private	String pmsnoGbCdNm;
	
	/// 허가번호_일련번호
	private	Long pmsnoSeqno;
	
	/// 건축_구분_코드
	private	String archGbCd;
	
	/// 건축_구분_코드명
	private	String archGbCdNm;

	///	허가_신고_구분_코드
	private	String pmsDeclGbCd;
	
	///	허가_신고_구분_코드명
	private	String pmsDeclGbCdNm;
	
	///	건축_허가_일
	private	String archPmsDay;
	
	/// 설계_변경_차수
	private	Long dsgnChangOdr;
	
	/// 대지_면적
	private	Double platArea;
	
	/// 전용_면적
	private	Double exuseArea;
	
	/// 허가_취소_여부
	private	String pmsCnclYn;
	
	/// 허가_취소_여부_명
	private	String pmsCnclYnNm;
	
	/// 허가_취소_일
	private	String pmsCnclDay;
	
	/// 취소_사유
	private	String cnclCaus;
	
	/// 착공_예정_일
	private	String stcnsSchedDay;
	
	/// 착공_구분_코드
	private	String stcnsGbCd;
	
	/// 착공_구분_코드명
	private	String stcnsGbCdNm;
	
	/// 착공_연기_사유
	private	String stcnsDelayCaus;
	
	/// 착공_연기_일
	private	String stcnsDelayDay;
	
	/// 실제_착공_일
	private	String realStcnsDay;
	
	/// 사용승인_구분_코드
	private	String useaprGbCd;
	
	/// 전체_사용승인_여부
	private	String entirUseaprYn;
	
	/// 임시_사용_차수
	private	Long tmpUseOdr;
	
	/// 임시_사용_일련번호
	private	Long tmpUseSeqno;
	
	/// 임시_사용승인_만료_일
	private	String tmpUseaprExpDay;
	
	/// 사용승인_일
	private	String useaprDay;
	
	/// 가설건축물_존치_만료_일
	private	String tmpbldPrsvExpDay;
	
	/// 가설건축물_연장_차수
	private	Long tmpbldExtndOdr;
	
	/// 가설건축물_처리_일
	private	String tmpbldTrsctDay;
	
	/// 건축_면적
	private	Double archArea;
	
	/// 연면적
	private	Double totarea;
	
	/// 주_건축물_수
	private	Long mainBldCnt;
	
	/// 부속_건축물_동_수
	private	Long atchBldDongCnt;
	
	/// 부속_건축물_면적
	private	Double atchBldArea;
	
	/// 주_용도_코드
	private	String mainPurpsCd;
	
	/// 주_용도_코드_명
	private	String mainPurpsCdNm;
	
	/// 기타_용도
	private	String etcPurps;
	
	/// 기타_용도명
	private	String etcPurpsNm;
	
	/// 세대_수
	private	Long hhldCnt;
	
	/// 가구_수
	private	Long fmlyCnt;
	
	/// 호_수
	private	Long hoCnt;
	
	/// 시군구 코드
	private String sigunguCd;
	
	/// 법정동_코드
	private	String bjdongCd;
	
	/// 대지_구분_코드
	private	String platGbCd;
	
	/// 번
	private	String bun;
	
	/// 지
	private	String ji;
	
	/// pnu
	private String Pnu;
	
	/// 사용승인_구분_코드_명
	private	String useaprGbCdNm;
	
	/// 대지_구분_코드_명
	private	String platGbCdNm;
	
	/// 시군구 코드 명
	private String sigunguCdNm;
	
	/// 읍면동 명
	private String bjdongCdNm;

	public Long getPmsrgstPk() {
		return pmsrgstPk;
	}

	public void setPmsrgstPk(Long pmsrgstPk) {
		this.pmsrgstPk = pmsrgstPk;
	}

	public String getPmsnoYear() {
		return pmsnoYear;
	}

	public void setPmsnoYear(String pmsnoYear) {
		this.pmsnoYear = pmsnoYear;
	}

	public String getPmsnoKikCd() {
		return pmsnoKikCd;
	}

	public void setPmsnoKikCd(String pmsnoKikCd) {
		this.pmsnoKikCd = pmsnoKikCd;
	}

	public String getPmsnoKikCdNm() {
		return pmsnoKikCdNm;
	}

	public void setPmsnoKikCdNm(String pmsnoKikCdNm) {
		this.pmsnoKikCdNm = pmsnoKikCdNm;
	}

	public String getPmsnoGbCd() {
		return pmsnoGbCd;
	}

	public void setPmsnoGbCd(String pmsnoGbCd) {
		this.pmsnoGbCd = pmsnoGbCd;
	}

	public String getPmsnoGbCdNm() {
		return pmsnoGbCdNm;
	}

	public void setPmsnoGbCdNm(String pmsnoGbCdNm) {
		this.pmsnoGbCdNm = pmsnoGbCdNm;
	}

	public Long getPmsnoSeqno() {
		return pmsnoSeqno;
	}

	public void setPmsnoSeqno(Long pmsnoSeqno) {
		this.pmsnoSeqno = pmsnoSeqno;
	}

	public String getArchGbCd() {
		return archGbCd;
	}

	public void setArchGbCd(String archGbCd) {
		this.archGbCd = archGbCd;
	}

	public String getArchGbCdNm() {
		return archGbCdNm;
	}

	public void setArchGbCdNm(String archGbCdNm) {
		this.archGbCdNm = archGbCdNm;
	}

	public String getPmsDeclGbCd() {
		return pmsDeclGbCd;
	}

	public void setPmsDeclGbCd(String pmsDeclGbCd) {
		this.pmsDeclGbCd = pmsDeclGbCd;
	}

	public String getPmsDeclGbCdNm() {
		return pmsDeclGbCdNm;
	}

	public void setPmsDeclGbCdNm(String pmsDeclGbCdNm) {
		this.pmsDeclGbCdNm = pmsDeclGbCdNm;
	}

	public String getArchPmsDay() {
		return archPmsDay;
	}

	public void setArchPmsDay(String archPmsDay) {
		this.archPmsDay = archPmsDay;
	}

	public Long getDsgnChangOdr() {
		return dsgnChangOdr;
	}

	public void setDsgnChangOdr(Long dsgnChangOdr) {
		this.dsgnChangOdr = dsgnChangOdr;
	}

	public Double getPlatArea() {
		return platArea;
	}

	public void setPlatArea(Double platArea) {
		this.platArea = platArea;
	}

	public Double getExuseArea() {
		return exuseArea;
	}

	public void setExuseArea(Double exuseArea) {
		this.exuseArea = exuseArea;
	}

	public String getPmsCnclYn() {
		return pmsCnclYn;
	}

	public void setPmsCnclYn(String pmsCnclYn) {
		this.pmsCnclYn = pmsCnclYn;
	}

	public String getPmsCnclYnNm() {
		return pmsCnclYnNm;
	}

	public void setPmsCnclYnNm(String pmsCnclYnNm) {
		this.pmsCnclYnNm = pmsCnclYnNm;
	}

	public String getPmsCnclDay() {
		return pmsCnclDay;
	}

	public void setPmsCnclDay(String pmsCnclDay) {
		this.pmsCnclDay = pmsCnclDay;
	}

	public String getCnclCaus() {
		return cnclCaus;
	}

	public void setCnclCaus(String cnclCaus) {
		this.cnclCaus = cnclCaus;
	}

	public String getStcnsSchedDay() {
		return stcnsSchedDay;
	}

	public void setStcnsSchedDay(String stcnsSchedDay) {
		this.stcnsSchedDay = stcnsSchedDay;
	}

	public String getStcnsGbCd() {
		return stcnsGbCd;
	}

	public void setStcnsGbCd(String stcnsGbCd) {
		this.stcnsGbCd = stcnsGbCd;
	}

	public String getStcnsGbCdNm() {
		return stcnsGbCdNm;
	}

	public void setStcnsGbCdNm(String stcnsGbCdNm) {
		this.stcnsGbCdNm = stcnsGbCdNm;
	}

	public String getStcnsDelayCaus() {
		return stcnsDelayCaus;
	}

	public void setStcnsDelayCaus(String stcnsDelayCaus) {
		this.stcnsDelayCaus = stcnsDelayCaus;
	}

	public String getStcnsDelayDay() {
		return stcnsDelayDay;
	}

	public void setStcnsDelayDay(String stcnsDelayDay) {
		this.stcnsDelayDay = stcnsDelayDay;
	}

	public String getRealStcnsDay() {
		return realStcnsDay;
	}

	public void setRealStcnsDay(String realStcnsDay) {
		this.realStcnsDay = realStcnsDay;
	}

	public String getUseaprGbCd() {
		return useaprGbCd;
	}

	public void setUseaprGbCd(String useaprGbCd) {
		this.useaprGbCd = useaprGbCd;
	}

	public String getEntirUseaprYn() {
		return entirUseaprYn;
	}

	public void setEntirUseaprYn(String entirUseaprYn) {
		this.entirUseaprYn = entirUseaprYn;
	}

	public Long getTmpUseOdr() {
		return tmpUseOdr;
	}

	public void setTmpUseOdr(Long tmpUseOdr) {
		this.tmpUseOdr = tmpUseOdr;
	}

	public Long getTmpUseSeqno() {
		return tmpUseSeqno;
	}

	public void setTmpUseSeqno(Long tmpUseSeqno) {
		this.tmpUseSeqno = tmpUseSeqno;
	}

	public String getTmpUseaprExpDay() {
		return tmpUseaprExpDay;
	}

	public void setTmpUseaprExpDay(String tmpUseaprExpDay) {
		this.tmpUseaprExpDay = tmpUseaprExpDay;
	}

	public String getUseaprDay() {
		return useaprDay;
	}

	public void setUseaprDay(String useaprDay) {
		this.useaprDay = useaprDay;
	}

	public String getTmpbldPrsvExpDay() {
		return tmpbldPrsvExpDay;
	}

	public void setTmpbldPrsvExpDay(String tmpbldPrsvExpDay) {
		this.tmpbldPrsvExpDay = tmpbldPrsvExpDay;
	}

	public Long getTmpbldExtndOdr() {
		return tmpbldExtndOdr;
	}

	public void setTmpbldExtndOdr(Long tmpbldExtndOdr) {
		this.tmpbldExtndOdr = tmpbldExtndOdr;
	}

	public String getTmpbldTrsctDay() {
		return tmpbldTrsctDay;
	}

	public void setTmpbldTrsctDay(String tmpbldTrsctDay) {
		this.tmpbldTrsctDay = tmpbldTrsctDay;
	}

	public Double getArchArea() {
		return archArea;
	}

	public void setArchArea(Double archArea) {
		this.archArea = archArea;
	}

	public Double getTotarea() {
		return totarea;
	}

	public void setTotarea(Double totarea) {
		this.totarea = totarea;
	}

	public Long getMainBldCnt() {
		return mainBldCnt;
	}

	public void setMainBldCnt(Long mainBldCnt) {
		this.mainBldCnt = mainBldCnt;
	}

	public Long getAtchBldDongCnt() {
		return atchBldDongCnt;
	}

	public void setAtchBldDongCnt(Long atchBldDongCnt) {
		this.atchBldDongCnt = atchBldDongCnt;
	}

	public Double getAtchBldArea() {
		return atchBldArea;
	}

	public void setAtchBldArea(Double atchBldArea) {
		this.atchBldArea = atchBldArea;
	}

	public String getMainPurpsCd() {
		return mainPurpsCd;
	}

	public void setMainPurpsCd(String mainPurpsCd) {
		this.mainPurpsCd = mainPurpsCd;
	}

	public String getMainPurpsCdNm() {
		return mainPurpsCdNm;
	}

	public void setMainPurpsCdNm(String mainPurpsCdNm) {
		this.mainPurpsCdNm = mainPurpsCdNm;
	}

	public String getEtcPurps() {
		return etcPurps;
	}

	public void setEtcPurps(String etcPurps) {
		this.etcPurps = etcPurps;
	}

	public String getEtcPurpsNm() {
		return etcPurpsNm;
	}

	public void setEtcPurpsNm(String etcPurpsNm) {
		this.etcPurpsNm = etcPurpsNm;
	}

	public Long getHhldCnt() {
		return hhldCnt;
	}

	public void setHhldCnt(Long hhldCnt) {
		this.hhldCnt = hhldCnt;
	}

	public Long getFmlyCnt() {
		return fmlyCnt;
	}

	public void setFmlyCnt(Long fmlyCnt) {
		this.fmlyCnt = fmlyCnt;
	}

	public Long getHoCnt() {
		return hoCnt;
	}

	public void setHoCnt(Long hoCnt) {
		this.hoCnt = hoCnt;
	}

	public String getSigunguCd() {
		return sigunguCd;
	}

	public void setSigunguCd(String sigunguCd) {
		this.sigunguCd = sigunguCd;
	}

	public String getBjdongCd() {
		return bjdongCd;
	}

	public void setBjdongCd(String bjdongCd) {
		this.bjdongCd = bjdongCd;
	}

	public String getPlatGbCd() {
		return platGbCd;
	}

	public void setPlatGbCd(String platGbCd) {
		this.platGbCd = platGbCd;
	}

	public String getBun() {
		return bun;
	}

	public void setBun(String bun) {
		this.bun = bun;
	}

	public String getJi() {
		return ji;
	}

	public void setJi(String ji) {
		this.ji = ji;
	}

	public String getPnu() {
		return Pnu;
	}

	public void setPnu(String pnu) {
		Pnu = pnu;
	}

	public String getUseaprGbCdNm() {
		return useaprGbCdNm;
	}

	public void setUseaprGbCdNm(String useaprGbCdNm) {
		this.useaprGbCdNm = useaprGbCdNm;
	}

	public String getPlatGbCdNm() {
		return platGbCdNm;
	}

	public void setPlatGbCdNm(String platGbCdNm) {
		this.platGbCdNm = platGbCdNm;
	}

	public String getSigunguCdNm() {
		return sigunguCdNm;
	}

	public void setSigunguCdNm(String sigunguCdNm) {
		this.sigunguCdNm = sigunguCdNm;
	}

	public String getBjdongCdNm() {
		return bjdongCdNm;
	}

	public void setBjdongCdNm(String bjdongCdNm) {
		this.bjdongCdNm = bjdongCdNm;
	}
	
	
}
