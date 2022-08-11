package kr.co.gitt.kworks.projects.gc.service.lossSttemnt;

import java.util.List;

import kr.co.gitt.kworks.projects.gc.dto.LossSttemntDTO;
import kr.co.gitt.kworks.projects.gc.model.EttLscpDt;
import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class LossSttemntService
/// kr.co.gitt.kworks.projects.gc.service.lossSttemnt \n
///   ㄴ LossSttemntService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 16. 오전 11:28:19 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 망실 신고 서비스 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface LossSttemntService {
	
	/////////////////////////////////////////////
	/// @fn listLossSttemnt
	/// @brief 함수 간략한 설명 : 망실신고 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettLscpDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EttLscpDt> listLossSttemnt(EttLscpDt ettLscpDt);
	
	/////////////////////////////////////////////
	/// @fn selectOneLossSttemnt
	/// @brief 함수 간략한 설명 : 망실신고 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param lossSttemntDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public EttLscpDt selectOneLossSttemnt(LossSttemntDTO lossSttemntDTO);
	
	/////////////////////////////////////////////
	/// @fn addLossSttemnt
	/// @brief 함수 간략한 설명 : 망실신고 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettLscpDt
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addLossSttemnt(EttLscpDt ettLscpDt) throws FdlException;

}
