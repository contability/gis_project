package kr.co.gitt.kworks.projects.ss.contact.eais.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.contact.eais.model.BildngPrmisnRegstr;
import kr.co.gitt.kworks.contact.eais.model.DjrBldRgst;
import kr.co.gitt.kworks.contact.eais.model.DjrChangItem;
import kr.co.gitt.kworks.contact.eais.model.DjrFlrouln;
import kr.co.gitt.kworks.contact.eais.model.DjrTitle;
import kr.co.gitt.kworks.contact.eais.service.BildngPrmisnRegstrService;
import kr.co.gitt.kworks.projects.ss.contact.eais.mappers.BildngPrmisnRegstrMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class BildngPrmisnRegstrServiceImpl
/// kr.co.gitt.kworks.cntc.service.eais \n
///   ㄴ BildngPrmisnRegstrServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 1. 2. 오후 2:53:06 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 건축허가대장 서비스 구현 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("bildngPrmisnRegstrService")
@Profile({"ss_dev", "ss_oper"})
public class BildngPrmisnRegstrServiceImpl implements BildngPrmisnRegstrService {
	
	// 건축허가대장 맵퍼
	@Resource
	BildngPrmisnRegstrMapper bildngPrmisnRegstrMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.cntc.service.eais.BildngPrmisnRegstrService#listBildngPrmisnRegstr(kr.co.gitt.kworks.cntc.eais.model.BildngPrmisnRegstr)
	/////////////////////////////////////////////
	@Override
	public List<BildngPrmisnRegstr> listBildngPrmisnRegstr(BildngPrmisnRegstr bildngPrmisnRegstr) {
		String pnu = bildngPrmisnRegstr.getPnu();
		
		bildngPrmisnRegstr.setSigunguCd(pnu.substring(0, 5));
		bildngPrmisnRegstr.setBjdongCd(pnu.substring(5, 10));
		bildngPrmisnRegstr.setPlatGbCd(pnu.substring(10, 11));
		
		if(StringUtils.equals(bildngPrmisnRegstr.getPlatGbCd(), "1")){
			bildngPrmisnRegstr.setPlatGbCd("0");
		}
		else if(StringUtils.equals(bildngPrmisnRegstr.getPlatGbCd(), "2")){
			bildngPrmisnRegstr.setPlatGbCd("1");
		}
		
		bildngPrmisnRegstr.setBun(pnu.substring(11, 15));
		bildngPrmisnRegstr.setJi(pnu.substring(15, 19));
		
		return bildngPrmisnRegstrMapper.list(bildngPrmisnRegstr);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.cntc.service.eais.BildngPrmisnRegstrService#selectOneBildngPrmisnRegstr(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public BildngPrmisnRegstr selectOneBildngPrmisnRegstr(BildngPrmisnRegstr bildngPrmisnRegstr) {
		String pnu = bildngPrmisnRegstr.getPnu();
		
		bildngPrmisnRegstr.setSigunguCd(pnu.substring(0, 5));
		bildngPrmisnRegstr.setBjdongCd(pnu.substring(5, 10));
		bildngPrmisnRegstr.setPlatGbCd(pnu.substring(10, 11));
		
		if(StringUtils.equals(bildngPrmisnRegstr.getPlatGbCd(), "1")){
			bildngPrmisnRegstr.setPlatGbCd("0");
		}
		else if(StringUtils.equals(bildngPrmisnRegstr.getPlatGbCd(), "2")){
			bildngPrmisnRegstr.setPlatGbCd("1");
		}
		
		bildngPrmisnRegstr.setBun(pnu.substring(11, 15));
		bildngPrmisnRegstr.setJi(pnu.substring(15, 19));
		return bildngPrmisnRegstrMapper.selectOne(bildngPrmisnRegstr);
	}
	
	
	//아래는 순창에서만 사용중(건축물대장등)
	@Override
	public List<DjrBldRgst> listDjrBldRgst(DjrBldRgst djrBldRgst) {
		return null;
	}

	@Override
	public DjrTitle selecOnetDjrTitle(DjrBldRgst djrBldRgst) {
		return null;
	}

	@Override
	public List<DjrChangItem> djrChangItemList(int bldrgstPk) {
		return null;
	}

	@Override
	public List<DjrFlrouln> djrFlroulnList(int bldrgstPk) {
		return null;
	}
	
}
