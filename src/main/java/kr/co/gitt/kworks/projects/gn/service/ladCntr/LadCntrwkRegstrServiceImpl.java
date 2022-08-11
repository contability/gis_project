package kr.co.gitt.kworks.projects.gn.service.ladCntr;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.LpPaCbndMapper;
import kr.co.gitt.kworks.projects.gn.model.LedgerEditHi;
import kr.co.gitt.kworks.model.LpPaCbnd;
import kr.co.gitt.kworks.projects.gn.mappers.LedgerEditHiMapper;
import kr.co.gitt.kworks.projects.gn.mappers.LdConsMaMapper;
import kr.co.gitt.kworks.projects.gn.mappers.LdDocMaMapper;
import kr.co.gitt.kworks.projects.gn.mappers.LdUseMaMapper;
import kr.co.gitt.kworks.projects.gn.model.LdConsMa;
import kr.co.gitt.kworks.projects.gn.model.LdDocMa;
import kr.co.gitt.kworks.projects.gn.model.LdUseMa;
import kr.co.gitt.kworks.service.file.FileService;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class LadCntrwkRegstrServiceImpl
/// kr.co.gitt.kworks.service.ladCntr \n
///   ㄴ LadCntrwkRegstrServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 3. 27. 오후 6:20:54 
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 토지중심공사대장 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("ladCntrwkRegstrService")
@Profile({"gn_dev", "gn_oper"})
public class LadCntrwkRegstrServiceImpl implements LadCntrwkRegstrService {

	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "landCenter";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX;
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 토지중심공사대장 맵퍼
	@Resource
	LdConsMaMapper ldConsMaMapper;
	
	// 토지중심공사대장 대장편집 이력정보 맵퍼
	@Resource
	LedgerEditHiMapper ledgerEditHiMapper;
	
	// 토지사용정보대장 맵퍼
	@Resource
	LdUseMaMapper ldUseMaMapper;
	
	// 토지사용증명서 파일 관리 맵퍼
	@Resource
	LdDocMaMapper ldDocMaMapper;
	
	// 연속지적도 맵퍼
	@Resource
	LpPaCbndMapper lpPaCbndMapper;
	
	// 토지중심공사대장 시퀀스 서비스
	@Resource
	EgovIdGnrService ldConsMaIdGnrService;
	
	// 토지중심공사대장 대장편집 이력정보 시퀀스 서비스
	@Resource
	EgovIdGnrService ledgerEditHiIdGnrService;
	
	// 토지사용정보대장 시퀀스 서비스
	@Resource
	EgovIdGnrService ldUseMaIdGnrService;
	
