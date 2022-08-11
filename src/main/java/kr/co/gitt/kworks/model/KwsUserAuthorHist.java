package kr.co.gitt.kworks.model;

import java.util.Date;
import java.util.List;

import kr.co.gitt.kworks.dto.UserSearchDTO;

/////////////////////////////////////////////
/// @class KwsUserAuthorHist
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsUserAuthorHist.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 9. 오후 5:26:12 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 권한부여 이력 모델 클래스 입니다.
///   -# 
/// 이승재, 2021.09.23, 권한부여이력 수정에 따른 변경
/////////////////////////////////////////////
public class KwsUserAuthorHist extends UserSearchDTO {

	/// 사용자
	private String userId;
	
	/// 사용자 명
	private String userNm;
	
	/// 부서코드
	private String deptCode;
		
	/// 부서이름
	private String deptNm;
	
	/// 기존권한
	//private UserGrad oldGrad;
	
	/// 신규권한
	//private UserGrad newGrad;
	
	/// 권한부여자 
	private String gradUserId;
	
	/// 권한부여자 명
	private String gradUserNm;
	
	/// 권한부여일시
	private Date gradAlwncDt;
	
	/// 권한그룹명칭
	private String authorGroupNm;
	
	/// 처리내용
	private String prcrCn;
	
	/// 권한그룹
	private List<KwsAuthorGroup> kwsAuthorGroup;	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	/*
	public UserGrad getOldGrad() {
		return oldGrad;
	}

	public void setOldGrad(UserGrad oldGrad) {
		this.oldGrad = oldGrad;
	}

	public UserGrad getNewGrad() {
		return newGrad;
	}

	public void setNewGrad(UserGrad newGrad) {
		this.newGrad = newGrad;
	}
	*/

	public String getGradUserId() {
		return gradUserId;
	}

	/**
	 * @return the authorGroupNm
	 */
	public String getAuthorGroupNm() {
		return authorGroupNm;
	}

	/**
	 * @param authorGroupNm the authorGroupNm to set
	 */
	public void setAuthorGroupNm(String authorGroupNm) {
		this.authorGroupNm = authorGroupNm;
	}

	/**
	 * @return the prcrCn
	 */
	public String getPrcrCn() {
		return prcrCn;
	}

	/**
	 * @param prcrCn the prcrCn to set
	 */
	public void setPrcrCn(String prcrCn) {
		this.prcrCn = prcrCn;
	}

	public void setGradUserId(String gradUserId) {
		this.gradUserId = gradUserId;
	}
	
	public String getGradUserNm() {
		return gradUserNm;
	}

	public void setGradUserNm(String gradUserNm) {
		this.gradUserNm = gradUserNm;
	}

	public Date getGradAlwncDt() {
		return gradAlwncDt;
	}

	public void setGradAlwncDt(Date gradAlwncDt) {
		this.gradAlwncDt = gradAlwncDt;
	}

	public List<KwsAuthorGroup> getKwsAuthorGroup() {
		return kwsAuthorGroup;
	}

	public void setKwsAuthorGroup(List<KwsAuthorGroup> kwsAuthorGroup) {
		this.kwsAuthorGroup = kwsAuthorGroup;
	}

}
