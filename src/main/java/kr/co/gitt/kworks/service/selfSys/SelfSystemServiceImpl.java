package kr.co.gitt.kworks.service.selfSys;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsSysMapper;
import kr.co.gitt.kworks.mappers.KwsUserMenuMapper;
import kr.co.gitt.kworks.mappers.KwsUserSysMapper;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.model.KwsUserMenu;
import kr.co.gitt.kworks.model.KwsUserSys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("selfSystemService")
public class SelfSystemServiceImpl implements SelfSystemService{
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
		
	@Resource
	KwsUserSysMapper kwsUserSysMapper;
	
	@Resource
	KwsUserMenuMapper kwsUserMenuMapper;
	
	@Resource
	KwsSysMapper kwsSysMapper;
	
	@Resource
	EgovIdGnrService kwsSysIdGnrService;
	
	@Resource
	EgovIdGnrService kwsUserMenuIdGnrService;
	
	public List<KwsUserSys> list(KwsUserSys kwsUserSys){
		return kwsUserSysMapper.list(kwsUserSys);
		
	}
	
	public KwsUserSys selectOneUserSystem(KwsUserSys kwsUserSys) {
		return kwsUserSysMapper.selectOne(kwsUserSys);
	}
	
	public Integer add(KwsUserSys kwsUserSys, String userId) throws FdlException{
		
		Long sysId = kwsSysIdGnrService.getNextLongId();
		kwsUserSys.setSysId(sysId);
		
		// KWS_SYS 등록
		Integer cnt = kwsSysMapper.selfSysInsert(kwsUserSys);
		
		// KWS_USER_SYS 등록
//		KwsUserSys kwsUserSys = new KwsUserSys();
		kwsUserSys.setSysId(sysId);
		kwsUserSys.setUserId(userId);
		kwsUserSysMapper.insert(kwsUserSys);
		
		Long upperMenuNo = (long) -1;
		Long menuOrdr = (long) 1;
		
		// KWS_USER_MENU 등록
		for(Integer i=0; i < kwsUserSys.getKwsUserMenu().size(); i++){
			
			KwsUserMenu kwsUserMenu = kwsUserSys.getKwsUserMenu().get(i);
			Long menuNo = kwsUserMenuIdGnrService.getNextLongId();
			kwsUserMenu.setMenuNo(menuNo);
			kwsUserMenu.setSysId(sysId);

			logger.debug("kwsUserMenu.getUpperMenuId() :" + kwsUserMenu.getUpperMenuId());
			if(kwsUserMenu.getUpperMenuId().equals((long) -1)){
				menuOrdr = (long) 1;
				upperMenuNo = menuNo;
				kwsUserMenu.setMenuOrdr(menuNo);
				menuOrdr++;
			}else{
				kwsUserMenu.setUpperMenuId(upperMenuNo);
				kwsUserMenu.setMenuOrdr(menuNo);
				menuOrdr++;
			}
			
			cnt += kwsUserMenuMapper.insert(kwsUserMenu);
//			kwsUserMenu.getMenuNo();
		}
		
		return cnt;
	}
	
	public Integer modify(KwsUserSys kwsUserSys, String userId) throws FdlException{
		KwsUserMenu kwsUserMenuRemove = new KwsUserMenu();
		kwsUserMenuRemove.setSysId(kwsUserSys.getSysId());
		kwsUserMenuRemove.setUserId(userId);
		
		// KWS_SYS update
		Integer cnt = kwsSysMapper.selfSysUpdate(kwsUserSys);
		
		cnt = kwsUserMenuMapper.delete(kwsUserMenuRemove);
		
		Long upperMenuNo = (long) -1;
		Long menuOrdr = (long) 1;
		
		// KWS_USER_MENU 등록
		for(Integer i=0; i < kwsUserSys.getKwsUserMenu().size(); i++){
			
			KwsUserMenu kwsUserMenu = kwsUserSys.getKwsUserMenu().get(i);
			Long menuNo = kwsUserMenuIdGnrService.getNextLongId();
			logger.debug("menuNo :" + menuNo);
			kwsUserMenu.setMenuNo(menuNo);
			kwsUserMenu.setSysId(kwsUserSys.getSysId());
			
			if(kwsUserMenu.getUpperMenuId().equals((long) -1)){
				menuOrdr = (long) 1;
				upperMenuNo = menuNo;
				kwsUserMenu.setMenuOrdr(menuNo);
				menuOrdr++;
			}else{
				kwsUserMenu.setUpperMenuId(upperMenuNo);
				kwsUserMenu.setMenuOrdr(menuNo);
				menuOrdr++;
			}
			
			cnt += kwsUserMenuMapper.insert(kwsUserMenu);
		}
		
		return cnt;
	}
	
	public Integer remove(KwsUserMenu kwsUserMenu, String userId){
		
		KwsUserSys kwsUserSys = new KwsUserSys();
		kwsUserSys.setSysId(kwsUserMenu.getSysId());
		kwsUserSys.setUserId(userId);
		kwsUserSys.setSysTy(SysTy.USER);
		
		Integer cnt = kwsUserMenuMapper.delete(kwsUserMenu);
		cnt += kwsUserSysMapper.delete(kwsUserSys);
		cnt += kwsSysMapper.selfSysDelete(kwsUserSys);
		return cnt;
	}
	
	public List<KwsUserMenu> listUserMenu(KwsUserMenu kwsUserMenu){
		return kwsUserMenuMapper.list(kwsUserMenu);
	}
}
