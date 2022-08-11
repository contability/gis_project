package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsSurvey;

public interface KwsSurveyMapper {
	
	public int insert(KwsSurvey kwsSurvey);
	
	public List<KwsSurvey> selectByUserId(String userId);
}
