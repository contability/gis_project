package kr.co.gitt.kworks.projects.gn.service.ocpat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.KwsOcpatRegMapper;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsOcpatReg;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSearchResultDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSearchSummaryDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSortDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSpatialSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegstrDTO;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOccaDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOccnDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcdrDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcdsDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcpeDtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcreDtMapper;
import kr.co.gitt.kworks.projects.gn.model.OcpatEditHi;
import kr.co.gitt.kworks.projects.gn.model.RdtOccaDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOccnDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcdrDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcdsDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpeDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcreDt;
import kr.co.gitt.kworks.repository.OcpatRegisterSearchResository;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import freemarker.template.utility.StringUtil;

@Service("ocpatRegstrService")
@Profile({"gn_dev", "gn_oper"})
public class OcpatRegstrServiceImpl implements OcpatRegstrService {
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 점용대장 목록
	 */
	@Resource
	KwsOcpatRegMapper kwsOcpatRegMapper;	
	
	/**
	 * 허가대장 맵퍼
	 */
	@Resource
	RdtOcpeDtMapper rdtOcpeDtMapper;	
	
	/**
	 * 변경대장 맵퍼
	 */
	@Resource
	RdtOccnDtMapper rdtOccnDtMapper;	

	/**
	 * 원상회복대장 맵퍼
	 */
	@Resource
	RdtOcreDtMapper rdtOcreDtMapper;	

	/**
	 * 불허가대장 맵퍼
	 */
	@Resource
	RdtOcdsDtMapper rdtOcdsDtMapper;	

	/**
	 * 취하원대장 맵퍼
	 */
	@Resource
	RdtOcdrDtMapper rdtOcdrDtMapper;	

	/**
	 * 취소신청 대장 맵퍼
	 */
	@Resource
	RdtOccaDtMapper rdtOccaDtMapper;	
	
	/**
	 * 데이터 서비스
	 */
	@Resource
	DataService dataService;

	/**
	 * 도메인 서비스
	 */
	@Resource
	DomnCodeService domnCodeService;

	/**
	 * 점용대장 이력관리 서비스 
	 */
	@Resource
	OcpatRegstrHistoryService ocpatRegstrHistoryService;
	
	/**
	 * 점용대장 검색 저장소
	 */
	@Resource
	OcpatRegisterSearchResository ocpatRegisterSearchResository;
	
	/**
	 * 허가대장 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService rdtOcpeDtIdGnrService;
	
	/**
	 * 변경대장 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService rdtOccnDtIdGnrService;
	
	/**
	 * 원상회복대장 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService rdtOcreDtIdGnrService;

	/**
	 * 불허가대장 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService rdtOcdsDtIdGnrService;

	/**
	 * 취하원대장 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService rdtOcdrDtIdGnrService;

	/**
	 * 취소신청대장 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService rdtOccaDtIdGnrService;
	
	/**
	 * 메세지 소스
	 */
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	
	@Override
	public Integer reloadUmdNam() {
		int result = 0;
		List<KwsOcpatReg> registerList = kwsOcpatRegMapper.listAll();
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		for(KwsOcpatReg register : registerList) {
			switch(register.getOcpatIdn()){
			case 100100:
			case 200100:
			case 300100:
			case 400100:
				result += reloadOcpeUmdNam(register, keyString);
				break;
				
			case 500100:
				result += reloadOccnUmdNam(register, keyString);
				break;
				
			case 600100:
				result += reloadOcreUmdNam(register, keyString);
				break;
				
			case 700100:
				result += reloadOcdsUmdNam(register, keyString);
				break;
				
			case 800100:
				result += reloadOccaUmdNam(register, keyString);
				break;
				
			case 900100:
				result += reloadOcdrUmdNam(register, keyString);
				break;
			}
		}
		
		return result;
	}
	
	private Integer reloadOcpeUmdNam(KwsOcpatReg register, String keyStr) {
		
		String idnField = register.getOcpatIdnField();
		String cdeField = register.getOcpatCdeField();

		List<String> spatialLayers = new ArrayList<String>();
		spatialLayers.add(register.getOcpatPsLayer());
		spatialLayers.add(register.getOcpatLsLayer());
		spatialLayers.add(register.getOcpatAsLayer());

		int count = 0;
		List<RdtOcpeDt> listAll = rdtOcpeDtMapper.selectAll(keyStr);
		if (listAll != null && listAll.size() > 0) {
			
			for (RdtOcpeDt data : listAll) {

				StringBuffer cql = new StringBuffer();
				cql.append(cdeField);
				cql.append(" = '");
				cql.append(data.getFtrCde());
				cql.append("' AND ");
				cql.append(idnField);
				cql.append(" = ");
				cql.append(data.getFtrIdn());
				
				for (String queryLayer : spatialLayers) {
					KwsData queryData = dataService.selectOneData(queryLayer);
					validatorDataAndDataField(queryData, queryLayer);

					try
					{
						StringBuilder umdNam = new StringBuilder();
						List<EgovMap> features = ocpatRegisterSearchResository.listAll(queryData, cql.toString(), false, "OBJECTID", "ASC");
						if (features != null && features.size() > 0) {
							int cnt = 0;
							for (int i = 0; i < features.size(); i++) {
								EgovMap feature = features.get(i);
								String hjdNam = (String)feature.get("hjdCde");
								if (hjdNam != null && hjdNam.length() > 0) {
									if (umdNam.indexOf(hjdNam) >= 0)
										continue;
									
									if (cnt > 0) {
										umdNam.append(",");
									}
									umdNam.append(hjdNam);
									cnt++;
								}
							}
						}
						
						if (umdNam.length() > 0) {
							count += rdtOcpeDtMapper.updateUmdNam(umdNam.toString(), data.getFtrCde(), data.getFtrIdn());
						}
					}
					catch(Exception ex)
					{
						
					}
				}				
			}
		}
		
		return count;
	}
	
	private Integer reloadOccnUmdNam(KwsOcpatReg register, String keyStr) {
		
		String idnField = register.getOcpatIdnField();
		String cdeField = register.getOcpatCdeField();

		List<String> spatialLayers = new ArrayList<String>();
		spatialLayers.add(register.getOcpatPsLayer());
		spatialLayers.add(register.getOcpatLsLayer());
		spatialLayers.add(register.getOcpatAsLayer());

		int count = 0;
		List<RdtOccnDt> listAll = rdtOccnDtMapper.selectAll(keyStr);
		if (listAll != null && listAll.size() > 0) {
			
			for (RdtOccnDt data : listAll) {

				StringBuffer cql = new StringBuffer();
				cql.append(cdeField);
				cql.append(" = '");
				cql.append(data.getFtrCde());
				cql.append("' AND ");
				cql.append(idnField);
				cql.append(" = ");
				cql.append(data.getFtrIdn());
				
				for (String queryLayer : spatialLayers) {
					KwsData queryData = dataService.selectOneData(queryLayer);
					validatorDataAndDataField(queryData, queryLayer);

					try
					{
						StringBuilder umdNam = new StringBuilder();
						List<EgovMap> features = ocpatRegisterSearchResository.listAll(queryData, cql.toString(), false, "OBJECTID", "ASC");
						if (features != null && features.size() > 0) {
							int cnt = 0;
							for (int i = 0; i < features.size(); i++) {
								EgovMap feature = features.get(i);
								String hjdNam = (String)feature.get("hjdCde");
								if (hjdNam != null && hjdNam.length() > 0) {
									if (umdNam.indexOf(hjdNam) >= 0)
										continue;
									
									if (cnt > 0) {
										umdNam.append(",");
									}
									umdNam.append(hjdNam);
									cnt++;
								}
							}
						}
						
						if (umdNam.length() > 0) {
							count += rdtOccnDtMapper.updateUmdNam(umdNam.toString(), data.getFtrCde(), data.getFtrIdn());
						}
					}
					catch(Exception ex) 
					{
						
					}
				}				
			}
		}
		
		return count;
	}
	
