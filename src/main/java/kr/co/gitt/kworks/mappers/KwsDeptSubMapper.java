package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsDeptSub;

public interface KwsDeptSubMapper {
	
	public List<KwsDeptSub> list(KwsDeptSub kwsDeptSub);
	
	public String deptSubNmReturn(String deptSubCode);
	
	public String forDeptCode(String deptSubCode);
	
}
