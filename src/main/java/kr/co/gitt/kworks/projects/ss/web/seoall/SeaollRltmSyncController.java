package kr.co.gitt.kworks.projects.ss.web.seoall;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.gitt.kworks.saeoll.model.AgrldReqstInfo;
import kr.co.gitt.kworks.saeoll.model.DfnndManageRegstr;
import kr.co.gitt.kworks.saeoll.service.SoapLinkUtilService;

/////////////////////////////////////////////
/// @class AgrldController
/// kr.co.gitt.kworks.web.agrld \n
///   ㄴ AgrldController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 10. 26. 오후 10:24:59 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 농지전용 화면 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/seaoll/")
@Profile({"ss_dev", "ss_oper"})
public class SeaollRltmSyncController {
	
	@Resource
	SoapLinkUtilService soapLinkUtilService;
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 농지,산지 (새올연계)화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnuCode
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{pnuCode}/selectOne.do", method=RequestMethod.POST)
	public String selectOne(@PathVariable("pnuCode") String pnuCode, Model model) throws Exception{
		
		//pnu 코드값 분리
		String cityCode	= pnuCode.substring(0,10);
//		String dongCode	= pnuCode.substring(5,10);
		String bonNo	= pnuCode.substring(11,15);
		String buNo		= pnuCode.substring(15,19);
		
		String ymdStr = "19000101";
		String cntFirst = "0";
		
		String agrldChk = "success";
		String dfnndChk = "success";
		
		//regn_code
		String agrldReqstInfoCndListStr	= "";
		
		//농지전용 조건검색에 사용할 조건
		String agrldReqstInfoDongCode	= "regn_code = '" + cityCode + "'";
		String agrldReqstInfoBonNo		= "bon_no = '" + bonNo + "'";
		String agrldReqstInfoBuNo		= "bu_no = '" + buNo + "'";
		
		
		//농지전용 조건문생성(시코드, 본번코드, 부번코드)
		agrldReqstInfoCndListStr += soapLinkUtilService.makeCndLisatStr(agrldReqstInfoDongCode);
		agrldReqstInfoCndListStr += soapLinkUtilService.makeCndLisatStr(agrldReqstInfoBonNo);
		agrldReqstInfoCndListStr += soapLinkUtilService.makeCndLisatStr(agrldReqstInfoBuNo);
		
		//농지전용 정보 리스트 가져오기
		ArrayList<AgrldReqstInfo> agrldReqstInfoList = soapLinkUtilService.soapLinkAgrldReqstInfo(ymdStr, cntFirst, agrldReqstInfoCndListStr);
		
		AgrldReqstInfo agrldReqstInfo = new AgrldReqstInfo();
		if(agrldReqstInfoList.size() > 0 ){
			agrldReqstInfo = agrldReqstInfoList.get(0);
		}else{
			agrldChk = "error";
		}
		
		//regn_code
		String dfnndManageRegstrCndListStr	= "";

		//산지전용 조건검색에 사용할 조건
		String dfnndManageRegstrCityCode	= "regn_code = '" + cityCode + "'";
		String dfnndManageRegstrBonNo		= "bo_jibn = '" + bonNo + "'";
		String dfnndManageRegstrBuNo		= "bu_jibn = '" + buNo + "'";
		
		//산지전용 조건문생성(시코드, 본번코드, 부번코드)
		dfnndManageRegstrCndListStr += soapLinkUtilService.makeCndLisatStr(dfnndManageRegstrCityCode);
		dfnndManageRegstrCndListStr += soapLinkUtilService.makeCndLisatStr(dfnndManageRegstrBonNo);
		dfnndManageRegstrCndListStr += soapLinkUtilService.makeCndLisatStr(dfnndManageRegstrBuNo);
		
		//산지전용 정보 리스트 가져오기
		ArrayList<DfnndManageRegstr> dfnndManageRegstrList = soapLinkUtilService.soapLinkDfnndManageRegstr(ymdStr, cntFirst, dfnndManageRegstrCndListStr);
		
		DfnndManageRegstr dfnndManageRegstr = new DfnndManageRegstr();
		if(dfnndManageRegstrList.size() > 0){
			dfnndManageRegstr = dfnndManageRegstrList.get(0);
		}else{
			dfnndChk = "error";
		}
		
		model.addAttribute("agrldChk", agrldChk);
		model.addAttribute("dfnndChk", dfnndChk);
		model.addAttribute("agrld", agrldReqstInfo);
		model.addAttribute("dfnnd", dfnndManageRegstr);
		return "window/seaoll";
	}
}
