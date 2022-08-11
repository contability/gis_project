package kr.co.gitt.kworks.projects.gn.model;

import java.util.Date;

/////////////////////////////////////////////
/// @class MngUserAuthorHist
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ MngUserAuthorHist.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2021. 9. 24.     |
///    | Class Version | v1.0 |
///    | 작업자 |               |
/// @section 상세설명
/// - 이 클래스는 강릉시 사용자권한부여이력 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class MngUserAuthorHist {
	/// 일련번호
	private Long sn;
	
	/// 사용자ID
	private String userId;
	
	/// 사용자명칭
	private String userNm;
	
	/// 사용자부서코드
	private String deptCode;
	
	/// 사용자부서명칭
	private String deptNm;
	
	/// 권한부여자ID
	private String gradUserId;
	
	/// 권한부여자명칭
	private String gradUserNm;
	
	/// 권한부여일시
	private Date gradAlwncDt;
		
	/// 권한그룹명칭
	private String authorGroupNm;
		
	/// 처리내용
	private String prcrCn;
	
	/// 업무명
	private String jobNm;

	/**
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userNm
	 */
	public String getUserNm() {
		return userNm;
	}

	/**
	 * @param userNm the userNm to set
	 */
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the deptNm
	 */
	public String getDeptNm() {
		return deptNm;
	}

	/**
	 * @param deptNm the deptNm to set
	 */
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	/**
	 * @return the gradUserId
	 */
	public String getGradUserId() {
		return gradUserId;
	}

	/**
	 * @param gradUserId the gradUserId to set
	 */
	public void setGradUserId(String gradUserId) {
		this.gradUserId = gradUserId;
	}

	/**
	 * @return the gradUserNm
	 */
	public String getGradUserNm() {
		return gradUserNm;
	}

	/**
	 * @param gradUserNm the gradUserNm to set
	 */
	public void setGradUserNm(String gradUserNm) {
		this.gradUserNm = gradUserNm;
	}

	/**
	 * @return the gradAlwncDt
	 */
	public Date getGradAlwncDt() {
		return gradAlwncDt;
	}

	/**
	 * @param gradAlwncDt the gradAlwncDt to set
	 */
	public void setGradAlwncDt(Date gradAlwncDt) {
		this.gradAlwncDt = gradAlwncDt;
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

	/**
	 * @return the jobNm
	 */
	public String getJobNm() {
		return jobNm;
	}

	/**
	 * @param jobNm the jobNm to set
	 */
	public void setJobNm(String jobNm) {
		this.jobNm = jobNm;
	}
}
