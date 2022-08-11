package kr.co.gitt.kworks.projects.gn.service.spatialInfoRegstr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.MngGrphinfoManageRegstrDTO;
import kr.co.gitt.kworks.projects.gn.dto.MngGrphinfoUploadDtlsDTO;
import kr.co.gitt.kworks.projects.gn.mappers.MngGrphinfoManageRegstrMapper;
import kr.co.gitt.kworks.projects.gn.model.MngGrphinfoManageRegstr;

@Service("spatialInfoRegstrService")
@Profile({"gn_dev", "gn_oper"})
public class SpatialInfoRegstrServiceImpl implements SpatialInfoRegstrService{
	
	@Resource
	MngGrphinfoManageRegstrMapper mngGrphinfoManageRegstrMapper;

	@Override
	public List<MngGrphinfoManageRegstrDTO> listMngGrphinfoManageRegstr(
			SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = mngGrphinfoManageRegstrMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if (totalRecordCount > 0) {
			return mngGrphinfoManageRegstrMapper.list(searchDTO);
		} else {
			return new ArrayList<MngGrphinfoManageRegstrDTO>();
		}
	}

	@Override
	public MngGrphinfoManageRegstrDTO selectOneMngGrphinfoManageRegstr(
			Long regstrSn) {
		return mngGrphinfoManageRegstrMapper.selectOne(regstrSn);
	}

	@Override
	public List<MngGrphinfoUploadDtlsDTO> selectMngGrphinfoUploadDtls(Long regstrSn) {
		return mngGrphinfoManageRegstrMapper.selectMngGrphinfoUploadDtls(regstrSn);
	}

	@Override
	public Integer modifyMngGrphinfoManageRegstr(
			MngGrphinfoManageRegstrDTO mngGrphinfoManageRegstrDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return mngGrphinfoManageRegstrMapper.update(modelMapper.map(mngGrphinfoManageRegstrDTO, MngGrphinfoManageRegstr.class));
	}

}