	private Integer reloadOcreUmdNam(KwsOcpatReg register, String keyStr) {
		
		String idnField = register.getOcpatIdnField();
		String cdeField = register.getOcpatCdeField();

		List<String> spatialLayers = new ArrayList<String>();
		spatialLayers.add(register.getOcpatPsLayer());
		spatialLayers.add(register.getOcpatLsLayer());
		spatialLayers.add(register.getOcpatAsLayer());

		int count = 0;
		List<RdtOcreDt> listAll = rdtOcreDtMapper.selectAll(keyStr);
		if (listAll != null && listAll.size() > 0) {
			
			for (RdtOcreDt data : listAll) {

				StringBuffer cql = new StringBuffer();
				cql.append(cdeField);
				cql.append(" = '");
				cql.append(data.getFtrCde());
				cql.append("' AND ");
				cql.append(idnField);
				cql.append(" = ");
				cql.append(data.getFtrIdn());
				
				for (String queryLayer : spatialLayers) {
					KwsData queryData = dataService.selectOneData(queryLayer);
					validatorDataAndDataField(queryData, queryLayer);

					try
					{
						StringBuilder umdNam = new StringBuilder();
						List<EgovMap> features = ocpatRegisterSearchResository.listAll(queryData, cql.toString(), false, "OBJECTID", "ASC");
						if (features != null && features.size() > 0) {
							int cnt = 0;
							for (int i = 0; i < features.size(); i++) {
								EgovMap feature = features.get(i);
								String hjdNam = (String)feature.get("hjdCde");
								if (hjdNam != null && hjdNam.length() > 0) {
									if (umdNam.indexOf(hjdNam) >= 0)
										continue;
									
									if (cnt > 0) {
										umdNam.append(",");
									}
									umdNam.append(hjdNam);
									cnt++;
								}
							}
						}
						
						if (umdNam.length() > 0) {
							count += rdtOcreDtMapper.updateUmdNam(umdNam.toString(), data.getFtrCde(), data.getFtrIdn());
						}
					}
					catch(Exception ex)
					{
						
					}
				}				
			}
		}
		
		return count;
	}
	
	private Integer reloadOcdsUmdNam(KwsOcpatReg register, String keyStr) {

		int count = 0;
		List<RdtOcdsDt> listAll = rdtOcdsDtMapper.selectAll(keyStr);
		if (listAll != null && listAll.size() > 0) {
			// 현 대장의 최초대장 정보로 읍면동 업데이트
			for (RdtOcdsDt data : listAll) {
				RdtOcpeDt old = rdtOcpeDtMapper.selectOneById(data.getOldCde(), data.getOldIdn(), keyStr);
				if (old != null) {
					count += rdtOcdsDtMapper.updateUmdNam(old.getUmdNam(), data.getFtrCde(), data.getFtrIdn());
				}
			}
		}
		
		return count;
	}
	
	private Integer reloadOccaUmdNam(KwsOcpatReg register, String keyStr) {

		int count = 0;
		List<RdtOccaDt> listAll = rdtOccaDtMapper.selectAll(keyStr);
		if (listAll != null && listAll.size() > 0) {
			// 현 대장의 최초대장 정보로 읍면동 업데이트
			for (RdtOccaDt data : listAll) {
				RdtOcpeDt old = rdtOcpeDtMapper.selectOneById(data.getOldCde(), data.getOldIdn(), keyStr);
				if (old != null) {
					count += rdtOccaDtMapper.updateUmdNam(old.getUmdNam(), data.getFtrCde(), data.getFtrIdn());
				}
			}
		}
		
		return count;
	}
	
	private Integer reloadOcdrUmdNam(KwsOcpatReg register, String keyStr) {

		int count = 0;
		List<RdtOcdrDt> listAll = rdtOcdrDtMapper.selectAll(keyStr);
		if (listAll != null && listAll.size() > 0) {
			// 현 대장의 최초대장 정보로 읍면동 업데이트
			for (RdtOcdrDt data : listAll) {
				RdtOcpeDt old = rdtOcpeDtMapper.selectOneById(data.getOldCde(), data.getOldIdn(), keyStr);
				if (old != null) {
					count += rdtOcdrDtMapper.updateUmdNam(old.getUmdNam(), data.getFtrCde(), data.getFtrIdn());
				}
			}
		}
		
		return count;
	}
	
	@Override
	public List<KwsOcpatReg> listAll() {
		return kwsOcpatRegMapper.listAll();
	}
	
	@Override
	public KwsOcpatReg selectOne(String ocpatIdn) {
		KwsOcpatReg result = kwsOcpatRegMapper.selectOne(ocpatIdn);
		return result;
	}
	
	private void validatorDataAndDataField(KwsData kwsData, String layerId) {
		/// 데이터 없음
		if(kwsData == null) {
			logger.warn("No Data : " + layerId);
			throw new RuntimeException("No Data : " + layerId);
		}
		
		List<KwsDataField> dataFields = kwsData.getKwsDataFields();
		/// 데이터 필드 없음
		if(dataFields == null || dataFields.size() < 1) {
			logger.warn("No Data Field : " + layerId);
			throw new RuntimeException("No Data Field : " + layerId);
		}
	}
	
	@Override
	public List<OcpatRegisterSearchSummaryDTO> searchByBBox(KwsOcpatReg ocpatReg, OcpatRegisterSpatialSearchDTO opatRegBBoxSearchDTO) throws Exception {
		List<OcpatRegisterSearchSummaryDTO> result = new ArrayList<OcpatRegisterSearchSummaryDTO>();
		
		OcpatRegisterSearchSummaryDTO ocpatSummary = listAllSummary(ocpatReg, opatRegBBoxSearchDTO);
		if(ocpatSummary.getFeatureIds() != null && ocpatSummary.getFeatureIds().size() > 0)
			result.add(ocpatSummary);
		
		return result;
	}	
	
