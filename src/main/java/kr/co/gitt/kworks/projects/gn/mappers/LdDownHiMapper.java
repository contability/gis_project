package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.LdDownHi;

public interface LdDownHiMapper {

	public Integer insert(LdDownHi ldDownHi);
	
	public Integer listCount(SearchDTO searchDTO);
	
	public List<LdDownHi> list(SearchDTO searchDTO);
	
}
