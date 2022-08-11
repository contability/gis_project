package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.BmlAcinAs;

public interface BmlAcinAsMapper {
	
	public List<BmlAcinAs> list();
	
	public BmlAcinAs selectOne(Long prtIdn);
	
}
