package kr.co.gitt.kworks.projects.sunchang.service.rplotreexmn;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.sunchang.dto.kwsRplotreexmnLgstrDTO;
import kr.co.gitt.kworks.projects.sunchang.model.KwsRplotreexmn;
import kr.co.gitt.kworks.projects.sunchang.model.KwsRplotreexmnLgstr;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public interface RplotreexmnService {
	
	public List<KwsRplotreexmnLgstr> selectList(SearchDTO searchDTO);
	
	public List<KwsRplotreexmnLgstr> selectList(String pnu, String bsnsAreaNm, String bsnsSe, kwsRplotreexmnLgstrDTO searchDTO, PaginationInfo paginationInfo);
	
	public KwsRplotreexmn selectOne(Long rplotreexmnNo);
	
	public List<KwsRplotreexmnLgstr> selectListLgstr(String pnu);
	
	public List<KwsRplotreexmnLgstr> selectOneLgstr(Long rplotreexmnNo);
	
}
