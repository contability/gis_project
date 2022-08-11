package kr.co.gitt.kworks.service.local;

import java.util.List;

import kr.co.gitt.kworks.model.RdtLoclMa;

public interface LocalPlanFileService {
	
	public List<RdtLoclMa> listLocalPlanFile(RdtLoclMa rdtLoclMa);
}