package kr.co.gitt.kworks.projects.dh.service.cityPlanRoad;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.dh.dto.CityPlanRoadSearchDTO;
import kr.co.gitt.kworks.projects.dh.model.DhgConsHt;
import kr.co.gitt.kworks.projects.dh.model.DhgRepaHt;
import kr.co.gitt.kworks.projects.dh.model.DhgRoplAs;

import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * 동해시 도시계획도로 서비스 인터페이스 
 * @author kwangsu.lee
 *
 */
public interface CityPlanRoadSearchService {

	/**
	 * 도시계획도로 검색
	 * 
	 * @param searchDTO - 검색조건
	 * @return
	 * @throws Exception
	 */
	public SpatialSearchSummaryDTO listAllSummary(CityPlanRoadSearchDTO searchDTO) throws Exception;


	/**
	 * 도시계획도로 정렬
	 *  
	 * @param searchDTO - 정렬조건
	 * @return
	 * @throws Exception
	 */
	public SpatialSearchSummaryDTO sortRegister(CityPlanRoadSearchDTO searchDTO) throws Exception;
	
	
	/**
	 * 도시계획도로 조회
	 * 
	 * @param searchDTO - 조회조건
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> searchRegister(CityPlanRoadSearchDTO searchDTO) throws Exception;
	
	/**
	 * 도시계획도로 단건 조회
	 * 
	 * @param ftrIdn - 관리번호
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public EgovMap getRegister(Long ftrIdn, boolean isOrigin) throws Exception;
	
	/**
	 * 도시계획도로 속성 변경
	 * 
	 * @param ftrIdn - 관리번호
	 * @param data - 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer updateRegister(Long ftrIdn, DhgRoplAs data) throws Exception;
	
	/**
	 * 도시계획도로 공사개요 조회
	 * 
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> getConstruction(Long ftrIdn) throws Exception;

	/**
	 * 도시계획도로 공사개요 단건 조회
	 * 
	 * @param ftrIdn - 관리번호
	 * @param conSeq - 이력번호
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public EgovMap getConstruction(Long ftrIdn, Long conSeq, boolean isOrigin) throws Exception;
	
	/**
	 * 도시계획도로 공사개요 추가
	 * 
	 * @param ftrIdn - 관리번호
	 * @param data - 신규 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer insertConstruction(Long ftrIdn, DhgConsHt data) throws Exception;
	
	/**
	 * 도시계획도로 공사개요 변경
	 * 
	 * @param ftrIdn - 관리번호
	 * @param conSeq - 이력번호
	 * @param data - 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer updateConstruction(Long ftrIdn, Long conSeq, DhgConsHt data) throws Exception;
	
	/**
	 * 도시계획도로 공사개요 삭제
	 * 
	 * @param ftrIdn - 관리번호
	 * @param conSeq - 이력번호
	 * @return
	 * @throws Exception
	 */
	public Integer deleteConstruction(Long ftrIdn, Long conSeq) throws Exception;
	
	
	/**
	 * 도시계획도로 정비이력 조회
	 * 
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> getRepair(Long ftrIdn) throws Exception;

	/**
	 * 도시계획도로 정비이력 단건 조회
	 * 
	 * @param ftrIdn - 관리번호
	 * @param repSeq - 이력번호
	 * @param isOrigin - 도메인 적용 여부
	 * @return
	 * @throws Exception
	 */
	public EgovMap getRepair(Long ftrIdn, Long repSeq, boolean isOrigin) throws Exception;
	
	/**
	 * 도시계획도로 정비이력 추가
	 * 
	 * @param ftrIdn - 관리번호
	 * @param data - 신규 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer insertRepair(Long ftrIdn, DhgRepaHt data) throws Exception;
	
	/**
	 * 도시계획도로 공사개요 변경
	 * 
	 * @param ftrIdn - 관리번호
	 * @param repSeq - 이력번호
	 * @param data - 데이터
	 * @return
	 * @throws Exception
	 */
	public Integer updateRepair(Long ftrIdn, Long conSeq, DhgRepaHt data) throws Exception;
	
	/**
	 * 도시계획도로 공사개요 삭제
	 * 
	 * @param ftrIdn - 관리번호
	 * @param repSeq - 이력번호
	 * @return
	 * @throws Exception
	 */
	public Integer deleteRepair(Long ftrIdn, Long conSeq) throws Exception;
	
	
	/**
	 * 사진 목록조회
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @return
	 * @throws Exception
	 */
	public List<KwsImage> listPhoto(String ftrCde, Long ftrIdn) throws Exception;	
	
	/**
	 * 동일한 제목의 사진이 있는지 확인
	 * @param ftrCde - 지형지물부호
	 * @param ftrIdn - 관리번호
	 * @param imageSj - 이미지 제목
	 * @return
	 * @throws Exception
	 */
	boolean checkPhoto(String ftrCde, Long ftrIdn, String imageSj) throws Exception;
	
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
