package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.SpatialInfoData;

public interface SpatialInfoDataMapper {
	
	public Integer listCount(HashMap<String, Object> searchMap);

	public List<SpatialInfoData> list(HashMap<String, Object> searchMap);
	
	public SpatialInfoData select(Long dtaNo);
	
	public Integer insert(SpatialInfoData spatialInfoData);
	
	public Integer update(SpatialInfoData spatialInfoData);
	
	public Integer delete(Long dtaNo);
	
	public void updateManageNo();
	
}
