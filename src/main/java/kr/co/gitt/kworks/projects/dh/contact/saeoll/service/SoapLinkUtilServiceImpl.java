package kr.co.gitt.kworks.projects.dh.contact.saeoll.service;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import kr.co.gitt.kworks.projects.dh.mappers.BmlBuidAsMapper;
import kr.co.gitt.kworks.projects.dh.mappers.BmlLoanAsMapper;
import kr.co.gitt.kworks.projects.dh.mappers.BmlOccpAsMapper;
import kr.co.gitt.kworks.projects.dh.mappers.BmlPropAsMapper;
import kr.co.gitt.kworks.projects.dh.model.BmlBuidAs;
import kr.co.gitt.kworks.projects.dh.model.BmlLoanAs;
import kr.co.gitt.kworks.projects.dh.model.BmlOccpAs;
import kr.co.gitt.kworks.projects.dh.model.BmlPropAs;
import kr.co.gitt.kworks.saeoll.model.AgrldReqstInfo;
import kr.co.gitt.kworks.saeoll.model.DfnndManageRegstr;
import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;
import kr.co.gitt.kworks.service.cmmn.ErrorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/////////////////////////////////////////////
/// @class SoapLinkUtilServiceImpl
/// kr.co.gitt.kworks.saeoll.service \n
///   ㄴ SoapLinkUtilServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 26. 오후 3:39:38 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 새올연계 서비스구현 클래스입니다.
///   -# 
/////////////////////////////////////////////
@Service("soapLinkUtilService")
@Profile({"dh_oper"})
public class SoapLinkUtilServiceImpl implements SoapLinkUtilService {
	
	//로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 에러 서비스
	@Resource
	ErrorService errorService;
	
	@Resource
	BmlPropAsMapper bmlPropAsMapper;
	
	@Resource
	BmlBuidAsMapper bmlBuidAsMapper;
	
	@Resource
	BmlLoanAsMapper bmlLoanAsMapper;
	
	@Resource
	BmlOccpAsMapper bmlOccpAsMapper;
		
	
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService#makeCndLisatStr(java.lang.String)
	/////////////////////////////////////////////
	public String makeCndLisatStr(String cndStr){
		String cndList = "";
		
		cndList += "<cndList><cnd>";
		cndList += cndStr;
		cndList += "</cnd></cndList>";
		
		return cndList;
	}
	
	/////////////////////////////////////////////
	/// @fn getRandomData
	/// @brief 함수 간략한 설명 : 랜덤 값 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String getRandomData(){
		
