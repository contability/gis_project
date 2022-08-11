package kr.co.gitt.kworks.service.section;

import java.io.IOException;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.model.RdtSectMa;

public interface SectionPlanService {
	
	public RdtSectMa selectOneFile(Long ftrIdn);
	
	public Integer modifySectionPlan(FileDTO fileDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException;

}