package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yy.model.EtlNtlpPs;

/**
 * 수준점
 * @author kwangsu.lee
 *
 */
public interface EtlNtlpPsMapper {

	/**
	 * 전체목록
	 * @return
	 */
	public List<EtlNtlpPs> listAll();
	
	/**
	 * 단건 조회
	 * @param ftrIdn
	 * @return
	 */
	public EtlNtlpPs selectOneById(Long ftrIdn);
	
	/**
	 * 등록
	 * @param data
	 * @return
	 */
	public Integer insert(EtlNtlpPs data);
	
	/**
	 * 갱신
	 * @param data
	 * @return
	 */
	public Integer update(EtlNtlpPs data);
	
	/**
	 * 삭제
	 * @param ftrIdn
	 * @return
	 */
	public Integer delete(Long ftrIdn);

}
