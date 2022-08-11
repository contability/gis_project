package kr.co.gitt.kworks.projects.gn.model;

// 실태조사
public class BmlAcexDt {
	//재산번호
	private Long prtIdn;

	//조사일자
	private String srchYmd;

	//고유번호
	private String prpNo;

	//확인자
	private String cfpNm;

	//확인일자
	private String confmYmd;

	//조사자
	private String srchpNm;

	//관리구분
	private String mgtGbn;

	//관리여부
	private String mgtYn;

	//유휴상태
	private String unusedStt;

	//공법상규제사항 용도지역
	private String srvRegn;

	//공법상규제사항 용도지구
	private String srvZone;

	//공법상규제사항 도시계획시설
	private String ctPlnFc;

	//공법상규제사항 토지거래허가
	private String lntrdPrm;

	//주위환경
	private String env;

	//편의시설
	private String cnveFcl;

	//대중교통
	private String pbLcmtn;

	//도로접면
	private String rdSrfc;

	//간선도로거리
	private String mrdDist;

	//고저
	private String highLw;

	//형상
	private String shape;

	//방위
	private String direction;

	//이용상제한
	private String useLimit;

	//이용현황 지목
	private String useSituJimk;

	//이용현황 이용상황
	private String useSitu;

	//이용현황 명칭
	private String useMeansNm;

	//이용현황일단의필지수
	private String useNearFiljiNum;

	//이용현황 총면적
	private Double useTotAr;

	//이용현황 대표필지
	private String useRepFilji;

	//이용현황 주사용주체
	private String useMuseMbd;

	//이용현황건물/구축물
	private String useBdngStc;

	//이용현황 구조/지붕
	private String useStcRof;

	//이용현황 용도
	private String useSrv;

	//이용현황 층수
	private Integer useBdngLayer;

	//이용현황 연면적
	private Double useYarea;

	//이용현황 건축년월일
	private String useCnstD;

	//이용현황제3자건축물
	private String useThirdpBdng;

	//이용현황 소유자명
	private String useBdngOwnerNm;

	//이용현황 건축물대장등재여부
	private String useAbdngRegstYn;

	//기타사항 용도지역
	private String etcSrvRegn;

	//기타사항 사권설정여부 건수
	private String etcCnt;

	//기타사항 사권설정여부 금액
	private Long etcAm;

	//기타사항 공유지분율
	private String etcShaQuota;

	//기타사항 활용형태
	private String etcUseForm;

	//기타사항 활용가능성
	private String etcUseCanSrv;

	//기타사항 활용가능면적
	private String etcUseCanAr;

	//기타사항 위치
	private String etcLoc;

	//기타사항 주변현황
	private String etcNearSitu;

	//기타사항 특기사항
	private String etcItem;

	//기타사항 조사자 종합의견
	private String etcRm;

	public Long getPrtIdn() {
		return prtIdn;
	}

	public void setPrtIdn(Long prtIdn) {
		this.prtIdn = prtIdn;
	}

	public String getSrchYmd() {
		return srchYmd;
	}

	public void setSrchYmd(String srchYmd) {
		this.srchYmd = srchYmd;
	}

	public String getPrpNo() {
		return prpNo;
	}

	public void setPrpNo(String prpNo) {
		this.prpNo = prpNo;
	}

	public String getCfpNm() {
		return cfpNm;
	}

	public void setCfpNm(String cfpNm) {
		this.cfpNm = cfpNm;
	}

	public String getConfmYmd() {
		return confmYmd;
	}

	public void setConfmYmd(String confmYmd) {
		this.confmYmd = confmYmd;
	}

	public String getSrchpNm() {
		return srchpNm;
	}

	public void setSrchpNm(String srchpNm) {
		this.srchpNm = srchpNm;
	}

	public String getMgtGbn() {
		return mgtGbn;
	}

	public void setMgtGbn(String mgtGbn) {
		this.mgtGbn = mgtGbn;
	}

	public String getMgtYn() {
		return mgtYn;
	}

	public void setMgtYn(String mgtYn) {
		this.mgtYn = mgtYn;
	}

	public String getUnusedStt() {
		return unusedStt;
	}

	public void setUnusedStt(String unusedStt) {
		this.unusedStt = unusedStt;
	}

	public String getSrvRegn() {
		return srvRegn;
	}

	public void setSrvRegn(String srvRegn) {
		this.srvRegn = srvRegn;
	}

	public String getSrvZone() {
		return srvZone;
	}

	public void setSrvZone(String srvZone) {
		this.srvZone = srvZone;
	}

	public String getCtPlnFc() {
		return ctPlnFc;
	}

	public void setCtPlnFc(String ctPlnFc) {
		this.ctPlnFc = ctPlnFc;
	}

	public String getLntrdPrm() {
		return lntrdPrm;
	}

