package kr.co.gitt.kworks.projects.yg.service.ladCntr;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yg.mappers.LedgerEditHiMapper;
import kr.co.gitt.kworks.projects.yg.model.LedgerEditHi;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("landCenterEditLogService")
@Profile({"yg_dev", "yg_oper"})
public class LandCenterEditLogServiceImpl implements LandCenterEditLogService{
	
	@Resource
	LedgerEditHiMapper ledgerEditHiMapper;
	
	public Integer listCount(SearchDTO searchDTO) {
		return ledgerEditHiMapper.listCount(searchDTO);
	}
	
	public List<LedgerEditHi> listSearch(SearchDTO searchDTO) {
		return ledgerEditHiMapper.list(searchDTO);
	}
	
}