package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yy.model.EtlNtbpPs;

/**
 * 통합기준점 
 * @author kwangsu.lee
 *
 */
public interface EtlNtbpPsMapper {

	/**
	 * 전체목록
	 * @return
	 */
	public List<EtlNtbpPs> listAll();
	
	/**
	 * 단건 조회
	 * @param ftrIdn
	 * @return
	 */
	public EtlNtbpPs selectOneById(Long ftrIdn);
	
	/**
	 * 등록
	 * @param data
	 * @return
	 */
	public Integer insert(EtlNtbpPs data);
	
	/**
	 * 갱신
	 * @param data
	 * @return
	 */
	public Integer update(EtlNtbpPs data);
	
	/**
	 * 삭제
	 * @param ftrIdn
	 * @return
	 */
	public Integer delete(Long ftrIdn);
}
