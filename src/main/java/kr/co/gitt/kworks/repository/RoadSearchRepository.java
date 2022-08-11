package kr.co.gitt.kworks.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.model.RdtRnmgDt;
import kr.co.gitt.kworks.model.RdtRoutDt;
import kr.co.gitt.kworks.service.spatial.QueryGeneratorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTWriter;

/////////////////////////////////////////////
/// @class RoadSearchRepository
/// kr.co.gitt.kworks.repository \n
///   ㄴ RoadSearchRepository.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오전 10:24:00 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 검색 저장소 입니다.
///   -# 
/////////////////////////////////////////////
@Repository("roadSearchRepository")
public class RoadSearchRepository {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;

	/// JdbcTemplate 
	@Resource
	JdbcTemplate jdbcTemplate;
	
	/// 쿼리 생성 서비스
	@Resource
	QueryGeneratorService queryGeneratorService;
	
	/////////////////////////////////////////////
	/// @fn searchRdtRoutDtByExtent
	/// @brief 함수 간략한 설명 : 영역에 포함되는 노선 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param filterBboxDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtRoutDt> searchRdtRoutDtByExtent(FilterBboxDTO filterBboxDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ROUT.FTR_CDE AS FTR_CDE, ROUT.FTR_IDN AS FTR_IDN, ROUT.RUT_NAM as RUT_NAM ");
		sql.append("FROM RDT_ROUT_DT ROUT ");
		sql.append("INNER JOIN RDT_RTCN_DT RTCN ON ROUT.FTR_IDN = RTCN.FTR_IDN ");
		sql.append("INNER JOIN RDL_CTLR_LS T ON RTCN.SEC_IDN = T.SEC_IDN ");
		sql.append("WHERE ");
		sql.append(createSelectWhereBboxSql(filterBboxDTO));
		sql.append(" ORDER BY RUT_NAM ");
		
		List<RdtRoutDt> list = jdbcTemplate.query(sql.toString(), new RowMapper<RdtRoutDt>() {
			@Override
			public RdtRoutDt mapRow(ResultSet rs, int rowNum) throws SQLException {
				RdtRoutDt rdtRoutDt = new RdtRoutDt();
				rdtRoutDt.setFtrCde(rs.getString("FTR_CDE"));
				rdtRoutDt.setFtrIdn(rs.getLong("FTR_IDN"));
				rdtRoutDt.setRutNam(rs.getString("RUT_NAM"));
				return rdtRoutDt;
			}
		});
		return list;
	}
	
	/////////////////////////////////////////////
	/// @fn searchRdtRnmgDtByExtent
	/// @brief 함수 간략한 설명 : 영역에 포함되는 도로 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param filterBboxDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RdtRnmgDt> searchRdtRnmgDtByExtent(FilterBboxDTO filterBboxDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ROAD.RDN_CDE AS RDN_CDE, ROAD.RDN_NAM AS RDN_NAM ");
		sql.append("FROM RDT_RNMG_DT ROAD ");
		sql.append("INNER JOIN RDL_CTLR_LS T ON ROAD.RDN_CDE = T.RDN_CDE ");
		sql.append("WHERE ");
		sql.append(createSelectWhereBboxSql(filterBboxDTO));
		sql.append(" GROUP BY ROAD.RDN_CDE, ROAD.RDN_NAM ");
		sql.append(" ORDER BY RDN_NAM ");
		
		List<RdtRnmgDt> list = jdbcTemplate.query(sql.toString(), new RowMapper<RdtRnmgDt>() {
			@Override
			public RdtRnmgDt mapRow(ResultSet rs, int rowNum) throws SQLException {
				RdtRnmgDt rdtRnmgDt = new RdtRnmgDt();
				rdtRnmgDt.setRdnCde(rs.getString("RDN_CDE"));
				rdtRnmgDt.setRdnNam(rs.getString("RDN_NAM"));
				return rdtRnmgDt;
			}
		});
		return list;
	}
	
	/////////////////////////////////////////////
	/// @fn searchFacility
	/// @brief 함수 간략한 설명 : 시설물 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param relationDataId
	/// @param fids
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<Long> searchFacility(String dataId, String relationDataId, List<Long> fids) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT(T.OBJECTID) FROM ").append(dataId).append(" T ");
		
		sql.append("INNER JOIN ").append(relationDataId).append(" T2 ON ");
		sql.append(queryGeneratorService.createSpatialIntersectsSql("T.GEOM", "T2.GEOM", false)).append(" ");
		sql.append("WHERE T2.OBJECTID IN (");
		for(int i=0, len=fids.size(); i < len; i++) {
			if(i >= 1000) {
				break;
			}
			if(i!=0) {
				sql.append(", ");
			}
			sql.append(fids.get(i));
		}
		sql.append(") ");
		
		List<Long> ids = jdbcTemplate.query(sql.toString(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		});
		return ids;
	}
	
	/////////////////////////////////////////////
	/// @fn searchRelationRegister
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param filterBboxDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<Long> searchRelationRegister(String dataId, String relationDataId, FilterBboxDTO filterBboxDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T1.FTR_IDN AS FTR_IDN ");
		sql.append("FROM ").append(dataId).append(" T1 ");
		sql.append("INNER JOIN RDT_RSCN_DT RSCN ON T1.FTR_CDE = RSCN.FTR_CDE AND T1.FTR_IDN = RSCN.FTR_IDN ");
		sql.append("INNER JOIN ").append(relationDataId).append(" T ON RSCN.RDL_IDN = T.RDL_IDN ");
		sql.append("WHERE ");
		sql.append(createSelectWhereBboxSql(filterBboxDTO));
		
		List<Long> ids = jdbcTemplate.query(sql.toString(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		});
		return ids;
	}
	
	/////////////////////////////////////////////
	/// @fn createSelectWhereBboxSql
	/// @brief 함수 간략한 설명 : 영역 조건문 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param filterBboxDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSelectWhereBboxSql(FilterBboxDTO filterBboxDTO) {
		StringBuffer sql = new StringBuffer();
		
		String geometryColumnName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		GeometryFactory geometryFactory = new GeometryFactory();
		Coordinate[] coordinates = new Coordinate[5];
		coordinates[0] = new Coordinate(filterBboxDTO.getXmin(), filterBboxDTO.getYmin());
		coordinates[1] = new Coordinate(filterBboxDTO.getXmax(), filterBboxDTO.getYmin());
		coordinates[2] = new Coordinate(filterBboxDTO.getXmax(), filterBboxDTO.getYmax());
		coordinates[3] = new Coordinate(filterBboxDTO.getXmin(), filterBboxDTO.getYmax());
		coordinates[4] = new Coordinate(filterBboxDTO.getXmin(), filterBboxDTO.getYmin());
		Polygon polygon = geometryFactory.createPolygon(coordinates);
		
		WKTWriter wktWriter = new WKTWriter();
		String wkt = wktWriter.write(polygon);
		sql.append(queryGeneratorService.createSelectWhereSpatialIntersectsSql(geometryColumnName, wkt, false));
		
		return sql.toString();
	}
	
}
