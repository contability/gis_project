package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class LpPaCbnd
/// kr.co.gitt.kworks.model \n
///   ㄴ LpPaCbnd.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 5. 11. 오전 10:46:49 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 연속지적도 모델입니다.
///   -# 
/////////////////////////////////////////////
public class LpPaCbnd {

	//발급여부
	private String bchk;
	
	//공시지가산정연도
	private String bsYea;
	
	//GEOMETRY
	private String geom;
	
	//지번
	private String jibun;
	
	//지목
	private String jimok;
	
	//ID
	private Long objectid; 
	
	//소유구분
	private String ownGbn;
	
	//필지면적
	private String parea;
	
	//PJJI_YN
	private String pjjiYn;
	
	//공시지가
	private Long pnilp;
	
	//공시지가고시일자
	private String pnnYmd;
	
	//PNU코드
	private String pnu;
	
	//기준월
	private String stdmt;
	
	
	public String getOwnGbn() {
		return ownGbn;
	}

	public void setOwnGbn(String ownGbn) {
		this.ownGbn = ownGbn;
	}

	public String getBchk() {
		return bchk;
	}

	public void setBchk(String bchk) {
		this.bchk = bchk;
	}

	public String getBsYea() {
		return bsYea;
	}

	public void setBsYea(String bsYea) {
		this.bsYea = bsYea;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public String getJimok() {
		return jimok;
	}

	public void setJimok(String jimok) {
		this.jimok = jimok;
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getParea() {
		return parea;
	}

	public void setParea(String parea) {
		this.parea = parea;
	}

	public String getPjjiYn() {
		return pjjiYn;
	}

	public void setPjjiYn(String pjjiYn) {
		this.pjjiYn = pjjiYn;
	}

	public Long getPnilp() {
		return pnilp;
	}

	public void setPnilp(Long pnilp) {
		this.pnilp = pnilp;
	}

	public String getPnnYmd() {
		return pnnYmd;
	}

	public void setPnnYmd(String pnnYmd) {
		this.pnnYmd = pnnYmd;
	}

	public String getStdmt() {
		return stdmt;
	}

	public void setStdmt(String stdmt) {
		this.stdmt = stdmt;
	}

	public String getPnu() {
		return pnu;
	}

	public void setPnu(String pnu) {
		this.pnu = pnu;
	}

	public String getJibun() {
		return jibun;
	}

	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	
}
