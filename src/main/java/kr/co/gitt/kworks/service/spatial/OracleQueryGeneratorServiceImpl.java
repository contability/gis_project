package kr.co.gitt.kworks.service.spatial;

import kr.co.gitt.kworks.cmmn.dto.cityplanroad.PlanRoadSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsDataField.FieldTy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/////////////////////////////////////////////
/// @class OracleQueryGeneratorServiceImpl
/// kr.co.gitt.kworks.service.spatial \n
///   ㄴ OracleQueryGeneratorServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 19. 오후 3:08:00 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 오라클 쿼리 생성 구현 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public class OracleQueryGeneratorServiceImpl implements QueryGeneratorService {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

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
		sql.append("T.").append(fieldId);
		sql.append(".GET_WKT() AS ");
		sql.append(fieldId);
		sql.append(" ");
		return sql.toString();
	}

	@Override
	public String createSelectColumnWktSql(String prefix, String fieldId) {
		StringBuffer sql = new StringBuffer();
		sql.append(prefix).append(".").append(fieldId);
		sql.append(".GET_WKT() AS ");
		sql.append(fieldId);
		sql.append(" ");
		return sql.toString();
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectWhereSpatialIntersectsSql(java.lang.String, kr.co.gitt.kworks.cmmn.dto.SpatialSearchDTO)
	/////////////////////////////////////////////
	@Override
	public String createSelectWhereSpatialIntersectsSql(String geometryColumnName, String wkt, Boolean isReverse) {
		return createSpatialIntersectsSql("T."+geometryColumnName, createGeometrySqlByWkt(wkt), isReverse);
	}
	
	@Override
	public String createSpatialIntersectsSql(String sourceGeometryColumnName, String targetGeometryName, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append("SDO_ANYINTERACT(").append(sourceGeometryColumnName).append(", ");
		sql.append(targetGeometryName);
		sql.append(") = 'TRUE'");
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelectWhereSpatialContainsSql(java.lang.String, kr.co.gitt.kworks.cmmn.dto.SpatialSearchDTO)
	/////////////////////////////////////////////
	@Override
	public String createSelectWhereSpatialWithinSql(String geometryColumnName, String wkt, Boolean isReverse) {
		StringBuffer sql = new StringBuffer();
		sql.append("SDO_INSIDE(").append("T.").append(geometryColumnName).append(", ");
		sql.append(createGeometrySqlByWkt(wkt));
		sql.append(") = 'TRUE'");
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
		sql.append("SDO_ANYINTERACT(").append("T.").append(geometryColumnName).append(", ");
		sql.append(createSelectFromBufferSql("R."+geometryColumnName, filterRelationDTO.getRelationBuffer()));
		sql.append(") = 'TRUE'");
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
		sql.append("SDO_INSIDE(").append("T.").append(geometryColumnName).append(", ");
		sql.append(createSelectFromBufferSql("R."+geometryColumnName, filterRelationDTO.getRelationBuffer()));
		sql.append(") = 'TRUE'");
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
			sql.append("SDO_GEOM.SDO_BUFFER(").append(geometryColumnName).append(", ").append(buffer).append(", 0.05, 'ARC_TOLERANCE=0.05 UNIT=M')");
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
		StringBuffer sql = new StringBuffer();
		sql.append("SDO_UTIL.FROM_WKTGEOMETRY('").append(wkt).append("')");
		return sql.toString();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.spatial.QueryGeneratorService#createSelctPagingSql(java.lang.String, int, int)
	/////////////////////////////////////////////
	@Override
	public String createSelctPagingSql(String selectSql, SpatialSearchDTO spatialSearchDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T2.* FROM (");
		sql.append("SELECT ROWNUM AS RNUM, T1.* FROM (");
		sql.append(selectSql);
		sql.append(") T1 WHERE ROWNUM <= ").append(spatialSearchDTO.getLastIndex());
		sql.append(") T2 WHERE RNUM > ").append(spatialSearchDTO.getFirstIndex());
		return sql.toString();
	}

	@Override
	public String createSelctPagingSql(String selectSql, PlanRoadSearchDTO searchDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T2.* FROM (");
		sql.append("SELECT ROWNUM AS RNUM, T1.* FROM (");
		sql.append(selectSql);
		sql.append(") T1 WHERE ROWNUM <= ").append(searchDTO.getLastIndex());
		sql.append(") T2 WHERE RNUM > ").append(searchDTO.getFirstIndex());
		return sql.toString();
	}
}
