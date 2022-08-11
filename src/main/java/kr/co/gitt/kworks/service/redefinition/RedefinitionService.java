package kr.co.gitt.kworks.service.redefinition;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.LayerSearchDTO;
import kr.co.gitt.kworks.model.KwsLyrCtgry;

// 레이어 재정의 서비스
// 2021.12.10	정신형
public interface RedefinitionService {
	
	// 부서별 레이어 재정의
	List<KwsLyrCtgry> listAllLayerCategoryByDept(LayerSearchDTO layerSearchDTO);
	
}
