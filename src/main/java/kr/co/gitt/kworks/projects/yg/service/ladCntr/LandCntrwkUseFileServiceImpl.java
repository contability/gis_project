package kr.co.gitt.kworks.projects.yg.service.ladCntr;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.yg.mappers.LdDocMaMapper;
import kr.co.gitt.kworks.projects.yg.model.LdDocMa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("landCntrwkUseFileService")
@Profile({"yg_dev", "yg_oper"})
public class LandCntrwkUseFileServiceImpl implements LandCntrwkUseFileService {

	@Resource
	LdDocMaMapper ldDocMaMapper;
	
	@Override
	public List<LdDocMa> listLandCntrwkUseFile(LdDocMa ldDocMa) {
		return ldDocMaMapper.list(ldDocMa);
	}

}
