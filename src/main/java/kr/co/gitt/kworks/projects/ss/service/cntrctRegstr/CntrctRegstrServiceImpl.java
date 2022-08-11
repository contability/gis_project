package kr.co.gitt.kworks.projects.ss.service.cntrctRegstr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.CntrctRegstrDTO;
import kr.co.gitt.kworks.cmmn.dto.CntrctRegstrSearchDTO;
import kr.co.gitt.kworks.contact.ehojo.mappers.CntrctRegstrMapper;
import kr.co.gitt.kworks.contact.ehojo.model.VTcmCtrtbooksLink;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("cntrctRegstrService")
@Profile({"ss_dev", "ss_oper"})
public class CntrctRegstrServiceImpl implements CntrctRegstrService {
	
	@Resource
	CntrctRegstrMapper cntrctRegstrMapper;
	
	@Override
	public List<VTcmCtrtbooksLink> list(CntrctRegstrSearchDTO cntrctRegstrSearchDTO, PaginationInfo paginationInfo){
		Integer totalRecordCount = cntrctRegstrMapper.listCount(cntrctRegstrSearchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return list(cntrctRegstrSearchDTO);
		}
		else {
			return new ArrayList<VTcmCtrtbooksLink>();
		}
	}
	
	public List<VTcmCtrtbooksLink> list(CntrctRegstrSearchDTO cntrctRegstrSearchDTO){
		return cntrctRegstrMapper.list(cntrctRegstrSearchDTO);
		
	}
	
	@Override
	public CntrctRegstrDTO selectOneCntrctRegstr(String ctrtAcctBookMngNo) {
		return cntrctRegstrMapper.selectOne(ctrtAcctBookMngNo);
	}

	@Override
	public List<String> listYears() {
		return cntrctRegstrMapper.listYears();
	}

	@Override
	public List<String> listCodes(String clId) {
		return cntrctRegstrMapper.listCodes(clId);
	}
	
}
