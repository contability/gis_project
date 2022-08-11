package kr.co.gitt.kworks.web.window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import kr.co.gitt.kworks.cmmn.dto.KrasRequestDTO;
import kr.co.gitt.kworks.contact.kras.model.KrasResponse;
import kr.co.gitt.kworks.contact.kras.model.LandUsePlanCnfInfoSet;
import kr.co.gitt.kworks.contact.kras.model.LandUsePlanRestrict;
import kr.co.gitt.kworks.contact.kras.service.KrasService;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class KrasController
/// kworks.itf.web \n
///   ㄴ KrasController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:10:41 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 부동산종합공부시스템 연계 컨트롤러입니다. (토지대장, 건물대장)
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/window/kras/")
public class KrasController {
	
	/// 메세지 소스
	@Autowired
	private MessageSource messageSource;
		
	/// 부동산종합공부시스템 연계 서비스
	@Resource(name="krasCommonService")
	KrasService krasCommonService;
	
	/// 도메인 코드 서비스
	@Resource(name="domnCodeService")
	DomnCodeService domnCodeService;
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 토지, 건물 정보 조회 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("list.do")
	public String list() throws ClientProtocolException, IOException, JAXBException {
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		
		return "projects/"+ prjCode +"/window/kras";
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief 함수 간략한 설명 : 토지, 건물 정보 윈도우 팝업으로 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("popupList.do")
	public String popupList(String pnu, Model model) {
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		
		model.addAttribute("pnu", pnu);
		return "projects/"+ prjCode +"/popup/krasPopup";
	}
	
	/////////////////////////////////////////////
	/// @fn searchBass
	/// @brief 함수 간략한 설명 : 기본대장 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @param retryCount
	/// @param model
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("searchBass.do")
	public String searchBass(String pnu, Integer retryCount, Model model) throws ClientProtocolException, IOException, JAXBException {
		KrasResponse landInfo = null;
		KrasResponse bldgInfo = null;
		if(pnu != null && pnu.length() == 19) {
			landInfo = callKrasCommonService(pnu, "KRAS000002", retryCount);
			if(landInfo != null) {
				bldgInfo = callKrasCommonService(pnu, "KRAS000102", retryCount);
			}
		}
		model.addAttribute("landInfo", landInfo);
		model.addAttribute("bldgInfo", bldgInfo);
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn searchJiga
	/// @brief 함수 간략한 설명 : 토지이동연혁 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @param retryCount
	/// @param model
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("searchLandMovHist.do")
	public String searchLandMovHist(String pnu, Integer retryCount, Model model) throws ClientProtocolException, IOException, JAXBException {
		KrasResponse landMovHistSet = null; 
		
		if(pnu != null && pnu.length() == 19) {
			landMovHistSet = callKrasCommonService(pnu, "KRAS000006", retryCount);
		}
		
		model.addAttribute("rows", landMovHistSet);
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn searchJiga
	/// @brief 함수 간략한 설명 : 공시지가 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @param retryCount
	/// @param model
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("searchJiga.do")
	public String searchJiga(String pnu, Integer retryCount, Model model) throws ClientProtocolException, IOException, JAXBException {
		KrasResponse jigaList = null; 
		
		if(pnu != null && pnu.length() == 19) {
			jigaList = callKrasCommonService(pnu, "KRAS000011", retryCount);
		}
		
		model.addAttribute("rows", jigaList);
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn searchUsePlan
	/// @brief 함수 간략한 설명 : 토지이용계획 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @param retryCount
	/// @param model
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("searchUsePlan.do")
	public String searchUsePlan(String pnu, Integer retryCount, String restrictYn, Model model) throws ClientProtocolException, IOException, JAXBException {
		KrasResponse usePlan = null;
				
		if(pnu != null && pnu.length() == 19) {
			List<NameValuePair> parameters = getKrasCommonParameter(pnu, "KRAS000026");
			parameters.add(new BasicNameValuePair("map_width", "325"));
			parameters.add(new BasicNameValuePair("map_height", "200"));
			parameters.add(new BasicNameValuePair("iss_scale", "500"));
			parameters.add(new BasicNameValuePair("restrict_yn", restrictYn));
			usePlan = krasCommonService.call(parameters, getTimeout(retryCount));
		}
		
		model.addAttribute("usePlan", usePlan);
		model.addAttribute("ucodes", getUCodes(usePlan));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn krasLandUsePlanCnfInfo
	/// @brief 함수 간략한 설명 : 토지이용규제 호출
	/// @remark
	/// - 함수의 상세 설명 : 축척 적용에 따른 도면과 범례 갱신에 사용
	/// @param krasRequestVO
	/// @param model
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("landUsePlanCnfInfo.do")
	public String krasLandUsePlanCnfInfo(KrasRequestDTO krasRequestVO, Integer retryCount, Model model) throws ClientProtocolException, IOException, JAXBException {
		KrasResponse usePlan = null;
		String pnu = krasRequestVO.getPnu();
		if(pnu != null && pnu.length() == 19) {
			List<NameValuePair> parameters = getKrasCommonParameter(pnu, "KRAS000026");
			parameters.add(new BasicNameValuePair("map_width", "325"));
			parameters.add(new BasicNameValuePair("map_height", "200"));
			parameters.add(new BasicNameValuePair("iss_scale", krasRequestVO.getScale()));
			usePlan = krasCommonService.call(parameters, getTimeout(retryCount));
		}
		model.addAttribute("usePlan", usePlan);
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn getKrasCommonParameter
	/// @brief 함수 간략한 설명 : KRAS 서비스를 호출하기 위한 공통 파라미터 호출
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @param connSvcId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected List<NameValuePair> getKrasCommonParameter(String pnu, String connSvcId) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("adm_sect_cd", pnu.substring(0, 5)));
		parameters.add(new BasicNameValuePair("land_loc_cd", pnu.substring(5, 10)));
		parameters.add(new BasicNameValuePair("ledg_gbn", pnu.substring(10, 11)));
		parameters.add(new BasicNameValuePair("bobn", String.valueOf(Integer.parseInt(pnu.substring(11, 15)))));
		parameters.add(new BasicNameValuePair("bubn", String.valueOf(Integer.parseInt(pnu.substring(15, 19)))));
		parameters.add(new BasicNameValuePair("conn_svc_id", connSvcId));
		return parameters;
	}
	
	/////////////////////////////////////////////
	/// @fn callKrasCommonService
	/// @brief 함수 간략한 설명 : KRAS 공통 서비스 호출
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pnu
	/// @param connSvcId
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected KrasResponse callKrasCommonService(String pnu, String connSvcId, Integer retryCount) throws ClientProtocolException, IOException, JAXBException {
		List<NameValuePair> parameters = getKrasCommonParameter(pnu, connSvcId);
		return krasCommonService.call(parameters, getTimeout(retryCount));
	}

	/////////////////////////////////////////////
	/// @fn getTimeout
	/// @brief 함수 간략한 설명 : 타임아웃 시간 반환 (첫번째 시도 시 10초. 두번째 시도 시 60초);
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param retryCount
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Integer getTimeout(Integer retryCount) {
		return retryCount==0?10:retryCount;
	}

	/////////////////////////////////////////////
	/// @fn searchBldgHdsInfo
	/// @brief 함수 간략한 설명 : 총괄 or 집합 or 일반 건축물 정보
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param krasRequestVO
	/// @param model
	/// @return
	/// @throws ClientProtocolException
	/// @throws IOException
	/// @throws JAXBException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("searchBldgHdsInfoJson.do")
	public String searchBldgHdsInfo(KrasRequestDTO krasRequestVO, Integer retryCount, Model model) throws ClientProtocolException, IOException, JAXBException {

		KrasResponse krasResponse = null;
		String pnu = krasRequestVO.getPnu();
		String connSvcId = null;

		if(StringUtils.equals("1", krasRequestVO.getBldgKindCd())) {
			connSvcId = "KRAS000017";
		}
		else if(StringUtils.equals("2", krasRequestVO.getBldgKindCd())) {
			connSvcId = "KRAS000014";
		}
		else if(StringUtils.equals("3", krasRequestVO.getBldgKindCd())) {
			connSvcId = "KRAS000015";
		}
		
		if(pnu != null && pnu.length() == 19 && connSvcId != null) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("conn_svc_id", connSvcId));
			parameters.add(new BasicNameValuePair("adm_sect_cd", pnu.substring(0, 5)));
			parameters.add(new BasicNameValuePair("land_loc_cd", pnu.substring(5, 10)));
			parameters.add(new BasicNameValuePair("ledg_gbn", pnu.substring(10, 11)));
			parameters.add(new BasicNameValuePair("bobn", String.valueOf(Integer.parseInt(pnu.substring(11, 15)))));
			parameters.add(new BasicNameValuePair("bubn", String.valueOf(Integer.parseInt(pnu.substring(15, 19)))));
			if(!StringUtils.equals("1", krasRequestVO.getBldgKindCd())) {
				parameters.add(new BasicNameValuePair("bldg_gbn_no", krasRequestVO.getBldgGbnNo()));
			}
			krasResponse = krasCommonService.call(parameters, getTimeout(retryCount));
		}
		model.addAttribute("data", krasResponse);
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn getUCodes
	/// @brief 함수 간략한 설명 : 행위제한구역 코드 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param usePlan
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private List<KwsDomnCode> getUCodes(KrasResponse usePlan) {
		List<KwsDomnCode> ucodes = new ArrayList<KwsDomnCode>();
		
		// 행위제한구역 구역코드 목록 반환
		List<String> codeValues = new ArrayList<String>();
		if(usePlan != null && usePlan.getBody() != null && usePlan.getBody().getData() != null) {
			LandUsePlanCnfInfoSet landUsePlanCnfInfoSet = (LandUsePlanCnfInfoSet) usePlan.getBody().getData();
			if(landUsePlanCnfInfoSet != null && landUsePlanCnfInfoSet.getLandUsePlanRestrictList() != null) {
				for(LandUsePlanRestrict landUsePlanRestrict : landUsePlanCnfInfoSet.getLandUsePlanRestrictList()) {
					String lawLevel = landUsePlanRestrict.getLawLevel();
					String codeValue = landUsePlanRestrict.getUcode();
					if(StringUtils.equals("0", lawLevel) && !codeValues.contains(codeValue)) {
						codeValues.add(codeValue);
					}
				}
			}
		}
		
		// 용도지역지구구분 코드 목록
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setDomnId("KWS-9900");
		List<KwsDomnCode> codes = domnCodeService.listDomnCode(kwsDomnCode);
		
		for(String codeId : codeValues) {
			KwsDomnCode ucode = null;
			for(KwsDomnCode codeVO : codes) {
				if(StringUtils.equals(codeId, codeVO.getCodeId())) {
					ucode = codeVO;
					ucodes.add(ucode);
					break;
				}
			}
			// 코드 테이블에 없는 경우 코드값으로 명칭까지 사용
			if(ucode == null) {
				ucode = new KwsDomnCode();
				ucode.setCodeId(codeId);
				ucode.setCodeNm(codeId);
				ucodes.add(ucode);
			}
		}
		
		return ucodes;
	}
	
}
