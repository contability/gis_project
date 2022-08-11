package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.projects.gn.dto.UseAreaSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.BmlPropAs;

public interface BmlPropAsMapper {
	
	public List<BmlPropAs> list(BmlPropAs bmlPropDt);
	
	public BmlPropAs selectOne(Long prtIdn);
	
	public Integer insert(BmlPropAs bmlPropAs);
	
	public Integer remove();
	
	public Integer geomUpdate();
	
	public Integer mismatchProp(Map<String, String> map);
	
	public List<BmlPropAs> selectListArea(UseAreaSearchDTO useAreaSearchDTO);
	
	public List<BmlPropAs> selectAccGroupList();
	
	public Integer filterValidData();
	
	public List<BmlPropAs> selectManGroupList();
	
	public Integer backup();
	
	public Integer deleteBackup();
}