	private OcpatRegisterSearchSummaryDTO listAllSummary(KwsOcpatReg ocpatRegister, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO) {
		String layerId = ocpatRegister.getOcpatLayerId();
		String regAlias = ocpatRegister.getOcpatAlias();
		
		OcpatRegisterSearchSummaryDTO result = new OcpatRegisterSearchSummaryDTO();
		result.setOcpatIdn(ocpatRegister.getOcpatIdn().toString());
		result.setOcpatAlias(regAlias);
		
		KwsData kwsData = dataService.selectOneData(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		String idnField = ocpatRegister.getOcpatIdnField();
		String cdeField = ocpatRegister.getOcpatCdeField();

		List<String> spatialLayers = new ArrayList<String>();
		spatialLayers.add(ocpatRegister.getOcpatPsLayer());
		spatialLayers.add(ocpatRegister.getOcpatLsLayer());
		spatialLayers.add(ocpatRegister.getOcpatAsLayer());
		
		List<String> idList = new ArrayList<String>();
		
		if (ocpatRegister.getOcpatIdn() != 700100 && ocpatRegister.getOcpatIdn() != 800100 && ocpatRegister.getOcpatIdn() != 900100) {
			StringBuffer cql = new StringBuffer();
			if (cdeField != null && cdeField.length() > 0) {
				cql.append(cdeField);
				cql.append(" = '");
				cql.append(ocpatRegister.getOcpatPropValue());
				cql.append("'");
			}
		
			for (String queryLayer : spatialLayers) {
				KwsData queryData = dataService.selectOneData(queryLayer);
				validatorDataAndDataField(queryData, queryLayer);

				// 점용시설물 공간검색으로 점용대장 관리번호 가져오기 
				List<Long> ids = ocpatRegisterSearchResository.listAllSummary(queryData, idnField, ocpatSpatialSearchDTO, cql.toString());
				if(ids != null && ids.size() > 0) {
					for (int i = 0; i < ids.size(); i++) {
						idList.add(ids.get(i).toString());
					}
				}
			}
		}
		else {// 불허가, 취하원, 점용취소의 경우 별도의 공간정보가 없어 최초대장의 공간정보를 활용해야함.
			// 점용시설물 공간검색으로 점용대장 지형지물번호 및 관리번호 가져오기 
			for (String queryLayer : spatialLayers) {
				KwsData queryData = dataService.selectOneData(queryLayer);
				validatorDataAndDataField(queryData, queryLayer);
			
				List<EgovMap> datas = ocpatRegisterSearchResository.listAllSummary(queryData, cdeField, idnField, ocpatSpatialSearchDTO);
				for (EgovMap data : datas) {
					StringBuffer cql = new StringBuffer();
					cql.append("OLD_CDE= '");
					cql.append(data.get("ftfCde"));
					cql.append("' AND OLD_IDN = ");
					cql.append(data.get("ftfIdn"));
					
					// 점용시설물의 대장 지형지물번호 및 관리번호와 일치하는 대장을 검색
					List<Long> ids = ocpatRegisterSearchResository.listAllSummary(kwsData, "FTR_IDN", cql.toString());
					if(ids != null && ids.size() > 0) {
						for (int i = 0; i < ids.size(); i++) {
							idList.add(ids.get(i).toString());
						}
					}
				}
			}
		}
//		else {// 불허가, 취하원, 점용취소의 경우 별도의 공간정보가 없어 지적의 공간정보를 활용해야함. <- 전체 영역의 경우 사용불가
//			// 지적 공간검색으로 PNU 가져오기
//			String landLayer = ocpatSpatialSearchDTO.getLand();
//			KwsData landData = dataService.selectOneData(landLayer);
//			validatorDataAndDataField(landData, landLayer);
//			
//			List<EgovMap> datas = ocpatRegisterSearchResository.listAllSummary(landData, "PNU", ocpatSpatialSearchDTO);
//			if (datas != null && datas.size() > 0) {
//				int count = 0;
//				StringBuffer cql = new StringBuffer();
//				cql.append(" PNU IN (");
//				for (EgovMap data : datas) {
//					if (count > 0)
//						cql.append(",");
//					
//					cql.append(data.get("pnu"));
//				}
//				cql.append(") ");
//				
//				// PNU가 일치하는 대장을 검색
//				List<Long> ids = ocpatRegisterSearchResository.listAllSummary(kwsData, "FTR_IDN", cql.toString());
//				if(ids != null && ids.size() > 0) {
//					List<String> idList = new ArrayList<String>();
//					for (int i = 0; i < ids.size(); i++)
//						idList.add(ids.get(i).toString());
//					
//					OcpatRegisterSearchResultDTO res = new OcpatRegisterSearchResultDTO();
//					res.setDataId(kwsData.getDataId());
//					res.setDataAlias(kwsData.getDataAlias());
//					res.setIds(idList);
//					
//					summary.add(res);
//				}
//			}
//		}
		
		if (idList.size() > 0) {
			OcpatRegisterSearchResultDTO res = new OcpatRegisterSearchResultDTO();
			res.setDataId(kwsData.getDataId());
			res.setDataAlias(kwsData.getDataAlias());
			res.setIds(idList);
			
			List<OcpatRegisterSearchResultDTO> summary = new ArrayList<OcpatRegisterSearchResultDTO>();
			summary.add(res);
			
			result.setFeatureIds(summary);
		}
		
		return result;
	}
	
	@Override
	public OcpatRegisterSearchSummaryDTO sortRegister(KwsOcpatReg ocpatReg, OcpatRegisterSortDTO ocpatRegisterSortDTO) throws Exception	{
		OcpatRegisterSearchSummaryDTO result = null;
		
		String layer = ocpatRegisterSortDTO.getLayerId();
		String idnField = ocpatRegisterSortDTO.getIdnField();
		List<String> ids = ocpatRegisterSortDTO.getFeatureIds();
		
		String sortField = ocpatRegisterSortDTO.getSortField();
		String sortDirection = ocpatRegisterSortDTO.getSortDirection();
		
		List<OcpatRegisterSearchResultDTO> featureIds = new ArrayList<OcpatRegisterSearchResultDTO>();
		OcpatRegisterSearchResultDTO subResult = sort(layer, idnField, ids, sortField, sortDirection);
		if (subResult == null)
			return null;
		
		if(subResult.getIds() != null && subResult.getIds().size() > 0) {
			featureIds.add(subResult);
		}
		
		if (featureIds.size() > 0) {
			result = new OcpatRegisterSearchSummaryDTO();
			result.setOcpatIdn(ocpatReg.getOcpatIdn().toString());
			result.setOcpatAlias(ocpatReg.getOcpatAlias());
			result.setLayerId(layer);
			result.setFeatureIds(featureIds);
		}
		
		return result;
	}
	
	private OcpatRegisterSearchResultDTO sort(String layerId, String idnField, List<String> featureIds, String sortField, String sortDirection) {
		OcpatRegisterSearchResultDTO result = null;
		
		KwsData kwsData = dataService.selectOneData(layerId);
		validatorDataAndDataField(kwsData, layerId);

		List<Long> ids = ocpatRegisterSearchResository.listAllSummary(kwsData, idnField, featureIds, sortField, sortDirection);
		if(ids != null && ids.size() > 0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++)
				idList.add(ids.get(i).toString());
			
			result = new OcpatRegisterSearchResultDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(idList);
		}
		
		return result;
	}
	
	@Override
	public List<EgovMap> searchRegister(KwsOcpatReg ocpatReg, OcpatRegisterSortDTO ocpatRegisterSortDTO) throws Exception {
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String layerId = ocpatRegisterSortDTO.getLayerId();
		String idnField = ocpatRegisterSortDTO.getIdnField();
		List<String> ids = ocpatRegisterSortDTO.getFeatureIds();
		
		String sortField = ocpatRegisterSortDTO.getSortField();
		String sortDirection = ocpatRegisterSortDTO.getSortDirection();
		boolean isOriginValue = false;
		if (ocpatRegisterSortDTO.getIsOriginValue() != null && ocpatRegisterSortDTO.getIsOriginValue())
			isOriginValue = true;
		
		KwsData kwsData = dataService.selectOneData(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		List<EgovMap> features = ocpatRegisterSearchResository.listAll(kwsData, idnField, ids, isOriginValue, sortField, sortDirection);
		if (ocpatReg.getOcpatIdn() != 700100 && ocpatReg.getOcpatIdn() != 800100 && ocpatReg.getOcpatIdn() != 900100) {
			for (EgovMap feature : features) {
				searchSpatialFeature(ocpatReg, idnField, feature, isOriginValue);
				result.add(feature);
			}
		}
		else {// 불허가, 취하원, 점용취소의 경우 별도의 공간정보가 없어 최초대장의 공간정보를 활용해야함.
			for (EgovMap feature : features) {// 최초대장과 연결되어있는 공간정보를 가져옴.
				searchSpatialFeature(ocpatReg, feature);
				result.add(feature);
			}
		}
			
		return result;
	}
	
	private void searchSpatialFeature(KwsOcpatReg ocpatReg, String ftrIdn, EgovMap feature, boolean isOriginValue) throws Exception {
		String geomName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault()).toLowerCase();

		String idnField = ocpatReg.getOcpatIdnField();
		String cdeField = ocpatReg.getOcpatCdeField();
		String ftrCde = ocpatReg.getOcpatPropValue();
		
		StringBuffer cql = new StringBuffer();
		if (cdeField != null && cdeField.length() > 0) {
			cql.append(cdeField);
			cql.append(" = '");
			cql.append(ftrCde);
			cql.append("' AND ");
			cql.append(idnField);
			cql.append(" = ");
			cql.append(feature.get(CamelUtil.convert2CamelCase(ftrIdn)));
		}			

		List<String> spatialLayers = new ArrayList<String>();
		spatialLayers.add(ocpatReg.getOcpatPsLayer());
		spatialLayers.add(ocpatReg.getOcpatLsLayer());
		spatialLayers.add(ocpatReg.getOcpatAsLayer());
		
		for (String queryLayer : spatialLayers) {
			KwsData queryData = dataService.selectOneData(queryLayer);
			validatorDataAndDataField(queryData, queryLayer);
			
			List<String> listGeom = new ArrayList<String>();
			List<EgovMap> datas = ocpatRegisterSearchResository.listAll(queryData, cql.toString(), isOriginValue, "OBJECTID", "ASC");
			if (datas != null && datas.size() > 0) {
				for (int i = 0; i < datas.size(); i++) {
					EgovMap data = datas.get(i);
					listGeom.add(data.get(geomName).toString());
				}
			}
			
			if (listGeom.size() > 0) {
				feature.put(geomName.toLowerCase(), listGeom);
			}
		}
		
		return;
	}

