package kr.co.gitt.kworks.projects.gn.model;


//토지재산
//조원기
//2021.11.08
public class BmlPropAs{
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

	//지목(공부)
	private String bmlCde;

	//지목(현황)
	private String mokCde;

	//면적(공부)
	private Double area;

	//실면적
	private Double parea;

	//공시지가
	private Double olnlp;

	//(최초)취득면적
	private Double acqAra;

	//공유지분1
	private String cnrQta;

	//용도지역
	private String spfc;

	//도시계획지구
	private String ctyPlan;

	//계획시설
	private String planFty;

	//개발사업
	private String dwk;

	//계획사업
	private String planBns;

	//소유구분명
	private String ownNm;

	//재산용도명
	private String mesrvNm;

	//행정재산명
	private String amnNm;

	//회계구분명
	private String accNm;

	//재산관리관명
	private String manNm;

	//위임관리관코드명
	private String mndNm;

	//법정동명
	private String bjdNm;

	//통
	private String tong;

	//반
	private String ban;

	//지목명
	private String jimokNm;

	//실지목명
	private String rjimokNm;

	//공유지분2
	private Double cnrQta2;

	//처분일자
	private String dispoYmd;

	//처분방법
	private String dispoMet;

	//처분방법코드
	private String dispoCode;

	//처분사유
	private String dispoWhy;

	//취득부서코드
	private String gainDep;

	//취득방법구분코드
	private String giseCde;

	//새올인증여부
	private String seaollYn;

	//입력시스템
	private String insysNm;

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

	public String getBmlCde() {
		return bmlCde;
	}

	public void setBmlCde(String bmlCde) {
		this.bmlCde = bmlCde;
	}

	public String getMokCde() {
		return mokCde;
	}

	public void setMokCde(String mokCde) {
		this.mokCde = mokCde;
	}


	public String getCnrQta() {
		return cnrQta;
	}

	public void setCnrQta(String cnrQta) {
		this.cnrQta = cnrQta;
	}

	public String getSpfc() {
		return spfc;
	}

	public void setSpfc(String spfc) {
		this.spfc = spfc;
	}

	public String getCtyPlan() {
		return ctyPlan;
	}

	public void setCtyPlan(String ctyPlan) {
		this.ctyPlan = ctyPlan;
	}

	public String getPlanFty() {
		return planFty;
	}

	public void setPlanFty(String planFty) {
		this.planFty = planFty;
	}

	public String getDwk() {
		return dwk;
	}

	public void setDwk(String dwk) {
		this.dwk = dwk;
	}

	public String getPlanBns() {
		return planBns;
	}

	public void setPlanBns(String planBns) {
		this.planBns = planBns;
	}

	public String getOwnNm() {
		return ownNm;
	}

	public void setOwnNm(String ownNm) {
		this.ownNm = ownNm;
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

	public String getJimokNm() {
		return jimokNm;
	}

	public void setJimokNm(String jimokNm) {
		this.jimokNm = jimokNm;
	}

	public String getRjimokNm() {
		return rjimokNm;
	}

	public void setRjimokNm(String rjimokNm) {
		this.rjimokNm = rjimokNm;
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

	public String getDispoCode() {
		return dispoCode;
	}

	public void setDispoCode(String dispoCode) {
		this.dispoCode = dispoCode;
	}

	public String getDispoWhy() {
		return dispoWhy;
	}

	public void setDispoWhy(String dispoWhy) {
		this.dispoWhy = dispoWhy;
	}

	public String getGainDep() {
		return gainDep;
	}

	public void setGainDep(String gainDep) {
		this.gainDep = gainDep;
	}

	public String getGiseCde() {
		return giseCde;
	}

	public void setGiseCde(String giseCde) {
		this.giseCde = giseCde;
	}

	public String getSeaollYn() {
		return seaollYn;
	}

	public void setSeaollYn(String seaollYn) {
		this.seaollYn = seaollYn;
	}

	public String getInsysNm() {
		return insysNm;
	}

	public void setInsysNm(String insysNm) {
		this.insysNm = insysNm;
	}

	public Long getPrtIdn() {
		return prtIdn;
	}

	public void setPrtIdn(Long prtIdn) {
		this.prtIdn = prtIdn;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getParea() {
		return parea;
	}

	public void setParea(Double parea) {
		this.parea = parea;
	}

	public Double getOlnlp() {
		return olnlp;
	}

	public void setOlnlp(Double olnlp) {
		this.olnlp = olnlp;
	}

	public Double getAcqAra() {
		return acqAra;
	}

	public void setAcqAra(Double acqAra) {
		this.acqAra = acqAra;
	}

	public Double getCnrQta2() {
		return cnrQta2;
	}

	public void setCnrQta2(Double cnrQta2) {
		this.cnrQta2 = cnrQta2;
	}
}
