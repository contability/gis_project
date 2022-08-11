package kr.co.gitt.kworks.projects.gn.service.ladCntr;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.LedgerEditHi;

public interface LandCenterEditLogService {

	public Integer listCount(SearchDTO searchDTO);
	
	public List<LedgerEditHi> listSearch(SearchDTO searchDTO);
	
}