package kr.co.gitt.kworks.projects.gs.service.enviro;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchSummaryDTO;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.gs.dto.EcologyAttrDTO;
import kr.co.gitt.kworks.projects.gs.dto.ReantPrintDTO;
import kr.co.gitt.kworks.projects.gs.model.ExplantAs;
import kr.co.gitt.kworks.projects.gs.model.ReantAs;

public interface EnviroService {

	public List<SpatialSearchSummaryDTO> search(List<String> dataIds, EcologyAttrDTO ecologyAttrDTO) throws Exception;
	
	public List<SpatialSearchSummaryDTO> explantSearch(EcologyAttrDTO ecologyAttrDTO) throws Exception;
	
	public ExplantAs selectOneEx(Long objectId) throws Exception;
	
	public ExplantAs selectEx(Long ftrIdn) throws Exception;
	
	public ReantAs selectOneRe(Long ftrIdn) throws Exception;
	
	public Integer updateEx(Long objectId, ExplantAs data) throws Exception;
	
	public Integer updateRe(Long ftrIdn, ReantAs data) throws Exception;
	
	public Integer removeEx(Long objectId) throws Exception;
	
	public Integer removeRe(Long ftrIdn) throws Exception;
	
	public ExplantAs lastFtrIdn() throws Exception;
	
	public ReantAs lastReFtrIdn() throws Exception;
	
	public List<ReantAs> listReantAs(Long expIdn) throws Exception;
	
	public List<KwsImage> listPhoto(Long ftrIdn) throws Exception;
	
	public List<KwsImage> listRePhoto(Long ftrIdn) throws Exception;
	
	public KwsImage selectPhotoByNo(Long imageNo) throws Exception;
	
	public KwsImage updatePhoto(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;	

	public ReantPrintDTO printReantAs(Long ftrIdn) throws Exception;
	
	public List<ReantAs> reantExpIdnSearch(Long expIdn, String jobType) throws Exception;
	
	public List<ReantAs> listFtrIdnGet(Long expIdn) throws Exception;
}
