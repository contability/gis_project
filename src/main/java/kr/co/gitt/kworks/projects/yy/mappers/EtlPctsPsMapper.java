package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yy.model.EtlPctsPs;

/**
 * 지적삼각보조점
 * @author kwangsu.lee
 *
 */
public interface EtlPctsPsMapper {

	/**
	 * 전체목록
	 * @return
	 */
	public List<EtlPctsPs> listAll();
	
	/**
	 * 단건 조회
	 * @param ftrIdn
	 * @return
	 */
	public EtlPctsPs selectOneById(Long ftrIdn);
	
	/**
	 * 등록
	 * @param data
	 * @return
	 */
	public Integer insert(EtlPctsPs data);
	
	/**
	 * 갱신
	 * @param data
	 * @return
	 */
	public Integer update(EtlPctsPs data);
	
	/**
	 * 삭제
	 * @param ftrIdn
	 * @return
	 */
	public Integer delete(Long ftrIdn);

}
