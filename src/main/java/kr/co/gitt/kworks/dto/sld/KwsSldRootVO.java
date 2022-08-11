package kr.co.gitt.kworks.dto.sld;

public class KwsSldRootVO {
	private String name;
	private String title;
	private KwsNamedLayersVO[] namedLayers;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public KwsNamedLayersVO[] getNamedLayers() {
		return namedLayers;
	}
	public void setNamedLayers(KwsNamedLayersVO[] namedLayers) {
		this.namedLayers = namedLayers;
	}
}
