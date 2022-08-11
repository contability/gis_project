package kr.co.gitt.kworks.projects.gn.service.userAnctMngRegstr;

import java.util.List;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.MngUserAuthorHist;

public interface UserAcntMngRegstrService {
	
	public List<MngUserAuthorHist> listMngUserAuthorHist(SearchDTO searchDTO);
	
	public List<MngUserAuthorHist> listMngUserAuthorHist(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	public MngUserAuthorHist selectMngUserAuthorHist(Long sn);
	
	public Integer updateMngUserAuthorHist(MngUserAuthorHist mngUserAuthorHist);
	
	public Integer deleteMngUserAuthorHist(Long sn);
	
}
