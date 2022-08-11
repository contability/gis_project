package kr.co.gitt.kworks.repository;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.spatial.QueryGeneratorService;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("corporationRepository")
public class CorporationRepository {
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;

	/// JdbcTemplate 
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Resource
	QueryGeneratorService queryGeneratorService;
	
	public List<String> searchWKT(String dataId, List<Long> ids) {
		String geometryColumnName = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(queryGeneratorService.createSelectColumnWktSql(geometryColumnName));
		sql.append("FROM ").append(dataId).append(" T ");
		sql.append("WHERE ");
		if(StringUtils.equals("RDL_CTLR_LS", dataId)) {
			sql.append("SEC_IDN ");
		}
		else {
			sql.append("FTR_IDN ");
		}
		sql.append("IN (");
		
		for(int i=0, len=ids.size(); i < len; i++) {
			if(i!=0) sql.append(", ");
			sql.append(ids.get(i));
		}
		sql.append(")");
		
		return jdbcTemplate.queryForList(sql.toString(), String.class);
	}
	
	
}
