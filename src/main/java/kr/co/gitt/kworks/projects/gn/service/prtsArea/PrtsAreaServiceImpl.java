package kr.co.gitt.kworks.projects.gn.service.prtsArea;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.projects.gn.dto.PrtsAreaDTO;
import kr.co.gitt.kworks.projects.gn.mappers.RdlPrtsAsMapper;
import kr.co.gitt.kworks.projects.gn.mappers.RdtPtimHtMapper;
import kr.co.gitt.kworks.projects.gn.model.RdlPrtsAs;
import kr.co.gitt.kworks.projects.gn.model.RdtPtimHt;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("prtsAreaService")
@Profile({"gn_dev","gn_oper"})
public class PrtsAreaServiceImpl implements PrtsAreaService{
	
	@Resource
	private RdlPrtsAsMapper rdlPrtsAsMapper;
	
	@Resource
	private RdtPtimHtMapper rdtPtimHtMapper;
	
	@Resource
	EgovIdGnrService rdtPtimHtIdGnrService;

	@Override
	public List<RdlPrtsAs> selectList(PrtsAreaDTO searchDTO) {
		return rdlPrtsAsMapper.selectList(searchDTO);
	}

	@Override
	public List<RdlPrtsAs> selectList(PrtsAreaDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = rdlPrtsAsMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0){
			return selectList(searchDTO);
		}else{
			return new ArrayList<RdlPrtsAs>();
		}
	}
	
	
	/////////////////////////////////// 보호구역 개선사업이력


	@Override
	public Integer rdtPtimHtInsert(RdtPtimHt rdtPtimHt) throws FdlException {
		
		Long impIdn = rdtPtimHtIdGnrService.getNextLongId();
		rdtPtimHt.setImpIdn(impIdn);
		
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		rdtPtimHt.setWrterId(userDTO.getUserId());
		rdtPtimHt.setUpdusrId(userDTO.getUserId());
		
		String dateToStr = dateTime();
		rdtPtimHt.setFrstRgsde(dateToStr);
		rdtPtimHt.setLastUpdde(dateToStr);
		
		return rdtPtimHtMapper.rdtPtimHtInsert(rdtPtimHt);
	}

	@Override
	public Integer rdtPtimHtUpdate(RdtPtimHt rdtPtimHt) {
		
		String dateToStr = dateTime();
		rdtPtimHt.setLastUpdde(dateToStr);
		
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		rdtPtimHt.setUpdusrId(userDTO.getUserId());
		
		return rdtPtimHtMapper.rdtPtimHtUpdate(rdtPtimHt);
	}

	@Override
	public Integer rdtPtimHtDelete(Long impIdn) {
		return rdtPtimHtMapper.rdtPtimHtDelete(impIdn);
	}
	
	public String dateTime(){
		Calendar cal = new GregorianCalendar();
		Date date = new Date(cal.getTimeInMillis());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		String dateToStr = dateFormat.format(date);
		
		
		return dateToStr;
	}

	@Override
	public List<RdtPtimHt> rdtPtimHtSelectList(Long ftfIdn, String ftfCde) {
		RdtPtimHt rdtPtimHt = new RdtPtimHt();
		rdtPtimHt.setFtfCde(ftfCde);
		rdtPtimHt.setFtfIdn(ftfIdn);
		
		return rdtPtimHtMapper.rdtPtimHtSelectList(rdtPtimHt);
	}

	@Override
	public List<RdtPtimHt> rdtPtimHtSelectOne(Long impIdn) {
		return rdtPtimHtMapper.rdtPtimHtSelectOne(impIdn);
	}

}
