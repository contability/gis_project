package kr.co.gitt.kworks.model;

import java.sql.Date;

/////////////////////////////////////////////
/// @class KwsAnswer
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsAnswer.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 12. 오전 9:24:57 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 묻고답하기 답변게시판 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsAnswer {
	
	/// 답변 번호
	private Long answerNo;
	
	/// 질문 번호
	private Long qestnNo;
	
	/// 답변 내용	
	private String answerCn;
	
	/// 작성자 아이디
	private String wrterId;
	
	/// 최초 등록 일
	private Date frstRgsde;
	
	/// 최종수정일
	private Date lastUpdde;
	
	/// 수정자 아이디
	private String updUsrid;

	public Long getAnswerNo() {
		return answerNo;
	}

	public void setAnswerNo(Long answerNo) {
		this.answerNo = answerNo;
	}

	public Long getQestnNo() {
		return qestnNo;
	}

	public void setQestnNo(Long qestnNo) {
		this.qestnNo = qestnNo;
	}

	public String getAnswerCn() {
		return answerCn;
	}

	public void setAnswerCn(String answerCn) {
		this.answerCn = answerCn;
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

	public Date getLastUpdde() {
		return lastUpdde;
	}

	public void setLastUpdde(Date lastUpdde) {
		this.lastUpdde = lastUpdde;
	}

	public String getUpdUsrid() {
		return updUsrid;
	}

	public void setUpdUsrid(String updUsrid) {
		this.updUsrid = updUsrid;
	}

}
