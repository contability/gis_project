package kr.co.gitt.kworks.projects.gn.service.cityPark;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.dto.CityParkChangeDetailsResultDTO;
import kr.co.gitt.kworks.projects.gn.dto.CityParkChangeDetailsSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.CityParkCivilAppealSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.CityParkSearchDTO;
import kr.co.gitt.kworks.projects.gn.mappers.CityParkChangeDetailsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.CtyParkAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.CtyPksdAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.CtyRegisterInquiryMapper;
import kr.co.gitt.kworks.projects.gn.mappers.CtyRserMaMapper;
import kr.co.gitt.kworks.projects.gn.model.CtyParkAs;
import kr.co.gitt.kworks.projects.gn.model.CtyPksdAs;
import kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry;
import kr.co.gitt.kworks.projects.gn.model.CtyRserMa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class CityParkServiceImpl
/// kr.co.gitt.kworks.projects.gn.service.cityPark \n
///   ㄴ CityParkServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 26. 오후 6:25:10 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("cityParkService")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class CityParkServiceImpl implements CityParkService {
	
	/// 도시공원 맵퍼
	@Resource
	CtyParkAsMapper ctyParkAsMapper;
	
	/// 공원 구역 맵퍼
	@Resource
	CtyPksdAsMapper ctyPksdAsMapper;
	
	/// 도시 공원 변천내역 맵퍼
	@Resource
	CityParkChangeDetailsMapper cityParkChangeDetailsMapper;
	
	/// 도시 공원 민원관리 맵퍼
	@Resource
	CtyRserMaMapper ctyRserMaMapper;
	
	/// 도시 공원 대장조회 맵퍼 
	@Resource
	CtyRegisterInquiryMapper ctyRegisterInquiryMapper;
	
	/// 민원대장 아이디 생성 서비스
	@Resource
	EgovIdGnrService ctyRserMaIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityAllPark()
	/////////////////////////////////////////////
	@Override
	public List<CtyParkAs> listCityAllPark() {
		return ctyParkAsMapper.listAll();
	}
	
	/////////////////////////////////////////////
	/// @fn listCityPark
	/// @brief 함수 간략한 설명 : 도시공원 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cityParkSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyParkAs> listCityPark(CityParkSearchDTO cityParkSearchDTO) {
		return ctyParkAsMapper.list(cityParkSearchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityPark(kr.co.gitt.kworks.projects.gn.dto.CityParkSearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<CtyParkAs> listCityPark(CityParkSearchDTO cityParkSearchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = ctyParkAsMapper.listCount(cityParkSearchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listCityPark(cityParkSearchDTO);
		}
		else {
			return new ArrayList<CtyParkAs>();
		}
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkZoneScap(kr.co.gitt.kworks.projects.gn.model.CtyPksdAs)
	/////////////////////////////////////////////
	@Override
	public List<CtyPksdAs> listCityParkZoneScap(CtyPksdAs ctyPksdAs) {
		return ctyPksdAsMapper.listScap(ctyPksdAs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkZoneAthl(kr.co.gitt.kworks.projects.gn.model.CtyPksdAs)
	/////////////////////////////////////////////
	@Override
	public List<CtyPksdAs> listCityParkZoneAthl(CtyPksdAs ctyPksdAs) {
		return ctyPksdAsMapper.listAthl(ctyPksdAs);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkZonePlay(kr.co.gitt.kworks.projects.gn.model.CtyPksdAs)
	/////////////////////////////////////////////
	@Override
	public List<CtyPksdAs> listCityParkZonePlay(CtyPksdAs ctyPksdAs) {
		return ctyPksdAsMapper.listPlay(ctyPksdAs);
	}
	
	/////////////////////////////////////////////
	/// @fn listChangeDetails
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CityParkChangeDetailsResultDTO> listChangeDetails(CityParkChangeDetailsSearchDTO searchDTO) {
		return cityParkChangeDetailsMapper.list(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listChangeDetails(kr.co.gitt.kworks.projects.gn.dto.CityParkChangeDetailsSearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<CityParkChangeDetailsResultDTO> listChangeDetails(CityParkChangeDetailsSearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = cityParkChangeDetailsMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		if(totalRecordCount > 0) {
			return listChangeDetails(searchDTO);
		}
		else {
			return new ArrayList<CityParkChangeDetailsResultDTO>();
		}
	}

	/////////////////////////////////////////////
	/// @fn listCityParkCivilAppeal
	/// @brief 함수 간략한 설명 : 민원관리 대장 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<CtyRserMa> listCityParkCivilAppeal(CityParkCivilAppealSearchDTO searchDTO) {
		return ctyRserMaMapper.list(searchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkCivilAppeal(kr.co.gitt.kworks.projects.gn.dto.CityParkCivilAppealSearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<CtyRserMa> listCityParkCivilAppeal(CityParkCivilAppealSearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = ctyRserMaMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		if(totalRecordCount > 0) {
			return listCityParkCivilAppeal(searchDTO);
		}
		else {
			return new ArrayList<CtyRserMa>();
		}
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#addCivilAppeal(kr.co.gitt.kworks.projects.gn.model.CtyRserMa)
	/////////////////////////////////////////////
	@Override
	public Integer addCivilAppeal(CtyRserMa ctyRserMa) throws FdlException {
		Long ftrIdn = ctyRserMaIdGnrService.getNextLongId();
		ctyRserMa.setFtrIdn(ftrIdn);
		return ctyRserMaMapper.insert(ctyRserMa);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#modifyCivilAppeal(kr.co.gitt.kworks.projects.gn.model.CtyRserMa)
	/////////////////////////////////////////////
	@Override
	public Integer modifyCivilAppeal(CtyRserMa ctyRserMa) {
		return ctyRserMaMapper.update(ctyRserMa);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#removeCivilAppeal(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeCivilAppeal(Long ftrIdn) {
		return ctyRserMaMapper.delete(ftrIdn);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 운동시설 목록조회
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkAthl(kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry)
	/////////////////////////////////////////////
	@Override
	public List<CtyRegisterInquiry> listCityParkAthl(CtyRegisterInquiry ctyRegisterInquiry) {
		return ctyRegisterInquiryMapper.listAthl(ctyRegisterInquiry);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 유희시설 목록조회
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkPlay(kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry)
	/////////////////////////////////////////////
	@Override
	public List<CtyRegisterInquiry> listCityParkPlay(CtyRegisterInquiry ctyRegisterInquiry) {
		return ctyRegisterInquiryMapper.listPlay(ctyRegisterInquiry);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 휴게시설(점) 목록조회
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkRestPs(kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry)
	/////////////////////////////////////////////
	@Override
	public List<CtyRegisterInquiry> listCityParkRestPs(CtyRegisterInquiry ctyRegisterInquiry) {
		return ctyRegisterInquiryMapper.listRestPs(ctyRegisterInquiry);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 휴게시설(선) 목록조회
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkRestLs(kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry)
	/////////////////////////////////////////////
	@Override
	public List<CtyRegisterInquiry> listCityParkRestLs(CtyRegisterInquiry ctyRegisterInquiry) {
		return ctyRegisterInquiryMapper.listRestLs(ctyRegisterInquiry);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 휴게시설(면) 목록조회
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkRestAs(kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry)
	/////////////////////////////////////////////
	@Override
	public List<CtyRegisterInquiry> listCityParkRestAs(CtyRegisterInquiry ctyRegisterInquiry) {
		return ctyRegisterInquiryMapper.listRestAs(ctyRegisterInquiry);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 공원구역 목록조회
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService#listCityParkPksdAs(kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry)
	/////////////////////////////////////////////
	@Override
	public List<CtyRegisterInquiry> listCityParkPksdAs(
			CtyRegisterInquiry ctyRegisterInquiry) {
		return ctyRegisterInquiryMapper.listPksdAs(ctyRegisterInquiry);
	}
	
}
