package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yy.model.EtlPcspPs;

/**
 * 지적도근점
 * @author kwangsu.lee
 *
 */
public interface EtlPcspPsMapper {

	/**
	 * 전체목록
	 * @return
	 */
	public List<EtlPcspPs> listAll();
	
	/**
	 * 단건 조회
	 * @param ftrIdn
	 * @return
	 */
	public EtlPcspPs selectOneById(Long ftrIdn);
	
	/**
	 * 등록
	 * @param data
	 * @return
	 */
	public Integer insert(EtlPcspPs data);
	
	/**
	 * 갱신
	 * @param data
	 * @return
	 */
	public Integer update(EtlPcspPs data);
	
	/**
	 * 삭제
	 * @param ftrIdn
	 * @return
	 */
	public Integer delete(Long ftrIdn);
	
}
