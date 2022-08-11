package kr.co.gitt.kworks.projects.gn.service.cityPlanRoad;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.cityplanroad.PlanRoadSearchDTO;
import kr.co.gitt.kworks.mappers.KwsDomnCodeMapper;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.gn.mappers.UrbPlanHtMapper;
import kr.co.gitt.kworks.projects.gn.mappers.UrbPlanMaMapper;
import kr.co.gitt.kworks.projects.gn.model.UrbPlanHt;
import kr.co.gitt.kworks.projects.gn.model.UrbPlanMa;
import kr.co.gitt.kworks.repository.CityPlanRoadSearchRepository;
import kr.co.gitt.kworks.service.data.DataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.CamelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("cityPlanRoadSearchService")
@Profile({"gn_dev", "gn_oper"})
public class CityPlanRoadSearchServiceImpl implements CityPlanRoadSearchService {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 프로퍼티
	 */
	@Resource
    private MessageSource messageSource;
	
	/**
	 * 데이터 서비스
	 */
	@Resource
	DataService dataService;
	
	/**
	 * 저장소
	 */
	@Resource
	CityPlanRoadSearchRepository cityPlanRoadSearchRepository;
	
	/**
	 * 도시계획도로 맵퍼
	 */
	@Resource
	UrbPlanMaMapper urbPlanMaMapper;	
	
	/**
	 * 도시계획도로 변경이력 맵퍼
	 */
	@Resource
	UrbPlanHtMapper urbPlanHtMapper;	

	/**
	 * 도메인 코드 맵퍼
	 */
	@Resource
	KwsDomnCodeMapper kwsDomnCodeMapper;

	/**
	 * 도시계획도로 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService urbPlanMaIdGnrService;
	
	
	/**
	 * 테이블과 컬럼 검증
	 * @param kwsData
	 * @param dataId
	 */
	private void validatorDataAndDataField(KwsData kwsData, String dataId) {
		/// 데이터 없음
		if(kwsData == null) {
			logger.warn("No Data : " + dataId);
			throw new RuntimeException("No Data : " + dataId);
		}
		
		List<KwsDataField> dataFields = kwsData.getKwsDataFields();
		/// 데이터 필드 없음
		if(dataFields == null || dataFields.size() < 1) {
			logger.warn("No Data Field : " + dataId);
			throw new RuntimeException("No Data Field : " + dataId);
		}
	}	
	
	@Override
	public List<EgovMap> listRegister(PlanRoadSearchDTO searchDTO, PaginationInfo paginationInfo) throws Exception {
		
		String spatial = messageSource.getMessage("Extension.CityPlanRoad.Spatial", null, Locale.getDefault());
		String attribute = messageSource.getMessage("Extension.CityPlanRoad.Attribute", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		String attributeKey = messageSource.getMessage("Extension.CityPlanRoad.Attribute.Field", null, Locale.getDefault());
		String geomName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault()).toLowerCase();
		
		KwsData attrData = dataService.selectOneData(attribute);
		validatorDataAndDataField(attrData, attribute);
		
		StringBuilder condition = new StringBuilder();
		condition.append(" UPR_NAM LIKE '%").append(searchDTO.getName()).append("%'");
		if (searchDTO.getGrade() != null && searchDTO.getGrade().length() > 0) {
			condition.append(" AND UPR_GRD = '").append(searchDTO.getGrade()).append("'");
		}
		if (searchDTO.getType() != null) {
			condition.append(" AND UPR_TYP = '").append(searchDTO.getType()).append("'");
		}
		if (searchDTO.getNum() != null && searchDTO.getNum().length() > 0) {
			condition.append(" AND UPR_NUM = '").append(searchDTO.getNum()).append("'");
		}
		
		String sql = condition.toString();
		Integer totalRecordCount = cityPlanRoadSearchRepository.listCount(attrData, sql);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		List<EgovMap> result = cityPlanRoadSearchRepository.list(attrData, searchDTO, sql);
		if (result != null && result.size() > 0) {
			KwsData geomData = dataService.selectOneData(spatial);
			validatorDataAndDataField(geomData, spatial);
			
			for (EgovMap data : result) {
				String mnum = data.get(CamelUtil.convert2CamelCase(attributeKey)).toString();
				String spatialSql = " " + spatialKey + " = '" + mnum + "'";
				
				EgovMap geometry = cityPlanRoadSearchRepository.selectGeometry(geomData, geomName, spatialSql);
				String colName = CamelUtil.convert2CamelCase(geomName);
				data.put(colName, geometry.get(colName));
			}
		}
		
		return result;
	}	

