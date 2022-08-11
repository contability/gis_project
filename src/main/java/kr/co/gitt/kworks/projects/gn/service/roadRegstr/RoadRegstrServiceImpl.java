package kr.co.gitt.kworks.projects.gn.service.roadRegstr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsRoadRegMapper;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsData.DataTy;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsRoadReg;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterBBoxSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSearchResultDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSearchSummaryDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSortDTO;
import kr.co.gitt.kworks.projects.gn.mappers.RdlSectLsMapper;
import kr.co.gitt.kworks.projects.gn.model.RdlSectLs;
import kr.co.gitt.kworks.repository.RoadRegisterSearchResository;
import kr.co.gitt.kworks.service.cmmn.AddressService;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("roadRegstrService")
@Profile({"gn_dev", "gn_oper"})
public class RoadRegstrServiceImpl implements RoadRegstrService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// Lks : 도로대장 맵퍼
	@Resource
	KwsRoadRegMapper kwsRoadRegMapper;	
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 도메인 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 주소 서비스
	@Resource
	AddressService addressService;
	
	/// 도로대장 검색 저장소
	@Resource
	RoadRegisterSearchResository roadRegisterSearchRepository;	
	
	/// 총괄 맵퍼
	@Resource
	RdlSectLsMapper rdlSectLsMapper;
	
	public List<RdlSectLs> listRoadRegstr(RdlSectLs rdlSectLs) {
		return rdlSectLsMapper.list(rdlSectLs);
	}
	
	// Lks : 도로대장
	@Override
	public List<KwsRoadReg> listAll() {
		return kwsRoadRegMapper.listAll();
	}

	@Override
	public List<EgovMap> rotNameAll() throws Exception {
		String layerId = "RDL_SECT_LS";
		KwsData kwsData = dataService.selectOneRoadReg(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		List<String> fields = new ArrayList<String>();
		fields.add("ROT_NAM");
		fields.add("ROT_IDN");
		
		List<EgovMap> names = roadRegisterSearchRepository.listAll(kwsData, fields, "ROT_NAM", "ASC");
		
		return names;
	}
	
	@Override
	public KwsRoadReg selectOne(String regIdn) {
		KwsRoadReg result = kwsRoadRegMapper.selectOne(regIdn);
		return result;
	}
	
	@Override
	public List<KwsRoadReg> listSelected(List<String> regIdns, boolean useCheck) {
		List<KwsRoadReg> result = new ArrayList<KwsRoadReg>();
		
		int cnt = regIdns.size();
		for(int i=0; i < cnt; i++) {
			String idn = regIdns.get(i);
			KwsRoadReg roadReg = kwsRoadRegMapper.selectOne(idn);
			if (roadReg == null) {
				logger.warn("No Data : " + idn);
				throw new RuntimeException("No Data : " + idn);				
			}
			
			if ((useCheck && roadReg.getUseAt().equalsIgnoreCase("Y")) || !useCheck) {
				result.add(roadReg);		
			} 
		}
		
		return result;
	}
	
	@Override
	public List<RoadRegisterSearchSummaryDTO> searchSummaries(List<KwsRoadReg> roadRegs, RoadRegisterSearchDTO roadRegisterSearchDTO) throws Exception {
		
		List<RoadRegisterSearchSummaryDTO> result = new ArrayList<RoadRegisterSearchSummaryDTO>();
		
		String roadName = roadRegisterSearchDTO.getRoadNam();
		Integer roadIdn = roadRegisterSearchDTO.getRoadIdn();
		Integer sectIdn = roadRegisterSearchDTO.getSectIdn();
		
		for(KwsRoadReg roadReg : roadRegs) {
			RoadRegisterSearchSummaryDTO roadRegSummary = listAllSummary(roadReg, roadName, roadIdn, sectIdn);
			if(roadRegSummary.getFeatureIds() != null && roadRegSummary.getFeatureIds().size() > 0) {
				result.add(roadRegSummary);
			}
		}
		
		return result;
	}
	
	private RoadRegisterSearchSummaryDTO listAllSummary(KwsRoadReg roadRegister, String roadName, Integer roadIdn, Integer sectIdn) {
		String layerId = roadRegister.getRegLayerId();
		String regAlias = roadRegister.getRegAlias();
		
		RoadRegisterSearchSummaryDTO result = new RoadRegisterSearchSummaryDTO();
		result.setRegIdn(roadRegister.getRegIdn().toString());
		result.setRegAlias(regAlias);
		
		KwsData kwsData = dataService.selectOneRoadReg(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		StringBuffer cql = new StringBuffer();
		if ((roadName != null && roadName.length() > 0) && (roadIdn != null && roadIdn > 0)) {
			cql.append("T.ROT_NAM = '");
			cql.append(roadName);
			cql.append("'");
			cql.append(" AND T.ROT_IDN = '");
			cql.append(roadIdn);
			cql.append("'");
		} else if ((roadName != null && roadName.length() > 0) && (roadIdn == null || roadIdn <= 0)) {
			cql.append("T.ROT_NAM = '");
			cql.append(roadName);
			cql.append("'");
		} else if ((roadName == null || roadName.length() <= 0) && (roadIdn != null && roadIdn > 0)) {
			cql.append("T.ROT_IDN = '");
			cql.append(roadIdn);
			cql.append("'");
		}
		
		if (sectIdn != null && sectIdn > 0) {
			cql.append(" AND T.SEC_IDN = '");
			cql.append(sectIdn);
			cql.append("'");
		}

		String propField = roadRegister.getRegPropField();
		if (propField != null && propField.length() > 0) {
			cql.append(" AND ");
			cql.append(propField);
			cql.append(" = '");
			cql.append(roadRegister.getRegPropValue());
			cql.append("'");
		}

		List<RoadRegisterSearchResultDTO> summary = new ArrayList<RoadRegisterSearchResultDTO>();
		if (roadRegister.getRegIdn() != 700200) {
			List<Long> ids = roadRegisterSearchRepository.listAllSummary(kwsData, cql.toString());
			if(ids != null && ids.size() > 0) {
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.size(); i++)
					idList.add(ids.get(i).toString());
				
				RoadRegisterSearchResultDTO searchResult = new RoadRegisterSearchResultDTO();
				searchResult.setDataId(kwsData.getDataId());
				searchResult.setDataAlias(kwsData.getDataAlias());
				searchResult.setIds(idList);
				summary.add(searchResult);
			}

			String subTable = roadRegister.getRegSubId();
			if (subTable != null && subTable.length() > 0) {
				RoadRegisterSearchResultDTO subResult = subTableQuery(subTable, roadName, roadIdn, sectIdn, roadRegister.getRegSubField(), roadRegister.getRegSubValue());
				if (subResult != null && subResult.getIds() != null && subResult.getIds().size() > 0)
					summary.add(subResult);
			}
		}
		else {
			String subTable = roadRegister.getRegSubId();
			KwsData subData = dataService.selectOneData(subTable);
			validatorDataAndDataField(subData, subTable);
			
			List<EgovMap> geometries = roadRegisterSearchRepository.listAllGeometry(kwsData, cql.toString());
			if(geometries != null && geometries.size() > 0) {
				List<String> ids = new ArrayList<String>();
				List<String> idn = new ArrayList<String>();
				for (int i = 0; i < geometries.size(); i++) {
					EgovMap map = geometries.get(i);
					String ftrIdn = map.get("ftrIdn").toString();
					String geom = map.get("geom").toString();
					if (geom != null) {
						List<Long> pnu = roadRegisterSearchRepository.listAllPnu(subData, geom);
						if (pnu != null && pnu.size() > 0) {
							for (int j = 0; j < pnu.size(); j++) {
								String pnucode = pnu.get(j).toString();
								if (!ids.contains(pnucode)) {
									ids.add(pnucode);
									idn.add(ftrIdn + "." + pnucode);
								}
							}
						}
					}
				}
				
				if(ids != null && ids.size() > 0) {
					RoadRegisterSearchResultDTO res = new RoadRegisterSearchResultDTO();
					res.setDataId(subData.getDataId());
					res.setDataAlias(subData.getDataAlias());
					res.setIds(ids);
					res.setFtrIdn(idn);
					
					summary.add(res);
				}
			
			}
		}
		
		if (summary.size() > 0)
			result.setFeatureIds(summary);
		
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
	
	private RoadRegisterSearchResultDTO subTableQuery(String subId, String roadName, Integer roadIdn, Integer sectIdn, String subField, String subValue) {
		
		RoadRegisterSearchResultDTO result = null;
		
		KwsData kwsData = dataService.selectOneRoadReg(subId);
		validatorDataAndDataField(kwsData, subId);
		
		StringBuffer cql = new StringBuffer();
		if ((roadName != null && roadName.length() > 0) && (roadIdn != null && roadIdn > 0)) {
			cql.append("ROT_NAM = '");
			cql.append(roadName);
			cql.append("'");
			cql.append(" AND ROT_IDN = '");
			cql.append(roadIdn);
			cql.append("'");
		} else if ((roadName != null && roadName.length() > 0) && (roadIdn == null || roadIdn <= 0)) {
			cql.append("ROT_NAM = '");
			cql.append(roadName);
			cql.append("'");
		} else if ((roadName == null || roadName.length() <= 0) && (roadIdn != null && roadIdn > 0)) {
			cql.append("ROT_IDN = '");
			cql.append(roadIdn);
			cql.append("'");
		}
		if (sectIdn != null && sectIdn > 0) {
			cql.append(" AND SEC_IDN = '");
			cql.append(sectIdn);
			cql.append("'");
		}
		
		if (sectIdn != null) {
			cql.append(" AND SEC_IDN = '");
			cql.append(sectIdn);
			cql.append("'");
		}

		if (subField != null) {
			cql.append(" AND ");
			cql.append(subField);
			cql.append(" = '");
			cql.append(subValue);
			cql.append("'");
		}
		
		List<Long> ids = roadRegisterSearchRepository.listAllSummary(kwsData, cql.toString());
		if(ids != null && ids.size() > 0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++)
				idList.add(ids.get(i).toString());
			
			result = new RoadRegisterSearchResultDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(idList);
		}
		
		return result;
	}

	@Override
	public RoadRegisterSearchSummaryDTO sortRoadRegister(KwsRoadReg roadReg, RoadRegisterSortDTO roadRegisterSortDTO) throws Exception {
		
		RoadRegisterSearchSummaryDTO result = null;
		
		if (roadReg.getRegIdn() != 700200) {
			String sortField = roadRegisterSortDTO.getSortField();
			String sortDirection = roadRegisterSortDTO.getSortDirection();
			List<String> layers = roadRegisterSortDTO.getLayerIds();
			List<List<String>> ids = roadRegisterSortDTO.getFeatureIds();
			
			List<RoadRegisterSearchResultDTO> featureIds = new ArrayList<RoadRegisterSearchResultDTO>();
			for(int i = 0; i < layers.size(); i++) {
				RoadRegisterSearchResultDTO subResult = sort(layers.get(i), ids.get(i), sortField, sortDirection);
				if(subResult.getIds() != null && subResult.getIds().size() > 0) {
					featureIds.add(subResult);
				}
			}
			
			if (featureIds.size() > 0) {
				result = new RoadRegisterSearchSummaryDTO();
				result.setRegIdn(roadReg.getRegIdn().toString());
				result.setRegAlias(roadReg.getRegAlias());
				result.setFeatureIds(featureIds);
			}
		}
		else {
			String sortField = roadRegisterSortDTO.getSortField();
			if (sortField.equalsIgnoreCase("FTR_IDN"))
				sortField = "PNU";//"OBJECTID";
			
			String sortDirection = roadRegisterSortDTO.getSortDirection();
			List<String> layers = roadRegisterSortDTO.getLayerIds();
			List<List<String>> ids = roadRegisterSortDTO.getFeatureIds();
			List<List<String>> ftrIdn = roadRegisterSortDTO.getFtrIdn();
			
			List<RoadRegisterSearchResultDTO> featureIds = new ArrayList<RoadRegisterSearchResultDTO>();
			for(int i = 0; i < layers.size(); i++) {
				String layer = layers.get(i);
				RoadRegisterSearchResultDTO subResult = sortPNU(layer, ids.get(i), ftrIdn.get(i), sortField, sortDirection);
				if(subResult.getIds() != null && subResult.getIds().size() > 0) {
					featureIds.add(subResult);
				}
			}
			
			if (featureIds.size() > 0) {
				result = new RoadRegisterSearchSummaryDTO();
				result.setRegIdn(roadReg.getRegIdn().toString());
				result.setRegAlias(roadReg.getRegAlias());
				result.setFeatureIds(featureIds);
			}
		}
		
		return result;
	}
	
	private RoadRegisterSearchResultDTO sort(String layerId, List<String> featureIds, String sortField, String sortDirection) {
	
		RoadRegisterSearchResultDTO result = null;
		
		KwsData kwsData = dataService.selectOneRoadReg(layerId);
		validatorDataAndDataField(kwsData, layerId);

		List<Long> ids = roadRegisterSearchRepository.listAllSummary(kwsData, featureIds, sortField, sortDirection);
		if(ids != null && ids.size() > 0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++)
				idList.add(ids.get(i).toString());
			
			result = new RoadRegisterSearchResultDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(idList);
		}
		
		return result;
	}

	private RoadRegisterSearchResultDTO sortPNU(String layerId, List<String> featureIds, List<String> ftrIdn, String sortField, String sortDirection) {
		
 		RoadRegisterSearchResultDTO result = null;
		
		KwsData kwsData = dataService.selectOneData(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		List<String> fid = new ArrayList<String>();
		List<String> pnu = new ArrayList<String>();
		String[] id = ftrIdn.get(0).split(","); 
		
		for (int i = 0; i < id.length; i++) {
			String[] split = id[i].split("\\.");
			fid.add(split[0]);
			pnu.add(split[1]);
		}

		List<Long> ids = roadRegisterSearchRepository.listAllPnu(kwsData, featureIds, sortField, sortDirection);
		if(ids != null && ids.size() > 0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++)
				idList.add(ids.get(i).toString());
			
			result = new RoadRegisterSearchResultDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(idList);
			List<String> idn = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++) {
				int idx = pnu.indexOf(idList.get(i).toString());
				idn.add(fid.get(idx) + "." + idList.get(i));
			}
			result.setFtrIdn(idn);
		}
		
		return result;
	}
	
	@Override
	public List<EgovMap> searchRoadRegister(KwsRoadReg roadReg, RoadRegisterSortDTO roadRegisterSortDTO) throws Exception {
	
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String sortField = roadRegisterSortDTO.getSortField();
		String sortDirection = roadRegisterSortDTO.getSortDirection();
		List<String> layers = roadRegisterSortDTO.getLayerIds();
		List<List<String>> ids = roadRegisterSortDTO.getFeatureIds();
		boolean isOriginValue = false;
		if (roadRegisterSortDTO.getIsOriginValue() != null && roadRegisterSortDTO.getIsOriginValue())
			isOriginValue = true;
		
		if (roadReg.getRegIdn() != 700200) {
			for(int i = 0; i < layers.size(); i++) {
				String layerId = layers.get(i);
				KwsData kwsData = dataService.selectOneRoadReg(layerId);
				validatorDataAndDataField(kwsData, layerId);
				
				List<EgovMap> features = roadRegisterSearchRepository.listAll(kwsData, ids.get(i), isOriginValue, sortField, sortDirection);
				for (EgovMap feature : features) {
					if (kwsData.getDataTy() == DataTy.ATTRIBUTE) {
						String domnId = null;
						String typeField = kwsData.getRoadRegField();
						
						if (typeField != null && typeField.length() > 0) {
							for (KwsDataField field : kwsData.getKwsDataFields()) {
								if (field.getFieldId().equals(typeField)) {
									domnId = field.getDomnId();
									break;
								}
							}
						}
						
						searchSpatialFeature(layerId, domnId, typeField, feature, isOriginValue);
					}
					
					result.add(feature);
				}
			}
		}
		else {
			if ("FTR_IDN".equalsIgnoreCase(sortField))
				sortField = "PNU";//"OBJECTID";
			
			List<List<String>> ftrIdnList = roadRegisterSortDTO.getFtrIdn();
			
			String mainLayer = roadReg.getRegLayerId();
			KwsData kwsData = dataService.selectOneRoadReg(mainLayer);
			validatorDataAndDataField(kwsData, mainLayer);
			Map<String, EgovMap> main = new HashMap<String, EgovMap>();
			WKTReader wktReader = new WKTReader();
			
			for(int i = 0; i < layers.size(); i++) {
				String layerId = layers.get(i);
				List<String> ftrIdn = ftrIdnList.get(i);
				
				List<String> fidList = new ArrayList<String>();
				List<String> pnuList = new ArrayList<String>();
				String[] id = ftrIdn.get(0).split(","); 
				
				for (int j = 0; j < id.length; j++) {
					String[] split = id[j].split("\\.");
					fidList.add(split[0]);
					pnuList.add(split[1]);
				}
				
				KwsData subData = dataService.selectOneData(layerId);
				validatorDataAndDataField(subData, layerId);
				
				List<EgovMap> features = roadRegisterSearchRepository.selectByPnu(subData, ids.get(i), isOriginValue, sortField, sortDirection);
				for (EgovMap feature : features) {
					String wktSub = feature.get("geom").toString();
					Geometry subGeom = wktReader.read(wktSub);
					
					String pnu = feature.get("pnu").toString();
					int idx = pnuList.indexOf(pnu);
					String fid = fidList.get(idx);
					EgovMap mainData = null;
					if (main.containsKey(fid)) {
						mainData = main.get(fid);
					}
					else {
						mainData = roadRegisterSearchRepository.selectOneData(kwsData, fid, isOriginValue);
						main.put(fid, mainData);
					}
					
					String wktMain = null;
					EgovMap newFeature = new EgovMap();
					for(Object key : mainData.keySet()) {
						if ("geom".equalsIgnoreCase(key.toString())) {
							wktMain = mainData.get("geom").toString();
							newFeature.put(key, feature.get("geom"));
							continue;
						}
						else if ("ftrIdn".equalsIgnoreCase(key.toString())) {
							newFeature.put(key, feature.get("pnu"));//"objectid"));
							continue;
						}
						
						newFeature.put(key, mainData.get(key));
					}

					Geometry mainGeom = wktReader.read(wktMain);
					double cofArea = mainGeom.intersection(subGeom).getArea();
					double subArea = Double.parseDouble(feature.get("area").toString()); //subGeom.getArea();
					double reaArea = Math.abs(subArea - cofArea);
					
					newFeature.put("cofAra", cofArea);	// 저촉면적
					newFeature.put("reaAra", reaArea);	// 잔여면적
					
					String jibun = feature.get("jibun").toString();
					newFeature.put("lotNum", jibun);	// 지번
					
//					String adrCde = addressService.getFullAddress("BML_BADM_AS", "LP_PA_CBND_MAN", pnu);
//					adrCde = adrCde.substring(0, adrCde.length() - 1);
					String addr = feature.get("codeNm").toString();
					String adrCde = addr + " " + jibun;
					newFeature.put("adrCde", adrCde);	// 소재지
					
					String jimok = feature.get("jimok").toString();
					newFeature.put("lotDes", jimok);	// 지목
					
					newFeature.put("ownDit", feature.get("ownGbn"));// 소유구문
					newFeature.put("ownMan", "");	// 소유자
					
					result.add(newFeature);
				}
			}
		}
		
		return result;
	}

	private void searchSpatialFeature(String regLayer, String domnId, String typeField, EgovMap feature, boolean isOriginValue) throws Exception {
		String geomName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());

		List<KwsData> layers = null;
		if (typeField != null && typeField.length() > 0){
			String field = JdbcUtils.convertUnderscoreNameToPropertyName(typeField);
			String codeId = null;
			if (!isOriginValue) {
				KwsDomnCode keyCode = domnCodeService.selectCodeIdByName(domnId, feature.get(field).toString());
				codeId = keyCode.getCodeId();
			}
			else {
				codeId = feature.get(field).toString();
			}
				
			layers = dataService.selectRoadRegByCode(regLayer, codeId);
		} else {
			layers = dataService.selectRoadRegByAttr(regLayer);
		}
		
		if (layers != null && layers.size() > 0) {
			for (KwsData layer : layers) {
				validatorDataAndDataField(layer, layer.getDataId());
				
				StringBuffer cql = new StringBuffer();
				cql.append(layer.getRoadRegField());
				cql.append(" = '");
				cql.append(feature.get("ftrIdn"));
				cql.append("' ");
				
				List<String> listGeom = new ArrayList<String>();
				List<EgovMap> datas = roadRegisterSearchRepository.listAll(layer, cql.toString(), isOriginValue, "OBJECTID", "ASC");
				if (datas != null && datas.size() > 0) {
					EgovMap data = datas.get(0);
					for(Object key : data.keySet()) {
						if(StringUtils.equalsIgnoreCase(key.toString(), geomName)) {
							listGeom.add(data.get(key).toString());
						} else {
							feature.put(key, data.get(key));
						}
					}
					
					for (int i = 1; i < datas.size(); i++) {
						data = datas.get(i);
						for(Object key : data.keySet()) {
							if(StringUtils.equalsIgnoreCase(key.toString(), geomName)) {
								listGeom.add(data.get(key).toString());
							}
						}
					}
				}
				
				if (listGeom.size() > 0) {
					feature.put(geomName.toLowerCase(), listGeom);
				}
			}
		}
		
		return;
	}
	
	@Override
	public List<RoadRegisterSearchSummaryDTO> searchByBBox(KwsRoadReg roadReg, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO) throws Exception {
		List<RoadRegisterSearchSummaryDTO> result = new ArrayList<RoadRegisterSearchSummaryDTO>();
		
		RoadRegisterSearchSummaryDTO roadRegSummary = listAllSummary(roadReg, roadRegisterBBoxSearchDTO);
		if(roadRegSummary.getFeatureIds() != null && roadRegSummary.getFeatureIds().size() > 0)
			result.add(roadRegSummary);
		
		return result;
	}

	private RoadRegisterSearchSummaryDTO listAllSummary(KwsRoadReg roadRegister, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO) {
		String layerId = roadRegister.getRegLayerId();
		String regAlias = roadRegister.getRegAlias();
		
		RoadRegisterSearchSummaryDTO result = new RoadRegisterSearchSummaryDTO();
		result.setRegIdn(roadRegister.getRegIdn().toString());
		result.setRegAlias(regAlias);
		
		KwsData kwsData = dataService.selectOneRoadReg(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		List<RoadRegisterSearchResultDTO> summary = new ArrayList<RoadRegisterSearchResultDTO>();

		if (kwsData.getDataTy() == DataTy.LAYER) {
			StringBuffer cql = new StringBuffer();
			cql.append("(ROT_IDN <> '' OR ROT_NAM <> '')");
			
			String propField = roadRegister.getRegPropField();
			if (propField != null && propField.length() > 0) {
				cql.append(" AND ");
				cql.append(propField);
				cql.append(" = '");
				cql.append(roadRegister.getRegPropValue());
				cql.append("'");
			}			
			
			List<Long> ids = roadRegisterSearchRepository.listAllSummary(kwsData, roadRegisterBBoxSearchDTO, cql.toString());
			if(ids != null && ids.size() > 0) {
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.size(); i++)
					idList.add(ids.get(i).toString());
				
				RoadRegisterSearchResultDTO res = new RoadRegisterSearchResultDTO();
				res.setDataId(kwsData.getDataId());
				res.setDataAlias(kwsData.getDataAlias());
				res.setIds(idList);
				
				summary.add(res);
			}
		}
		else if (kwsData.getDataTy() == DataTy.ATTRIBUTE) {
			List<Long> ids = searchFeature(layerId, roadRegisterBBoxSearchDTO);
			if(ids != null && ids.size() > 0) {
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.size(); i++)
					idList.add(ids.get(i).toString());
				
				RoadRegisterSearchResultDTO res = new RoadRegisterSearchResultDTO();
				res.setDataId(kwsData.getDataId());
				res.setDataAlias(kwsData.getDataAlias());
				res.setIds(idList);
				
				summary.add(res);
			}
		}
		
		String subTable = roadRegister.getRegSubId();
		if (subTable != null && subTable.length() > 0) {
			String subField = roadRegister.getRegSubField();
			String subValue = roadRegister.getRegSubValue();
			
			StringBuffer cql = new StringBuffer();
			if (subField != null) {
				cql.append(subField);
				cql.append(" = '");
				cql.append(subValue);
				cql.append("'");
			}

			RoadRegisterSearchResultDTO subResult = subTableQuery(subTable, roadRegisterBBoxSearchDTO, cql.toString());
			if (subResult != null && subResult.getIds() != null && subResult.getIds().size() > 0)
				summary.add(subResult);
		}
		
		if (summary.size() > 0)
			result.setFeatureIds(summary);
		
		
		return result;
	}
	
	private List<Long> searchFeature(String regLayer, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO) {
		
		List<Long> ids = new ArrayList<Long>();
		List<KwsData> layers = dataService.selectRoadRegByAttr(regLayer);
		
		if (layers != null && layers.size() > 0) {
			for (KwsData layer : layers) {
				validatorDataAndDataField(layer, layer.getDataId());
				
				List<Long> datas = roadRegisterSearchRepository.listDistinct(layer, roadRegisterBBoxSearchDTO, layer.getRoadRegField());
				if (datas != null && datas.size() > 0)
					ids.addAll(datas);
			}
		}
		
		return ids;
	}
	
	private RoadRegisterSearchResultDTO subTableQuery(String subId, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO, String whereSql) {
		
		RoadRegisterSearchResultDTO result = null;
		
		KwsData kwsData = dataService.selectOneRoadReg(subId);
		validatorDataAndDataField(kwsData, subId);
		
		List<Long> ids = roadRegisterSearchRepository.listAllSummary(kwsData, roadRegisterBBoxSearchDTO, whereSql);		
		if(ids != null && ids.size() > 0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++)
				idList.add(ids.get(i).toString());
			
			result = new RoadRegisterSearchResultDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(idList);
		}
		
		return result;
	}

	@Override
	public List<RoadRegisterSearchSummaryDTO> searchVideoByBBox(KwsRoadReg roadReg, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO) throws Exception {
		List<RoadRegisterSearchSummaryDTO> result = new ArrayList<RoadRegisterSearchSummaryDTO>();
		
		RoadRegisterSearchSummaryDTO roadRegSummary = movieAllSummary(roadReg, roadRegisterBBoxSearchDTO);
		if(roadRegSummary.getFeatureIds() != null && roadRegSummary.getFeatureIds().size() > 0)
			result.add(roadRegSummary);
		
		return result;
	}
	
	private RoadRegisterSearchSummaryDTO movieAllSummary(KwsRoadReg roadRegister, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO) {
		String layerId = roadRegister.getRegLayerId();
		String regAlias = roadRegister.getRegAlias();
		
		RoadRegisterSearchSummaryDTO result = new RoadRegisterSearchSummaryDTO();
		result.setRegIdn(roadRegister.getRegIdn().toString());
		result.setRegAlias(regAlias);
		
		KwsData kwsData = dataService.selectOneRoadReg(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		List<RoadRegisterSearchResultDTO> summary = new ArrayList<RoadRegisterSearchResultDTO>();

		if (kwsData.getDataTy() == DataTy.LAYER) {
			StringBuffer cql = new StringBuffer();
			String propField = roadRegister.getRegPropField();
			if (propField != null && propField.length() > 0) {
				cql.append(propField);
				cql.append(" = '");
				cql.append(roadRegister.getRegPropValue());
				cql.append("'");
			}			
			
			List<Long> ids = roadRegisterSearchRepository.videoAllSummary(kwsData, roadRegisterBBoxSearchDTO, cql.toString());
			if(ids != null && ids.size() > 0) {
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.size(); i++)
					idList.add(ids.get(i).toString());
				
				RoadRegisterSearchResultDTO res = new RoadRegisterSearchResultDTO();
				res.setDataId(kwsData.getDataId());
				res.setDataAlias(kwsData.getDataAlias());
				res.setIds(idList);
				
				summary.add(res);
			}
		}
		else if (kwsData.getDataTy() == DataTy.ATTRIBUTE) {
			List<Long> ids = searchFeature(layerId, roadRegisterBBoxSearchDTO);
			if(ids != null && ids.size() > 0) {
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.size(); i++)
					idList.add(ids.get(i).toString());

				RoadRegisterSearchResultDTO res = new RoadRegisterSearchResultDTO();
				res.setDataId(kwsData.getDataId());
				res.setDataAlias(kwsData.getDataAlias());
				res.setIds(idList);
				
				summary.add(res);
			}
		}
		
		String subTable = roadRegister.getRegSubId();
		if (subTable != null && subTable.length() > 0) {
			String subField = roadRegister.getRegSubField();
			String subValue = roadRegister.getRegSubValue();
			
			StringBuffer cql = new StringBuffer();
			if (subField != null) {
				cql.append(subField);
				cql.append(" = '");
				cql.append(subValue);
				cql.append("'");
			}

			RoadRegisterSearchResultDTO subResult = subTableQuery(subTable, roadRegisterBBoxSearchDTO, cql.toString());
			if (subResult != null && subResult.getIds() != null && subResult.getIds().size() > 0)
				summary.add(subResult);
		}
		
		if (summary.size() > 0)
			result.setFeatureIds(summary);
		
		
		return result;
	}

	@Override
	public RoadRegisterSearchSummaryDTO sortRoadVideo(KwsRoadReg roadReg, RoadRegisterSortDTO roadRegisterSortDTO) throws Exception {
		
		RoadRegisterSearchSummaryDTO result = null;
		
		String sortField = roadRegisterSortDTO.getSortField();
		String sortDirection = roadRegisterSortDTO.getSortDirection();
		List<String> layers = roadRegisterSortDTO.getLayerIds();
		List<List<String>> ids = roadRegisterSortDTO.getFeatureIds();
		
		List<RoadRegisterSearchResultDTO> featureIds = new ArrayList<RoadRegisterSearchResultDTO>();
		for(int i = 0; i < layers.size(); i++) {
			RoadRegisterSearchResultDTO subResult = sortMovie(layers.get(i), ids.get(i), sortField, sortDirection);
			if(subResult.getIds() != null && subResult.getIds().size() > 0) {
				featureIds.add(subResult);
			}
		}
		
		if (featureIds.size() > 0) {
			result = new RoadRegisterSearchSummaryDTO();
			result.setRegIdn(roadReg.getRegIdn().toString());
			result.setRegAlias(roadReg.getRegAlias());
			result.setFeatureIds(featureIds);
		}
			
		return result;
	}

	private RoadRegisterSearchResultDTO sortMovie(String layerId, List<String> featureIds, String sortField, String sortDirection) {
		
		RoadRegisterSearchResultDTO result = null;
		
		KwsData kwsData = dataService.selectOneRoadReg(layerId);
		validatorDataAndDataField(kwsData, layerId);

		List<Long> ids = roadRegisterSearchRepository.videoAllSummary(kwsData, featureIds, sortField, sortDirection);
		if(ids != null && ids.size() > 0) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.size(); i++)
				idList.add(ids.get(i).toString());
			
			result = new RoadRegisterSearchResultDTO();
			result.setDataId(kwsData.getDataId());
			result.setDataAlias(kwsData.getDataAlias());
			result.setIds(idList);
		}
		
		return result;
	}

	@Override
	public List<EgovMap> searchRoadVideo(KwsRoadReg roadReg, RoadRegisterSortDTO roadRegisterSortDTO) throws Exception {
	
		List<EgovMap> result = new ArrayList<EgovMap>();
		
		String sortField = roadRegisterSortDTO.getSortField();
		String sortDirection = roadRegisterSortDTO.getSortDirection();
		List<String> layers = roadRegisterSortDTO.getLayerIds();
		List<List<String>> ids = roadRegisterSortDTO.getFeatureIds();
		boolean isOriginValue = false;
		if (roadRegisterSortDTO.getIsOriginValue() != null && roadRegisterSortDTO.getIsOriginValue())
			isOriginValue = true;
		
		for(int i = 0; i < layers.size(); i++) {
			String layerId = layers.get(i);
			KwsData kwsData = dataService.selectOneRoadReg(layerId);
			validatorDataAndDataField(kwsData, layerId);
			
			List<EgovMap> features = roadRegisterSearchRepository.videoAll(kwsData, ids.get(i), isOriginValue, sortField, sortDirection);
			for (EgovMap feature : features) {
				result.add(feature);
			}
		}
		
		return result;
	}
	
	@Override
	public String selectOneVideo(KwsRoadReg roadReg, String pathIdn) throws Exception{
		
		KwsData kwsData = dataService.selectOneRoadReg(roadReg.getRegLayerId());
		Map<String, Object> rowData = roadRegisterSearchRepository.selectOneVideo(kwsData, pathIdn);
		if (rowData != null)
			return rowData.get("video_nm").toString();
		
		return null;
	}
	
	@Override
	public List<RoadRegisterSearchSummaryDTO> searchRoadAreaByBBox(KwsRoadReg roadReg, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO) throws Exception {
		List<RoadRegisterSearchSummaryDTO> result = new ArrayList<RoadRegisterSearchSummaryDTO>();
		
		RoadRegisterSearchSummaryDTO roadRegSummary = listAllRoadArea(roadReg, roadRegisterBBoxSearchDTO);
		if(roadRegSummary.getFeatureIds() != null && roadRegSummary.getFeatureIds().size() > 0)
			result.add(roadRegSummary);
		
		return result;
	}
	
	private RoadRegisterSearchSummaryDTO listAllRoadArea(KwsRoadReg roadRegister, RoadRegisterBBoxSearchDTO roadRegisterBBoxSearchDTO) {
		String layerId = roadRegister.getRegLayerId();
		String regAlias = roadRegister.getRegAlias();
		String subLayer = roadRegister.getRegSubId();
		
		RoadRegisterSearchSummaryDTO result = new RoadRegisterSearchSummaryDTO();
		result.setRegIdn(roadRegister.getRegIdn().toString());
		result.setRegAlias(regAlias);
		
		List<RoadRegisterSearchResultDTO> summary = new ArrayList<RoadRegisterSearchResultDTO>();
		List<String> ids = new ArrayList<String>();
		List<String> idn = new ArrayList<String>();
		
		KwsData kwsData = dataService.selectOneRoadReg(layerId);
		validatorDataAndDataField(kwsData, layerId);
		
		List<EgovMap> geometries = roadRegisterSearchRepository.listAllGeometry(kwsData, roadRegisterBBoxSearchDTO);

		KwsData subData = dataService.selectOneData(subLayer);
		validatorDataAndDataField(subData, subLayer);
		
		int cnt = geometries.size();
		for (int i = 0; i < cnt; i++) {
			EgovMap map = geometries.get(i);
			String ftrIdn = map.get("ftrIdn").toString();
			String geom = map.get("geom").toString();
			if (geom != null) {
				List<Long> pnu = roadRegisterSearchRepository.listAllPnu(subData, geom);
				if (pnu != null && pnu.size() > 0) {
					for (int j = 0; j < pnu.size(); j++) {
						String pnucode = pnu.get(j).toString();
						if (!ids.contains(pnucode)) {
							ids.add(pnucode);
							idn.add(ftrIdn + "." + pnucode);
						}
					}
				}
			}
		}
		
		if(ids != null && ids.size() > 0) {
			RoadRegisterSearchResultDTO res = new RoadRegisterSearchResultDTO();
			res.setDataId(subData.getDataId());
			res.setDataAlias(subData.getDataAlias());
			res.setIds(ids);
			res.setFtrIdn(idn);
			
			summary.add(res);
		}
		
		if (summary.size() > 0)
			result.setFeatureIds(summary);
		
 		return result;
	}
	
}