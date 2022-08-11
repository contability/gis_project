package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.co.gitt.kworks.projects.yy.dto.PolicyRepoRegisterDTO;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoCt;


public interface PlcyRepoCtMapper {
	
	
	public List<PlcyRepoCt> list(PlcyRepoCt plcyRepoCt);
	
	public Integer insert(PlcyRepoCt plcyRepoCt);
	
	public PolicyRepoRegisterDTO selectOne(@Param("ftrIdn") Long ftrIdn);
	
	public Integer delete(@Param("repoIdn") Long repoIdn);
	
	
}
