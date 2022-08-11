package kr.co.gitt.kworks.projects.dh.contact.saeoll.web;

import javax.annotation.Resource;

import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cmmnProp/exe/")
@Profile({"dh_oper"})
public class CmmnPropLinkExecute {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	SoapLinkUtilService soapLinkUtilService;
	
	@RequestMapping(value="propExecute.do", method=RequestMethod.GET)
	public void propExecute() throws Exception{
		String queryId = "4210000SOI001";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	@RequestMapping(value="buidExecute.do", method=RequestMethod.GET)
	public void buidExecute() throws Exception{
		String queryId = "4210000SOI002";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	@RequestMapping(value="loanExecute.do", method=RequestMethod.GET)
	public void loanExecute() throws Exception{
		String queryId = "4210000SOI003";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
	
	@RequestMapping(value="occpExecute.do", method=RequestMethod.GET)
	public void occpExecute() throws Exception{
		String queryId = "4210000SOI004";
		
		logger.info(queryId + " Link Execute===================");
		
		soapLinkUtilService.soapLinkCmmnProp(queryId);
	}
}
