package kr.co.gitt.kworks.projects.ss.service.cntrctRegstr;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.CntrctRegstrDTO;
import kr.co.gitt.kworks.cmmn.dto.CntrctRegstrSearchDTO;
import kr.co.gitt.kworks.contact.ehojo.model.VTcmCtrtbooksLink;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public interface CntrctRegstrService {

	public List<VTcmCtrtbooksLink> list(CntrctRegstrSearchDTO cntrctRegstrSearchDTO, PaginationInfo paginationInfo);
	
	public CntrctRegstrDTO selectOneCntrctRegstr(String ctrtAcctBookMngNo);
	
	public List<String> listYears();
	
	public List<String> listCodes(String clId);
	
}
