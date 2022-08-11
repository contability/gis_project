package kr.co.gitt.kworks.service.video;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.mappers.RdtVideoMaMapper;
import kr.co.gitt.kworks.model.RdtVideoMa;
import kr.co.gitt.kworks.service.file.FileService;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("videoManageService")
@Profile({"gn_dev", "gn_oper"})
public class VideoManageServiceImpl implements VideoManageService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "video";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX;
	
	// 영상관리 파일 맵퍼
	@Resource
	RdtVideoMaMapper rdtVideoMaMapper;
	
	//파일 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsFileIdGnrService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	@Override
	public RdtVideoMa selectOneFile(Long ftrIdn) {
		return rdtVideoMaMapper.selectOne(ftrIdn);
	}
	
	@Override
	public Integer modifyVideoManage(FileDTO fileDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		
		Integer cnt = 0;
		
		Long ftrIdn = fileDTO.getFtrIdn();
		RdtVideoMa rdtVideoMa = new RdtVideoMa();
		MultipartFile multipartFile = null;
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					// 상행 영상
					if(StringUtils.equals("videoManageUpFile", key)) {
						rdtVideoMa.setFtrIdn(ftrIdn);
						
						List<RdtVideoMa> rdtVideoMaData = rdtVideoMaMapper.list(rdtVideoMa);
						
						if(rdtVideoMaData.size() > 0) {
							Long fileNo = rdtVideoMaData.get(0).getUpNo();
							fileService.removeVideoManageFile(fileNo);
						}
					}
					// 하행 영상
					else if(StringUtils.equals("videoManageDownFile", key)) {
						rdtVideoMa.setFtrIdn(ftrIdn);
						
						List<RdtVideoMa> rdtVideoMaData = rdtVideoMaMapper.list(rdtVideoMa);
						
						if(rdtVideoMaData.size() > 0) {
							Long fileNo = rdtVideoMaData.get(0).getDownNo();
							fileService.removeVideoManageFile(fileNo);
						}
					}
				}
				// 파일 등록
				multipartFile = entry.getValue();
				String orignlFileNm = multipartFile.getOriginalFilename();
				String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					/// 상행 영상
					if(StringUtils.equals("videoManageUpFile", key)){
						Long fileNo = kwsFileIdGnrService.getNextLongId();
						rdtVideoMa.setUpNo(fileNo);
						rdtVideoMa.setFtrIdn(ftrIdn);
						
						if(fileExtsn.equals("mp4")) {
							rdtVideoMa.setUpFile("VIDEO_UP" + fileNo + ".mp4");
						}
						rdtVideoMa.setUpFile(orignlFileNm);
						rdtVideoMaMapper.updateVideoUp(rdtVideoMa);

						FILE_NAME_PREFIX = "VIDEO_UP" + fileNo;
						fileService.addVideoManageFile(multipartFile, fileNo, FOLDER_NAME, FILE_NAME_PREFIX);
					} 
					/// 하행 영상
					else if(StringUtils.equals("videoManageDownFile", key)){
						Long fileNo = kwsFileIdGnrService.getNextLongId();
						rdtVideoMa.setDownNo(fileNo);
						rdtVideoMa.setFtrIdn(ftrIdn);
						
						if(fileExtsn.equals("mp4")) {
							rdtVideoMa.setDownFile("VIDEO_DOWN" + fileNo + ".mp4");
						}
						rdtVideoMa.setDownFile(orignlFileNm);
						rdtVideoMaMapper.updateVideoDown(rdtVideoMa);

						FILE_NAME_PREFIX = "VIDEO_DOWN" + fileNo;
						fileService.addVideoManageFile(multipartFile, fileNo, FOLDER_NAME, FILE_NAME_PREFIX);
					} 
				}
			}
		}
		return cnt;
	}
	
}