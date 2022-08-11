package kr.co.gitt.kworks.projects.ss.contact.saeoll.service;

import java.util.ArrayList;

import kr.co.gitt.kworks.saeoll.model.AgrldReqstInfo;
import kr.co.gitt.kworks.saeoll.model.DfnndManageRegstr;
import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class SoapLinkSampleServiceImpl
/// kr.co.gitt.kworks.saeoll.service \n
///   ㄴ SoapLinkSampleServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 4:59:37 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 새올연계가 되지 않을 경우 사용하는 샘플 서비스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("soapLinkUtilService")
@Profile({"gds_dev", "ss_dev"})
public class SoapLinkSampleServiceImpl implements SoapLinkUtilService {
		
		/////////////////////////////////////////////
		/// @fn 
		/// @brief (Override method) 함수 간략한 설명
		/// @remark
		/// - 오버라이드 함수의 상세 설명
		/// @see kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService#soapLink(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		/////////////////////////////////////////////
		public Integer soapLink(String ymdStr,String cntFirst, String cntEnd, String cndStr) throws Exception{
			Integer cnt = 0;
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
			AgrldReqstInfo agrldReqstInfo = new AgrldReqstInfo();
			
			agrldReqstInfo.setApvPermRegMgtNo("AGCC045301692016000200");
			agrldReqstInfo.setBsnClCode("AGCC0");
			agrldReqstInfo.setBsnEndYmd("");
			agrldReqstInfo.setFmlAddr("경기 의왕시 포일동");
			agrldReqstInfo.setBonNo("123");
			agrldReqstInfo.setBuNo("12");
			
			agrldReqstInfo.setRegnCodeNm("경기 의왕시 포일동");
			agrldReqstInfo.setRegnCode("");
			
			agrldReqstInfo.setFmlId("123456789");
			agrldReqstInfo.setFmlSeqNo(1);
			agrldReqstInfo.setPymntPreArrPstnBeaAm(Double.valueOf("0"));
			agrldReqstInfo.setPflRlndJimkCode("01");
			agrldReqstInfo.setPflFactJimkCode("01");
			agrldReqstInfo.setPflRlndAr(Double.valueOf("2420"));
			agrldReqstInfo.setPflEexclAr(Double.valueOf("375"));
			agrldReqstInfo.setPflFmlSeCode("03");
			agrldReqstInfo.setPflSrvDistCode("43");
			agrldReqstInfo.setPflClndArgmtYn("N");
			agrldReqstInfo.setPflWtrDevelopYn("N");
			agrldReqstInfo.setPflMstCultCrpCode("13");
			agrldReqstInfo.setPflRaeRt(0);
			
			agrldReqstInfo.setApmFlapObjNm("농업용");
			agrldReqstInfo.setCapApvPermYmd("20160630");
			agrldReqstInfo.setTgtResiOrgzNm("최영");
			
			agrldReqstInfo.setSid("880520-1******");
			agrldReqstInfo.setCapCggApvPermNo("2016000200");
			agrldReqstInfo.setTgtTelNo("");
			
			list.add(agrldReqstInfo);
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
			DfnndManageRegstr dfnndManageRegstr = new DfnndManageRegstr();
			
			dfnndManageRegstr.setFomApvNo("123456321201000077");
			dfnndManageRegstr.setPermYmd("20150415");
			
			dfnndManageRegstr.setRgtMbdNm("이순신");
			
			dfnndManageRegstr.setAplrAddr("경기도 의왕시 포일동 492");
			
			dfnndManageRegstr.setRdnWhlAddr("");
			
			dfnndManageRegstr.setRegnNm("포일동");
			
			dfnndManageRegstr.setRegnCode("4421010100");
			dfnndManageRegstr.setLgGbn("1");
			dfnndManageRegstr.setBoJibn("0492");
			dfnndManageRegstr.setBuJibn("0000");
			dfnndManageRegstr.setHalfFomAddr("경기도 의왕시 포일동 492");
			dfnndManageRegstr.setAcrg(Double.valueOf("1346"));
			dfnndManageRegstr.setPermArea(Double.valueOf("150"));
			
			dfnndManageRegstr.setExclPurpose("묘지시설");
			dfnndManageRegstr.setEtcPurpose("");
			
			dfnndManageRegstr.setWorkStdt("20150415");
			dfnndManageRegstr.setWorkEnddt("20160331");
			dfnndManageRegstr.setAaexpAmt(Long.valueOf("0"));
			dfnndManageRegstr.setRecoryAmt(Long.valueOf("0"));
			dfnndManageRegstr.setRecoryEnddt("20151120");
			dfnndManageRegstr.setRecoryJgongYmd("20151120");
			
			dfnndManageRegstr.setRecoryDtyExmYn("");
			
			list.add(dfnndManageRegstr);
			return list;
		}

		/////////////////////////////////////////////
		/// @fn 
		/// @brief (Override method) 함수 간략한 설명
		/// @remark
		/// - 오버라이드 함수의 상세 설명
		/// @see kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService#makeCndLisatStr(java.lang.String)
		/////////////////////////////////////////////
		public String makeCndLisatStr(String cndStr) {
			return "";
		}

		@Override
		public Integer soapLinkCmmnProp(String queryId) throws Exception {
			return null;
		}
}
