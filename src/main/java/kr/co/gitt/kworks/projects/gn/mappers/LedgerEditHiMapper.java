package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.LedgerEditHi;

public interface LedgerEditHiMapper {

	public Integer insert(LedgerEditHi ledgerEditHi);
	
	public Integer listCount(SearchDTO searchDTO);
	
	public List<LedgerEditHi> list(SearchDTO searchDTO);
	
}
