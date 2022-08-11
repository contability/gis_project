package kr.co.gitt.kworks.dto.feature;

import java.util.List;

/////////////////////////////////////////////
/// @class FeatureResultDTO
/// kr.co.gitt.kworks.dto.feature \n
///   ㄴ FeatureResultDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 26. 오후 12:15:03 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도형 결과 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class FeatureResultDTO {

	/// 전체 수 
	private int count;
	
	/// 성공한 수 
	private int successCount;
	
	/// 실패한 수
	private int failCount;
	
	private List<String> features;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}
	
}
