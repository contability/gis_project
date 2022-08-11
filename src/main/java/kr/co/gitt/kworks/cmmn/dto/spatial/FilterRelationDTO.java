package kr.co.gitt.kworks.cmmn.dto.spatial;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SpatialType;

/////////////////////////////////////////////
/// @class FilterRelationDTO
/// kr.co.gitt.kworks.cmmn.dto.spatial \n
///   ㄴ FilterRelationDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:55:36 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 수정사항 : 이승재, 2021.02.19, 연관 데이터 PK 컬럼명 인스턴스 변수 추가, 초기값 "OBJECTID" 부여
/////////////////////////////////////////////
public class FilterRelationDTO {
	
	/// 공간 검색 타입
	private SpatialType spatialType;

	/// 연관 데이터 아이디
	private String relationDataId;
	
	/// 연관 데이터 PK 컬럼명
	private String relationDataPkColumnName = "OBJECTID";
	
	/// 도형 아이디
	private Long fid;
	
	/// 도형 아이디 목록
	private List<Long> fids;
	
	/// 연관 버퍼 (m)
	private Integer relationBuffer;

	public SpatialType getSpatialType() {
		return spatialType;
	}

	public void setSpatialType(SpatialType spatialType) {
		this.spatialType = spatialType;
	}

	public String getRelationDataId() {
		return relationDataId;
	}

	public void setRelationDataId(String relationDataId) {
		this.relationDataId = relationDataId;
	}
	
	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public List<Long> getFids() {
		return fids;
	}

	public void setFids(List<Long> fids) {
		this.fids = fids;
	}

	public Integer getRelationBuffer() {
		return relationBuffer;
	}

	public void setRelationBuffer(Integer relationBuffer) {
		this.relationBuffer = relationBuffer;
	}

	public String getRelationDataPkColumnName() {
		return relationDataPkColumnName;
	}

	public void setRelationDataPkColumnName(String relationDataPkColumnName) {
		this.relationDataPkColumnName = relationDataPkColumnName;
	}
	
}
