package kr.co.gitt.kworks.model;

import java.sql.Date;
import java.util.List;

/////////////////////////////////////////////
/// @class KwsThemamap
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsThemamap.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 10. 오후 4:48:53 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 주제도 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsThemamap {
	
	/////////////////////////////////////////////
	/// @class ThemamapTy
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsThemamap.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 9. 17. 오전 2:52:37 |
	///    | Class Version | v1.0 |
	///    | 작업자 | libraleo, Others... |
	/// @section 상세설명
	/// - 이 클래스는 
	///   -# 
	/////////////////////////////////////////////
	
	//2017.07.29 이승재
	//시스템 기본주제도 외 공융주제도 사용을 위한 PUBLIC 추가
	public enum ThemamapTy {
		XRAY, SYS, USER, PUBLIC
	}

	/// 주제도 아이디
	private Long themamapId;
	
	/// 주제도 명
	private String themamapNm;
	
	/// 주제도 설명
	private String themamapDc;
	
	/// 주제도 타입
	private ThemamapTy themamapTy;
	
	/// 작성자 아이디
	private String wrterId;
	
	/// 최초 등록 일
	private Date frstRgsde;
	
	/// 수정자 아이디
	private String updusrId;
	
	/// 최종수정일
	private Date lastUpdde;
	
	/// 주제도 레이어 목록
	private List<KwsThemamapLyr> kwsThemamapLyrs;
	
	/// 주제도 기본지도 목록
	private List<KwsThemamapBaseMap> kwsThemamapBaseMaps;
	
	// 시스템
	private List<KwsSys> kwsSys;

	public Long getThemamapId() {
		return themamapId;
	}

	public void setThemamapId(Long themamapId) {
		this.themamapId = themamapId;
	}

	public String getThemamapNm() {
		return themamapNm;
	}

	public void setThemamapNm(String themamapNm) {
		this.themamapNm = themamapNm;
	}

	public String getThemamapDc() {
		return themamapDc;
	}

	public void setThemamapDc(String themamapDc) {
		this.themamapDc = themamapDc;
	}

	public ThemamapTy getThemamapTy() {
		return themamapTy;
	}

	public void setThemamapTy(ThemamapTy themamapTy) {
		this.themamapTy = themamapTy;
	}

	public String getWrterId() {
		return wrterId;
	}

	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}

	public Date getFrstRgsde() {
		return frstRgsde;
	}

	public void setFrstRgsde(Date frstRgsde) {
		this.frstRgsde = frstRgsde;
	}

	public String getUpdusrId() {
		return updusrId;
	}

	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
	}

	public Date getLastUpdde() {
		return lastUpdde;
	}

	public void setLastUpdde(Date lastUpdde) {
		this.lastUpdde = lastUpdde;
	}

	public List<KwsThemamapLyr> getKwsThemamapLyrs() {
		return kwsThemamapLyrs;
	}

	public void setKwsThemamapLyrs(List<KwsThemamapLyr> kwsThemamapLyrs) {
		this.kwsThemamapLyrs = kwsThemamapLyrs;
	}

	public List<KwsSys> getKwsSys() {
		return kwsSys;
	}

	public void setKwsSys(List<KwsSys> kwsSys) {
		this.kwsSys = kwsSys;
	}

	public List<KwsThemamapBaseMap> getKwsThemamapBaseMaps() {
		return kwsThemamapBaseMaps;
	}

	public void setKwsThemamapBaseMaps(List<KwsThemamapBaseMap> kwsThemamapBaseMaps) {
		this.kwsThemamapBaseMaps = kwsThemamapBaseMaps;
	}

}
