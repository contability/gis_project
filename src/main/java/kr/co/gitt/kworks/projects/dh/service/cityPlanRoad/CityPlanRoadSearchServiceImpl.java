package kr.co.gitt.kworks.projects.dh.service.cityPlanRoad;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataField;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.dh.dto.CityPlanRoadSearchDTO;
import kr.co.gitt.kworks.projects.dh.mappers.DhgConsHtMapper;
import kr.co.gitt.kworks.projects.dh.mappers.DhgRepaHtMapper;
import kr.co.gitt.kworks.projects.dh.mappers.DhgRoplAsMapper;
import kr.co.gitt.kworks.projects.dh.model.DhgConsHt;
import kr.co.gitt.kworks.projects.dh.model.DhgRepaHt;
import kr.co.gitt.kworks.projects.dh.model.DhgRoplAs;
import kr.co.gitt.kworks.projects.dh.repository.CityPlanRoadSearchRepository;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.data.DataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("dhPlanRoadSearchService")
@Profile({"dh_dev", "dh_oper"})
public class CityPlanRoadSearchServiceImpl implements CityPlanRoadSearchService {
	/**
	 *  로거
	 */
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 메세지 소스
	 */
	@Resource(name="messageSource")
    private MessageSource messageSource;

	/**
	 * 데이터 서비스
	 */
	@Resource
	DataService dataService;
	
	/**
	 * 도시계획도로 저장소
	 */
	@Resource
	CityPlanRoadSearchRepository dhPlanRoadSearchRepository;
	
	/**
	 * 도시계획도로 맵퍼
	 */
	@Resource
	DhgRoplAsMapper dhgRoplAsMapper;	
	
	/**
	 * 도시계획도로 공사개요 맵퍼
	 */
	@Resource
	DhgConsHtMapper dhgConsHtMapper;	

	/**
	 * 도시계획도로 정비이력 맵퍼
	 */
	@Resource
	DhgRepaHtMapper dhgRepaHtMapper;	
	
	/**
	 * 도시계획도로 공사개요 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService dhgConsHtIdGnrService;
	
	/**
	 * 도시계획도로 정비이력 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService dhgRepaHtIdGnrService;
	
	/**
	 *  이미지 서비스
	 */
	@Resource
	ImageService imageService;
	
	
	/**
	 * 테이블과 컬럼 검증
	 * @param kwsData
	 * @param dataId
	 */
	private void validatorDataAndDataField(KwsData kwsData, String dataId) 
	{
		/// 데이터 없음
		if(kwsData == null) {
			logger.warn("No Data : " + dataId);
			throw new RuntimeException("No Data : " + dataId);
		}
		
		List<KwsDataField> dataFields = kwsData.getKwsDataFields();
		/// 데이터 필드 없음
		if(dataFields == null || dataFields.size() < 1) {
			logger.warn("No Data Field : " + dataId);
			throw new RuntimeException("No Data Field : " + dataId);
		}
	}
	
