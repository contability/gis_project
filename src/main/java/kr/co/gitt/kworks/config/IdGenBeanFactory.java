package kr.co.gitt.kworks.config;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.gitt.kworks.service.cmmn.TableIdGenServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import egovframework.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl;
import egovframework.rte.psl.dataaccess.util.CamelUtil;


/////////////////////////////////////////////
/// @class IdGenBeanFactory
/// kworks.config \n
///   ㄴ IdGenBeanFactory.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 3. 11. 오전 11:44:02 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 ID 생성 Bean 팩토리 입니다.
///   -# 
/////////////////////////////////////////////
public class IdGenBeanFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/////////////////////////////////////////////
	/// @fn createIdGenBean
	/// @brief 함수 간략한 설명 : KWS_TABLE_SN 테이블을 기준으로 Bean 을 생성합니다.
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param beanFactory
	/// @param dataSource
	/// @param jdbcTemplate 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void createIdGenBean(ConfigurableListableBeanFactory beanFactory, ApplicationContext applicationContext) {
		DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
		JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
		
		String sql = "SELECT SN_NM, NEXT_SN FROM KWS_TABLE_SN";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : list) {
			String snNm = (String) map.get("SN_NM");
			
			EgovIdGnrStrategyImpl egovIdGnrStrategyImpl = new EgovIdGnrStrategyImpl();
			egovIdGnrStrategyImpl.setPrefix("");
			egovIdGnrStrategyImpl.setCipers(8);
			egovIdGnrStrategyImpl.setFillChar('0');
			
			TableIdGenServiceImpl tableIdGenServiceImpl = new TableIdGenServiceImpl();
			tableIdGenServiceImpl.setApplicationContext(applicationContext);
			tableIdGenServiceImpl.setDataSource(dataSource);
			tableIdGenServiceImpl.setStrategy(egovIdGnrStrategyImpl);
			tableIdGenServiceImpl.setBlockSize(1);
			tableIdGenServiceImpl.setTable("KWS_TABLE_SN");
			tableIdGenServiceImpl.setTableName(snNm);
			try {
				tableIdGenServiceImpl.afterPropertiesSet();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
			StringBuffer beanName = new StringBuffer();
			beanName.append(CamelUtil.convert2CamelCase(snNm));
	    	beanName.append("IdGnrService");
	    	
			beanFactory.registerSingleton(beanName.toString(), tableIdGenServiceImpl);
		}
	}
		
}
