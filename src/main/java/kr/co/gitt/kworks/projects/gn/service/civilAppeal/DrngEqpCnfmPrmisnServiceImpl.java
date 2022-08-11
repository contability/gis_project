package kr.co.gitt.kworks.projects.gn.service.civilAppeal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.LpPaCbnd;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnAtchmnflDTO;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnDTO;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpVO;
import kr.co.gitt.kworks.projects.gn.mappers.SwtSpmtAtchmnflDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.SwtSpmtCnncDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.SwtSpmtMaMapper;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtAtchmnflDt;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtCnncDt;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtMa;
import kr.co.gitt.kworks.service.file.FileService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class DrngEqpCnfmPrmisnServiceImpl
/// kr.co.gitt.kworks.projects.gn.service.civilAppeal \n
///   ㄴ DrngEqpCnfmPrmisnServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 4:54:15 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
///    | 수정자 | 이승재, 2019.12.09
/// @section 상세설명
/// - 이 클래스는 배수설비인허가 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("drngEqpCnfmPrmisnService")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class DrngEqpCnfmPrmisnServiceImpl implements DrngEqpCnfmPrmisnService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 배수설비인허가 시퀀스 서비스
	@Resource
	EgovIdGnrService swtSpmtMaIdGnrService;
	
	//파일 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsFileIdGnrService;
	
	//배수설비인허가대장 첨부파일 시퀀스 서비스
	@Resource
	EgovIdGnrService swtSpmtAtchmnflDtIdGnrService;
	
	// 파일 서비스
	@Resource
	FileService fileService;
	
	// 배수설비인허가 맵퍼
	@Resource
	SwtSpmtMaMapper swtSpmtMaMapper;
	
	// 배수설비인허가대장 첨부파일 맵퍼
	@Resource
	SwtSpmtAtchmnflDtMapper swtSpmtAtchmnflDtMapper;
	
	// 배수설비연결 맵퍼
	@Resource
	SwtSpmtCnncDtMapper swtSpmtCnncDtMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// - 작성자 : 이승재, 2019.12.09
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService#searchLocation(kr.co.gitt.kworks.model.LpPaCbnd)
	/////////////////////////////////////////////
	@Override
	public List<LpPaCbnd> searchLocation(Long ftrIdn) {
		return swtSpmtMaMapper.selectLpPaCbnd(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// - 수정자 : 이승재, 2019.12.14
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService#selectOneDrngEqpCnfmPrmisn(java.lang.Long)
	/////////////////////////////////////////////
	public DrngEqpCnfmPrmisnDTO selectOneDrngEqpCnfmPrmisn(Long ftrIdn){
		return swtSpmtMaMapper.selectOne(ftrIdn);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// - 작성자 : 이승재, 2019.12.09
	/// - 수정자 : 이승재, 2020.01.13
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService#removeDrngEqpCnfmPrmisn(java.lang.String, java.lang.Long)
	/////////////////////////////////////////////
	public boolean removeDrngEqpCnfmPrmisn(String ftrCde, Long ftrIdn) {
		boolean result = false;
		//첨부문서 삭제
		removeSwtSpmtAtchmnflDt(ftrCde, ftrIdn);
		
		//배수설비 연결 테이블 삭제
		removeSwtSpmtCnncDt(ftrCde, ftrIdn);
		
		//배수설비인허가 대장 삭제
		Integer rowCount = swtSpmtMaMapper.delete(ftrIdn);
		if (rowCount > 0) {
			result = true;
		}
		return result;
	}
	
	private void removeSwtSpmtCnncDt(String ftrCde, Long ftrIdn) {
		SwtSpmtCnncDt swtSpmtCnncDt = new SwtSpmtCnncDt();
		swtSpmtCnncDt.setFtrCde(ftrCde);
		swtSpmtCnncDt.setFtrIdn(ftrIdn);
		swtSpmtCnncDtMapper.delete(swtSpmtCnncDt);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService#modifyDrngEqpCnfmPrmisn(kr.co.gitt.kworks.projects.gn.model.SwtSpmtMa)
	/////////////////////////////////////////////
	public Integer modifyDrngEqpCnfmPrmisn(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO) {
		ModelMapper modelMapper = new ModelMapper();
		Integer rowCount = swtSpmtMaMapper.update(modelMapper.map(drngEqpCnfmPrmisnDTO, SwtSpmtMa.class));
		return rowCount;
	}
	
	@Override
	public Map<String, Object> quickSearch(String dataId, String objectid) {
		Map<String, Object> quickSearchResult = new HashMap<String, Object>();
		List<String> ids = new ArrayList<String>();
		
		if (dataId.equals("LP_PA_CBND")) {
			List<SwtSpmtMa> swtSpmtMas = swtSpmtMaMapper.searchWhereCbndObjectid(objectid);
			
			if (swtSpmtMas.size() > 0) {
				for (int i = 0; i < swtSpmtMas.size(); i++) {
					ids.add(Long.toString(swtSpmtMas.get(i).getFtrIdn()));
				}
			}
						
			quickSearchResult.put("dataId", "SWT_SPMT_MA");
			quickSearchResult.put("dataAlias", "배수설비인허가대장");
			quickSearchResult.put("ids", ids);
		} else {
			Map<String, String> queryParameterMap = new HashMap<String, String>();
			queryParameterMap.put("dataId", dataId);
			queryParameterMap.put("objectid", objectid);
			List<SwtSpmtCnncDt> swtSpmtCnncDts = swtSpmtCnncDtMapper.selectWhereDataIdNObjectid(queryParameterMap);
			
			if (swtSpmtCnncDts.size() > 0) {
				ids.add(Long.toString(swtSpmtCnncDts.get(0).getFtrIdn()));
				
				quickSearchResult.put("dataId", "SWT_SPMT_MA");
				quickSearchResult.put("dataAlias", "배수설비인허가대장");
				quickSearchResult.put("ids", ids);
			} else {
				ids.add(objectid);
				
				quickSearchResult.put("dataId", dataId);
				quickSearchResult.put("dataAlias", "");
				quickSearchResult.put("ids", ids);
			}
		}
		return quickSearchResult;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService#addDrngEqpCnfmPrmisn(kr.co.gitt.kworks.projects.gn.model.SwtSpmtMa)
	/////////////////////////////////////////////
	public Long addDrngEqpCnfmPrmisn(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO) throws FdlException {
		SwtSpmtMa swtSpmtMa = new SwtSpmtMa();
		ModelMapper modelMapper = new ModelMapper();
		swtSpmtMa = modelMapper.map(drngEqpCnfmPrmisnDTO, SwtSpmtMa.class);
		
		Long ftrIdn = swtSpmtMaIdGnrService.getNextLongId();
		swtSpmtMa.setFtrIdn(ftrIdn);
		Integer rowCount = swtSpmtMaMapper.insert(swtSpmtMa);
		return ftrIdn;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService#listAtchmnfl(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public List<DrngEqpCnfmPrmisnAtchmnflDTO> listAtchmnfl(Long ftrIdn) {
		DrngEqpCnfmPrmisnAtchmnflDTO drngEqpCnfmPrmisnAtchmnfls = new DrngEqpCnfmPrmisnAtchmnflDTO();
		drngEqpCnfmPrmisnAtchmnfls.setFtrIdn(ftrIdn);
		return swtSpmtAtchmnflDtMapper.select(drngEqpCnfmPrmisnAtchmnfls);
	}

	@Override
	public Integer addAtchmnfl(DrngEqpCnfmPrmisnAtchmnflDTO drngEqpCnfmPrmisnAtchmnflDTO,
			MultipartRequest multipartRequest) throws FdlException, IOException {
		Integer insertRowCount = 0;
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				KwsFile kwsFile = fileService.addFile(multipartFile, "SPMT", "SPMT");
				
				if (kwsFile != null) {
					SwtSpmtAtchmnflDt swtSpmtAtchmnflDt = new SwtSpmtAtchmnflDt();
					ModelMapper modelMapper = new ModelMapper();
					swtSpmtAtchmnflDt = modelMapper.map(drngEqpCnfmPrmisnAtchmnflDTO, SwtSpmtAtchmnflDt.class);
					swtSpmtAtchmnflDt.setFileNo(kwsFile.getFileNo());
					swtSpmtAtchmnflDt.setShtIdn(swtSpmtAtchmnflDtIdGnrService.getNextLongId());
					insertRowCount += swtSpmtAtchmnflDtMapper.insert(swtSpmtAtchmnflDt);
				}
			}
		}
		return insertRowCount;
	}

	@Override
	public Integer removeAtchmnfl(Long shtIdn, Long fileNo) {
		Integer deleteRowCount = 0;
		
		//KwsFile 삭제	
		if (fileNo > 0) {
			fileService.removeFile(fileNo);
		}
		
		//배수설비인허가대장 붙임문서 삭제
		SwtSpmtAtchmnflDt swtSpmtAtchmnflDt = new  SwtSpmtAtchmnflDt();
		swtSpmtAtchmnflDt.setShtIdn(shtIdn);
		deleteRowCount = swtSpmtAtchmnflDtMapper.delete(swtSpmtAtchmnflDt);
		
		return deleteRowCount;
	}
	
	private void removeSwtSpmtAtchmnflDt(String ftrCde, Long ftrIdn) {
		DrngEqpCnfmPrmisnAtchmnflDTO drngEqpCnfmPrmisnAtchmnflDTO = new DrngEqpCnfmPrmisnAtchmnflDTO();
		drngEqpCnfmPrmisnAtchmnflDTO.setFtrCde(ftrCde);
		drngEqpCnfmPrmisnAtchmnflDTO.setFtrIdn(ftrIdn);
		List<DrngEqpCnfmPrmisnAtchmnflDTO> drngEqpCnfmPrmisnAtchmnfls = swtSpmtAtchmnflDtMapper.select(drngEqpCnfmPrmisnAtchmnflDTO);
		
		ModelMapper modelMapper = new ModelMapper();
		if (drngEqpCnfmPrmisnAtchmnfls.size() > 0) {
			for (int i = 0; i < drngEqpCnfmPrmisnAtchmnfls.size(); i++) {
				SwtSpmtAtchmnflDt swtSpmtAtchmnflDt = new  SwtSpmtAtchmnflDt();
				swtSpmtAtchmnflDt = modelMapper.map(drngEqpCnfmPrmisnAtchmnfls.get(i), SwtSpmtAtchmnflDt.class);
				
				//KwsFile 삭제	
				Long fileNo = swtSpmtAtchmnflDt.getFileNo();
				if (fileNo > 0) {
					fileService.removeFile(fileNo);
				}
			}
			
			//배수설비인허가대장 붙임문서 삭제
			SwtSpmtAtchmnflDt swtSpmtAtchmnflDt = new  SwtSpmtAtchmnflDt();
			swtSpmtAtchmnflDt.setFtrCde(ftrCde);
			swtSpmtAtchmnflDt.setFtrIdn(ftrIdn);
			swtSpmtAtchmnflDtMapper.delete(swtSpmtAtchmnflDt);
		}
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 배수설비인허가대장과 신규 물받이, 하수연결관의 연결 테이블 작성
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService#connectDrngEqpCnfmPrmisnAndDrngEqpl(SwtSpmtCnncDt)
	@Override
	public void connectDrngEqpCnfmPrmisnAndDrngEqp(SwtSpmtCnncDt swtSpmtCnncDt) {
		swtSpmtCnncDtMapper.insert(swtSpmtCnncDt);
	}

	/* (non-Javadoc)
	 * @see kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService#listDrngEqp(kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnDTO)
	 */
	@Override
	public List<DrngEqpVO> listDrngEqp(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO) {
		return swtSpmtCnncDtMapper.listDrngEqp(drngEqpCnfmPrmisnDTO);
	}
}
