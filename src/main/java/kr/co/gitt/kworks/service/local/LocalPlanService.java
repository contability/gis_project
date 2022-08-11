package kr.co.gitt.kworks.service.local;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.model.RdtLoclMa;

public interface LocalPlanService {
	
	public List<RdtLoclMa> listAllFile(RdtLoclMa rdtLoclMa);
	
	public RdtLoclMa selectOneByFtrIdn(Long ftrIdn);
	
	public RdtLoclMa selectOneFile(Long fileNo);
	
	public Integer removeFile(FileDTO fileDTO);
	
	public Integer modifyLocalPlan(FileDTO fileDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException;
	
	public Integer addLocalPlan(FileDTO fileDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException;
}