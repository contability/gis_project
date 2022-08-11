package kr.co.gitt.kworks.projects.yg.service.ladCntr;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.projects.yg.mappers.LdDownHiMapper;
import kr.co.gitt.kworks.projects.yg.model.LdDownHi;
import kr.co.gitt.kworks.projects.yg.model.LdlConsPs;
import kr.co.gitt.kworks.projects.yg.mappers.LdDocMaMapper;
import kr.co.gitt.kworks.projects.yg.mappers.LdUseMaMapper;
import kr.co.gitt.kworks.projects.yg.mappers.LdlConsPsMapper;
import kr.co.gitt.kworks.projects.yg.model.LdDocMa;
import kr.co.gitt.kworks.service.cmmn.AddressService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

@Service("landCenterUseDownLogService")
@Profile({"yg_dev", "yg_oper"})
public class LandCenterUseDownLogServiceImpl implements LandCenterUseDownLogService{
	
	@Resource
	LdDownHiMapper ldDownHiMapper;
	
	// 토지사용증명서 다운로드 관리 시퀀스
	@Resource
	EgovIdGnrService ldDownHiIdGnrService;
	
	// 토지중심공사대장 맵퍼
	@Resource
	LdlConsPsMapper ldlConsPsMapper;
	
	// 토지사용정보대장 맵퍼
	@Resource
	LdUseMaMapper ldUseMaMapper;
	
	// 토지사용증명서 파일 관리 맵퍼
	@Resource
	LdDocMaMapper ldDocMaMapper;
	
	// 주소 서비스
	@Resource
	AddressService addressService;
	
	public Integer listCount(SearchDTO searchDTO) {
		return ldDownHiMapper.listCount(searchDTO);
	}
	
	public List<LdDownHi> listSearch(SearchDTO searchDTO) {
		return ldDownHiMapper.list(searchDTO);
	}
	
	public Integer insertLog(LdDocMa ldDocMa) throws Exception {
		Integer rowCount = 0;
		
		LdDownHi ldDownHi = new LdDownHi();
		
		// 순번
		Long hisNo = ldDownHiIdGnrService.getNextLongId();
		ldDownHi.setHisNo(hisNo);
		
		// 다운로드 일시
		String timeStr 	= null;
		String timePattern = "yyyyMMdd";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(timePattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		timeStr = sdfCurrent.format(ts.getTime());
		ldDownHi.setDwYmd(timeStr);
		
		// 공사명
		List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);
		Long cntIdn = ldDocMaData.get(0).getCntIdn();
		
		LdlConsPs ldConsMaData = ldlConsPsMapper.cntSelectOne(cntIdn);
		String cntNam = ldConsMaData.getCntNam();
		ldDownHi.setCntNam(cntNam);
		
		// 공사번호
		ldDownHi.setCntIdn(ldConsMaData.getCntIdn());
		
		// 사용증명서 번호
		ldDownHi.setLuiIdn(ldDocMaData.get(0).getLuiIdn());
		
		// 위치 -- 생략 양구는 cntLoc 컬럼없음
		/*String pnu = ldDocMa.getPnu();
		String cntLoc = addressService.getFullAddress("BML_BADM_AS", "LP_PA_CBND", pnu);
		ldDownHi.setCntLoc(cntLoc);*/
		
		// 증명서 구분
		ldDownHi.setDocCde(ldDocMa.getDocCde());
		
		// 열람자
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ldDownHi.setUserId(userDTO.getUserId());
		
		// 열람자 소속
		ldDownHi.setDeptCde(userDTO.getDeptCode());
		
		ldDownHiMapper.insert(ldDownHi);
		
		return rowCount;
	}
	
}