package kr.co.gitt.kworks.projects.yg.service.ladCntr;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.LpPaCbnd;
import kr.co.gitt.kworks.mappers.KwsFileMapper;
import kr.co.gitt.kworks.mappers.LpPaCbndMapper;
import kr.co.gitt.kworks.projects.yg.dto.LandCenterCntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.yg.model.LedgerEditHi;
import kr.co.gitt.kworks.projects.yg.mappers.LdlConsPsMapper;
import kr.co.gitt.kworks.projects.yg.mappers.LedgerEditHiMapper;
import kr.co.gitt.kworks.projects.yg.mappers.LdDocMaMapper;
import kr.co.gitt.kworks.projects.yg.mappers.LdUseMaMapper;
import kr.co.gitt.kworks.projects.yg.model.LdDocMa;
import kr.co.gitt.kworks.projects.yg.model.LdUseMa;
import kr.co.gitt.kworks.projects.yg.model.LdlConsPs;
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
@Profile({"yg_dev", "yg_oper"})
public class LadCntrwkRegstrServiceImpl implements LadCntrwkRegstrService {

	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "landCenter";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX;
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 토지중심공사대장 맵퍼
	@Resource
	LdlConsPsMapper ldlConsPsMapper;
	
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
	
	// 파일맵퍼
	@Resource
	KwsFileMapper kwsFileMapper;
	
	// 토지중심공사대장 시퀀스 서비스
	@Resource
	EgovIdGnrService ldConsMaIdGnrService;
	
	// 토지중심공사대장(LDL_Cons_PS) 시퀀스 서비스
	@Resource
	EgovIdGnrService ldlConsPsIdGnrService;
	
	// 토지중심공사대장 대장편집 이력정보 시퀀스 서비스
	@Resource
	EgovIdGnrService ledgerEditHiIdGnrService;
	
	// 토지사용정보대장 시퀀스 서비스
	@Resource
	EgovIdGnrService ldUseMaIdGnrService;
	
	// 토지사용정보대장 시퀀스 서비스
	@Resource
	EgovIdGnrService ldDocMaIdGnrService;
	
	//파일 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsFileIdGnrService;
	
