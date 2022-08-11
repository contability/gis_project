package kr.co.gitt.kworks.service.export;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.SpatialType;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterFidsDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterSpatialDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.co.service.CompressService;
import kr.co.gitt.kworks.mappers.KwsExportConfmMapper;
import kr.co.gitt.kworks.mappers.KwsExportDataMapper;
import kr.co.gitt.kworks.mappers.KwsExportExcelOptnMapper;
import kr.co.gitt.kworks.mappers.KwsExportFileMapper;
import kr.co.gitt.kworks.mappers.KwsExportFilterBboxMapper;
import kr.co.gitt.kworks.mappers.KwsExportFilterDongMapper;
import kr.co.gitt.kworks.mappers.KwsExportFilterFidMapper;
import kr.co.gitt.kworks.mappers.KwsExportFilterImageMapper;
import kr.co.gitt.kworks.mappers.KwsExportFilterOutptMapper;
import kr.co.gitt.kworks.mappers.KwsExportFilterPolygonMapper;
import kr.co.gitt.kworks.mappers.KwsExportMapper;
import kr.co.gitt.kworks.model.KwsExport;
import kr.co.gitt.kworks.model.KwsExport.ExportFilterTy;
import kr.co.gitt.kworks.model.KwsExport.ExportTy;
import kr.co.gitt.kworks.model.KwsExport.ProgrsSttus;
import kr.co.gitt.kworks.model.KwsExportConfm;
import kr.co.gitt.kworks.model.KwsExportData;
import kr.co.gitt.kworks.model.KwsExportExcelOptn;
import kr.co.gitt.kworks.model.KwsExportFile;
import kr.co.gitt.kworks.model.KwsExportFilterBbox;
import kr.co.gitt.kworks.model.KwsExportFilterDong;
import kr.co.gitt.kworks.model.KwsExportFilterFid;
import kr.co.gitt.kworks.model.KwsExportFilterImage;
import kr.co.gitt.kworks.model.KwsExportFilterOutpt;
import kr.co.gitt.kworks.model.KwsExportFilterPolygon;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.file.FileService;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;
import kr.co.gitt.kworks.service.user.UserService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.MissingNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.kmaps.framework.action.coordinateconvert.SRSTransform;
import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.common.Extent;
import com.kmaps.framework.reference.ISpatialReferenceSystem;
import com.kmaps.framework.reference.initialize.DefaultSRS;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ExportServiceImpl
/// kr.co.gitt.kworks.service.export \n
///   ㄴ ExportServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 6. 오전 11:53:50 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근" |
/// @section 상세설명
/// - 이 클래스는 내보내기 관리 서비스 구현 클래스 입니다.
/// - 수정자 : 이승재, 2021.02.19, 동기준 내보내기 관련 convertSpatialSearchDTOByKwsExport 수정 
/////////////////////////////////////////////
@Service("exportService")
public class ExportServiceImpl implements ExportService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 내보내기 관리 맵퍼
	@Resource
	KwsExportMapper kwsExportMapper;
	
	/// 내보내기 숭인 맵퍼
	@Resource
	KwsExportConfmMapper kwsExportConfmMapper;
	
	/// 내보내기 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsExportIdGnrService;
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "export";

	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX = "KWS_EXPORT_";

	/// 내보내기 파일 맵퍼
	@Resource
	KwsExportFileMapper kwsExportFileMapper;
	
	/// 내보내기 데이터 맵퍼
	@Resource
	KwsExportDataMapper kwsExportDataMapper;
	
	/// 내보내기 엑셀 옵션 맵퍼
	@Resource
	KwsExportExcelOptnMapper kwsExportExcelOptnMapper;
	
	/// 내보내기 필터 FID
	@Resource
	KwsExportFilterFidMapper kwsExportFilterFidMapper;
	
	/// 내보내기 필터 BBOX
	@Resource
	KwsExportFilterBboxMapper kwsExportFilterBboxMapper;
	
	/// 내보내기 필터 동
	@Resource
	KwsExportFilterDongMapper kwsExportFilterDongMapper;
	
	/// 내보내기 필터 이미지
	@Resource
	KwsExportFilterImageMapper kwsExportFilterImageMapper;
	
	/// 내보내기 필터 출력
	@Resource
	KwsExportFilterOutptMapper kwsExportFilterOutptMapper;
	
	/// 내보내기 필터 다각형
	@Resource
	KwsExportFilterPolygonMapper kwsExportFilterPolygonMapper;
	
	// 파일 서비스
	@Resource
	FileService fileService;
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 사용자 서비스
	@Resource
	UserService userService;
	
	/// 공간 검색 서비스
	@Resource
	SpatialSearchService spatialSearchService;
	
	/// Dxf 내보내기 서비스
	@Resource(name="dxfExportService")
	DxfExportService dxfExportService;
	
	/// 엑셀 내보내기 서비스
	@Resource(name="excelExportService")
	ExcelExportService excelExportService;
	
	/// Shape 내보내기 서비스
	@Resource(name="shapeExportService")
	ShapeExportService shapeExportService;
	
	/// 압축 서비스
	@Resource(name="compressService")
	CompressService compressService;
	
	/// 내보내기 권한 서비스
	@Resource
	ExportAuthorService exportAuthorService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#listAllExport(kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO)
	/////////////////////////////////////////////
	public List<KwsExport> listAllExport(ExportSearchDTO searchDTO) {
		return kwsExportMapper.listAll(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#listExport(kr.co.gitt.kworks.cmmn.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsExport> listExport(ExportSearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsExportMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listExport(searchDTO);
		}
		else {
			return new ArrayList<KwsExport>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#selectOneExport(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsExport selectOneExport(Long exportNo) {
		KwsExport kwsExport = kwsExportMapper.selectOne(exportNo);
		if(kwsExport.getExportTy().equals(ExportTy.OUTPUT)) {
			kwsExport.setKwsExportFilterOutpt(kwsExportFilterOutptMapper.selectOne(exportNo));
		}
		return kwsExport;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#selectOneExportNo(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsExport selectOneExportNo() {
		return kwsExportMapper.selectOneExportNo();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#consentExport(kr.co.gitt.kworks.model.KwsExport)
	/////////////////////////////////////////////
	@Override
	public Integer consentExport(KwsExport kwsExport) {
		Integer rowCount = kwsExportConfmMapper.insert(kwsExport.getKwsExportConfm());	// 승인 등록
		modifyProgrsSttusConsent(kwsExport);		// 진행단계 수정
		return rowCount;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#returnExport(kr.co.gitt.kworks.model.KwsExport)
	/////////////////////////////////////////////
	@Override
	public Integer returnExport(KwsExport kwsExport) {
		Integer rowCount = modifyProgrsSttusReturn(kwsExport);		// 진행단계수정
		kwsExportConfmMapper.insert(kwsExport.getKwsExportConfm());	// 승인 등록
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#periodEndExport(kr.co.gitt.kworks.model.KwsExport)
	/////////////////////////////////////////////
	@Override
	public void periodEndExport(KwsExport kwsExport) {
		modifyProgrsSttusExportPeriodEnd(kwsExport);
	}

	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#addExport(kr.co.gitt.kworks.model.KwsExport)
	/////////////////////////////////////////////
	@Override
	public Integer addExport(KwsExport kwsExport) throws Exception {
		Long exportNo = kwsExportIdGnrService.getNextLongId();
		kwsExport.setExportNo(exportNo);
		kwsExport.setProgrsSttus(ProgrsSttus.EXPORT_REQUEST);
		
		Integer rowCount = kwsExportMapper.insert(kwsExport);

		// 내보내기 타입
		switch(kwsExport.getExportTy()) {
			case EXCEL : 
				addExportExcelOptn(kwsExport.getKwsExportExcelOptn(), exportNo);
			case DXF : 
			case SHAPE : 
				break;
			case IMAGE : 
				break;
			case OUTPUT : 
				break;
		}
		
		// 내보내기 데이터
		addExportDatas(kwsExport.getKwsExportDatas(), exportNo);
		
		// 필터 등록
		switch(kwsExport.getExportFilterTy()) {
			case NONE : break; 
			case FIDS : 
				addExportFilterFids(kwsExport.getKwsExportFilterFids(), exportNo);
				break;
			case DONG :
				addExportFilterDong(kwsExport.getKwsExportFilterDong(), exportNo);
				break;
			case BBOX : 
				addExportFilterBbox(kwsExport.getKwsExportFilterBbox(), exportNo);
				break;
			case IMAGE :
				addExportFilterImage(kwsExport.getKwsExportFilterImage(), exportNo);
				break;
			case OUTPUT : 
				addExportFilterOutput(kwsExport.getKwsExportFilterOutpt(), exportNo);
				break;
			case POLYGON :
				addExportFilterPolygon(kwsExport.getKwsExportFilterPolygon(), exportNo);
				break;
		}
		
		if(kwsExport.getExportTy().equals(ExportTy.OUTPUT) || kwsExport.getExportTy().equals(ExportTy.IMAGE)) {
			modifyProgrsSttus(kwsExportMapper.selectOne(exportNo));
		}
		
		return rowCount;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#addExport(kr.co.gitt.kworks.model.KwsExport, java.awt.image.BufferedImage)
	/////////////////////////////////////////////
	@Override
	public Integer addExport(KwsExport kwsExport, BufferedImage bufferedImage) throws FdlException, IOException {
		Long exportNo = kwsExportIdGnrService.getNextLongId();
		kwsExport.setExportNo(exportNo);
		kwsExport.setProgrsSttus(ProgrsSttus.EXPORT_REQUEST);
		
		// 내보내기 등록
		Integer rowCount = kwsExportMapper.insert(kwsExport);
		
		// 내보내기 데이터 등록
		//Vector 없이 baseMap만 이미지 내보내기 했을 경우
		//내보내기 데이터 로그는 기록하지 않도록 처리
		if (kwsExport.getKwsExportDatas() != null) {
			addExportDatas(kwsExport.getKwsExportDatas(), exportNo);
		}
				
		/// 내보내기 필터 이미지 등록
		addExportFilterImage(kwsExport.getKwsExportFilterImage(), exportNo);
		
		// 내보내기 파일 등록
		addExportFile(kwsExport, bufferedImage);
		
		// 내보내기 단계 변경
		modifyProgrsSttus(kwsExportMapper.selectOne(exportNo));
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#exportData(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public void exportData(Long exportNo) throws Exception {
		String exportPath = messageSource.getMessage("Com.Export.Path", null, Locale.getDefault());
		String path = exportPath + exportNo;
		
		KwsExport kwsExport = selectOneExport(exportNo);
		
		// 내보내기 타입
		ExportTy exportTy = kwsExport.getExportTy();
		switch(exportTy) {
			case DXF :
			case EXCEL : 
			case SHAPE :
				
				// 중심점을 구하기 위한 영역
				Extent extent = new Extent();
				
				SpatialSearchDTO spatialSearchDTO = convertSpatialSearchDTOByKwsExport(kwsExport);
				
				// 요약 검색
				List<SpatialSearchSummaryDTO> list =  null;
				// FIDS 필터
				if(kwsExport.getExportFilterTy().equals(ExportFilterTy.FIDS)) {
					list = new ArrayList<SpatialSearchSummaryDTO>();
					SpatialSearchSummaryDTO spatialSearchSummaryDTO = new SpatialSearchSummaryDTO();
					spatialSearchSummaryDTO.setDataId(spatialSearchDTO.getDataIds().get(0));
					spatialSearchSummaryDTO.setIds(spatialSearchDTO.getFilterFidsDTO().getFids());
					list.add(spatialSearchSummaryDTO);
				}
				else {
					list = spatialSearchService.searchSummaries(spatialSearchDTO);
				}
				
				if(list.size() > 0) {
					if(exportTy.equals(ExportTy.DXF)) {
						// DXF 파일 생성 및 등록
						String dxfPath = dxfExportService.export(list, path, kwsExport.getExportCntmId(), extent);
						File file = new File(dxfPath);
						addExportFile(kwsExport, file);

						File directory = new File(path);
						for(File deleteFile : directory.listFiles()) {
							deleteFile.delete();
						}
						FileUtils.deleteDirectory(directory);
					}
					else {
						for(SpatialSearchSummaryDTO spatialSearchSummaryDTO : list) {
							// 엑셀 내보내기
							if(exportTy.equals(ExportTy.EXCEL)) {
								KwsExportExcelOptn kwsExportExcelOptn = kwsExportExcelOptnMapper.selectOne(exportNo);
								boolean isFieldName = StringUtils.equals(kwsExportExcelOptn.getFieldNmIndictAt(), "Y")?true:false;
									// 검색 결과 목록 현황에서 엑셀 내보내기
									if(kwsExport.getExportFilterTy().equals(ExportFilterTy.FIDS)) {
										//양구군 토지공사대장 위치 내보내기
										if (spatialSearchSummaryDTO.getDataId().toUpperCase().equals("VIEW_LD_CONS_PS")) {
											excelExportService.fidsExportwithCoordinates(spatialSearchSummaryDTO, isFieldName, path, kwsExport.getExportCntmId(), extent);
										}
										//검색결과 목록에서 엑셀 내보내기
										else {
											excelExportService.fidsExport(spatialSearchSummaryDTO, isFieldName, path, kwsExport.getExportCntmId(), extent);
										}
									} else {
										excelExportService.export(spatialSearchSummaryDTO, isFieldName, path, kwsExport.getExportCntmId(), extent);
									}
							}
							// 공간 내보내기
							else if(exportTy.equals(ExportTy.SHAPE)) {
								shapeExportService.export(spatialSearchSummaryDTO, path, kwsExport.getExportCntmId(), extent);
							}
							else {
								throw new Exception(exportTy + " 타입은 지원되지 않는 내보내기 타입입니다. dataId = [" + spatialSearchSummaryDTO.getDataId() + "]");
							}
						}
						
						// 압축파일 생성
						String zipPath = exportPath + exportNo + FilenameUtils.EXTENSION_SEPARATOR_STR + "zip";
						compressService.compressFolder(path, zipPath);
						File file = new File(zipPath);
						
						// 내보내기 파일 등록
						addExportFile(kwsExport, file);
						file.delete();
						
						File directory = new File(path);
						for(File deleteFile : directory.listFiles()) {
							deleteFile.delete();
						}
						FileUtils.deleteDirectory(directory);
					}
					
					modifyCenterLonLat(exportNo, extent);
					
					// 진행상태 변경
					modifyProgrsSttus(kwsExport);
				}
				else {
					// 데이터 없음
					modifyProgrsSttusDataNone(kwsExport);
				}
				break;
			case IMAGE : 
			case OUTPUT : 
				// 진행상태 변경
				modifyProgrsSttus(kwsExport);
				break;
		}
		
	}
	
	/////////////////////////////////////////////
	/// @fn modifyCenterLonLat
	/// @brief 함수 간략한 설명 : 중심 점 변경
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @param extent
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer modifyCenterLonLat(Long exportNo, Extent extent) throws Exception {
		String fromSrid = messageSource.getMessage("Map.Projection", null, Locale.getDefault());

		SRSTransform srsTrans = null;
		srsTrans = new SRSTransform();
		
		// 소스 좌표계
		ISpatialReferenceSystem sourceSrs = DefaultSRS.get(fromSrid, 0, 0);
		// 목표 좌표계
		ISpatialReferenceSystem targetSrs = DefaultSRS.get("EPSG:4326", 0, 0);
		srsTrans.set(sourceSrs, targetSrs);
		
		Coordinate center = extent.center();
		Coordinate lonlat = srsTrans.convert(center);
		
		KwsExport kwsExport = new KwsExport();
		kwsExport.setExportNo(exportNo);
		kwsExport.setCenterLon(lonlat.getX());
		kwsExport.setCenterLat(lonlat.getY());
		
		return kwsExportMapper.updateCenterLonLat(kwsExport);
	}

	/////////////////////////////////////////////
	/// @fn listExport
	/// @brief 함수 간략한 설명 : 내보내기 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private List<KwsExport> listExport(ExportSearchDTO searchDTO) {
		return kwsExportMapper.list(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn modifyDeletePrearngeDt
	/// @brief 함수 간략한 설명 : 삭제 예정 일시 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer modifyDeletePrearngeDt(KwsExport kwsExport) {
		KwsExportConfm kwsExportConfm = kwsExportConfmMapper.selectOne(kwsExport.getExportNo());
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		Date date = null;
		if(kwsExportConfm == null) {
			date = kwsExport.getRequstDt();
		}
		else {
			date = kwsExportConfm.getConfmDt();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if(prjCode.equals("dh")){
			calendar.add(Calendar.DATE, 31);
		}else {
			calendar.add(Calendar.DATE, 7);
		}
		kwsExport.setDeletePrearngeDt(calendar.getTime());
		
		return kwsExportMapper.updateDeletePrearngeDt(kwsExport);
	}
	
	/////////////////////////////////////////////
	/// @fn modifyProgrsSttusConsentWaiting
	/// @brief 함수 간략한 설명 : 승인 대기 상태로 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer modifyProgrsSttusConsentWaiting(KwsExport kwsExport) {
		kwsExport.setProgrsSttus(ProgrsSttus.CONSENT_WAITING);
		Integer rowCount = kwsExportMapper.updateProgrsSttus(kwsExport);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn modifyProgrsSttusConsent
	/// @brief 함수 간략한 설명 : 승인 완료 상태로 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer modifyProgrsSttusConsent(KwsExport kwsExport) {
		kwsExport.setProgrsSttus(ProgrsSttus.CONSENT_COMPLETION);
		Integer rowCount = kwsExportMapper.updateProgrsSttus(kwsExport);
		modifyDeletePrearngeDt(kwsExport);							// 삭제 예정 일시 수정
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn modifyProgrsSttusReturn
	/// @brief 함수 간략한 설명 : 승인 거절 상태로 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer modifyProgrsSttusReturn(KwsExport kwsExport) {
		kwsExport.setProgrsSttus(ProgrsSttus.RETURN);
		Integer rowCount = kwsExportMapper.updateProgrsSttus(kwsExport);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn modifyProgrsSttusDataNone
	/// @brief 함수 간략한 설명 : 데이터 없음 상태로 변경
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer modifyProgrsSttusDataNone(KwsExport kwsExport) {
		kwsExport.setProgrsSttus(ProgrsSttus.DATA_NONE);
		Integer rowCount = kwsExportMapper.updateProgrsSttus(kwsExport);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn modifyProgrsSttusExportFailure
	/// @brief 함수 간략한 설명 : 내보내기 오류 상태로 변경
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyProgrsSttusExportFailure(KwsExport kwsExport) {
		kwsExport.setProgrsSttus(ProgrsSttus.EXPORT_FAILURE);
		Integer rowCount = kwsExportMapper.updateProgrsSttus(kwsExport);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn modifyProgrsSttusExportPeriodEnd
	/// @brief 함수 간략한 설명 : 내보내기 기간 만료 상태로 변경
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer modifyProgrsSttusExportPeriodEnd(KwsExport kwsExport) {
		kwsExport.setProgrsSttus(ProgrsSttus.PERIOD_END);
		Integer rowCount = kwsExportMapper.updateProgrsSttus(kwsExport);
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn addExportDatas
	/// @brief 함수 간략한 설명 : 내보내기 데이터 목록 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportDatas
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportDatas(List<KwsExportData> kwsExportDatas, Long exportNo) {
		for(KwsExportData kwsExportData : kwsExportDatas) {
			kwsExportData.setExportNo(exportNo);
			kwsExportDataMapper.insert(kwsExportData);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addExportExcelOptn
	/// @brief 함수 간략한 설명 : 엑셀 옵션 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportExcelOptn
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportExcelOptn(KwsExportExcelOptn kwsExportExcelOptn, Long exportNo) {
		kwsExportExcelOptn.setExportNo(exportNo);
		kwsExportExcelOptnMapper.insert(kwsExportExcelOptn);
	}

	/////////////////////////////////////////////
	/// @fn addExportFilterFids
	/// @brief 함수 간략한 설명 : FID 목록 필터 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterFids
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportFilterFids(List<KwsExportFilterFid> kwsExportFilterFids, Long exportNo) {
		for(KwsExportFilterFid kwsExportFilterFid : kwsExportFilterFids) {
			kwsExportFilterFid.setExportNo(exportNo);
			kwsExportFilterFidMapper.insert(kwsExportFilterFid);
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addExportFilterBbox
	/// @brief 함수 간략한 설명 : BBOX 필터 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterBbox
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportFilterBbox(KwsExportFilterBbox kwsExportFilterBbox, Long exportNo) {
		kwsExportFilterBbox.setExportNo(exportNo);
		kwsExportFilterBboxMapper.insert(kwsExportFilterBbox);
	}
	
	/////////////////////////////////////////////
	/// @fn addExportFilterDong
	/// @brief 함수 간략한 설명 : 동 필터 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterDong
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportFilterDong(KwsExportFilterDong kwsExportFilterDong, Long exportNo) {
		kwsExportFilterDong.setExportNo(exportNo);
		kwsExportFilterDongMapper.insert(kwsExportFilterDong);
	}
	
	/////////////////////////////////////////////
	/// @fn addExportFilterImage
	/// @brief 함수 간략한 설명 : 이미지 필터 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterImage
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportFilterImage(KwsExportFilterImage kwsExportFilterImage, Long exportNo) {
		kwsExportFilterImage.setExportNo(exportNo);
		kwsExportFilterImageMapper.insert(kwsExportFilterImage);
	}
	
	/////////////////////////////////////////////
	/// @fn addExportFilterOutput
	/// @brief 함수 간략한 설명 : 출력 필터 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterOutput
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportFilterOutput(KwsExportFilterOutpt kwsExportFilterOutpt, Long exportNo) {
		kwsExportFilterOutpt.setExportNo(exportNo);
		kwsExportFilterOutptMapper.insert(kwsExportFilterOutpt);
	}
	
	/////////////////////////////////////////////
	/// @fn addExportFilterPolygon
	/// @brief 함수 간략한 설명 : 내보내기 필터 다각형 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExportFilterPolygon
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportFilterPolygon(KwsExportFilterPolygon kwsExportFilterPolygon, Long exportNo) {
		kwsExportFilterPolygon.setExportNo(exportNo);
		kwsExportFilterPolygonMapper.insert(kwsExportFilterPolygon);
	}
	
	/////////////////////////////////////////////
	/// @fn addExportFile
	/// @brief 함수 간략한 설명 : 내보내기 파일 등록 (이미지)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @param bufferedImage
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportFile(KwsExport kwsExport, BufferedImage bufferedImage) throws FdlException, IOException {
		// 파일 등록
		KwsFile kwsFile = fileService.addFile(bufferedImage, kwsExport.getExportNm(), FOLDER_NAME, FILE_NAME_PREFIX);
		Long fileNo = kwsFile.getFileNo();
	
		// 내보내기 파일 등록
		KwsExportFile kwsExportFile = new KwsExportFile();
		kwsExportFile.setExportNo(kwsExport.getExportNo());
		kwsExportFile.setFileNo(fileNo);
		kwsExportFile.setFileOrdr(0);
		kwsExportFileMapper.insert(kwsExportFile);
	}
	
	/////////////////////////////////////////////
	/// @fn addExportFile
	/// @brief 함수 간략한 설명 : 내보내기 파일 등록 (파일)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @param file
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void addExportFile(KwsExport kwsExport, File file) throws FdlException, IOException {
		// 파일 등록
		String extension = null;
		ExportTy exportTy = kwsExport.getExportTy();
		if(exportTy.equals(ExportTy.DXF)) {
			extension = "dxf";
		}
		else if(exportTy.equals(ExportTy.EXCEL) || exportTy.equals(ExportTy.SHAPE)) {
			extension = "zip";
		}
		
		KwsFile kwsFile = fileService.addFile(file, kwsExport.getExportNm(), extension, FOLDER_NAME, FILE_NAME_PREFIX);
		Long fileNo = kwsFile.getFileNo();
	
		// 내보내기 파일 등록
		KwsExportFile kwsExportFile = new KwsExportFile();
		kwsExportFile.setExportNo(kwsExport.getExportNo());
		kwsExportFile.setFileNo(fileNo);
		kwsExportFile.setFileOrdr(0);
		kwsExportFileMapper.insert(kwsExportFile);
	}

	/////////////////////////////////////////////
	/// @fn convertSpatialSearchDTOByKwsExport
	/// @brief 함수 간략한 설명 : 내보내기 객체를 내보내기 검색 DTO로 변환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 수정자 : 이승재, 2021.02.19
	///   수정내용 : 법정동 기준 내보내기 시(연관검색 기능 사용) 연관테이블이  PK필드명을 검색대상 테이블의 PK필드명을 사용하고 있었던 문제 수정
	///          공간정보의 PK컬럼을 모두 OBJECTID로 사용하고 있어 현실적으로는 문제 없으나, 검색대상 테이블과 연관테이블과 PK컬럼명이 다를수 있다는 가정적용 필요
	///          법정동기준 내보내기 외 경우는 테스트 하지 않음
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private SpatialSearchDTO convertSpatialSearchDTOByKwsExport(KwsExport kwsExport) {
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		
		Long exportNo = kwsExport.getExportNo();
		ExportFilterTy exportFilterTy = kwsExport.getExportFilterTy();
		
		// 데이터 목록
		List<String> dataIds = new ArrayList<String>();
		List<KwsExportData> kwsExportDatas = kwsExport.getKwsExportDatas();
		for(KwsExportData kwsExportData : kwsExportDatas) {
			dataIds.add(kwsExportData.getDataId());
		}
		spatialSearchDTO.setDataIds(dataIds);
		
		switch(exportFilterTy) {
			case FIDS : 
				FilterFidsDTO filterFidsDTO = new FilterFidsDTO();
				List<Long> fids = new ArrayList<Long>();
				List<KwsExportFilterFid> kwsExportFilterFids = kwsExportFilterFidMapper.listAll(exportNo);
				for(KwsExportFilterFid kwsExportFilterFid : kwsExportFilterFids) {
					fids.add(kwsExportFilterFid.getFid());
				}
				filterFidsDTO.setFids(fids);
				
				spatialSearchDTO.setFilterType(FilterType.FIDS);
				spatialSearchDTO.setFilterFidsDTO(filterFidsDTO);
				break;
			case DONG : 
				FilterRelationDTO filterRelationDTO = new FilterRelationDTO();
				KwsExportFilterDong kwsExportFilterDong = kwsExportFilterDongMapper.selectOne(exportNo);
				filterRelationDTO.setRelationDataId("BML_BADM_AS");
				filterRelationDTO.setRelationDataPkColumnName("OBJECTID");
				filterRelationDTO.setSpatialType(SpatialType.INTERSECTS);
				filterRelationDTO.setFid(kwsExportFilterDong.getDongId());
				
				spatialSearchDTO.setFilterType(FilterType.RELATION);
				spatialSearchDTO.setFilterRelationDTO(filterRelationDTO);
				break;
			case BBOX : 
				FilterBboxDTO filterBboxDTO = new FilterBboxDTO();
				KwsExportFilterBbox kwsExportFilterBbox = kwsExportFilterBboxMapper.selectOne(exportNo);
				filterBboxDTO.setXmin(kwsExportFilterBbox.getMinX());
				filterBboxDTO.setYmin(kwsExportFilterBbox.getMinY());
				filterBboxDTO.setXmax(kwsExportFilterBbox.getMaxX());
				filterBboxDTO.setYmax(kwsExportFilterBbox.getMaxY());
				
				spatialSearchDTO.setFilterType(FilterType.BBOX);
				spatialSearchDTO.setFilterBboxDTO(filterBboxDTO);
				break;
			case POLYGON :
				KwsExportFilterPolygon kwsExportFilterPolygon = kwsExportFilterPolygonMapper.selectOne(exportNo);
				FilterSpatialDTO filterSpatialDTO = new FilterSpatialDTO();
				filterSpatialDTO.setSpatialType(SpatialType.INTERSECTS);
				filterSpatialDTO.setWkt(kwsExportFilterPolygon.getWkt());
				
				spatialSearchDTO.setFilterType(FilterType.SPATIAL);
				spatialSearchDTO.setFilterSpatialDTO(filterSpatialDTO);
				break;
			default : break;
		}
		
		return spatialSearchDTO;
	}
	
	/////////////////////////////////////////////
	/// @fn modifyProgrsSttus
	/// @brief 함수 간략한 설명 : 진행 단계 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void modifyProgrsSttus(KwsExport kwsExport) {
		if(exportAuthorService.checkAutoConsent(kwsExport)) {
			modifyProgrsSttusConsent(kwsExport);
		}
		else {
			modifyProgrsSttusConsentWaiting(kwsExport);
		}
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportService#getUserGraphics(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public String getUserGraphics(Long exportNo) throws JsonProcessingException, IOException {
		String symbolUrl = messageSource.getMessage("Map.Url.Symbol", null, Locale.getDefault());
		
		KwsExportFilterOutpt kwsExportFilterOutpt = kwsExportFilterOutptMapper.selectOne(exportNo);
		String userGraphics = kwsExportFilterOutpt.getUserGraphics();
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(userGraphics);
		ArrayNode features = (ArrayNode) jsonNode.path("features");
		for(JsonNode feature : features) {
			ObjectNode properties = (ObjectNode) feature.path("properties");
			JsonNode size = properties.path("size");
			if(!(size instanceof MissingNode)) {
				String sizeStr = size.asText();
				if(StringUtils.isNotBlank(sizeStr) && sizeStr != "null") {
					properties.remove("size");
					ArrayNode arrayNode = objectMapper.createArrayNode();
					arrayNode.add(Integer.parseInt(sizeStr));
					arrayNode.add(Integer.parseInt(sizeStr));
					properties.put("size", arrayNode);
				}
			}
			
			JsonNode anchor = properties.path("anchor");
			if(!(anchor instanceof MissingNode)) {
				properties.remove("anchor");
				Double[] anchors = getAnchor(anchor.asText());
				ArrayNode arrayNode = objectMapper.createArrayNode();
				arrayNode.add(anchors[0]);
				arrayNode.add(anchors[1]);
				properties.put("anchor", arrayNode);
			}
			
			JsonNode resource = properties.path("resource");
			if(!(resource instanceof MissingNode)) {
				if(!resource.asText().startsWith("http")) {
					String symbolName = resource.asText();
					properties.remove("resource");
					properties.put("resource", symbolUrl + "/" + symbolName);
				}
			}
			
			JsonNode strokeDasharray = properties.path("strokeDasharray");
			if(!(strokeDasharray instanceof MissingNode)) {
				properties.remove("strokeDasharray");
				properties.put("strokeDasharray", getDashArray(strokeDasharray.asText()));
			}
		}
		
		return jsonNode.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn getDashArray
	/// @brief 함수 간략한 설명 : DashArray 변환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dashArray
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String getDashArray(String dashArray) {
		if(StringUtils.equals("dot", dashArray)) {
			return "2.0 2.0";
		}
		else if(StringUtils.equals("dash", dashArray)) {
			return "7.0 3.0";
		}
		else if(StringUtils.equals("dashDot", dashArray)) {
			return "10.0 2.0 2.0 2.0";
		}
		else if(StringUtils.equals("dashDotDot", dashArray)) {
			return "10.0 2.0 2.0 2.0 2.0 2.0";
		}
		else {
			return "0";
		}
	}

	/////////////////////////////////////////////
	/// @fn getAnchor
	/// @brief 함수 간략한 설명 : anchor 변환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param anchor
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Double[] getAnchor(String anchor) {
		Double[] anchors = new Double[2];
		if(StringUtils.equals("lt", anchor)) {
			anchors[0] = 0d;
			anchors[1] = 0d;
		}
		else if(StringUtils.equals("ct", anchor)) {
			anchors[0] = 0.5d;
			anchors[1] = 0d;
		}
		else if(StringUtils.equals("rt", anchor)) {
			anchors[0] = 1d;
			anchors[1] = 0d;
		}
		else if(StringUtils.equals("lm", anchor)) {
			anchors[0] = 0d;
			anchors[1] = 0.5d;
		}
		else if(StringUtils.equals("cm", anchor)) {
			anchors[0] = 0.5d;
			anchors[1] = 0.5d;
		}
		else if(StringUtils.equals("rm", anchor)) {
			anchors[0] = 1d;
			anchors[1] = 0.5d;
		}
		else if(StringUtils.equals("lb", anchor)) {
			anchors[0] = 0d;
			anchors[1] = 1d;
		}
		else if(StringUtils.equals("cb", anchor)) {
			anchors[0] = 0.5d;
			anchors[1] = 1d;
		}
		else if(StringUtils.equals("rb", anchor)) {
			anchors[0] = 1d;
			anchors[1] = 1d;
		}
		else {
			anchors[0] = 0.5;
			anchors[1] = 0.5;
		}
		return anchors;
	}

}