		String DATE_FORMAT = "yyyyMMddHHmmssSSS";
		SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT );
		Calendar cal = Calendar.getInstance();

		Random oRandom = new Random();
		long rndValue = 0;

		while(true){
			rndValue = Math.abs( oRandom.nextInt()*1000000 );
			if(String.valueOf(rndValue).length()==8)
				break;
		}

		return sdf.format( cal.getTime() )+rndValue;
	}
	
	
	/////////////////////////////////////////////
	/// @fn getDocument
	/// @brief 함수 간략한 설명 : Document로 파싱
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param inputMsg
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Document getDocument(String inputMsg) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(inputMsg)));
	}
	
	@Override
	public Integer soapLinkCmmnProp(String queryId) throws Exception {
		
		Integer pagingNumber = 1;
		Integer allCount = 0;
		
		removeCmmnProp(queryId);
		
		Integer cnt = soapConn(pagingNumber, queryId);
		
		while(cnt != 0){
			
			if(pagingNumber == 1){
				allCount += cnt;
				pagingNumber += 200;
			}
			
			cnt = soapConn(pagingNumber, queryId);
			allCount += cnt;
			pagingNumber += 200;
		}
		
		int geomUpdateRes = geomUpdateByLpPaCbnd(queryId);
		
		logger.info("\n\n" + queryId + " batchSuccess count : " + allCount);
		logger.info("\n\n" + queryId + " geomUpdate count : " + geomUpdateRes);
		
		return allCount;
	}
	
	public Integer geomUpdateByLpPaCbnd(String queryId){
		
		int cnt = 0;
		
		if(queryId.equals("4210000SOI001")){
			cnt = bmlPropAsMapper.geomUpdate();
		}else if(queryId.equals("4210000SOI002")){
			cnt = bmlBuidAsMapper.geomUpdate();
		}else if(queryId.equals("4210000SOI003")){
			cnt = bmlLoanAsMapper.geomUpdate();
		}else if(queryId.equals("4210000SOI004")){
			cnt = bmlOccpAsMapper.geomUpdate();
		}
		
		return cnt;
	}
	
	public Integer soapConn(Integer pagingNumber, String queryId) throws Exception {
		
		String IFID = "SOINN00001";
		
		//새올 서버
		String WSDL_URL = "http://106.4.1.33:3100/stmr/websvc/std/ws?wsdl=" + IFID;
		
		String SRCORGCD = "4210000";
		
		String TGTORGCD = "4210008";
		
		String RESULTCODE = "000";
		
		String msgKey = getRandomData();
		Integer cnt = 0;
		
		
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		
		SOAPPart soapPart = soapMessage.getSOAPPart();
		
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		SOAPBody soapBody = soapEnvelope.getBody();
		
		String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		msg += "<DOCUMENT>";
			msg += "<IFID>" + IFID + "</IFID>";
			msg += "<SRCORGCD>" + SRCORGCD + "</SRCORGCD>";
			msg += "<TGTORGCD>" + TGTORGCD + "</TGTORGCD>";
			msg += "<RESULTCODE>" + RESULTCODE + "</RESULTCODE>";
			msg += "<MSGKEY>" + msgKey +"</MSGKEY>";
			msg += "<DATA>";
				msg += "<message>";
					msg += "<body>";
						msg += "<query_id>" + queryId + "</query_id>";
						msg += "<dataList><data>" + pagingNumber + "</data></dataList>";
					msg += "</body>";
				msg += "</message>";
			msg += "</DATA>";
		msg += "</DOCUMENT>";
		
		String url = WSDL_URL;
		
		Document reqDoc = getDocument(msg);
		
		soapBody.addDocument(reqDoc);
		
		SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
		SOAPConnection sc = scf.createConnection();
		
		SOAPMessage resSm = sc.call(soapMessage, new URL(url));
		ByteArrayOutputStream resOut = new ByteArrayOutputStream();
		resSm.writeTo(resOut);
		String resXmlStr = new String(resOut.toByteArray());
		
		Document resDoc = getDocument(resXmlStr);
		NodeList nodeList = resDoc.getElementsByTagName("list");
		NodeList resCntList = resDoc.getElementsByTagName("res_cnt");
		
		cnt = Integer.valueOf(resCntList.item(0).getTextContent());
		int insertCount = 0;
		
		if(queryId.equals("4210000SOI001")){
			insertCount = rebuildingBmlPropAs(nodeList);
		}else if(queryId.equals("4210000SOI002")){
			insertCount = rebuildingBmlBuidAs(nodeList);
		}else if(queryId.equals("4210000SOI003")){
			insertCount = rebuildingBmlLoanAs(nodeList);
		}else if(queryId.equals("4210000SOI004")){
			insertCount = rebuildingBmlOccpAs(nodeList);
		}
		
		if(cnt==insertCount){
			logger.info("SOAP success");
		}
		
		return cnt;
	}
	
	public Integer removeCmmnProp(String queryId){
		int resultCount = 0;
		
		if(queryId.equals("4210000SOI001")){
			resultCount = bmlPropAsMapper.remove();
		}else if(queryId.equals("4210000SOI002")){
			resultCount = bmlBuidAsMapper.remove();
		}else if(queryId.equals("4210000SOI003")){
			resultCount = bmlLoanAsMapper.remove();
		}else if(queryId.equals("4210000SOI004")){
			resultCount = bmlOccpAsMapper.remove();
		}
		
		return resultCount;
	}
	
	public Integer rebuildingBmlPropAs(NodeList nodeList){
		
		int insertCount = 0;
		
		for(int i = 0; i < nodeList.getLength(); i++){
			
			BmlPropAs bmlPropAs = new BmlPropAs();
			
			Element element = (Element)nodeList.item(i);
			
			String meansNo = element.getElementsByTagName("means_no").item(0).getTextContent().trim();
			if(meansNo != null && !meansNo.equals("")){
				bmlPropAs.setPrtIdn(Long.parseLong(meansNo));
			}
			bmlPropAs.setPbpKnd("01");
			bmlPropAs.setPrtNam(element.getElementsByTagName("means_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_nm").item(0).getTextContent());
			bmlPropAs.setOwnCde(element.getElementsByTagName("own_code").item(0).getTextContent()==null?"":element.getElementsByTagName("own_code").item(0).getTextContent());
			bmlPropAs.setPrsCde(element.getElementsByTagName("means_srv_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_srv_code").item(0).getTextContent());
			bmlPropAs.setAmnCde(element.getElementsByTagName("adm_means_code").item(0).getTextContent()==null?"":element.getElementsByTagName("adm_means_code").item(0).getTextContent());
			bmlPropAs.setAccCde(element.getElementsByTagName("acct_se_code").item(0).getTextContent()==null?"":element.getElementsByTagName("acct_se_code").item(0).getTextContent());
			bmlPropAs.setManCde(element.getElementsByTagName("means_mng_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_mng_code").item(0).getTextContent());
			bmlPropAs.setChrNam(element.getElementsByTagName("cha_dep_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("cha_dep_nm").item(0).getTextContent());
			bmlPropAs.setMndCde(element.getElementsByTagName("etst_mng_code").item(0).getTextContent()==null?"":element.getElementsByTagName("etst_mng_code").item(0).getTextContent());
			bmlPropAs.setPstNum(element.getElementsByTagName("regt_copy_no_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("regt_copy_no_nm").item(0).getTextContent());
			bmlPropAs.setBjdCde(element.getElementsByTagName("regn_code").item(0).getTextContent()==null?"":element.getElementsByTagName("regn_code").item(0).getTextContent());
			bmlPropAs.setLocPlc(element.getElementsByTagName("address").item(0).getTextContent()==null?"":element.getElementsByTagName("address").item(0).getTextContent());
			bmlPropAs.setMonut(element.getElementsByTagName("san").item(0).getTextContent()==null?"":element.getElementsByTagName("san").item(0).getTextContent());
			bmlPropAs.setBun(element.getElementsByTagName("bunji").item(0).getTextContent()==null?"":element.getElementsByTagName("bunji").item(0).getTextContent());
			bmlPropAs.setHo(element.getElementsByTagName("ho").item(0).getTextContent()==null?"":element.getElementsByTagName("ho").item(0).getTextContent());
			bmlPropAs.setSpcAdr(element.getElementsByTagName("spec_addr").item(0).getTextContent()==null?"":element.getElementsByTagName("spec_addr").item(0).getTextContent());
			bmlPropAs.setSpcDong(element.getElementsByTagName("spec_dng").item(0).getTextContent()==null?"":element.getElementsByTagName("spec_dng").item(0).getTextContent());
			bmlPropAs.setSpcHo(element.getElementsByTagName("spec_ho").item(0).getTextContent()==null?"":element.getElementsByTagName("spec_ho").item(0).getTextContent());
			bmlPropAs.setRnAddr(element.getElementsByTagName("rdn_whl_addr").item(0).getTextContent()==null?"":element.getElementsByTagName("rdn_whl_addr").item(0).getTextContent());
			bmlPropAs.setPtyPc(element.getElementsByTagName("means_pc").item(0).getTextContent()==null?"":element.getElementsByTagName("means_pc").item(0).getTextContent());
			bmlPropAs.setAutAmnt(element.getElementsByTagName("acct_crit_pc").item(0).getTextContent()==null?"":element.getElementsByTagName("acct_crit_pc").item(0).getTextContent());
			bmlPropAs.setAcqDept(element.getElementsByTagName("gain_dep_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_dep_nm").item(0).getTextContent());
			bmlPropAs.setAcqPc(element.getElementsByTagName("gain_pc").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_pc").item(0).getTextContent());
			bmlPropAs.setAcqDate(element.getElementsByTagName("gain_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_ymd").item(0).getTextContent());
			bmlPropAs.setAcqCde(element.getElementsByTagName("gain_met_se_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_met_se_nm").item(0).getTextContent());
			bmlPropAs.setAcqPrv(element.getElementsByTagName("gain_why").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_why").item(0).getTextContent());
			bmlPropAs.setRstYn(element.getElementsByTagName("regt_yn").item(0).getTextContent()==null?"":element.getElementsByTagName("regt_yn").item(0).getTextContent());
			bmlPropAs.setLoanYn(element.getElementsByTagName("loan_can_yn").item(0).getTextContent()==null?"":element.getElementsByTagName("loan_can_yn").item(0).getTextContent());
			bmlPropAs.setSaleLmt(element.getElementsByTagName("sil_limit_yn_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("sil_limit_yn_nm").item(0).getTextContent());
			bmlPropAs.setRmkExp(element.getElementsByTagName("rm").item(0).getTextContent()==null?"":element.getElementsByTagName("rm").item(0).getTextContent());
			bmlPropAs.setBmlCde(element.getElementsByTagName("land_jimk_code").item(0).getTextContent()==null?"":element.getElementsByTagName("land_jimk_code").item(0).getTextContent());
			bmlPropAs.setMokCde(element.getElementsByTagName("real_jimk_code").item(0).getTextContent()==null?"":element.getElementsByTagName("real_jimk_code").item(0).getTextContent());
			String ar = element.getElementsByTagName("ar").item(0).getTextContent().trim();
			if(ar != null && !ar.equals("")){
				bmlPropAs.setArea(Double.parseDouble(ar));
			}
			String realAr = element.getElementsByTagName("real_ar").item(0).getTextContent().trim();
			if(realAr != null && !realAr.equals("")){
				bmlPropAs.setParea(Double.parseDouble(realAr));
			}
			String gsjg = element.getElementsByTagName("gsjg").item(0).getTextContent().trim();
			gsjg = gsjg.trim();
			if(gsjg != null & !gsjg.equals("")){
				bmlPropAs.setOlnlp(Double.parseDouble(gsjg));
			}
			String gainAr = element.getElementsByTagName("gain_ar").item(0).getTextContent().trim();
			if(gainAr != null && !gainAr.equals("")){
				bmlPropAs.setAcqAra(Double.parseDouble(gainAr));
			}
			bmlPropAs.setCnrQta(element.getElementsByTagName("sha_quota1").item(0).getTextContent()==null?"":element.getElementsByTagName("sha_quota1").item(0).getTextContent());
			bmlPropAs.setSpfc(element.getElementsByTagName("srv_regn").item(0).getTextContent()==null?"":element.getElementsByTagName("srv_regn").item(0).getTextContent());
			bmlPropAs.setCtyPlan(element.getElementsByTagName("city_pln_zone").item(0).getTextContent()==null?"":element.getElementsByTagName("city_pln_zone").item(0).getTextContent());
			bmlPropAs.setPlanFty(element.getElementsByTagName("pln_facil").item(0).getTextContent()==null?"":element.getElementsByTagName("pln_facil").item(0).getTextContent());
			bmlPropAs.setDwk(element.getElementsByTagName("develop_bsns").item(0).getTextContent()==null?"":element.getElementsByTagName("develop_bsns").item(0).getTextContent());
			bmlPropAs.setPlanBns(element.getElementsByTagName("pln_bsns").item(0).getTextContent()==null?"":element.getElementsByTagName("pln_bsns").item(0).getTextContent());
			bmlPropAs.setOwnNm(element.getElementsByTagName("own_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("own_code_nm").item(0).getTextContent());
			bmlPropAs.setMesrvNm(element.getElementsByTagName("means_srv_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_srv_code_nm").item(0).getTextContent());
			bmlPropAs.setAmnNm(element.getElementsByTagName("adm_means_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("adm_means_code_nm").item(0).getTextContent());
			bmlPropAs.setAccNm(element.getElementsByTagName("acct_se_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("acct_se_code_nm").item(0).getTextContent());
			bmlPropAs.setManNm(element.getElementsByTagName("means_mng_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_mng_code_nm").item(0).getTextContent());
			bmlPropAs.setMndNm(element.getElementsByTagName("etst_mng_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("etst_mng_code_nm").item(0).getTextContent());
			bmlPropAs.setBjdNm(element.getElementsByTagName("regn_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("regn_code_nm").item(0).getTextContent());
			bmlPropAs.setTong(element.getElementsByTagName("tong").item(0).getTextContent()==null?"":element.getElementsByTagName("tong").item(0).getTextContent());
			bmlPropAs.setBan(element.getElementsByTagName("ban").item(0).getTextContent()==null?"":element.getElementsByTagName("ban").item(0).getTextContent());
			bmlPropAs.setJimokNm(element.getElementsByTagName("land_jimk_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("land_jimk_code_nm").item(0).getTextContent());
			bmlPropAs.setRjimokNm(element.getElementsByTagName("real_jimk_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("real_jimk_code_nm").item(0).getTextContent());
			String shaQuota2 = element.getElementsByTagName("sha_quota2").item(0).getTextContent().trim();
			if(shaQuota2 != null && !shaQuota2.equals("")){
				bmlPropAs.setCnrQta2(Double.parseDouble(shaQuota2));
			}
			bmlPropAs.setDispoYmd(element.getElementsByTagName("dispo_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("dispo_ymd").item(0).getTextContent());
			bmlPropAs.setDispoMet(element.getElementsByTagName("dispo_met").item(0).getTextContent()==null?"":element.getElementsByTagName("dispo_met").item(0).getTextContent());
			bmlPropAs.setDispoCode(element.getElementsByTagName("dispo_met_code").item(0).getTextContent()==null?"":element.getElementsByTagName("dispo_met_code").item(0).getTextContent());
			bmlPropAs.setDispoWhy(element.getElementsByTagName("dispo_why").item(0).getTextContent()==null?"":element.getElementsByTagName("dispo_why").item(0).getTextContent());
			bmlPropAs.setGainDep(element.getElementsByTagName("gain_dep").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_dep").item(0).getTextContent());
			bmlPropAs.setGiseCde(element.getElementsByTagName("gain_met_se").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_met_se").item(0).getTextContent());
			bmlPropAs.setSeaollYn(element.getElementsByTagName("gain_accpt_yn").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_accpt_yn").item(0).getTextContent());
			bmlPropAs.setInsysNm(element.getElementsByTagName("in_sys_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("in_sys_nm").item(0).getTextContent());
			
			insertCount += bmlPropAsMapper.insert(bmlPropAs);
		}
		
		return insertCount;
	}
	
	public Integer rebuildingBmlBuidAs(NodeList nodeList){
		int insertCount = 0;
		
		for(int i = 0; i < nodeList.getLength(); i++){
			BmlBuidAs bmlBuidAs = new BmlBuidAs();
			
			Element element = (Element)nodeList.item(i);
			
			String meansNo = element.getElementsByTagName("means_no").item(0).getTextContent().trim();
			if(meansNo != null && !meansNo.equals("")){
				bmlBuidAs.setPrtIdn(Long.parseLong(meansNo));
			}
			bmlBuidAs.setPbpKnd("02");
			bmlBuidAs.setPrtNam(element.getElementsByTagName("means_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_nm").item(0).getTextContent());
			bmlBuidAs.setOwnCde(element.getElementsByTagName("own_code").item(0).getTextContent()==null?"":element.getElementsByTagName("own_code").item(0).getTextContent());
			bmlBuidAs.setPrsCde(element.getElementsByTagName("means_srv_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_srv_code").item(0).getTextContent());
			bmlBuidAs.setAmnCde(element.getElementsByTagName("adm_means_code").item(0).getTextContent()==null?"":element.getElementsByTagName("adm_means_code").item(0).getTextContent());
			bmlBuidAs.setAccCde(element.getElementsByTagName("acct_se_code").item(0).getTextContent()==null?"":element.getElementsByTagName("acct_se_code").item(0).getTextContent());
			bmlBuidAs.setManCde(element.getElementsByTagName("means_mng_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_mng_code").item(0).getTextContent());
			bmlBuidAs.setChrNam(element.getElementsByTagName("cha_dep_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("cha_dep_nm").item(0).getTextContent());
			bmlBuidAs.setMndCde(element.getElementsByTagName("etst_mng_code").item(0).getTextContent()==null?"":element.getElementsByTagName("etst_mng_code").item(0).getTextContent());
			bmlBuidAs.setPstNum(element.getElementsByTagName("regt_copy_no").item(0).getTextContent()==null?"":element.getElementsByTagName("regt_copy_no").item(0).getTextContent());
			bmlBuidAs.setBjdCde(element.getElementsByTagName("regn_code").item(0).getTextContent()==null?"":element.getElementsByTagName("regn_code").item(0).getTextContent());
			bmlBuidAs.setLocPlc(element.getElementsByTagName("address").item(0).getTextContent()==null?"":element.getElementsByTagName("address").item(0).getTextContent());
			bmlBuidAs.setMonut(element.getElementsByTagName("san").item(0).getTextContent()==null?"":element.getElementsByTagName("san").item(0).getTextContent());
			bmlBuidAs.setBun(element.getElementsByTagName("bunji").item(0).getTextContent()==null?"":element.getElementsByTagName("bunji").item(0).getTextContent());
			bmlBuidAs.setHo(element.getElementsByTagName("ho").item(0).getTextContent()==null?"":element.getElementsByTagName("ho").item(0).getTextContent());
			bmlBuidAs.setSpcAdr(element.getElementsByTagName("spec_addr").item(0).getTextContent()==null?"":element.getElementsByTagName("spec_addr").item(0).getTextContent());
			bmlBuidAs.setSpcDong(element.getElementsByTagName("spec_dng").item(0).getTextContent()==null?"":element.getElementsByTagName("spec_dng").item(0).getTextContent());
			bmlBuidAs.setSpcHo(element.getElementsByTagName("spec_ho").item(0).getTextContent()==null?"":element.getElementsByTagName("spec_ho").item(0).getTextContent());
			bmlBuidAs.setRnAddr(element.getElementsByTagName("rdn_whl_addr").item(0).getTextContent()==null?"":element.getElementsByTagName("rdn_whl_addr").item(0).getTextContent());
			bmlBuidAs.setPtyPc(element.getElementsByTagName("means_pc").item(0).getTextContent()==null?"":element.getElementsByTagName("means_pc").item(0).getTextContent());
			bmlBuidAs.setAutAmnt(element.getElementsByTagName("acct_crit_pc").item(0).getTextContent()==null?"":element.getElementsByTagName("acct_crit_pc").item(0).getTextContent());
			bmlBuidAs.setAcqDept(element.getElementsByTagName("gain_dep_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_dep_nm").item(0).getTextContent());
			bmlBuidAs.setAcqPc(element.getElementsByTagName("gain_pc").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_pc").item(0).getTextContent());
			bmlBuidAs.setAcqDate(element.getElementsByTagName("gain_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_ymd").item(0).getTextContent());
			bmlBuidAs.setAcqCde(element.getElementsByTagName("gain_met_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_met_nm").item(0).getTextContent());
			bmlBuidAs.setAcqPrv(element.getElementsByTagName("gain_why").item(0).getTextContent()==null?"":element.getElementsByTagName("gain_why").item(0).getTextContent());
			bmlBuidAs.setRstYn(element.getElementsByTagName("regt_yn").item(0).getTextContent()==null?"":element.getElementsByTagName("regt_yn").item(0).getTextContent());
			bmlBuidAs.setLoanYn(element.getElementsByTagName("loan_can_yn").item(0).getTextContent()==null?"":element.getElementsByTagName("loan_can_yn").item(0).getTextContent());
			bmlBuidAs.setSaleLmt(element.getElementsByTagName("sil_limit_yn").item(0).getTextContent()==null?"":element.getElementsByTagName("sil_limit_yn").item(0).getTextContent());
			bmlBuidAs.setRmkExp(element.getElementsByTagName("rm").item(0).getTextContent()==null?"":element.getElementsByTagName("rm").item(0).getTextContent());
			bmlBuidAs.setSrcCde(element.getElementsByTagName("mstc_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("mstc_nm").item(0).getTextContent());
			bmlBuidAs.setRfCde(element.getElementsByTagName("rof_stc_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("rof_stc_nm").item(0).getTextContent());
			String jisgLayerCnt = element.getElementsByTagName("jisg_layer_cnt").item(0).getTextContent().trim();
			if(jisgLayerCnt != null && !jisgLayerCnt.equals("")){
				bmlBuidAs.setGrdCde(Long.parseLong(jisgLayerCnt));
			}
			String underLayerCnt = element.getElementsByTagName("under_layer_cnt").item(0).getTextContent().trim();
			if(underLayerCnt != null && !underLayerCnt.equals("")){
				bmlBuidAs.setUgrdCde(Long.parseLong(underLayerCnt));
			}
			String cnstAr = element.getElementsByTagName("cnst_ar").item(0).getTextContent().trim();
			if(cnstAr != null && !cnstAr.equals("")){
				bmlBuidAs.setBldArea(Double.parseDouble(cnstAr));
			}
			bmlBuidAs.setBldDate(element.getElementsByTagName("cnst_d").item(0).getTextContent()==null?"":element.getElementsByTagName("cnst_d").item(0).getTextContent());
			String cnstPc = element.getElementsByTagName("cnst_pc").item(0).getTextContent().trim();
			if(cnstPc != null && !cnstPc.equals("")){
				bmlBuidAs.setBldPri(Long.parseLong(cnstPc));
			}
			bmlBuidAs.setBldSe(element.getElementsByTagName("bdng_se_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("bdng_se_nm").item(0).getTextContent());
			bmlBuidAs.setBldPrp(element.getElementsByTagName("bdng_srv_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("bdng_srv_nm").item(0).getTextContent());
			String yArea = element.getElementsByTagName("yarea").item(0).getTextContent().trim();
			if(yArea != null && !yArea.equals("")){
				bmlBuidAs.setGrsArea(Double.parseDouble(yArea));
			}
			String gainAr = element.getElementsByTagName("gain_ar").item(0).getTextContent().trim();
			if(gainAr != null && !gainAr.equals("")){
				bmlBuidAs.setAcsArea(Double.parseDouble(gainAr));
			}
			bmlBuidAs.setMesrvNm(element.getElementsByTagName("means_srv_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_srv_code_nm").item(0).getTextContent());
			bmlBuidAs.setAmnNm(element.getElementsByTagName("adm_means_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("adm_means_code_nm").item(0).getTextContent());
			bmlBuidAs.setAccNm(element.getElementsByTagName("acct_se_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("acct_se_code_nm").item(0).getTextContent());
			bmlBuidAs.setManNm(element.getElementsByTagName("means_mng_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_mng_code_nm").item(0).getTextContent());
			bmlBuidAs.setMndNm(element.getElementsByTagName("etst_mng_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("etst_mng_code_nm").item(0).getTextContent());
			bmlBuidAs.setOwnNm(element.getElementsByTagName("own_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("own_code_nm").item(0).getTextContent());
			bmlBuidAs.setBjdNm(element.getElementsByTagName("regn_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("regn_code_nm").item(0).getTextContent());
			bmlBuidAs.setTong(element.getElementsByTagName("tong").item(0).getTextContent()==null?"":element.getElementsByTagName("tong").item(0).getTextContent());
			bmlBuidAs.setBan(element.getElementsByTagName("ban").item(0).getTextContent()==null?"":element.getElementsByTagName("ban").item(0).getTextContent());
			bmlBuidAs.setDispoYmd(element.getElementsByTagName("dispo_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("dispo_ymd").item(0).getTextContent());
			bmlBuidAs.setDispoMet(element.getElementsByTagName("dispo_met_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("dispo_met_nm").item(0).getTextContent());
			bmlBuidAs.setMeansSe(element.getElementsByTagName("means_se").item(0).getTextContent()==null?"":element.getElementsByTagName("means_se").item(0).getTextContent());
			
			insertCount += bmlBuidAsMapper.insert(bmlBuidAs);
			
		}
		
		return insertCount;
	}
	
	public Integer rebuildingBmlLoanAs(NodeList nodeList){
		
		int insertCount = 0;
		
		for(int i = 0; i < nodeList.getLength(); i++){
			BmlLoanAs bmlLoanAs = new BmlLoanAs();
			
			Element element = (Element) nodeList.item(i);
			
			String meansNo = element.getElementsByTagName("means_no").item(0).getTextContent().trim();
			if(meansNo != null && !meansNo.equals("")){
				bmlLoanAs.setPrtIdn(Long.parseLong(meansNo));
			}
			String loanNo = element.getElementsByTagName("loan_use_perm_sno").item(0).getTextContent().trim();
			if(loanNo != null && !loanNo.equals("")){
				bmlLoanAs.setLonIdn(Long.parseLong(loanNo));
			}
			bmlLoanAs.setPbpKnd(element.getElementsByTagName("means_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_code").item(0).getTextContent());
			bmlLoanAs.setThgAdr(element.getElementsByTagName("address").item(0).getTextContent()==null?"":element.getElementsByTagName("address").item(0).getTextContent());
			bmlLoanAs.setCrtDate(element.getElementsByTagName("ctr_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("ctr_ymd").item(0).getTextContent());
			bmlLoanAs.setTntDate(element.getElementsByTagName("dctl_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("dctl_ymd").item(0).getTextContent());
			bmlLoanAs.setTntResn(element.getElementsByTagName("dctl_why").item(0).getTextContent()==null?"":element.getElementsByTagName("dctl_why").item(0).getTextContent());
			bmlLoanAs.setCrtStr(element.getElementsByTagName("ctr_st_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("ctr_st_ymd").item(0).getTextContent());
			bmlLoanAs.setCrtEnd(element.getElementsByTagName("ctr_end_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("ctr_end_ymd").item(0).getTextContent());
			String loanAr1 = element.getElementsByTagName("loan_perm_ar1").item(0).getTextContent().trim();
			if(loanAr1 != null && !loanAr1.equals("")){
				bmlLoanAs.setLonArea(Double.parseDouble(loanAr1));
			}
			String loanAr2 = element.getElementsByTagName("loan_perm_ar2").item(0).getTextContent().trim();
			if(loanAr2 != null && !loanAr2.equals("")){
				bmlLoanAs.setSlonArea(Double.parseDouble(loanAr2));
			}
			bmlLoanAs.setLonPup(element.getElementsByTagName("loan_perm_obj_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("loan_perm_obj_code_nm").item(0).getTextContent());
			bmlLoanAs.setEmpNam(element.getElementsByTagName("usr_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_nm").item(0).getTextContent());
			bmlLoanAs.setPbpKndnm(element.getElementsByTagName("means_code_cn").item(0).getTextContent()==null?"":element.getElementsByTagName("means_code_cn").item(0).getTextContent());
			bmlLoanAs.setOwnCde(element.getElementsByTagName("own_code").item(0).getTextContent()==null?"":element.getElementsByTagName("own_code").item(0).getTextContent());
			bmlLoanAs.setOwnNm(element.getElementsByTagName("own_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("own_code_nm").item(0).getTextContent());
			bmlLoanAs.setManCde(element.getElementsByTagName("means_mng_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_mng_code").item(0).getTextContent());
			bmlLoanAs.setManNm(element.getElementsByTagName("means_mng_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_mng_nm").item(0).getTextContent());
			bmlLoanAs.setPrsCde(element.getElementsByTagName("means_srv_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_srv_code").item(0).getTextContent());
			bmlLoanAs.setMesrvNm(element.getElementsByTagName("means_srv_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_srv_code_nm").item(0).getTextContent());
			bmlLoanAs.setAmnCde(element.getElementsByTagName("adm_means_code").item(0).getTextContent()==null?"":element.getElementsByTagName("adm_means_code").item(0).getTextContent());
			bmlLoanAs.setAmnNm(element.getElementsByTagName("adm_means_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("adm_means_code_nm").item(0).getTextContent());
			bmlLoanAs.setAccCde(element.getElementsByTagName("means_acct").item(0).getTextContent()==null?"":element.getElementsByTagName("means_acct").item(0).getTextContent());
			bmlLoanAs.setAccNm(element.getElementsByTagName("means_acct_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_acct_nm").item(0).getTextContent());
			bmlLoanAs.setBjdCde(element.getElementsByTagName("regn_code").item(0).getTextContent()==null?"":element.getElementsByTagName("regn_code").item(0).getTextContent());
			bmlLoanAs.setBjdNm(element.getElementsByTagName("regn_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("regn_code_nm").item(0).getTextContent());
			bmlLoanAs.setMonut(element.getElementsByTagName("san").item(0).getTextContent()==null?"":element.getElementsByTagName("san").item(0).getTextContent());
			bmlLoanAs.setBun(element.getElementsByTagName("bunji").item(0).getTextContent()==null?"":element.getElementsByTagName("bunji").item(0).getTextContent());
			bmlLoanAs.setHo(element.getElementsByTagName("ho").item(0).getTextContent()==null?"":element.getElementsByTagName("ho").item(0).getTextContent());
			String perAmt = element.getElementsByTagName("per_amt").item(0).getTextContent().trim();
			if(perAmt != null && !perAmt.equals("")){
				bmlLoanAs.setPerAmt(Long.parseLong(perAmt));
			}
			bmlLoanAs.setLonStart(element.getElementsByTagName("loan_perm_gigan_st_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("loan_perm_gigan_st_ymd").item(0).getTextContent());
			bmlLoanAs.setLonEnd(element.getElementsByTagName("loan_perm_gigan_end_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("loan_perm_gigan_end_ymd").item(0).getTextContent());
			String loanDayCnt = element.getElementsByTagName("loan_perm_daycnt").item(0).getTextContent().trim();
			if(loanDayCnt != null && !loanDayCnt.equals("")){
				bmlLoanAs.setLonDay(Long.parseLong(loanDayCnt));
			}
			String bdngUseAr = element.getElementsByTagName("bdng_loan_use_ar").item(0).getTextContent().trim();
			if(bdngUseAr != null && !bdngUseAr.equals("")){
				bmlLoanAs.setBlLonAr(Double.parseDouble(bdngUseAr));
			}
			String bdngBdAr = element.getElementsByTagName("bdng_bd_ar").item(0).getTextContent().trim();
			if(bdngBdAr != null && !bdngBdAr.equals("")){
				bmlLoanAs.setBlBdAr(Double.parseDouble(bdngBdAr));
			}
			bmlLoanAs.setUseFlr(element.getElementsByTagName("use_flr").item(0).getTextContent()==null?"":element.getElementsByTagName("use_flr").item(0).getTextContent());
			bmlLoanAs.setEmpSe(element.getElementsByTagName("usr_se").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_se").item(0).getTextContent());
			bmlLoanAs.setUsrSeNm(element.getElementsByTagName("usr_se_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_se_nm").item(0).getTextContent());
			bmlLoanAs.setUsrbjdCd(element.getElementsByTagName("usr_regn_code").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_regn_code").item(0).getTextContent());
			bmlLoanAs.setUsrbjdNm(element.getElementsByTagName("usr_regn_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_regn_code_nm").item(0).getTextContent());
			bmlLoanAs.setUsrSan(element.getElementsByTagName("usr_san").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_san").item(0).getTextContent());
			bmlLoanAs.setUsrBunji(element.getElementsByTagName("usr_bunji").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_bunji").item(0).getTextContent());
			bmlLoanAs.setUsrHo(element.getElementsByTagName("usr_ho").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_ho").item(0).getTextContent());
			bmlLoanAs.setEmpBjd(element.getElementsByTagName("usr_address").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_address").item(0).getTextContent());
			bmlLoanAs.setEmpRn(element.getElementsByTagName("usr_rdn_whl_addr").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_rdn_whl_addr").item(0).getTextContent());
			bmlLoanAs.setEmpNum(element.getElementsByTagName("usr_cntct").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_cntct").item(0).getTextContent());
			
			insertCount += bmlLoanAsMapper.insert(bmlLoanAs);
		}
		
		return insertCount;
	}
	
	public Integer rebuildingBmlOccpAs(NodeList nodeList){
		
		int insertCount = 0;
		
		for(int i = 0; i < nodeList.getLength(); i++){
			
			Element element = (Element)nodeList.item(i);
			
			BmlOccpAs bmlOccpAs = new BmlOccpAs();
			
			String meansNo = element.getElementsByTagName("means_no").item(0).getTextContent().trim();
			if(meansNo != null && !meansNo.equals("")){
				bmlOccpAs.setPrtIdn(Long.parseLong(meansNo));
			}
			String wopNo = element.getElementsByTagName("wop_poss_use_sno").item(0).getTextContent().trim();
			if(wopNo != null && !wopNo.equals("")){
				bmlOccpAs.setOcpIdn(Long.parseLong(wopNo));
			}
			bmlOccpAs.setPbpKnd(element.getElementsByTagName("means_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_code").item(0).getTextContent());
			bmlOccpAs.setOcpAdr(element.getElementsByTagName("address").item(0).getTextContent()==null?"":element.getElementsByTagName("address").item(0).getTextContent());
			bmlOccpAs.setOcpStr(element.getElementsByTagName("poss_st_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("poss_st_ymd").item(0).getTextContent());
			bmlOccpAs.setOcpEnd(element.getElementsByTagName("poss_end_ymd").item(0).getTextContent()==null?"":element.getElementsByTagName("poss_end_ymd").item(0).getTextContent());
			String possAr1 = element.getElementsByTagName("poss_ar1").item(0).getTextContent().trim();
			if(possAr1 != null && !possAr1.equals("")){
				bmlOccpAs.setOcpAra(Double.parseDouble(possAr1));
			}
			String sidoQuota = element.getElementsByTagName("sido_quota").item(0).getTextContent().trim();
			if(sidoQuota != null && !sidoQuota.equals("")){
				bmlOccpAs.setRvrSi(Long.parseLong(sidoQuota));
			}
			String cggQuota = element.getElementsByTagName("cgg_quota").item(0).getTextContent().trim();
			if(cggQuota != null && !cggQuota.equals("")){
				bmlOccpAs.setRvrGu(Long.parseLong(cggQuota));
			}
			bmlOccpAs.setOcpPrs(element.getElementsByTagName("poss_obj_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("poss_obj_code_nm").item(0).getTextContent());
			bmlOccpAs.setRmkExp(element.getElementsByTagName("rm").item(0).getTextContent()==null?"":element.getElementsByTagName("rm").item(0).getTextContent());
			bmlOccpAs.setEmpSe(element.getElementsByTagName("usr_se").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_se").item(0).getTextContent());
			bmlOccpAs.setEmpNam(element.getElementsByTagName("usr_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_nm").item(0).getTextContent());
			bmlOccpAs.setEmpNum(element.getElementsByTagName("usr_cntct").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_cntct").item(0).getTextContent());
			bmlOccpAs.setEmpBjd(element.getElementsByTagName("usr_address").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_address").item(0).getTextContent());
			bmlOccpAs.setEmpRn(element.getElementsByTagName("usr_rdn_whl_addr").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_rdn_whl_addr").item(0).getTextContent());
			bmlOccpAs.setMeansNm(element.getElementsByTagName("means_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_nm").item(0).getTextContent());
			bmlOccpAs.setRegnCode(element.getElementsByTagName("regn_code").item(0).getTextContent()==null?"":element.getElementsByTagName("regn_code").item(0).getTextContent());
			bmlOccpAs.setRegnNm(element.getElementsByTagName("regn_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("regn_code_nm").item(0).getTextContent());
			bmlOccpAs.setSan(element.getElementsByTagName("san").item(0).getTextContent()==null?"":element.getElementsByTagName("san").item(0).getTextContent());
			bmlOccpAs.setBunji(element.getElementsByTagName("bunji").item(0).getTextContent()==null?"":element.getElementsByTagName("bunji").item(0).getTextContent());
			bmlOccpAs.setHo(element.getElementsByTagName("ho").item(0).getTextContent()==null?"":element.getElementsByTagName("ho").item(0).getTextContent());
			bmlOccpAs.setTong(element.getElementsByTagName("tong").item(0).getTextContent()==null?"":element.getElementsByTagName("tong").item(0).getTextContent());
			bmlOccpAs.setBan(element.getElementsByTagName("ban").item(0).getTextContent()==null?"":element.getElementsByTagName("ban").item(0).getTextContent());
			bmlOccpAs.setPobjCode(element.getElementsByTagName("poss_obj_code").item(0).getTextContent()==null?"":element.getElementsByTagName("poss_obj_code").item(0).getTextContent());
			bmlOccpAs.setManCde(element.getElementsByTagName("means_mng_code").item(0).getTextContent()==null?"":element.getElementsByTagName("means_mng_code").item(0).getTextContent());
			bmlOccpAs.setManNm(element.getElementsByTagName("means_mng_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_mng_code_nm").item(0).getTextContent());
			bmlOccpAs.setUsrSeNm(element.getElementsByTagName("usr_se_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_se_nm").item(0).getTextContent());
			bmlOccpAs.setRnAddr(element.getElementsByTagName("rdn_whl_addr").item(0).getTextContent()==null?"":element.getElementsByTagName("rdn_whl_addr").item(0).getTextContent());
			bmlOccpAs.setPbpKndnm(element.getElementsByTagName("means_code_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("means_code_nm").item(0).getTextContent());
			bmlOccpAs.setUsrbjdCd(element.getElementsByTagName("usr_regn_code").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_regn_code").item(0).getTextContent());
			bmlOccpAs.setUsrbjdNm(element.getElementsByTagName("usr_regn_nm").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_regn_nm").item(0).getTextContent());
			bmlOccpAs.setUsrSan(element.getElementsByTagName("usr_san").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_san").item(0).getTextContent());
			bmlOccpAs.setUsrBunji(element.getElementsByTagName("usr_bunji").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_bunji").item(0).getTextContent());
			bmlOccpAs.setUsrHo(element.getElementsByTagName("usr_ho").item(0).getTextContent()==null?"":element.getElementsByTagName("usr_ho").item(0).getTextContent());
			
			
			insertCount += bmlOccpAsMapper.insert(bmlOccpAs);
		}
		
		return insertCount;
	}
	


	@Override
	public Integer soapLink(String ymdStr, String cntFirst, String cntEnd,
			String cndStr) throws Exception {
		return null;
	}

	@Override
	public ArrayList<AgrldReqstInfo> soapLinkAgrldReqstInfo(String ymdStr,
			String cntFirst, String cndStr) throws Exception {
		return null;
	}

	@Override
	public ArrayList<DfnndManageRegstr> soapLinkDfnndManageRegstr(
			String ymdStr, String cntFirst, String cndStr) throws Exception {
		return null;
	}
	
	public String getTodayStr(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String todayStr = format.format(date);
		
		return todayStr;
	}

	
}
