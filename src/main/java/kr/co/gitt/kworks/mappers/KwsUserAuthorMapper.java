package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsUserAuthor;

public interface KwsUserAuthorMapper {
	
	public List<KwsAuthorGroup> listAll(String userId);
	
	public Integer insert(KwsUserAuthor kwsUserAuthor);
	
	public Integer deleteByUserId(String userId);

}
