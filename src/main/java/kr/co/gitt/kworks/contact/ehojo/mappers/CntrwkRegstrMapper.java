package kr.co.gitt.kworks.contact.ehojo.mappers;

import kr.co.gitt.kworks.cmmn.dto.EhojoCntrwkRegstrDTO;

public interface CntrwkRegstrMapper {
	
	public EhojoCntrwkRegstrDTO selectOne(String cttNum);
	
}
