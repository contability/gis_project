package kr.co.gitt.kworks.model;

import java.sql.Date;
import java.util.List;

/////////////////////////////////////////////
/// @class KwsQestn
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsQestn.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 10. 오후 12:11:31 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 묻고답하기 질문게시판 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsQestn {
	
	/////////////////////////////////////////////
	/// @class QestnProgrsSttus
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsQestn.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 8. 20. 오전 10:23:57 |
	///    | Class Version | v1.0 |
	///    | 작업자 | libraleo, Others... |
	/// @section 상세설명
	/// - 이 클래스는 질문 상태 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum QestnProgrsSttus {

		ANSWER_WAITING("답변 중"),
		ANSWER_COMPLETION("답변 완료");
		
		/// 질문 진행 상태 값
		private String value;
		
		/////////////////////////////////////////////
		/// @fn 
		/// @brief 생성자 간략 설명 : 생성자
		/// @remark
		/// - 생성자 상세 설명 : 
		/// @param value 
		///~~~~~~~~~~~~~{.java}
		/// // 핵심코드
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		private QestnProgrsSttus(String value) {
			this.value = value;
		}
		
		/////////////////////////////////////////////
		/// @fn getValue
		/// @brief 함수 간략한 설명 : 질문 진행 상태 값 반환
		/// @remark
		/// - 함수의 상세 설명 : 
		/// @return 
		///
		///~~~~~~~~~~~~~{.java}
		/// // 핵심코드
		///~~~~~~~~~~~~~
		/////////////////////////////////////////////
		public String getValue() {
			return value;
		}
	}
	
	/// 게시물 번호
	private Long qestnNo;
	
	/// 게시물 제목
	private String qestnSj;
	
	/// 게시물 내용	
	private String qestnCn;
	
	/// 진행상태
	private QestnProgrsSttus progrsSttus;
	
	/// 작성자 아이디
	private String wrterId;
	
	/// 최초 등록 일
	private Date frstRgsde;
	
	/// 최종수정일
	private Date lastUpdde;
	
	/// 첨부파일 목록 
	private List<KwsQestnFile> kwsQestnFiles;
	
	/// 답변
	private KwsAnswer kwsAnswer;

	
	public Long getQestnNo() {
		return qestnNo;
	}

	public void setQestnNo(Long qestnNo) {
		this.qestnNo = qestnNo;
	}

	public String getQestnSj() {
		return qestnSj;
	}

	public void setQestnSj(String qestnSj) {
		this.qestnSj = qestnSj;
	}

	public String getQestnCn() {
		return qestnCn;
	}

	public void setQestnCn(String qestnCn) {
		this.qestnCn = qestnCn;
	}

	public QestnProgrsSttus getProgrsSttus() {
		return progrsSttus;
	}

	public void setProgrsSttus(QestnProgrsSttus progrsSttus) {
		this.progrsSttus = progrsSttus;
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

	public List<KwsQestnFile> getKwsQestnFiles() {
		return kwsQestnFiles;
	}

	public void setKwsQestnFiles(List<KwsQestnFile> kwsQestnFiles) {
		this.kwsQestnFiles = kwsQestnFiles;
	}

	public KwsAnswer getKwsAnswer() {
		return kwsAnswer;
	}

	public void setKwsAnswer(KwsAnswer kwsAnswer) {
		this.kwsAnswer = kwsAnswer;
	}

}
