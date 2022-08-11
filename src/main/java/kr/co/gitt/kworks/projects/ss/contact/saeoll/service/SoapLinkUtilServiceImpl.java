package kr.co.gitt.kworks.projects.ss.contact.saeoll.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import kr.co.gitt.kworks.projects.ss.mappers.BmlWellPsMapper;
import kr.co.gitt.kworks.projects.ss.model.BmlWellPs;
import kr.co.gitt.kworks.saeoll.model.AgrldReqstInfo;
import kr.co.gitt.kworks.saeoll.model.DfnndManageRegstr;
import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;
import kr.co.gitt.kworks.service.cmmn.ErrorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.gpki.gpkiapi.GpkiApi;
import com.gpki.gpkiapi.cert.X509Certificate;
import com.gpki.gpkiapi.cms.EnvelopedData;
import com.gpki.gpkiapi.exception.GpkiApiException;
import com.gpki.gpkiapi.storage.Disk;
import com.gpki.gpkiapi.util.Base64;
import com.gpki.gpkiapi.util.Ldap;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
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
@Profile({"ss_oper"})
public class SoapLinkUtilServiceImpl implements SoapLinkUtilService {
	
	//로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 에러 서비스
	@Resource
	ErrorService errorService;
	
	//지하수 관정
	@Resource
	BmlWellPsMapper bmlWellPsMapper;
	
	/// 지하수관정 ID 생성 서비스
	@Resource
	EgovIdGnrService bmlWellPsIdGnrService;
	
	//
	@Resource
	private MessageSource messageSource;
		
	//wsdl URL (새올 서버)
	public String WSDL_URL = "http://108.5.2.80:3100/stmr/websvc/std/ws?wsdl=SOINN00001";
	
	//서산 서버 인증서 패스 (마지막에 / 꼭 넣어야함)
	public String SERVER_CER_PATH = "C:/GPKI/Certificate/class1/";
	
	//서산 서버 인증서 파일명
	public String SERVER_CER_FILE_NM = "SVR4530234002_env.cer";
	public String SERVER_KEY_FILE_NM = "SVR4530234002_env.key";
	///////////////////////////////////////////////////////////////////////////////////
	//인증서 암호 (특수문자 포함 9자리 이상 )
	public String SERVER_KEY_PASS_CODE = "rhdrkswjdqh*3182";
	///////////////////////////////////////////////////////////////////////////////////
	
	// 폴더 구조만 만들어 져 있으면 됨(마지막에 / 꼭 넣어야함)
	public String SEAOL_CER_PATH = "C:/GPKI/LDAP/";
	// 새올 인증서 명 (인증서명만 필요함 확장자는 필요없음)
	public String SEAOL_CER_FILE_NM = "SVR4530000003";
	
	// gpkiapi.lic 파일 위치(마지막에 / 꼭 넣어야함)
	public String GPKI_LIC_PATH = "C:/GPKI/gpkiAPI/conf/";
	
	//시군구 코드
	public String SROCRGCD = "4530004";
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService#soapLink(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	public Integer soapLink(String ymdStr,String cntFirst, String cntEnd, String cndStr) throws Exception{
		
		//연계id
		String ifid			= "SOINN00001";
		//요청 기관코드
		String srcorgcd		= SROCRGCD;
		//수신 기관코드
		String tgtorgcd		= SROCRGCD;
		//송수신 코드 (요청 000, 수신성공 200, 그외 코드는 에러)
		String resultCode	= "000";
		//연계 메시지 구분을 위한 식별자 코드 YYYYMMddHHmmssSSS + 랜덤값 8자리 (총25자리)
		String msgkey		= getRandomData();
		//서비스 id 시설관리현황 (4530000SOI2210), 농지전용신청정보 (4530000SOI2220), 산지전용관리대장 (4530000SOI2230)
		String queryId		= "4530000SOI2210";
		
		//cndList
		String cndList		= "";
		
		//조회된 결과값
		Integer cnt			= 0;
		
		// LDAP 에서 새올 인증서를 받아옵니다.
		getLdap();
		
		//세부조건문 
		if(!cndStr.equals("")){
			cndList += cndStr;
		}
		
		//요청 message 생성
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope(); 
		soapEnvelope.addNamespaceDeclaration("xmlns", "http://schema.xmlsoap.org/soap/envelope/");
		SOAPBody soapBody = soapEnvelope.getBody();
	
		String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DOCUMENT xmlns=\"\">";
		msg += "<IFID>" + ifid + "</IFID>";
		msg += "<SRCORGCD>" + srcorgcd + "</SRCORGCD>";
		msg += "<TGTORGCD>" + tgtorgcd + "</TGTORGCD>";
		msg += "<RESULTCODE>" + resultCode + "</RESULTCODE>";
		
		msg += "<MSGKEY>" + msgkey + "</MSGKEY>";
		
		msg += "<DATA>";
		
