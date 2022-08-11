package kr.co.gitt.kworks.projects.gn.contact.saeoll.web;

import javax.annotation.Resource;

import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 20220321
// 정신형
// 강릉시 공유재산 새올 연계 강제 실행 controller입니다.
// 로직은 schedule.SeaollSchedule.java 와 동일합니다.
@Controller
@RequestMapping("/cmmnProp/exe/")
@Profile({"gn_oper"})
public class CmmnPropLinkExecute {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	SoapLinkUtilService soapLinkUtilService;
	
	@RequestMapping(value="propExecute.do", method=RequestMethod.GET)
	public void propExecute() throws Exception{
		String queryId = "4200000SOI001";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	@RequestMapping(value="buidExecute.do", method=RequestMethod.GET)
	public void buidExecute() throws Exception{
		String queryId = "4200000SOI002";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	@RequestMapping(value="loanExecute.do", method=RequestMethod.GET)
	public void loanExecute() throws Exception{
		String queryId = "4200000SOI003";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	@RequestMapping(value="occpExecute.do", method=RequestMethod.GET)
	public void occpExecute() throws Exception{
		String queryId = "4200000SOI004";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	@RequestMapping(value="acexExecute.do", method=RequestMethod.GET)
	public void acexExecute() throws Exception{
		String queryId = "4200000SOI005";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
}
