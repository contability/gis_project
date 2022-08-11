package kr.co.gitt.kworks.projects.gn.service.spatialInfoRegstr;

import java.util.List;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.MngGrphinfoManageRegstrDTO;
import kr.co.gitt.kworks.projects.gn.dto.MngGrphinfoUploadDtlsDTO;

public interface SpatialInfoRegstrService {
	public List<MngGrphinfoManageRegstrDTO> listMngGrphinfoManageRegstr(SearchDTO searchDTO, PaginationInfo paginationInfo);
	
	public MngGrphinfoManageRegstrDTO selectOneMngGrphinfoManageRegstr(Long regstrSn);
	
	public List<MngGrphinfoUploadDtlsDTO> selectMngGrphinfoUploadDtls(Long regstrSn);
	
	public Integer modifyMngGrphinfoManageRegstr(MngGrphinfoManageRegstrDTO mngGrphinfoManageRegstrDTO);
}
