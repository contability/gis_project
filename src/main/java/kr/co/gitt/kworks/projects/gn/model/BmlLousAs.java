package kr.co.gitt.kworks.projects.gn.model;


//대부 물건
//조원기
//2022.02.21
public class BmlLousAs{
	
	private Long objectid;

	//재산번호
	private Long prtIdn;

	//대부 및 사용허가 일련번호
	private Long lonIdn;

	//제목
	private String lonTit;
	
	//내용
	private String rmkExp;
	
	//등록날짜
	private String prtDat;

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

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

	public String getLonTit() {
		return lonTit;
	}

	public void setLonTit(String lonTit) {
		this.lonTit = lonTit;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}

	public String getPrtDat() {
		return prtDat;
	}

	public void setPrtDat(String prtDat) {
		this.prtDat = prtDat;
	}
	
	
	
	
}
