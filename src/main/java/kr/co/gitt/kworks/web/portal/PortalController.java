package kr.co.gitt.kworks.web.portal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.model.KwsSysAuthor;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.model.KwsUserSys;
import kr.co.gitt.kworks.service.authorGroup.AuthorGroupService;
import kr.co.gitt.kworks.service.conectStats.UnitySysService;
import kr.co.gitt.kworks.service.notice.NoticeService;
import kr.co.gitt.kworks.service.recsroom.RecsroomService;
import kr.co.gitt.kworks.service.selfSys.SelfSystemService;
import kr.co.gitt.kworks.service.sys.SysService;
import kr.co.gitt.kworks.service.user.UserService;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class PortalController
/// kr.co.gitt.kworks.web.portal \n
///   ㄴ PortalController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 9. 오후 6:11:26 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 포털 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
public class PortalController {
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;
	
	/// 시스템 서비스
	@Resource
	SysService sysService;
	
	/// 사용자 시스템 서비스
	@Resource
	SelfSystemService selfSystemService;
	
	/// 공지사항 서비스
	@Resource
	NoticeService noticeService;
	
	/// 자료실 서비스
	@Resource
	RecsroomService recsroomService;
	
	// 사용자 서비스
	@Resource
	UserService userService;
	
	// 권한 그룹 서비스
	@Resource
	AuthorGroupService authorGroupService;
	
	@Resource
	UnitySysService unitySysService;
	
	
	/////////////////////////////////////////////
	/// @fn portalPage
	/// @brief 함수 간략한 설명 : 포털 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/portal.do")
	public String portalPage(Model model) throws ParseException {
		String prjCode = messageSource.getMessage("Globals.Prj", null, Locale.getDefault());
		
		SearchDTO searchDTO = new SearchDTO();
		searchDTO.setFirstIndex(0);
		searchDTO.setLastIndex(5);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		if(userDTO.getKwsAuthorGroups() == null) {
			String userId = userDTO.getUserId();
			KwsUser kwsUser = userService.selectOneUser(userId);
			List<KwsAuthorGroup> kwsAuthorGroups = new ArrayList<KwsAuthorGroup>();
			for(KwsAuthorGroup kwsAuthorGroup : kwsUser.getKwsAuthorGroups()) {
				Long authorGroupNo = kwsAuthorGroup.getAuthorGroupNo();
				kwsAuthorGroups.add(authorGroupService.selectOneAuthorGroup(authorGroupNo));
			}
			userDTO.setKwsAuthorGroups(kwsAuthorGroups);
		}

		// 사용자 시스템 권한 
		List<KwsSysAuthor> kwsSysAuthors = null;
		for(int i=0, len=userDTO.getKwsAuthorGroups().size(); i < len; i++) {
			KwsAuthorGroup kwsAuthorGroup = userDTO.getKwsAuthorGroups().get(i);
			if(i==0) {
				kwsSysAuthors = kwsAuthorGroup.getKwsSysAuthors();
			}
			else {
				for(KwsSysAuthor kwsSysAuthor : kwsSysAuthors) {
					if(StringUtils.equals("N", kwsSysAuthor.getAuthorAt())) {
						for(KwsSysAuthor author : kwsAuthorGroup.getKwsSysAuthors()) {
							if(kwsSysAuthor.getSysId() == author.getSysId()) {
								kwsSysAuthor.setAuthorAt(author.getAuthorAt());
								break;
							}
						}
					}
				}
			}
		}
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		//List<KwsSys> sysList = sysService.listAllSys(kwsSys);
		
		// 동해 정책지도 서비스
		List<KwsSys> sysList = null;
		
		if(prjCode.equals("dh")){
			sysList = sysService.listAllSys(kwsSys);
			
			sysList.add(sysList.get(5));
			sysList.remove(5);
		}else{
			sysList = sysService.listAllSys(kwsSys);
		}
		
		// 동해 정책지도 서비스 끝
		
		KwsUserSys kwsUserSys = new KwsUserSys();
		kwsUserSys.setUserId(userDTO.getUserId());
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();
		searchDTO.setSearchEndDt(date.format(cal.getTime()));
		
		cal.add(Calendar.MONTH, -1);
		cal.add(Calendar.DATE, +1);
		searchDTO.setSearchStartDt(date.format(cal.getTime()));
		ArrayList<HashMap<String,String>> list = unitySysService.listGroupByMonthCount(searchDTO);
		
		cal.add(Calendar.MONTH, +1);
		cal.add(Calendar.DATE, -1);
		searchDTO.setSearchStartDt(cal.get(Calendar.YEAR)+"-01-01");
		ArrayList<HashMap<String,String>> thisYearList = unitySysService.listGroupByMonthCount(searchDTO);
		
		cal.add(Calendar.YEAR, -1);
		searchDTO.setSearchEndDt(date.format(cal.getTime()));
		searchDTO.setSearchStartDt(cal.get(Calendar.YEAR)+"-01-01");
		ArrayList<HashMap<String,String>> lastYearList = unitySysService.listGroupByMonthCount(searchDTO);
		
		model.addAttribute("result", unitySysService.listTodayGroupByHourCount()); // 2020.03.13 문세준 추가 148 ~ 169 - 접속통계
		model.addAttribute("monthResult", list);
		model.addAttribute("thisYearResult", thisYearList);
		model.addAttribute("lastYearResult", lastYearList);
		model.addAttribute("sysAuthorList", kwsSysAuthors);
		model.addAttribute("sysList", sysList);
		model.addAttribute("selfSysemList", selfSystemService.list(kwsUserSys));
		model.addAttribute("noticeList", noticeService.listNotice(searchDTO));
		model.addAttribute("recsroomList", recsroomService.listRecsroom(searchDTO));
		model.addAttribute("userGrad", userDTO.getUserGrad());
		model.addAttribute("userId", userDTO.getUserId());	//2017.10.27 이승재 추가 - 동해 인허가 의사결정지원시스템 링크 추가 관련
		
		return "projects/" + prjCode + "/portal/portal";
	}

}
