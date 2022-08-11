package kr.co.gitt.kworks.model;

import java.util.List;


/////////////////////////////////////////////
/// @class KwsSys
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsSys.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 9. 오후 4:08:24 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 시스템 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsSys {
	
	/////////////////////////////////////////////
	/// @class SysTy
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsSys.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 9. 30. 오후 2:49:17 |
	///    | Class Version | v1.0 |
	///    | 작업자 | libraleo, Others... |
	/// @section 상세설명
	/// - 이 클래스는 시스템 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum SysTy {
		SYSTEM, USER
	}

	/// 시스템 아이디
	private Long sysId;
	
	/// 시스템 명
	private String sysNm;
	
	/// 시스템 타입
	private SysTy sysTy;
	
	/// 시스템 가명
	private String sysAlias;
	
	/// 시스템 설명
	private String sysDc;
	
	/// 주제도 아이디
	private Long themamapId;
	
	/// 시스템 데이터 카테고리 목록
	private List<KwsSysDataCtgry> kwsSysDataCtgrys;

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getSysNm() {
		return sysNm;
	}

	public void setSysNm(String sysNm) {
		this.sysNm = sysNm;
	}

	public SysTy getSysTy() {
		return sysTy;
	}

	public void setSysTy(SysTy sysTy) {
		this.sysTy = sysTy;
	}

	public String getSysAlias() {
		return sysAlias;
	}

	public void setSysAlias(String sysAlias) {
		this.sysAlias = sysAlias;
	}

	public String getSysDc() {
		return sysDc;
	}

	public void setSysDc(String sysDc) {
		this.sysDc = sysDc;
	}

	public Long getThemamapId() {
		return themamapId;
	}

	public void setThemamapId(Long themamapId) {
		this.themamapId = themamapId;
	}

	public List<KwsSysDataCtgry> getKwsSysDataCtgrys() {
		return kwsSysDataCtgrys;
	}

	public void setKwsSysDataCtgrys(List<KwsSysDataCtgry> kwsSysDataCtgrys) {
		this.kwsSysDataCtgrys = kwsSysDataCtgrys;
	}

}
