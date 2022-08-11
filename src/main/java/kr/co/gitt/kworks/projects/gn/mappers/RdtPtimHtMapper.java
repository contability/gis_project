package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtPtimHt;

public interface RdtPtimHtMapper {
	
	public List<RdtPtimHt> rdtPtimHtSelectList(RdtPtimHt rdtPtimHt);
	
	public List<RdtPtimHt> rdtPtimHtSelectOne(Long impIdn);
	
	public Integer rdtPtimHtInsert(RdtPtimHt rdtPtimHt);
	
	public Integer rdtPtimHtUpdate(RdtPtimHt rdtPtimHt);
	
	public Integer rdtPtimHtDelete(Long impIdn);
}
