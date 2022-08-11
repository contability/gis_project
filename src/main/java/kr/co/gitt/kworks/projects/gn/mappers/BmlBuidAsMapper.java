package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.projects.gn.dto.UseAreaSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.BmlBuidAs;

public interface BmlBuidAsMapper {
	
	public List<BmlBuidAs> list(BmlBuidAs bmlBuidDt);
	
	public BmlBuidAs selectOne(Long prtIdn);
	
	public Integer insert(BmlBuidAs bmlBuidAs);
	
	public Integer remove();
	
	public Integer geomUpdate();

	public Integer mismatchBuid(Map<String, String> map);
	
	public List<BmlBuidAs> selectListArea(UseAreaSearchDTO useAreaSearchDTO);
	
	public Integer filterValidData();
	
	public Integer backup();
	
	public Integer deleteBackup();
}
