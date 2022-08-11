package kr.co.gitt.kworks.projects.gn.service.prtsArea;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.projects.gn.dto.PrtsAreaDTO;
import kr.co.gitt.kworks.projects.gn.model.RdlPrtsAs;
import kr.co.gitt.kworks.projects.gn.model.RdtPtimHt;

public interface PrtsAreaService {
	
	public List<RdlPrtsAs> selectList(PrtsAreaDTO searchDTO);
	
	public List<RdlPrtsAs> selectList(PrtsAreaDTO searchDTO, PaginationInfo paginationInfo);
	
	
	////////////////////////////////// 보호구역 개선사업이력
	
	public List<RdtPtimHt> rdtPtimHtSelectList(Long ftfIdn, String ftfCde);
	
	public List<RdtPtimHt> rdtPtimHtSelectOne(Long impIdn);
	
	public Integer rdtPtimHtInsert(RdtPtimHt rdtPtimHt) throws FdlException;
	
	public Integer rdtPtimHtUpdate(RdtPtimHt rdtPtimHt);
	
	public Integer rdtPtimHtDelete(Long impIdn);
	
}
