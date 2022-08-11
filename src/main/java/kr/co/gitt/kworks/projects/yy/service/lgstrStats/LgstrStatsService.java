package kr.co.gitt.kworks.projects.yy.service.lgstrStats;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yy.model.LgstrStats;

public interface LgstrStatsService {
	
	public List<LgstrStats> listLgstrStats(SearchDTO searchDTO);
	
	public List<LgstrStats> listLgstrStats(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	public LgstrStats selectOneLgstrStats(Long lgstrStatsNo);
	
	public Integer addLgstrStats(LgstrStats lgstrStats, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException;
	
	public Integer modifyLgstrStats(LgstrStats lgstrStats, MultipartRequest multipartRequest, Long deleteFileNo) throws FdlException, IllegalStateException, IOException;
	
	public Integer removeLgstrStats(Long lgstrStatsNo);
}
