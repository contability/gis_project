package kr.co.gitt.kworks.service.restore;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.CntcDataDTO;
import kr.co.gitt.kworks.model.KwsCntcBackupData;

/////////////////////////////////////////////
/// @class RestoreService
/// kr.co.gitt.kworks.service.restore \n
///   ㄴ RestoreService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 19. 오후 2:59:59 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 복원 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface RestoreService {
	
	/////////////////////////////////////////////
	/// @fn listData
	/// @brief 함수 간략한 설명 : 연계 데이터 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CntcDataDTO> listData();
	
	/////////////////////////////////////////////
	/// @fn listAllBackupData
	/// @brief 함수 간략한 설명 : 백업 데이터 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsCntcBackupData> listAllBackupData(String dataId);
	
	/////////////////////////////////////////////
	/// @fn restore
	/// @brief 함수 간략한 설명 : 복원
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param backupId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public long restore(Long backupId);
	
}
