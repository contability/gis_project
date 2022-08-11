package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.dto.UseAreaSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.BmlAcexAs;
import kr.co.gitt.kworks.projects.gn.model.BmlAcexDt;

public interface BmlAcexAsMapper {
	
	public BmlAcexAs selectOne(Long prtIdn);
	
	public List<BmlAcexAs> areaList(UseAreaSearchDTO useAreaSearchDTO);
	
	public Integer insert(BmlAcexDt bmlAcexDt);
	
	public Integer remove();
}