	private void searchSpatialFeature(KwsOcpatReg ocpatReg, EgovMap feature) throws Exception {
		String geomName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault()).toLowerCase();

		String idnField = ocpatReg.getOcpatIdnField();
		String cdeField = ocpatReg.getOcpatCdeField();
		KwsDomnCode kdc = domnCodeService.selectCodeIdByName("KWS-0445", (String)feature.get("oldCde"));
		if (kdc == null)
			return;
		
		String ftrCde = kdc.getCodeId();

		StringBuffer cql = new StringBuffer();
		if (cdeField != null && cdeField.length() > 0) {
			cql.append(cdeField);
			cql.append(" = '");
			cql.append(ftrCde);
			cql.append("' AND ");
			cql.append(idnField);
			cql.append(" = ");
			cql.append(feature.get("oldIdn"));
		}			
		
		List<String> spatialLayers = new ArrayList<String>();
		spatialLayers.add(ocpatReg.getOcpatPsLayer());
		spatialLayers.add(ocpatReg.getOcpatLsLayer());
		spatialLayers.add(ocpatReg.getOcpatAsLayer());
		
		for (String queryLayer : spatialLayers) {
			KwsData queryData = dataService.selectOneData(queryLayer);
			validatorDataAndDataField(queryData, queryLayer);
			
			List<String> listGeom = new ArrayList<String>();
			List<EgovMap> datas = ocpatRegisterSearchResository.listAll(queryData, cql.toString(), true, "OBJECTID", "ASC");
			if (datas != null && datas.size() > 0) {
				for (int i = 0; i < datas.size(); i++) {
					EgovMap data = datas.get(i);
					listGeom.add(data.get(geomName).toString());
				}
			}
			
			if (listGeom.size() > 0) {
				feature.put(geomName.toLowerCase(), listGeom);
			}
		}
		
