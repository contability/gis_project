package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsUserMenu;

public interface KwsUserMenuMapper {

	public List<KwsUserMenu> list(KwsUserMenu kwsUserMenu);
	
	public Integer insert(KwsUserMenu kwsUserMenu);
	
	public Integer update(KwsUserMenu kwsUserMenu);
	
	public Integer delete(KwsUserMenu kwsUserMenu);
}
