package kr.co.gitt.kworks.co.service;


/////////////////////////////////////////////
/// @class CompressService
/// kworks.co.service \n
///   ㄴ CompressService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 5. 25. 오전 10:32:50 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 압축 서비스입니다.
///   -# 
/////////////////////////////////////////////
public interface CompressService {

	/////////////////////////////////////////////
	/// @fn compressFolder
	/// @brief 함수 간략한 설명 : 폴더 압축
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param folderPath
	/// @param zipPath
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void compressFolder(String folderPath, String zipPath) throws Exception;

	/**
	 * 하위폴더 포함 압축
	 * @param folderPath
	 * @param zipPath
	 * @throws Exception
	 */
	void compressChild(String folderPath, String zipPath) throws Exception;
	
}
