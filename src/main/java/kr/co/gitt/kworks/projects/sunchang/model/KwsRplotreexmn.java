package kr.co.gitt.kworks.projects.sunchang.model;

import java.sql.Date;

//환지재조사
public class KwsRplotreexmn {
	
	//연번
	private Long rplotreexmnNo ;
		
	//사업지구명
	private String bsnsAreaNm ;
		
	//사업구분
	private String bsnsSe;
		
	//사업년도
	private Long bsnsRplotYr ;
		
	//정리일자
	private Date changeYmd ;
		
	//시행자
	private String enforcemen ;
		
	//소유자성명
	private String ownerNm ;
		
	//생년월일
	private Date ownerBrthdy ;
	
	//비고
	private String rmkExp ;

	public Long getRplotreexmnNo() {
		return rplotreexmnNo;
	}

	public void setRplotreexmnNo(Long rplotreexmnNo) {
		this.rplotreexmnNo = rplotreexmnNo;
	}

	public String getBsnsAreaNm() {
		return bsnsAreaNm;
	}

	public void setBsnsAreaNm(String bsnsAreaNm) {
		this.bsnsAreaNm = bsnsAreaNm;
	}

	public String getBsnsSe() {
		return bsnsSe;
	}

	public void setBsnsSe(String bsnsSe) {
		this.bsnsSe = bsnsSe;
	}

	public Long getBsnsRplotYr() {
		return bsnsRplotYr;
	}

	public void setBsnsRplotYr(Long bsnsRplotYr) {
		this.bsnsRplotYr = bsnsRplotYr;
	}

	public Date getChangeYmd() {
		return changeYmd;
	}

	public void setChangeYmd(Date changeYmd) {
		this.changeYmd = changeYmd;
	}

	public String getEnforcemen() {
		return enforcemen;
	}

	public void setEnforcemen(String enforcemen) {
		this.enforcemen = enforcemen;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	public Date getOwnerBrthdy() {
		return ownerBrthdy;
	}

	public void setOwnerBrthdy(Date ownerBrthdy) {
		this.ownerBrthdy = ownerBrthdy;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}
}
