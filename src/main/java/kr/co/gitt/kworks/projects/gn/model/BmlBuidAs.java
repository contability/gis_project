package kr.co.gitt.kworks.projects.gn.model;


//건물재산
//조원기
//2021.11.08
public class BmlBuidAs{
// OBJECTID
	private Long objectId;

	//재산번호
	private Long prtIdn;

	//재산종류
	private String pbpKnd;

	//재산명
	private String prtNam;

	//재산소유구분
	private String ownCde;

	//재산용도
	private String prsCde;

	//행정재산코드
	private String amnCde;

	//회계구분
	private String accCde;

	//재산관리관
	private String manCde;

	//담당부서
	private String chrNam;

	//위임관리관
	private String mndCde;

	//등기번호
	private String pstNum;

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

	//특수주소
	private String spcAdr;

	//특수지동
	private String spcDong;

	//특수지호
	private String spcHo;

	//소재지(도로명주소)
	private String rnAddr;

	//재산가격
	private String ptyPc;

	//회계기준가격
	private String autAmnt;

	//취득부서
	private String acqDept;

	//취득가액
	private String acqPc;

	//취득일
	private String acqDate;

	//취득방법
	private String acqCde;

	//취득사유
	private String acqPrv;

	//등기여부
	private String rstYn;

	//사용허가/대부 가능여부
	private String loanYn;

	//처분(매각)제한 유무
	private String saleLmt;

	//비고
	private String rmkExp;

	//주구조
	private String srcCde;

	//지붕구조
	private String rfCde;

	//지상층수
	private Long grdCde;

	//지하층수
	private Long ugrdCde;

	//건축면적
	private Double bldArea;

	//건축일자
	private String bldDate;

	//건축가격
	private Long bldPri;

	//건물구분
	private String bldSe;

	//건물용도
	private String bldPrp;

	//연면적
	private Double grsArea;

	//(최초)취득면적
	private Double acsArea;

	//재산용도명
	private String mesrvNm;

	//행정재산명
	private String amnNm;

	//회계구분명
	private String accNm;

	//재산관리관명
	private String manNm;

	//위임관리관명
	private String mndNm;

	//소유구분명
	private String ownNm;

	//법정동
	private String bjdNm;

	//통
	private String tong;

	//반
	private String ban;

	//처분일자
	private String dispoYmd;

	//처분방법
	private String dispoMet;

	//재산구분
	private String meansSe;

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getPbpKnd() {
		return pbpKnd;
	}

	public void setPbpKnd(String pbpKnd) {
		this.pbpKnd = pbpKnd;
	}

	public String getPrtNam() {
		return prtNam;
	}

	public void setPrtNam(String prtNam) {
		this.prtNam = prtNam;
	}

	public String getOwnCde() {
		return ownCde;
	}

	public void setOwnCde(String ownCde) {
		this.ownCde = ownCde;
	}

	public String getPrsCde() {
		return prsCde;
	}

	public void setPrsCde(String prsCde) {
		this.prsCde = prsCde;
	}

	public String getAmnCde() {
		return amnCde;
	}

	public void setAmnCde(String amnCde) {
		this.amnCde = amnCde;
	}

	public String getAccCde() {
		return accCde;
	}

	public void setAccCde(String accCde) {
		this.accCde = accCde;
	}

	public String getManCde() {
		return manCde;
	}

	public void setManCde(String manCde) {
		this.manCde = manCde;
	}

	public String getChrNam() {
		return chrNam;
	}

	public void setChrNam(String chrNam) {
		this.chrNam = chrNam;
	}

	public String getMndCde() {
		return mndCde;
	}

	public void setMndCde(String mndCde) {
		this.mndCde = mndCde;
	}

	public String getPstNum() {
		return pstNum;
	}

