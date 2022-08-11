package kr.co.gitt.kworks.projects.dh.model;


//대부내역
//조원기
//2021.11.08
public class BmlLoanAs{

	//재산번호
	private Long prtIdn;

	//대부 및 사용허가 일련번호
	private Long lonIdn;

	//재산종류
	private String pbpKnd;

	//물건지주소
	private String thgAdr;

	//계약일
	private String crtDate;

	//해지일자
	private String tntDate;

	//해지사유
	private String tntResn;

	//계약시작일
	private String crtStr;

	//계약종료일
	private String crtEnd;

	//대부(허가)면적 1
	private Double lonArea;

	//대부(허가)면적 2
	private Double slonArea;

	//대부(허가)목적
	private String lonPup;

	//성명/명칭
	private String empNam;

	//재산코드명
	private String pbpKndnm;

	//재산소유구분
	private String ownCde;

	//소유구분명
	private String ownNm;

	//재산관리관
	private String manCde;

	//재산관리관명
	private String manNm;

	//재산용도
	private String prsCde;

	//재산용도명
	private String mesrvNm;

	//행정재산코드
	private String amnCde;

	//행정재산명
	private String amnNm;

	//회계구분
	private String accCde;

	//회계구분명
	private String accNm;

	//법정동코드
	private String bjdCde;

	//법정동
	private String bjdNm;

	//산코드
	private String monut;

	//번지
	private String bun;

	//호
	private String ho;

	//대부허가요금
	private Long perAmt;

	//대부허가기간 시작일자
	private String lonStart;

	//대부허가기간 종료일자
	private String lonEnd;

	//대부허가일수
	private Long lonDay;

	//건물대부면적
	private Double blLonAr;

	//건물바닥면적
	private Double blBdAr;

	//사용층
	private String useFlr;

	//사용자구분
	private String empSe;

	//사용자구분명
	private String usrSeNm;

	//사용자법정동코드
	private String usrbjdCd;

	//사용자법정동명
	private String usrbjdNm;

	//사용자산
	private String usrSan;

	//사용자번지
	private String usrBunji;

	//사용자호
	private String usrHo;

	//사용자주소
	private String empBjd;

	//사용자도로명주소
	private String empRn;

	//사용자연락처
	private String empNum;

	public Long getPrtIdn() {
		return prtIdn;
	}

	public void setPrtIdn(Long prtIdn) {
		this.prtIdn = prtIdn;
	}

	public Long getLonIdn() {
		return lonIdn;
	}

	public void setLonIdn(Long lonIdn) {
		this.lonIdn = lonIdn;
	}

	public String getPbpKnd() {
		return pbpKnd;
	}

	public void setPbpKnd(String pbpKnd) {
		this.pbpKnd = pbpKnd;
	}

	public String getThgAdr() {
		return thgAdr;
	}

	public void setThgAdr(String thgAdr) {
		this.thgAdr = thgAdr;
	}

