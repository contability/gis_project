package kr.co.gitt.kworks.projects.gn.service.krasDataPvsnRegstr;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.KrasDataPvsnRegstrDTO;
import kr.co.gitt.kworks.projects.gn.dto.KrasDataPvsnRegstrVO;

public interface KrasDataPvsnRegstrService {
	public List<KrasDataPvsnRegstrVO> listMngKrasDataPvsnRegstr(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	public KrasDataPvsnRegstrVO selectOnrMngKrasDataPvsnRegstr(Long dtaNo);

	public Integer addMngKrasDataPvsnRegstr(
			KrasDataPvsnRegstrDTO krasDataPvsnRegstrDTO) throws FdlException;

	public Integer modifyMngKrasDataPvsnRegstr(
			KrasDataPvsnRegstrDTO krasDataPvsnRegstrDTO);

	public Integer removeMngKrasDataPvsnRegstr(Long dtaNo);
}
