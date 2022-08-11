package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.RdtVideoMa;

public interface RdtVideoMaMapper {
	
	public RdtVideoMa selectOne(Long ftrIdn);
	
	public List<RdtVideoMa> list(RdtVideoMa rdtVideoMa);
	
	public Integer updateVideoUp(RdtVideoMa rdtVideoMa);
	
	public Integer updateVideoDown(RdtVideoMa rdtVideoMa);
	
}