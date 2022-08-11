package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yy.model.LgstrStats;

public interface LgstrStatsMapper {

	public Integer listCount(SearchDTO searchDTO);
	
	public List<LgstrStats> list(SearchDTO searchDTO);
	
	public LgstrStats selectOne(Long lgstrStatsNo);
	
	public Integer insert(LgstrStats lgstrStats);
	
	public Integer update(LgstrStats lgstrStats);
	
	public Integer delete(Long lgstrStatsNo);
}
