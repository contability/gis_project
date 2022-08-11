package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.Map;

import kr.co.gitt.kworks.projects.yy.model.LgstrStatsFile;

public interface LgstrStatsFileMapper {
	
	public LgstrStatsFile listAll(Map<String, Object> map);
	
	public Integer insert(LgstrStatsFile lgstrStatsFile);
	
	public Integer delete(LgstrStatsFile lgstrStatsFile);

}
