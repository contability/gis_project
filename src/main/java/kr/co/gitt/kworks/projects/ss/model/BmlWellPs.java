package kr.co.gitt.kworks.projects.ss.model;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

public class BmlWellPs extends SearchDTO {
	//OBJECTID
	private Long objectid;
	
	private String geom;
	
	//지형지물부호
	private String ftrCde;
	
	//지형지물ID
	private Long ftrIdn;
	
	//허가신고번호
	private String permNtNo;
	
	//관리번호
	private String mgNo;
	
	//시설구분
	private String permNtFormGbn;
	
	//구분
	private String rgtMbdGbn;
	
	//법인_사업자_생년월일
	private String rgtMbdRegNo;
	
	//상호또는성명
	private String rgtMbdNm;
	
	//대표자또는상호
	private String nm;
	
	//주소
	private String rgtMbdAddr;
	
	//도로명 주소
	private String rgtMbdRdnAddr;
	
	//개발위치_지번주소
	private String dvopLocAddr;
	
	//개발위치_도로명주소
	private String dvopLocRdnAddr;
	
	//개발위치_법정동코드
	private String dvopLocRegnCode;
	
	//개발위치_법정동명
	private String dvopLocUmdName;
	
	//개발위치_본번
	private String dvopLocBunji;
	
	//개발위치_부번
	private String dvopLocHo;
	
	//경도도
	private Integer litdDg;
	
	//경도분
	private Integer litdMint;
	
	//경도초
	private Double litdSc;
	
	//위도도
	private Integer lttdDg;
	
	//위도분
	private Integer lttdMint;
	
	//위도초
	private Double lttdSc;
	
	//용도
	private String uwaterSrv;
	
	//세부용도
	private String uwaterDtlSrv;
	
	//음용여부
	private String uwaterPotaYn;
	
	//굴착깊이
	private Double digDph;
	
	//굴착지름
	private Double digDbt;
	
	//취수계획량
	private Double frwPlnQua;
	
	//소요수량
	private Integer ndQt;
	
	//동력장치마력
	private Double dynEqnHrp;
	
	//토출관직경
	private Double pipeDiam;
	
	//설치심도
	private Double esbDph;
	
	//양수능력
	private Double rwtCap;
	
	//종료신고일
	private String dvusEndNtYmd;
	
	//폐공발생일
	private String lnhoRaiseYmd;
	
	//폐공발생원인
	private String lnhoRaiseCau;
	
	//폐공처리방법
	private String lnhoDealMet;
	
	//원상복구방법
	private String ostrsMet;
	
	//폐공처리자
	private String lnhoDppNm;
	
	//민원취하
	private String permYn;
	
	//허가취소
	private String permCancelYn;
	
	//비고
	private String rem;
	
	/// 데이터 한글 명칭
	private String dataAlias;
	
	/// 데이터 아이디
	private String dataId;
	
	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getPermNtNo() {
		return permNtNo;
	}

	public void setPermNtNo(String permNtNo) {
		this.permNtNo = permNtNo;
	}

	public String getMgNo() {
		return mgNo;
	}

	public void setMgNo(String mgNo) {
		this.mgNo = mgNo;
	}

	public String getPermNtFormGbn() {
		return permNtFormGbn;
	}

	public void setPermNtFormGbn(String permNtFormGbn) {
		this.permNtFormGbn = permNtFormGbn;
	}

	public String getRgtMbdGbn() {
		return rgtMbdGbn;
	}

	public void setRgtMbdGbn(String rgtMbdGbn) {
		this.rgtMbdGbn = rgtMbdGbn;
	}

	public String getRgtMbdRegNo() {
		return rgtMbdRegNo;
	}

	public void setRgtMbdRegNo(String rgtMbdRegNo) {
		this.rgtMbdRegNo = rgtMbdRegNo;
	}

	public String getRgtMbdNm() {
		return rgtMbdNm;
	}

