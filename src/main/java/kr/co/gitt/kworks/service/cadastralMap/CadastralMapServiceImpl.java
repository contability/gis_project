package kr.co.gitt.kworks.service.cadastralMap;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsOverlayTileMapper;
import kr.co.gitt.kworks.model.KwsOverlayTile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"yy_dev", "yy_oper", "sc_dev", "sc_oper", "sunchang_dev", "sunchang_oper"})
@Service("cadastralMapService")
public class CadastralMapServiceImpl implements CadastralMapService {

	/**
	 * 지적원도/드론영상 맵퍼
	 */
	@Resource
	KwsOverlayTileMapper kwsOverlayTileMapper;

	
	@Override
	public List<KwsOverlayTile> listAll() {
		
		return kwsOverlayTileMapper.listAll();
	}	
	
}
