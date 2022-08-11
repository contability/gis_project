package kr.co.gitt.kworks.projects.yy.service.ctrlpnt;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.yy.dto.ControlPointDTO;
import kr.co.gitt.kworks.projects.yy.dto.ControlPointSearchDTO;
import kr.co.gitt.kworks.projects.yy.model.EtlCopoDt;

import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/////////////////////////////////////////////
/// @class CtrlpntService
/// kr.co.gitt.kworks.projects.gn.service.ctrlpnt \n
///   ㄴ CtrlpntService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오후 3:51:21 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 기준점 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface CtrlpntService {

	/**
	 * 통합검색 
	 * @param dataIds - 데이터 목록
	 * @param searchDTO - 검색조건
	 * @return
	 * @throws Exception
	 */
	public List<SpatialSearchSummaryDTO> searchSummaries(List<String> dataIds, ControlPointSearchDTO searchDTO) throws Exception;

	/**
	 * 기준점 조회
	 * @param dataId - 데이터아이디
	 * @param ftrIdn - 관리번호
	 * @param isOriginColumnValue - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public EgovMap getRegister(String dataId, Long ftrIdn, boolean isOriginColumnValue) throws Exception;

	/**
	 * 기준점 등록
	 * @param registerDTO
	 * @return
	 * @throws Exception
	 */
	public Integer insertRegister(ControlPointDTO registerDTO) throws Exception;

	/**
	 * 기준점 변경
	 * @param registerDTO
	 * @return
	 * @throws Exception
	 */
	public Integer updateRegister(Long ftrIdn, ControlPointDTO registerDTO) throws Exception;

	/**
	 * 기준점 삭제
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public Integer deleteRegister(String ftrCde, Long ftrIdn) throws Exception;

	
	///////////////////////////////////////////////////////////////////////////////////////////
	// 시준점
	//
	/**
	 * 시준점 목록조회
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public List<EtlCopoDt> listPassPoint(String ftrCde, Long ftrIdn) throws Exception;	

	/**
	 * 단건 조회
	 * @param ecpNo - 고유번호
	 * @return
	 * @throws Exception
	 */
	public EtlCopoDt selectassPointByNo(Long ecpNo) throws Exception;	

	/**
	 * 시준점 등록
	 * @param data - 시준점 정보
	 * @return
	 * @throws Exception
	 */
	public Integer insertPassPoint(EtlCopoDt data) throws Exception;
	
	/**
	 * 시준점 변경
	 * @param ecpNo - 시준점 고유번호
	 * @return
	 * @throws Exception
	 */
	public Integer updatePassPoint(Long ecpNo, EtlCopoDt data) throws Exception;

	/**
	 * 시준점 삭제
	 * @param ecpNo - 시준점 고유번호
	 * @return
	 * @throws Exception
	 */
	public Integer deletePassPoint(Long ecpNo) throws Exception;
	
	/**
	 * 시준점 삭제
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public Integer deletePassPoint(String ftrCde, Long ftrIdn) throws Exception;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// 이미지
	//
	
	/**
	 * 사진 목록조회
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public List<KwsImage> listPhoto(String ftrCde, Long ftrIdn) throws Exception;	

	/**
	 * 사진 단건 조회
	 * @param imageNo
	 * @return
	 * @throws Exception
	 */
	public KwsImage selectPhotoByNo(Long imageNo) throws Exception;
	
	/**
	 * 사진 등록
	 * @param imageDTO
	 * @param multipartFile
	 * @param thumbnailWidth
	 * @param thumbnailHeight
	 * @return
	 * @throws Exception
	 */
	public KwsImage insertPhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;	
	
	/**
	 * 사진 변경
	 * @param imageDTO
	 * @param multipartFile
	 * @param thumbnailWidth
	 * @param thumbnailHeight
	 * @return
	 * @throws Exception
	 */
	public KwsImage updatePhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;	
}
