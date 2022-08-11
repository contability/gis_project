package kr.co.gitt.kworks.service.layer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.LayerSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.KwsLyrCtgryMapper;
import kr.co.gitt.kworks.mappers.KwsLyrMapper;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsLyr;
import kr.co.gitt.kworks.model.KwsLyrCtgry;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.dept.DeptService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class LayerServiceImpl
/// kr.co.gitt.kworks.service.lyr \n
///   ㄴ LayerServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 11. 오후 6:01:54 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 레이어 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("lyrService")
public class LayerServiceImpl implements LayerService {
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	// 부서 서비스
	@Resource
	DeptService deptService;
	
	/// 레이어 카테고리 맵퍼
	@Resource
	KwsLyrCtgryMapper kwsLyrCtgryMapper;
	
	/// 레이어 맵퍼
	@Resource
	KwsLyrMapper kwsLyrMapper;
	
	// 도메인 코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	@Resource
	private MessageSource messageSource;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.lyr.LyrService#listAllLyrCategory(kr.co.gitt.kworks.cmmn.dto.LayerSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsLyrCtgry> listAllLayerCategory(LayerSearchDTO layerSearchDTO)
			throws JsonProcessingException, IOException {

		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());

		if (StringUtils.equals(prjCode, "dh")) {
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();

			String deptCode = userDTO.getDeptCode();

			KwsDept kwsDept = deptService.selectOneDept(deptCode);
			
			List<KwsLyrCtgry> kwsLyrCtgrys = kwsLyrCtgryMapper
					.listAll(layerSearchDTO);

			for (int i = 0; i < kwsLyrCtgrys.size(); i++) {
				List<KwsLyr> kwsLyrs = kwsLyrCtgrys.get(i).getKwsLyrs();
				List<KwsLyr> newKwsLyrs = new ArrayList<KwsLyr>();

				for (int j = 0; j < kwsLyrs.size(); j++) {

					KwsLyr kwsLyr = kwsLyrs.get(j);

					if (!StringUtils.equals(deptCode, "4210209") && (StringUtils.equals(kwsLyr.getLyrId(), "BML_PROP_AS") || StringUtils.equals(kwsLyr.getLyrId(),"BML_BUID_AS") || StringUtils.equals(kwsLyr.getLyrId(),"BML_LOAN_AS") || StringUtils.equals(kwsLyr.getLyrId(),"BML_OCCP_AS"))) {
							ObjectNode newLyrBassStyle = new ObjectMapper().createObjectNode();

							newLyrBassStyle.put("visible", true);
							newLyrBassStyle.put("name", kwsLyr.getLyrId());

							String dataNm = dataService.selectOneData(kwsLyr.getDataId()).getDataAlias();
							newLyrBassStyle.put("title", dataNm);

							ArrayNode rules = new ObjectMapper().createArrayNode();

							ObjectNode rule = new ObjectMapper().createObjectNode();
							rule.put("visible", true);

							ObjectNode filter = new ObjectMapper().createObjectNode();
							filter.put("type", "==");
							filter.put("property", "MAN_CDE");
							filter.put("literal", deptCode + "0000");
							rule.put("filter", filter);

							ObjectNode polygon = new ObjectMapper().createObjectNode();

							if (StringUtils.equals(kwsLyr.getLyrId(),"BML_PROP_AS")) {
								polygon.put("fill", "#a92fa0");
								polygon.put("fillOpacity", "0.5");
								polygon.put("stroke", "#0000FF");
								polygon.put("strokeOpacity", "1.0");
								polygon.put("strokeWidth", "1.0");
							}else if(StringUtils.equals(kwsLyr.getLyrId(), "BML_BUID_AS")){
								polygon.put("fill", "#5a6391");
								polygon.put("fillOpacity", "0.5");
								polygon.put("stroke", "#2ee6d1");
								polygon.put("strokeOpacity", "1.0");
								polygon.put("strokeWidth", "1.0");
							}else if(StringUtils.equals(kwsLyr.getLyrId(), "BML_LOAN_AS")){
								polygon.put("fill", "#49EE7F");
								polygon.put("fillOpacity", "0.5");
								polygon.put("stroke", "#4d1c69");
								polygon.put("strokeOpacity", "1.0");
								polygon.put("strokeWidth", "1.0");
							}else{
								polygon.put("fill", "#aed93d");
								polygon.put("fillOpacity", "0.5");
								polygon.put("stroke", "#414756");
								polygon.put("strokeOpacity", "1.0");
								polygon.put("strokeWidth", "1.0");
							}
							rule.put("polygon", polygon);
							rule.put("name", "DEFAULT");
							
							KwsDomnCode domnCode = domnCodeService.selectOneById("KWS-1835", kwsDept.getDeptCode() + "0000");
							
							if(domnCode != null){
								rule.put("title", domnCode.getCodeNm());
							}else{
								rule.put("title", kwsDept.getDeptNm());
							}

							rules.add(rule);

							newLyrBassStyle.put("rules", rules);

							kwsLyr.setLyrBassStyle(newLyrBassStyle.toString());
					}
					newKwsLyrs.add(kwsLyr);
				}
				kwsLyrCtgrys.get(i).setKwsLyrs(newKwsLyrs);
			}
			return kwsLyrCtgrys;
		}else {
			return kwsLyrCtgryMapper.listAll(layerSearchDTO);
		}
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.layer.LayerService#listAllLayer(kr.co.gitt.kworks.cmmn.dto.LayerSearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsLyr> listAllLayer(LayerSearchDTO layerSearchDTO) {
		return kwsLyrMapper.listAll(layerSearchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.layer.LayerService#selectOneLayer(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsLyr selectOneLayer(String lyrId) {
		return kwsLyrMapper.selectOne(lyrId);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.layer.LayerService#selectOneLayerByDataId(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsLyr selectOneLayerByDataId(String dataId) {
		KwsLyr kwsLyr = new KwsLyr();
		kwsLyr.setDataId(dataId);
		return kwsLyrMapper.selectOneByKwsLyr(kwsLyr);
	}
}
