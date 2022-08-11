package kr.co.gitt.kworks.projects.gs.service.enviro;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterCqlDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO.FilterType;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.contact.kras.service.KrasService;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsImage.ImageTy;
import kr.co.gitt.kworks.projects.gs.dto.EcologyAttrDTO;
import kr.co.gitt.kworks.projects.gs.dto.ReantPrintDTO;
import kr.co.gitt.kworks.projects.gs.mappers.ExplantAsMapper;
import kr.co.gitt.kworks.projects.gs.mappers.ReantAsMapper;
import kr.co.gitt.kworks.projects.gs.model.ExplantAs;
import kr.co.gitt.kworks.projects.gs.model.ReantAs;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;
import kr.co.gitt.kworks.service.spatial.SpatialSearchService;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;


@Service("ecologyNatureMapService")
@Profile({"gs_dev", "gs_oper"})
public class EnviroServiceImpl implements EnviroService {

	/// 메세지 소스 
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	@Resource
	SpatialSearchService spatialSearchService;
	
	@Resource
	ExplantAsMapper explantAsMapper;
	
	@Resource
	ReantAsMapper reantAsMapper;
	
	/// 부동산종합공부시스템 연계 서비스
	@Resource(name="krasCommonService")
	KrasService krasCommonService;
	
	/// 도메인 코드 서비스
	@Resource(name="domnCodeService")
	DomnCodeService domnCodeService;

	@Resource
	EgovIdGnrService explantAsIdGnrService;
	
	@Resource
	EgovIdGnrService reantAsIdGnrService;
	
	/**
	 *  이미지 서비스
	 */
	@Resource
	ImageService imageService;
	
	@Override
	public List<SpatialSearchSummaryDTO> search(List<String> dataIds, EcologyAttrDTO ecologyAttrDTO) throws Exception {
//		List<SpatialSearchResultDTO> data = new ArrayList<SpatialSearchResultDTO>();
		String pnu = ecologyAttrDTO.getPnu();
		
		StringBuilder cql = new StringBuilder();
		cql.append("PNU LIKE '%").append(pnu).append("%'");
		
		SpatialSearchDTO search = new SpatialSearchDTO();		
		search.setDataIds(dataIds);
		search.setFilterType(FilterType.CQL);
		search.setIsOriginColumnValue(true);

		FilterCqlDTO filter = new FilterCqlDTO(); 
		filter.setCql(cql.toString());
		search.setFilterCqlDTO(filter);
		
		List<SpatialSearchSummaryDTO> result = spatialSearchService.searchSummaries(search);
		return result;
	}
	
	public List<SpatialSearchSummaryDTO> explantSearch(EcologyAttrDTO ecologyAttrDTO) throws Exception {
		StringBuilder cql = new StringBuilder();
		cql.append("PNU = '").append(ecologyAttrDTO.getPnu()).append("'");
		
		List<String> dataIds = ecologyAttrDTO.getDataIds();
		SpatialSearchDTO search = new SpatialSearchDTO();		
		search.setDataIds(dataIds);
		search.setFilterType(FilterType.CQL);
		search.setIsOriginColumnValue(true);

		FilterCqlDTO filter = new FilterCqlDTO(); 
		filter.setCql(cql.toString());
		search.setFilterCqlDTO(filter);
		
		List<SpatialSearchSummaryDTO> result = spatialSearchService.searchSummaries(search);
		return result;
	}

	@Override
	public ExplantAs selectOneEx(Long objectId) throws Exception {
		return explantAsMapper.selectOne(objectId);
	}

	@Override
	public ReantAs selectOneRe(Long ftrIdn) throws Exception {
		return reantAsMapper.selectOne(ftrIdn);
	}

	@Override
	public Integer updateEx(Long objectId, ExplantAs data) throws Exception {
		return explantAsMapper.update(objectId, data);
	}

	@Override
	public Integer updateRe(Long ftrIdn, ReantAs data) throws Exception {
		return reantAsMapper.update(ftrIdn, data); 
	}

	@Override
	public Integer removeEx(Long objectId) throws Exception {
		return explantAsMapper.delete(objectId);
	}

