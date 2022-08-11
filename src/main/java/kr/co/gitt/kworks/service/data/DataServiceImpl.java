package kr.co.gitt.kworks.service.data;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.dto.search.DataSearchDTO;
import kr.co.gitt.kworks.mappers.KwsDataCtgryMapper;
import kr.co.gitt.kworks.mappers.KwsDataFieldCtgryMapper;
import kr.co.gitt.kworks.mappers.KwsDataFtrCdeMapper;
import kr.co.gitt.kworks.mappers.KwsDataMapper;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataCtgry;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataFieldCtgry;
import kr.co.gitt.kworks.model.KwsDataFtrCde;
import kr.co.gitt.kworks.model.KwsDataField.FieldTy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class DataServiceImpl
/// kr.co.gitt.kworks.service.data \n
///   ㄴ DataServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | admin |
///    | Date | 2016. 8. 18. 오전 11:14:31 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 서비스 구현 입니다.
/// - 수정자 : 이승재, 2021.02.19, getPkColumnName, getGeometryColumnName 구현
/////////////////////////////////////////////
@Service("dataService")
public class DataServiceImpl implements DataService {
	
	/// 데이터 맵퍼
	@Resource
	KwsDataMapper kwsDataMapper;
	
	/// 데이터 카테고리 맵퍼
	@Resource
	KwsDataCtgryMapper kwsDataCtgryMapper;
	
	/// 데이터 필드 카테고리 맵퍼
	@Resource
	KwsDataFieldCtgryMapper kwsDataFieldCtgryMapper;
	
	/// 데이터 지형지물부호 맵퍼
	@Resource
	KwsDataFtrCdeMapper kwsDataFtrCdeMapper;

	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#listAllDataCtgry()
	/////////////////////////////////////////////
	@Override
	public List<KwsDataCtgry> listAllDataCtgry() {
		return kwsDataCtgryMapper.listAll();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#selectData(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsData selectOneData(String dataId) {
		return kwsDataMapper.selectOne(dataId);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#listAllData()
	/////////////////////////////////////////////
	@Override
	public List<KwsData> listAllData(DataSearchDTO dataSearchDTO) {
		return kwsDataMapper.listAll(dataSearchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#listAllDataFieldCtgry()
	/////////////////////////////////////////////
	@Override
	public List<KwsDataFieldCtgry> listAllDataFieldCtgry() {
		return kwsDataFieldCtgryMapper.listAll();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#listAllDataFtrCde(kr.co.gitt.kworks.model.KwsDataFtrCde)
	/////////////////////////////////////////////
	@Override
	public List<KwsDataFtrCde> listAllDataFtrCde(KwsDataFtrCde kwsDataFtrCde) {
		return kwsDataFtrCdeMapper.listAll(kwsDataFtrCde);
	}
	
	// Lks : 도로대장
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#selectOneRoadReg(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsData selectOneRoadReg(String dataId) {
		return kwsDataMapper.selectOneRoadReg(dataId);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#selectOneRoadRegByAttr(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public List<KwsData> selectRoadRegByAttr(String dataId) {
		return kwsDataMapper.selectRoadRegByAttr(dataId);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#selectOneRoadRegByCode(java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public List<KwsData> selectRoadRegByCode(String dataId, String typeCode) {
		return kwsDataMapper.selectRoadRegByCode(dataId, typeCode);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#getPkColumnName(kr.co.gitt.kworks.model.KwsData)
	/////////////////////////////////////////////
	@Override
	public List<String> getPkColumnName(KwsData kwsData) {
		List<String> pkColumnNames = new ArrayList<String>();
		List<KwsDataField> kwsDataFields = kwsData.getKwsDataFields();
		for(int i=0, len=kwsDataFields.size(); i < len; i++) {
			KwsDataField kwsDataField = kwsDataFields.get(i);
			if(StringUtils.equals("Y", kwsDataField.getPkAt())) {
				pkColumnNames.add(kwsDataField.getFieldId());
			}
		}
		return pkColumnNames;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.data.DataService#getGeometryColumnName(kr.co.gitt.kworks.model.KwsData)
	/////////////////////////////////////////////
	@Override
	public List<String> getGeometryColumnName(KwsData kwsData) {
		List<String> geometryColumnNames = new ArrayList<String>();
		List<KwsDataField> kwsDataFields = kwsData.getKwsDataFields();
		for(int i=0, len=kwsDataFields.size(); i < len; i++) {
			KwsDataField kwsDataField = kwsDataFields.get(i);
			
			FieldTy fieldTy = kwsDataField.getFieldTy();
			if(fieldTy == FieldTy.GEOMETRY) {
				geometryColumnNames.add(kwsDataField.getFieldId());
			}
		}
		return geometryColumnNames;
	}	
}
