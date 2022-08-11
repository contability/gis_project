package kr.co.gitt.kworks.service.pledge;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsPledge;

public interface PledgeService {
	
	public UserDTO page();
	
	public Boolean pledgeChk();
	
	public Integer pledgeInsert(KwsPledge kwsPledge);

}
