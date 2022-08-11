package kr.co.gitt.kworks.service.survey;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.mappers.KwsSurveyMapper;
import kr.co.gitt.kworks.model.KwsSurvey;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	KwsSurveyMapper kwsSurveyMapper;
	
	@Override
	public int insert(KwsSurvey kwsSurvey) {

		// 현재 월 가져옴
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		int month = cal.get(Calendar.MONTH) + 1;
		
		// 유저 아이디 가져오기
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		String userId = userDTO.getUserId();
		
		int result = 0;
		
		kwsSurvey.setMonth(month);
		kwsSurvey.setUserId(userId);

		result = kwsSurveyMapper.insert(kwsSurvey);
		
		return result;
	}

	@Override
	public boolean select() {
		// 유저 아이디 가져오기
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper
				.getAuthenticatedUser();
		String userId = userDTO.getUserId();

		List<KwsSurvey> surveyList = kwsSurveyMapper.selectByUserId(userId);

		boolean chk = true;

		// 현재 월 가져옴
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		int month = cal.get(Calendar.MONTH) + 1;

		for (int i = 0; i < surveyList.size(); i++) {
			if (month == surveyList.get(i).getMonth()) {
				chk = false;
			}
		}
		return chk;
	}
}
