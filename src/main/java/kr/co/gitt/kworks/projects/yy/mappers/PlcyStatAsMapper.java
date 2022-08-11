package kr.co.gitt.kworks.projects.yy.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yy.dto.PolicyDepRegisterSearchDTO;
import kr.co.gitt.kworks.projects.yy.model.PlcyStatAs;


public interface PlcyStatAsMapper {
	
	//조건 검색 시
	public List<PolicyDepRegisterSearchDTO> listDep(PlcyStatAs plcyStatAs);
	
	//KWS_MENU 위치 조회
	public List<PolicyDepRegisterSearchDTO> listSearch(String geom);
	
	//단건검색
	public PlcyStatAs selectOne(Long ftrIdn);
	
	public Integer update(PlcyStatAs plcyStatAs);
	
	//보고서
	public String selectPlcyTit(Long ftrIdn);
	
	//주소
	public PlcyStatAs selectPlcyAdr(Long ftrIdn);
	
	//부속자료 등록시 정책명 조회를위한 쿼리
	public PlcyStatAs selectPlcyStatAs(Long ftrIdn);
	
	//삭제
	public Integer delete(Long ftrIdn);
	
	//삭제
	public Integer deleteImage(Long ftrIdn);
	
	public List<PolicyDepRegisterSearchDTO> quickSearch(String plcyAdr);

	//정책지도 트리구조형 추가
	public List<PlcyStatAs> deptSubList(String deptSbCd);
}
