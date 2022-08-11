package kr.co.gitt.kworks.service.selfSys;

import java.util.List;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.gitt.kworks.model.KwsUserMenu;
import kr.co.gitt.kworks.model.KwsUserSys;

public interface SelfSystemService {

	public List<KwsUserSys> list(KwsUserSys kwsUserSys);
	
	public KwsUserSys selectOneUserSystem(KwsUserSys kwsUserSys);
	
//	public Integer add(List<KwsUserMenu> kwsUserMenuList, KwsSys kwsSys, String userId) throws FdlException;
	public Integer add(KwsUserSys kwsUserSys, String userId) throws FdlException;	
	
	public Integer modify(KwsUserSys kwsUserSys, String userId) throws FdlException;
	
	public Integer remove(KwsUserMenu kwsUserMenu, String userId);
	
	public List<KwsUserMenu> listUserMenu(KwsUserMenu kwsUserMenu);
}
