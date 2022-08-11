package kr.co.gitt.kworks.projects.gc.model;

import java.util.List;

/**
 * 재난현장 모바일 폴더
 * @author kwangsu.lee
 *
 */
public class MobileFolder {

	/**
	 * 폴더명
	 */
	private String name;
	
	/**
	 * 레이어 목록
	 */
	private List<String> layers;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLayers() {
		return layers;
	}

	public void setLayers(List<String> layers) {
		this.layers = layers;
	}
}
