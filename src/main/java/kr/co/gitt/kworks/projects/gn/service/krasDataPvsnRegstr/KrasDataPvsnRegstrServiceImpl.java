package kr.co.gitt.kworks.projects.gn.service.krasDataPvsnRegstr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.KrasDataPvsnRegstrDTO;
import kr.co.gitt.kworks.projects.gn.dto.KrasDataPvsnRegstrVO;
import kr.co.gitt.kworks.projects.gn.mappers.MngKrasDataPvsnRegstrMapper;
import kr.co.gitt.kworks.projects.gn.model.MngKrasDataPvsnRegstr;

@Service("krasDataPvsnRegstrService")
@Profile({"gn_dev", "gn_oper"})
public class KrasDataPvsnRegstrServiceImpl implements KrasDataPvsnRegstrService {
	
	@Resource
	MngKrasDataPvsnRegstrMapper mngKrasDataPvsnRegstrMapper;
	
	// ID 생성 서비스
	@Resource
	EgovIdGnrService spatialInfoDataIdGnrService;
	
	@Override
	public List<KrasDataPvsnRegstrVO> listMngKrasDataPvsnRegstr(
			SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = listCountKrasDataPvsnRegstr(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if (totalRecordCount > 0) {
			return mngKrasDataPvsnRegstrMapper.list(searchDTO);
		} else {
			return new ArrayList<KrasDataPvsnRegstrVO>();
		}
	}

	private Integer listCountKrasDataPvsnRegstr(SearchDTO searchDTO) {
		return mngKrasDataPvsnRegstrMapper.listCount(searchDTO);
	}
	
	@Override
	public KrasDataPvsnRegstrVO selectOnrMngKrasDataPvsnRegstr(Long dtaNo) {
		return mngKrasDataPvsnRegstrMapper.selectOne(dtaNo);
	}

	@Override
	public Integer addMngKrasDataPvsnRegstr(
			KrasDataPvsnRegstrDTO krasDataPvsnRegstrDTO) throws FdlException {
		Long dtaNo = spatialInfoDataIdGnrService.getNextLongId();
		krasDataPvsnRegstrDTO.setDtaNo(dtaNo);
		
		ModelMapper modelMapper = new ModelMapper();
		MngKrasDataPvsnRegstr mngKrasDataPvsnRegstr = modelMapper.map(krasDataPvsnRegstrDTO, MngKrasDataPvsnRegstr.class);
		
		Integer rowCount = mngKrasDataPvsnRegstrMapper.insert(mngKrasDataPvsnRegstr);
		updateManageNo();
		return rowCount;
	}

	private void updateManageNo() {
		mngKrasDataPvsnRegstrMapper.updateManageNo();
	}

	@Override
	public Integer modifyMngKrasDataPvsnRegstr(
			KrasDataPvsnRegstrDTO krasDataPvsnRegstrDTO) {
		ModelMapper modelMapper = new ModelMapper();
		MngKrasDataPvsnRegstr mngKrasDataPvsnRegstr = modelMapper.map(krasDataPvsnRegstrDTO, MngKrasDataPvsnRegstr.class);
		
		Integer rowCount = mngKrasDataPvsnRegstrMapper.update(mngKrasDataPvsnRegstr);
		updateManageNo();
		return rowCount;
	}

	@Override
	public Integer removeMngKrasDataPvsnRegstr(Long dtaNo) {
		Integer rowCount = mngKrasDataPvsnRegstrMapper.delete(dtaNo);
		updateManageNo();
		return rowCount;
	}
}
