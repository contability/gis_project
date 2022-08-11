package kr.co.gitt.kworks.service.cmmn;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.gitt.kworks.model.KwsAtch;

public interface AtchFileService {
	
	public static final String FOLDER_NAME = "atch";
	
	public static final String FILE_NAME_PREFIX = "KWS_ATCH_";

	public List<KwsAtch> listAtchFileByFtf(KwsAtch kwsAtch);
	
	public Integer insertAtchFile(KwsAtch kwsAtch, MultipartRequest mr) throws FdlException, IOException ;
	
	public Boolean deleteAtchFiles(Long[] fileNos);
	
	public void downloadAtchFiles(Long[] fileNos, String dataId, HttpServletResponse response, String userAgent) throws IOException ;
	
	public void previewAtchFile(Long fileNo, String userAgent, HttpServletResponse response) throws IOException  ;
}
