package kr.co.gitt.kworks.service.log;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsErrorMapper;
import kr.co.gitt.kworks.model.KwsError;

import org.springframework.stereotype.Service;

@Service("ErrorLogService")
public class ErrorLogServiceImpl implements ErrorLogService{
	
	@Resource
	KwsErrorMapper kwsErrorMapper;
	
	public Integer listCount(SearchDTO searchDTO) {
		return kwsErrorMapper.listCount(searchDTO);
	}
	
	public List<KwsError> listSearch(SearchDTO searchDTO) {
		return kwsErrorMapper.list(searchDTO);
	}
	
	public KwsError selectOne(Integer errorNo) {
		return kwsErrorMapper.selectOne(errorNo);
	}
	
	@Override
	public List<KwsError> listErrorLogExcel(SearchDTO searchDTO) {
		return kwsErrorMapper.listErrorLogExcel(searchDTO);
	}
	
}