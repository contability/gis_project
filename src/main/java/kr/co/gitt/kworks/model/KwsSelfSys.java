package kr.co.gitt.kworks.model;

import java.util.List;

public class KwsSelfSys {
	//사용자 ID
	private String userId;
	
	//시스템 ID
	private Long sysId;
	
	private String sysAlias;
	
	private String sysDc;
	
	private List<KwsUserMenu> kwsUserMenu;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getSysAlias() {
		return sysAlias;
	}

	public void setSysAlias(String sysAlias) {
		this.sysAlias = sysAlias;
	}

	public String getSysDc() {
		return sysDc;
	}

	public void setSysDc(String sysDc) {
		this.sysDc = sysDc;
	}

	public List<KwsUserMenu> getKwsUserMenu() {
		return kwsUserMenu;
	}

	public void setKwsUserMenu(List<KwsUserMenu> kwsUserMenu) {
		this.kwsUserMenu = kwsUserMenu;
	}

}
