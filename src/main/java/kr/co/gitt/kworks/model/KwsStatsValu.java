package kr.co.gitt.kworks.model;

public class KwsStatsValu {
	//통계항목 ID
	private Long iemNo;
	
	//행정구역 코드
	private String administzone;
	
	//통계값 
	private Double valu;
	
	public Long getIemNo() {
		return iemNo;
	}
	public void setIemNo(Long iemNo) {
		this.iemNo = iemNo;
	}
	public String getAdministzone() {
		return administzone;
	}
	public void setAdministzone(String administzone) {
		this.administzone = administzone;
	}
	public Double getValu() {
		return valu;
	}
	public void setValu(Double valu) {
		this.valu = valu;
	}
	
	
}