		return;
	}
	
	@Override
	public List<OcpatRegisterSearchSummaryDTO> searchSummaries(List<KwsOcpatReg> registerList, OcpatRegisterSearchDTO ocpatRegisterSearchDTO) throws Exception
	{
		List<OcpatRegisterSearchSummaryDTO> result = new ArrayList<OcpatRegisterSearchSummaryDTO>();
		
		for(KwsOcpatReg register : registerList) {
			OcpatRegisterSearchSummaryDTO searchResult = listAllSummary(register, ocpatRegisterSearchDTO);
			if(searchResult.getFeatureIds() != null && searchResult.getFeatureIds().size() > 0) {
				result.add(searchResult);
			}
		}
		
		return result;
	}

	private OcpatRegisterSearchSummaryDTO listAllSummary(KwsOcpatReg register, OcpatRegisterSearchDTO ocpatRegisterSearchDTO) throws Exception {
		String layerId = register.getOcpatLayerId();
		String regAlias = register.getOcpatAlias();
		
		OcpatRegisterSearchSummaryDTO result = new OcpatRegisterSearchSummaryDTO();
		result.setOcpatIdn(register.getOcpatIdn().toString());
		result.setOcpatAlias(regAlias);
		result.setLayerId(layerId);
		
		KwsData kwsData = dataService.selectOneData(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		String idnField = "FTR_IDN";
		String cdeField = "FTR_CDE";
		String ftrCde = register.getOcpatPropValue();
		
		StringBuffer cql = new StringBuffer();
		cql.append(cdeField);
		cql.append(" = '");
		cql.append(ftrCde).append("'");

		// 허가번호
		String pmtNum = ocpatRegisterSearchDTO.getPmtNum();
		if (pmtNum != null && pmtNum.length() > 0) {
			cql.append(" AND PMT_NUM Like '%");
			cql.append(pmtNum).append("%'");
		}
		
		// 허가일
		String pmtYmdMin = ocpatRegisterSearchDTO.getPmtYmdMin();
		if (pmtYmdMin != null && pmtYmdMin.length() > 0) {
			cql.append(" AND PMT_YMD >= '");
			cql.append(pmtYmdMin).append("'");
		}

		// 허가일
		String pmtYmdMax = ocpatRegisterSearchDTO.getPmtYmdMax();
		if (pmtYmdMax != null && pmtYmdMax.length() > 0) {
			cql.append(" AND PMT_YMD <= '");
			cql.append(pmtYmdMax).append("'");
		}
		
		// 점용위치
		String jygLoc = ocpatRegisterSearchDTO.getJygLoc();
		if (jygLoc != null && jygLoc.length() > 0) {
			cql.append(" AND JYG_LOC Like '%");
			cql.append(jygLoc).append("%'");
		}
		
		// 점용자 성명
		String prsNam = ocpatRegisterSearchDTO.getPrsNam();
		if (prsNam != null && prsNam.length() > 0) {
			cql.append(" AND PRS_NAM Like '%");
			cql.append(prsNam).append("%'");
		}
		
		// 점용기간 유형
		int jygUlm = ocpatRegisterSearchDTO.getJygUlm();
		if (jygUlm >= 0 && jygUlm <= 2) {
			cql.append(" AND JYG_ULM = ");
			cql.append(jygUlm);
		}
		
		// 점용시작일
		String jysYmdMin = ocpatRegisterSearchDTO.getJysYmdMin();
		if (jysYmdMin != null && jysYmdMin.length() > 0) {
			cql.append(" AND JYS_YMD >= '");
			cql.append(jysYmdMin).append("'");
		}

		// 점용시작일
		String jysYmdMax = ocpatRegisterSearchDTO.getJysYmdMax();
		if (jysYmdMax != null && jysYmdMax.length() > 0) {
			cql.append(" AND JYS_YMD <= '");
			cql.append(jysYmdMax).append("'");
		}
		
		// 점용종료일
		String jyeYmdMin = ocpatRegisterSearchDTO.getJyeYmdMin();
		if (jyeYmdMin != null && jyeYmdMin.length() > 0) {
			cql.append(" AND JYE_YMD >= '");
			cql.append(jyeYmdMin).append("'");
		}

		// 점용종료일
		String jyeYmdMax = ocpatRegisterSearchDTO.getJyeYmdMax();
		if (jyeYmdMax != null && jyeYmdMax.length() > 0) {
			cql.append(" AND JYE_YMD <= '");
			cql.append(jyeYmdMax).append("'");
		}
		
		List<OcpatRegisterSearchResultDTO> summary = new ArrayList<OcpatRegisterSearchResultDTO>();
		List<Long> ids = ocpatRegisterSearchResository.listAllSummary(kwsData, idnField, cql.toString());
		if (ids != null && ids.size() > 0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++) 
				idList.add(ids.get(i).toString());
			
			OcpatRegisterSearchResultDTO searchResult = new OcpatRegisterSearchResultDTO();
			searchResult.setDataId(kwsData.getDataId());
			searchResult.setDataAlias(kwsData.getDataAlias());
			searchResult.setIds(idList);
			summary.add(searchResult);
		}
		
		if (summary.size() > 0)
			result.setFeatureIds(summary);
		
		return result;
	}

	@Override
	public List<OcpatRegisterSearchSummaryDTO> quickSearch(List<KwsOcpatReg> registerList, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO, String pnu) throws Exception {
		
		List<OcpatRegisterSearchSummaryDTO> result = new ArrayList<OcpatRegisterSearchSummaryDTO>();
		
		for(KwsOcpatReg register : registerList) {
			OcpatRegisterSearchSummaryDTO searchResult = null;
			switch(register.getOcpatIdn()){
			case 100100:
			case 200100:
			case 300100:
			case 400100:
			case 500100:
			case 600100:
				// 공간데이터가 있는 대장				
				searchResult = listAllSummary(register, ocpatSpatialSearchDTO);
				if(searchResult == null || searchResult.getFeatureIds() == null || searchResult.getFeatureIds().size() <= 0) {
					// 점용시설물이 없는 경우 PNU로 다시 검색 
					searchResult = listAllSummary(register, pnu);					
				}
				else {
					OcpatRegisterSearchSummaryDTO searchPNU = listAllSummary(register, pnu);
					if(searchPNU != null && searchPNU.getFeatureIds() != null && searchPNU.getFeatureIds().size() > 0) {
						searchResult.getFeatureIds().get(0).getIds().addAll(searchPNU.getFeatureIds().get(0).getIds());
					}
				}
				break;
				
			case 700100:
			case 800100:
			case 900100:
				// 공간데이터가 없는 대장				
				searchResult = listAllSummary(register, pnu);
				break;
			}
			
			if(searchResult != null && searchResult.getFeatureIds() != null && searchResult.getFeatureIds().size() > 0) {
				result.add(searchResult);
			}
		}
		
		return result;
	};
	
	private OcpatRegisterSearchSummaryDTO listAllSummary(KwsOcpatReg ocpatRegister, String pnu) {
		String layerId = ocpatRegister.getOcpatLayerId();
		String regAlias = ocpatRegister.getOcpatAlias();
		
		OcpatRegisterSearchSummaryDTO result = new OcpatRegisterSearchSummaryDTO();
		result.setOcpatIdn(ocpatRegister.getOcpatIdn().toString());
		result.setOcpatAlias(regAlias);
		
		KwsData kwsData = dataService.selectOneData(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		StringBuffer cql = new StringBuffer();
		cql.append(" FTR_CDE = '");
		cql.append(ocpatRegister.getOcpatPropValue());
		cql.append("' AND PNU = '");
		cql.append(pnu);
		cql.append("'");
				
		List<OcpatRegisterSearchResultDTO> summary = new ArrayList<OcpatRegisterSearchResultDTO>();
		
		List<Long> ids = ocpatRegisterSearchResository.listAllSummary(kwsData, "FTR_IDN", cql.toString());
		if(ids != null && ids.size() > 0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++) {
				idList.add(ids.get(i).toString());
			}
			
			OcpatRegisterSearchResultDTO res = new OcpatRegisterSearchResultDTO();
			res.setDataId(kwsData.getDataId());
			res.setDataAlias(kwsData.getDataAlias());
			res.setIds(idList);
			
			summary.add(res);
		}
		
		if (summary.size() > 0)
			result.setFeatureIds(summary);
		
		return result;
	}
	
	@Override
	public String getRegisterPmtNum(String ftrCde, Long ftrIdn) {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		String pmtNum = "";
		if (ftrCde.equals("OC001") || ftrCde.equals("OC002") || ftrCde.equals("OC003") || ftrCde.equals("OC004")) {
			RdtOcpeDt reg = rdtOcpeDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (reg != null)
				pmtNum = reg.getPmtNum();
		}
		else if (ftrCde.equals("OC005")) {
			RdtOccnDt reg = rdtOccnDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (reg != null)
				pmtNum = reg.getPmtNum();
		}
		else if (ftrCde.equals("OC006")) {
			RdtOcreDt reg = rdtOcreDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (reg != null)
				pmtNum = reg.getPmtNum();
		}
		else if (ftrCde.equals("OC007")) {
			RdtOcdsDt reg = rdtOcdsDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (reg != null)
				pmtNum = reg.getPmtNum();
		}
		else if (ftrCde.equals("OC008")) {
			RdtOccaDt reg = rdtOccaDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (reg != null)
				pmtNum = reg.getPmtNum();
		}
		else if (ftrCde.equals("OC009")) {
			RdtOcdrDt reg = rdtOcdrDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (reg != null)
				pmtNum = reg.getPmtNum();
		}

		return pmtNum;
	}
	
	@Override
	public Integer insertRegster(OcpatRegstrDTO ocpatRegstr) throws Exception {
		KwsOcpatReg regstr = kwsOcpatRegMapper.selectOne(ocpatRegstr.getOcpatIdn());
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());
		
		Integer result = -1;
		switch(regstr.getOcpatIdn()) {
		case 100100:
		case 200100:
		case 300100:
		case 400100:
			result = insertRdtOcpeDt(ocpatRegstr, keyString);
			break;
			
		case 500100:	// 변경허가 대장
			result = insertRdtOccnDt(ocpatRegstr, keyString);
			break;
			
		case 600100:	// 원상회복공사 대장
			result = insertRdtOcreDt(ocpatRegstr, keyString);
			break;

		case 700100:	// 불허가
			result = insertRdtOcdsDt(ocpatRegstr, keyString);
			break;

		case 800100:	// 취소신청
			result = insertRdtOccaDt(ocpatRegstr, keyString);
			break;

		case 900100:	// 취하원
			result = insertRdtOcdrDt(ocpatRegstr, keyString);
			break;
		}
		
		return result;
	}
	
	/**
	 * 허가대장 등록 
	 * @param ocpatRegstr - 대장 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertRdtOcpeDt(OcpatRegstrDTO ocpatRegstr, String keyStr) throws Exception {
		Long ftrIdn = rdtOcpeDtIdGnrService.getNextLongId();
		
		RdtOcpeDt rdtOcpeDt = new RdtOcpeDt();
		rdtOcpeDt.setFtrIdn(ftrIdn);
		rdtOcpeDt.setFtrCde(ocpatRegstr.getFtrCde());
		rdtOcpeDt.setPmtNum(ocpatRegstr.getPmtNum());
		rdtOcpeDt.setPmtYmd(ocpatRegstr.getPmtYmd());
		rdtOcpeDt.setPrsNam(ocpatRegstr.getPrsNam());
		rdtOcpeDt.setRegNum(ocpatRegstr.getRegNum());
		rdtOcpeDt.setPrsAdr(ocpatRegstr.getPrsAdr());
		rdtOcpeDt.setjygLoc(ocpatRegstr.getjygLoc());
		rdtOcpeDt.setjygPur(ocpatRegstr.getjygPur());
		rdtOcpeDt.setjygAra(ocpatRegstr.getjygAra());
		rdtOcpeDt.setjygUlm(ocpatRegstr.getjygUlm());
		rdtOcpeDt.setjysYmd(ocpatRegstr.getjysYmd());
		rdtOcpeDt.setjyeYmd(ocpatRegstr.getjyeYmd());
		rdtOcpeDt.setjygCmt(ocpatRegstr.getjygCmt());
		rdtOcpeDt.setjygPay(ocpatRegstr.getjygPay());
		rdtOcpeDt.setjygTel(ocpatRegstr.getjygTel());
		rdtOcpeDt.setRodTyp(ocpatRegstr.getRodTyp());
		rdtOcpeDt.setRotNam(ocpatRegstr.getRotNam());
		rdtOcpeDt.setUmdNam(ocpatRegstr.getUmdNam());
		rdtOcpeDt.setRotIdn(ocpatRegstr.getRotIdn());
		rdtOcpeDt.setSecIdn(ocpatRegstr.getSecIdn());
		rdtOcpeDt.setRmkExp(ocpatRegstr.getRmkExp());
		
		String dongCode = ocpatRegstr.getSelDong(); 
		if (dongCode != null && dongCode.length() > 0) {
			StringBuffer pnu = new StringBuffer();
			pnu.append(StringUtil.rightPad(dongCode, 10, "0"));
			if (ocpatRegstr.getCheckMauntain() == null)
				pnu.append(1);
			else
				pnu.append(ocpatRegstr.getCheckMauntain());
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getMainNum()), 4, "0"));
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getSubNum()), 4, "0"));
			if (pnu.length() >  0)
				rdtOcpeDt.setPnu(pnu.toString());
		}
		
		Integer result = rdtOcpeDtMapper.insert(rdtOcpeDt, keyStr);
		
		insertEditHistory(rdtOcpeDt.getFtrCde(), rdtOcpeDt.getFtrIdn(), rdtOcpeDt.getPmtNum(), "INS");
		
		return result;
	}
	
	/**
	 * 변경대장 등록 
	 * @param ocpatRegstr - 대장 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertRdtOccnDt(OcpatRegstrDTO ocpatRegstr, String keyStr) throws Exception {
		Long ftrIdn = rdtOccnDtIdGnrService.getNextLongId();
		
		RdtOccnDt rdtOccnDt = new RdtOccnDt();
		rdtOccnDt.setFtrIdn(ftrIdn);
		rdtOccnDt.setFtrCde(ocpatRegstr.getFtrCde());
		rdtOccnDt.setPmtNum(ocpatRegstr.getPmtNum());
		rdtOccnDt.setPmtYmd(ocpatRegstr.getPmtYmd());
		rdtOccnDt.setPrsNam(ocpatRegstr.getPrsNam());
		rdtOccnDt.setRegNum(ocpatRegstr.getRegNum());
		rdtOccnDt.setPrsAdr(ocpatRegstr.getPrsAdr());
		rdtOccnDt.setjygLoc(ocpatRegstr.getjygLoc());
		rdtOccnDt.setjygPur(ocpatRegstr.getjygPur());
		rdtOccnDt.setjygAra(ocpatRegstr.getjygAra());
		rdtOccnDt.setjygUlm(ocpatRegstr.getjygUlm());
		rdtOccnDt.setjysYmd(ocpatRegstr.getjysYmd());
		rdtOccnDt.setjyeYmd(ocpatRegstr.getjyeYmd());
		rdtOccnDt.setjygCmt(ocpatRegstr.getjygCmt());
		rdtOccnDt.setjygPay(ocpatRegstr.getjygPay());
		rdtOccnDt.setjygTel(ocpatRegstr.getjygTel());
		rdtOccnDt.setOldCde(ocpatRegstr.getOldCde());
		rdtOccnDt.setOldIdn(ocpatRegstr.getOldIdn());
		rdtOccnDt.setChnCmt(ocpatRegstr.getChnCmt());
		rdtOccnDt.setChnYmd(ocpatRegstr.getChnYmd());
		rdtOccnDt.setRodTyp(ocpatRegstr.getRodTyp());
		rdtOccnDt.setRotNam(ocpatRegstr.getRotNam());
		rdtOccnDt.setUmdNam(ocpatRegstr.getUmdNam());
		rdtOccnDt.setRotIdn(ocpatRegstr.getRotIdn());
		rdtOccnDt.setSecIdn(ocpatRegstr.getSecIdn());
		rdtOccnDt.setRmkExp(ocpatRegstr.getRmkExp());
		
		String dongCode = ocpatRegstr.getSelDong(); 
		if (dongCode != null && dongCode.length() > 0) {
			StringBuffer pnu = new StringBuffer();
			pnu.append(StringUtil.rightPad(dongCode, 10, "0"));
			if (ocpatRegstr.getCheckMauntain() == null)
				pnu.append(1);
			else
				pnu.append(ocpatRegstr.getCheckMauntain());
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getMainNum()), 4, "0"));
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getSubNum()), 4, "0"));
			if (pnu.length() >  0)
				rdtOccnDt.setPnu(pnu.toString());
		}
		
		Integer result = rdtOccnDtMapper.insert(rdtOccnDt, keyStr);
		
		insertEditHistory(rdtOccnDt.getFtrCde(), rdtOccnDt.getFtrIdn(), rdtOccnDt.getPmtNum(), "INS");
		
		return result;
	}

	/**
	 * 원상회복공사 등록 
	 * @param ocpatRegstr - 대장 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertRdtOcreDt(OcpatRegstrDTO ocpatRegstr, String keyStr) throws Exception {
		Long ftrIdn = rdtOcreDtIdGnrService.getNextLongId();
		
		RdtOcreDt rdtOcreDt = new RdtOcreDt();
		rdtOcreDt.setFtrIdn(ftrIdn);
		rdtOcreDt.setFtrCde(ocpatRegstr.getFtrCde());
		rdtOcreDt.setPmtNum(ocpatRegstr.getPmtNum());
		rdtOcreDt.setPmtYmd(ocpatRegstr.getPmtYmd());
		rdtOcreDt.setPrsNam(ocpatRegstr.getPrsNam());
		rdtOcreDt.setRegNum(ocpatRegstr.getRegNum());
		rdtOcreDt.setPrsAdr(ocpatRegstr.getPrsAdr());
		rdtOcreDt.setjygLoc(ocpatRegstr.getjygLoc());
		rdtOcreDt.setjygPur(ocpatRegstr.getjygPur());
		rdtOcreDt.setjygAra(ocpatRegstr.getjygAra());
		rdtOcreDt.setjygUlm(ocpatRegstr.getjygUlm());
		rdtOcreDt.setjysYmd(ocpatRegstr.getjysYmd());
		rdtOcreDt.setjyeYmd(ocpatRegstr.getjyeYmd());
		rdtOcreDt.setjygCmt(ocpatRegstr.getjygCmt());
		rdtOcreDt.setEndYmd(ocpatRegstr.getEndYmd());
		rdtOcreDt.setjygTel(ocpatRegstr.getjygTel());
		rdtOcreDt.setOldCde(ocpatRegstr.getOldCde());
		rdtOcreDt.setOldIdn(ocpatRegstr.getOldIdn());
		rdtOcreDt.setRodTyp(ocpatRegstr.getRodTyp());
		rdtOcreDt.setRotNam(ocpatRegstr.getRotNam());
		rdtOcreDt.setUmdNam(ocpatRegstr.getUmdNam());
		rdtOcreDt.setRmkExp(ocpatRegstr.getRmkExp());
		
		String dongCode = ocpatRegstr.getSelDong(); 
		if (dongCode != null && dongCode.length() > 0) {
			StringBuffer pnu = new StringBuffer();
			pnu.append(StringUtil.rightPad(dongCode, 10, "0"));
			if (ocpatRegstr.getCheckMauntain() == null)
				pnu.append(1);
			else
				pnu.append(ocpatRegstr.getCheckMauntain());
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getMainNum()), 4, "0"));
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getSubNum()), 4, "0"));
			if (pnu.length() >  0)
				rdtOcreDt.setPnu(pnu.toString());
		}
		
		Integer result = rdtOcreDtMapper.insert(rdtOcreDt, keyStr);
		
		insertEditHistory(rdtOcreDt.getFtrCde(), rdtOcreDt.getFtrIdn(), rdtOcreDt.getPmtNum(), "INS");
		
		return result;
	}

	/**
	 * 불허가 등록 
	 * @param ocpatRegstr - 대장 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertRdtOcdsDt(OcpatRegstrDTO ocpatRegstr, String keyStr) throws Exception {
		Long ftrIdn = rdtOcdsDtIdGnrService.getNextLongId();
		
		RdtOcdsDt rdtOcdsDt = new RdtOcdsDt();
		rdtOcdsDt.setFtrIdn(ftrIdn);
		rdtOcdsDt.setFtrCde(ocpatRegstr.getFtrCde());
		rdtOcdsDt.setPmtNum(ocpatRegstr.getPmtNum());
		rdtOcdsDt.setPmtYmd(ocpatRegstr.getPmtYmd());
		rdtOcdsDt.setPrsNam(ocpatRegstr.getPrsNam());
		rdtOcdsDt.setRegNum(ocpatRegstr.getRegNum());
		rdtOcdsDt.setPrsAdr(ocpatRegstr.getPrsAdr());
		rdtOcdsDt.setjygLoc(ocpatRegstr.getjygLoc());
		rdtOcdsDt.setjygPur(ocpatRegstr.getjygPur());
		rdtOcdsDt.setjygAra(ocpatRegstr.getjygAra());
		rdtOcdsDt.setjygUlm(ocpatRegstr.getjygUlm());
		rdtOcdsDt.setjysYmd(ocpatRegstr.getjysYmd());
		rdtOcdsDt.setjyeYmd(ocpatRegstr.getjyeYmd());
		rdtOcdsDt.setjygCmt(ocpatRegstr.getjygCmt());
		rdtOcdsDt.setjygTel(ocpatRegstr.getjygTel());
		rdtOcdsDt.setOldCde(ocpatRegstr.getOldCde());
		rdtOcdsDt.setOldIdn(ocpatRegstr.getOldIdn());
		rdtOcdsDt.setRodTyp(ocpatRegstr.getRodTyp());
		rdtOcdsDt.setRotNam(ocpatRegstr.getRotNam());
		rdtOcdsDt.setUmdNam(ocpatRegstr.getUmdNam());
		rdtOcdsDt.setRmkExp(ocpatRegstr.getRmkExp());
		
		String dongCode = ocpatRegstr.getSelDong(); 
		if (dongCode != null && dongCode.length() > 0) {
			StringBuffer pnu = new StringBuffer();
			pnu.append(StringUtil.rightPad(dongCode, 10, "0"));
			if (ocpatRegstr.getCheckMauntain() == null)
				pnu.append(1);
			else
				pnu.append(ocpatRegstr.getCheckMauntain());
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getMainNum()), 4, "0"));
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getSubNum()), 4, "0"));
			if (pnu.length() >  0)
				rdtOcdsDt.setPnu(pnu.toString());
		}
		
		Integer result = rdtOcdsDtMapper.insert(rdtOcdsDt, keyStr);
		
		insertEditHistory(rdtOcdsDt.getFtrCde(), rdtOcdsDt.getFtrIdn(), rdtOcdsDt.getPmtNum(), "INS");
		
		return result;
	}

	/**
	 * 취하원 등록 
	 * @param ocpatRegstr - 대장 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertRdtOcdrDt(OcpatRegstrDTO ocpatRegstr, String keyStr) throws Exception {
		Long ftrIdn = rdtOcdrDtIdGnrService.getNextLongId();
		
		RdtOcdrDt rdtOcdrDt = new RdtOcdrDt();
		rdtOcdrDt.setFtrIdn(ftrIdn);
		rdtOcdrDt.setFtrCde(ocpatRegstr.getFtrCde());
		rdtOcdrDt.setPmtNum(ocpatRegstr.getPmtNum());
		rdtOcdrDt.setPmtYmd(ocpatRegstr.getPmtYmd());
		rdtOcdrDt.setPrsNam(ocpatRegstr.getPrsNam());
		rdtOcdrDt.setRegNum(ocpatRegstr.getRegNum());
		rdtOcdrDt.setPrsAdr(ocpatRegstr.getPrsAdr());
		rdtOcdrDt.setjygLoc(ocpatRegstr.getjygLoc());
		rdtOcdrDt.setjygPur(ocpatRegstr.getjygPur());
		rdtOcdrDt.setjygAra(ocpatRegstr.getjygAra());
		rdtOcdrDt.setjygUlm(ocpatRegstr.getjygUlm());
		rdtOcdrDt.setjysYmd(ocpatRegstr.getjysYmd());
		rdtOcdrDt.setjyeYmd(ocpatRegstr.getjyeYmd());
		rdtOcdrDt.setjygCmt(ocpatRegstr.getjygCmt());
		rdtOcdrDt.setjygTel(ocpatRegstr.getjygTel());
		rdtOcdrDt.setOldCde(ocpatRegstr.getOldCde());
		rdtOcdrDt.setOldIdn(ocpatRegstr.getOldIdn());
		rdtOcdrDt.setRodTyp(ocpatRegstr.getRodTyp());
		rdtOcdrDt.setRotNam(ocpatRegstr.getRotNam());
		rdtOcdrDt.setUmdNam(ocpatRegstr.getUmdNam());
		rdtOcdrDt.setRmkExp(ocpatRegstr.getRmkExp());
		
		String dongCode = ocpatRegstr.getSelDong(); 
		if (dongCode != null && dongCode.length() > 0) {
			StringBuffer pnu = new StringBuffer();
			pnu.append(StringUtil.rightPad(dongCode, 10, "0"));
			if (ocpatRegstr.getCheckMauntain() == null)
				pnu.append(1);
			else
				pnu.append(ocpatRegstr.getCheckMauntain());
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getMainNum()), 4, "0"));
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getSubNum()), 4, "0"));
			if (pnu.length() >  0)
				rdtOcdrDt.setPnu(pnu.toString());
		}
		
		Integer result = rdtOcdrDtMapper.insert(rdtOcdrDt,keyStr);
		
		insertEditHistory(rdtOcdrDt.getFtrCde(), rdtOcdrDt.getFtrIdn(), rdtOcdrDt.getPmtNum(), "INS");
		
		return result;
	}
	
	/**
	 * 취소신청 등록 
	 * @param ocpatRegstr - 대장 정보
	 * @return
	 * @throws Exception
	 */
	private Integer insertRdtOccaDt(OcpatRegstrDTO ocpatRegstr, String keyStr) throws Exception {
		Long ftrIdn = rdtOccaDtIdGnrService.getNextLongId();
		
		RdtOccaDt rdtOccaDt = new RdtOccaDt();
		rdtOccaDt.setFtrIdn(ftrIdn);
		rdtOccaDt.setFtrCde(ocpatRegstr.getFtrCde());
		rdtOccaDt.setPmtNum(ocpatRegstr.getPmtNum());
		rdtOccaDt.setPmtYmd(ocpatRegstr.getPmtYmd());
		rdtOccaDt.setPrsNam(ocpatRegstr.getPrsNam());
		rdtOccaDt.setRegNum(ocpatRegstr.getRegNum());
		rdtOccaDt.setPrsAdr(ocpatRegstr.getPrsAdr());
		rdtOccaDt.setjygLoc(ocpatRegstr.getjygLoc());
		rdtOccaDt.setjygPur(ocpatRegstr.getjygPur());
		rdtOccaDt.setjygAra(ocpatRegstr.getjygAra());
		rdtOccaDt.setjygUlm(ocpatRegstr.getjygUlm());
		rdtOccaDt.setjysYmd(ocpatRegstr.getjysYmd());
		rdtOccaDt.setjyeYmd(ocpatRegstr.getjyeYmd());
		rdtOccaDt.setjygCmt(ocpatRegstr.getjygCmt());
		rdtOccaDt.setjygTel(ocpatRegstr.getjygTel());
		rdtOccaDt.setOldCde(ocpatRegstr.getOldCde());
		rdtOccaDt.setOldIdn(ocpatRegstr.getOldIdn());
		rdtOccaDt.setRodTyp(ocpatRegstr.getRodTyp());
		rdtOccaDt.setRotNam(ocpatRegstr.getRotNam());
		rdtOccaDt.setUmdNam(ocpatRegstr.getUmdNam());
		rdtOccaDt.setRmkExp(ocpatRegstr.getRmkExp());
		
		String dongCode = ocpatRegstr.getSelDong(); 
		if (dongCode != null && dongCode.length() > 0) {
			StringBuffer pnu = new StringBuffer();
			pnu.append(StringUtil.rightPad(dongCode, 10, "0"));
			if (ocpatRegstr.getCheckMauntain() == null)
				pnu.append(1);
			else
				pnu.append(ocpatRegstr.getCheckMauntain());
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getMainNum()), 4, "0"));
			pnu.append(StringUtil.leftPad(Integer.toString(ocpatRegstr.getSubNum()), 4, "0"));
			if (pnu.length() >  0)
				rdtOccaDt.setPnu(pnu.toString());
		}
		
		Integer result = rdtOccaDtMapper.insert(rdtOccaDt,keyStr);
		
		insertEditHistory(rdtOccaDt.getFtrCde(), rdtOccaDt.getFtrIdn(), rdtOccaDt.getPmtNum(), "INS");
		
		return result;
	}

	@Override
	public Integer updateRegsterUmd(String layerId, String ftrCde, Long ftrIdn) throws Exception {
		String keyString = messageSource.getMessage("Com.KeyString", null, Locale.getDefault());

		KwsOcpatReg register = kwsOcpatRegMapper.selectOneByFtrCde(ftrCde);
		String idnField = register.getOcpatIdnField();
		String cdeField = register.getOcpatCdeField();
		
		KwsData queryData = dataService.selectOneData(layerId);
		validatorDataAndDataField(queryData, layerId);
		StringBuffer cql = new StringBuffer();
		cql.append(cdeField);
		cql.append(" = '");
		cql.append(ftrCde);
		cql.append("' AND ");
		cql.append(idnField);
		cql.append(" = ");
		cql.append(ftrIdn);
		
		StringBuilder umdNam = new StringBuilder();
		List<EgovMap> datas = ocpatRegisterSearchResository.listAll(queryData, cql.toString(), false, "OBJECTID", "ASC");
		if (datas != null && datas.size() > 0) {
			int cnt = 0;
			for (int i = 0; i < datas.size(); i++) {
				EgovMap data = datas.get(i);
				String hjdNam = (String)data.get("hjdCde");
				if (hjdNam != null && hjdNam.length() > 0) {
					if (umdNam.indexOf(hjdNam) >= 0)
						continue;
					
					if (cnt > 0) {
						umdNam.append(",");
					}
					umdNam.append(hjdNam);
					cnt++;
				}
			}
		}
		
		String dongName = umdNam.toString();
		Integer result = -1;
		String pmtNum = null;
		switch(register.getOcpatIdn()) {
		case 100100:
		case 200100:
		case 300100:
		case 400100: 	// 허가대장
		{
			RdtOcpeDt rdtOcpeDt = rdtOcpeDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (rdtOcpeDt != null) {
				pmtNum = rdtOcpeDt.getPmtNum();
				result = rdtOcpeDtMapper.updateUmdNam(dongName, ftrCde, ftrIdn);
			}
			else
				result = 0;
		}
			break;
			
		case 500100:	// 변경허가 대장
		{
			RdtOccnDt rdtOccnDt = rdtOccnDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (rdtOccnDt != null) {
				pmtNum = rdtOccnDt.getPmtNum();
				result = rdtOccnDtMapper.updateUmdNam(dongName, ftrCde, ftrIdn);
			}
			else
				result = 0;
		}
			break;
			
		case 600100:	// 원상회복공사 대장
		{
			RdtOcreDt rdtOcreDt = rdtOcreDtMapper.selectOneById(ftrCde, ftrIdn, keyString);
			if (rdtOcreDt != null) {
				pmtNum = rdtOcreDt.getPmtNum();
				result = rdtOcreDtMapper.updateUmdNam(dongName, ftrCde, ftrIdn);
			}
			else
				result = 0;
		}
			break;
		}
		
		if (pmtNum != null) {
			// 점용시설의 공간정보가 없는 대장은 최초대장의 공간정보가 변경되면 같이 변경한다. 
			insertEditHistory(ftrCde, ftrIdn, pmtNum, "UPD");
		
			// 불허가
			List<RdtOcdsDt> listOcds = rdtOcdsDtMapper.selectByOldId(ftrCde, ftrIdn, keyString);
			if (listOcds != null && listOcds.size() > 0) {
				for (RdtOcdsDt ocdsData : listOcds) {
					if (rdtOcdsDtMapper.updateUmdNam(dongName, ocdsData.getFtrCde(), ocdsData.getFtrIdn()) >= 1) {
						insertEditHistory(ocdsData.getFtrCde(), ocdsData.getFtrIdn(), ocdsData.getPmtNum(), "UPD");
					}
				}
			}
	
			// 취소신청
			List<RdtOccaDt> listOcca = rdtOccaDtMapper.selectByOldId(ftrCde, ftrIdn, keyString);
			if (listOcca != null && listOcca.size() > 0) {
				for (RdtOccaDt occaData : listOcca) { 
					if (rdtOccaDtMapper.updateUmdNam(dongName, occaData.getFtrCde(), occaData.getFtrIdn()) >= 1) {
						insertEditHistory(occaData.getFtrCde(), occaData.getFtrIdn(), occaData.getPmtNum(), "UPD");
					}
				}
			}
	
			// 취하원
			List<RdtOcdrDt> listOcdr = rdtOcdrDtMapper.selectByOldId(ftrCde, ftrIdn, keyString);
			if (listOcdr != null && listOcdr.size() > 0) {
				for (RdtOcdrDt ocdrData : listOcdr) { 
					if (rdtOcdrDtMapper.updateUmdNam(dongName, ocdrData.getFtrCde(), ocdrData.getFtrIdn()) >= 1) {
						insertEditHistory(ocdrData.getFtrCde(), ocdrData.getFtrIdn(), ocdrData.getPmtNum(), "UPD");
					}
				}
			}
		}
		
		return result;
	}
	
	private void insertEditHistory(String ftrCde, Long ftrIdn, String pmtNum, String editType) throws Exception {
		
		// 대장 편집이력
		OcpatEditHi ocpatEditHi = new OcpatEditHi(); 
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ocpatEditHi.setUserId(userDTO.getUserId());

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		String editDate	= dateFormat.format(ts.getTime());
		ocpatEditHi.setEditDt(editDate);

		ocpatEditHi.setFtrCde(ftrCde);
		ocpatEditHi.setFtrIdn(ftrIdn);
		ocpatEditHi.setPmtNum(pmtNum);
		ocpatEditHi.setEditType(editType);
		
		ocpatRegstrHistoryService.insert(ocpatEditHi);
	}
}