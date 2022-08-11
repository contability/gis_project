package kr.co.gitt.kworks.model;

import java.sql.Date;
import java.util.List;


/////////////////////////////////////////////
/// @class KwsNotice
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsNotice.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 1. 오전 10:16:08 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 공지사항 모델입니다.
///   -# 
/////////////////////////////////////////////
public class KwsNotice {
	
	/// 공지사항 번호
	private Long noticeNo;
	
	/// 공지사항 제목
	private String noticeSj;
	
	/// 공지사항 내용	
	private String noticeCn;
	
	/// 조회수
	private Integer rdCnt;
	
	/// 작성자 아이디
	private String wrterId;
	
	/// 최초 등록 일
	private Date frstRgsde;
	
	/// 수정자 아이디
	private String updusrId;
	
	/// 최종수정일
	private Date lastUpdde;
	
	// 첨부파일 목록 
	private List<KwsNoticeFile> kwsNoticeFiles;
	
	// 팝업 시작일
	private String popupStart;
	
	// 팝업 종료일
	private String popupEnd;
	

	public Long getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(Long noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeSj() {
		return noticeSj;
	}

	public void setNoticeSj(String noticeSj) {
		this.noticeSj = noticeSj;
	}

	public String getNoticeCn() {
		return noticeCn;
	}

	public void setNoticeCn(String noticeCn) {
		this.noticeCn = noticeCn;
	}

	public Integer getRdCnt() {
		return rdCnt;
	}

	public void setRdCnt(Integer rdCnt) {
		this.rdCnt = rdCnt;
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

	public List<KwsNoticeFile> getKwsNoticeFiles() {
		return kwsNoticeFiles;
	}

	public void setKwsNoticeFiles(List<KwsNoticeFile> kwsNoticeFiles) {
		this.kwsNoticeFiles = kwsNoticeFiles;
	}

	public String getPopupStart() {
		return popupStart;
	}

	public void setPopupStart(String popupStart) {
		this.popupStart = popupStart;
	}

	public String getPopupEnd() {
		return popupEnd;
	}

	public void setPopupEnd(String popupEnd) {
		this.popupEnd = popupEnd;
	}
}