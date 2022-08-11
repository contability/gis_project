package kr.co.gitt.kworks.service.section;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.RdtSectMaMapper;
import kr.co.gitt.kworks.model.RdtSectMa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("sectionPlanFile")
@Profile({"gn_dev", "gn_oper"})
public class SectionPlanFileServiceImpl implements SectionPlanFileService {
	
	@Resource
	RdtSectMaMapper rdtSectMaMapper;
	
	@Override
	public List<RdtSectMa> listSectionPlanFile(RdtSectMa rdtSectMa) {
		return rdtSectMaMapper.list(rdtSectMa);
	}
}