		String messagePartStr = "";
		messagePartStr += "<message>";
		
		messagePartStr += "<body>";
		messagePartStr += "<query_id>" + queryId + "</query_id>";
		messagePartStr += "<dataList><data>" + ymdStr + "</data></dataList>";
		messagePartStr += "<dataList><data>" + cntFirst + "</data></dataList>";
		if(!cntEnd.equals("")){
			messagePartStr += "<dataList><data>" + cntEnd + "</data></dataList>";
		}
		
		messagePartStr += cndList;
		messagePartStr += "</body>";
		messagePartStr += "</message>";
		
		//암호화 
		msg += getBase64Enc(messagePartStr);
		
		msg += "</DATA>";
		
		msg += "</DOCUMENT>";
		String url = WSDL_URL;
		
		Document document = getDocument(msg);
		soapBody.addDocument(document);
		
		SOAPConnectionFactory soapconnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapconnectionFactory.createConnection();
		
		//soap server에 호출해서 message 받아오기
		SOAPMessage resSOAPMessage = soapConnection.call(soapMessage,new URL(url));
		SOAPBody responseSOAPBody = resSOAPMessage.getSOAPBody();
		
		NodeList dataNodes = responseSOAPBody.getElementsByTagName("DATA");
		
		//복호화
		String decStr = getBase64Dec(dataNodes.item(0).getTextContent());
		Document doc = getDocument(decStr);
		NodeList nodeList = doc.getElementsByTagName("list");
		NodeList resCntList = doc.getElementsByTagName("res_cnt");
		
		cnt = Integer.valueOf(resCntList.item(0).getTextContent());
		
		//받은 message 처리
		for(int i=0; i < nodeList.getLength(); i++){
			Element ele = (Element)nodeList.item(i);
			BmlWellPs bmlWellPs = elementToBmlWellPs(ele);
			
			remove(bmlWellPs);
			insert(bmlWellPs);
		}
		
		
		return cnt;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService#soapLinkAgrldReqstInfo(java.lang.String, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	public ArrayList<AgrldReqstInfo> soapLinkAgrldReqstInfo(String ymdStr,String cntFirst, String cndStr) throws Exception{
		
		ArrayList<AgrldReqstInfo> list = new ArrayList<AgrldReqstInfo>();
		
		try {
			// LDAP 에서 새올 인증서를 받아옵니다.
			getLdap();
			
			//연계id
			String ifid			= "SOINN00001";
			//요청 기관코드
			String srcorgcd		= SROCRGCD;
			//수신 기관코드
			String tgtorgcd		= SROCRGCD;
			//송수신 코드 (요청 000, 수신성공 200, 그외 코드는 에러)
			String resultCode	= "000";
			//연계 메시지 구분을 위한 식별자 코드 YYYYMMddHHmmssSSS + 랜덤값 8자리 (총25자리)
			String msgkey		= getRandomData();
			//서비스 id 시설관리현황 (4530000SOI2210), 농지전용신청정보 (4530000SOI2220), 산지전용관리대장 (4530000SOI2230)
			String queryId		= "4530000SOI2220";
			
			//cndList
			String cndList		= "";
			
			//세부조건문 
			if(!cndStr.equals("")){
				cndList += cndStr;
			}
			
			//요청 message 생성
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope(); 
			soapEnvelope.addNamespaceDeclaration("xmlns", "http://schema.xmlsoap.org/soap/envelope/");
			SOAPBody soapBody = soapEnvelope.getBody();
		
			String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DOCUMENT xmlns=\"\">";
			msg += "<IFID>" + ifid + "</IFID>";
			msg += "<SRCORGCD>" + srcorgcd + "</SRCORGCD>";
			msg += "<TGTORGCD>" + tgtorgcd + "</TGTORGCD>";
			msg += "<RESULTCODE>" + resultCode + "</RESULTCODE>";
			
			msg += "<MSGKEY>" + msgkey + "</MSGKEY>";
			
			msg += "<DATA>";
			
			String messagePartStr = "";
			messagePartStr += "<message>";
			
			messagePartStr += "<body>";
			messagePartStr += "<query_id>" + queryId + "</query_id>";
			messagePartStr += "<dataList><data>" + ymdStr + "</data></dataList>";
			messagePartStr += "<dataList><data>" + cntFirst + "</data></dataList>";
			
			messagePartStr += cndList;
			messagePartStr += "</body>";
			messagePartStr += "</message>";
			
			//암호화 
			msg += getBase64Enc(messagePartStr);
			
			msg += "</DATA>";
			
			msg += "</DOCUMENT>";
			String url = WSDL_URL;
			
			Document document = getDocument(msg);
			soapBody.addDocument(document);
			
			SOAPConnectionFactory soapconnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapconnectionFactory.createConnection();
			
			//soap server에 호출해서 message 받아오기
			SOAPMessage resSOAPMessage = soapConnection.call(soapMessage,new URL(url));
			SOAPBody responseSOAPBody = resSOAPMessage.getSOAPBody();
			
			NodeList dataNodes = responseSOAPBody.getElementsByTagName("DATA");
			
			//복호화
			String decStr = getBase64Dec(dataNodes.item(0).getTextContent());
			Document doc = getDocument(decStr);
			NodeList nodeList = doc.getElementsByTagName("list");
			
			//받은 message 처리
			for(int i=0; i < nodeList.getLength(); i++){
				Element ele = (Element)nodeList.item(i);
				AgrldReqstInfo agrldReqstInfo = elementToAgrldReqstInfo(ele);
				list.add(agrldReqstInfo);
			}
		
		} catch (Exception e) {
			errorService.addError(e);
		}
		
