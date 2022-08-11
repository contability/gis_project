package kr.co.gitt.kworks.projects.gn.service.ocpat;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.projects.gn.model.RdtOcpatFile;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;


public interface RdtOcpatFileService {
	
	public List<RdtOcpatFile> listRdtOcpatFile(RdtOcpatFile rdtOcpatFile);
	
	public RdtOcpatFile selectOneFile(Long ocpatFileNo);
	
	public RdtOcpatFile selectOneFilebyFileNo(Long fileNo);

	public Integer insert(RdtOcpatFile rdtOcpatFile, MultipartRequest multipartRequest) throws FdlException, IOException, ParseException;

	public Integer delete(RdtOcpatFile rdtOcpatFile);

	public Integer update(RdtOcpatFile rdtOcpatFile, MultipartRequest multipartRequest);
}
