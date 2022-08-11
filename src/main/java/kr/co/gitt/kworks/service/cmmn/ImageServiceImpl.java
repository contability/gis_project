package kr.co.gitt.kworks.service.cmmn;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.ImageDTO.ImageType;
import kr.co.gitt.kworks.mappers.KwsImageMapper;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.service.file.FileService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class ImageServiceImpl
/// kr.co.gitt.kworks.service.cmmn \n
///   ㄴ ImageServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:32:07 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 이미지 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "image";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX = "KWS_IMAGE_";
	
	/// 썸네일 너비
	private Integer THUMBNAIL_WIDTH = 200;
	
	/// 썸네일 높이
	private Integer THUMBNAIL_HEIGHT = 150;
	
	/// 출력파일 너비 (높이는 너비에 비례해서 자동 계산 됨)
	private Integer OUTPUT_WIDTH = 800;
	
	/// 이미지 맵퍼
	@Resource
	KwsImageMapper kwsImageMapper;
	
	// 이미지 아이디 생성 서비스
	@Resource
	EgovIdGnrService kwsImageIdGnrService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#listAllImage(kr.co.gitt.kworks.model.KwsImage)
	/////////////////////////////////////////////
	@Override
	public List<KwsImage> listAllImage(KwsImage kwsImage) {
		return kwsImageMapper.listAll(kwsImage);
	}
	
	@Override
	public List<KwsImage> listAllImageFile(KwsImage kwsImage) {
		return kwsImageMapper.listAllFile(kwsImage);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#selectOneImage(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsImage selectOneImage(Long imageNo) {
		return kwsImageMapper.selectOne(imageNo);
	}

	@Override
	public KwsImage selectOneByFileNo(Long fileNo) {
		return kwsImageMapper.selectOneByFileNo(fileNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#addImage(kr.co.gitt.kworks.cmmn.dto.ImageDTO, org.springframework.web.multipart.MultipartFile, int, int)
	/////////////////////////////////////////////
	@Override
	public KwsImage addImage(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		KwsImage kwsImage = modelMapper.map(imageDTO, KwsImage.class);
		
		Long imageNo = kwsImageIdGnrService.getNextLongId();
		kwsImage.setImageNo(imageNo);
		
		String fileName = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase(); 
		if(StringUtils.equals(extension, "mp4")) {
			KwsFile videoFile = fileService.addFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
			kwsImage.setImageFileNo(videoFile.getFileNo());
			
			BufferedImage image = createImageFileByVideo(multipartFile);
			
			BufferedImage thumbnailImage = createThumbnailFile(image, thumbnailWidth, thumbnailHeight);
			KwsFile thumbnailFile = fileService.addFile(thumbnailImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
			kwsImage.setThumbFileNo(thumbnailFile.getFileNo());
			
			BufferedImage outputImage = createOupputFile(image);
			KwsFile outputFile = fileService.addFile(outputImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
			kwsImage.setOutptFileNo(outputFile.getFileNo());
		}
		else {
			KwsFile imageFile = fileService.addFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
			kwsImage.setImageFileNo(imageFile.getFileNo());
			
			BufferedImage image = createImageFile(multipartFile);
			
			BufferedImage thumbnailImage = createThumbnailFile(image, thumbnailWidth, thumbnailHeight);
			KwsFile thumbnailFile = fileService.addFile(thumbnailImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
			kwsImage.setThumbFileNo(thumbnailFile.getFileNo());
			
			BufferedImage outputImage = createOupputFile(image);
			KwsFile outputFile = fileService.addFile(outputImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
			kwsImage.setOutptFileNo(outputFile.getFileNo());
		}
		
		kwsImageMapper.insert(kwsImage);
		
		return kwsImage;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#addImage(kr.co.gitt.kworks.cmmn.dto.ImageDTO, org.springframework.web.multipart.MultipartFile)
	/////////////////////////////////////////////
	@Override
	public KwsImage addImage(ImageDTO imageDTO, MultipartFile multipartFile) throws Exception {
		return addImage(imageDTO, multipartFile, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#addImage(kr.co.gitt.kworks.cmmn.dto.ImageDTO, java.lang.String, java.awt.image.BufferedImage)
	/////////////////////////////////////////////
	public KwsImage addImage(ImageDTO imageDTO, String originalFilename, BufferedImage image) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		KwsImage kwsImage = modelMapper.map(imageDTO, KwsImage.class);
		
		Long imageNo = kwsImageIdGnrService.getNextLongId();
		kwsImage.setImageNo(imageNo);
		
		String fileName = FilenameUtils.getBaseName(originalFilename);
		
		KwsFile imageFile = fileService.addFile(image, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
		kwsImage.setImageFileNo(imageFile.getFileNo());
		
		BufferedImage thumbnailImage = createThumbnailFile(image, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
		KwsFile thumbnailFile = fileService.addFile(thumbnailImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
		kwsImage.setThumbFileNo(thumbnailFile.getFileNo());
		
		BufferedImage outputImage = createOupputFile(image);
		KwsFile outputFile = fileService.addFile(outputImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
		kwsImage.setOutptFileNo(outputFile.getFileNo());
		
		kwsImageMapper.insert(kwsImage);
		
		return kwsImage;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#getImageFile(kr.co.gitt.kworks.model.KwsImage, kr.co.gitt.kworks.cmmn.dto.ImageDTO.ImageType)
	/////////////////////////////////////////////
	@Override
	public File getImageFile(KwsImage kwsImage, ImageType imageType) {
		KwsFile kwsFile = null;
		switch(imageType) {
			case IMAGE:
				kwsFile = kwsImage.getImageFile();
				break;
			case OUTPUT:
				kwsFile = kwsImage.getOutptFile();
				break;
			case THUMBNAIL:
				kwsFile = kwsImage.getThumbFile();
				break;
			default:
				logger.warn("지원되지 않는 이미지 타입 입니다.");
				break;
		}
		return fileService.getFile(kwsFile);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#removeImage(kr.co.gitt.kworks.cmmn.dto.ImageDTO)
	/////////////////////////////////////////////
	@Override
	public Integer removeImage(ImageDTO imageDTO) {
		Integer rows = 0;
		ModelMapper modelMapper = new ModelMapper();
		List<KwsImage> kwsImages = kwsImageMapper.listAll(modelMapper.map(imageDTO, KwsImage.class));
		for(KwsImage kwsImage : kwsImages) {
			if(kwsImage.getImageFileNo() != null) {
				fileService.removeFile(kwsImage.getImageFileNo());
			}
			if(kwsImage.getThumbFileNo() != null) {
				fileService.removeFile(kwsImage.getThumbFileNo());	
			}
			if(kwsImage.getOutptFileNo() != null) {
				fileService.removeFile(kwsImage.getOutptFileNo());
			}
			rows += kwsImageMapper.delete(kwsImage.getImageNo());
		}
		return rows;
	}
	
	/////////////////////////////////////////////
	/// @fn createImageFile
	/// @brief 함수 간략한 설명 : 이미지 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param multipartFile
	/// @return
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private BufferedImage createImageFile(MultipartFile multipartFile) throws IOException {
		InputStream inputStream = multipartFile.getInputStream();
		return ImageIO.read(inputStream);
	}
	
	private BufferedImage createImageFileByVideo(MultipartFile multipartFile) throws Exception {
		InputStream inputStream = multipartFile.getInputStream();
		Java2DFrameConverter converter = new Java2DFrameConverter();
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
		grabber.start();
		Frame frame = grabber.grabImage();
		BufferedImage bufferedImage = converter.convert(frame);
		grabber.stop();
		grabber.close();
		return bufferedImage;
	}
	
	/////////////////////////////////////////////
	/// @fn createThumbnailFile
	/// @brief 함수 간략한 설명 : 썸네일 이미지 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param image
	/// @param width
	/// @param height
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private BufferedImage createThumbnailFile(BufferedImage image, int width, int height) {
		return resize(image, width, height);
	}
	
	/////////////////////////////////////////////
	/// @fn createOupputFile
	/// @brief 함수 간략한 설명 : 출력 이미지 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param image
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private BufferedImage createOupputFile(BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		
		int resizeHeight = Math.round((float)height/(float)width*(float)OUTPUT_WIDTH);
		return resize(image, OUTPUT_WIDTH, resizeHeight);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#getBufferdImageByBase64String(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public BufferedImage getBufferdImageByBase64String(String base64Str) throws IOException {
		byte[] bytes = Base64.decodeBase64(base64Str);
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
		
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		graphics.drawImage(image, 0, 0, null);
		
		return bufferedImage;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#resize(java.awt.image.BufferedImage, int, int)
	/////////////////////////////////////////////
	@Override
	public BufferedImage resize(BufferedImage image, int width, int height) {
		int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);


		g.setRenderingHint(RenderingHints.KEY_RENDERING, 
		RenderingHints.VALUE_RENDER_QUALITY);


		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
		RenderingHints.VALUE_ANTIALIAS_ON);


		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		
		return resizedImage; 
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#modifyImage(kr.co.gitt.kworks.cmmn.dto.ImageDTO, org.springframework.web.multipart.MultipartFile, int, int)
	/////////////////////////////////////////////
	@Override
	public KwsImage modifyImage(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		KwsImage kwsImage = modelMapper.map(imageDTO, KwsImage.class);
		
		Long imageNo = imageDTO.getImageNo();
		kwsImage.setImageNo(imageNo);
		
		KwsImage selectImage = kwsImageMapper.selectOne(imageNo);
		if(multipartFile == null) {
			kwsImage.setImageFileNo(selectImage.getImageFileNo());
			kwsImage.setThumbFileNo(selectImage.getThumbFileNo());
			kwsImage.setOutptFileNo(selectImage.getOutptFileNo());
		}
		else {
			Long imageFileNo = selectImage.getImageFileNo();
			Long thumbFileNo = selectImage.getThumbFileNo();
			Long outptFileNo = selectImage.getOutptFileNo();
			if(imageFileNo != null) {
				fileService.removeFile(imageFileNo);	
			}
			if(thumbFileNo != null) {
				fileService.removeFile(thumbFileNo);
			}
			if(outptFileNo != null) {
				fileService.removeFile(outptFileNo);
			}
			
			String fileName = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
			String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase(); 
			if(StringUtils.equals(extension, "mp4")) {
				KwsFile videoFile = fileService.addFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
				kwsImage.setImageFileNo(videoFile.getFileNo());

				BufferedImage image = createImageFileByVideo(multipartFile);
				
				BufferedImage thumbnailImage = createThumbnailFile(image, thumbnailWidth, thumbnailHeight);
				KwsFile thumbnailFile = fileService.addFile(thumbnailImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
				kwsImage.setThumbFileNo(thumbnailFile.getFileNo());
				
				BufferedImage outputImage = createOupputFile(image);
				KwsFile outputFile = fileService.addFile(outputImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
				kwsImage.setOutptFileNo(outputFile.getFileNo());
			}
			else {
				KwsFile imageFile = fileService.addFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
				kwsImage.setImageFileNo(imageFile.getFileNo());
				
				BufferedImage image = createImageFile(multipartFile);
				
				BufferedImage thumbnailImage = createThumbnailFile(image, thumbnailWidth, thumbnailHeight);
				KwsFile thumbnailFile = fileService.addFile(thumbnailImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
				kwsImage.setThumbFileNo(thumbnailFile.getFileNo());
				
				BufferedImage outputImage = createOupputFile(image);
				KwsFile outputFile = fileService.addFile(outputImage, fileName, FOLDER_NAME, FILE_NAME_PREFIX);
				kwsImage.setOutptFileNo(outputFile.getFileNo());
			}
		}
		kwsImageMapper.update(kwsImage);
		return kwsImage;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ImageService#modifyImage(kr.co.gitt.kworks.cmmn.dto.ImageDTO, org.springframework.web.multipart.MultipartFile)
	/////////////////////////////////////////////
	@Override
	public KwsImage modifyImage(ImageDTO imageDTO, MultipartFile multipartFile) throws Exception {
		return addImage(imageDTO, multipartFile, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
	}
	
}
