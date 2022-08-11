package kr.co.gitt.kworks.projects.yg.service.ladCntr;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yg.model.LedgerEditHi;

public interface LandCenterEditLogService {

	public Integer listCount(SearchDTO searchDTO);
	
	public List<LedgerEditHi> listSearch(SearchDTO searchDTO);
	
}