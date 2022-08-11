package kr.co.gitt.kworks.projects.yg.service.ecologyNatureMap;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import kr.co.gitt.kworks.cmmn.dto.SpatialType;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterComparisonDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterComparisonDTO.ComparisonType;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterSpatialDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchResultDTO;
import kr.co.gitt.kworks.contact.kras.model.KrasResponse;
import kr.co.gitt.kworks.contact.kras.model.LandInfo;
import kr.co.gitt.kworks.contact.kras.model.LandUsePlanCnfInfoSet;
import kr.co.gitt.kworks.contact.kras.model.LandUsePlanRestrict;
import kr.co.gitt.kworks.contact.kras.service.KrasService;
import kr.co.gitt.kworks.mappers.LpPaCbndStatsMapper;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.LpPaCbndStats;
import kr.co.gitt.kworks.projects.yg.dto.EcologyNatureMapAttrDTO;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("ecologyNatureMapService")
@Profile({"yg_dev", "yg_oper"})
public class EcologyNatureMapServiceImpl implements EcologyNatureMapService {

	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	@Resource
	SpatialSearchService spatialSearchService;
	
	/// 소유자별 통계 연속지적 맵퍼
	@Resource
	LpPaCbndStatsMapper lpPaCbndStatsMapper;
	
	/// 부동산종합공부시스템 연계 서비스
	@Resource(name="krasCommonService")
	KrasService krasCommonService;
	
	/// 도메인 코드 서비스
	@Resource(name="domnCodeService")
	DomnCodeService domnCodeService;

	@Override
	public List<SpatialSearchResultDTO> search(String dataId, String pnu) throws Exception {
		List<SpatialSearchResultDTO> data = new ArrayList<SpatialSearchResultDTO>();
		
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		FilterComparisonDTO filterComparisonDTO = new FilterComparisonDTO();
		filterComparisonDTO.setComparisonType(ComparisonType.EQUAL_TO);
		filterComparisonDTO.setProperty("PNU");
		filterComparisonDTO.setValue(pnu);
		spatialSearchDTO.setFilterType(FilterType.COMPARISON);
		spatialSearchDTO.setFilterComparisonDTO(filterComparisonDTO);
		
		List<EgovMap> rows = spatialSearchService.listAll(dataId, spatialSearchDTO);
		if(rows != null && rows.size() > 0) {
			SpatialSearchResultDTO spatialSearchResultDTO = new SpatialSearchResultDTO();
			spatialSearchResultDTO.setDataId(dataId);
			spatialSearchResultDTO.setRows(rows);
			data.add(spatialSearchResultDTO);		
			
			SpatialSearchDTO ecologyNatureMapSearchDTO = new SpatialSearchDTO();
			List<String> dataIds = new ArrayList<String>();
			dataIds.add("NEL_ECO1_AS");
			dataIds.add("NEL_ECO2_AS");
			dataIds.add("NEL_ECO3_AS");
			dataIds.add("NEL_ECO9_AS");
			
			Long fid = new Long(0);
			Object fidObj = rows.get(0).get("objectid");
			if(fidObj instanceof BigDecimal) {
				fid = ((BigDecimal) fidObj).longValue();
			}
			else {
				fid = (Long) fidObj;
			}
			
			FilterRelationDTO filterRelationDTO = new FilterRelationDTO();
			filterRelationDTO.setSpatialType(SpatialType.INTERSECTS);
			filterRelationDTO.setRelationDataId(dataId);
			filterRelationDTO.setFid(fid);
			
			ecologyNatureMapSearchDTO.setDataIds(dataIds);
			ecologyNatureMapSearchDTO.setFilterType(FilterType.RELATION);
			ecologyNatureMapSearchDTO.setFilterRelationDTO(filterRelationDTO);
			
			data.addAll(spatialSearchService.search(ecologyNatureMapSearchDTO));
		}
		
		return data;
	}