	public String getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}

	public String getTntDate() {
		return tntDate;
	}

	public void setTntDate(String tntDate) {
		this.tntDate = tntDate;
	}

	public String getTntResn() {
		return tntResn;
	}

	public void setTntResn(String tntResn) {
		this.tntResn = tntResn;
	}

	public String getCrtStr() {
		return crtStr;
	}

	public void setCrtStr(String crtStr) {
		this.crtStr = crtStr;
	}

	public String getCrtEnd() {
		return crtEnd;
	}

	public void setCrtEnd(String crtEnd) {
		this.crtEnd = crtEnd;
	}

	public Double getLonArea() {
		return lonArea;
	}

	public void setLonArea(Double lonArea) {
		this.lonArea = lonArea;
	}

	public Double getSlonArea() {
		return slonArea;
	}

	public void setSlonArea(Double slonArea) {
		this.slonArea = slonArea;
	}

	public String getLonPup() {
		return lonPup;
	}

	public void setLonPup(String lonPup) {
		this.lonPup = lonPup;
	}

	public String getEmpNam() {
		return empNam;
	}

	public void setEmpNam(String empNam) {
		this.empNam = empNam;
	}

	public String getPbpKndnm() {
		return pbpKndnm;
	}

	public void setPbpKndnm(String pbpKndnm) {
		this.pbpKndnm = pbpKndnm;
	}

	public String getOwnCde() {
		return ownCde;
	}

	public void setOwnCde(String ownCde) {
		this.ownCde = ownCde;
	}

	public String getOwnNm() {
		return ownNm;
	}

	public void setOwnNm(String ownNm) {
		this.ownNm = ownNm;
	}

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

	public String getPrsCde() {
		return prsCde;
	}

	public void setPrsCde(String prsCde) {
		this.prsCde = prsCde;
	}

	public String getMesrvNm() {
		return mesrvNm;
	}

	public void setMesrvNm(String mesrvNm) {
		this.mesrvNm = mesrvNm;
	}

	public String getAmnCde() {
		return amnCde;
	}

	public void setAmnCde(String amnCde) {
		this.amnCde = amnCde;
	}

	public String getAmnNm() {
		return amnNm;
	}

	public void setAmnNm(String amnNm) {
		this.amnNm = amnNm;
	}

	public String getAccCde() {
		return accCde;
	}

	public void setAccCde(String accCde) {
		this.accCde = accCde;
	}

	public String getAccNm() {
		return accNm;
	}

	public void setAccNm(String accNm) {
		this.accNm = accNm;
	}

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getBjdNm() {
		return bjdNm;
	}

	public void setBjdNm(String bjdNm) {
		this.bjdNm = bjdNm;
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

	public Long getPerAmt() {
		return perAmt;
	}

	public void setPerAmt(Long perAmt) {
		this.perAmt = perAmt;
	}

	public String getLonStart() {
		return lonStart;
	}

	public void setLonStart(String lonStart) {
		this.lonStart = lonStart;
	}

	public String getLonEnd() {
		return lonEnd;
	}

	public void setLonEnd(String lonEnd) {
		this.lonEnd = lonEnd;
	}

	public Long getLonDay() {
		return lonDay;
	}

	public void setLonDay(Long lonDay) {
		this.lonDay = lonDay;
	}

	public Double getBlLonAr() {
		return blLonAr;
	}

	public void setBlLonAr(Double blLonAr) {
		this.blLonAr = blLonAr;
	}

	public Double getBlBdAr() {
		return blBdAr;
	}

	public void setBlBdAr(Double blBdAr) {
		this.blBdAr = blBdAr;
	}

	public String getUseFlr() {
		return useFlr;
	}

	public void setUseFlr(String useFlr) {
		this.useFlr = useFlr;
	}

	public String getEmpSe() {
		return empSe;
	}

	public void setEmpSe(String empSe) {
		this.empSe = empSe;
	}

	public String getUsrSeNm() {
		return usrSeNm;
	}

	public void setUsrSeNm(String usrSeNm) {
		this.usrSeNm = usrSeNm;
	}

	public String getUsrbjdCd() {
		return usrbjdCd;
	}

	public void setUsrbjdCd(String usrbjdCd) {
		this.usrbjdCd = usrbjdCd;
	}

	public String getUsrbjdNm() {
		return usrbjdNm;
	}

	public void setUsrbjdNm(String usrbjdNm) {
		this.usrbjdNm = usrbjdNm;
	}

	public String getUsrSan() {
		return usrSan;
	}

	public void setUsrSan(String usrSan) {
		this.usrSan = usrSan;
	}

	public String getUsrBunji() {
		return usrBunji;
	}

	public void setUsrBunji(String usrBunji) {
		this.usrBunji = usrBunji;
	}

	public String getUsrHo() {
		return usrHo;
	}

	public void setUsrHo(String usrHo) {
		this.usrHo = usrHo;
	}

	public String getEmpBjd() {
		return empBjd;
	}

	public void setEmpBjd(String empBjd) {
		this.empBjd = empBjd;
	}

	public String getEmpRn() {
		return empRn;
	}

	public void setEmpRn(String empRn) {
		this.empRn = empRn;
	}

	public String getEmpNum() {
		return empNum;
	}

	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}
}
