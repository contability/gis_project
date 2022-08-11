package kr.co.gitt.kworks.service.video;

import java.io.IOException;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.model.RdtVideoMa;

public interface VideoManageService {
	
	public RdtVideoMa selectOneFile(Long ftrIdn);
	
	public Integer modifyVideoManage(FileDTO fileDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException;
}