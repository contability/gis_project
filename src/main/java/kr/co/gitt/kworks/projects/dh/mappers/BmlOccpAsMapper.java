package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.BmlOccpAs;

public interface BmlOccpAsMapper {
	
	public List<BmlOccpAs> list(BmlOccpAs bmlOccpDt);
	
	public BmlOccpAs selectOne(BmlOccpAs bmlOccpDt);
	
	public Integer insert(BmlOccpAs bmlOccpDt);
	
	public Integer remove();
	
	public Integer geomUpdate();
}
