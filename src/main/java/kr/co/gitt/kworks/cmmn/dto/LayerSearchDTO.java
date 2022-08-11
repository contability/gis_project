package kr.co.gitt.kworks.cmmn.dto;

import java.util.List;

/////////////////////////////////////////////
/// @class LayerSearchDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ LayerSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 30. 오후 5:03:13 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 레이어 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class LayerSearchDTO {

	/// 권한 그룹 번호 
	private List<Long> authorGroupNos;
	
	/// 레이어 목록
	private List<String> layerIds;

	public List<Long> getAuthorGroupNos() {
		return authorGroupNos;
	}

	public void setAuthorGroupNos(List<Long> authorGroupNos) {
		this.authorGroupNos = authorGroupNos;
	}

	public List<String> getLayerIds() {
		return layerIds;
	}

	public void setLayerIds(List<String> layerIds) {
		this.layerIds = layerIds;
	}


	
}
