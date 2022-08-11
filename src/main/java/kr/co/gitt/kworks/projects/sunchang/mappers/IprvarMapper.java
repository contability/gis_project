package kr.co.gitt.kworks.projects.sunchang.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.sunchang.model.KwsIprvarDt;
import kr.co.gitt.kworks.projects.sunchang.model.KwsIprvarDtFile;

public interface IprvarMapper {
	
	public Integer listCount(SearchDTO searchDTO);

	public List<KwsIprvarDt> selectList(SearchDTO searchDTO);
	
	public KwsIprvarDt selectOne(Long iprvarNo);
	
	public Integer insert(KwsIprvarDt kwsIprvarDt);
	
	public Integer update(KwsIprvarDt kwsIprvarDt);
	
	public Integer delete(Long iprvarNo);
	
	public Integer chkPnu(String pnu);
	
	public List<KwsIprvarDtFile> fileSelectList(Long iprvarNo);
	
	public Integer fileInsert(KwsIprvarDtFile kwsIprvarDtFile);
	
	public Integer fileDelete(Long iprvarFileNo);
	
}
