package kr.co.gitt.kworks.service.survey;

import kr.co.gitt.kworks.model.KwsSurvey;

public interface SurveyService {
	
	public boolean select();
	
	public int insert(KwsSurvey kwsSurvey);

}
