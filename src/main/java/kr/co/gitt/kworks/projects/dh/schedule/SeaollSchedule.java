package kr.co.gitt.kworks.projects.dh.schedule;


import javax.annotation.Resource;

import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/////////////////////////////////////////////
/// @class SeaollSchedule
/// kr.co.gitt.kworks.schedule \n
///   ㄴ SeaollSchedule.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 26. 오후 9:11:25 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 새올 스케쥴러 입니다.
///   -# 
/////////////////////////////////////////////
@Configuration
@EnableAsync
@EnableScheduling
@Profile({"dh_oper"})
public class SeaollSchedule {
	
	// 로그
	Logger logger = LoggerFactory.getLogger(getClass());

	//새올연계
	@Resource
	SoapLinkUtilService soapLinkUtilService;
	
	//토지현황정보
	@Scheduled(cron="0 0 22 * * *")
	public void propLink() throws Exception{
		
		String queryId = "4210000SOI001";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	//건물현황정보
	@Scheduled(cron="0 30 22 * * *")
	public void BuidLink() throws Exception{
		
		String queryId = "4210000SOI002";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	//대부현황정보
	@Scheduled(cron="0 0 23 * * *")
	public void loanLink() throws Exception{
		String queryId = "4210000SOI003";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	//무단점유현황정보
	@Scheduled(cron="0 30 23 * * *")
	public void occpLink() throws Exception{
		
		String queryId = "4210000SOI004";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
}
