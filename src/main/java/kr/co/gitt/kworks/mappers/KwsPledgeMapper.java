package kr.co.gitt.kworks.mappers;

import kr.co.gitt.kworks.model.KwsPledge;

public interface KwsPledgeMapper {
	
	public Integer pledgeChk(String userId);
	
	public Integer pledgeInsert(KwsPledge kwsPledge);

}
