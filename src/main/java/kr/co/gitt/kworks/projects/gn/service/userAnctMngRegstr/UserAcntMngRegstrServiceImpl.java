package kr.co.gitt.kworks.projects.gn.service.userAnctMngRegstr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.mappers.MngUserAuthorHistMapper;
import kr.co.gitt.kworks.projects.gn.model.MngUserAuthorHist;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("userAcntMngRegstrService")
@Profile({"gn_dev", "gn_oper"})
public class UserAcntMngRegstrServiceImpl implements UserAcntMngRegstrService{
	
	@Resource
	MngUserAuthorHistMapper mngUserAuthorHistMapper;


	@Override
	public MngUserAuthorHist selectMngUserAuthorHist(Long sn) {
		return mngUserAuthorHistMapper.selectOne(sn);
	}

	@Override
	public Integer updateMngUserAuthorHist(MngUserAuthorHist mngUserAuthorHist) {
		return mngUserAuthorHistMapper.update(mngUserAuthorHist);
	}

	@Override
	public Integer deleteMngUserAuthorHist(Long sn) {
		return mngUserAuthorHistMapper.delete(sn);
	}

	@Override
	public List<MngUserAuthorHist> listMngUserAuthorHist(SearchDTO searchDTO) {
		return mngUserAuthorHistMapper.list(searchDTO);
	}

	@Override
	public List<MngUserAuthorHist> listMngUserAuthorHist(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = mngUserAuthorHistMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0){
			return listMngUserAuthorHist(searchDTO);
		}else{
			return new ArrayList<MngUserAuthorHist>();
		}
	}

}
