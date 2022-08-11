package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.BmlLoanAs;

public interface BmlLoanAsMapper {
	
	public List<BmlLoanAs> list(BmlLoanAs bmlLoanDt);
	
	public BmlLoanAs selectOne(BmlLoanAs bmlLoanAs);
	
	public Integer insert(BmlLoanAs bmlLoanAs);
	
	public Integer remove();
	
	public Integer geomUpdate();
}
