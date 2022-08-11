package kr.co.gitt.kworks.projects.gn.schedule;

import javax.annotation.Resource;

import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableAsync
@EnableScheduling
@Profile({"gn_oper"})
public class SeaollSchedule {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	SoapLinkUtilService soapLinkUtilService;
	
//	오후 10시
	@Scheduled(cron="0 0 22 * * *")
	public void propLink() throws Exception{
		String queryId = "4200000SOI001";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
//	오후 11시
	@Scheduled(cron="0 0 23 * * *")
	public void buidLink() throws Exception{
		String queryId = "4200000SOI002";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
//	오후 11시 30분
	@Scheduled(cron="0 30 23 * * *")
	public void loanLink() throws Exception{
		String queryId = "4200000SOI003";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
//	오전 12시 30분
	@Scheduled(cron="0 30 0 * * *")
	public void occpLink() throws Exception{
		String queryId = "4200000SOI004";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
//	오전 1시
	@Scheduled(cron="0 0 1 * * *")
	public void acexLink() throws Exception{
		String queryId = "4200000SOI005";
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
}
