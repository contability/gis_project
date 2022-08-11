package kr.co.gitt.kworks.contact.kras.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import kr.co.gitt.kworks.cmmn.util.MessageUtils;

import org.apache.commons.lang3.StringUtils;

/////////////////////////////////////////////
/// @class LandInfo
/// kworks.itf.vo \n
///   ㄴ LandInfo.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:01:53 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '토지(임야) 대장' 서비스 응답 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class LandInfo {

	/// 행정구역코드
	private String admSectCd;
	
	/// 소재지코드
	private String landLocCd;
	
	/// 대장구분
	private String ledgGbn;
	
	/// 본번
	private String bobn;
	
	/// 부번 
	private String bubn;
	
	/// 지목코드
	private String jimok;
	
	/// 지목명
	private String jimokNm;
	
	/// 면적
	private double parea;
	
	/// 토지등급
	private String grd;
	
	/// 등급변동일자
	private String grdYmd;
	
	/// 이동사유코드
	private String landMovRsnCd;
	
	/// 이동사유명
	private String landMovRsnCdNm;
	
	/// 이동일자
	private String landMovYmd;
	
	/// 대장대조필구분
	private String ledgCntrstCnfGbn;
	
	/// 사업시행신고구분
	private String bizActNtcGbn;
	
	/// 도해 / 수치 구분
	private String mapGbn;
	
	/// 토지최종연혁순번
	private String landLastHistOdrno;
	
	/// 소유권최종연혁순번
	private String ownRgtLastHistOdrno;

	/// 소유자명
	private String ownerNm;

	/// 등록번호
	private String dregno;

	/// 소유구분코드
	private String ownGbn;

	/// 소유구분명
	private String ownGbnNm;

	/// 공유인수
	private Integer shrCnt;

	/// 주소
	private String ownerAddr;

	/// 변동원인코드
	private String ownRgtChgRsnCd;

	/// 변동원인명
	private String ownRgtChgRsnCdNm;

	/// 변동일자
	private String owndymd;

	/// 축척코드
	private String scale;

	/// 축척명
	private String scaleNm;

	/// 도호
	private String doho;

	/// 공시지가기준월
	private String jigaBaseMon;

	/// 공시지가
	private Integer pannJiga;

	/// 최종종번
	private String lastJibn;

	/// 본번의 최종 부번
	private String lastBu;

	/// 최종종번의 본번
	private String lastbobn;

	/// 최종종번의 부번
	private String lastBubn;

	/// 토지이동담당자ID
	private String landMovChrgManId;

	/// 소유권변동 담당자ID
	private String ownRgtChgChrgManId;

	/// 건물식별번호
	private Long bldgGbnNo;

	/// 관련지번
	private String landMoveRellJibn;

	@XmlElement(name = "ADM_SECT_CD")
	public String getAdmSectCd() {
		return admSectCd;
	}

	public void setAdmSectCd(String admSectCd) {
		this.admSectCd = admSectCd;
	}
	
	@XmlElement(name = "LAND_LOC_CD")
	public String getLandLocCd() {
		return landLocCd;
	}

	public void setLandLocCd(String landLocCd) {
		this.landLocCd = landLocCd;
	}

	@XmlElement(name = "LEDG_GBN")
	public String getLedgGbn() {
		return ledgGbn;
	}

	public void setLedgGbn(String ledgGbn) {
		this.ledgGbn = ledgGbn;
	}

	@XmlElement(name = "BOBN")
	public String getBobn() {
		return bobn;
	}

	public void setBobn(String bobn) {
		this.bobn = bobn;
	}

	@XmlElement(name = "BUBN")
	public String getBubn() {
		return bubn;
	}

	public void setBubn(String bubn) {
		this.bubn = bubn;
	}

	@XmlElement(name = "JIMOK")
	public String getJimok() {
		return jimok;
	}

	public void setJimok(String jimok) {
		this.jimok = jimok;
	}

	@XmlElement(name = "JIMOK_NM")
	public String getJimokNm() {
		return jimokNm;
	}

	public void setJimokNm(String jimokNm) {
		this.jimokNm = jimokNm;
	}

	@XmlElement(name = "PAREA")
	public double getParea() {
		return parea;
	}

	public void setParea(double parea) {
		this.parea = parea;
	}

	@XmlElement(name = "GRD")
	public String getGrd() {
		return grd;
	}

	public void setGrd(String grd) {
		this.grd = grd;
	}

	@XmlElement(name = "GRD_YMD")
	public String getGrdYmd() {
		return grdYmd;
	}

	public void setGrdYmd(String grdYmd) {
		this.grdYmd = grdYmd;
	}

	@XmlElement(name = "LAND_MOV_RSN_CD")
	public String getLandMovRsnCd() {
		return landMovRsnCd;
	}

	public void setLandMovRsnCd(String landMovRsnCd) {
		this.landMovRsnCd = landMovRsnCd;
	}

	@XmlElement(name = "LAND_MOV_RSN_CD_NM")
	public String getLandMovRsnCdNm() {
		return landMovRsnCdNm;
	}

	public void setLandMovRsnCdNm(String landMovRsnCdNm) {
		this.landMovRsnCdNm = landMovRsnCdNm;
	}

	@XmlElement(name = "LAND_MOV_YMD")
	public String getLandMovYmd() {
		return landMovYmd;
	}

	public void setLandMovYmd(String landMovYmd) {
		this.landMovYmd = landMovYmd;
	}

	@XmlElement(name = "LEDG_CNTRST_CNF_GBN")
	public String getLedgCntrstCnfGbn() {
		return ledgCntrstCnfGbn;
	}

	public void setLedgCntrstCnfGbn(String ledgCntrstCnfGbn) {
		this.ledgCntrstCnfGbn = ledgCntrstCnfGbn;
	}

	@XmlElement(name = "BIZ_ACT_NTC_GBN")
	public String getBizActNtcGbn() {
		return bizActNtcGbn;
	}

	public void setBizActNtcGbn(String bizActNtcGbn) {
		this.bizActNtcGbn = bizActNtcGbn;
	}

	@XmlElement(name = "MAP_GBN")
	public String getMapGbn() {
		return mapGbn;
	}

	public void setMapGbn(String mapGbn) {
		this.mapGbn = mapGbn;
	}

	@XmlElement(name = "LAND_LAST_HIST_ODRNO")
	public String getLandLastHistOdrno() {
		return landLastHistOdrno;
	}

	public void setLandLastHistOdrno(String landLastHistOdrno) {
		this.landLastHistOdrno = landLastHistOdrno;
	}

	@XmlElement(name = "OWN_RGT_LAST_HIST_ODRNO")
	public String getOwnRgtLastHistOdrno() {
		return ownRgtLastHistOdrno;
	}

	public void setOwnRgtLastHistOdrno(String ownRgtLastHistOdrno) {
		this.ownRgtLastHistOdrno = ownRgtLastHistOdrno;
	}

	@XmlElement(name = "OWNER_NM")
	public String getOwnerNm() {
		String isNameMasking = MessageUtils.getMessage("Contact.Kras.isNameMasking");
		if(StringUtils.equals(isNameMasking, "true")) {
			List<String> prevents = new ArrayList<String>();
			prevents.add("02");
			prevents.add("04");
			prevents.add("05");
			if(StringUtils.isNotBlank(ownGbn) && !prevents.contains(ownGbn)) {
				int len = ownerNm.length();
				if(len == 2) {
					return ownerNm.charAt(0) + "*";
				}
				else if(len > 2) {
					return ownerNm.charAt(0) + ownerNm.substring(1, len-1).replaceAll(".", "*") + ownerNm.charAt(len-1);
				}
				else {
					return ownerNm;
				}
			}
			else {
				return ownerNm;
			}
		}
		else {
			return ownerNm;
		}
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	@XmlElement(name = "DREGNO")
	public String getDregno() {
		return dregno;
	}

	public void setDregno(String dregno) {
		this.dregno = dregno;
	}

	@XmlElement(name = "OWN_GBN")
	public String getOwnGbn() {
		return ownGbn;
	}

	public void setOwnGbn(String ownGbn) {
		this.ownGbn = ownGbn;
	}

	@XmlElement(name = "OWN_GBN_NM")
	public String getOwnGbnNm() {
		return ownGbnNm;
	}

	public void setOwnGbnNm(String ownGbnNm) {
		this.ownGbnNm = ownGbnNm;
	}

	@XmlElement(name = "SHR_CNT")
	public Integer getShrCnt() {
		return shrCnt;
	}

	public void setShrCnt(Integer shrCnt) {
		this.shrCnt = shrCnt;
	}

	@XmlElement(name = "OWNER_ADDR")
	public String getOwnerAddr() {
		return ownerAddr;
	}

	public void setOwnerAddr(String ownerAddr) {
		this.ownerAddr = ownerAddr;
	}

	@XmlElement(name = "OWN_RGT_CHG_RSN_CD")
	public String getOwnRgtChgRsnCd() {
		return ownRgtChgRsnCd;
	}

	public void setOwnRgtChgRsnCd(String ownRgtChgRsnCd) {
		this.ownRgtChgRsnCd = ownRgtChgRsnCd;
	}

	@XmlElement(name = "OWN_RGT_CHG_RSN_CD_NM")
	public String getOwnRgtChgRsnCdNm() {
		return ownRgtChgRsnCdNm;
	}

	public void setOwnRgtChgRsnCdNm(String ownRgtChgRsnCdNm) {
		this.ownRgtChgRsnCdNm = ownRgtChgRsnCdNm;
	}

	@XmlElement(name = "OWNDYMD")
	public String getOwndymd() {
		return owndymd;
	}

	public void setOwndymd(String owndymd) {
		this.owndymd = owndymd;
	}

	@XmlElement(name = "SCALE")
	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	@XmlElement(name = "SCALE_NM")
	public String getScaleNm() {
		return scaleNm;
	}

	public void setScaleNm(String scaleNm) {
		this.scaleNm = scaleNm;
	}

	@XmlElement(name = "DOHO")
	public String getDoho() {
		return doho;
	}

	public void setDoho(String doho) {
		this.doho = doho;
	}

	@XmlElement(name = "JIGA_BASE_MON")
	public String getJigaBaseMon() {
		return jigaBaseMon;
	}

	public void setJigaBaseMon(String jigaBaseMon) {
		this.jigaBaseMon = jigaBaseMon;
	}

	@XmlElement(name = "PANN_JIGA")
	public Integer getPannJiga() {
		return pannJiga;
	}

	public void setPannJiga(Integer pannJiga) {
		this.pannJiga = pannJiga;
	}

	@XmlElement(name = "LAST_JIBN")
	public String getLastJibn() {
		return lastJibn;
	}

	public void setLastJibn(String lastJibn) {
		this.lastJibn = lastJibn;
	}

	@XmlElement(name = "LAST_BU")
	public String getLastBu() {
		return lastBu;
	}

	public void setLastBu(String lastBu) {
		this.lastBu = lastBu;
	}

	@XmlElement(name = "LASTBOBN")
	public String getLastbobn() {
		return lastbobn;
	}

	public void setLastbobn(String lastbobn) {
		this.lastbobn = lastbobn;
	}

	@XmlElement(name = "LASTBUBN")
	public String getLastBubn() {
		return lastBubn;
	}

	public void setLastBubn(String lastBubn) {
		this.lastBubn = lastBubn;
	}

	@XmlElement(name = "LAND_MOV_CHRG_MAN_ID")
	public String getLandMovChrgManId() {
		return landMovChrgManId;
	}

	public void setLandMovChrgManId(String landMovChrgManId) {
		this.landMovChrgManId = landMovChrgManId;
	}

	@XmlElement(name = "OWN_RGT_CHG_CHRG_MAN_ID")
	public String getOwnRgtChgChrgManId() {
		return ownRgtChgChrgManId;
	}

	public void setOwnRgtChgChrgManId(String ownRgtChgChrgManId) {
		this.ownRgtChgChrgManId = ownRgtChgChrgManId;
	}

	@XmlElement(name = "BLDG_GBN_NO")
	public Long getBldgGbnNo() {
		return bldgGbnNo;
	}

	public void setBldgGbnNo(Long bldgGbnNo) {
		this.bldgGbnNo = bldgGbnNo;
	}

	@XmlElement(name = "LAND_MOVE_RELL_JIBN")
	public String getLandMoveRellJibn() {
		return landMoveRellJibn;
	}

	public void setLandMoveRellJibn(String landMoveRellJibn) {
		this.landMoveRellJibn = landMoveRellJibn;
	}
	
}
