package kr.co.gitt.kworks.contact.kras.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class LandMovHistSet {

	/// 토지이동연혁 목록
	private List<LandMovHist> landMovHists;
	
	@XmlElement(name = "LAND_MOV_HIST")
	public List<LandMovHist> getLandMovHists() {
		return landMovHists;
	}

	public void setLandMovHists(List<LandMovHist> landMovHists) {
		this.landMovHists = landMovHists;
	}
	
}
