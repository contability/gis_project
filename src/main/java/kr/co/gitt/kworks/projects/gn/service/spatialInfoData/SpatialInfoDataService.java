package kr.co.gitt.kworks.projects.gn.service.spatialInfoData;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.SpatialInfoData;

public interface SpatialInfoDataService {
	
	//  공간정보 자료제공 대장 리스트
	public List<SpatialInfoData> listSpatialInfoData(HashMap<String, Object> searchMap);
	
	//  공간정보 자료제공 대장 리스트	
	public List<SpatialInfoData> listSpatialInfoData(SearchDTO searchDTO, PaginationInfo paginationInfo, String startDate, String endDate);
	
	//  공간정보 자료제공 대장 조회
	public SpatialInfoData selectSpatialInfoData(Long dtaNo);

	//  공간정보 자료제공 대장 등록
	public Integer addSpatialInfoData(SpatialInfoData spatialInfoData) throws FdlException;

	//  공간정보 자료제공 대장 수정
	public Integer modifySpatialInfoData(SpatialInfoData spatialInfoData);
	
	//  공간정보 자료제공 대장 삭제
	public Integer removeSpatialInfoData(Long dtaNo);
	
	// 관리번호 업데이트
	public void updateManageNo();

}
