package kr.co.gitt.kworks.projects.gc.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.projects.gc.model.MobileExportHt;


/**
 * 재난현장 모버일 멥퍼
 * @author kwangsu.lee
 *
 */
public interface MobileExportHtMapper {

	/**
	 * 내보내기 이력 검색 개수 
	 * @param searchDTO - 검색 조건
	 * @return
	 */
	public Integer listCount(ExportSearchDTO searchDTO);	
	
	/**
	 * 내보내기 이력 검색
	 * @param searchDTO - 검색 조건
	 * @return
	 */
	public List<MobileExportHt> list(ExportSearchDTO searchDTO);
	
	/**
	 * 이력 등록
	 * @param export - 내보내기 이력
	 * @return
	 */
	public Integer insert(MobileExportHt mobileExportHt);
}
