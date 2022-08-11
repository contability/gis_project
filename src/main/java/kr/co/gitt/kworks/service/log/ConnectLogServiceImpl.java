package kr.co.gitt.kworks.service.log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsConectLogMapper;
import kr.co.gitt.kworks.model.KwsConectLog;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class ConnectLogServiceImpl
/// kr.co.gitt.kworks.service.log \n
///   ㄴ ConnectLogServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오후 5:49:18 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 접속 로그 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("connectLogService")
public class ConnectLogServiceImpl implements ConnectLogService {
	
	/// 접속 로그 번호 생성 서비스
	@Resource
	EgovIdGnrService kwsConectLogIdGnrService;

	/// 접속 로그 맵퍼 입니다. 
	@Resource
	KwsConectLogMapper kwsConectLogMapper;

	@Override
	public Integer checkSession(String sessionId) {
		return kwsConectLogMapper.checkSession(sessionId);
	}
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.ConnectLogService#login(kr.co.gitt.kworks.model.KwsConectLog)
	/////////////////////////////////////////////
	@Override
	public Integer login(KwsConectLog kwsConectLog) throws FdlException {
		Integer rowCount = 0;
		kwsConectLog.setLogNo(kwsConectLogIdGnrService.getNextLongId());
		Calendar calendar = Calendar.getInstance();
		kwsConectLog.setLoginDt(new Date(calendar.getTimeInMillis()));
		kwsConectLog.setDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
		rowCount = kwsConectLogMapper.insert(kwsConectLog);
		return rowCount;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.ConnectLogService#logout(kr.co.gitt.kworks.model.KwsConectLog)
	/////////////////////////////////////////////
	@Override
	public Integer logout(KwsConectLog kwsConectLog) {
		Calendar calendar = Calendar.getInstance();
		kwsConectLog.setLogoutDt(new Date(calendar.getTimeInMillis()));
		return kwsConectLogMapper.update(kwsConectLog);
	}
	
	/////////////////////////////////////////////
	/// @fn \
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.ConnectLogService#listCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public Integer listCount(SearchDTO searchDTO) {
		return kwsConectLogMapper.listCount(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.ConnectLogService#listSearch(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsConectLog> listSearch(SearchDTO searchDTO) {
		return kwsConectLogMapper.listSearch(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.ConnectLogService#listConectLogExcel(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsConectLog> listConectLogExcel(SearchDTO searchDTO) {
		return kwsConectLogMapper.listConectLogExcel(searchDTO);
	}
	
}
