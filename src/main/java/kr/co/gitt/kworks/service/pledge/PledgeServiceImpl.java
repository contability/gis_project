package kr.co.gitt.kworks.service.pledge;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.KwsPledgeMapper;
import kr.co.gitt.kworks.model.KwsPledge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;


@Service("pledgeService")
public class PledgeServiceImpl implements PledgeService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private KwsPledgeMapper kwsPledgeMapper;
	
	@Override
	public UserDTO page() {
		
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		return userDTO;
	}
	
	@Override
	public Boolean pledgeChk() {
		
		boolean isChk = false;
		
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		int result = kwsPledgeMapper.pledgeChk(userDTO.getUserId());
		
		if(result > 0 || userDTO.getUserGrad().getValue().equals("관리자")){
			isChk = true;
		}
		
		return isChk;
	}

	@Override
	public Integer pledgeInsert(KwsPledge kwsPledge) {
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		kwsPledge.setUserId(userDTO.getUserId());
		kwsPledge.setDeptCode(userDTO.getDeptCode());
		
		Calendar cal = new GregorianCalendar();
		Date date = new Date(cal.getTimeInMillis());
		
		kwsPledge.setPldYmd(date);
		
		return kwsPledgeMapper.pledgeInsert(kwsPledge);
	}
}
