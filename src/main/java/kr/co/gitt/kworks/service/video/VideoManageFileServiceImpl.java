package kr.co.gitt.kworks.service.video;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.RdtVideoMaMapper;
import kr.co.gitt.kworks.model.RdtVideoMa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("videoManageFile")
@Profile({"gn_dev", "gn_oper"})
public class VideoManageFileServiceImpl implements VideoManageFileService {
	
	@Resource
	RdtVideoMaMapper rdtVideoMaMapper;
	
	@Override
	public List<RdtVideoMa> listVideoManageFile(RdtVideoMa rdtVideoMa) {
		return rdtVideoMaMapper.list(rdtVideoMa);
	}
}