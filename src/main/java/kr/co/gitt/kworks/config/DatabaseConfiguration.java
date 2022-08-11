package kr.co.gitt.kworks.config;

import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.spatial.ArcsdeQueryGeneratorServiceImpl;
import kr.co.gitt.kworks.service.spatial.KairosQueryGeneratorServiceImpl;
import kr.co.gitt.kworks.service.spatial.OracleQueryGeneratorServiceImpl;
import kr.co.gitt.kworks.service.spatial.PostgresqlQueryGeneratorServiceImpl;
import kr.co.gitt.kworks.service.spatial.QueryGeneratorService;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

	/// 메세지 소스
	@Resource
    private MessageSource messageSource;
	
	@Bean
	public QueryGeneratorService queryGeneratorService() {
		String dbType = messageSource.getMessage("Globals.DbType", null, Locale.getDefault());
		QueryGeneratorService queryGeneratorService = null;
		if(StringUtils.equalsIgnoreCase("oracle", dbType)) {
			String serverType = messageSource.getMessage("Map.ServerType", null, Locale.getDefault());
			if(StringUtils.equalsIgnoreCase("arcgis", serverType)) {
				queryGeneratorService = new ArcsdeQueryGeneratorServiceImpl();
			}
			else {
				queryGeneratorService = new OracleQueryGeneratorServiceImpl();
			}
			
		}
		else if(StringUtils.equalsIgnoreCase("postgresql", dbType)) {
			queryGeneratorService = new PostgresqlQueryGeneratorServiceImpl();
		}
		else if(StringUtils.equalsIgnoreCase("kairos", dbType)) {
			queryGeneratorService = new KairosQueryGeneratorServiceImpl();
		}
		return queryGeneratorService;
	}
	
}
