package kr.co.gitt.kworks.service.local;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




import java.util.Locale;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.mappers.RdtLoclMaMapper;
import kr.co.gitt.kworks.model.RdtLoclMa;
import kr.co.gitt.kworks.service.file.FileService;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("localPlanService")
@Profile({"gn_dev", "gn_oper"})
public class LocalPlanServiceImpl implements LocalPlanService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "local";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX = "KWS_LOCAL_";
	
	/// 단위도면 관리 맵퍼
	@Resource
	RdtLoclMaMapper rdtLoclMaMapper;
	
	//파일 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsFileIdGnrService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	@Override
	public List<RdtLoclMa> listAllFile(RdtLoclMa rdtLoclMa) {
		return rdtLoclMaMapper.listAll(rdtLoclMa);
	}
	
	@Override
	public RdtLoclMa selectOneByFtrIdn(Long ftrIdn) {
		return rdtLoclMaMapper.selectOneByFtrIdn(ftrIdn);
	}
	
	@Override
	public RdtLoclMa selectOneFile(Long fileNo) {
		return rdtLoclMaMapper.selectOne(fileNo);
	}
	
	@Override
	public Integer removeFile(FileDTO fileDTO) {
		Integer rows = 0;
		ModelMapper modelMapper = new ModelMapper();
		List<RdtLoclMa> rdtLoclMas = rdtLoclMaMapper.listAll(modelMapper.map(fileDTO, RdtLoclMa.class));
		for(RdtLoclMa rdtLoclMa : rdtLoclMas) {
			if(rdtLoclMa.getFileNo() != null) {
				fileService.removeFile(rdtLoclMa.getFileNo());
			}
			rows += rdtLoclMaMapper.deleteByFileNo(rdtLoclMa.getFileNo());
		}
		return rows;
	}
	
	@Override
	public Integer modifyLocalPlan(FileDTO fileDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
	
		Integer cnt = 0;
		
		Long ftrIdn = fileDTO.getFtrIdn();
		RdtLoclMa rdtLoclMa = new RdtLoclMa();
		MultipartFile multipartFile = null;
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					
					if(StringUtils.equals("localPlanFile", key)) {
						rdtLoclMa.setFtrIdn(ftrIdn);
						
						List<RdtLoclMa> rdtLoclMaData = rdtLoclMaMapper.listAll(rdtLoclMa);
						
						if(rdtLoclMaData.size() > 0) {
							Long fileNo = rdtLoclMaData.get(0).getFileNo();
							
							rdtLoclMaMapper.deleteByFileNo(fileNo);
							fileService.removeLocalPlanFile(fileNo);
						}
					}
				}
				// 신규파일 등록
				Long fileNo = kwsFileIdGnrService.getNextLongId();
				String secDis = fileDTO.getSecDis();
				String lclCde = fileDTO.getLclCde();
				
				rdtLoclMa.setFtrIdn(ftrIdn);
				rdtLoclMa.setFileNo(fileNo);
				rdtLoclMa.setSecDis(secDis);
				rdtLoclMa.setLclCde(lclCde);
				
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd", Locale.KOREA);
				Date today = new Date();
				String frstRgsde = formatter.format(today);
				rdtLoclMa.setFrstRgsde(frstRgsde);
				
				String orignlFileNm = multipartFile.getOriginalFilename();
				String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
				
				FILE_NAME_PREFIX = "LOCAL_" + fileNo;
				fileService.addLocalPlanFile(multipartFile, fileNo, FOLDER_NAME, FILE_NAME_PREFIX);
				
				if(fileExtsn.equals("DWG")){
					rdtLoclMa.setDocFile("LOCAL_" + fileNo + ".dwg");
				}
				rdtLoclMa.setDocFile(orignlFileNm);
				
				rdtLoclMaMapper.insert(rdtLoclMa);
			}
		}
		return cnt;
	}
	
	@Override
	public Integer addLocalPlan(FileDTO fileDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException  {
		
		Integer cnt = 0;
		
		Long ftrIdn = fileDTO.getFtrIdn();
		Long fileNo = kwsFileIdGnrService.getNextLongId();
		String secDis = fileDTO.getSecDis();
		String lclCde = fileDTO.getLclCde();
		
		RdtLoclMa rdtLoclMa = new RdtLoclMa();
		rdtLoclMa.setFtrIdn(ftrIdn);
		rdtLoclMa.setFileNo(fileNo);
		rdtLoclMa.setSecDis(secDis);
		rdtLoclMa.setLclCde(lclCde);
		
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd", Locale.KOREA);
		Date today = new Date();
		String frstRgsde = formatter.format(today);
		rdtLoclMa.setFrstRgsde(frstRgsde);
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				String orignlFileNm = multipartFile.getOriginalFilename();
				String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
				
				FILE_NAME_PREFIX = "LOCAL_" + fileNo;
				fileService.addLocalPlanFile(multipartFile, fileNo, FOLDER_NAME, FILE_NAME_PREFIX);
				
				if(fileExtsn.equals("DWG")){
					rdtLoclMa.setDocFile("LOCAL_" + fileNo + ".dwg");
				}
				rdtLoclMa.setDocFile(orignlFileNm);
			}
		}
		
		rdtLoclMaMapper.insert(rdtLoclMa);
		
		return cnt;
	}
}