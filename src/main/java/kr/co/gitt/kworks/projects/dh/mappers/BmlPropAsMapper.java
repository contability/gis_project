package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.BmlPropAs;

public interface BmlPropAsMapper {
	
	public List<BmlPropAs> list(BmlPropAs bmlPropDt);
	
	public BmlPropAs selectOne(Long prtIdn);
	
	public Integer insert(BmlPropAs bmlPropAs);
	
	public Integer remove();
	
	public Integer geomUpdate();
	
}