	@Override
	public SpatialSearchSummaryDTO listAllSummary(CityPlanRoadSearchDTO searchDTO) throws Exception {
		SpatialSearchSummaryDTO result = null;
		
		String spatial = messageSource.getMessage("Extension.CityPlanRoad.Spatial", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		
		KwsData spatialData = dataService.selectOneData(spatial);
		validatorDataAndDataField(spatialData, spatial);
		
		String uprNam = searchDTO.getUprNam();
		String uprGrd = searchDTO.getUprGrd();
		String uprTyp = searchDTO.getUprTyp();
		String uprNum = searchDTO.getUprNum();
		
		StringBuilder condition = new StringBuilder();
		if (uprNam != null && uprNam.length() > 0)
			condition.append(" UPR_NAM LIKE '%").append(uprNam).append("%'");
		
		if (uprGrd != null && uprGrd.length() > 0) {
			if (condition.length() > 0 && !condition.toString().endsWith("AND"))
				condition.append(" AND");
			condition.append(" UPR_GRD = '").append(uprGrd).append("'");
		}
		
		if (uprTyp != null && uprTyp.length() > 0) {
			if (condition.length() > 0 && !condition.toString().endsWith("AND"))
				condition.append(" AND");
			condition.append(" UPR_TYP = '").append(uprTyp).append("'");
		}

		if (uprNum != null && uprNum.length() > 0) {
			if (condition.length() > 0 && !condition.toString().endsWith("AND"))
				condition.append(" AND");
			condition.append(" UPR_NUM = '").append(uprNum).append("'");
		}
		
		String sql = condition.toString();
		
		List<Long> ids = dhPlanRoadSearchRepository.listAllSummary(spatialData, spatialKey, sql);
		if(ids != null && ids.size() > 0) {
			result = new SpatialSearchSummaryDTO();
			result.setDataId(spatialData.getDataId());
			result.setDataAlias(spatialData.getDataAlias());
			result.setIds(ids);
		}
		
		return result;
	}

	@Override
	public SpatialSearchSummaryDTO sortRegister(CityPlanRoadSearchDTO searchDTO) throws Exception {
		SpatialSearchSummaryDTO result = null;
		
		String spatial = messageSource.getMessage("Extension.CityPlanRoad.Spatial", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		
		KwsData spatialData = dataService.selectOneData(spatial);
		validatorDataAndDataField(spatialData, spatial);
		
		List<Long> ids = dhPlanRoadSearchRepository.listAllSummary(spatialData, spatialKey, searchDTO.getIds(), searchDTO.getSortField(), searchDTO.getSortDirection());
		if(ids != null && ids.size() > 0) {
			result = new SpatialSearchSummaryDTO();
			result.setDataId(spatialData.getDataId());
			result.setDataAlias(spatialData.getDataAlias());
			result.setIds(ids);
		}
		
		return result;
	}

	@Override
	public List<EgovMap> searchRegister(CityPlanRoadSearchDTO searchDTO) throws Exception {
		
		String spatial = messageSource.getMessage("Extension.CityPlanRoad.Spatial", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		
		KwsData spatialData = dataService.selectOneData(spatial);
		validatorDataAndDataField(spatialData, spatial);
		
		boolean isOriginValue = false;
		if (searchDTO.getIsOriginValue() != null && searchDTO.getIsOriginValue())
			isOriginValue = true;

		List<EgovMap> result = dhPlanRoadSearchRepository.listAll(spatialData, spatialKey, searchDTO.getIds(), searchDTO.getSortField(), searchDTO.getSortDirection(), isOriginValue);
		return result;
	}		
	
	@Override
	public EgovMap getRegister(Long ftrIdn, boolean isOrigin) throws Exception	{

		String spatial = messageSource.getMessage("Extension.CityPlanRoad.Spatial", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		
		KwsData spatialData = dataService.selectOneData(spatial);
		validatorDataAndDataField(spatialData, spatial);
		
		StringBuilder condition = new StringBuilder();
		condition.append(spatialKey).append(" = ").append(ftrIdn);
		String sql = condition.toString();
		
		EgovMap result = dhPlanRoadSearchRepository.selectOne(spatialData, sql, isOrigin);

		return result;
	}

	@Override
	public Integer updateRegister(Long ftrIdn, DhgRoplAs data) throws Exception	{
	
		return dhgRoplAsMapper.update(ftrIdn, data);
	}
	
	@Override
	public List<EgovMap> getConstruction(Long ftrIdn) throws Exception	{

		String construction = messageSource.getMessage("Extension.CityPlanRoad.Construction", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		
		KwsData consData = dataService.selectOneData(construction);
		validatorDataAndDataField(consData, construction);
		
		StringBuilder condition = new StringBuilder();
		condition.append(spatialKey).append(" = ").append(ftrIdn);
		String sql = condition.toString();
		
		List<EgovMap> result = dhPlanRoadSearchRepository.selectList(consData, sql, false);
		return result;
	}
	
	@Override
	public EgovMap getConstruction(Long ftrIdn, Long conSeq, boolean isOrigin) throws Exception	{

		String construction = messageSource.getMessage("Extension.CityPlanRoad.Construction", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		
		KwsData consData = dataService.selectOneData(construction);
		validatorDataAndDataField(consData, construction);
		
		StringBuilder condition = new StringBuilder();
		condition.append(spatialKey).append(" = ").append(ftrIdn);
		condition.append(" AND CON_SEQ = ").append(conSeq);
		String sql = condition.toString();
		
		EgovMap result = dhPlanRoadSearchRepository.selectOne(consData, sql, isOrigin);
		return result;
	}
	
	@Override
	public Integer insertConstruction(Long ftrIdn, DhgConsHt data) throws Exception {
		
		data.setFtrIdn(ftrIdn);
		
		Long conSeq = dhgConsHtIdGnrService.getNextLongId();
		data.setConSeq(conSeq);
		
		return dhgConsHtMapper.insert(data);
	}
	
	@Override
	public Integer updateConstruction(Long ftrIdn, Long conSeq, DhgConsHt data) throws Exception {
		
		return dhgConsHtMapper.update(ftrIdn, conSeq, data);
	}

	@Override
	public Integer deleteConstruction(Long ftrIdn, Long conSeq) throws Exception {
		
		return dhgConsHtMapper.delete(ftrIdn, conSeq);
	}
	
	@Override
	public List<EgovMap> getRepair(Long ftrIdn) throws Exception {

		String repair = messageSource.getMessage("Extension.CityPlanRoad.Repair", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		
		KwsData repaData = dataService.selectOneData(repair);
		validatorDataAndDataField(repaData, repair);
		
		StringBuilder condition = new StringBuilder();
		condition.append(spatialKey).append(" = ").append(ftrIdn);
		String sql = condition.toString();
		
		List<EgovMap> result = dhPlanRoadSearchRepository.selectList(repaData, sql, false);
		return result;
	}

	@Override
	public EgovMap getRepair(Long ftrIdn, Long repSeq, boolean isOrigin) throws Exception	{

		String repair = messageSource.getMessage("Extension.CityPlanRoad.Repair", null, Locale.getDefault());
		String spatialKey = messageSource.getMessage("Extension.CityPlanRoad.Spatial.Field", null, Locale.getDefault());
		
		KwsData repaData = dataService.selectOneData(repair);
		validatorDataAndDataField(repaData, repair);
		
		StringBuilder condition = new StringBuilder();
		condition.append(spatialKey).append(" = ").append(ftrIdn);
		condition.append(" AND REP_SEQ = ").append(repSeq);
		String sql = condition.toString();
		
		EgovMap result = dhPlanRoadSearchRepository.selectOne(repaData, sql, isOrigin);
		return result;
	}
	
	@Override
	public Integer insertRepair(Long ftrIdn, DhgRepaHt data) throws Exception {
		
		data.setFtrIdn(ftrIdn);
		
		Long repSeq = dhgRepaHtIdGnrService.getNextLongId();
		data.setRepSeq(repSeq);
		
		return dhgRepaHtMapper.insert(data);
	}

	@Override
	public Integer updateRepair(Long ftrIdn, Long repSeq, DhgRepaHt data) throws Exception {
		
		return dhgRepaHtMapper.update(ftrIdn, repSeq, data);
	}

	@Override
	public Integer deleteRepair(Long ftrIdn, Long repSeq) throws Exception {
		
		return dhgRepaHtMapper.delete(ftrIdn, repSeq);
	}
	
	@Override
	public List<KwsImage> listPhoto(String ftrCde, Long ftrIdn) throws Exception {
	
		KwsImage cql = new KwsImage();
		cql.setFtrCde(ftrCde);
		cql.setFtrIdn(ftrIdn);
		cql.setImageTy(ImageTy.CITY_PLAN_ROAD);
		
		return imageService.listAllImage(cql);
	}	

	@Override
	public boolean checkPhoto(String ftrCde, Long ftrIdn, String imageSj) throws Exception {
	
		KwsImage cql = new KwsImage();
		cql.setFtrCde(ftrCde);
		cql.setFtrIdn(ftrIdn);
		cql.setImageTy(ImageTy.CITY_PLAN_ROAD);
		
		List<KwsImage> results = imageService.listAllImage(cql);
		if (results != null && results.size() > 0) {
			for (KwsImage img : results) {
				if (imageSj.equals(img.getImageSj()))
					return false;
			}
		}
		
		return true;
	}	
	
	@Override
	public KwsImage selectPhotoByNo(Long imageNo) throws Exception {
		
		return imageService.selectOneImage(imageNo);
	}
	
	@Override
	public KwsImage insertPhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {

		return imageService.addImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
	}
	
	@Override
	public KwsImage updatePhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {

		return imageService.modifyImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
	}
}
