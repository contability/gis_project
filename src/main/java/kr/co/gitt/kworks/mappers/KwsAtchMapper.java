package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsAtch;

public interface KwsAtchMapper {
	
	public List<KwsAtch> listAtchFileByFtf(KwsAtch kwsAtch);
	
	public Integer insertAtchFile(KwsAtch kwsAtch);
	
	public Integer deleteAtchFile(Long fileNo);
}
