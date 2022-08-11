package kr.co.gitt.kworks.rest.themamap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.ThemamapSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsThemamap;
import kr.co.gitt.kworks.model.KwsThemamap.ThemamapTy;
import kr.co.gitt.kworks.service.themamap.ThemamapService;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class ThemamapController
/// kr.co.gitt.kworks.web.gis \n
///   ㄴ ThemamapController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 11. 오후 6:07:22 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 주제도 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/themamap/")
public class ThemamapController {

	/// 주제도 서비스
	@Resource
	ThemamapService themamapService;
	
	/////////////////////////////////////////////
	/// @fn listAllThemamapByUser
	/// @brief 함수 간략한 설명 : 주제도 전체 검색 (사용자)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapSearchDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	
	//2017.07.29 이승재
	//시스템 기본주제도 외 공용주제도 사용을 위해 PUBLIC 주제도 Search
	@RequestMapping(value="listAll.do", method=RequestMethod.GET)
	public String listAllThemamapByUser(Model model) {
		ThemamapSearchDTO themamapSearchDTO = new ThemamapSearchDTO();
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		themamapSearchDTO.setWrterId(userDTO.getUserId());
		
		List<ThemamapTy> themamapTys = new ArrayList<ThemamapTy>();
		themamapTys.add(ThemamapTy.XRAY);
		themamapTys.add(ThemamapTy.SYS);
		themamapTys.add(ThemamapTy.PUBLIC);
		themamapSearchDTO.setThemamapTys(themamapTys);
		
		model.addAttribute("rows", themamapService.listAllThemamap(themamapSearchDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneThemamap
	/// @brief 함수 간략한 설명 : 주제도 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{themamapId}/select.do", method=RequestMethod.GET)
	public String selectOneThemamap(@PathVariable("themamapId") Long themamapId, Model model) throws JsonProcessingException, IOException {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		ThemamapSearchDTO themamapSearchDTO = new ThemamapSearchDTO();
		themamapSearchDTO.setThemamapId(themamapId);
		List<Long> authorGroupNos = new ArrayList<Long>();
		for(KwsAuthorGroup kwsAuthorGroup : userDTO.getKwsAuthorGroups()) {
			authorGroupNos.add(kwsAuthorGroup.getAuthorGroupNo());
		}
		themamapSearchDTO.setAuthorGroupNos(authorGroupNos);
		
		KwsThemamap kwsThemamap = themamapService.selectOneThemamap(themamapSearchDTO);
		model.addAttribute("themamap", kwsThemamap);
		
		String jsonStr = null;
		if(kwsThemamap != null) {
			jsonStr = themamapService.getSldString(kwsThemamap);
		}
		model.addAttribute("jsonStr", jsonStr);
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addThemamap
	/// @brief 함수 간략한 설명 : 주제도 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamap
	/// @param jsonStr
	/// @param model
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addThemamap(KwsThemamap kwsThemamap, String jsonStr, String baseMapStr, HttpServletRequest request, Model model) throws FdlException, IOException {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsThemamap.setWrterId(userDTO.getUserId());
		kwsThemamap.setUpdusrId(userDTO.getUserId());
		
		String sldUrl = createSldUrl(request);
		model.addAttribute("rowCount", themamapService.insertThemamap(kwsThemamap, jsonStr, sldUrl, baseMapStr));
		return "jsonView";
	}
	
	@RequestMapping(value="{themamapId}/remove.do")
	public String removeThemamap(@PathVariable("themamapId") Long themamapId, Model model) {
		model.addAttribute("rowCount", themamapService.removeThemamap(themamapId));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn getThumnail
	/// @brief 함수 간략한 설명 : 썸네일 불러오기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapId
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{themamapId}/getThumnail.do", method=RequestMethod.GET)
	public void getThumnail(@PathVariable("themamapId") Long themamapId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sldUrl = createSldUrl(request);
		BufferedImage bufferedImage = themamapService.getThumnail(themamapId, sldUrl);
		
		response.reset();
		response.setContentType("image/jpeg");
		
		OutputStream os = response.getOutputStream();
		ImageIO.write(bufferedImage, "jpeg", os);
		os.close();
	}
	
	/////////////////////////////////////////////
	/// @fn createSldUrl
	/// @brief 함수 간략한 설명 : SLD 주소 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String createSldUrl(HttpServletRequest request) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("http://");
		stringBuffer.append(request.getServerName());
		stringBuffer.append(":");
		stringBuffer.append(request.getServerPort());
		stringBuffer.append(request.getContextPath());
		stringBuffer.append("/cmmn/sld/getSLD.do?sldKey=");
		return stringBuffer.toString();
	}
	
}
