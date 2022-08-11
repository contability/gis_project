package kr.co.gitt.kworks.projects.sunchang.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.sunchang.model.KwsRplotreexmn;
import kr.co.gitt.kworks.projects.sunchang.model.KwsRplotreexmnLgstr;

public interface RplotreexmnMapper {
	
	public Integer listCount(SearchDTO searchDTO);
	
	public List<KwsRplotreexmnLgstr> selectLgstrByFk(Long rplotreexmnNo);
	
	public KwsRplotreexmn selectOne(Long rplotreexmnNo);
	
	public List<KwsRplotreexmnLgstr> selectListLgstr(SearchDTO searchDTO);
	
	public KwsRplotreexmnLgstr selectForAfter(Long rplotreexmnNo);
	
}
