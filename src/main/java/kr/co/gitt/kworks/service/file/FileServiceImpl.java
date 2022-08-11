package kr.co.gitt.kworks.service.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import kr.co.gitt.kworks.mappers.KwsFileMapper;
import kr.co.gitt.kworks.model.KwsFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class FileServiceImpl
/// kr.co.gitt.kworks.service.file \n
///   ㄴ FileServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | libraleo |
///    | Date | 2016. 8. 3. 오전 9:52:18 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 파일 서비스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("fileService")
public class FileServiceImpl implements FileService {
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;
	
	/// 파일 맵퍼
	@Resource
	KwsFileMapper kwsFileMapper;
	
	/// 파일 아이디 생성 서비스
	@Resource
	EgovIdGnrService kwsFileIdGnrService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#selectOneFile(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsFile selectOneFile(Long fileNo) {
		return kwsFileMapper.selectOne(fileNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#addFile(java.io.File, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile addFile(File file, String fileName, String fileExtsn, String folderName, String fileNamePrefix) throws FdlException, IOException {
		Long fileNo = kwsFileIdGnrService.getNextLongId();
	
		//String uploadPath = messageSource.getMessage("Com.Upload.Path", null, Locale.getDefault());
		//String fileStreCours = uploadPath + folderName + File.separator;
				
		String fileStreCours = getFileStreCours(folderName);
		String orignlFileNm = fileName + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;
		String streFileNm = fileNamePrefix + fileNo + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;
		
		// 디스크에 파일 저장
		FileUtils.forceMkdir(new File(fileStreCours));
		storeFile(file, fileStreCours+streFileNm);
		
		// 디스크에 파일 저장
		KwsFile kwsFile = new KwsFile();
		kwsFile.setFileNo(fileNo);
		kwsFile.setFileStreCours(fileStreCours);
		kwsFile.setStreFileNm(streFileNm);
		kwsFile.setOrignlFileNm(orignlFileNm);
		kwsFile.setFileExtsn(fileExtsn);
		kwsFile.setFileSize(file.length());
		kwsFileMapper.insert(kwsFile);
		
		return kwsFile;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#addFile(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile addFile(MultipartFile multipartFile, String folderName, String fileNamePrefix) throws FdlException, IOException {
		Long fileNo = kwsFileIdGnrService.getNextLongId();
		
		//String uploadPath = messageSource.getMessage("Com.Upload.Path", null, Locale.getDefault());
		//String fileStreCours = uploadPath + folderName + File.separator;
				
		String fileStreCours = getFileStreCours(folderName);
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		String streFileNm = fileNamePrefix + fileNo + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;
		
		// 디스크에 파일 저장
		FileUtils.forceMkdir(new File(fileStreCours));
		File file = storeFile(multipartFile, fileStreCours+streFileNm);
		
		// 데이터베이스에 파일 저장
		KwsFile kwsFile = new KwsFile();
		kwsFile.setFileNo(fileNo);
		kwsFile.setFileStreCours(fileStreCours);
		kwsFile.setStreFileNm(streFileNm);
		kwsFile.setOrignlFileNm(orignlFileNm);
		kwsFile.setFileExtsn(fileExtsn);
		kwsFile.setFileSize(file.length());
		kwsFileMapper.insert(kwsFile);
		
		return kwsFile;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#addFile(java.awt.image.BufferedImage, java.lang.String, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile addFile(BufferedImage bufferedImage, String fileName, String folderName, String fileNamePrefix) throws FdlException, IOException {
		Long fileNo = kwsFileIdGnrService.getNextLongId();
		
		//String uploadPath = messageSource.getMessage("Com.Upload.Path", null, Locale.getDefault());
		//String fileStreCours = uploadPath + folderName + File.separator;
		
		String fileStreCours = getFileStreCours(folderName);
		String fileExtsn = "png";
		String orignlFileNm = fileName + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;
		String streFileNm = fileNamePrefix + fileNo + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;
		
		// 디스크에 파일 저장
		FileUtils.forceMkdir(new File(fileStreCours));
		File file = storeFile(bufferedImage, fileStreCours+streFileNm);
		
		// 데이터베이스에 파일 저장
		KwsFile kwsFile = new KwsFile();
		kwsFile.setFileNo(fileNo);
		kwsFile.setFileStreCours(fileStreCours);
		kwsFile.setStreFileNm(streFileNm);
		kwsFile.setOrignlFileNm(orignlFileNm);
		kwsFile.setFileExtsn(fileExtsn);
		kwsFile.setFileSize(file.length());
		kwsFileMapper.insert(kwsFile);
		
		return kwsFile;
	}
	