	public void setRgtMbdNm(String rgtMbdNm) {
		this.rgtMbdNm = rgtMbdNm;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getRgtMbdAddr() {
		return rgtMbdAddr;
	}

	public void setRgtMbdAddr(String rgtMbdAddr) {
		this.rgtMbdAddr = rgtMbdAddr;
	}

	public String getRgtMbdRdnAddr() {
		return rgtMbdRdnAddr;
	}

	public void setRgtMbdRdnAddr(String rgtMbdRdnAddr) {
		this.rgtMbdRdnAddr = rgtMbdRdnAddr;
	}

	public String getDvopLocAddr() {
		return dvopLocAddr;
	}

	public void setDvopLocAddr(String dvopLocAddr) {
		this.dvopLocAddr = dvopLocAddr;
	}

	public String getDvopLocRdnAddr() {
		return dvopLocRdnAddr;
	}

	public void setDvopLocRdnAddr(String dvopLocRdnAddr) {
		this.dvopLocRdnAddr = dvopLocRdnAddr;
	}

	public String getDvopLocRegnCode() {
		return dvopLocRegnCode;
	}

	public void setDvopLocRegnCode(String dvopLocRegnCode) {
		this.dvopLocRegnCode = dvopLocRegnCode;
	}

	public String getDvopLocUmdName() {
		return dvopLocUmdName;
	}

	public void setDvopLocUmdName(String dvopLocUmdName) {
		this.dvopLocUmdName = dvopLocUmdName;
	}

	public String getDvopLocBunji() {
		return dvopLocBunji;
	}

	public void setDvopLocBunji(String dvopLocBunji) {
		this.dvopLocBunji = dvopLocBunji;
	}

	public String getDvopLocHo() {
		return dvopLocHo;
	}

	public void setDvopLocHo(String dvopLocHo) {
		this.dvopLocHo = dvopLocHo;
	}

	public Integer getLitdDg() {
		return litdDg;
	}

	public void setLitdDg(Integer litdDg) {
		this.litdDg = litdDg;
	}

	public Integer getLitdMint() {
		return litdMint;
	}

	public void setLitdMint(Integer litdMint) {
		this.litdMint = litdMint;
	}

	public Double getLitdSc() {
		return litdSc;
	}

	public void setLitdSc(Double litdSc) {
		this.litdSc = litdSc;
	}

	public Integer getLttdDg() {
		return lttdDg;
	}

	public void setLttdDg(Integer lttdDg) {
		this.lttdDg = lttdDg;
	}

	public Integer getLttdMint() {
		return lttdMint;
	}

	public void setLttdMint(Integer lttdMint) {
		this.lttdMint = lttdMint;
	}

	public Double getLttdSc() {
		return lttdSc;
	}

	public void setLttdSc(Double lttdSc) {
		this.lttdSc = lttdSc;
	}

	public String getUwaterSrv() {
		return uwaterSrv;
	}

	public void setUwaterSrv(String uwaterSrv) {
		this.uwaterSrv = uwaterSrv;
	}

	public String getUwaterDtlSrv() {
		return uwaterDtlSrv;
	}

	public void setUwaterDtlSrv(String uwaterDtlSrv) {
		this.uwaterDtlSrv = uwaterDtlSrv;
	}

	public String getUwaterPotaYn() {
		return uwaterPotaYn;
	}

	public void setUwaterPotaYn(String uwaterPotaYn) {
		this.uwaterPotaYn = uwaterPotaYn;
	}

	public Double getDigDph() {
		return digDph;
	}

	public void setDigDph(Double digDph) {
		this.digDph = digDph;
	}

	public Double getDigDbt() {
		return digDbt;
	}

	public void setDigDbt(Double digDbt) {
		this.digDbt = digDbt;
	}

	public Double getFrwPlnQua() {
		return frwPlnQua;
	}

	public void setFrwPlnQua(Double frwPlnQua) {
		this.frwPlnQua = frwPlnQua;
	}

	public Integer getNdQt() {
		return ndQt;
	}

	public void setNdQt(Integer ndQt) {
		this.ndQt = ndQt;
	}

	public Double getDynEqnHrp() {
		return dynEqnHrp;
	}

	public void setDynEqnHrp(Double dynEqnHrp) {
		this.dynEqnHrp = dynEqnHrp;
	}

	public Double getPipeDiam() {
		return pipeDiam;
	}

	public void setPipeDiam(Double pipeDiam) {
		this.pipeDiam = pipeDiam;
	}

	public Double getEsbDph() {
		return esbDph;
	}

	public void setEsbDph(Double esbDph) {
		this.esbDph = esbDph;
	}

	public Double getRwtCap() {
		return rwtCap;
	}

	public void setRwtCap(Double rwtCap) {
		this.rwtCap = rwtCap;
	}

	public String getDvusEndNtYmd() {
		return dvusEndNtYmd;
	}

	public void setDvusEndNtYmd(String dvusEndNtYmd) {
		this.dvusEndNtYmd = dvusEndNtYmd;
	}

	public String getLnhoRaiseYmd() {
		return lnhoRaiseYmd;
	}

	public void setLnhoRaiseYmd(String lnhoRaiseYmd) {
		this.lnhoRaiseYmd = lnhoRaiseYmd;
	}

	public String getLnhoRaiseCau() {
		return lnhoRaiseCau;
	}

	public void setLnhoRaiseCau(String lnhoRaiseCau) {
		this.lnhoRaiseCau = lnhoRaiseCau;
	}

	public String getLnhoDealMet() {
		return lnhoDealMet;
	}

	public void setLnhoDealMet(String lnhoDealMet) {
		this.lnhoDealMet = lnhoDealMet;
	}

	public String getOstrsMet() {
		return ostrsMet;
	}

	public void setOstrsMet(String ostrsMet) {
		this.ostrsMet = ostrsMet;
	}

	public String getLnhoDppNm() {
		return lnhoDppNm;
	}

	public void setLnhoDppNm(String lnhoDppNm) {
		this.lnhoDppNm = lnhoDppNm;
	}

	public String getPermYn() {
		return permYn;
	}

	public void setPermYn(String permYn) {
		this.permYn = permYn;
	}

	public String getPermCancelYn() {
		return permCancelYn;
	}

	public void setPermCancelYn(String permCancelYn) {
		this.permCancelYn = permCancelYn;
	}

	public String getRem() {
		return rem;
	}

	public void setRem(String rem) {
		this.rem = rem;
	}

	public String getDataAlias() {
		return dataAlias;
	}

	public void setDataAlias(String dataAlias) {
		this.dataAlias = dataAlias;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
}
