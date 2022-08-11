package kr.co.gitt.kworks.service.cadastralMap;

import java.util.List;

import kr.co.gitt.kworks.model.KwsOverlayTile;

/**
 * 지적원도 서비스 인터페이스
 * @author kwangsu.lee
 *
 */
public interface CadastralMapService {

	/**
	 * 지적원도 전체 목록
	 * @return
	 */
	public List<KwsOverlayTile> listAll();
}
