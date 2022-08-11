package kr.co.gitt.kworks.projects.gn.model;


//무단점유
//조원기
//2021.11.08
public class BmlOccpAs{
	//재산번호
	private Long prtIdn;

	//재산종류
	private String pbpKnd;

	//무단점사용 일련번호
	private Long ocpIdn;

	//물건지주소
	private String ocpAdr;

	//점유시작일
	private String ocpStr;

	//점유종료일
	private String ocpEnd;

	//무단점유면적
	private Double ocpAra;

	//귀속요율 – 시도분
	private Long rvrSi;

	//귀속요율 – 시군구분
	private Long rvrGu;

	//무단점유목적
	private String ocpPrs;

	//비고
	private String rmkExp;

	//사용자 구분
	private String empSe;

	//성명/명칭
	private String empNam;

	//연락처
	private String empNum;

	//사용자법정동주소
	private String empBjd;

	//사용자도로명주소
	private String empRn;

	//재산명
	private String meansNm;

	//법정동코드
	private String regnCode;

	//법정동명
	private String regnNm;

	//일반/산
	private String san;

	//번지
	private String bunji;

	//호
	private String ho;

	//통
	private String tong;

	//반
	private String ban;

	//점유목적코드
	private String pobjCode;

	//재산관리관코드
	private String manCde;

	//재산관리관
	private String manNm;

	//사용자구분명
	private String usrSeNm;

	//도로명주소
	private String rnAddr;

	//재산구분명
	private String pbpKndnm;

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

	public Long getPrtIdn() {
		return prtIdn;
	}

	public void setPrtIdn(Long prtIdn) {
		this.prtIdn = prtIdn;
	}

	public String getPbpKnd() {
		return pbpKnd;
	}

	public void setPbpKnd(String pbpKnd) {
		this.pbpKnd = pbpKnd;
	}

	public Long getOcpIdn() {
		return ocpIdn;
	}

	public void setOcpIdn(Long ocpIdn) {
		this.ocpIdn = ocpIdn;
	}

	public String getOcpAdr() {
		return ocpAdr;
	}

	public void setOcpAdr(String ocpAdr) {
		this.ocpAdr = ocpAdr;
	}

	public String getOcpStr() {
		return ocpStr;
	}

	public void setOcpStr(String ocpStr) {
		this.ocpStr = ocpStr;
	}

	public String getOcpEnd() {
		return ocpEnd;
	}

	public void setOcpEnd(String ocpEnd) {
		this.ocpEnd = ocpEnd;
	}

	public Double getOcpAra() {
		return ocpAra;
	}

	public void setOcpAra(Double ocpAra) {
		this.ocpAra = ocpAra;
	}

	public Long getRvrSi() {
		return rvrSi;
	}

	public void setRvrSi(Long rvrSi) {
		this.rvrSi = rvrSi;
	}

	public Long getRvrGu() {
		return rvrGu;
	}

	public void setRvrGu(Long rvrGu) {
		this.rvrGu = rvrGu;
	}

	public String getOcpPrs() {
		return ocpPrs;
	}

	public void setOcpPrs(String ocpPrs) {
		this.ocpPrs = ocpPrs;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}

	public String getEmpSe() {
		return empSe;
	}

	public void setEmpSe(String empSe) {
		this.empSe = empSe;
	}

	public String getEmpNam() {
		return empNam;
	}

	public void setEmpNam(String empNam) {
		this.empNam = empNam;
	}

	public String getEmpNum() {
		return empNum;
	}

	public void setEmpNum(String empNum) {
		this.empNum = empNum;
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

	public String getMeansNm() {
		return meansNm;
	}

	public void setMeansNm(String meansNm) {
		this.meansNm = meansNm;
	}

	public String getRegnCode() {
		return regnCode;
	}

	public void setRegnCode(String regnCode) {
		this.regnCode = regnCode;
	}

	public String getRegnNm() {
		return regnNm;
	}

	public void setRegnNm(String regnNm) {
		this.regnNm = regnNm;
	}

	public String getSan() {
		return san;
	}

	public void setSan(String san) {
		this.san = san;
	}

	public String getBunji() {
		return bunji;
	}

	public void setBunji(String bunji) {
		this.bunji = bunji;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
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

	public String getPobjCode() {
		return pobjCode;
	}

	public void setPobjCode(String pobjCode) {
		this.pobjCode = pobjCode;
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

	public String getUsrSeNm() {
		return usrSeNm;
	}

	public void setUsrSeNm(String usrSeNm) {
		this.usrSeNm = usrSeNm;
	}

	public String getRnAddr() {
		return rnAddr;
	}

	public void setRnAddr(String rnAddr) {
		this.rnAddr = rnAddr;
	}

	public String getPbpKndnm() {
		return pbpKndnm;
	}

	public void setPbpKndnm(String pbpKndnm) {
		this.pbpKndnm = pbpKndnm;
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
}
