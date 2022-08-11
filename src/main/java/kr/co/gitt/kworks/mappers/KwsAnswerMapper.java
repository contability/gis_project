package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsAnswer;

/////////////////////////////////////////////
/// @class KwsAnswerMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsAnswerMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 12. 오전 9:29:11 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 묻고답하기 답변게시판 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsAnswerMapper {
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param answerNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsAnswer selectOne(Long answerNo);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAnswer
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(KwsAnswer kwsAnswer);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAnswer
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(KwsAnswer kwsAnswer);
	
}
