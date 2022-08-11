package kr.co.gitt.kworks.projects.gn.model;

// V_BML_ACEX_AS 뷰 테이블 모델입니다
// 20220119 정신형
public class BmlAcexAs extends BmlAcexDt{
	
//	//재산번호
//	private Long prtIdn;
//
//	//조사일자
//	private String srchYmd;
//
//	//고유번호
//	private String prpNo;
//
//	//확인자
//	private String cfpNm;
//
//	//확인일자
//	private String confmYmd;
//
//	//조사자
//	private String srchpNm;
//
//	//관리구분
//	private String mgtGbn;
//
//	//관리여부
//	private String mgtYn;
//
//	//유휴상태
//	private String unusedStt;
//
//	//공법상규제사항 용도지역
//	private String srvRegn;
//
//	//공법상규제사항 용도지구
//	private String srvZone;
//
//	//공법상규제사항 도시계획시설
//	private String ctPlnFc;
//
//	//공법상규제사항 토지거래허가
//	private String lntrdPrm;
//
//	//주위환경
//	private String env;
//
//	//편의시설
//	private String cnveFcl;
//
//	//대중교통
//	private String pbLcmtn;
//
//	//도로접면
//	private String rdSrfc;
//
//	//간선도로거리
//	private String mrdDist;
//
//	//고저
//	private String highLw;
//
//	//형상
//	private String shape;
//
//	//방위
//	private String direction;
//
//	//이용상제한
//	private String useLimit;
//
//	//이용현황 지목
//	private String useSituJimk;
//
//	//이용현황 이용상황
//	private String useSitu;
//
//	//이용현황 명칭
//	private String useMeansNm;
//
//	//이용현황일단의필지수
//	private String useNearFiljiNum;
//
//	//이용현황 총면적
//	private Long useTotAr;
//
//	//이용현황 대표필지
//	private String useRepFilji;
//
//	//이용현황 주사용주체
//	private String useMuseMbd;
//
//	//이용현황건물/구축물
//	private String useBdngStc;
//
//	//이용현황 구조/지붕
//	private String useStcRof;
//
//	//이용현황 용도
//	private String useSrv;
//
//	//이용현황 층수
//	private Integer useBdngLayer;
//
//	//이용현황 연면적
//	private Long useYarea;
//
//	//이용현황 건축년월일
//	private String useCnstD;
//
//	//이용현황제3자건축물
//	private String useThirdpBdng;
//
//	//이용현황 소유자명
//	private String useBdngOwnerNm;
//
//	//이용현황 건축물대장등재여부
//	private String useAbdngRegstYn;
//
//	//기타사항 용도지역
//	private String etcSrvRegn;
//
//	//기타사항 사권설정여부 건수
//	private String etcCnt;
//
//	//기타사항 사권설정여부 금액
//	private Long etcAm;
//
//	//기타사항 공유지분율
//	private String etcShaQuota;
//
//	//기타사항 활용형태
//	private String etcUseForm;
//
//	//기타사항 활용가능성
//	private String etcUseCanSrv;
//
//	//기타사항 활용가능면적
//	private String etcUseCanAr;
//
//	//기타사항 위치
//	private String etcLoc;
//
//	//기타사항 주변현황
//	private String etcNearSitu;
//
//	//기타사항 특기사항
//	private String etcItem;
//
//	//기타사항 조사자 종합의견
//	private String etcRm;

	//재산관리관코드
	private String manCde;

	//재산관리관
	private String manNm;

	//위임관리관코드명
	private String mndNm;

	//재산종류
	private String pbpKnd;

	//법정동코드
	private String bjdCde;

	//소재지(지번주소)
	private String locPlc;

	//산코드
	private String monut;

	//번지
	private String bun;

	//호
	private String ho;

	//재산가격
	private Long ptyPc;

	//회계구분명
	private String accNm;

	//재산구분
	private String mesrvNm;

	//취득부서
	private String acqDept;

	//취득일
	private String acqDate;

	//취득방법
	private String acqCde;

	//취득금액
	private String acqPc;

	//등기여부
	private String rstYn;

	//등기번호
	private String pstNum;

	//사용허가/대부 가능여부
	private String loanYn;

	public String getManCde() {
		return manCde;
	}

	public void setManCde(String manCde) {
		this.manCde = manCde;
	}

	public String getManNm() {
		return manNm;
	}

	public void setManNm(String manNm) {
		this.manNm = manNm;
	}

	public String getMndNm() {
		return mndNm;
	}

	public void setMndNm(String mndNm) {
		this.mndNm = mndNm;
	}

	public String getPbpKnd() {
		return pbpKnd;
	}

	public void setPbpKnd(String pbpKnd) {
		this.pbpKnd = pbpKnd;
	}

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getLocPlc() {
		return locPlc;
	}

	public void setLocPlc(String locPlc) {
		this.locPlc = locPlc;
	}

	public String getMonut() {
		return monut;
	}

	public void setMonut(String monut) {
		this.monut = monut;
	}

	public String getBun() {
		return bun;
	}

	public void setBun(String bun) {
		this.bun = bun;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public Long getPtyPc() {
		return ptyPc;
	}

	public void setPtyPc(Long ptyPc) {
		this.ptyPc = ptyPc;
	}

	public String getAccNm() {
		return accNm;
	}

	public void setAccNm(String accNm) {
		this.accNm = accNm;
	}

	public String getMesrvNm() {
		return mesrvNm;
	}

	public void setMesrvNm(String mesrvNm) {
		this.mesrvNm = mesrvNm;
	}

	public String getAcqDept() {
		return acqDept;
	}

	public void setAcqDept(String acqDept) {
		this.acqDept = acqDept;
	}

	public String getAcqDate() {
		return acqDate;
	}

	public void setAcqDate(String acqDate) {
		this.acqDate = acqDate;
	}

	public String getAcqCde() {
		return acqCde;
	}

	public void setAcqCde(String acqCde) {
		this.acqCde = acqCde;
	}

	public String getAcqPc() {
		return acqPc;
	}

	public void setAcqPc(String acqPc) {
		this.acqPc = acqPc;
	}

	public String getRstYn() {
		return rstYn;
	}

	public void setRstYn(String rstYn) {
		this.rstYn = rstYn;
	}

	public String getPstNum() {
		return pstNum;
	}

	public void setPstNum(String pstNum) {
		this.pstNum = pstNum;
	}

	public String getLoanYn() {
		return loanYn;
	}

	public void setLoanYn(String loanYn) {
		this.loanYn = loanYn;
	}
}
