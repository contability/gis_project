package kr.co.gitt.kworks.projects.gn.model;

/////////////////////////////////////////////
/// @class SwtSpmtMa
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ SwtSpmtMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 21. 오후 3:54:51 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
///    | 수정자 | 이승재, 2019.12.09
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class SwtSpmtMa {

	///지형지물부호
	private String ftrCde;
	
	///관리번호
	private Long ftrIdn;
	
	///신고일
	private String lprYmd;
	
	///건축주성명
	private String budNam;
	
	///대표지번 법정읍면동코드
	private String rpstBjdcd;
	
	///대표지번 산구분
	private String rpstMtlnb;
	
	///대표지번 본번
	private String rpstMnnm;
	
	/// 대표지번 부번
	private String rpstSlno;
	
	///건축물소재지
	private String bildLcplc;
	
	///건축연면적
	private double bldAra;
	
	///신청면적
	private double reqstAr;
	
	///하수배출량
	private double sdrVol;
	
	///민원종류코드
	private String pmsCde;
	
	///민원종류기타
	private String pmsEtc;
	
	///협의내용
	private String dscss;
	
	///협의내용기타
	private String dscssEtc;
	
	///건축물도로명주소
	private String bildRnadr;
	
	///건축용도
	private String bldUse;
	
	///시공업체
	private String makNam;
	
	///착공일자
	private String begYmd;
	
	///배수설비준공일자
	private String cntYmd;
	
	///관재질코드
	private String mopCde;
	
	///관재질기타
	private String mopEtc;
	
	///관경코드
	private String diaCde;
	
	///관경코드
	private double diaEtc;
	
	///연장
	private double pipLen;
	
	///접합방법코드
	private String cnMthCde;
	
	///접합방법기타
	private String cnMthEtc;
	
	///비고
	private String rmkExp;
	
	///인허가일자
	private String pmtYmd;
	
	///처리자성명
	private String proNam;
	
	///원인자부담금
	private double csAlotm;
	
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
	
	public String getLprYmd() {
		return lprYmd;
	}

	public void setLprYmd(String lprYmd) {
		this.lprYmd = lprYmd;
	}

	public String getBudNam() {
		return budNam;
	}

	public void setBudNam(String budNam) {
		this.budNam = budNam;
	}

	public String getRpstBjdcd() {
		return rpstBjdcd;
	}

	public void setRpstBjdcd(String rpstBjdcd) {
		this.rpstBjdcd = rpstBjdcd;
	}

	///대표지번 산구분
	public String getRpstMtlnb() {
		return rpstMtlnb;
	}

	public void setRpstMtlnb(String rpstMtlnb) {
		this.rpstMtlnb = rpstMtlnb;
	}

	///대표지번 본번
	public String getRpstMnnm() {
		return rpstMnnm;
	}

	public void setRpstMnnm(String rpstMnnm) {
		this.rpstMnnm = rpstMnnm;
	}

	///대표지번 부번
	public String getRpstSlno() {
		return rpstSlno;
	}

	public void setRpstSlno(String rpstSlno) {
		this.rpstSlno = rpstSlno;
	}

	///건축물소재지
	public String getBildLcplc() {
		return bildLcplc;
	}

	public void setBildLcplc(String bildLcplc) {
		this.bildLcplc = bildLcplc;
	}

	///건축연면적
	public double getBldAra() {
		return bldAra;
	}

	public void setBldAra(double bldAra) {
		this.bldAra = bldAra;
	}
	
	///신청면적
	public double getReqstAr() {
		return reqstAr;
	}

	public void setReqstAr(double reqstAr) {
		this.reqstAr = reqstAr;
	}
	
	///하수배출량
	public double getSdrVol() {
		return sdrVol;
	}

	public void setSdrVol(double sdrVol) {
		this.sdrVol = sdrVol;
	}
	
	///민원종류코드
	public String getPmsCde() {
		return pmsCde;
	}

	public void setPmsCde(String pmsCde) {
		this.pmsCde = pmsCde;
	}
	
	///민원종류기타
	public String getPmsEtc() {
		return pmsEtc;
	}

	public void setPmsEtc(String pmsEtc) {
		this.pmsEtc = pmsEtc;
	}
	
	///협의내용코드
	public String getDscss() {
		return dscss;
	}

	public void setDscss(String dscss) {
		this.dscss = dscss;
	}
		
	///협의내용기타
	public String getDscssEtc() {
		return dscssEtc;
	}

	public void setDscssEtc(String dscssEtc) {
		this.dscssEtc = dscssEtc;
	}
	
	///건축물도로명주소
	public String getBildRnadr() {
		return bildRnadr;
	}

	public void setBildRnadr(String bildRnadr) {
		this.bildRnadr = bildRnadr;
	}
	
	///건출용도
	public String getBldUse() {
		return bldUse;
	}

	public void setBldUse(String bldUse) {
		this.bldUse = bldUse;
	}
	
	///시공업체
	public String getMakNam() {
		return makNam;
	}

	public void setMakNam(String makNam) {
		this.makNam = makNam;
	}
	
	///착공일자
	public String getBegYmd() {
		return begYmd;
	}

	public void setBegYmd(String begYmd) {
		this.begYmd = begYmd;
	}

	///배수설비준공일자
	public String getCntYmd() {
		return cntYmd;
	}

	public void setCntYmd(String cntYmd) {
		this.cntYmd = cntYmd;
	}
	
	///관재질코드
	public String getMopCde() {
		return mopCde;
	}

	public void setMopCde(String mopCde) {
		this.mopCde = mopCde;
	}
	
	///관재질기타
	public String getMopEtc() {
		return mopEtc;
	}

	public void setMopEtc(String mopEtc) {
		this.mopEtc = mopEtc;
	}
	
	///관경코드
	public String getDiaCde() {
		return diaCde;
	}

	public void setDiaCde(String diaCde) {
		this.diaCde = diaCde;
	}
	
	///관경기타
	public double getDiaEtc() {
		return diaEtc;
	}

	public void setDiaEtc(double diaEtc) {
		this.diaEtc = diaEtc;
	}
	
	///연장
	public double getPipLen() {
		return pipLen;
	}

	public void setPipLen(double pipLen) {
		this.pipLen = pipLen;
	}
	
	///접합방법코드
	public String getCnMthCde() {
		return cnMthCde;
	}

	public void setCnMthCde(String cnMthCde) {
		this.cnMthCde = cnMthCde;
	}
	
	///접합방법기타
	public String getCnMthEtc() {
		return cnMthEtc;
	}

	public void setCnMthEtc(String cnMthEtc) {
		this.cnMthEtc = cnMthEtc;
	}
	
	///비고
	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}
	
	///인허가일자
	public String getPmtYmd() {
		return pmtYmd;
	}

	public void setPmtYmd(String pmtYmd) {
		this.pmtYmd = pmtYmd;
	}
	
	///처리자성명
	public String getProNam() {
		return proNam;
	}

	public void setProNam(String proNam) {
		this.proNam = proNam;
	}

	public double getCsAlotm() {
		return csAlotm;
	}

	public void setCsAlotm(double csAlotm) {
		this.csAlotm = csAlotm;
	}
}
