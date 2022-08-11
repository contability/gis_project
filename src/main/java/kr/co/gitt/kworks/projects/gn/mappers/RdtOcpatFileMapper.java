package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.RdtOcpatFile;


public interface RdtOcpatFileMapper {
	
	public List<RdtOcpatFile> listAll(RdtOcpatFile rdtOcpatFile);

	public List<RdtOcpatFile> listAllFile(RdtOcpatFile rdtOcpatFile);

	// ///////////////////////////////////////////
	// / @fn selectOne
	// / @brief 함수 간략한 설명 : 단 건 검색
	// / @remark
	// / - 함수의 상세 설명 :
	// / @param imageNo
	// / @return
	// /
	// /~~~~~~~~~~~~~{.java}
	// / // 핵심코드
	// /~~~~~~~~~~~~~
	// ///////////////////////////////////////////
	public RdtOcpatFile selectOne(Long ocpatFileNo);

	/**
	 * 파일넘버로 검색
	 * 
	 * @param fileNo
	 *            - 파일번호
	 * @return
	 */
	public RdtOcpatFile selectOneByFileNo(Long fileNo);

	// ///////////////////////////////////////////
	// / @fn insert
	// / @brief 함수 간략한 설명 : 등록
	// / @remark
	// / - 함수의 상세 설명 :
	// / @param rdtOcpatFile
	// / @return
	// /
	// /~~~~~~~~~~~~~{.java}
	// / // 핵심코드
	// /~~~~~~~~~~~~~
	// ///////////////////////////////////////////
	public Integer insert(RdtOcpatFile rdtOcpatFile);

	// ///////////////////////////////////////////
	// / @fn update
	// / @brief 함수 간략한 설명 : 수정
	// / @remark
	// / - 함수의 상세 설명 :
	// / @param rdtOcpatFile
	// / @return
	// /
	// /~~~~~~~~~~~~~{.java}
	// / // 핵심코드
	// /~~~~~~~~~~~~~
	// ///////////////////////////////////////////
	public Integer update(RdtOcpatFile rdtOcpatFile);

	// ///////////////////////////////////////////
	// / @fn delete
	// / @brief 함수 간략한 설명 : 삭제
	// / @remark
	// / - 함수의 상세 설명 :
	// / @param imageNo
	// / @return
	// /
	// /~~~~~~~~~~~~~{.java}
	// / // 핵심코드
	// /~~~~~~~~~~~~~
	// ///////////////////////////////////////////
	public Integer delete(Long ocpatFileNo);
}
