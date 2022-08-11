package kr.co.gitt.kworks.projects.sunchang.service.rplotreexmn;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.sunchang.dto.kwsRplotreexmnLgstrDTO;
import kr.co.gitt.kworks.projects.sunchang.mappers.RplotreexmnMapper;
import kr.co.gitt.kworks.projects.sunchang.model.KwsRplotreexmn;
import kr.co.gitt.kworks.projects.sunchang.model.KwsRplotreexmnLgstr;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("rplotreexmnService")
@Profile({"sunchang_dev","sunchang_oper"})
public class RplotreexmnServiceImpl implements RplotreexmnService{
	
	@Resource
	private RplotreexmnMapper rplotreexmnMapper;
	
	@Override
	public List<KwsRplotreexmnLgstr> selectList(SearchDTO searchDTO) {
		
		List<KwsRplotreexmnLgstr> chkList = rplotreexmnMapper.selectListLgstr(searchDTO);
		List<KwsRplotreexmnLgstr> chkedList = new ArrayList<KwsRplotreexmnLgstr>();
		
		for(int i = 0; i < chkList.size(); i++){
			String brftrSe = chkList.get(i).getBrftrSe();
			if(brftrSe.equals("1")){
				chkedList.add(selectForAfter(chkList.get(i).getRplotreexmnNo()));
			}else{
				chkedList.add(chkList.get(i));
			}
		}
		
		return chkedList;
	}

	@Override
	public List<KwsRplotreexmnLgstr> selectList(String pnu, String bsnsAreaNm, String bsnsSe, kwsRplotreexmnLgstrDTO searchDTO, PaginationInfo paginationInfo) {
		searchDTO.setSearchKeyword(pnu);
		searchDTO.setSearchCondition(bsnsSe);
		searchDTO.setBsnsAreaNm(bsnsAreaNm);
		
		Integer totalRecordCount = rplotreexmnMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0){
			return selectList(searchDTO);
		}else{
			return new ArrayList<KwsRplotreexmnLgstr>();
		}
	}

	@Override
	public KwsRplotreexmn selectOne(Long rplotreexmnNo) {
		return rplotreexmnMapper.selectOne(rplotreexmnNo);
	}

	@Override
	public List<KwsRplotreexmnLgstr> selectListLgstr(String pnu) {
		return null;
	}

	@Override
	public List<KwsRplotreexmnLgstr> selectOneLgstr(Long rplotreexmnNo) {
		return rplotreexmnMapper.selectLgstrByFk(rplotreexmnNo);
	}
	
	public KwsRplotreexmnLgstr selectForAfter(Long rplotreexmnNo){
		return rplotreexmnMapper.selectForAfter(rplotreexmnNo);
	}
}
