package kr.co.gitt.kworks.projects.gn.service.ecologyNatureMap;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchResultDTO;
import kr.co.gitt.kworks.projects.gn.dto.EcologyNatureMapAttrDTO;

public interface EcologyNatureMapService {

	public List<SpatialSearchResultDTO> search(String dataId, String pnu) throws Exception;
	
	public EcologyNatureMapAttrDTO searchJibun(String dataId, String pnu) throws Exception;
	
}
