package kr.co.gitt.kworks.model;

import java.util.Date;

/////////////////////////////////////////////
/// @class KwsCntcBackupData
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsCntcBackupData.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 19. 오후 2:28:50 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 연계 백업 데이터 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsCntcBackupData {

	/// 백업 아이디
	private Long backupId;
	
	/// 데이터 아이디
	private String dataId;
	
	/// 백업 일
	private Date backupDe;
	
	/// 백업 데이터 아이디
	private String backupDataId;
	
	/// 백업 데이터 수
	private Long backupDataCo;
	
	/// 백업 데이터 경로
	private String backupDataPath;

	public Long getBackupId() {
		return backupId;
	}

	public void setBackupId(Long backupId) {
		this.backupId = backupId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Date getBackupDe() {
		return backupDe;
	}

	public void setBackupDe(Date backupDe) {
		this.backupDe = backupDe;
	}

	public String getBackupDataId() {
		return backupDataId;
	}

	public void setBackupDataId(String backupDataId) {
		this.backupDataId = backupDataId;
	}

	public Long getBackupDataCo() {
		return backupDataCo;
	}

	public void setBackupDataCo(Long backupDataCo) {
		this.backupDataCo = backupDataCo;
	}

	public String getBackupDataPath() {
		return backupDataPath;
	}

	public void setBackupDataPath(String backupDataPath) {
		this.backupDataPath = backupDataPath;
	}
	
}
