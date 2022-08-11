package kr.co.gitt.kworks.service.spatial;

import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.cityplanroad.PlanRoadSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.FieldTy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

/////////////////////////////////////////////
/// @class PostgresqlQueryGeneratorServiceImpl
/// kr.co.gitt.kworks.service.spatial \n
///   ㄴ PostgresqlQueryGeneratorServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 10. 오후 12:18:07 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 Postgresql(PostGIS) 쿼리 생성 구현 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public class PostgresqlQueryGeneratorServiceImpl implements QueryGeneratorService {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectColumnDateFromStringSql(kr.co.gitt.kworks.model.KwsDataField)
	/////////////////////////////////////////////
	@Override
	public String createSelectColumnDateFromStringSql(KwsDataField kwsDataField) {
		StringBuffer sql = new StringBuffer();
		String fieldId = kwsDataField.getFieldId();
		sql.append("CASE ");
		
		sql.append("WHEN LENGTH(").append("T.").append(fieldId).append(") > 7 ");
		sql.append("THEN ");
		sql.append("SUBSTR(").append("T.").append(fieldId).append(", 1, 4)||'-'||");
		sql.append("SUBSTR(").append("T.").append(fieldId).append(", 5, 2)||'-'||");
		sql.append("SUBSTR(").append("T.").append(fieldId).append(", 7, 2) ");
		
		sql.append("WHEN LENGTH(").append("T.").append(fieldId).append(") > 5 ");
		sql.append("THEN ");
		sql.append("SUBSTR(").append("T.").append(fieldId).append(", 1, 4)||'.'||");
		sql.append("SUBSTR(").append("T.").append(fieldId).append(", 5, 2) ");
		
		sql.append("ELSE ").append("T.").append(fieldId).append(" ");
		sql.append("END AS ").append(fieldId);
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectColumnCurrencySql(kr.co.gitt.kworks.model.KwsDataField)
	/////////////////////////////////////////////
	@Override
	public String createSelectColumnCurrencySql(KwsDataField kwsDataField) {
		StringBuffer sql = new StringBuffer();
		String fieldId = kwsDataField.getFieldId();
		if(kwsDataField.getFieldTy().equals(FieldTy.NUMBER)) {
			Integer fieldLt = kwsDataField.getFieldLt();
			Integer dcmlLt = kwsDataField.getDcmlLt();
		
			StringBuffer format = new StringBuffer();
			for(int i=0; i < fieldLt; i ++) {
				if(i!=0 && i%3==0) {
					format.insert(0, ",");
				}
				format.insert(0, "9");
			}
			
			if(format.length() > 0) {
				format.replace(format.length()-1, format.length(), "0");
			}
			
			if(dcmlLt != null && dcmlLt > 0) {
				format.append(".");
				for(int i=0; i < dcmlLt; i++) {
					format.append("9");
				}
			}
			
			sql.append("TO_CHAR(").append("T.").append(fieldId).append(", '").append(format).append("') AS ").append(kwsDataField.getFieldId());
		}
		else {
			logger.warn("CURRENCY TYPE - ["+ kwsDataField.getDataId() +" : " + kwsDataField.getFieldId() + "] IS NOT NUMBER");
			sql.append(kwsDataField.getFieldId());
		}
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectColumnWktSql(kr.co.gitt.kworks.model.KwsDataField)
	/////////////////////////////////////////////
	@Override
	public String createSelectColumnWktSql(String fieldId) {
		StringBuffer sql = new StringBuffer();
		sql.append("ST_ASTEXT(T.").append(fieldId).append(") AS ");
		sql.append(fieldId);
		sql.append(" ");
		return sql.toString();
	}

	@Override
	public String createSelectColumnWktSql(String prefix, String fieldId) {
		StringBuffer sql = new StringBuffer();
		sql.append("ST_ASTEXT(").append(prefix).append(".").append(fieldId).append(") AS ");
		sql.append(fieldId);
		sql.append(" ");
		return sql.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectWhereSpatialIntersectsSql(java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String createSelectWhereSpatialIntersectsSql(String geometryColumnName, String wkt, Boolean isReverse) {
		return createSpatialIntersectsSql("T."+geometryColumnName, createGeometrySqlByWkt(wkt), isReverse);
	}
	
	@Override
	public String createSpatialIntersectsSql(String sourceGeometryColumnName, String targetGeometryName, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append("ST_INTERSECTS(").append(sourceGeometryColumnName).append(", ");
		sql.append(targetGeometryName);
		sql.append(")");
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectWhereSpatialWithinSql(java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String createSelectWhereSpatialWithinSql(String geometryColumnName, String wkt, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append("ST_WITHIN(").append("T.").append(geometryColumnName).append(", ");
		sql.append(createGeometrySqlByWkt(wkt));
		sql.append(")");
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectFromRelationIntersectsSql(java.lang.String, java.lang.String, kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO)
	/////////////////////////////////////////////
	@Override
	public String createSelectFromRelationIntersectsSql(String geometryColumnName, String pkColumnName, FilterRelationDTO filterRelationDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("ST_INTERSECTS(").append("T.").append(geometryColumnName).append(", ");
		sql.append(createSelectFromBufferSql("R."+geometryColumnName, filterRelationDTO.getRelationBuffer()));
		sql.append(")");
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectFromRelationWithinSql(java.lang.String, java.lang.String, kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO)
	/////////////////////////////////////////////
	@Override
	public String createSelectFromRelationWithinSql(String geometryColumnName, String pkColumnName, FilterRelationDTO filterRelationDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("ST_WITHIN(").append("T.").append(geometryColumnName).append(", ");
		sql.append(createSelectFromBufferSql("R."+geometryColumnName, filterRelationDTO.getRelationBuffer()));
		sql.append(")");
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectFromBufferSql(java.lang.String, java.lang.Integer)
	/////////////////////////////////////////////
	@Override
	public String createSelectFromBufferSql(String geometryColumnName, Integer buffer) {
		StringBuffer sql = new StringBuffer();
		if(buffer != null && buffer > 0) {
			sql.append("ST_BUFFER(").append(geometryColumnName).append(", ").append(buffer).append(")");
		}
		else {
			sql.append(geometryColumnName);
		}
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createGeometrySqlByWkt(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public String createGeometrySqlByWkt(String wkt) {
		String projection = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		projection = projection.replace("EPSG:", "");
		
		StringBuffer sql = new StringBuffer();
		sql.append("ST_GEOMFROMTEXT('").append(wkt).append("'").append(")");
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelctPagingSql(java.lang.String, kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO)
	/////////////////////////////////////////////
	@Override
	public String createSelctPagingSql(String selectSql, SpatialSearchDTO spatialSearchDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(selectSql);
		sql.append(" LIMIT ").append(spatialSearchDTO.getRecordCountPerPage());
		sql.append(" OFFSET ").append(spatialSearchDTO.getFirstIndex());
		return sql.toString();
	}
	
	@Override
	public String createSelctPagingSql(String selectSql, PlanRoadSearchDTO searchDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(selectSql);
		sql.append(" LIMIT ").append(searchDTO.getRecordCountPerPage());
		sql.append(" OFFSET ").append(searchDTO.getFirstIndex());
		return sql.toString();
	}
}
