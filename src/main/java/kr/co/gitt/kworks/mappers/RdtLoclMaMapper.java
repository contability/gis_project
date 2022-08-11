package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.model.RdtLoclMa;

public interface RdtLoclMaMapper {
	
	public List<RdtLoclMa> listAll(RdtLoclMa rdtLoclMa);
	
	public RdtLoclMa selectOneByFtrIdn(Long ftrIdn);
	
	public RdtLoclMa selectOne(Long fileNo);
	
	public Integer deleteByFileNo(Long fileNo);
	
	public Integer delete(RdtLoclMa rdtLoclMa);
	
	public Integer insert(RdtLoclMa rdtLoclMa);
}