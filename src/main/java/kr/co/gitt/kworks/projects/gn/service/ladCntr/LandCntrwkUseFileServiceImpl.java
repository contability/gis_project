package kr.co.gitt.kworks.projects.gn.service.ladCntr;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.mappers.LdDocMaMapper;
import kr.co.gitt.kworks.projects.gn.model.LdDocMa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("landCntrwkUseFileService")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class LandCntrwkUseFileServiceImpl implements LandCntrwkUseFileService {

	@Resource
	LdDocMaMapper ldDocMaMapper;
	
	@Override
	public List<LdDocMa> listLandCntrwkUseFile(LdDocMa ldDocMa) {
		return ldDocMaMapper.list(ldDocMa);
	}

}