		return list;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService#soapLinkDfnndManageRegstr(java.lang.String, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	public ArrayList<DfnndManageRegstr> soapLinkDfnndManageRegstr(String ymdStr,String cntFirst, String cndStr) throws Exception{
		
		ArrayList<DfnndManageRegstr> list = new ArrayList<DfnndManageRegstr>();
		
		try {
			// LDAP 에서 새올 인증서를 받아옵니다.
			getLdap();
			
			//연계id
			String ifid			= "SOINN00001";
			//요청 기관코드
			String srcorgcd		= SROCRGCD;
			//수신 기관코드
			String tgtorgcd		= SROCRGCD;
			//송수신 코드 (요청 000, 수신성공 200, 그외 코드는 에러)
			String resultCode	= "000";
			//연계 메시지 구분을 위한 식별자 코드 YYYYMMddHHmmssSSS + 랜덤값 8자리 (총25자리)
			String msgkey		= getRandomData();
			//서비스 id 시설관리현황 (4530000SOI2210), 농지전용신청정보 (4530000SOI2220), 산지전용관리대장 (4530000SOI2230)
			String queryId		= "4530000SOI2230";
			
			//cndList
			String cndList		= "";
			
			if(!cndStr.equals("")){
				cndList += cndStr;
			}
			
			//메세지 생성
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope(); 
			soapEnvelope.addNamespaceDeclaration("xmlns", "http://schema.xmlsoap.org/soap/envelope/");
			SOAPBody soapBody = soapEnvelope.getBody();
		
			String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DOCUMENT xmlns=\"\">";
			msg += "<IFID>" + ifid + "</IFID>";
			msg += "<SRCORGCD>" + srcorgcd + "</SRCORGCD>";
			msg += "<TGTORGCD>" + tgtorgcd + "</TGTORGCD>";
			msg += "<RESULTCODE>" + resultCode + "</RESULTCODE>";
			
			msg += "<MSGKEY>" + msgkey + "</MSGKEY>";
			
			msg += "<DATA>";
			
			String messagePartStr = "";
			messagePartStr += "<message>";
			
			messagePartStr += "<body>";
			messagePartStr += "<query_id>" + queryId + "</query_id>";
			messagePartStr += "<dataList><data>" + ymdStr + "</data></dataList>";
			messagePartStr += "<dataList><data>" + cntFirst + "</data></dataList>";
			
			messagePartStr += cndList;
			messagePartStr += "</body>";
			messagePartStr += "</message>";
			
			//암호화 
			msg += getBase64Enc(messagePartStr);
			
			msg += "</DATA>";
			
			msg += "</DOCUMENT>";
			String url = WSDL_URL;
			
			Document document = getDocument(msg);
			soapBody.addDocument(document);
			
			SOAPConnectionFactory soapconnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapconnectionFactory.createConnection();
			
			//soap server에 호출해서 message 받아오기
			SOAPMessage resSOAPMessage = soapConnection.call(soapMessage,new URL(url));
			SOAPBody responseSOAPBody = resSOAPMessage.getSOAPBody();
			
			NodeList dataNodes = responseSOAPBody.getElementsByTagName("DATA");
			
			//복호화
			String decStr = getBase64Dec(dataNodes.item(0).getTextContent());
			Document doc = getDocument(decStr);
			NodeList nodeList = doc.getElementsByTagName("list");
			
			//받은 message 처리
			for(int i=0; i < nodeList.getLength(); i++){
				Element ele = (Element)nodeList.item(i);
				DfnndManageRegstr dfnndManageRegstr = elementToDfnndManageRegstr(ele);
				list.add(dfnndManageRegstr);
			}
		
		} catch (Exception e) {
			errorService.addError(e);
		}
		return list;
	}
	
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
	/// @fn getLdap
	/// @brief 함수 간략한 설명 : LDAP 에서 인증서 받아오기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @throws GpkiApiException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void getLdap() throws GpkiApiException, IOException{
		
		try {
			GpkiApi.init(GPKI_LIC_PATH);
	//		String path = SEAOL_CER_PATH;
			Ldap ldap = new Ldap();
			ldap.setLdap("152.99.56.86", 389);
			
			ldap.searchCN(Ldap.DATA_TYPE_KM_CERT , SEAOL_CER_FILE_NM );
			
			byte[] sn=ldap.getData();
			
			File directory = new File(SEAOL_CER_PATH);
			if( !directory.exists() ){
				directory.mkdir();
			}
			
			FileOutputStream fos = new FileOutputStream(SEAOL_CER_PATH + SEAOL_CER_FILE_NM + ".cer"); 
			fos.write(sn);
			fos.close();
		} catch (Exception e) {
			errorService.addError(e);
		}
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
	/// @fn getBase64Enc
	/// @brief 함수 간략한 설명 : base64로 encoding
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param msg
	/// @return
	/// @throws GpkiApiException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getBase64Enc(String msg) throws GpkiApiException, IOException{
		//gpkiApi 초기화 (gpkiApi 라이센스 체크)
		GpkiApi.init(GPKI_LIC_PATH);
		getLdap();

		//인증서(새올 시스템)
		String SERVER_KM_CERT_PATH = SEAOL_CER_PATH + SEAOL_CER_FILE_NM + ".cer";
		//인증서 읽어오기
		X509Certificate srvCert = Disk.readCert(SERVER_KM_CERT_PATH);
		
		String strBase64 = "";
		//암호화 방식 설정
		EnvelopedData envData = new EnvelopedData("NEAT");
		envData.addRecipient(srvCert);
		
		Base64 base64 = new Base64();
		strBase64 = new String(base64.encode(envData.generate(msg.getBytes())));
		return strBase64;
	}
	
	/////////////////////////////////////////////
	/// @fn getBase64Dec
	/// @brief 함수 간략한 설명 : base64로 decoding
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param msg
	/// @return
	/// @throws GpkiApiException
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getBase64Dec(String msg) throws GpkiApiException, UnsupportedEncodingException{
		//gpkiApi 초기화 (gpkiApi 라이센스 체크)
		GpkiApi.init(GPKI_LIC_PATH);
		
		//인증서 
		String SERVER_KM_CERT_PATH = SERVER_CER_PATH + SERVER_CER_FILE_NM;
		String SERVER_KM_PRIKEY_PATH = SERVER_CER_PATH + SERVER_KEY_FILE_NM;
		
		//인증서 읽어오기
		X509Certificate srvCert = Disk.readCert(SERVER_KM_CERT_PATH);
		Base64 base64 = new Base64();
		
		//복호화 방식 설정
		EnvelopedData envData = new EnvelopedData("NEAT");
		envData.addRecipient(srvCert);
		byte[] outMsg = null;
		byte[] byteContent = base64.decode(msg);
		outMsg = envData.process(byteContent, srvCert, Disk.readPriKey(SERVER_KM_PRIKEY_PATH, SERVER_KEY_PASS_CODE));
		
		//euc-kr로 decoding
		String outMsgStr = new String(outMsg, "euc-kr");
		
		return outMsgStr;
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
	
	
	/////////////////////////////////////////////
	/// @fn elementToBmlWellPs
	/// @brief 함수 간략한 설명 : 지하수관정 정보를 클래스에 맵핑
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ele
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private BmlWellPs elementToBmlWellPs(Element ele){
		BmlWellPs bmlWellPs = new BmlWellPs();
//		perm_nt_no
		bmlWellPs.setPermNtNo(ele.getElementsByTagName("perm_nt_no").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("perm_nt_no").item(0).getTextContent());
		
//		mg_no
		bmlWellPs.setMgNo(ele.getElementsByTagName("mg_no").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("mg_no").item(0).getTextContent());
		
//		perm_nt_form_gbn
		bmlWellPs.setPermNtFormGbn(ele.getElementsByTagName("perm_nt_form_gbn").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("perm_nt_form_gbn").item(0).getTextContent());
		
//		rgt_mbd_gbn
		bmlWellPs.setRgtMbdGbn(ele.getElementsByTagName("rgt_mbd_gbn").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("rgt_mbd_gbn").item(0).getTextContent());
		
//		rgt_mbd_reg_no
		bmlWellPs.setRgtMbdRegNo(ele.getElementsByTagName("rgt_mbd_reg_no").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("rgt_mbd_reg_no").item(0).getTextContent());
		
//		rgt_mbd_nm
		bmlWellPs.setRgtMbdNm(ele.getElementsByTagName("rgt_mbd_nm").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("rgt_mbd_nm").item(0).getTextContent());
		
//		nm
		bmlWellPs.setNm(ele.getElementsByTagName("nm").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("nm").item(0).getTextContent());
		
//		rgt_mbd_addr
		bmlWellPs.setRgtMbdAddr(ele.getElementsByTagName("rgt_mbd_addr").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("rgt_mbd_addr").item(0).getTextContent());
		
//		rgt_mbd_rdn_addr
		bmlWellPs.setRgtMbdRdnAddr(ele.getElementsByTagName("rgt_mbd_rdn_addr").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("rgt_mbd_rdn_addr").item(0).getTextContent());
		
//		dvop_loc_addr
		bmlWellPs.setDvopLocAddr(ele.getElementsByTagName("dvop_loc_addr").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dvop_loc_addr").item(0).getTextContent());
		
//		dvop_loc_rdn_addr
		bmlWellPs.setDvopLocRdnAddr(ele.getElementsByTagName("dvop_loc_rdn_addr").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dvop_loc_rdn_addr").item(0).getTextContent());
		
//		dvop_loc_regn_code
		bmlWellPs.setDvopLocRegnCode(ele.getElementsByTagName("dvop_loc_regn_code").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dvop_loc_regn_code").item(0).getTextContent());
		
//		dvop_loc_umd_name
		bmlWellPs.setDvopLocUmdName(ele.getElementsByTagName("dvop_loc_umd_name").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dvop_loc_umd_name").item(0).getTextContent());
		
//		dvop_loc_bunji
		bmlWellPs.setDvopLocBunji(ele.getElementsByTagName("dvop_loc_bunji").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dvop_loc_bunji").item(0).getTextContent());
		
//		dvop_loc_ho
		bmlWellPs.setDvopLocHo(ele.getElementsByTagName("dvop_loc_ho").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dvop_loc_ho").item(0).getTextContent());
		
//		litd_dg
		String tmp_litd_dg = ele.getElementsByTagName("litd_dg").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("litd_dg").item(0).getTextContent();
		if(!tmp_litd_dg.equals("")){
			bmlWellPs.setLitdDg( Integer.valueOf(tmp_litd_dg) );
		}
			
//		litd_mint
		String tmp_litd_mint = ele.getElementsByTagName("litd_mint").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("litd_mint").item(0).getTextContent();
		if(!tmp_litd_mint.equals("")){
			bmlWellPs.setLitdMint( Integer.valueOf(tmp_litd_mint) );
		}
		
//		litd_sc
		String tmp_litd_sc = ele.getElementsByTagName("litd_sc").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("litd_sc").item(0).getTextContent();
		if(!tmp_litd_sc.equals("")){
			bmlWellPs.setLitdSc( Double.valueOf(tmp_litd_sc) );
		}
		
//		lttd_dg
		String tmp_lttd_dg = ele.getElementsByTagName("lttd_dg").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("lttd_dg").item(0).getTextContent();
		if(!tmp_lttd_dg.equals("")){
			bmlWellPs.setLttdDg( Integer.valueOf(tmp_lttd_dg) );
		}
		
//		lttd_mint
		String tmp_lttd_mint = ele.getElementsByTagName("lttd_mint").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("lttd_mint").item(0).getTextContent();
		if(!tmp_lttd_mint.equals("")){
			bmlWellPs.setLttdMint( Integer.valueOf(tmp_lttd_mint) );
		}
		
//		lttd_sc
		String tmp_lttd_sc = ele.getElementsByTagName("lttd_sc").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("lttd_sc").item(0).getTextContent();
		if(!tmp_lttd_sc.equals("")){
			bmlWellPs.setLttdSc( Double.valueOf(tmp_lttd_sc) );
		}
		
//		uwater_srv
		bmlWellPs.setUwaterSrv(ele.getElementsByTagName("uwater_srv").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("uwater_srv").item(0).getTextContent());
		
//		uwater_dtl_srv
		bmlWellPs.setUwaterDtlSrv(ele.getElementsByTagName("uwater_dtl_srv").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("uwater_dtl_srv").item(0).getTextContent());
		
//		uwater_pota_yn
		bmlWellPs.setUwaterPotaYn(ele.getElementsByTagName("uwater_pota_yn").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("uwater_pota_yn").item(0).getTextContent());
		
//		dig_dph
		String tmp_dig_dph = ele.getElementsByTagName("dig_dph").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dig_dph").item(0).getTextContent();
		if(!tmp_dig_dph.equals("")){
			bmlWellPs.setDigDph( Double.valueOf(tmp_dig_dph) );
		}
		
//		dig_dbt
		String tmp_dig_dbt = ele.getElementsByTagName("dig_dbt").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dig_dbt").item(0).getTextContent();
		if(!tmp_dig_dbt.equals("")){
			bmlWellPs.setDigDbt( Double.valueOf(tmp_dig_dbt) );
		}
		
//		frw_pln_qua
		String tmp_frw_pln_qua = ele.getElementsByTagName("frw_pln_qua").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("frw_pln_qua").item(0).getTextContent();
		if(!tmp_frw_pln_qua.equals("")){
			bmlWellPs.setFrwPlnQua( Double.valueOf(tmp_frw_pln_qua) );
		}
		
//		nd_qt
		String tmp_nd_qt = ele.getElementsByTagName("nd_qt").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("nd_qt").item(0).getTextContent();
		if(!tmp_nd_qt.equals("")){
			bmlWellPs.setNdQt( Integer.valueOf(tmp_nd_qt) );
		}
		
//		dyn_eqn_hrp
		String tmp_dyn_eqn_hrp = ele.getElementsByTagName("dyn_eqn_hrp").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dyn_eqn_hrp").item(0).getTextContent();
		if(!tmp_dyn_eqn_hrp.equals("")){
			bmlWellPs.setDynEqnHrp( Double.valueOf(tmp_dyn_eqn_hrp) );
		}
		
//		pipe_diam
		String tmp_pipe_diam = ele.getElementsByTagName("pipe_diam").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("pipe_diam").item(0).getTextContent();
		if(!tmp_pipe_diam.equals("")){
			bmlWellPs.setPipeDiam( Double.valueOf(tmp_pipe_diam) );
		}
//		esb_dph
		String tmp_esb_dph = ele.getElementsByTagName("esb_dph").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("esb_dph").item(0).getTextContent();
		if(!tmp_esb_dph.equals("")){
			bmlWellPs.setEsbDph( Double.valueOf(tmp_esb_dph) );
		}
		
//		rwt_cap
		String tmp_rwt_cap = ele.getElementsByTagName("rwt_cap").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("rwt_cap").item(0).getTextContent();
		if(!tmp_rwt_cap.equals("")){
			bmlWellPs.setRwtCap( Double.valueOf(tmp_rwt_cap) );
		}
		
//		dvus_end_nt_ymd
		bmlWellPs.setDvusEndNtYmd(ele.getElementsByTagName("dvus_end_nt_ymd").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("dvus_end_nt_ymd").item(0).getTextContent());
//		lnho_raise_ymd
		bmlWellPs.setLnhoRaiseYmd(ele.getElementsByTagName("lnho_raise_ymd").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("lnho_raise_ymd").item(0).getTextContent());
//		lnho_raise_cau
		bmlWellPs.setLnhoRaiseCau(ele.getElementsByTagName("lnho_raise_cau").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("lnho_raise_cau").item(0).getTextContent());
//		lnho_deal_met
		bmlWellPs.setLnhoDealMet(ele.getElementsByTagName("lnho_deal_met").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("lnho_deal_met").item(0).getTextContent());
//		ostrs_met
		bmlWellPs.setOstrsMet(ele.getElementsByTagName("ostrs_met").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("ostrs_met").item(0).getTextContent());
//		lnho_dpp_nm
		bmlWellPs.setLnhoDppNm(ele.getElementsByTagName("lnho_dpp_nm").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("lnho_dpp_nm").item(0).getTextContent());
//		perm_yn
		bmlWellPs.setPermYn(ele.getElementsByTagName("perm_yn").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("perm_yn").item(0).getTextContent());
//		perm_cancel_yn
		bmlWellPs.setPermCancelYn(ele.getElementsByTagName("perm_cancel_yn").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("perm_cancel_yn").item(0).getTextContent());
//		rem
		bmlWellPs.setRem(ele.getElementsByTagName("rem").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("rem").item(0).getTextContent());

		return bmlWellPs;
	}
	
	
	/////////////////////////////////////////////
	/// @fn elementToAgrldReqstInfo
	/// @brief 함수 간략한 설명 : 농지전용신청정보를 클래스에 맵핑
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ele
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private AgrldReqstInfo elementToAgrldReqstInfo(Element ele){
		AgrldReqstInfo agrldReqstInfo = new AgrldReqstInfo();
		
		//APV_PERM_REG_MGT_NO	인허가등록관리번호
		agrldReqstInfo.setApvPermRegMgtNo(ele.getElementsByTagName("apv_perm_reg_mgt_no").item(0).getTextContent());
		//BSN_CL_CODE	업무구분
		agrldReqstInfo.setBsnClCode(ele.getElementsByTagName("bsn_cl_code").item(0).getTextContent());
		//BSN_END_YMD	업무종료일자
		agrldReqstInfo.setBsnEndYmd(ele.getElementsByTagName("bsn_end_ymd").item(0).getTextContent());
		//FML_ADDR	소재지
		agrldReqstInfo.setFmlAddr(ele.getElementsByTagName("fml_addr").item(0).getTextContent());
		//BON_NO	소재지_본번
		agrldReqstInfo.setBonNo(ele.getElementsByTagName("bon_no").item(0).getTextContent());
		//BU_NO	소재지_부번
		agrldReqstInfo.setBuNo(ele.getElementsByTagName("bu_no").item(0).getTextContent());
		//REGN_CODE_NM	법정동명
		agrldReqstInfo.setRegnCodeNm(ele.getElementsByTagName("regn_code_nm").item(0).getTextContent());
		//REGN_CODE	법정동코드
		agrldReqstInfo.setRegnCode(ele.getElementsByTagName("regn_code").item(0).getTextContent());
		//FML_ID	농지아이디
		agrldReqstInfo.setFmlId(ele.getElementsByTagName("fml_id").item(0).getTextContent());
		
		//FML_SEQ_NO	농지일련번호
		String fml_seq_no = ele.getElementsByTagName("fml_seq_no").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("fml_seq_no").item(0).getTextContent();
		if(!fml_seq_no.equals("")){
			agrldReqstInfo.setFmlSeqNo( Integer.valueOf(fml_seq_no) );
		}
		
		//PYMNT_PRE_ARR_PSTN_BEA_AM	농지보전부담금
		String pymnt_pre_arr_pstn_bea_am = ele.getElementsByTagName("pymnt_pre_arr_pstn_bea_am").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("pymnt_pre_arr_pstn_bea_am").item(0).getTextContent();
		if(!pymnt_pre_arr_pstn_bea_am.equals("")){
			agrldReqstInfo.setPymntPreArrPstnBeaAm( Double.valueOf(pymnt_pre_arr_pstn_bea_am) );
		}
		
		//PFL_RLND_JIMK_CODE	공부지목
		agrldReqstInfo.setPflRlndJimkCode(ele.getElementsByTagName("pfl_rlnd_jimk_code").item(0).getTextContent());
		//PFL_FACT_JIMK_CODE	실제지목
		agrldReqstInfo.setPflFactJimkCode(ele.getElementsByTagName("pfl_fact_jimk_code").item(0).getTextContent());
		//PFL_RLND_AR	필지면적
		String pfl_rlnd_ar = ele.getElementsByTagName("pfl_rlnd_ar").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("pfl_rlnd_ar").item(0).getTextContent();
		if(!pfl_rlnd_ar.equals("")){
			agrldReqstInfo.setPflRlndAr( Double.valueOf(pfl_rlnd_ar) );
		}
		
		//PFL_EXCL_AR	전용면적
		String pfl_excl_ar = ele.getElementsByTagName("pfl_excl_ar").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("pfl_excl_ar").item(0).getTextContent();
		if(!pfl_excl_ar.equals("")){
			agrldReqstInfo.setPflEexclAr( Double.valueOf(pfl_excl_ar) );
		}
		
		//PFL_FML_SE_CODE	농지구분
		agrldReqstInfo.setPflFmlSeCode(ele.getElementsByTagName("pfl_fml_se_code").item(0).getTextContent());
		//PFL_SRV_DIST_CODE	용도구역
		agrldReqstInfo.setPflSrvDistCode(ele.getElementsByTagName("pfl_srv_dist_code").item(0).getTextContent());
		//PFL_CLND_ARGMT_YN	경지정리여부
		agrldReqstInfo.setPflClndArgmtYn(ele.getElementsByTagName("pfl_clnd_argmt_yn").item(0).getTextContent());
		//PFL_WTR_DEVELOP_YN	용수개발여부
		agrldReqstInfo.setPflWtrDevelopYn(ele.getElementsByTagName("pfl_wtr_develop_yn").item(0).getTextContent());
		//PFL_MST_CULT_CRP_CODE	주재배작물
		agrldReqstInfo.setPflMstCultCrpCode(ele.getElementsByTagName("pfl_mst_cult_crp_code").item(0).getTextContent());
		//PFL_RAE_RT	감면율
		String pfl_rae_rt = ele.getElementsByTagName("pfl_rae_rt").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("pfl_rae_rt").item(0).getTextContent();
		if(!pfl_rae_rt.equals("")){
			agrldReqstInfo.setPflRaeRt( Integer.valueOf(pfl_rae_rt) );
		}
		
		//APM_FLAP_OBJ_NM	전용목적
		agrldReqstInfo.setApmFlapObjNm(ele.getElementsByTagName("apm_flap_obj_nm").item(0).getTextContent());
		//CAP_APV_PERM_YMD	허가일자
		agrldReqstInfo.setCapApvPermYmd(ele.getElementsByTagName("cap_apv_perm_ymd").item(0).getTextContent());
		//TGT_RESI_ORGZ_NM	전용자명
		agrldReqstInfo.setTgtResiOrgzNm(ele.getElementsByTagName("tgt_resi_orgz_nm").item(0).getTextContent());
		//SID	전용자생년월일(법인번호)
		agrldReqstInfo.setSid(ele.getElementsByTagName("sid").item(0).getTextContent());
		//CAP_CGG_APV_PERM_NO	허가번호
		agrldReqstInfo.setCapCggApvPermNo(ele.getElementsByTagName("cap_cgg_apv_perm_no").item(0).getTextContent());
		//TGT_TEL_NO	전용자전화번호
		agrldReqstInfo.setTgtTelNo(ele.getElementsByTagName("tgt_tel_no").item(0).getTextContent());
		
		return agrldReqstInfo;
	}
	
	/////////////////////////////////////////////
	/// @fn elementToDfnndManageRegstr
	/// @brief 함수 간략한 설명 : 산지전용신청정보를 클래스에 맵핑
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ele
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private DfnndManageRegstr elementToDfnndManageRegstr(Element ele){
		DfnndManageRegstr dfnndManageRegstr = new DfnndManageRegstr();
		
		dfnndManageRegstr.setFomApvNo(ele.getElementsByTagName("fom_apv_no").item(0).getTextContent());
		dfnndManageRegstr.setPermYmd(ele.getElementsByTagName("perm_ymd").item(0).getTextContent());
		dfnndManageRegstr.setRgtMbdNm(ele.getElementsByTagName("rgt_mbd_nm").item(0).getTextContent());
		dfnndManageRegstr.setAplrAddr(ele.getElementsByTagName("aplr_addr").item(0).getTextContent());
		dfnndManageRegstr.setRdnWhlAddr(ele.getElementsByTagName("rdn_whl_addr").item(0).getTextContent());
		dfnndManageRegstr.setRegnNm(ele.getElementsByTagName("regn_nm").item(0).getTextContent());
		dfnndManageRegstr.setRegnCode(ele.getElementsByTagName("regn_code").item(0).getTextContent());
		dfnndManageRegstr.setLgGbn(ele.getElementsByTagName("lg_gbn").item(0).getTextContent());
		dfnndManageRegstr.setBoJibn(ele.getElementsByTagName("bo_jibn").item(0).getTextContent());
		dfnndManageRegstr.setBuJibn(ele.getElementsByTagName("bu_jibn").item(0).getTextContent());
		dfnndManageRegstr.setHalfFomAddr(ele.getElementsByTagName("half_fom_addr").item(0).getTextContent());
		String acrg = ele.getElementsByTagName("acrg").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("acrg").item(0).getTextContent();
		if(!acrg.equals("")){
			dfnndManageRegstr.setAcrg( Double.valueOf(acrg) );
		}
		
		String perm_area = ele.getElementsByTagName("perm_area").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("perm_area").item(0).getTextContent();
		if(!perm_area.equals("")){
			dfnndManageRegstr.setPermArea( Double.valueOf(perm_area) );
		}
		
		dfnndManageRegstr.setExclPurpose(ele.getElementsByTagName("excl_purpose").item(0).getTextContent());
		dfnndManageRegstr.setEtcPurpose(ele.getElementsByTagName("etc_purpose").item(0).getTextContent());
		dfnndManageRegstr.setWorkStdt(ele.getElementsByTagName("work_stdt").item(0).getTextContent());
		dfnndManageRegstr.setWorkEnddt(ele.getElementsByTagName("work_enddt").item(0).getTextContent());
		
		String aaexp_amt = ele.getElementsByTagName("aaexp_amt").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("aaexp_amt").item(0).getTextContent();
		if(!aaexp_amt.equals("")){
			dfnndManageRegstr.setAaexpAmt( Long.valueOf(aaexp_amt) );
		}
		
		String recory_amt = ele.getElementsByTagName("recory_amt").item(0).getTextContent() == null ? "" : ele.getElementsByTagName("recory_amt").item(0).getTextContent();
		if(!recory_amt.equals("")){
			dfnndManageRegstr.setRecoryAmt( Long.valueOf(recory_amt) );
		}
		
		dfnndManageRegstr.setRecoryEnddt(ele.getElementsByTagName("recory_enddt").item(0).getTextContent());
		dfnndManageRegstr.setRecoryJgongYmd(ele.getElementsByTagName("recory_jgong_ymd").item(0).getTextContent());
		dfnndManageRegstr.setRecoryDtyExmYn(ele.getElementsByTagName("recory_dty_exm_yn").item(0).getTextContent());

		return dfnndManageRegstr;
	}
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : DB에 입력
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer insert(BmlWellPs bmlWellPs){
		Long nextId = (long) 0;
		
		try {
			nextId = bmlWellPsIdGnrService.getNextLongId();
			
		} catch (FdlException e) {
			e.printStackTrace();
		}
		
		
		bmlWellPs.setObjectid(nextId);
		bmlWellPs.setFtrIdn(nextId);
		bmlWellPs.setFtrCde("BW001");
		
		Integer cnt = bmlWellPsMapper.insert(bmlWellPs) ;
		return cnt;
	}
	
	/////////////////////////////////////////////
	/// @fn remove
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer remove(BmlWellPs bmlWellPs){
		return bmlWellPsMapper.remove(bmlWellPs);
	}

	@Override
	public Integer soapLinkCmmnProp(String queryId) throws Exception {
		return null;
	}

	
	/*private Integer removeAll(){
		return bmlWellPsMapper.removeAll();
	}*/
}