	public void setPstNum(String pstNum) {
		this.pstNum = pstNum;
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

	public String getSpcAdr() {
		return spcAdr;
	}

	public void setSpcAdr(String spcAdr) {
		this.spcAdr = spcAdr;
	}

	public String getSpcDong() {
		return spcDong;
	}

	public void setSpcDong(String spcDong) {
		this.spcDong = spcDong;
	}

	public String getSpcHo() {
		return spcHo;
	}

	public void setSpcHo(String spcHo) {
		this.spcHo = spcHo;
	}

	public String getRnAddr() {
		return rnAddr;
	}

	public void setRnAddr(String rnAddr) {
		this.rnAddr = rnAddr;
	}

	public String getPtyPc() {
		return ptyPc;
	}

	public void setPtyPc(String ptyPc) {
		this.ptyPc = ptyPc;
	}

	public String getAutAmnt() {
		return autAmnt;
	}

	public void setAutAmnt(String autAmnt) {
		this.autAmnt = autAmnt;
	}

	public String getAcqDept() {
		return acqDept;
	}

	public void setAcqDept(String acqDept) {
		this.acqDept = acqDept;
	}

	public String getAcqPc() {
		return acqPc;
	}

	public void setAcqPc(String acqPc) {
		this.acqPc = acqPc;
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

	public String getAcqPrv() {
		return acqPrv;
	}

	public void setAcqPrv(String acqPrv) {
		this.acqPrv = acqPrv;
	}

	public String getRstYn() {
		return rstYn;
	}

	public void setRstYn(String rstYn) {
		this.rstYn = rstYn;
	}

	public String getLoanYn() {
		return loanYn;
	}

	public void setLoanYn(String loanYn) {
		this.loanYn = loanYn;
	}

	public String getSaleLmt() {
		return saleLmt;
	}

	public void setSaleLmt(String saleLmt) {
		this.saleLmt = saleLmt;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}

	public String getSrcCde() {
		return srcCde;
	}

	public void setSrcCde(String srcCde) {
		this.srcCde = srcCde;
	}

	public String getRfCde() {
		return rfCde;
	}

	public void setRfCde(String rfCde) {
		this.rfCde = rfCde;
	}

	public String getBldDate() {
		return bldDate;
	}

	public void setBldDate(String bldDate) {
		this.bldDate = bldDate;
	}

	public String getBldSe() {
		return bldSe;
	}

	public void setBldSe(String bldSe) {
		this.bldSe = bldSe;
	}

	public String getBldPrp() {
		return bldPrp;
	}

	public void setBldPrp(String bldPrp) {
		this.bldPrp = bldPrp;
	}

	public String getMesrvNm() {
		return mesrvNm;
	}

	public void setMesrvNm(String mesrvNm) {
		this.mesrvNm = mesrvNm;
	}

	public String getAmnNm() {
		return amnNm;
	}

	public void setAmnNm(String amnNm) {
		this.amnNm = amnNm;
	}

	public String getAccNm() {
		return accNm;
	}

	public void setAccNm(String accNm) {
		this.accNm = accNm;
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

	public String getOwnNm() {
		return ownNm;
	}

	public void setOwnNm(String ownNm) {
		this.ownNm = ownNm;
	}

	public String getBjdNm() {
		return bjdNm;
	}

	public void setBjdNm(String bjdNm) {
		this.bjdNm = bjdNm;
	}

	public String getTong() {
		return tong;
	}

	public void setTong(String tong) {
		this.tong = tong;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getDispoYmd() {
		return dispoYmd;
	}

	public void setDispoYmd(String dispoYmd) {
		this.dispoYmd = dispoYmd;
	}

	public String getDispoMet() {
		return dispoMet;
	}

	public void setDispoMet(String dispoMet) {
		this.dispoMet = dispoMet;
	}

	public String getMeansSe() {
		return meansSe;
	}

	public void setMeansSe(String meansSe) {
		this.meansSe = meansSe;
	}

	public Long getPrtIdn() {
		return prtIdn;
	}

	public void setPrtIdn(Long prtIdn) {
		this.prtIdn = prtIdn;
	}

	public Long getGrdCde() {
		return grdCde;
	}

	public void setGrdCde(Long grdCde) {
		this.grdCde = grdCde;
	}

	public Long getUgrdCde() {
		return ugrdCde;
	}

	public void setUgrdCde(Long ugrdCde) {
		this.ugrdCde = ugrdCde;
	}

	public Double getBldArea() {
		return bldArea;
	}

	public void setBldArea(Double bldArea) {
		this.bldArea = bldArea;
	}

	public Long getBldPri() {
		return bldPri;
	}

	public void setBldPri(Long bldPri) {
		this.bldPri = bldPri;
	}

	public Double getGrsArea() {
		return grsArea;
	}

	public void setGrsArea(Double grsArea) {
		this.grsArea = grsArea;
	}

	public Double getAcsArea() {
		return acsArea;
	}

	public void setAcsArea(Double acsArea) {
		this.acsArea = acsArea;
	}
}
