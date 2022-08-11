package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yy.model.EtlCtbpPs;

/**
 * 도시기준점
 * @author kwangsu.lee
 *
 */
public interface EtlCtbpPsMapper {

	/**
	 * 전체목록
	 * @return
	 */
	public List<EtlCtbpPs> listAll();
	
	/**
	 * 단건 조회
	 * @param ftrIdn
	 * @return
	 */
	public EtlCtbpPs selectOneById(Long ftrIdn);
	
	/**
	 * 등록
	 * @param data
	 * @return
	 */
	public Integer insert(EtlCtbpPs data);
	
	/**
	 * 갱신
	 * @param data
	 * @return
	 */
	public Integer update(EtlCtbpPs data);
	
	/**
	 * 삭제
	 * @param ftrIdn
	 * @return
	 */
	public Integer delete(Long ftrIdn);

}