	public void setLntrdPrm(String lntrdPrm) {
		this.lntrdPrm = lntrdPrm;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getCnveFcl() {
		return cnveFcl;
	}

	public void setCnveFcl(String cnveFcl) {
		this.cnveFcl = cnveFcl;
	}

	public String getPbLcmtn() {
		return pbLcmtn;
	}

	public void setPbLcmtn(String pbLcmtn) {
		this.pbLcmtn = pbLcmtn;
	}

	public String getRdSrfc() {
		return rdSrfc;
	}

	public void setRdSrfc(String rdSrfc) {
		this.rdSrfc = rdSrfc;
	}

	public String getMrdDist() {
		return mrdDist;
	}

	public void setMrdDist(String mrdDist) {
		this.mrdDist = mrdDist;
	}

	public String getHighLw() {
		return highLw;
	}

	public void setHighLw(String highLw) {
		this.highLw = highLw;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getUseLimit() {
		return useLimit;
	}

	public void setUseLimit(String useLimit) {
		this.useLimit = useLimit;
	}

	public String getUseSituJimk() {
		return useSituJimk;
	}

	public void setUseSituJimk(String useSituJimk) {
		this.useSituJimk = useSituJimk;
	}

	public String getUseSitu() {
		return useSitu;
	}

	public void setUseSitu(String useSitu) {
		this.useSitu = useSitu;
	}

	public String getUseMeansNm() {
		return useMeansNm;
	}

	public void setUseMeansNm(String useMeansNm) {
		this.useMeansNm = useMeansNm;
	}

	public String getUseNearFiljiNum() {
		return useNearFiljiNum;
	}

	public void setUseNearFiljiNum(String useNearFiljiNum) {
		this.useNearFiljiNum = useNearFiljiNum;
	}

	public Double getUseTotAr() {
		return useTotAr;
	}

	public void setUseTotAr(Double useTotAr) {
		this.useTotAr = useTotAr;
	}

	public String getUseRepFilji() {
		return useRepFilji;
	}

	public void setUseRepFilji(String useRepFilji) {
		this.useRepFilji = useRepFilji;
	}

	public String getUseMuseMbd() {
		return useMuseMbd;
	}

	public void setUseMuseMbd(String useMuseMbd) {
		this.useMuseMbd = useMuseMbd;
	}

	public String getUseBdngStc() {
		return useBdngStc;
	}

	public void setUseBdngStc(String useBdngStc) {
		this.useBdngStc = useBdngStc;
	}

	public String getUseStcRof() {
		return useStcRof;
	}

	public void setUseStcRof(String useStcRof) {
		this.useStcRof = useStcRof;
	}

	public String getUseSrv() {
		return useSrv;
	}

	public void setUseSrv(String useSrv) {
		this.useSrv = useSrv;
	}

	public Integer getUseBdngLayer() {
		return useBdngLayer;
	}

	public void setUseBdngLayer(Integer useBdngLayer) {
		this.useBdngLayer = useBdngLayer;
	}

	public Double getUseYarea() {
		return useYarea;
	}

	public void setUseYarea(Double useYarea) {
		this.useYarea = useYarea;
	}

	public String getUseCnstD() {
		return useCnstD;
	}

	public void setUseCnstD(String useCnstD) {
		this.useCnstD = useCnstD;
	}

	public String getUseThirdpBdng() {
		return useThirdpBdng;
	}

	public void setUseThirdpBdng(String useThirdpBdng) {
		this.useThirdpBdng = useThirdpBdng;
	}

	public String getUseBdngOwnerNm() {
		return useBdngOwnerNm;
	}

	public void setUseBdngOwnerNm(String useBdngOwnerNm) {
		this.useBdngOwnerNm = useBdngOwnerNm;
	}

	public String getUseAbdngRegstYn() {
		return useAbdngRegstYn;
	}

	public void setUseAbdngRegstYn(String useAbdngRegstYn) {
		this.useAbdngRegstYn = useAbdngRegstYn;
	}

	public String getEtcSrvRegn() {
		return etcSrvRegn;
	}

	public void setEtcSrvRegn(String etcSrvRegn) {
		this.etcSrvRegn = etcSrvRegn;
	}

	public String getEtcCnt() {
		return etcCnt;
	}

	public void setEtcCnt(String etcCnt) {
		this.etcCnt = etcCnt;
	}

	public Long getEtcAm() {
		return etcAm;
	}

	public void setEtcAm(Long etcAm) {
		this.etcAm = etcAm;
	}

	public String getEtcShaQuota() {
		return etcShaQuota;
	}

	public void setEtcShaQuota(String etcShaQuota) {
		this.etcShaQuota = etcShaQuota;
	}

	public String getEtcUseForm() {
		return etcUseForm;
	}

	public void setEtcUseForm(String etcUseForm) {
		this.etcUseForm = etcUseForm;
	}

	public String getEtcUseCanSrv() {
		return etcUseCanSrv;
	}

	public void setEtcUseCanSrv(String etcUseCanSrv) {
		this.etcUseCanSrv = etcUseCanSrv;
	}

	public String getEtcUseCanAr() {
		return etcUseCanAr;
	}

	public void setEtcUseCanAr(String etcUseCanAr) {
		this.etcUseCanAr = etcUseCanAr;
	}

	public String getEtcLoc() {
		return etcLoc;
	}

	public void setEtcLoc(String etcLoc) {
		this.etcLoc = etcLoc;
	}

	public String getEtcNearSitu() {
		return etcNearSitu;
	}

	public void setEtcNearSitu(String etcNearSitu) {
		this.etcNearSitu = etcNearSitu;
	}

	public String getEtcItem() {
		return etcItem;
	}

	public void setEtcItem(String etcItem) {
		this.etcItem = etcItem;
	}

	public String getEtcRm() {
		return etcRm;
	}

	public void setEtcRm(String etcRm) {
		this.etcRm = etcRm;
	}
}
