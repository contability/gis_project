package kr.co.gitt.kworks.service.local;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.RdtLoclMaMapper;
import kr.co.gitt.kworks.model.RdtLoclMa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("localPlanFile")
@Profile({"gn_dev", "gn_oper"})
public class LocalPlanFileServiceImpl implements LocalPlanFileService {
	
	@Resource
	RdtLoclMaMapper rdtLoclMaMapper;
	
	@Override
	public List<RdtLoclMa> listLocalPlanFile(RdtLoclMa rdtLoclMa) {
		return rdtLoclMaMapper.listAll(rdtLoclMa);
	}
}