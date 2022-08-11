package kr.co.gitt.kworks.service.mobile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.co.service.CompressService;
import kr.co.gitt.kworks.mappers.KwsDataAuthorMapper;
import kr.co.gitt.kworks.mappers.KwsDataMapper;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.projects.gc.mappers.MobileExportHtMapper;
import kr.co.gitt.kworks.projects.gc.model.MobileConfig;
import kr.co.gitt.kworks.projects.gc.model.MobileExportHt;
import kr.co.gitt.kworks.projects.gc.model.MobileFolder;
import kr.co.gitt.kworks.service.export.ShapeExportService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;


@Service("mobileExportService")
@Profile({"gc_dev", "gc_oper"})
public class MobileExportServiceImpl implements MobileExportService {

	/**
	 * 로거
	 */
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 폴더명
	 */
	private final String DIRECTORY_NAME = "mobile";
	
	/**
	 * 폴더명
	 */
	private final String DIRECTORY_CONFIG = "config";

	/**
	 * 파일명
	 */
	private final String FILE_NAME = "config.json";
	

	/**
	 *  메세지 소스
	 */
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/**
	 * 내려받기 이력
	 */
	@Resource
	MobileExportHtMapper mobileExportHtMapper;
	
	/**
	 *  내려받기 이력 시퀀스 서비스
	 */
	@Resource
	EgovIdGnrService mobileExportHtIdGnrService;
	
	/**
	 * 데이터 권한 관리 맵퍼
	 */
	@Resource
	KwsDataAuthorMapper kwsDataAuthorMapper;
	
	/**
	 *  데이터 맵퍼
	 */
	@Resource
	KwsDataMapper kwsDataMapper;

	/**
	 * Shape 내보내기 서비스
	 */
	@Resource(name="shapeExportService")
	ShapeExportService shapeExportService;
	
	/**
	 * 압축 서비스
	 */
	@Resource(name="compressService")
	CompressService compressService;
	
	
	@Override
	public Integer listCount(ExportSearchDTO searchDTO) {
		
		return mobileExportHtMapper.listCount(searchDTO);
	}
	
	@Override
	public List<MobileExportHt> list(ExportSearchDTO searchDTO) {
		
		return mobileExportHtMapper.list(searchDTO);
	}

	@Override
	public String export() {
		// 내보내기 경로
		String exportPath = messageSource.getMessage("Com.Export.Path", null, Locale.getDefault());
		if (!exportPath.endsWith(File.separator))
			exportPath += File.separator;
		
		// 설정파일 열기 
		String configPath = exportPath + DIRECTORY_CONFIG + File.separator + FILE_NAME;
		File configFile = new File(configPath);
		
		String result = null;
		if(configFile.exists() && configFile.isFile()) {
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(configFile.getPath());
				result = IOUtils.toString(inputStream, "UTF-8");
			} catch (IOException e) {
				logger.error("설정파일 오픈 에러 : " + configPath);
				logger.error(e.getMessage());
			} finally {
				try {
					if(inputStream != null) {
						inputStream.close();
					}
				}
				catch(Exception ex) {
					logger.error("설정파일 딛기 에러 : " + ex.getMessage());
					logger.error(ex.getMessage());
				}
			}
		}

		if (result == null || result.length() <= 0) {
			logger.error("설정파일을 열지 못 했습니다.");
			return null;
		}
		
		result = result.trim();
		if(!result.startsWith("{")) {
			result = result.substring(result.indexOf("{"));
		}
		
		MobileConfig config = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			config = objectMapper.readValue(result, MobileConfig.class);
		}
		catch(Exception ex) {
			logger.error("설정파일을 파싱하지 못했습니다.");
			logger.error(ex.getMessage());
		}
		
		if (config == null) {
			logger.error("설정파일 객체화 실패.");
			return null;
		}
		
		List<MobileFolder> folders = config.getFolders();
		if (folders == null || folders.size() <= 0) {
			logger.error("설정파일에 내용이 없습니다.");
			return null;
		}

//		// 권한체크
//		Map<String, KwsData> kwsDatas = new HashMap<String, KwsData>();
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("authorGroupNo", authorGroupNo);
//		
//		List<KwsDataAuthor> listAuthor = kwsDataAuthorMapper.listAll(params);	
//		for (KwsDataAuthor author : listAuthor) {
//			if ("Y".equalsIgnoreCase(author.getExportAt())) {
//				KwsData data = kwsDataMapper.selectOne(author.getDataId());
//				if (data != null)
//					kwsDatas.put(data.getDataId(), data);
//			}
//		}
//
//		if (kwsDatas.size() <= 0) {
//			logger.error("권한을 만족하는 데이터가 없습니다.");
//			return null;
//		}
		
		// 내보내기 경로 생성
		String rootPath = exportPath + DIRECTORY_NAME;
		File rootDir = new File(rootPath);
//		try {
//			if (rootDir.exists()) {
//				FileUtils.deleteDirectory(rootDir);
//			}
//		}
//		catch(Exception ex) {
//			ex.printStackTrace();
//		}
		rootPath = rootDir.getPath();
		
		// 사용자 정보
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		String fileName = userDTO.getUserId() + "_" + System.currentTimeMillis();
		String path = rootPath + File.separator + fileName;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		path = dir.getPath();

		boolean isCreate = false;
		try {
			for (MobileFolder folder : folders) {
				String subPath = path + File.separator + folder.getName();
				File subDir = new File(subPath);
				if (!subDir.exists()) {
					subDir.mkdirs();
				}
				
				List<String> layers = folder.getLayers();
				for (String layer : layers) {
					KwsData data = kwsDataMapper.selectOne(layer);
					if (data == null)
						continue;
					
					long start = System.currentTimeMillis();
					shapeExportService.export(data, subPath);
					long end = System.currentTimeMillis() - start;
					logger.debug(data.getDataAlias() + "(" + data.getDataId() + ") : export time - "  + end + "ms" );
				}
			}
			
			isCreate = true;
		}
		catch(Exception ex) {
			logger.warn(ex.getLocalizedMessage());
			isCreate = false;
		}
		
		// 압축파일 생성
		try
		{
			String zipPath = null;
			if (isCreate) {
				zipPath = rootPath + File.separator + fileName + FilenameUtils.EXTENSION_SEPARATOR_STR + "zip";
				compressService.compressChild(path, zipPath);
			}
			FileUtils.deleteDirectory(dir);
			
			if (zipPath != null && zipPath.length() > 0) {
				File file = new File(zipPath);
				if (file.exists()) {
					Long extNo = mobileExportHtIdGnrService.getNextLongId();
					MobileExportHt exportInfo = new MobileExportHt();
					exportInfo.setExtNo(extNo);
					exportInfo.setExtUser(userDTO.getUserId());
					
					mobileExportHtMapper.insert(exportInfo);
					
					return zipPath;
				}
			}
			
			return "";
		}
		catch(Exception ex) {
			logger.warn(ex.getLocalizedMessage());
		}
		
		return null;
	}
}
