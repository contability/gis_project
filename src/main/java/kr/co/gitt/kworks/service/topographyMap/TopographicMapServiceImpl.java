package kr.co.gitt.kworks.service.topographyMap;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsTopographicMapMapper;
import kr.co.gitt.kworks.model.KwsTopographicMap;

import org.springframework.stereotype.Service;

@Service("topographicService")
public class TopographicMapServiceImpl implements TopographicMapService {

	/// 지형지도 맵퍼
	@Resource
	KwsTopographicMapMapper kwsTopographicMapMapper;

	@Override
	public List<String> listGroup() {
		
		return kwsTopographicMapMapper.listGroup();
	}

	@Override
	public List<KwsTopographicMap> listAll() {
		
		return kwsTopographicMapMapper.listAll();
	}

	@Override
	public List<KwsTopographicMap> selectByGroup(String groupName) {
		return kwsTopographicMapMapper.selectByGroup(groupName);
	}

	@Override
	public KwsTopographicMap selectOne(String groupName, String layerName) {
		return kwsTopographicMapMapper.selectOne(groupName, layerName);
	}
}