	@Override
	public EcologyNatureMapAttrDTO searchJibun(String dataId, String pnu) throws Exception {
		EcologyNatureMapAttrDTO ecologyNatureMapAttrDTO = new EcologyNatureMapAttrDTO();
		
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setFilterType(FilterType.COMPARISON);
		
		FilterComparisonDTO filterComparisonDTO = new FilterComparisonDTO();
		filterComparisonDTO.setComparisonType(ComparisonType.EQUAL_TO);
		filterComparisonDTO.setProperty("PNU");
		filterComparisonDTO.setValue(pnu);
		spatialSearchDTO.setFilterComparisonDTO(filterComparisonDTO);
		
		List<EgovMap> list = spatialSearchService.listAll(dataId, spatialSearchDTO);
		
		EgovMap map = null;
		if(list.size() > 0) {
			map = list.get(0);
		}
		
		
		if(map != null) {
			String geometryName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
			String wkt = map.get(geometryName.toLowerCase()).toString();
			
			// 도엽번호
			List<EgovMap> i050List = searchBmlI050As(wkt);
			List<String> shtNums = new ArrayList<String>();
			for(int i=0, len=i050List.size(); i < len; i++) {
				shtNums.add((String) i050List.get(i).get("shtNum"));
			}
			ecologyNatureMapAttrDTO.setShtNums(shtNums);
			
			/// 토지피복정보
			List<EgovMap> cbrdList = searchNelCbrdAs(wkt);
			Set<String> useCls = new HashSet<String>();
			for(int i=0, len=cbrdList.size(); i < len; i++) {
				useCls.add((String) cbrdList.get(i).get("useCl"));
			}
			ecologyNatureMapAttrDTO.setUseCls(useCls);
			
		}
		
		// 면적, 지목, 소유자
		KrasResponse landResponse = getLandInfo(pnu);
		if(landResponse != null && landResponse.getBody() != null && landResponse.getBody().getData() != null) {
			LandInfo landInfo = (LandInfo) landResponse.getBody().getData();
			ecologyNatureMapAttrDTO.setParea(landInfo.getParea());
			ecologyNatureMapAttrDTO.setJimokNm(landInfo.getJimokNm());
			ecologyNatureMapAttrDTO.setOwnerNm(landInfo.getOwnerNm());
		}
		
		/// 토지용도지역
		KrasResponse landUsePlanCnfInfoResponse = getLandUsePlanCnfInfo(pnu);
		if(landUsePlanCnfInfoResponse != null && landUsePlanCnfInfoResponse.getBody() != null && landUsePlanCnfInfoResponse.getBody().getData() != null) {
			ecologyNatureMapAttrDTO.setUsePlans(getUCodes(landUsePlanCnfInfoResponse));
		}
		
		// 지형특성도
		List<EgovMap> tgctList = searchNelTgctAs(pnu);
		if(tgctList != null && tgctList.size() > 0) {
			EgovMap tgctMap = tgctList.get(0);
			ecologyNatureMapAttrDTO.setGrdnt(tgctMap.get("grdnt").toString().trim());
			ecologyNatureMapAttrDTO.setSfhht(tgctMap.get("sfhht").toString().trim());
			ecologyNatureMapAttrDTO.setSltNgl(tgctMap.get("sltNgl").toString().trim());
			ecologyNatureMapAttrDTO.setSltDrc(tgctMap.get("sltDrc").toString().trim());
		}
		return ecologyNatureMapAttrDTO;
	}
	
	private List<EgovMap> searchBmlI050As(String wkt) throws Exception {
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setFilterType(FilterType.SPATIAL);
		
		FilterSpatialDTO filterSpatialDTO = new FilterSpatialDTO();
		filterSpatialDTO.setSpatialType(SpatialType.INTERSECTS);
		filterSpatialDTO.setWkt(wkt);
		spatialSearchDTO.setFilterSpatialDTO(filterSpatialDTO);
		
		return spatialSearchService.listAll("BML_I050_AS", spatialSearchDTO);
	}
	
	private KrasResponse getLandInfo(String pnu) throws ClientProtocolException, IOException, JAXBException {
		List<NameValuePair> parameters = getKrasCommonParameter(pnu, "KRAS000002");
		return krasCommonService.call(parameters, 10);
	}
	
