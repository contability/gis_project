package kr.co.gitt.kworks.projects.gs.mappers;

import org.apache.ibatis.annotations.Param;

import kr.co.gitt.kworks.projects.gs.model.ExplantAs;

public interface ExplantAsMapper {

	public ExplantAs selectOne(Long objectId);
	
	public ExplantAs selectEx(Long ftrIdn);
	
	public Integer update(@Param("objectId") Long objectId, @Param("data") ExplantAs data);
	
	public Integer delete(@Param("objectId") Long objectId);
}
