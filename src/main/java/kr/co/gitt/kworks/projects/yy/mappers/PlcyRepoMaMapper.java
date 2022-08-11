package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.co.gitt.kworks.projects.yy.model.PlcyRepoMa;


public interface PlcyRepoMaMapper {
	
	public List<PlcyRepoMa> list(PlcyRepoMa plcyRepoMa);
	
	public Integer insert(PlcyRepoMa plcyRepoMa);
	
	public Integer repoVerSeq(@Param("ftrIdn") Long ftrIdn, @Param("repoIdn") Long repoIdn);
	
	public Integer update(PlcyRepoMa plcyRepoMa);
	
	public Integer delete(@Param("ftrIdn") Long ftrIdn);
	
	public String selectRepoTit(@Param("ftrIdn") Long ftrIdn, @Param("repoIdn") Long repoIdn);
	
	public Long selectRepoIdn(@Param("ftrIdn") Long ftrIdn);
}
