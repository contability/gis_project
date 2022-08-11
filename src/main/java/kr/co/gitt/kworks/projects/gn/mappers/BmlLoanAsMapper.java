package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.projects.gn.dto.UseAreaSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.BmlLoanAs;
import kr.co.gitt.kworks.projects.gn.model.BmlPropAs;

public interface BmlLoanAsMapper {
	
	public List<BmlLoanAs> list(BmlLoanAs bmlLoanDt);
	
	public BmlLoanAs selectOne(BmlLoanAs bmlLoanAs);
	
	public Integer insertBackup(BmlLoanAs bmlLoanAs);
	
	public Integer remove();
	
	public Integer geomUpdate();
	
	public Integer loanImmCount(Map<String, String> map);
	
	public List<BmlLoanAs> areaList(UseAreaSearchDTO useAreaSearchDTO);
	
	public List<BmlLoanAs> selectPrsGroupList();
	
	public List<BmlLoanAs> selectManGroupList();
	
	public Integer removeBackup();
	
	public Integer insertPropLoanData();
	
	public Integer insertBuidLoanData();
}