	// 파일 서비스
	@Resource
	FileService fileService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#listLandCenterCetrwkRegstr(kr.co.gitt.kworks.projects.gn.model.LdConsMa)
	/////////////////////////////////////////////
	@Override
	public List<LdConsMa> listLandCenterCetrwkRegstr(LdConsMa ldConsMa) {
		return ldConsMaMapper.list(ldConsMa);
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#selectOneLandCenterCetrwkRegstr(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public LdConsMa selectOneLandCenterCetrwkRegstr(Long cntIdn) {
		return ldConsMaMapper.selectOne(cntIdn);
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#modifyLandCenterCetrwkRegstr(kr.co.gitt.kworks.projects.gn.model.LdConsMa)
	/////////////////////////////////////////////
	@Override
	public Integer modifyLandCenterCetrwkRegstr(LdConsMa ldConsMa) throws FdlException {
		
		/// 토지공사대장 수정
		Integer rowCount =  ldConsMaMapper.update(ldConsMa);
		
		// 토지공사대장 수정이력 등록
		LedgerEditHi ledgerEditHi = new LedgerEditHi();
		
		Long hisNo = ledgerEditHiIdGnrService.getNextLongId();
		ledgerEditHi.setHisNo(hisNo);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ledgerEditHi.setEditorId(userDTO.getUserId());
		
		String timeStr 	= null;
		String timePattern = "yyyy-MM-dd HH:mm:ss";
		
		SimpleDateFormat sdfCurrent = new SimpleDateFormat(timePattern, Locale.KOREA);
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
	    timeStr = sdfCurrent.format(ts.getTime());
		ledgerEditHi.setEditDt(timeStr);
		
		ledgerEditHi.setLedgCde("LDCON");
		
		ledgerEditHi.setCntNam(ldConsMa.getCntNam());
		
		ledgerEditHi.setEditIdn(ldConsMa.getCntIdn());
		
		ledgerEditHi.setEditType("UPD");
		
		ledgerEditHiMapper.insert(ledgerEditHi);
		
		return rowCount;
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#addLandCenterCetrwkRegstr(kr.co.gitt.kworks.projects.gn.model.LdConsMa)
	/////////////////////////////////////////////
	@Override
	public Integer addLandCenterCetrwkRegstr(LdConsMa ldConsMa) throws FdlException {
		Long cntIdn = ldConsMaIdGnrService.getNextLongId();
		
		ldConsMa.setCntIdn(cntIdn);
		
		Integer rowCount = ldConsMaMapper.insert(ldConsMa);
		
		// 토지공사대장 수정이력 등록
		LedgerEditHi ledgerEditHi = new LedgerEditHi();

		Long hisNo = ledgerEditHiIdGnrService.getNextLongId();
		ledgerEditHi.setHisNo(hisNo);

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ledgerEditHi.setEditorId(userDTO.getUserId());

		String timeStr 	= null;
		String timePattern = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(timePattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		timeStr = sdfCurrent.format(ts.getTime());
		ledgerEditHi.setEditDt(timeStr);

		ledgerEditHi.setLedgCde("LDCON");

		ledgerEditHi.setCntNam(ldConsMa.getCntNam());

		ledgerEditHi.setEditIdn(ldConsMa.getCntIdn());

		ledgerEditHi.setEditType("INS");

		ledgerEditHiMapper.insert(ledgerEditHi);
		
		return rowCount;
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#removeLandCenterCetrwkRegstr(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeLandCenterCetrwkRegstr(Long cntIdn) throws FdlException {

		LdConsMa ldConsMa = new LdConsMa();
		ldConsMa = ldConsMaMapper.selectOne(cntIdn);
				
		// 토지공사대장 수정이력 등록
		LedgerEditHi ledgerEditHi = new LedgerEditHi();

		Long hisNo = ledgerEditHiIdGnrService.getNextLongId();
		ledgerEditHi.setHisNo(hisNo);

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ledgerEditHi.setEditorId(userDTO.getUserId());

		String timeStr 	= null;
		String timePattern = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(timePattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		timeStr = sdfCurrent.format(ts.getTime());
		ledgerEditHi.setEditDt(timeStr);

		ledgerEditHi.setLedgCde("LDCON");

		ledgerEditHi.setCntNam(ldConsMa.getCntNam());

		ledgerEditHi.setEditIdn(ldConsMa.getCntIdn());
		
		ledgerEditHi.setEditType("DEL");

		ledgerEditHiMapper.insert(ledgerEditHi);
		
		Integer rowCount = ldConsMaMapper.delete(cntIdn);
		
		return rowCount;
	}

	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#listAllLandCenterCetrwkRegstr(kr.co.gitt.kworks.projects.gn.model.LdConsMa)
	/////////////////////////////////////////////
	@Override
	public List<LdConsMa> listAllLandCenterCetrwkRegstr(LdConsMa ldConsMa) {
		return ldConsMaMapper.listAll(ldConsMa);
	}
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#cntSelectOneLandCenterCetrwkRegstr(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public LdConsMa cntSelectOneLandCenterCetrwkRegstr(Long cntIdn) {
		return ldConsMaMapper.cntSelectOne(cntIdn);
	}
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#listLandUseInfoRegstr(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public List<LdUseMa> listLandUseInfoRegstr(Long cntIdn) {
		return ldUseMaMapper.pnuList(cntIdn);
	}
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#listAllLandUseInfoRegstr(kr.co.gitt.kworks.projects.gn.model.LdUseMa)
	/////////////////////////////////////////////
	@Override
	public List<LdUseMa> listAllLandUseInfoRegstr(LdUseMa ldUseMa) {
		return ldUseMaMapper.listAll(ldUseMa);
	}

	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#selectOneLandUseInfoRegstr(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public LdUseMa selectOneLandUseInfoRegstr(Long luiIdn) {
		return ldUseMaMapper.selectOne(luiIdn);
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#modifyLandUseInfoRegstr(kr.co.gitt.kworks.projects.gn.model.LdUseMa)
	/////////////////////////////////////////////
	@Override
	public Integer modifyLandUseInfoRegstr(LdUseMa ldUseMa, MultipartRequest multipartRequest) throws FdlException, IOException {
		LdDocMa ldDocMa = new LdDocMa();
		MultipartFile multipartFile = null;
		
		Long cntIdnVal = ldUseMa.getCntIdn();
		Long luiIdnVal = ldUseMa.getLuiIdn();
		
		ldDocMa.setCntIdn(cntIdnVal);
		ldDocMa.setLuiIdn(luiIdnVal);
		
		if(multipartRequest.getFileMap().entrySet() != null) {
				for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
					multipartFile = entry.getValue();
					if(!multipartFile.isEmpty()) {
						String key = entry.getKey();
							/// 토지사용승낙서
							if(StringUtils.equals("agrFile", key)) {
								ldDocMa.setDocCde("AGR");
								
								List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);
								
								if(ldDocMaData.size() > 0) {
									String docFileName = ldDocMaData.get(0).getDocFile();
									
									ldDocMaMapper.delete(ldDocMa);
									fileService.removeLandFile(docFileName);
								}
							}
							/// 인감증명서
							else if(StringUtils.equals("regFile", key)) {
								ldDocMa.setDocCde("REG");
								
								List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);
								
								if(ldDocMaData.size() > 0) {
									String docFileName = ldDocMaData.get(0).getDocFile();
									
									ldDocMaMapper.delete(ldDocMa);
									fileService.removeLandFile(docFileName);
								}
							}
							/// 용지도
							else if(StringUtils.equals("sitFile", key)) {
								ldDocMa.setDocCde("SIT");
								
								List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);
								
								if(ldDocMaData.size() > 0) {
									String docFileName = ldDocMaData.get(0).getDocFile();
									
									ldDocMaMapper.delete(ldDocMa);
									fileService.removeLandFile(docFileName);
								}
							}
							/// 기타문서
							else if(StringUtils.equals("etcFile", key)) {
								ldDocMa.setDocCde("ETC");
								
								List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);
								
								if(ldDocMaData.size() > 0) {
									String docFileName = ldDocMaData.get(0).getDocFile();
									
									ldDocMaMapper.delete(ldDocMa);
									fileService.removeLandFile(docFileName);
								}
							}
						}
					//신규파일 등록
					multipartFile = entry.getValue();
					String orignlFileNm = multipartFile.getOriginalFilename();
					String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
					if(!multipartFile.isEmpty()) {
						String key = entry.getKey();
						/// 토지사용승낙서
						if(StringUtils.equals("agrFile", key)){
							ldDocMa.setDocCde("AGR");
								if(fileExtsn.equals("pdf")){
									ldDocMa.setDocFile("AGR_" + cntIdnVal + "_" + luiIdnVal + ".pdf");
								}else if(fileExtsn.equals("jpg")){
									ldDocMa.setDocFile("AGR_" + cntIdnVal + "_" + luiIdnVal + ".jpg");
								}
							ldDocMaMapper.insert(ldDocMa);
	
							FILE_NAME_PREFIX = "AGR_" + cntIdnVal + "_" + luiIdnVal;
							fileService.addLandFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
						} 
						/// 인감증명서
						else if(StringUtils.equals("regFile", key)){
							ldDocMa.setDocCde("REG");
								if(fileExtsn.equals("pdf")){
									ldDocMa.setDocFile("REG_" + cntIdnVal + "_" + luiIdnVal + ".pdf");
								}else if(fileExtsn.equals("jpg")){
									ldDocMa.setDocFile("REG_" + cntIdnVal + "_" + luiIdnVal + ".jpg");
								}
							ldDocMaMapper.insert(ldDocMa);
	
							FILE_NAME_PREFIX = "REG_" + cntIdnVal + "_" + luiIdnVal;
							fileService.addLandFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
						}
						/// 용지도
						else if(StringUtils.equals("sitFile", key)){
							ldDocMa.setDocCde("SIT");
								if(fileExtsn.equals("pdf")){
									ldDocMa.setDocFile("SIT_" + cntIdnVal + "_" + luiIdnVal + ".pdf");
								}else if(fileExtsn.equals("jpg")){
									ldDocMa.setDocFile("SIT_" + cntIdnVal + "_" + luiIdnVal + ".jpg");
								}
							ldDocMaMapper.insert(ldDocMa);
	
							FILE_NAME_PREFIX = "SIT_" + cntIdnVal + "_" + luiIdnVal;
							fileService.addLandFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
						}
						/// 기타문서
						else if(StringUtils.equals("etcFile", key)){
							ldDocMa.setDocCde("ETC");
								if(fileExtsn.equals("pdf")){
									ldDocMa.setDocFile("ETC_" + cntIdnVal + "_" + luiIdnVal + ".pdf");
								}else if(fileExtsn.equals("jpg")){
									ldDocMa.setDocFile("ETC_" + cntIdnVal + "_" + luiIdnVal + ".jpg");
								}
							ldDocMaMapper.insert(ldDocMa);
	
							FILE_NAME_PREFIX = "ETC_" + cntIdnVal + "_" + luiIdnVal;
							fileService.addLandFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
						}
					}
				}
			//}
		}
		Integer rowCount = ldUseMaMapper.update(ldUseMa);
		
		// 토지공사대장 수정이력 등록
		LedgerEditHi ledgerEditHi = new LedgerEditHi();
				
		Long hisNo = ledgerEditHiIdGnrService.getNextLongId();
		ledgerEditHi.setHisNo(hisNo);

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ledgerEditHi.setEditorId(userDTO.getUserId());

		String timeStr 	= null;
		String timePattern = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(timePattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		timeStr = sdfCurrent.format(ts.getTime());
		ledgerEditHi.setEditDt(timeStr);

		ledgerEditHi.setLedgCde("LDUSE");

		ledgerEditHi.setCntNam(ldUseMa.getCntNam());
		
		ledgerEditHi.setEditIdn(ldUseMa.getCntIdn());
		
		ledgerEditHi.setEditType("UPD");

		ledgerEditHiMapper.insert(ledgerEditHi);
		
		return rowCount;
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#addLandUseInfoRegstr(kr.co.gitt.kworks.projects.gn.model.LdUseMa)
	/////////////////////////////////////////////
	@Override
	public Integer addLandUseInfoRegstr(LdUseMa ldUseMa, MultipartRequest multipartRequest) throws Exception {
		Long cntIdn = ldUseMa.getCntIdn();
		Long luiIdn = ldUseMaIdGnrService.getNextLongId();
		ldUseMa.setLuiIdn(luiIdn);		
		
		// 토지사용증명서파일 등록
		addUseInfoFiles(cntIdn, luiIdn, multipartRequest);
		
		// 토지사용정보대장 등록
		Integer rowCount = ldUseMaMapper.insert(ldUseMa);
		
		// 토지공사대장 수정이력 등록
		LedgerEditHi ledgerEditHi = new LedgerEditHi();
				
		Long hisNo = ledgerEditHiIdGnrService.getNextLongId();
		ledgerEditHi.setHisNo(hisNo);

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ledgerEditHi.setEditorId(userDTO.getUserId());

		String timeStr 	= null;
		String timePattern = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(timePattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		timeStr = sdfCurrent.format(ts.getTime());
		ledgerEditHi.setEditDt(timeStr);

		ledgerEditHi.setLedgCde("LDUSE");
		
		ledgerEditHi.setEditIdn(ldUseMa.getCntIdn());
		
		LdConsMa ldConsMaData = new LdConsMa();
		ldConsMaData = ldConsMaMapper.selectOne(cntIdn);
		ledgerEditHi.setCntNam(ldConsMaData.getCntNam());
		
		ledgerEditHi.setEditType("INS");
		
		ledgerEditHiMapper.insert(ledgerEditHi);
		
		return rowCount;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#removeLandUseInfoRegstr(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeLandUseInfoRegstr(LdUseMa ldUseMa) throws Exception {
		Integer rowCount = ldUseMaMapper.delete(ldUseMa.getLuiIdn());
		
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setLuiIdn(ldUseMa.getLuiIdn());
		
		List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);
		
		if(ldDocMaData.size() > 0) {
			for(int i=0; i<ldDocMaData.size(); i++) {
				String docFileName = ldDocMaData.get(i).getDocFile();
				
				ldDocMaMapper.delete(ldDocMa);
				fileService.removeLandFile(docFileName);
			}
		}
		
		// 토지공사대장 수정이력 등록
		LedgerEditHi ledgerEditHi = new LedgerEditHi();
				
		Long hisNo = ledgerEditHiIdGnrService.getNextLongId();
		ledgerEditHi.setHisNo(hisNo);

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ledgerEditHi.setEditorId(userDTO.getUserId());

		String timeStr 	= null;
		String timePattern = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(timePattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		timeStr = sdfCurrent.format(ts.getTime());
		ledgerEditHi.setEditDt(timeStr);

		ledgerEditHi.setLedgCde("LDUSE");

		LdConsMa ldConsMaData = new LdConsMa();
		ldConsMaData = ldConsMaMapper.selectOne(ldUseMa.getCntIdn());
		ledgerEditHi.setCntNam(ldConsMaData.getCntNam());
		
		ledgerEditHi.setEditIdn(ldUseMa.getCntIdn());
		
		ledgerEditHi.setEditType("DEL");

		ledgerEditHiMapper.insert(ledgerEditHi);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#searchSpatial(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public List<Integer> searchSpatial(Long luiIdn) {
		return ldUseMaMapper.searchWKT(luiIdn);
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#landUseInfoQuickSearch(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public List<LdUseMa> landUseInfoQuickSearch(String pnu) {
		return ldUseMaMapper.landUseInfoQuick(pnu);
	}
	
	
	/////////////////////////////////////////////
	/// @fn addUseInfoFiles
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param luiIdn
	/// @param multipartRequest
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addUseInfoFiles(Long cntIdn, Long luiIdn, MultipartRequest multipartRequest) throws Exception {
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				addUseInfoFile(cntIdn, luiIdn, multipartFile);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addUseInfoFile
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param luiIdn
	/// @param multipartFile
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addUseInfoFile(Long cntIdn, Long luiIdn, MultipartFile multipartFile) throws Exception {
		// 증명서 파일 등록
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setCntIdn(cntIdn);
		ldDocMa.setLuiIdn(luiIdn);
		
		Long cntIdnVal = cntIdn;
		Long luiIdnVal = luiIdn;
		String mulFileName = multipartFile.getName();
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		
		///토지사용승낙서
		if(StringUtils.equals("agrFile",mulFileName)){
			FILE_NAME_PREFIX = "AGR_" + cntIdnVal + "_" + luiIdnVal;
			fileService.addLandFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
			
			ldDocMa.setDocCde("AGR");
				if(fileExtsn.equals("pdf")){
					ldDocMa.setDocFile("AGR_" + cntIdnVal + "_" + luiIdnVal + ".pdf");
				}else if(fileExtsn.equals("jpg")){
					ldDocMa.setDocFile("AGR_" + cntIdnVal + "_" + luiIdnVal + ".jpg");
				}
			ldDocMaMapper.insert(ldDocMa);
		} 
		/// 인감증명서
		if(StringUtils.equals("regFile",mulFileName)) {
			FILE_NAME_PREFIX = "REG_" + cntIdnVal + "_" + luiIdnVal;
			fileService.addLandFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
			
			ldDocMa.setDocCde("REG");
				if(fileExtsn.equals("pdf")){
					ldDocMa.setDocFile("REG_" + cntIdnVal + "_" + luiIdnVal + ".pdf");
				}else if(fileExtsn.equals("jpg")){
					ldDocMa.setDocFile("REG_" + cntIdnVal + "_" + luiIdnVal + ".jpg");
				}
			ldDocMaMapper.insert(ldDocMa);
		}
		/// 용지도
		if(StringUtils.equals("sitFile",mulFileName)) {
			FILE_NAME_PREFIX = "SIT_" + cntIdnVal + "_" + luiIdnVal;
			fileService.addLandFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
			
			ldDocMa.setDocCde("SIT");
				if(fileExtsn.equals("pdf")){
					ldDocMa.setDocFile("SIT_" + cntIdnVal + "_" + luiIdnVal + ".pdf");
				}else if(fileExtsn.equals("jpg")){
					ldDocMa.setDocFile("SIT_" + cntIdnVal + "_" + luiIdnVal + ".jpg");
				}
			ldDocMaMapper.insert(ldDocMa);
		}
		/// 기타문서
		if(StringUtils.equals("etcFile",mulFileName)) {
			FILE_NAME_PREFIX = "ETC_" + cntIdnVal + "_" + luiIdnVal;
			fileService.addLandFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
			
			ldDocMa.setDocCde("ETC");
				if(fileExtsn.equals("pdf")){
					ldDocMa.setDocFile("ETC_" + cntIdnVal + "_" + luiIdnVal + ".pdf");
				}else if(fileExtsn.equals("jpg")){
					ldDocMa.setDocFile("ETC_" + cntIdnVal + "_" + luiIdnVal + ".jpg");
				}
			ldDocMaMapper.insert(ldDocMa);
		}
		
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#landDocFileList(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public List<LdDocMa> landDocFileList(Long cntIdn, Long luiIdn) {
	LdDocMa ldDocMa = new LdDocMa();
	ldDocMa.setCntIdn(cntIdn);
	ldDocMa.setLuiIdn(luiIdn);
	
	return ldDocMaMapper.fileList(ldDocMa);
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.ladCntr.LadCntrwkRegstrService#searchPnuList(kr.co.gitt.kworks.model.LpPaCbnd)
	/////////////////////////////////////////////
	@Override
	public LpPaCbnd searchPnuList(LpPaCbnd lpPaCbnd) {
		return lpPaCbndMapper.lpPaCbndList(lpPaCbnd);
	}
	
}