	@Override
	public Integer removeRe(Long ftrIdn) throws Exception {
		return reantAsMapper.delete(ftrIdn);
	}

	@Override
	public ExplantAs lastFtrIdn() throws Exception {
		ExplantAs explantAs = new ExplantAs();
		explantAs.setFtrIdn(explantAsIdGnrService.getNextLongId());
		return explantAs;
	}

	@Override
	public ExplantAs selectEx(Long ftrIdn) throws Exception {
		return explantAsMapper.selectEx(ftrIdn);
	}

	@Override
	public List<ReantAs> listReantAs(Long expIdn) throws Exception {
		return reantAsMapper.list(expIdn);
	}
	
	@Override
	public List<KwsImage> listPhoto(Long ftrIdn) throws Exception {
	
		KwsImage cql = new KwsImage();
		cql.setFtrCde("EX001");
		cql.setFtrIdn(ftrIdn);
		cql.setImageTy(ImageTy.EXPLANT_PHOTO);
		
		return imageService.listAllImage(cql);
	}
	
	@Override
	public List<KwsImage> listRePhoto(Long ftrIdn) throws Exception {
	
		KwsImage cql = new KwsImage();
		cql.setFtrCde("RE001");
		cql.setFtrIdn(ftrIdn);
		cql.setImageTy(ImageTy.REPLANT_PHOTO);
		
		return imageService.listAllImage(cql);
	}
	
	@Override
	public KwsImage selectPhotoByNo(Long imageNo) throws Exception {
		return imageService.selectOneImage(imageNo);
	}
	
	@Override
	public KwsImage updatePhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {
		return imageService.modifyImage(imageDTO, multipartFile, thumbnailWidth, thumbnailHeight);
	}

	@Override
	public ReantAs lastReFtrIdn() throws Exception {
		ReantAs reantAs = new ReantAs();
		reantAs.setFtrIdn(reantAsIdGnrService.getNextLongId());
		return reantAs;
	}

