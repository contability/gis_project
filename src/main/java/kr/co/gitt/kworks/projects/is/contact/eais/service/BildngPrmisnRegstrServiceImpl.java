package kr.co.gitt.kworks.projects.is.contact.eais.service;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.contact.eais.model.BildngPrmisnRegstr;
import kr.co.gitt.kworks.contact.eais.model.DjrBldRgst;
import kr.co.gitt.kworks.contact.eais.model.DjrChangItem;
import kr.co.gitt.kworks.contact.eais.model.DjrFlrouln;
import kr.co.gitt.kworks.contact.eais.model.DjrTitle;
import kr.co.gitt.kworks.contact.eais.service.BildngPrmisnRegstrService;
import kr.co.gitt.kworks.projects.is.contact.eais.mappers.BildngPrmisnRegstrMapper;
import kr.co.gitt.kworks.projects.is.contact.eais.mappers.DjrBldRgstMapper;
import kr.co.gitt.kworks.projects.is.contact.eais.mappers.DjrChangItemMapper;
import kr.co.gitt.kworks.projects.is.contact.eais.mappers.DjrFlroulnMapper;
import kr.co.gitt.kworks.projects.is.contact.eais.mappers.DjrTitleMapper;

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
@Profile({"is_dev", "is_oper"})
public class BildngPrmisnRegstrServiceImpl implements BildngPrmisnRegstrService {
	
	// 건축허가대장 맵퍼
	@Resource
	BildngPrmisnRegstrMapper bildngPrmisnRegstrMapper;
	
	// 건축물대장 맵퍼
	@Resource
	DjrBldRgstMapper djrBldRgstMapper;
	
	// 건축물대장_총괄표제부&건축물대장_표제부 맵퍼
	@Resource
	DjrTitleMapper djrTitleMapper;
	
	//건축물대장_변동_사항 맵퍼
	@Resource
	DjrChangItemMapper djrChangItemMapper;
	
	//건축물대장_층별개요 맵퍼
	@Resource
	DjrFlroulnMapper djrFlroulnMapper;
	
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
	
	//건축물대장 목록 조회
	@Override
	public List<DjrBldRgst> listDjrBldRgst(DjrBldRgst djrBldRgst) {
		String pnu = djrBldRgst.getPnu();
		
		djrBldRgst.setSigunguCd(pnu.substring(0,5));
		djrBldRgst.setBjdongCd(pnu.substring(5,10));
		djrBldRgst.setPlatGbCd(pnu.substring(10, 11));
		
		if(StringUtils.equals(djrBldRgst.getPlatGbCd(), "1")){
			djrBldRgst.setPlatGbCd("0");
		}else if(StringUtils.equals(djrBldRgst.getPlatGbCd(), "2")){
			djrBldRgst.setPlatGbCd("1");
		}
		
		djrBldRgst.setBun(pnu.substring(11,15));
		djrBldRgst.setJi(pnu.substring(15,19));
		
		return djrBldRgstMapper.listDjrBldRgst(djrBldRgst);
	}
	
	// 총괄표제부 or 표제부 단건 조회
	@Override
	public DjrTitle selecOnetDjrTitle(DjrBldRgst djrBldRgst) {
		
		int bldrgstPk = djrBldRgst.getBldrgstPk();
		String regstrKindCd = djrBldRgst.getRegstrKindCd();
		DjrTitle djrTitle = null;
		
		if(regstrKindCd.equals("1")){
			djrTitle = djrTitleMapper.selecOnetDjrRecapTitle(bldrgstPk);
		}else{
			djrTitle = djrTitleMapper.selectOneDjrTitle(bldrgstPk);
		}
		
		if(djrTitle!=null){
			List<DjrChangItem> djrChangItem = djrChangItemMapper.djrChangItemList(bldrgstPk);
			List<DjrFlrouln> djrFlrouln = djrFlroulnMapper.djrFlroulnList(bldrgstPk);
			
			djrTitle.setDjrChangItemList(djrChangItem);
			djrTitle.setDjFlroulnList(djrFlrouln);
		}
		
		
		return djrTitle;
	}
	
	//변동 현황
	@Override
	public List<DjrChangItem> djrChangItemList(int bldrgstPk) {
		return djrChangItemMapper.djrChangItemList(bldrgstPk);
	}
	
	// 층별 현황
	@Override
	public List<DjrFlrouln> djrFlroulnList(int bldrgstPk) {
		return djrFlroulnMapper.djrFlroulnList(bldrgstPk);
	}

}
