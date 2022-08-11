package kr.co.gitt.kworks.projects.gs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.co.gitt.kworks.projects.gs.model.ReantAs;

public interface ReantAsMapper {

	public ReantAs selectOne(Long ftrIdn);
	
	public Integer update(@Param("ftrIdn") Long ftrIdn, @Param("data") ReantAs data);
	
	public Integer delete(@Param("ftrIdn") Long ftrIdn);
	
	public List<ReantAs> list(Long expIdn);
	
	public ReantAs getExpIdn(Long ftrIdn);
	
	public List<ReantAs> reantExpIdnSearch(ReantAs reantAs);
	
	public List<ReantAs> listFtrIdnGet(Long expIdn);
}
