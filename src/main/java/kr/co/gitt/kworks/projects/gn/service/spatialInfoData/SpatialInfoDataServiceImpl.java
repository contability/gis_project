package kr.co.gitt.kworks.projects.gn.service.spatialInfoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.mappers.SpatialInfoDataMapper;
import kr.co.gitt.kworks.projects.gn.model.SpatialInfoData;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("spatialInfoDataService")
@Profile({"gn_dev", "gn_oper"})
public class SpatialInfoDataServiceImpl implements SpatialInfoDataService{
	
	// 공간정보 자료제공 대장
	@Resource
	SpatialInfoDataMapper spatialInfoDataMapper;
	
	// ID 생성 서비스
	@Resource
	EgovIdGnrService spatialInfoDataIdGnrService;

	@Override
	public List<SpatialInfoData> listSpatialInfoData(HashMap<String, Object> searchMap) {
		return spatialInfoDataMapper.list(searchMap);
	}

	@Override
	public List<SpatialInfoData> listSpatialInfoData(SearchDTO searchDTO, PaginationInfo paginationInfo, String startDate, String endDate) {
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("searchDTO", searchDTO);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("searchKeyword", searchDTO.getSearchKeyword());
		searchMap.put("searchCondition", searchDTO.getSearchCondition());
		searchMap.put("lastIndex", searchDTO.getLastIndex());
		searchMap.put("firstIndex", searchDTO.getFirstIndex());
		
		Integer totalRecordCount = spatialInfoDataMapper.listCount(searchMap);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0){
			return listSpatialInfoData(searchMap);
		}else{
			return new ArrayList<SpatialInfoData>();
		}
	}

	@Override
	public SpatialInfoData selectSpatialInfoData(Long dtaNo) {
		return spatialInfoDataMapper.select(dtaNo);
	}

	@Override
	public Integer addSpatialInfoData(SpatialInfoData spatialInfoData) throws FdlException {
		Long dtaNo = spatialInfoDataIdGnrService.getNextLongId();
		spatialInfoData.setDtaNo(dtaNo);
		
		Integer rowCount = spatialInfoDataMapper.insert(spatialInfoData);
		updateManageNo();
		
		return rowCount;
	}

	@Override
	public Integer modifySpatialInfoData(SpatialInfoData spatialInfoData) {
		
		Integer rowCount = spatialInfoDataMapper.update(spatialInfoData);
		updateManageNo();
		
		return rowCount;
	}

	@Override
	public Integer removeSpatialInfoData(Long dtaNo) {
		Integer rowCount = spatialInfoDataMapper.delete(dtaNo);
		updateManageNo();
		
		return rowCount;
	}

	@Override
	public void updateManageNo() {
		spatialInfoDataMapper.updateManageNo();
	}
}
