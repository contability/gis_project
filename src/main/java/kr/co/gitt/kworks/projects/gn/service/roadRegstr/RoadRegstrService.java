package kr.co.gitt.kworks.projects.gn.service.roadRegstr;

import java.util.List;

import kr.co.gitt.kworks.model.KwsRoadReg;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterBBoxSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSearchSummaryDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSortDTO;
import kr.co.gitt.kworks.projects.gn.model.RdlSectLs;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface RoadRegstrService {
	
	public List<RdlSectLs> listRoadRegstr(RdlSectLs rdlSectLs);

	public List<KwsRoadReg> listAll();
	
	public List<EgovMap> rotNameAll() throws Exception;

	public KwsRoadReg selectOne(String regIdn);
	
	public List<KwsRoadReg> listSelected(List<String> regIdns, boolean useCheck);
	
	public List<RoadRegisterSearchSummaryDTO> searchSummaries(List<KwsRoadReg> roadRegs, RoadRegisterSearchDTO roadRegisterSearchDTO) throws Exception;

	public RoadRegisterSearchSummaryDTO sortRoadRegister(KwsRoadReg roadReg, RoadRegisterSortDTO roadRegisterSortDTO) throws Exception;
	
	public List<EgovMap> searchRoadRegister(KwsRoadReg roadReg, RoadRegisterSortDTO roadRegisterSortDTO) throws Exception;
	
	public List<RoadRegisterSearchSummaryDTO> searchByBBox(KwsRoadReg roadReg, RoadRegisterBBoxSearchDTO roadRegisterSortDTO) throws Exception;
	
	public List<RoadRegisterSearchSummaryDTO> searchVideoByBBox(KwsRoadReg roadReg, RoadRegisterBBoxSearchDTO roadRegisterSortDTO) throws Exception;
	
	public RoadRegisterSearchSummaryDTO sortRoadVideo(KwsRoadReg roadReg, RoadRegisterSortDTO roadRegisterSortDTO) throws Exception;
	
	public List<EgovMap> searchRoadVideo(KwsRoadReg roadReg, RoadRegisterSortDTO roadRegisterSortDTO) throws Exception;
	
	public String selectOneVideo(KwsRoadReg roadReg, String pathIdn) throws Exception;
	
	public List<RoadRegisterSearchSummaryDTO> searchRoadAreaByBBox(KwsRoadReg roadReg, RoadRegisterBBoxSearchDTO roadRegisterSortDTO) throws Exception;
	
}