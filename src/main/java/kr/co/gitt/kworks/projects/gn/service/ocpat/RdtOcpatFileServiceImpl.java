package kr.co.gitt.kworks.projects.gn.service.ocpat;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.projects.gn.mappers.RdtOcpatFileMapper;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpatFile;
import kr.co.gitt.kworks.service.file.FileService;

@Service("rdtOcpatFileService")
@Profile({ "gn_dev", "gn_oper" })
public class RdtOcpatFileServiceImpl implements RdtOcpatFileService {

	// / 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	// / 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "";

	// / 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX = "";

	// RDT_OCPAT_FILE MAPPER
	@Resource
	RdtOcpatFileMapper rdtOcpatFileMapper;

	// 아이디 생성 서비스
	//@Resource
	//EgovIdGnrService rdtOcpatFileIdGnrService;

	// / 파일 서비스
	@Resource
	FileService fileService;
	
	@Override
	public List<RdtOcpatFile> listRdtOcpatFile(RdtOcpatFile rdtOcpatFile) {

		List<RdtOcpatFile> rdtOcpatFileList = rdtOcpatFileMapper.listAll(rdtOcpatFile);

		for (int i = 0; i < rdtOcpatFileList.size(); i++) {
			KwsFile kwsFile = fileService.selectOneFile(rdtOcpatFileList.get(i).getFileNo());
			rdtOcpatFileList.get(i).setKwsFile(kwsFile);
		}

		return rdtOcpatFileList;
	}


	@Override
	public RdtOcpatFile selectOneFile(Long ocpatFileNo) {
		RdtOcpatFile rdtOcpatFile = rdtOcpatFileMapper.selectOne(ocpatFileNo);
		KwsFile kwsFile = fileService.selectOneFile(rdtOcpatFile.getFileNo());
		rdtOcpatFile.setKwsFile(kwsFile);

		return rdtOcpatFile;
	}
	
	@Override
	public RdtOcpatFile selectOneFilebyFileNo(Long fileNo) {
		RdtOcpatFile rdtOcpatFile = rdtOcpatFileMapper.selectOneByFileNo(fileNo);
		return rdtOcpatFile;
	}

	@Override
	public Integer update(RdtOcpatFile rdtOcpatFile, MultipartRequest multipartRequest) {

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		rdtOcpatFile.setUpdusrId(userDTO.getUserId());
		rdtOcpatFile.setLastUpdde(getDate());

		MultipartFile multipartFile = null;
		Long fileNo = null;
		Integer rowCount = 0;

		if (multipartRequest.getFileMap().size() > 0) {
			removeFile(rdtOcpatFile.getFileNo());

			for (Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
				if (!multipartFile.isEmpty()) {
					fileNo = addFile(multipartFile);
				}
			}

			rdtOcpatFile.setFileNo(fileNo);
			rowCount = rdtOcpatFileMapper.update(rdtOcpatFile);
		} else {
			rowCount = rdtOcpatFileMapper.update(rdtOcpatFile);
		}

		return rowCount;
	}

	@Override
	public Integer delete(RdtOcpatFile rdtOcpatFile) {

		removeFile(rdtOcpatFile.getFileNo());
		Integer rowCount = rdtOcpatFileMapper.delete(rdtOcpatFile.getOcpatFileNo());

		return rowCount;
	}

	
	@Override
	public Integer insert(RdtOcpatFile rdtOcpatFile,MultipartRequest multipartRequest) throws FdlException,	IOException, ParseException {
		//Long ocpatFileNo = rdtOcpatFileIdGnrService.getNextLongId();
		//rdtOcpatFile.setOcpatFileNo(ocpatFileNo);

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper
				.getAuthenticatedUser();
		rdtOcpatFile.setWrterId(userDTO.getUserId());
		rdtOcpatFile.setUpdusrId(userDTO.getUserId());

		rdtOcpatFile.setFrstRgsde(getDate());
		rdtOcpatFile.setLastUpdde(getDate());

		for (Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if (!multipartFile.isEmpty()) {
				Long fileNo = addFile(multipartFile);
				rdtOcpatFile.setFileNo(fileNo);
			}
		}

		Integer rowCount = rdtOcpatFileMapper.insert(rdtOcpatFile);

		return rowCount;
	}
	
	protected Long addFile(MultipartFile multipartFile) {

		KwsFile kwsFile = null;
		try {
			kwsFile = fileService.addFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
		} catch (FdlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Long fileNo = kwsFile.getFileNo();

		return fileNo;
	}

	protected void removeFile(Long fileNo) {
		fileService.removeFile(fileNo);
	}

	protected Date getDate() {

		Calendar cal = new GregorianCalendar();

		Date date = new Date(cal.getTimeInMillis());

		return date;
	}
}