	private List<NameValuePair> getKrasCommonParameter(String pnu, String connSvcId) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("adm_sect_cd", pnu.substring(0, 5)));
		parameters.add(new BasicNameValuePair("land_loc_cd", pnu.substring(5, 10)));
		parameters.add(new BasicNameValuePair("ledg_gbn", pnu.substring(10, 11)));
		parameters.add(new BasicNameValuePair("bobn", String.valueOf(Integer.parseInt(pnu.substring(11, 15)))));
		parameters.add(new BasicNameValuePair("bubn", String.valueOf(Integer.parseInt(pnu.substring(15, 19)))));
		parameters.add(new BasicNameValuePair("conn_svc_id", connSvcId));
		return parameters;
	}
	
	private List<EgovMap> searchNelTgctAs(String pnu) throws Exception {
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setFilterType(FilterType.COMPARISON);
		
		FilterComparisonDTO filterComparisonDTO = new FilterComparisonDTO();
		filterComparisonDTO.setComparisonType(ComparisonType.EQUAL_TO);
		filterComparisonDTO.setProperty("PNU");
		filterComparisonDTO.setValue(pnu);
		spatialSearchDTO.setFilterComparisonDTO(filterComparisonDTO);
		
		return spatialSearchService.listAll("NEL_TGCT_AS", spatialSearchDTO);
	}
	
	private KrasResponse getLandUsePlanCnfInfo(String pnu) throws ClientProtocolException, IOException, JAXBException {
		List<NameValuePair> parameters = getKrasCommonParameter(pnu, "KRAS000026");
		parameters.add(new BasicNameValuePair("map_width", "325"));
		parameters.add(new BasicNameValuePair("map_height", "200"));
		parameters.add(new BasicNameValuePair("iss_scale", "500"));
		parameters.add(new BasicNameValuePair("restrict_yn", "Y"));
		
		return krasCommonService.call(parameters, 10);
	}
	
	private List<EgovMap> searchNelCbrdAs(String wkt) throws Exception {
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setFilterType(FilterType.SPATIAL);
		
		FilterSpatialDTO filterSpatialDTO = new FilterSpatialDTO();
		filterSpatialDTO.setSpatialType(SpatialType.INTERSECTS);
		filterSpatialDTO.setWkt(wkt);
		spatialSearchDTO.setFilterSpatialDTO(filterSpatialDTO);
		
		return spatialSearchService.listAll("NEL_CBRD_AS", spatialSearchDTO);
	}
	
	private List<KwsDomnCode> getUCodes(KrasResponse usePlan) {
		List<KwsDomnCode> ucodes = new ArrayList<KwsDomnCode>();
		
		// 행위제한구역 구역코드 목록 반환
		List<String> codeValues = new ArrayList<String>();
		if(usePlan != null && usePlan.getBody() != null && usePlan.getBody().getData() != null) {
			LandUsePlanCnfInfoSet landUsePlanCnfInfoSet = (LandUsePlanCnfInfoSet) usePlan.getBody().getData();
			if(landUsePlanCnfInfoSet != null && landUsePlanCnfInfoSet.getLandUsePlanRestrictList() != null) {
				for(LandUsePlanRestrict landUsePlanRestrict : landUsePlanCnfInfoSet.getLandUsePlanRestrictList()) {
					String lawLevel = landUsePlanRestrict.getLawLevel();
					String codeValue = landUsePlanRestrict.getUcode();
					if(StringUtils.equals("0", lawLevel) && !codeValues.contains(codeValue)) {
						codeValues.add(codeValue);
					}
				}
			}
		}
		
		// 용도지역지구구분 코드 목록
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setDomnId("KWS-9900");
		List<KwsDomnCode> codes = domnCodeService.listDomnCode(kwsDomnCode);
		
		for(String codeId : codeValues) {
			KwsDomnCode ucode = null;
			for(KwsDomnCode codeVO : codes) {
				if(StringUtils.equals(codeId, codeVO.getCodeId())) {
					ucode = codeVO;
					ucodes.add(ucode);
					break;
				}
			}
			// 코드 테이블에 없는 경우 코드값으로 명칭까지 사용
			if(ucode == null) {
				ucode = new KwsDomnCode();
				ucode.setCodeId(codeId);
				ucode.setCodeNm(codeId);
				ucodes.add(ucode);
			}
		}
		
		return ucodes;
	}
	
	@Override
	public List<LpPaCbndStats> list(LpPaCbndStats lpPaCbndStats) {
		return lpPaCbndStatsMapper.list(lpPaCbndStats);
	}
	
	@Override
	public LpPaCbndStats select(LpPaCbndStats lpPaCbndStats) {
		return lpPaCbndStatsMapper.select(lpPaCbndStats);
	}
}