	// 파일 서비스
	@Resource
	FileService fileService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#listAllLandCenterCetrwkRegstr(kr.co.gitt.kworks.projects.gn.model.LdConsMa)
	/////////////////////////////////////////////
	@Override
	public List<LdlConsPs> listAllLandCenterCetrwkRegstr() {
		return ldlConsPsMapper.listAllConsName();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#cntSelectOneLandCenterCetrwkRegstr(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public LdlConsPs cntSelectOneLandCenterCetrwkRegstr(Long cntIdn) {
		return ldlConsPsMapper.cntSelectOne(cntIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 토지중심 공사위치 표현을 위한 토지중심 공사의치 전체 검색
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#listAlllandCenterCntrwkPoi()
	/////////////////////////////////////////////
	@Override
	public List<LandCenterCntrwkRegstrDTO> listAlllandCenterCntrwkPoi() {
		return ldlConsPsMapper.listAll();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#selectOneLandCenterCetrwkRegstr(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public LdlConsPs selectOneLandCenterCetrwkRegstr(LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO) {
		return ldlConsPsMapper.selectOne(landCenterCntrwkRegstrDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#modifyLandCenterCetrwkRegstr(kr.co.gitt.kworks.projects.gn.model.LdConsMa)
	/////////////////////////////////////////////
	@Override
	public Integer modifyLandCenterCetrwkRegstr(LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		
		Long cntIdn = landCenterCntrwkRegstrDTO.getCntIdn();
		LdDocMa ldDocMa = new LdDocMa();
		MultipartFile multipartFile = null;
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
						/// 토지대장붙임문서
						if(StringUtils.equals("atcFile", key)) {
							ldDocMa.setCntIdn(cntIdn);
							
							List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);
							
							if(ldDocMaData.size() > 0) {
								Long fileNo = ldDocMaData.get(0).getFileNo();
								
								ldDocMa.setFileNo(fileNo);
								ldDocMaMapper.delete(ldDocMa);
								fileService.removeFile(fileNo);
							}
						}
					}
				
				//신규파일 등록
				addLandCenterFiles(cntIdn, multipartRequest);
				
			}
		}
		
		/// 토지공사대장 수정
		Integer rowCount =  ldlConsPsMapper.update(landCenterCntrwkRegstrDTO);
		
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
		
		ledgerEditHi.setCntNam(landCenterCntrwkRegstrDTO.getCntNam());
		
		ledgerEditHi.setEditIdn(landCenterCntrwkRegstrDTO.getCntIdn());
		
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
	public Integer addLandCenterCetrwkRegstr(LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO, MultipartRequest multipartRequest)  throws FdlException, IllegalStateException, IOException  {
		Long cntIdn = ldlConsPsIdGnrService.getNextLongId();
		Long hisNo = ledgerEditHiIdGnrService.getNextLongId();
		
		landCenterCntrwkRegstrDTO.setCntIdn(cntIdn);
		
		//붙임문서 파일등록
		addLandCenterFiles(cntIdn, multipartRequest);
		//토지공사대장 등록
		Integer rowCount = ldlConsPsMapper.insert(landCenterCntrwkRegstrDTO);
		
		// 토지공사대장 수정이력 등록
		LedgerEditHi ledgerEditHi = new LedgerEditHi();
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
		ledgerEditHi.setCntNam(landCenterCntrwkRegstrDTO.getCntNam());
		ledgerEditHi.setEditIdn(landCenterCntrwkRegstrDTO.getCntIdn());
		ledgerEditHi.setEditType("INS");
		ledgerEditHiMapper.insert(ledgerEditHi);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn addLandCenterFiles
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param multipartRequest
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addLandCenterFiles(Long cntIdn, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException{
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				addLandCenterFile(cntIdn, multipartFile);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addLandCenterFile
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param multipartFile
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addLandCenterFile(Long cntIdn, MultipartFile multipartFile) throws FdlException, IllegalStateException, IOException {

		Long fileNo  = kwsFileIdGnrService.getNextLongId();
		Long docNo = ldDocMaIdGnrService.getNextLongId();
		LdDocMa ldDocMa = new LdDocMa();
		
		ldDocMa.setCntIdn(cntIdn);
		ldDocMa.setDocNo((long)docNo);
		ldDocMa.setFileNo((long)fileNo);
		
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		
		FILE_NAME_PREFIX = "ATC_" + fileNo;
		fileService.addYgLandCntFile(multipartFile, fileNo, FOLDER_NAME, FILE_NAME_PREFIX);
		
		ldDocMa.setDocCde("ATC");
			if(fileExtsn.equals("pdf")){
				ldDocMa.setDocFile("ATC_" + fileNo + ".pdf");
			}else if(fileExtsn.equals("jpg")){
				ldDocMa.setDocFile("ATC_" + fileNo + ".jpg");
			}
			
		ldDocMaMapper.insert(ldDocMa);
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

		Long fileNo = null;
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setCntIdn(cntIdn);
		
		 List<LdDocMa> ldDocMaData = ldDocMaMapper.fileList(ldDocMa);
			
		if(ldDocMaData.size() > 0) {
			for(int i=0; i<ldDocMaData.size(); i++) {
				fileNo = ldDocMaData.get(i).getFileNo();
				//KwsFile 삭제	
				fileService.removeFile(fileNo);
				//붙임문서삭제
				ldDocMa.setFileNo(fileNo);
				ldDocMaMapper.delete(ldDocMa);
			}
		}
			
		LandCenterCntrwkRegstrDTO landCenterCntrwkRegstrDTO = new LandCenterCntrwkRegstrDTO();
		landCenterCntrwkRegstrDTO.setCntIdn(cntIdn);
			
		LdlConsPs ldlConsPs = new LdlConsPs();
		ldlConsPs = ldlConsPsMapper.selectOne(landCenterCntrwkRegstrDTO);
				
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
		ledgerEditHi.setCntNam(ldlConsPs.getCntNam());
		ledgerEditHi.setEditIdn(ldlConsPs.getCntIdn());
		ledgerEditHi.setEditType("DEL");

		ledgerEditHiMapper.insert(ledgerEditHi);
		
		//공사대장 삭제
		Integer rowCount = ldlConsPsMapper.delete(cntIdn);
		
		return rowCount;
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
	public Integer modifyLandUseInfoRegstr(LdUseMa ldUseMa, MultipartRequest multipartRequest) throws Exception {
		LdDocMa ldDocMa = new LdDocMa();
		MultipartFile multipartFile = null;
		
		Long luiIdn = ldUseMa.getLuiIdn();
		
		ldDocMa.setLuiIdn(luiIdn);
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					
					List<LdDocMa> ldDocMaData = ldDocMaMapper.list(ldDocMa);
					
					if(ldDocMaData.size() > 0) {
						Long fileNo = ldDocMaData.get(0).getFileNo();
						//붙임문서삭제
						ldDocMa.setFileNo(fileNo);
						ldDocMaMapper.delete(ldDocMa);
						fileService.removeFile(fileNo);
					}
				}
				//신규파일 등록
				addUseInfoFiles(luiIdn, multipartRequest);
			}
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
		addUseInfoFiles(luiIdn, multipartRequest);
		
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
		
		LdlConsPs ldlConsPsData = ldlConsPsMapper.cntSelectOne(cntIdn);
		ledgerEditHi.setCntNam(ldlConsPsData.getCntNam());
		
		ledgerEditHi.setEditType("INS");
		
		ledgerEditHiMapper.insert(ledgerEditHi);
		
		return rowCount;
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
	protected void addUseInfoFiles(Long luiIdn, MultipartRequest multipartRequest) throws Exception {
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				addUseInfoFile(luiIdn, multipartFile);
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
	protected void addUseInfoFile(Long luiIdn, MultipartFile multipartFile) throws Exception {
		Long docNo = ldDocMaIdGnrService.getNextLongId();
		Long fileNo  = kwsFileIdGnrService.getNextLongId();
		
		// 증명서 파일 등록
		LdDocMa ldDocMa = new LdDocMa();
		//ldDocMa.setCntIdn(cntIdn); 양구에서는 토지사용승낙서 등록할경우에는 luiIdn만 등록함
		ldDocMa.setLuiIdn(luiIdn);
		ldDocMa.setDocNo(docNo);
		ldDocMa.setFileNo(fileNo);
		
		Long luiIdnVal = luiIdn;
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		
		///토지사용승낙서
		FILE_NAME_PREFIX = "AGR_" + luiIdnVal;
		fileService.addYgLandCntFile(multipartFile, fileNo, FOLDER_NAME, FILE_NAME_PREFIX);
		
		ldDocMa.setDocCde("AGR");
		if(fileExtsn.equals("pdf")){
			ldDocMa.setDocFile("AGR_" + luiIdnVal + ".pdf");
		}else if(fileExtsn.equals("jpg")){
			ldDocMa.setDocFile("AGR_" + luiIdnVal + ".jpg");
		}
		
		ldDocMaMapper.insert(ldDocMa);
	
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
				Long fileNo = ldDocMaData.get(0).getFileNo();
				
				ldDocMaMapper.delete(ldDocMa);
				fileService.removeFile(fileNo);
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

		LdlConsPs ldlConsPsData = ldlConsPsMapper.cntSelectOne(ldUseMa.getCntIdn());
		ledgerEditHi.setCntNam(ldlConsPsData.getCntNam());
		
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
	/// @see kr.co.gitt.kworks.projects.gn.service.ladCntr.LadCntrwkRegstrService#searchPnuList(kr.co.gitt.kworks.model.LpPaCbnd)
	/////////////////////////////////////////////
	@Override
	public LpPaCbnd searchPnuList(LpPaCbnd lpPaCbnd) {
		return lpPaCbndMapper.lpPaCbndList(lpPaCbnd);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.yg.service.ladCntr.LadCntrwkRegstrService#removeAtcFile(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeAtcFile(Long fileNo) throws FdlException {
		
		fileService.removeFile(fileNo);
		
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setFileNo(fileNo);
		
		Integer rowCount = ldDocMaMapper.delete(ldDocMa);
		
		return rowCount;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.yg.service.ladCntr.LadCntrwkRegstrService#findAtcFile(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsFile findAtcFile(Long cntIdn) {
		Long fileNo = null;
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setCntIdn(cntIdn);
		
		 List<LdDocMa> ldDocMaData = ldDocMaMapper.fileList(ldDocMa);
			
			if(ldDocMaData.size() > 0) {
				for(int i=0; i<ldDocMaData.size(); i++) {
					fileNo = ldDocMaData.get(i).getFileNo();
				}
			}
		
		return kwsFileMapper.selectOne(fileNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.ladCntr.LadCntrwkRegstrService#landDocFileList(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile findAgrFile(Long luiIdn) {
		Long fileNo = null;
		LdDocMa ldDocMa = new LdDocMa();
		ldDocMa.setLuiIdn(luiIdn);
		
		List<LdDocMa> ldDocMaData = ldDocMaMapper.fileList(ldDocMa);
		
		if(ldDocMaData.size() > 0) {
			for(int i=0; i<ldDocMaData.size(); i++) {
				fileNo = ldDocMaData.get(i).getFileNo();
			}
		}
		
		return kwsFileMapper.selectOne(fileNo);
	}
}
