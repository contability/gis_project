package kr.co.gitt.kworks.service.mobile;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.projects.gc.model.MobileExportHt;

/**
 * 재난현장 모바일 데이터 내보내기 서비스
 * @author kwangsu.lee
 *
 */
public interface MobileExportService {


	/**
	 * 데이터 목록을 조회
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public Integer listCount(ExportSearchDTO searchDTO);
	
	/**
	 * 데이터 목록을 조회
	 * @param searchDTO - 검색조건
	 * @return
	 */
	public List<MobileExportHt> list(ExportSearchDTO searchDTO);
	
	/**
	 * 내보내기 
	 * @return
	 */
	public String export();
}
