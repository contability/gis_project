package kr.co.gitt.kworks.projects.sunchang.service.iprvar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.projects.sunchang.model.KwsIprvarDt;
import kr.co.gitt.kworks.projects.sunchang.model.KwsIprvarDtFile;

public interface IprvarService {
	
	public List<KwsIprvarDt> selectList(SearchDTO searchDTO);
	
	public List<KwsIprvarDt> selectList(String pnu, SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	public KwsIprvarDt selectOne(Long iprvarNo);
	
	public Integer insert(KwsIprvarDt kwsIprvarDt) throws FdlException ;
	
	public Integer update(KwsIprvarDt kwsIprvarDt);
	
	public Integer delete(Long iprvarNo);
	
	public Integer chkPnu(String pnu);
	
	public List<KwsIprvarDtFile> fileSelectList(Long iprvarNo);
	
	public Integer fileInsert(KwsIprvarDtFile kwsIprvarDtFile, MultipartRequest mr) throws FdlException, IOException ;
	
	public Integer fileDelete(String[] iprvarFileNos, String[] fileNos);
	
	public KwsFile iprvarFileChk(Long fileNo);
	
	public void iprvarPdfPreview(Long fileNo, HttpServletResponse response, String userAgent) throws IOException;
	
	public void iprvarFileDownload(String chkArr, HttpServletResponse response, String userAgent)throws IOException ;
	
	public String nameEncoding(String fileName, String userAgent) throws UnsupportedEncodingException;
	
}