	/////////////////////////////////////////////
	/// @fn getFileStreCours
	/// @brief 함수 간략한 설명 : 업로드/익스포트 유형에 따라 파일 저장경로 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param folderName
	/// @return String
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String getFileStreCours(String folderName) {
		if (folderName.toLowerCase() == "export") {
			return messageSource.getMessage("Com.Export.Path", null, Locale.getDefault());
		}
		else {
			return messageSource.getMessage("Com.Upload.Path", null, Locale.getDefault()) + folderName + File.separator;
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#removeFile(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeFile(Long fileNo) {
		KwsFile kwsFile = kwsFileMapper.selectOne(fileNo);
		String filePath = kwsFile.getFileStreCours() + kwsFile.getStreFileNm();
		File file = new File(filePath);
		if(file != null && file.isFile()) {
			file.delete();
		}
		return kwsFileMapper.delete(fileNo);
	}
	
	/////////////////////////////////////////////
	/// @fn storeFile
	/// @brief 함수 간략한 설명 : 디스크에 파일 저장
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param file
	/// @param toFilePath
	/// @return
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private File storeFile(File file, String toFilePath) throws IOException {
		File toFile = new File(toFilePath);
		if(toFile == null || !toFile.isFile()) {
			toFile.createNewFile();
		}
		FileUtils.copyFile(file, toFile);
		return toFile;
	}
	
	/////////////////////////////////////////////
	/// @fn storeFile
	/// @brief 함수 간략한 설명 : 디스크에 파일 저장
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param multipartFile
	/// @param toFilePath
	/// @return
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private File storeFile(MultipartFile multipartFile, String toFilePath) throws IOException {
		File toFile = new File(toFilePath);
		if(toFile == null || !toFile.isFile()) {
			toFile.createNewFile();
		}
		multipartFile.transferTo(toFile);
		return toFile;
	}
	
	/////////////////////////////////////////////
	/// @fn storeFile
	/// @brief 함수 간략한 설명 : 디스크에 이미지 파일 저장
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bufferedImage
	/// @param toFilePath
	/// @return
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private File storeFile(BufferedImage bufferedImage, String toFilePath) throws IOException {
		File toFile = new File(toFilePath);
		if(toFile == null || !toFile.isFile()) {
			toFile.createNewFile();
		}
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(toFile);
			ImageIO.write(bufferedImage, "png", outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(outputStream != null) {
				outputStream.close();
			}
		}
		return toFile;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#getFile(kr.co.gitt.kworks.model.KwsFile)
	/////////////////////////////////////////////
	@Override
	public File getFile(KwsFile kwsFile) {
		return new File(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#addLandFile(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile addLandFile(MultipartFile multipartFile, String folderName,
			String fileNamePrefix) throws FdlException, IOException {
		Long fileNo = kwsFileIdGnrService.getNextLongId();
		
		String uploadPath = "C:/kworks/gn/data/";
		String fileStreCours = uploadPath + folderName + File.separator;
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		String streFileNm = fileNamePrefix + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;

		// 디스크에 파일 저장
		FileUtils.forceMkdir(new File(fileStreCours));
		File file = storeFile(multipartFile, fileStreCours+streFileNm);
		
		// 데이터베이스에 파일 저장
		KwsFile kwsFile = new KwsFile();
		kwsFile.setFileNo(fileNo);
		kwsFile.setFileStreCours(fileStreCours);
		kwsFile.setStreFileNm(streFileNm);
		kwsFile.setOrignlFileNm(orignlFileNm);
		kwsFile.setFileExtsn(fileExtsn);
		kwsFile.setFileSize(file.length());
		kwsFileMapper.insert(kwsFile);
		
		return kwsFile;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#removeLandFile(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public Integer removeLandFile(String docFile) {
		Integer rowCount = 0;
				
		String streFileNm = docFile;
		
		KwsFile kwsFile = kwsFileMapper.selectFileNm(streFileNm);
		
		if(kwsFile != null) {
			String filePath = kwsFile.getFileStreCours() + kwsFile.getStreFileNm();
			File file = new File(filePath);
			
			if(file != null && file.isFile()) {
				file.delete();
			}
			
			kwsFileMapper.deleteFileNm(streFileNm);
		}
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#selectOneLandFile(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile selectOneLandFile(String streFileNm) {
	return kwsFileMapper.selectFileNm(streFileNm);
	}

	@Override
	public KwsFile addYgLandCntFile(MultipartFile multipartFile, Long fileNo,
			String folderName, String fileNamePrefix) throws FdlException,
			IOException {
		String uploadPath = "C:/kworks/yg/data/";
		String fileStreCours = uploadPath + folderName + File.separator;
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		String streFileNm = fileNamePrefix + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;

		// 디스크에 파일 저장
		FileUtils.forceMkdir(new File(fileStreCours));
		File file = storeFile(multipartFile, fileStreCours+streFileNm);
		
		// 데이터베이스에 파일 저장
		KwsFile kwsFile = new KwsFile();
		kwsFile.setFileNo(fileNo);
		kwsFile.setFileStreCours(fileStreCours);
		kwsFile.setStreFileNm(streFileNm);
		kwsFile.setOrignlFileNm(orignlFileNm);
		kwsFile.setFileExtsn(fileExtsn);
		kwsFile.setFileSize(file.length());
		kwsFileMapper.insert(kwsFile);
		
		return kwsFile;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#addSectionPlanFile(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile addSectionPlanFile(MultipartFile multipartFile, Long fileNo,
			String folderName, String fileNamePrefix) throws FdlException,
			IOException {
		String uploadPath = "d:/kworks/gn/data/";
		String fileStreCours = uploadPath + folderName + File.separator;
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		String streFileNm = fileNamePrefix + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;

		// 디스크에 파일 저장
		FileUtils.forceMkdir(new File(fileStreCours));
		File file = storeFile(multipartFile, fileStreCours+streFileNm);
		
		// 데이터베이스에 파일 저장
		KwsFile kwsFile = new KwsFile();
		kwsFile.setFileNo(fileNo);
		kwsFile.setFileStreCours(fileStreCours);
		kwsFile.setStreFileNm(streFileNm);
		kwsFile.setOrignlFileNm(orignlFileNm);
		kwsFile.setFileExtsn(fileExtsn);
		kwsFile.setFileSize(file.length());
		kwsFileMapper.insert(kwsFile);
		
		return kwsFile;
	}
		
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#removeLandFile(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public Integer removeSectionPlanFile(Long fileNo) {
		KwsFile kwsFile = kwsFileMapper.selectOne(fileNo);
		String filePath = kwsFile.getFileStreCours() + kwsFile.getStreFileNm();
		File file = new File(filePath);
		if(file != null && file.isFile()) {
			file.delete();
		}
		return kwsFileMapper.delete(fileNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#addLocalPlanFile(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile addLocalPlanFile(MultipartFile multipartFile, Long fileNo,
			String folderName, String fileNamePrefix) throws FdlException,
			IOException {
		String uploadPath = "d:/kworks/gn/data/";
		String fileStreCours = uploadPath + folderName + File.separator;
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		String streFileNm = fileNamePrefix + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;

		// 디스크에 파일 저장
		FileUtils.forceMkdir(new File(fileStreCours));
		File file = storeFile(multipartFile, fileStreCours+streFileNm);
		
		// 데이터베이스에 파일 저장
		KwsFile kwsFile = new KwsFile();
		kwsFile.setFileNo(fileNo);
		kwsFile.setFileStreCours(fileStreCours);
		kwsFile.setStreFileNm(streFileNm);
		kwsFile.setOrignlFileNm(orignlFileNm);
		kwsFile.setFileExtsn(fileExtsn);
		kwsFile.setFileSize(file.length());
		kwsFileMapper.insert(kwsFile);
		
		return kwsFile;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#removeLocalPlanFile(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public Integer removeLocalPlanFile(Long fileNo) {
		KwsFile kwsFile = kwsFileMapper.selectOne(fileNo);
		String filePath = kwsFile.getFileStreCours() + kwsFile.getStreFileNm();
		File file = new File(filePath);
		if(file != null && file.isFile()) {
			file.delete();
		}
		return kwsFileMapper.delete(fileNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#addVideoManageFile(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String)
	/////////////////////////////////////////////
	@Override
	public KwsFile addVideoManageFile(MultipartFile multipartFile, Long filNo,
		String folderName, String fileNamePrefix) throws FdlException,
		IOException {
		String uploadPath = "d:/kworks/gn/data/";
		String fileStreCours = uploadPath + folderName + File.separator;
		String orignlFileNm = multipartFile.getOriginalFilename();
		String fileExtsn = FilenameUtils.getExtension(orignlFileNm);
		String streFileNm = fileNamePrefix + FilenameUtils.EXTENSION_SEPARATOR_STR + fileExtsn;
		
		// 디스크에 파일 저장
		FileUtils.forceMkdir(new File(fileStreCours));
		File file = storeFile(multipartFile, fileStreCours+streFileNm);
		
		// 데이터베이스에 파일 저장
		KwsFile kwsFile = new KwsFile();
		kwsFile.setFileNo(filNo);
		kwsFile.setFileStreCours(fileStreCours);
		kwsFile.setStreFileNm(streFileNm);
		kwsFile.setOrignlFileNm(orignlFileNm);
		kwsFile.setFileExtsn(fileExtsn);
		kwsFile.setFileSize(file.length());
		kwsFileMapper.insert(kwsFile);
		
		return kwsFile;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.file.FileService#removeVideoManageFile(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public Integer removeVideoManageFile(Long filNo) {
		KwsFile kwsFile = kwsFileMapper.selectOne(filNo);
		String filePath = kwsFile.getFileStreCours() + kwsFile.getStreFileNm();
		File file = new File(filePath);
		if(file != null && file.isFile()) {
			file.delete();
		}
	return kwsFileMapper.delete(filNo);
	}
	
}
