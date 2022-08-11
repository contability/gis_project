package kr.co.gitt.kworks.service.restore;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.CntcDataDTO;
import kr.co.gitt.kworks.mappers.KwsCntcBackupDataMapper;
import kr.co.gitt.kworks.model.KwsCntcBackupData;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.repository.DataRestoreRepository;
import kr.co.gitt.kworks.service.data.DataService;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class RestoreServiceImpl
/// kr.co.gitt.kworks.service.restore \n
///   ㄴ RestoreServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 19. 오후 2:59:30 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 복원 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("restoreService")
public class RestoreServiceImpl implements RestoreService {

	/// 연계 데이터 백업 맵퍼
	@Resource
	KwsCntcBackupDataMapper kwsCntcBackupDataMapper;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 데이터 복원 저장소
	@Resource
	DataRestoreRepository dataRestoreRepository;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.restore.RestoreService#listData()
	/////////////////////////////////////////////
	@Override
	public List<CntcDataDTO> listData() {
		List<CntcDataDTO> list = new ArrayList<CntcDataDTO>();
		for(String dataId : kwsCntcBackupDataMapper.listDistinctDataId()) {
			CntcDataDTO cntcDataDTO = new CntcDataDTO();
			cntcDataDTO.setDataId(dataId);
			KwsData kwsData = dataService.selectOneData(dataId);
			cntcDataDTO.setDataAlias(kwsData.getDataAlias());
			cntcDataDTO.setDataCo(dataRestoreRepository.count(dataId));
			list.add(cntcDataDTO);
		}
		return list;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.restore.RestoreService#listAllBackupData(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public List<KwsCntcBackupData> listAllBackupData(String dataId) {
		return kwsCntcBackupDataMapper.listAll(dataId);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.restore.RestoreService#restore(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public long restore(Long backupId) {
		KwsCntcBackupData kwsCntcBackupData = kwsCntcBackupDataMapper.selectOne(backupId);
		String sourceDataId = kwsCntcBackupData.getBackupDataId();
		String targetDataId = kwsCntcBackupData.getDataId();
		return dataRestoreRepository.load(sourceDataId, targetDataId);
	}
	
}
