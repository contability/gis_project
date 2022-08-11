package kr.co.gitt.kworks.projects.yy.model;

import kr.co.gitt.kworks.model.KwsFile;

public class LgstrStatsFile {
	
	// 지적통계 번호
	private Long lgstrStatsNo;
	
	// 파일 번호
	private Long fileNo;
	
	// 파일
	private KwsFile kwsFile;

	public Long getLgstrStatsNo() {
		return lgstrStatsNo;
	}

	public void setLgstrStatsNo(Long lgstrStatsNo) {
		this.lgstrStatsNo = lgstrStatsNo;
	}

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}

	public KwsFile getKwsFile() {
		return kwsFile;
	}

	public void setKwsFile(KwsFile kwsFile) {
		this.kwsFile = kwsFile;
	}
}
