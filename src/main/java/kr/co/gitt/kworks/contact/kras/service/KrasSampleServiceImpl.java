package kr.co.gitt.kworks.contact.kras.service;

import java.io.IOException;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import kr.co.gitt.kworks.contact.kras.model.BldgDongInfo;
import kr.co.gitt.kworks.contact.kras.model.BldgDongList;
import kr.co.gitt.kworks.contact.kras.model.BldgHdsInfo;
import kr.co.gitt.kworks.contact.kras.model.BldgHdsInfoList;
import kr.co.gitt.kworks.contact.kras.model.BldgLedgGenHdsInfo;
import kr.co.gitt.kworks.contact.kras.model.ChgData;
import kr.co.gitt.kworks.contact.kras.model.FloorInfo;
import kr.co.gitt.kworks.contact.kras.model.Jiga;
import kr.co.gitt.kworks.contact.kras.model.JigaList;
import kr.co.gitt.kworks.contact.kras.model.KrasBody;
import kr.co.gitt.kworks.contact.kras.model.KrasHeader;
import kr.co.gitt.kworks.contact.kras.model.KrasResponse;
import kr.co.gitt.kworks.contact.kras.model.LandInfo;
import kr.co.gitt.kworks.contact.kras.model.LandMovHist;
import kr.co.gitt.kworks.contact.kras.model.LandMovHistSet;
import kr.co.gitt.kworks.contact.kras.model.LandUsePlanCnfInfoBase;
import kr.co.gitt.kworks.contact.kras.model.LandUsePlanCnfInfoSet;
import kr.co.gitt.kworks.contact.kras.model.LandUsePlanRestrict;
import kr.co.gitt.kworks.contact.kras.model.Legend;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class KrasSampleServiceImpl
/// kr.co.gitt.kworks.cntc.service.kras \n
///   ㄴ KrasSampleServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 2:00:29 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 부동산 종합공부 연계 샘플 서비스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("krasCommonService")
@Profile({"gds_dev", "ss_dev", "gn_dev", "dh_dev", "gc_dev", "sc_dev", "yy_dev", "yg_dev", "gds_oper", "sunchang_dev", "hc_dev", "gs_dev","is_dev","mj_dev"})
public class KrasSampleServiceImpl implements KrasService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	/// 메세지 소스
	@Resource(name="messageSource")
	private MessageSource messageSource;

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kworks.itf.service.KrasCommonService#call(java.util.List)
	/////////////////////////////////////////////
	@Override
	public KrasResponse call(List<NameValuePair> parameters, Integer timeout) throws ClientProtocolException, IOException, JAXBException {
		KrasResponse krasResponse = new KrasResponse();
		
		krasResponse.setHeader(createKrasHeader());
		krasResponse.setBody(createKrasBody(parameters));
		
		return krasResponse;
	}
	
	/////////////////////////////////////////////
	/// @fn createKrasHeader
	/// @brief 함수 간략한 설명 : 연계 헤더 정보 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private KrasHeader createKrasHeader() {
		KrasHeader krasHeader = new KrasHeader();
		krasHeader.setCode("0000");
		krasHeader.setMessage("SUCCESS");
		return krasHeader;
	}
	
	/////////////////////////////////////////////
	/// @fn createKrasBody
	/// @brief 함수 간략한 설명 : 연계 정보 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param parameters
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private KrasBody createKrasBody(List<NameValuePair> parameters) {
		KrasBody krasBody = new KrasBody();
		
		String connSvcId = null;
		String restrictYn = null;
		for(NameValuePair nameValuePair : parameters) {
			if(StringUtils.equals("conn_svc_id", nameValuePair.getName())) {
				connSvcId = nameValuePair.getValue();
			}
			if(StringUtils.equals("restrict_yn", nameValuePair.getName())) {
				restrictYn = nameValuePair.getValue();
			}
		}
		
		if(StringUtils.equals("KRAS000002", connSvcId)) {
			krasBody.setData(createLandInfo());
		}
		else if(StringUtils.equals("KRAS000006", connSvcId)) {
			krasBody.setData(createLandMovHists());
		}
		else if(StringUtils.equals("KRAS000011", connSvcId)) {
			krasBody.setData(createJigas());
		}
		else if(StringUtils.equals("KRAS000014", connSvcId)) {
			krasBody.setData(createBldgHdsInfoList());
		}
		else if(StringUtils.equals("KRAS000015", connSvcId)) {
			krasBody.setData(createBldgHdsInfoList());
		}
		else if(StringUtils.equals("KRAS000017", connSvcId)) {
			krasBody.setData(createBldgLedgGenHdsInfo());
		}
		else if(StringUtils.equals("KRAS000026", connSvcId)) {
			krasBody.setData(createLandUsePlanCnfInfoSet(restrictYn));
		}
		else if(StringUtils.equals("KRAS000102", connSvcId)) {
			krasBody.setData(createBldgDongList());
		}
		else {
			logger.warn("지원되지 않는 서비스 입니다.");
		}
		
		return krasBody;
	}

	/////////////////////////////////////////////
	/// @fn createLandInfo
	/// @brief 함수 간략한 설명 : 토지(임야) 대장 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createLandInfo() {
		LandInfo landInfo = new LandInfo();
		
		landInfo.setAdmSectCd("27170");
		landInfo.setLandLocCd("10200");
		landInfo.setLedgGbn("1");
		landInfo.setBobn("0400");
		landInfo.setBubn("0028");
		landInfo.setJimok("08");
		landInfo.setJimokNm("대");
		landInfo.setParea(152);
		landInfo.setGrd("227");
		landInfo.setGrdYmd("");
		landInfo.setLandMovRsnCd("50");
		landInfo.setLandMovRsnCdNm("대구직할시 서구 에서 행정구역명칭변경");
		landInfo.setLandMovYmd("");
		landInfo.setLedgCntrstCnfGbn("1");
		landInfo.setBizActNtcGbn("0");
		landInfo.setMapGbn("도해");
		landInfo.setLandLastHistOdrno("02");
		landInfo.setOwnRgtLastHistOdrno("0005");
		landInfo.setOwnerNm("소유자");
		landInfo.setDregno("");
		landInfo.setOwnGbn("00");	// 02, 04, 05 제외하고 소유지명 보안처리 필요
		landInfo.setOwnGbnNm("");
		landInfo.setShrCnt(0);
		landInfo.setOwnerAddr("");
		landInfo.setOwnRgtChgRsnCd("");
		landInfo.setOwnRgtChgRsnCdNm("");
		landInfo.setOwndymd("");
		landInfo.setScale("12");
		landInfo.setScaleNm("1:1200");
		landInfo.setDoho("009");
		landInfo.setJigaBaseMon("2013-01");
		landInfo.setPannJiga(788000);
		landInfo.setLastJibn("34430000");
		landInfo.setLastBu("40");
		landInfo.setLastbobn("3443");
		landInfo.setLastBubn("0");
		landInfo.setLandMovChrgManId("22006900");
		landInfo.setOwnRgtChgChrgManId("62121300");
		landInfo.setBldgGbnNo(0L);
		landInfo.setLandMoveRellJibn("");
		
		return landInfo;
	}
	
	/////////////////////////////////////////////
	/// @fn createLandMovHists
	/// @brief 함수 간략한 설명 : 토지이동연혁
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createLandMovHists() {
		LandMovHistSet landMovHistSet = new LandMovHistSet();
		
		List<LandMovHist> landMovHists = new ArrayList<LandMovHist>();
		LandMovHist landMovHist1 = new LandMovHist();
		landMovHist1.setLandHistOdrno("02");
		landMovHist1.setDymd("1991-12-01");
		landMovHist1.setJimok("03");
		landMovHist1.setParea("92458");
		landMovHist1.setLandMovRsnCdNm("행정구역명칭변경");
		landMovHists.add(landMovHist1);

		LandMovHist landMovHist2 = new LandMovHist();
		landMovHist2.setLandHistOdrno("01");
		landMovHist2.setDymd("1995-01-01");
		landMovHist2.setJimok("01");
		landMovHist2.setParea("42235");
		landMovHist2.setLandMovRsnCdNm("충청남도 서산군 대산읍에서 행정구역변경");
		landMovHists.add(landMovHist2);
		
		landMovHistSet.setLandMovHists(landMovHists);
		
		return landMovHistSet;
	}
	
	/////////////////////////////////////////////
	/// @fn createJigas
	/// @brief 함수 간략한 설명 : 개별공시지가 확인서 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createJigas() {
		JigaList jigaList = new JigaList();
		
		List<Jiga> jigas = new ArrayList<Jiga>();
		Jiga jiga1 = new Jiga();
		jiga1.setBaseYear("2013");
		jiga1.setBaseMon("01");
		jiga1.setPannJiga("701000");
		jiga1.setPannYmd("20130101");
		jiga1.setRemark("");
		jigas.add(jiga1);
		
		Jiga jiga2 = new Jiga();
		jiga2.setBaseYear("2012");
		jiga2.setBaseMon("01");
		jiga2.setPannJiga("676000");
		jiga2.setPannYmd("20120101");
		jiga2.setRemark("");
		jigas.add(jiga2);
		
		jigaList.setJigas(jigas);
		
		return jigaList;
	}
	
	/////////////////////////////////////////////
	/// @fn createBldgHdsInfo
	/// @brief 함수 간략한 설명 : 일반건축물 또는 집합건축물(표제부) 목록 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createBldgHdsInfoList() {
		BldgHdsInfoList bldgHdsInfoList = new BldgHdsInfoList();
		bldgHdsInfoList.setBldgHdsInfo(createBldgHdsInfo());
		return bldgHdsInfoList;
	}

	/////////////////////////////////////////////
	/// @fn createBldgHdsInfo
	/// @brief 함수 간략한 설명 : '일반건축물' 또는 '집합건축물(표제부)' 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private BldgHdsInfo createBldgHdsInfo() {
		BldgHdsInfo bldgHdsInfo = new BldgHdsInfo();
		
		bldgHdsInfo.setBldgGbnNo("17864");
		bldgHdsInfo.setSpcNm("");
		bldgHdsInfo.setBlock("");
		bldgHdsInfo.setLot("");
		bldgHdsInfo.setBldgNm("한신 휴 플러스 아파트");
		bldgHdsInfo.setCmplGbn("0");
		bldgHdsInfo.setLegaYn("0");
		bldgHdsInfo.setVioBldgYn("0");
		bldgHdsInfo.setLandCnt(0);
		bldgHdsInfo.setEtcWrtItem("");
		bldgHdsInfo.setDong("101동");
		bldgHdsInfo.setMainSubGbnNm("주건축물");
		bldgHdsInfo.setMainSubSeqno(1);
		bldgHdsInfo.setStruNm("철근콘크리트구조");
		bldgHdsInfo.setEtcStru("철근콘크리트구조");
		bldgHdsInfo.setRoofNm("(철근)콘크리트");
		bldgHdsInfo.setEtcRoof("(철근)콘크리트");
		bldgHdsInfo.setLarea(0.0);
		bldgHdsInfo.setFmlyCnt(0);
		bldgHdsInfo.setHeadCnt(189);
		bldgHdsInfo.setHoCnt(0);
		bldgHdsInfo.setBarea(1465.7891);
		bldgHdsInfo.setMainUseNm("공동주택");
		bldgHdsInfo.setEtcUse("아파트");
		bldgHdsInfo.setGarea(20879.7479);
		bldgHdsInfo.setTotDongGarea(0.0);
		bldgHdsInfo.setDongBlr(0.0);
		bldgHdsInfo.setFsiCalcGarea(20879.7479);
		bldgHdsInfo.setFsi(148.241021654);
		bldgHdsInfo.setHgt(51.0);
		bldgHdsInfo.setSubBldgCnt(0);
		bldgHdsInfo.setSubBldgArea(0.0);
		bldgHdsInfo.setBldgKindCd("3");
		
		bldgHdsInfo.setFloorInfoList(createFloorInfoList());
		bldgHdsInfo.setChgDataList(createChgDataList());

		return bldgHdsInfo;
	}

	/////////////////////////////////////////////
	/// @fn createFloorInfoList
	/// @brief 함수 간략한 설명 : 층별현황 목록 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private List<FloorInfo> createFloorInfoList() {
		List<FloorInfo> floorInfoList = new ArrayList<FloorInfo>();
		
		FloorInfo floorInfo = new FloorInfo();
		floorInfo.setFlr("3층");
		floorInfo.setEtcStru("철근콘크리트구조");
		floorInfo.setMainUseNm("아파트");
		floorInfo.setEtcUse("아파트");
		floorInfo.setBtmArea(1315.1775);
		floorInfo.setFlrGbnCd("지상");
		floorInfo.setMainSubGbnNm("주건축물");
		floorInfo.setMainSubSeqno(1);
		floorInfoList.add(floorInfo);
		
		return floorInfoList;
	}

	/////////////////////////////////////////////
	/// @fn createChgDataList
	/// @brief 함수 간략한 설명 : 변동현황 목록 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private List<ChgData> createChgDataList() {
		List<ChgData> chgDataList = new ArrayList<ChgData>();
		
		ChgData chgData = new ChgData();
		chgData.setChgRsnNm("신축");
		chgData.setChgCntn("신규작성(신축 97-1)");
		chgData.setChgYmd("2015-12-12");
		chgData.setAdjYmd("2015-12-12");
		chgDataList.add(chgData);
		
		return chgDataList;
	}

	/////////////////////////////////////////////
	/// @fn createBldgLedgGenHdsInfo
	/// @brief 함수 간략한 설명 : '건축물대장(총괄 표제부)' 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createBldgLedgGenHdsInfo() {
		BldgLedgGenHdsInfo bldgLedgGenHdsInfo = new BldgLedgGenHdsInfo();
		
		bldgLedgGenHdsInfo.setBldgGbnNo("204");
		bldgLedgGenHdsInfo.setAdmSectCd("27170");
		bldgLedgGenHdsInfo.setLandLocCd("10200");
		bldgLedgGenHdsInfo.setLedgGbn("1");
		bldgLedgGenHdsInfo.setBobn("0315");
		bldgLedgGenHdsInfo.setBubn("0000");
		bldgLedgGenHdsInfo.setBldgNm("한신휴플러스 아파트");
		bldgLedgGenHdsInfo.setLandCnt(0);
		bldgLedgGenHdsInfo.setEtcWrtItem("");
		bldgLedgGenHdsInfo.setLegaYn("0");
		bldgLedgGenHdsInfo.setUpperBldgNo(0L);
		bldgLedgGenHdsInfo.setVioBldgYn("0");
		bldgLedgGenHdsInfo.setSpcNm("");
		bldgLedgGenHdsInfo.setBlock("");
		bldgLedgGenHdsInfo.setLot("");
		bldgLedgGenHdsInfo.setCmplGbn("0");
		bldgLedgGenHdsInfo.setSpcItem("");
		bldgLedgGenHdsInfo.setLarea(14085.0);
		bldgLedgGenHdsInfo.setBarea(3103.6891);
		bldgLedgGenHdsInfo.setBlr(22.04);
		bldgLedgGenHdsInfo.setGarea(45163.647);
		bldgLedgGenHdsInfo.setFsi(271.94);
		bldgLedgGenHdsInfo.setFsiCalcGarea(38302.648);
		bldgLedgGenHdsInfo.setMainUseNm("");
		bldgLedgGenHdsInfo.setEtcUse("공동주택,아파트");
		bldgLedgGenHdsInfo.setTotFmlyCnt(0);
		bldgLedgGenHdsInfo.setTotHehdCnt(335);
		bldgLedgGenHdsInfo.setTotHoCnt(0);
		bldgLedgGenHdsInfo.setTotMainBldgCnt(4);
		bldgLedgGenHdsInfo.setSubBldgCnt(7);
		bldgLedgGenHdsInfo.setSubBldgArea(6899.25);
		bldgLedgGenHdsInfo.setTotParkCnt(380);
		bldgLedgGenHdsInfo.setPermYmd("1977-12-23");
		bldgLedgGenHdsInfo.setBgconsYmd("2003-10-01");
		bldgLedgGenHdsInfo.setUseAprvYmd("2005-12-12");
		bldgLedgGenHdsInfo.setPermymdnum("1997-3430014-2101-1");
		
		return bldgLedgGenHdsInfo;
	}

	/////////////////////////////////////////////
	/// @fn createLandUsePlanCnfInfoSet
	/// @brief 함수 간략한 설명 : '토지이용계획확인서' 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createLandUsePlanCnfInfoSet(String restrictYn) {
		LandUsePlanCnfInfoSet landUsePlanCnfInfoSet = new LandUsePlanCnfInfoSet();
		landUsePlanCnfInfoSet.setLandUsePlanCnfInfoBase(createLandUsePlanCnfInfoBase());
		landUsePlanCnfInfoSet.setLegendList(createLegendList());
		if(StringUtils.equals("Y", restrictYn)) {
			landUsePlanCnfInfoSet.setLandUsePlanRestrictList(createLandUsePlanRestrictList());	
		}
		return landUsePlanCnfInfoSet;
	}
	
	/////////////////////////////////////////////
	/// @fn createLandUsePlanCnfInfoBase
	/// @brief 함수 간략한 설명 : '토지이용계획확인서' 기본 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private LandUsePlanCnfInfoBase createLandUsePlanCnfInfoBase() {
		LandUsePlanCnfInfoBase landUsePlanCnfInfoBase = new LandUsePlanCnfInfoBase();
		
		landUsePlanCnfInfoBase.setIssScale("5200");
		landUsePlanCnfInfoBase.setIssNo("0000");
		landUsePlanCnfInfoBase.setSeqNo("0000");
		landUsePlanCnfInfoBase.setAdmSectHead("대구광역시 서구청장");
		landUsePlanCnfInfoBase.setBchk("1");
		landUsePlanCnfInfoBase.setLandLocNm("대구광역시 서구 평리동");
		landUsePlanCnfInfoBase.setJibn("0404-0002");
		landUsePlanCnfInfoBase.setJimok("09");
		landUsePlanCnfInfoBase.setJimokNm("");
		landUsePlanCnfInfoBase.setParea("37389");
		landUsePlanCnfInfoBase.setUselawA("도시지역( ), 경관지구미분류(2014-08-19)");
		landUsePlanCnfInfoBase.setUselawB("일반산업단지{산업입지 및 개발에 관한 법률}");
		landUsePlanCnfInfoBase.setUselawC("[해당없음]");
		landUsePlanCnfInfoBase.setUselawD("[해당없음]");
		landUsePlanCnfInfoBase.setImg("iVBORw0KGgoAAAANSUhEUgAAAUUAAADICAIAAADwT2S4AAAFdUlEQVR42u3dW27bOhRAUc1/ChlsWqCoY0sk9YgonUOtYH20uQkuSnGHejD29P31xemmaTIIPUZ1gIHt+k8w7fSsZD0j6asyHm8k+/2LzDk9y1jPSNp5dby5YcLp2YKsZ/QsYz07bDzqvPriuWG26dmCrGckLeN4c8MQ61nJCebGxpE00NF7Xh7F2meK/6N/n7y4KBmfOzdeX6bn3EkvD2TjD8WcLu5ZxufOjdnh0/NDe754fbYg95sbryPofDt90ht7nh3py3qW8TUTY/sgOxIJluj3w7l3xe6UnIxvuYpeHXaHJM1dscM9n7g+W5DvOuXeeAntwORIem/P7x9n3WOX8S1zY/ksQ89Zey6GtOUzZ63PMg7Vs/Ptp29aPtazBTnaU6v3U63Wj29Daa+YBTnRVXT7B7Qjp2cLcpqNJavnXI7fo5OWcbqraNfPenZePc5VtP0kerYgP2VuOK6PSFrGg23/1PNDe5axnkmftAX5mUk73kP1LOOn7ws2fGP0rOTHzo2PR9OGL3XSMjY39DxCzzK2V6zwmznGLlfSFmQTo/HT3LTI0bOMzY3Gdu6fFdvAxX8yIWNzo/0jXs/RD5sFme1LtPPtoMdMxuxaouczx6gFOWwOB4cvx36mkFGLcNgcCHZv1S69ApFpBIkvoedvpWDIYJy9YsYL9AzoGei3j9BggZ6BcEnrGfQM6BnouP3TMIGeAT0D3ZLWM+gZiJe0nkHPgJ6BfknrGfQMxEtaz6BnQM9Av6T1DHoG4iWtZ9AzoGegX9J6Bj0Degb6Ja1n0DMQL2k9g54BPQP9ktYz6BmIl7SeQc9AwKQNAeiZDP6efy0ZFj2TsuH2FxgrPRM95r3lGzQ9kyXmlbc7+kx6+v+x5XvRMyc9lvj8mMU8lf5c+JbXf/23FaEeNnqm+2bd2so8Vdpu/fWz3mlROHrmkp6XLwp3oOfFCm991jNXn29/L6qbSivtxr9+u37WM3cu1JXFudjkVLpO/tUdcvRMvxvay4yLPddW4NeC73xbz9xzsv0Kr3guXQu7eservXqjZ27cPbL+/FnPeiZUzNPaXbHiZ6rX1TLWM3f1PB39w+ysW896JnHP1mc9k/J8W896Zpz1uf27FvOnU3rWM8F7tj7rmZF7nmqPr/WsZ+5NevX6eevzKjHrmbD7SbbuA9eznhmjZ7+PoWdG7FnMemacpPWsZwZJWsx6ZpCkxaxnBklazHpmkKTFrGfiJr33LTIMmp5JUPXq+1eJWc+kDFvDegb0DOgZ0DOgZ9AzoGdAz4Ce4YE9e88x0DMQ8nxb0qBnIOT9MEmDngE9A12fP0sa9AzoGei631PSoGcgXs+SBj0Dega69ixp0DOgZ6Brz5IGPQN391xL9/3z7by3x//6yqny4XjAOT036qp9TbH8Rqtn/VwA1nvetT7XvrHxXXqG23pup35uz2KGO3tenmM3mtczxOp511Vxo+dp7cNRgS7Xz8fuY9WuvdvX4cClPW8MUs8Q9P5240J673KtZ7j5+fOuntv3uvUM9+wPq90Y+80dLD3DDfu3JQd6BsL3LGnQM6BnoGvPkgY9A3oGuvYsadAzEKbnxquFATl6rr3un54hWc/LX1re+BqdQNyeZzE75YaUPTd+EVLPMML9bT3DIPu33eWG3D27yw2D9Ny4yy1pyNqzu9wwQs/Fu9xOuWHA+9uShvQ9W6Iha8/t19yWNKRfn90VAz0DgXuWNCTuefkQS8+Qr+daxnqGlD23d4MCWXu2RMMIPRf3gUoa8l0/L9/eXc+QrOfZ1bL1GdI/r/K6YjDs/TA9w2j7w/QMA/YsadAzELJnSYOeAT0DXXuWNOgZ0DPQtWdJg56BkD1LGvQMHPcHCIPXIkywXQQAAAAASUVORK5CYII=");
		
		return landUsePlanCnfInfoBase;
	}

	/////////////////////////////////////////////
	/// @fn createLegendList
	/// @brief 함수 간략한 설명 : 범례 목록 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private List<Legend> createLegendList() {
		List<Legend> legendList = new ArrayList<Legend>();
		
		Legend legend1 = new Legend();
		legend1.setImg("iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAIAAADZF8uwAAAAFUlEQVR42mNgIBL8xwtGFVFfEUEAADTBKuRyrj9sAAAAAElFTkSuQmCC");
		legend1.setText("도시지역");
		legendList.add(legend1);
		
		Legend legend2 = new Legend();
		legend2.setImg("iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAIAAADZF8uwAAAAF0lEQVR42mP4z8BAEIEp/GBUEfUVEUIAZKdWuGXrgWsAAAAASUVORK5CYII=");
		legend2.setText("방화지구");
		legendList.add(legend2);
		
		Legend legend3 = new Legend();
		legend3.setImg("iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAIAAADZF8uwAAAAGUlEQVR42mNYNaOUIGIA4v94wagi6isiiACwRHZY6htIOAAAAABJRU5ErkJggg==");
		legend3.setText("중심지미관지구");
		legendList.add(legend3);
		
		Legend legend4 = new Legend();
		legend4.setImg("iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAIAAADZF8uwAAAAFElEQVR42mP4P3s2QcQwqmgwKgIA5Hk94KBAuBcAAAAASUVORK5CYII=");
		legend4.setText("법정동");
		legendList.add(legend4);

		return legendList;
	}
	
	/////////////////////////////////////////////
	/// @fn createLandUsePlanRestrictList
	/// @brief 함수 간략한 설명 : 행위제한 내역 목록 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private List<LandUsePlanRestrict> createLandUsePlanRestrictList() {
		List<LandUsePlanRestrict> landUsePlanRestrictList = new ArrayList<LandUsePlanRestrict>();
		
		LandUsePlanRestrict landUsePlanRestrict1 = new LandUsePlanRestrict();
		landUsePlanRestrict1.setUseRestrict("");
		landUsePlanRestrict1.setUcode("UQA001");
		landUsePlanRestrict1.setLawContents("국토의 계획 및 이용에 관한 법률 제56조");
		landUsePlanRestrict1.setLawLevel("0");
		landUsePlanRestrict1.setLawFullCd("");
		landUsePlanRestrictList.add(landUsePlanRestrict1);
		
		LandUsePlanRestrict landUsePlanRestrict2 = new LandUsePlanRestrict();
		landUsePlanRestrict2.setUseRestrict("");
		landUsePlanRestrict2.setUcode("UQA220");
		landUsePlanRestrict2.setLawContents("국토의 계획 및 이용에 관한 법률 시행령 별표 9");
		landUsePlanRestrict2.setLawLevel("0");
		landUsePlanRestrict2.setLawFullCd("");
		landUsePlanRestrictList.add(landUsePlanRestrict2);
		
		LandUsePlanRestrict landUsePlanRestrict3 = new LandUsePlanRestrict();
		landUsePlanRestrict3.setUseRestrict("");
		landUsePlanRestrict3.setUcode("UQA220");
		landUsePlanRestrict3.setLawContents("동해시 도시계획에 관한 조례 별표 8");
		landUsePlanRestrict3.setLawLevel("0");
		landUsePlanRestrict3.setLawFullCd("");
		landUsePlanRestrictList.add(landUsePlanRestrict3);
		
		LandUsePlanRestrict landUsePlanRestrict4 = new LandUsePlanRestrict();
		landUsePlanRestrict4.setUseRestrict("");
		landUsePlanRestrict4.setUcode("UQA220");
		landUsePlanRestrict4.setLawContents("동해시 도시계획에 관한 조례 제31조");
		landUsePlanRestrict4.setLawLevel("0");
		landUsePlanRestrict4.setLawFullCd("");
		landUsePlanRestrictList.add(landUsePlanRestrict4);
		
		LandUsePlanRestrict landUsePlanRestrict5 = new LandUsePlanRestrict();
		landUsePlanRestrict5.setUseRestrict("");
		landUsePlanRestrict5.setUcode("UQI100");
		landUsePlanRestrict5.setLawContents("국토의 계획 및 이용에 관한 법률 시행령 제82조");
		landUsePlanRestrict5.setLawLevel("0");
		landUsePlanRestrict5.setLawFullCd("");
		landUsePlanRestrictList.add(landUsePlanRestrict5);
		
		return landUsePlanRestrictList;
	}

	/////////////////////////////////////////////
	/// @fn createBldgDongList
	/// @brief 함수 간략한 설명 : 건물 동 목록 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private Object createBldgDongList() {
		BldgDongList bldgDongList = new BldgDongList();
		
		List<BldgDongInfo> bldgDongInfos = new ArrayList<BldgDongInfo>();
		
		BldgDongInfo bldgDongInfo1 = new BldgDongInfo();
		bldgDongInfo1.setAdmSectCd("27170");
		bldgDongInfo1.setLandLocCd("10200");
		bldgDongInfo1.setLedgGbn("1");
		bldgDongInfo1.setBobn("0504");
		bldgDongInfo1.setBubn("0047");
		bldgDongInfo1.setBldgKindNm("총괄");
		bldgDongInfo1.setBldgKindCd("1");
		bldgDongInfo1.setBldgGbnNo("1000");
		bldgDongInfo1.setBldgNm("");
		bldgDongInfo1.setDongNm("");
		bldgDongInfo1.setBmapYn("");
		bldgDongInfo1.setGarea("");
		bldgDongInfos.add(bldgDongInfo1);
		
		BldgDongInfo bldgDongInfo2 = new BldgDongInfo();
		bldgDongInfo2.setAdmSectCd("27170");
		bldgDongInfo2.setLandLocCd("10200");
		bldgDongInfo2.setLedgGbn("1");
		bldgDongInfo2.setBobn("0504");
		bldgDongInfo2.setBubn("0047");
		bldgDongInfo2.setBldgKindNm("일반");
		bldgDongInfo2.setBldgKindCd("2");
		bldgDongInfo2.setBldgGbnNo("100000");
		bldgDongInfo2.setBldgNm("");
		bldgDongInfo2.setDongNm("");
		bldgDongInfo2.setBmapYn("");
		bldgDongInfo2.setGarea("");
		bldgDongInfos.add(bldgDongInfo2);
		
		bldgDongList.setBldgDongInfos(bldgDongInfos);
		
		return bldgDongList;
	}

}
