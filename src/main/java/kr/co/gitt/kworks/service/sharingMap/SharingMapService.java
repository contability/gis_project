package kr.co.gitt.kworks.service.sharingMap;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.gitt.kworks.dto.SharingMapDTO;
import kr.co.gitt.kworks.model.CmtCnrsMap;

/////////////////////////////////////////////
/// @class SharingMapService
/// kr.co.gitt.kworks.service.sharingMap \n
///   ㄴ SharingMapService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2019. 5. 15. |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 이미지 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SharingMapService {
	
	/////////////////////////////////////////////
	/// @fn importKml
	/// @brief 함수 간략한 설명 : KML파일 임포트 
	/// @remark
	/// - 함수의 상세 설명 : KML 파일을 파싱하여 저장
	/// @param SharingMapDTO
	/// @param multipartRequest
	/// @return boolean 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public boolean importKml(MultipartFile multipartFile, SharingMapDTO sharingMapDTO) throws Exception;
	
	public SharingMapDTO addSharingMap(SharingMapDTO sharingMapDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;
	
	public SharingMapDTO addSharingMap(SharingMapDTO sharingMapDTO) throws Exception;
	
	public List<SharingMapDTO> listAll();
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 공유지도 단건조회 
	/// @remark
	/// - 공유지도 단건조회
	/// @param sharingMapDTO
	/// @return sharingMapDTO 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SharingMapDTO selectOne(SharingMapDTO sharingMapDTO);
	
	public SharingMapDTO modifySharingMap(SharingMapDTO sharingMapDTO, MultipartFile multipartFile, int thumbnailWidth, int thumbnailHeight) throws Exception;
	
	public Integer removeSharingMap(SharingMapDTO sharingMapDTO);
}
