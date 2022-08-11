package kr.co.gitt.kworks.service.log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsSysConectLogMapper;
import kr.co.gitt.kworks.model.KwsSysConectLog;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class SysConectLogServiceImpl
/// kr.co.gitt.kworks.service.log \n
///   ㄴ SysConectLogServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 9. 13. 오후 4:11:19 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 로그 통계 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("sysConectLogService")
public class SysConectLogServiceImpl implements SysConectLogService{
	
	/// 로그 매퍼
	@Resource
	KwsSysConectLogMapper kwsSysConectLogMapper;
	
	/// 시스템 접속 로그 번호 생성 서비스
	@Resource
	EgovIdGnrService kwsSysConectLogIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public Integer listCount(SearchDTO searchDTO) {
		return kwsSysConectLogMapper.listCount(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listSearch(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public List<KwsSysConectLog> listSearch(SearchDTO searchDTO) {
		return kwsSysConectLogMapper.listSearch(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listSys(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listSys(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listSys(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountGroupBySys(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listCountGroupBySys(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountGroupBySys(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountUser(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public Integer listCountUser(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountUser(searchDTO);
	}
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listUser(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listUser(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listUser(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountGroupByUser(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listCountGroupByUser(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountGroupByUser(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountDept(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public Integer listCountDept(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountDept(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listDept(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listDept(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listDept(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountGroupByDept(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listCountGroupByDept(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountGroupByDept(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listMonth(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listMonth(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listMonth(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn listCountMonth
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer listCountMonth(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountMonth(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountGroupByMonth(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listCountGroupByMonth(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountGroupByMonth(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listDay(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listDay(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listDay(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountGroupByDay(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listCountGroupByDay(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountGroupByDay(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn listCountDay
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@Override
	public Integer listCountDay(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountDay(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountGroupByWeekday(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listCountGroupByWeekday(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountGroupByWeekday(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountWeekday(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public Integer listCountWeekday(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountWeekday(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listWeekday(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listWeekday(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listWeekday(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listCountGroupByTime(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listCountGroupByTime(SearchDTO searchDTO){
		return kwsSysConectLogMapper.listCountGroupByTime(searchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#insertSystemConnectLog(kr.co.gitt.kworks.model.KwsSysConectLog)
	/////////////////////////////////////////////
	@Override
	public Integer insertSystemConnectLog(KwsSysConectLog kwsSysConectLog) throws FdlException {
		kwsSysConectLog.setLogNo(kwsSysConectLogIdGnrService.getNextLongId());
		Calendar calendar = Calendar.getInstance();
		kwsSysConectLog.setDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
		kwsSysConectLog.setConectDt(new Date(calendar.getTimeInMillis()));
		return kwsSysConectLogMapper.insert(kwsSysConectLog);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.SysConectLogService#listSysConectLogExcel(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsSysConectLog> listSysConectLogExcel(SearchDTO searchDTO) {
		return kwsSysConectLogMapper.listSysConectLogExcel(searchDTO);
	}
	
}
