package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnDTO;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpVO;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtCnncDt;

public interface SwtSpmtCnncDtMapper {
	public List<SwtSpmtCnncDt> selectWhereDataIdNObjectid(Map<String, String> parameterMap);
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief 함수 간략한 설명 : 배수설비인허가 대장과 연결된 물받이, 하수연결관 정보 합께 가져오기
	/// @remark
	/// - 이승재, 2020.07.28
	/// - 함수의 상세 설명 : 
	/// @param drngEqpCnfmPrmisnDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<DrngEqpVO> listDrngEqp(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO);
	
	public void insert(SwtSpmtCnncDt swtSpmtCnncDt);
	
	public void delete(SwtSpmtCnncDt swtSpmtCnncDt);
}
