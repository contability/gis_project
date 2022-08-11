package kr.co.gitt.kworks.web.manage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.dto.UserSearchDTO;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.model.KwsUser.UserGrad;
import kr.co.gitt.kworks.model.KwsUser.UserType;
import kr.co.gitt.kworks.service.authorGroup.AuthorGroupService;
import kr.co.gitt.kworks.service.dept.DeptService;
import kr.co.gitt.kworks.service.user.UserEnvironmentService;
import kr.co.gitt.kworks.service.user.UserService;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class UserManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ UserManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 16. 오전 11:57:03 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/user/")
public class UserManageController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;
	
	/// 사용자관리 서비스
	@Resource
	UserService userService;
	
	/// 사용자 환경 서비스
	@Resource
	UserEnvironmentService userEnvironmentService;
	
	/// 부서관리 서비스
	@Resource
	DeptService deptService;
	
	/// 권한 그룹 서비스
	@Resource
	AuthorGroupService authorGroupService;
	
	/////////////////////////////////////////////
	/// @fn listUserPage
	/// @brief 함수 간략한 설명 : 사용자 목록 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listUserPage(UserSearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("depts", deptService.listAllDept());
		model.addAttribute("authorGroups", authorGroupService.listAllAuthorGroup());
		model.addAttribute("rows", userService.listUser(searchDTO, paginationInfo));
		model.addAttribute("userGrads", UserGrad.values());
		model.addAttribute("userTypes", UserType.values());
		
		return "/manage/user/listUser";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneUser
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{userId}/select.do", method=RequestMethod.GET)
	public String selectOneUser(@PathVariable("userId") String userId, Model model) {
		model.addAttribute("userGrads", UserGrad.values());
		model.addAttribute("data", userService.selectOneUser(userId));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyUser
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @param kwsUser
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{userId}/modify.do", method=RequestMethod.POST)
	public String modifyUser(@PathVariable("userId") String userId, KwsUser kwsUser, @RequestParam(value="authorGroup", required=false) List<Long> authorGroup, Model model) throws FdlException {
		
		KwsUser applyUser = userService.selectOneUserInfo(userId);
		kwsUser.setUserId(applyUser.getUserId());
		kwsUser.setDeptNm(deptService.selectOneDept(kwsUser.getDeptCode()).getDeptNm());
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsUser.setGradUserId(userDTO.getUserId());
		kwsUser.setGradUserNm(userDTO.getUserNm());
		
		model.addAttribute("rowCount", userService.modifyUser(kwsUser, authorGroup));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addUser
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUser
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addUser(KwsUser kwsUser, @RequestParam(value="authorGroup", required=false) List<Long> authorGroup, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsUser.setGradUserId(userDTO.getUserId());
		kwsUser.setGradUserNm(userDTO.getUserNm());
		
		model.addAttribute("rowCount", userService.addUser(kwsUser, authorGroup));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeUser
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param userId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{userId}/remove.do", method=RequestMethod.POST)
	public String removeUser(@PathVariable("userId") String userId, Model model) {
		model.addAttribute("rowCount", userService.removeUser(userId));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn userExcelDownload
	/// @brief 함수 간략한 설명 : 엑셀 다운로드
	/// @remarka
	/// - 함수의 상세 설명 : 
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("excel.do")
	public ModelAndView userExcelDownload(UserSearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("category", userService.listUserExcel(searchDTO));
	    return new ModelAndView("userExcelDownload", "categoryMap", map);
	} 
	
	@RequestMapping(value="syncSaeol.do", method=RequestMethod.POST)
	public void syncSaeol(HttpServletResponse response) throws IOException {
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(60 * 1000).setConnectionRequestTimeout(60 * 1000).setSocketTimeout(60 * 1000).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		
		StringBuffer url = new StringBuffer();
		url.append(messageSource.getMessage("Contact.Sync.Url", null, Locale.getDefault()));
		url.append("/sync/execute/3");
		
		HttpPost post = new HttpPost(url.toString());
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			HttpResponse res = client.execute(post);
			inputStream = res.getEntity().getContent();
			
			response.reset();
			outputStream = response.getOutputStream();
			IOUtils.copy(inputStream, outputStream);
		}
		catch (IOException e) {
			logger.warn(e.getMessage());
		}
		finally {
			if(inputStream != null) {
				inputStream.close();
			}
			if(outputStream != null) {
				outputStream.close();
			}
		}
		
	}
	
}
