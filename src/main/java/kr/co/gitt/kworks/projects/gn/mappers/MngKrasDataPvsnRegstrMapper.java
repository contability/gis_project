package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.KrasDataPvsnRegstrVO;
import kr.co.gitt.kworks.projects.gn.model.MngKrasDataPvsnRegstr;

public interface MngKrasDataPvsnRegstrMapper {
	public Integer listCount(SearchDTO searchDTO);
	
	public List<KrasDataPvsnRegstrVO> list(SearchDTO searchDTO);
	
	public KrasDataPvsnRegstrVO selectOne(Long dtaNo);

	public Integer insert(MngKrasDataPvsnRegstr mngKrasDataPvsnRegstr);

	public void updateManageNo();

	public Integer update(MngKrasDataPvsnRegstr mngKrasDataPvsnRegstr);

	public Integer delete(Long dtaNo);
}