	@Override
	public EgovMap getRegister(String uprIdn) throws Exception	{

		String attribute = messageSource.getMessage("Extension.CityPlanRoad.Attribute", null, Locale.getDefault());
		String field = messageSource.getMessage("Extension.CityPlanRoad.Attribute.Field", null, Locale.getDefault());
		
		KwsData attrData = dataService.selectOneData(attribute);
		validatorDataAndDataField(attrData, attribute);
		
		StringBuilder condition = new StringBuilder();
		condition.append(field).append(" = '").append(uprIdn).append("'");
		String sql = condition.toString();
		
		EgovMap result = cityPlanRoadSearchRepository.selectOne(attrData, sql, true);
		return result;
	}
	
	@Override
	public EgovMap getRegister(Long ftrIdn, boolean isOrigin) throws Exception	{

		String attribute = messageSource.getMessage("Extension.CityPlanRoad.Attribute", null, Locale.getDefault());
		
		KwsData attrData = dataService.selectOneData(attribute);
		validatorDataAndDataField(attrData, attribute);
		
		StringBuilder condition = new StringBuilder();
		condition.append("FTR_IDN = ").append(ftrIdn);
		String sql = condition.toString();
		
		EgovMap result = cityPlanRoadSearchRepository.selectOne(attrData, sql, isOrigin);
		
		if (!isOrigin && !result.isEmpty()) {
			String bjdCode = result.get("uprBjd") + "00000";
			KwsDomnCode code = kwsDomnCodeMapper.selectOneById("KWS-0106", bjdCode);
			result.put("uprBjd", code.getCodeNm());
		}
		
		return result;
	}

	@Override
	public Integer deleteRegister(Long ftrIdn) throws Exception {
		
		urbPlanHtMapper.deleteAll(ftrIdn);
		return urbPlanMaMapper.delete(ftrIdn);
	}

	@Override
	public Integer updateRegister(Long ftrIdn, UrbPlanMa data) throws Exception {
		
		return urbPlanMaMapper.update(ftrIdn, data);
	}

	@Override
	public Integer insertRegister(UrbPlanMa data) throws Exception {
		Long ftrIdn = urbPlanMaIdGnrService.getNextLongId();
		data.setFtrIdn(ftrIdn);
		
		return urbPlanMaMapper.insert(data);
	}
	
	@Override
	public List<EgovMap> getHistory(Long ftrIdn, boolean isOrigin) throws Exception	{

		String history = messageSource.getMessage("Extension.CityPlanRoad.History", null, Locale.getDefault());
		
		KwsData histData = dataService.selectOneData(history);
		validatorDataAndDataField(histData, history);
		
		StringBuilder condition = new StringBuilder();
		condition.append("FTR_IDN = ").append(ftrIdn);
		String sql = condition.toString();
		
		List<EgovMap> result = cityPlanRoadSearchRepository.selectHistory(histData, sql, isOrigin);
		return result;
	}
	
	@Override
	public EgovMap getDetail(Long ftrIdn, Long uprSeq, boolean isOrigin) throws Exception	{

		String history = messageSource.getMessage("Extension.CityPlanRoad.History", null, Locale.getDefault());
		
		KwsData histData = dataService.selectOneData(history);
		validatorDataAndDataField(histData, history);
		
		StringBuilder condition = new StringBuilder();
		condition.append("FTR_IDN = ").append(ftrIdn);
		condition.append(" AND UPR_SEQ = ").append(uprSeq);
		String sql = condition.toString();
		
		EgovMap result = cityPlanRoadSearchRepository.selectOne(histData, sql, isOrigin);
		return result;
	}

	@Override
	public Integer deleteHistory(Long ftrIdn) throws Exception {
		
		return urbPlanHtMapper.deleteAll(ftrIdn);
	}

	@Override
	public Integer deleteHistory(Long ftrIdn, Long uprSeq) throws Exception {
		
		return urbPlanHtMapper.delete(ftrIdn, uprSeq);
	}

	@Override
	public Integer updateHistory(Long ftrIdn, Long uprSeq, UrbPlanHt data) throws Exception {
		
		return urbPlanHtMapper.update(ftrIdn, uprSeq, data);
	}

	@Override
	public Integer insertHistory(Long ftrIdn, UrbPlanHt data) throws Exception {
		
		Integer count = urbPlanHtMapper.getCount(ftrIdn);
		if (count == 0) {
			data.setUprSeq(1);
		}
		else {
			Integer seq = urbPlanHtMapper.getLastSequence(ftrIdn);
			data.setUprSeq(seq + 1);
		}
		
		return urbPlanHtMapper.insert(data);
	}
	
}