	@Override
	public ReantPrintDTO printReantAs(Long ftrIdn) throws Exception {
		ReantPrintDTO result = new ReantPrintDTO();
		
//		List<ReantAs> reantAs = reantAsMapper.getExpIdn(ftrIdn);
		ReantAs reantAsDate = reantAsMapper.getExpIdn(ftrIdn);
		Long expIdn = reantAsDate.getExpIdn();
		String jobDate = reantAsDate.getJobDate();
//		ReantAs jobDate = reantAsMapper.getDate(ftrIdn);
		
		//List<SpatialSearchResultDTO> data = new ArrayList<SpatialSearchResultDTO>();
		List<ReantAs> reantAsList = reantAsMapper.list(expIdn);
		
//		reantAs = reantAsMapper.list(expIdn);
		
		for(int i=0; i<reantAsList.size(); i++){
			ReantAs reantAs = reantAsList.get(i);
			//제거작업
			if (reantAs.getJobType().equals("REM001")){
				result.setJobAddrA(reantAs.getAddress());
				result.setJobNoteA(reantAs.getJobNote());
				result.setJobPerA(reantAs.getJobPers());
				
				//누계
				result.setTotalGsAandB(reantAs.getAreaGj()+result.getAreaGsB());
				result.setTotalGjAandB(reantAs.getAreaGj()+result.getAreaGjB());
				result.setTotalHnAandB(reantAs.getAreaGj()+result.getAreaHnB());
				result.setTotalJwAandB(reantAs.getAreaGj()+result.getAreaJwB());
				result.setTotalTsAandB(reantAs.getAreaGj()+result.getAreaTsB());
				
				//금일
				result.setAreaGjA(reantAs.getAreaGj());
				result.setAreaHnA(reantAs.getAreaHn());
				result.setAreaGsA(reantAs.getAreaGs());
				result.setAreaJwA(reantAs.getAreaJw());
				result.setAreaTsA(reantAs.getAreaTs());
				result.setTotalTodayA(reantAs.getAreaGj()+reantAs.getAreaHn()+reantAs.getAreaGs()+reantAs.getAreaJw()+reantAs.getAreaTs());
				
			}
			//제거작업(예초기)
			else if (reantAs.getJobType().equals("REM002")){
				result.setJobAddrB(reantAs.getAddress());
				result.setJobNoteB(reantAs.getJobNote());
				result.setJobPerB(reantAs.getJobPers());
				
				//누계
				result.setTotalGsAandB(result.getTotalGsA()+reantAs.getAreaGj());
				result.setTotalGjAandB(result.getTotalGsA()+reantAs.getAreaGj());
				result.setTotalHnAandB(result.getTotalGsA()+reantAs.getAreaGj());
				result.setTotalJwAandB(result.getTotalGsA()+reantAs.getAreaGj());
				result.setTotalTsAandB(result.getTotalGsA()+reantAs.getAreaGj());
				
				//금일
				result.setAreaGjB(reantAs.getAreaGj());
				result.setAreaHnB(reantAs.getAreaHn());
				result.setAreaGsB(reantAs.getAreaGs());
				result.setAreaJwB(reantAs.getAreaJw());
				result.setAreaTsB(reantAs.getAreaTs());
				result.setTotalTodayA(reantAs.getAreaGj()+reantAs.getAreaHn()+reantAs.getAreaGs()+reantAs.getAreaJw()+reantAs.getAreaTs());
				
			}
			//식제작업
			else if (reantAs.getJobType().equals("REM003")){
				result.setJobAddrC(reantAs.getAddress());
				result.setJobNoteC(reantAs.getJobNote());
				result.setJobPerC(reantAs.getJobPers());
				
				result.setTotalGsC(reantAs.getAreaGj());
				result.setTotalGjC(reantAs.getAreaGj());
				result.setTotalHnC(reantAs.getAreaGj());
				result.setTotalJwC(reantAs.getAreaGj());
				result.setTotalTsC(reantAs.getAreaGj());
				
				//금일
				result.setAreaGjC(reantAs.getAreaGj());
				result.setAreaHnC(reantAs.getAreaHn());
				result.setAreaGsC(reantAs.getAreaGs());
				result.setAreaJwC(reantAs.getAreaJw());
				result.setAreaTsC(reantAs.getAreaTs());
				result.setTotalTodayC(reantAs.getAreaGj()+reantAs.getAreaHn()+reantAs.getAreaGs()+reantAs.getAreaJw()+reantAs.getAreaTs());
				
			}
			//서식지조사
			else if (reantAs.getJobType().equals("REM004")){
				
				result.setJobAddrD(reantAs.getAddress());
				result.setJobNoteD(reantAs.getJobNote());
				result.setJobPerD(reantAs.getJobPers());
				
				result.setTotalGsD(reantAs.getAreaGj());
				result.setTotalGjD(reantAs.getAreaGj());
				result.setTotalHnD(reantAs.getAreaGj());
				result.setTotalJwD(reantAs.getAreaGj());
				result.setTotalTsD(reantAs.getAreaGj());
				
				//금일
				result.setAreaGjD(reantAs.getAreaGj());
				result.setAreaHnD(reantAs.getAreaHn());
				result.setAreaGsD(reantAs.getAreaGs());
				result.setAreaJwD(reantAs.getAreaJw());
				result.setAreaTsD(reantAs.getAreaTs());
				result.setTotalTodayD(reantAs.getAreaGj()+reantAs.getAreaHn()+reantAs.getAreaGs()+reantAs.getAreaJw()+reantAs.getAreaTs());
				
			}
		}
		//클릭한 대장의 근무일
		result.setJobDateTo(jobDate);
		
		return result;
	}

	@Override
	public List<ReantAs> reantExpIdnSearch(Long expIdn, String jobType)throws Exception {
		ReantAs reantAs = new ReantAs();
		reantAs.setExpIdn(expIdn);
		reantAs.setJobType(jobType);
//		reantAs.setExpIdn(expIdn);
//		reantAs.setJobType(jobType);
		return reantAsMapper.reantExpIdnSearch(reantAs);
	}

	@Override
	public List<ReantAs> listFtrIdnGet(Long expIdn) throws Exception {
		return reantAsMapper.listFtrIdnGet(expIdn);
	}
	
}
