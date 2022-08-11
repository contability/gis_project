package kr.co.gitt.kworks.projects.gc.service.lossSttemnt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.projects.gc.dto.LossSttemntDTO;
import kr.co.gitt.kworks.projects.gc.mappers.EttLscpDtMapper;
import kr.co.gitt.kworks.projects.gc.model.EttLscpDt;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class LossSttemntServiceImpl
/// kr.co.gitt.kworks.projects.gc.service.lossSttemnt \n
///   ㄴ LossSttemntServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 16. 오전 11:28:08 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 망싫 신고 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("lossSttemntService")
@Profile({"gc_dev", "gc_oper"})
public class LossSttemntServiceImpl implements LossSttemntService {
	
	// 망실신고 맵퍼
	@Resource
	EttLscpDtMapper ettLscpDtMapper;
	
	// 망실신고 시퀀스 서비스
	@Resource
	EgovIdGnrService ettLscpDtIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gc.service.lossSttemnt.LossSttemntService#listLossSttemnt(kr.co.gitt.kworks.projects.gc.model.EttLscpDt)
	/////////////////////////////////////////////
	public List<EttLscpDt> listLossSttemnt(EttLscpDt ettLscpDt){
		return ettLscpDtMapper.list(ettLscpDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gc.service.lossSttemnt.LossSttemntService#selectOneLossSttemnt(kr.co.gitt.kworks.projects.gc.dto.LossSttemntDTO)
	/////////////////////////////////////////////
	public EttLscpDt selectOneLossSttemnt(LossSttemntDTO lossSttemntDTO){
		return ettLscpDtMapper.selectOne(lossSttemntDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gc.service.lossSttemnt.LossSttemntService#addLossSttemnt(kr.co.gitt.kworks.projects.gc.model.EttLscpDt)
	/////////////////////////////////////////////
	public Integer addLossSttemnt(EttLscpDt ettLscpDt) throws FdlException {
		String ftrCde = "ZA090";
		ettLscpDt.setFtrCde(ftrCde);

		Long shtIdn = ettLscpDtIdGnrService.getNextLongId();
		ettLscpDt.setShtIdn(shtIdn);

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ettLscpDt.setUsrDes(userDTO.getUserId());
		
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd", Locale.KOREA);
		Date today = new Date();
		String lprYmd = formatter.format(today);
		ettLscpDt.setLprYmd(lprYmd);
		
		Integer rowCount = ettLscpDtMapper.insert(ettLscpDt);
		return rowCount;
	}
	
}
