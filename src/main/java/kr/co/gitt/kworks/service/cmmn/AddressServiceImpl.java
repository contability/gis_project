package kr.co.gitt.kworks.service.cmmn;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterComparisonDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterComparisonDTO.ComparisonType;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class AddressServiceImpl
/// kr.co.gitt.kworks.service.cmmn \n
///   ㄴ AddressServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2018. 4. 20. 오전 11:16:56 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 주소조회 서비스구현 클래스입니다.
///   -# 
/////////////////////////////////////////////
@Service("addressService")
public class AddressServiceImpl implements AddressService {

	/// 주소조회 서비스
	@Resource
	SpatialSearchService spatialSearchService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.AddressService#getAdministrationZone(java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String getAdministrationZone(String dataId, String bjdCde) throws Exception {
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setDataId(dataId);
		spatialSearchDTO.setFilterType(FilterType.COMPARISON);
		
		FilterComparisonDTO filterComparisonDTO = new FilterComparisonDTO();
		filterComparisonDTO.setProperty("BJD_CDE");
		filterComparisonDTO.setComparisonType(ComparisonType.EQUAL_TO);
		filterComparisonDTO.setValue(bjdCde);
		spatialSearchDTO.setFilterComparisonDTO(filterComparisonDTO);
		
		String bjdNam = "";
		EgovMap egovMap = null;
		try {
			egovMap = spatialSearchService.selectOne(spatialSearchDTO);
			if(egovMap != null && !egovMap.isEmpty()) {
				bjdNam = (String)egovMap.get("bjdNam");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return bjdNam;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.AddressService#getAddress(java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String getAddress(String dataId, String pnu) throws Exception {
		SpatialSearchDTO spatialSearchDTO = new SpatialSearchDTO();
		spatialSearchDTO.setDataId(dataId);
		spatialSearchDTO.setFilterType(FilterType.COMPARISON);
		
		FilterComparisonDTO filterComparisonDTO = new FilterComparisonDTO();
		filterComparisonDTO.setProperty("PNU");
		filterComparisonDTO.setComparisonType(ComparisonType.EQUAL_TO);
		filterComparisonDTO.setValue(pnu);
		spatialSearchDTO.setFilterComparisonDTO(filterComparisonDTO);
		
		String address = "";
		EgovMap egovMap = null;
		try {
			egovMap = spatialSearchService.selectOne(spatialSearchDTO);
			if(egovMap != null && !egovMap.isEmpty()) {
				address = (String)egovMap.get("jibun");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return address;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.AddressService#getFullAddress(java.lang.String, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String getFullAddress(String zoneDataId, String dataId, String pnu) throws Exception {
		StringBuffer address = new StringBuffer();
		
		String bjdNam = getAdministrationZone(zoneDataId, pnu.substring(0, 10));
		if(StringUtils.isNotBlank(bjdNam)) {
			address.append(bjdNam);
			address.append(" ");
		}
		address.append(getAddress(dataId, pnu));
		return address.toString();
	}
	
}
