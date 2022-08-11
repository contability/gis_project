package kr.co.gitt.kworks.projects.dh.service.beachErosion;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.dh.dto.BeachErosionRequestDTO;
import kr.co.gitt.kworks.projects.dh.mappers.ViewOclBersMaMapper;
import kr.co.gitt.kworks.projects.dh.model.ViewOclBersMa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class BeachErosionServiceImpl
/// kr.co.gitt.kworks.projects.dh.service.beachErosion \n
///   ㄴ BeachErosionServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 8. 22. 오후 4:22:46 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 
///   -# 
/////////////////////////////////////////////
@Service("beachErosionService")
@Profile({"dh_dev", "dh_oper"})
public class BeachErosionServiceImpl implements BeachErosionService {

	/// 해안침식 맵퍼
	@Resource
	ViewOclBersMaMapper viewOclBersMaMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.beachErosion.BeachErosionService#searchMserYy()
	/////////////////////////////////////////////
	@Override
	public List<String> searchMserYy() {
		return viewOclBersMaMapper.searchMserYy();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.beachErosion.BeachErosionService#searchBganMt()
	/////////////////////////////////////////////
	@Override
	public List<String> searchBganMt() {
		return viewOclBersMaMapper.searchBganMt();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.beachErosion.BeachErosionService#searchEdanMt()
	/////////////////////////////////////////////
	@Override
	public List<String> searchEdanMt() {
		return viewOclBersMaMapper.searchEdanMt();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.beachErosion.BeachErosionService#searchZoneNm()
	/////////////////////////////////////////////
	@Override
	public List<String> searchZoneNm() {
		return viewOclBersMaMapper.searchZoneNm();
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.beachErosion.BeachErosionService#listCount(kr.co.gitt.kworks.projects.dh.dto.BeachErosionRequestDTO)
	/////////////////////////////////////////////
	@Override
	public Integer listCount(BeachErosionRequestDTO beachErosionRequestDTO) {
		return viewOclBersMaMapper.listCount(beachErosionRequestDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.projects.dh.service.beachErosion.BeachErosionService#list(kr.co.gitt.kworks.projects.dh.dto.BeachErosionRequestDTO)
	/////////////////////////////////////////////
	@Override
	public List<ViewOclBersMa> list(BeachErosionRequestDTO beachErosionRequestDTO) {
		return viewOclBersMaMapper.list(beachErosionRequestDTO);
	}

}
