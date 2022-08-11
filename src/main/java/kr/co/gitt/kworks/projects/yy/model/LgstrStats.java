package kr.co.gitt.kworks.projects.yy.model;

public class LgstrStats {
	
	// 지적통계 번호
	private Long lgstrStatsNo;
		
	// 지적통계 제목
	private String lgstrStatsSj;
	
	// 지적통계 내용
	private String lgstrStatsCn;
		
	// 작성자 아이디
	private String wrterId;
		
	// 첨부파일
	private LgstrStatsFile lgstrStatsFile;
	

	public Long getLgstrStatsNo() {
		return lgstrStatsNo;
	}

	public void setLgstrStatsNo(Long lgstrStatsNo) {
		this.lgstrStatsNo = lgstrStatsNo;
	}

	public String getLgstrStatsSj() {
		return lgstrStatsSj;
	}

	public void setLgstrStatsSj(String lgstrStatsSj) {
		this.lgstrStatsSj = lgstrStatsSj;
	}

	public String getLgstrStatsCn() {
		return lgstrStatsCn;
	}

	public void setLgstrStatsCn(String lgstrStatsCn) {
		this.lgstrStatsCn = lgstrStatsCn;
	}

	public String getWrterId() {
		return wrterId;
	}

	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}

	public LgstrStatsFile getLgstrStatsFile() {
		return lgstrStatsFile;
	}

	public void setLgstrStatsFile(LgstrStatsFile lgstrStatsFile) {
		this.lgstrStatsFile = lgstrStatsFile;
	}
}
