package kr.co.gitt.kworks.service.log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsMenuConectLogMapper;
import kr.co.gitt.kworks.model.KwsMenuConectLog;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("menuConnectLogService")
public class MenuConnectLogServiceImpl implements MenuConnectLogService {

	@Resource
	KwsMenuConectLogMapper kwsMenuConectLogMapper;
	
	/// 메뉴 접속 로그 번호 생성 서비스
	@Resource
	EgovIdGnrService kwsMenuConectLogIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.MenuConnectLogService#insert(kr.co.gitt.kworks.model.KwsMenuConectLog)
	/////////////////////////////////////////////
	@Override
	public Integer insert(KwsMenuConectLog kwsMenuConectLog) throws FdlException {
		kwsMenuConectLog.setLogNo(kwsMenuConectLogIdGnrService.getNextLongId());
		Calendar calendar = Calendar.getInstance();
		kwsMenuConectLog.setDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
		kwsMenuConectLog.setConectDt(new Date(calendar.getTimeInMillis()));
		return kwsMenuConectLogMapper.insert(kwsMenuConectLog);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.MenuConnectLogService#listCount(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public Integer listCount(SearchDTO searchDTO) {
		return kwsMenuConectLogMapper.listCount(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.MenuConnectLogService#listSearch(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	public List<KwsMenuConectLog> listSearch(SearchDTO searchDTO) {
		return kwsMenuConectLogMapper.listSearch(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.log.MenuConnectLogService#listMenuLogExcel(kr.co.gitt.kworks.cmmn.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsMenuConectLog> listMenuLogExcel(SearchDTO searchDTO) {
		return kwsMenuConectLogMapper.listMenuLogExcel(searchDTO);
	}
	
}
