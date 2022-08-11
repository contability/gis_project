package kr.co.gitt.kworks.projects.gc.model;

import java.util.List;

/**
 * 재난현장 모바일 설정
 * @author kwangsu.lee
 *
 */
public class MobileConfig {
	
	/**
	 * 내보내기 폴더 목록
	 */
	private List<MobileFolder> folders;

	
	public List<MobileFolder> getFolders() {
		return folders;
	}

	public void setFolders(List<MobileFolder> folders) {
		this.folders = folders;
	}
}
