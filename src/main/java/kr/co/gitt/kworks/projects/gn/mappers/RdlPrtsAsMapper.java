package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.projects.gn.model.RdlPrtsAs;
import kr.co.gitt.kworks.projects.gn.model.RdtPtimHt;

public interface RdlPrtsAsMapper {
	
	public Integer listCount(SearchDTO searchDTO);
	
	public List<RdlPrtsAs> selectList(SearchDTO searchDTO);
	
}
