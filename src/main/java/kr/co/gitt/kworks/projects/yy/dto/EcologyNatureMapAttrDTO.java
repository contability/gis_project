package kr.co.gitt.kworks.projects.yy.dto;

import java.util.List;
import java.util.Set;

import kr.co.gitt.kworks.model.KwsDomnCode;

public class EcologyNatureMapAttrDTO {

	/// 도엽번호
	private List<String> shtNums;
	
	/// 지목
	private String jimokNm;
	
	/// 소유자명
	private String ownerNm;
	
	/// 전체 면적
	private double parea;
	
	/// 1등급 편입 면적
	private double grade1Area;
	
	/// 2등급 편입	면적
	private double grade2Area;
	
	/// 3등급 편입 면적
	private double grade3;
	
	/// 별도 편입 면적
	private double etc;
	
	/// 편입 면적 합 
	private double totalArea;
	
	/// 경사도
	private String grdnt;
	
	/// 고도
	private String sfhht;
	
	/// 경사향
	private String sltNgl;
	
	/// 방향
	private String sltDrc;
	
	/// 토지용도지역
	private List<KwsDomnCode> usePlans;
	
	/// 토지피복정보
	private Set<String> useCls;

	public List<String> getShtNums() {
		return shtNums;
	}

	public void setShtNums(List<String> shtNums) {
		this.shtNums = shtNums;
	}

	public String getJimokNm() {
		return jimokNm;
	}

	public void setJimokNm(String jimokNm) {
		this.jimokNm = jimokNm;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	public double getParea() {
		return parea;
	}

	public void setParea(double parea) {
		this.parea = parea;
	}

	public double getGrade1Area() {
		return grade1Area;
	}

	public void setGrade1Area(double grade1Area) {
		this.grade1Area = grade1Area;
	}

	public double getGrade2Area() {
		return grade2Area;
	}

	public void setGrade2Area(double grade2Area) {
		this.grade2Area = grade2Area;
	}

	public double getGrade3() {
		return grade3;
	}

	public void setGrade3(double grade3) {
		this.grade3 = grade3;
	}

	public double getEtc() {
		return etc;
	}

	public void setEtc(double etc) {
		this.etc = etc;
	}

	public double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(double totalArea) {
		this.totalArea = totalArea;
	}

	public String getGrdnt() {
		return grdnt;
	}

	public void setGrdnt(String grdnt) {
		this.grdnt = grdnt;
	}

	public String getSfhht() {
		return sfhht;
	}

	public void setSfhht(String sfhht) {
		this.sfhht = sfhht;
	}

	public String getSltNgl() {
		return sltNgl;
	}

	public void setSltNgl(String sltNgl) {
		this.sltNgl = sltNgl;
	}

	public String getSltDrc() {
		return sltDrc;
	}

	public void setSltDrc(String sltDrc) {
		this.sltDrc = sltDrc;
	}

	public List<KwsDomnCode> getUsePlans() {
		return usePlans;
	}

	public void setUsePlans(List<KwsDomnCode> usePlans) {
		this.usePlans = usePlans;
	}

	public Set<String> getUseCls() {
		return useCls;
	}

	public void setUseCls(Set<String> useCls) {
		this.useCls = useCls;
	}
	
}
