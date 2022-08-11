package kr.co.gitt.kworks.projects.yg.service.ladCntr;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yg.model.LdDownHi;
import kr.co.gitt.kworks.projects.yg.model.LdDocMa;

public interface LandCenterUseDownLogService {

	public Integer listCount(SearchDTO searchDTO);
	
	public List<LdDownHi> listSearch(SearchDTO searchDTO);
	
	public Integer insertLog(LdDocMa ldDocMa) throws Exception ; 
	
}