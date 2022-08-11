package kr.co.gitt.kworks.model;

import java.sql.Date;
import java.util.List;

/////////////////////////////////////////////
/// @class KwsRecsroom
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsRecsroom.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 5. 오후 3:34:53 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 자료실 모델입니다. 
///   -# 
/////////////////////////////////////////////
public class KwsRecsroom {
	
	/// 자료실 번호
	private Long recsroomNo;
	
	/// 자료실 제목
	private String recsroomSj;
	
	/// 자료실 내용	
	private String recsroomCn;
	
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
	private List<KwsRecsroomFile> kwsRecsroomFiles;
	
	
	public Long getRecsroomNo() {
		return recsroomNo;
	}

	public void setRecsroomNo(Long recsroomNo) {
		this.recsroomNo = recsroomNo;
	}

	public String getRecsroomSj() {
		return recsroomSj;
	}

	public void setRecsroomSj(String recsroomSj) {
		this.recsroomSj = recsroomSj;
	}

	public String getRecsroomCn() {
		return recsroomCn;
	}

	public void setRecsroomCn(String recsroomCn) {
		this.recsroomCn = recsroomCn;
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

	public List<KwsRecsroomFile> getKwsRecsroomFiles() {
		return kwsRecsroomFiles;
	}

	public void setKwsRecsroomFiles(List<KwsRecsroomFile> kwsRecsroomFiles) {
		this.kwsRecsroomFiles = kwsRecsroomFiles;
	}
	
}
