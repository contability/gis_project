package kr.co.gitt.kworks.service.section;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.mappers.RdtSectMaMapper;
import kr.co.gitt.kworks.model.RdtSectMa;
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

@Service("sectionPlanService")
@Profile({"gn_dev", "gn_oper"})
public class SectionPlanServiceImpl implements SectionPlanService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "section";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX;
	
	/// 구간도면 파일 맵퍼
	@Resource
	RdtSectMaMapper rdtSectMaMapper;
	
	//파일 시퀀스 서비스
	@Resource
	EgovIdGnrService kwsFileIdGnrService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	@Override
	public RdtSectMa selectOneFile(Long ftrIdn) {
		return rdtSectMaMapper.selectOne(ftrIdn);
	}
	
	@Override
	public Integer modifySectionPlan(FileDTO fileDTO, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		
		Integer cnt = 0;
		
		Long ftrIdn = fileDTO.getFtrIdn();
		RdtSectMa rdtSectMa = new RdtSectMa();
		MultipartFile multipartFile = null;
		
		if(multipartRequest.getFileMap().entrySet() != null) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					String key = entry.getKey();
					
					if(StringUtils.equals("sectionPlanFile", key)) {
						rdtSectMa.setFtrIdn(ftrIdn);
						
						List<RdtSectMa> rdtSectMaData = rdtSectMaMapper.list(rdtSectMa);
						
						if(rdtSectMaData.size() > 0) {
							Long fileNo = rdtSectMaData.get(0).getFileNo();
							
							rdtSectMa.setFileNo(fileNo);
							rdtSectMaMapper.delete(rdtSectMa);
							fileService.removeSectionPlanFile(fileNo);
						}
					}
				}
			//신규파일 등록
			addSectionPlanFiles(ftrIdn, multipartRequest);
			}
		}
		return cnt;
	}
	
	protected void addSectionPlanFiles(Long ftrIdn, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException{
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				addSectionPlanFile(ftrIdn, multipartFile);
			}
		}
	}
	
	protected void addSectionPlanFile(Long ftrIdn, MultipartFile multipartFile) throws FdlException, IllegalStateException, IOException {

		Long fileNo = kwsFileIdGnrService.getNextLongId();
		RdtSectMa rdtSectMa = new RdtSectMa();
		rdtSectMa.setFtrIdn(ftrIdn);
		rdtSectMa.setFileNo(fileNo);
		
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		
		FILE_NAME_PREFIX = "SECTION_" + fileNo;
		fileService.addSectionPlanFile(multipartFile, fileNo, FOLDER_NAME, FILE_NAME_PREFIX);
		
		if(fileExtsn.equals("DWG")){
			rdtSectMa.setDocFile("SECTION_" + fileNo + ".dwg");
		}
		rdtSectMa.setDocFile(orignlFileNm);
		
		rdtSectMaMapper.insert(rdtSectMa);
	}
}