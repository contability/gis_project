package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.RdtPcbsDt;

public interface RdtPcbsDtMapper {
	
	public List<RdtPcbsDt> listAll();
	
	public List<RdtPcbsDt> list(RdtPcbsDt rdtPcbsDt);
	
	public RdtPcbsDt selectOne(RdtPcbsDt rdtPcbsDt);
	
	public Integer add(RdtPcbsDt rdtPcbsDt);
	
	public Integer modify(RdtPcbsDt rdtPcbsDt);
	
	public Integer remove(RdtPcbsDt rdtPcbsDt);
}
