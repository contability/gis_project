package kr.co.gitt.kworks.service.cmmn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.ImageDTO.ImageType;
import kr.co.gitt.kworks.model.KwsImage;

import org.springframework.web.multipart.MultipartFile;

/////////////////////////////////////////////
/// @class ImageService
/// kr.co.gitt.kworks.service.cmmn \n
///   ㄴ ImageService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:31:35 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 이미지 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ImageService {
	
	/////////////////////////////////////////////
	/// @fn listAllImage
	/// @brief 함수 간략한 설명 : 이미지 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsImage
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsImage> listAllImage(KwsImage kwsImage);
	
	public List<KwsImage> listAllImageFile(KwsImage kwsImage);
	
	/////////////////////////////////////////////
	/// @fn selectOneImage
	/// @brief 함수 간략한 설명 : 이미지 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsImage selectOneImage(Long imageNo);

	/**
	 * 
	 * @param fileNo
	 * @return
	 */
	public KwsImage selectOneByFileNo(Long fileNo);

	/////////////////////////////////////////////
	/// @fn addImage
	/// @brief 함수 간략한 설명 : 이미지 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageDTO
	/// @param multipartFile
	/// @param thumbnailWidth
	/// @param thumbnailHeight
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsImage addImage(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn addImage
	/// @brief 함수 간략한 설명 : 이미지 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageDTO
	/// @param multipartFile
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsImage addImage(ImageDTO imageDTO, MultipartFile multipartFile) throws Exception;

	/////////////////////////////////////////////
	/// @fn addImage
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageDTO
	/// @param originalFilename
	/// @param image
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsImage addImage(ImageDTO imageDTO, String originalFilename, BufferedImage image) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn getImageFile
	/// @brief 함수 간략한 설명 : 이미지 파일 가져오기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsImage
	/// @param imageType
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public File getImageFile(KwsImage kwsImage, ImageType imageType);
	
	/////////////////////////////////////////////
	/// @fn removeImage
	/// @brief 함수 간략한 설명 : 이미지 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeImage(ImageDTO imageDTO);
	
	/////////////////////////////////////////////
	/// @fn getBufferdImageByBase64String
	/// @brief 함수 간략한 설명 : Base64 String 형태의 이미지를 BufferedImage로 변환 합니다.
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param base64Str
	/// @return
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public BufferedImage getBufferdImageByBase64String(String base64Str) throws IOException;
	
	/////////////////////////////////////////////
	/// @fn resize
	/// @brief 함수 간략한 설명 : 이미지 리사이즈
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
	public BufferedImage resize(BufferedImage image, int width, int height);
	
	/////////////////////////////////////////////
	/// @fn modifyImage
	/// @brief 함수 간략한 설명 : 이미지 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageDTO
	/// @param multipartFile
	/// @param thumbnailWidth
	/// @param thumbnailHeight
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsImage modifyImage(ImageDTO imageDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modifyImage
	/// @brief 함수 간략한 설명 : 이미지 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageDTO
	/// @param multipartFile
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsImage modifyImage(ImageDTO imageDTO, MultipartFile multipartFile) throws Exception;
	
}
