package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.BmlBuidAs;

public interface BmlBuidAsMapper {
	
	public List<BmlBuidAs> list(BmlBuidAs bmlBuidAs);
	
	public BmlBuidAs selectOne(Long prtIdn);

	public Integer insert(BmlBuidAs bmlBuidAs);
	
	public Integer remove();
	
	public Integer geomUpdate();
}
