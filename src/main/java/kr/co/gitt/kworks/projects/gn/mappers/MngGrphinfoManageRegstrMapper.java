package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.MngGrphinfoManageRegstrDTO;
import kr.co.gitt.kworks.projects.gn.dto.MngGrphinfoUploadDtlsDTO;
import kr.co.gitt.kworks.projects.gn.model.MngGrphinfoManageRegstr;

public interface MngGrphinfoManageRegstrMapper {
	public Integer listCount(SearchDTO searchDTO);
	
	public List<MngGrphinfoManageRegstrDTO> list(SearchDTO searchDTO);
	
	public MngGrphinfoManageRegstrDTO selectOne(Long regstrSn);

	public List<MngGrphinfoUploadDtlsDTO> selectMngGrphinfoUploadDtls(Long regstrSn);

	public Integer update(
			MngGrphinfoManageRegstr mngGrphinfoManageRegstr);
}
