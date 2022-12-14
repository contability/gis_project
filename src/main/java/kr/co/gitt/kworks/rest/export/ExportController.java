package kr.co.gitt.kworks.rest.export;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsExport;
import kr.co.gitt.kworks.model.KwsExport.ExportFilterTy;
import kr.co.gitt.kworks.model.KwsExport.ExportTy;
import kr.co.gitt.kworks.model.KwsExportConfm;
import kr.co.gitt.kworks.model.KwsExportData;
import kr.co.gitt.kworks.model.KwsExportExcelOptn;
import kr.co.gitt.kworks.model.KwsExportFilterBbox;
import kr.co.gitt.kworks.model.KwsExportFilterDong;
import kr.co.gitt.kworks.model.KwsExportFilterFid;
import kr.co.gitt.kworks.model.KwsExportFilterImage;
import kr.co.gitt.kworks.model.KwsExportFilterOutpt;
import kr.co.gitt.kworks.model.KwsExportFilterPolygon;
import kr.co.gitt.kworks.model.KwsExportOutpt;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.export.ExportHistoryService;
import kr.co.gitt.kworks.service.export.ExportService;
import kr.co.gitt.kworks.service.file.FileService;
import kr.co.gitt.kworks.service.user.UserService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class ExportController
/// kr.co.gitt.kworks.rest.export \n
///   ??? ExportController.java
/// @section ?????????????????????
///    |    ???  ???       |      ???  ???       |
///    | :-------------: | -------------   |
///    | Company | (???)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 13. ?????? 9:01:15 |
///    | Class Version | v1.0 |
///    | ????????? | libraleo, Others... |
/// @section ????????????
/// - ??? ???????????? ???????????? ???????????? ?????????.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/export/")
public class ExportController {
	
	// ??????
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// ???????????? ?????????
	@Resource
	ExportService exportService;
	
	/// ???????????? ?????? ?????????
	@Resource
	ExportHistoryService exportHistoryService;
	
	/// ????????? ?????????
	@Resource
	ImageService imageService;
	
	/// ????????? ?????????
	@Resource
	UserService userService;
	
	/// ?????? ?????????
	@Resource
	FileService fileService;
	
	/// ???????????? ?????? ??????
	public enum ExportAuthorMode {
		VIEW, AUTHOR
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneExport
	/// @brief ?????? ????????? ?????? : ??? ??? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/select.do", method=RequestMethod.GET)
	public String selectOneExport(@PathVariable("exportNo") Long exportNo, Model model) {
		KwsExport kwsExport = exportService.selectOneExport(exportNo);
		if(authorConfirm(kwsExport, ExportAuthorMode.VIEW)) {
			model.addAttribute("data", exportService.selectOneExport(exportNo));
			return "jsonView";
		}
		else {
			return "redirect:/accessDenied.do";
		}
	}
	
	/////////////////////////////////////////////
	/// @fn consentExport
	/// @brief ?????? ????????? ?????? : ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportNo
	/// @param kwsExport
	/// @param kwsExportConfm
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/consent.do", method=RequestMethod.POST)
	public String consentExport(@PathVariable("exportNo") Long exportNo, KwsExport kwsExport, KwsExportConfm kwsExportConfm, Model model) throws Exception {
		KwsExport selectModel = exportService.selectOneExport(exportNo);
		if(authorConfirm(selectModel, ExportAuthorMode.AUTHOR)) {
			kwsExport.setExportNo(exportNo);
			kwsExportConfm.setExportNo(exportNo);
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			kwsExportConfm.setConfmerId(userDTO.getUserId());

			kwsExport.setKwsExportConfm(kwsExportConfm);
			
			// ?????? - ???????????? ???????????? Service ??? ?????? (Transaction ?????? ??????)
			model.addAttribute("rowCount", exportService.consentExport(kwsExport));
			return "jsonView";
		}
		else {
			return "redirect:/accessDenied.do";
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addReturn
	/// @brief ?????? ????????? ?????? : ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportNo
	/// @param kwsExport
	/// @param kwsExportConfm
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/return.do", method=RequestMethod.POST)
	public String addReturn(@PathVariable("exportNo") Long exportNo, KwsExport kwsExport, KwsExportConfm kwsExportConfm, Model model) throws Exception {
		KwsExport selectModel = exportService.selectOneExport(exportNo);
		if(authorConfirm(selectModel, ExportAuthorMode.AUTHOR)) {
			kwsExport.setExportNo(exportNo);
			kwsExportConfm.setExportNo(exportNo);
			
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			kwsExportConfm.setConfmerId(userDTO.getUserId());
			
			kwsExport.setKwsExportConfm(kwsExportConfm);
			
			// ?????? - ???????????? ???????????? Service ??? ?????? (Transaction ?????? ??????)
			model.addAttribute("rowCount", exportService.returnExport(kwsExport));
			return "jsonView";
		}
		else {
			return "redirect:/accessDenied.do";
		}
		
		
	}
	
	/////////////////////////////////////////////
	/// @fn addByOutput
	/// @brief ?????? ????????? ?????? : ?????? ???????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportDTO
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addByOutput.do", method=RequestMethod.POST)
	public String addByOutput(ExportOutputDTO exportDTO, Model model) throws Exception {
		
		// ????????????
		ModelMapper modelMapper = new ModelMapper();
		KwsExport kwsExport = modelMapper.map(exportDTO, KwsExport.class);
		kwsExport.setExportFilterTy(ExportFilterTy.OUTPUT);
		
		// ?????? ??????
		KwsExportFilterOutpt kwsExportFilterOutpt = modelMapper.map(exportDTO, KwsExportFilterOutpt.class);
		kwsExport.setKwsExportFilterOutpt(kwsExportFilterOutpt);
		
		// ???????????? ?????????
		List<KwsExportData> kwsExportDatas = new ArrayList<KwsExportData>();
		for(String dataId : exportDTO.getDataIds()) {
			KwsExportData kwsExportData = new KwsExportData();
			kwsExportData.setDataId(dataId);
			kwsExportDatas.add(kwsExportData);
		}
		kwsExport.setKwsExportDatas(kwsExportDatas);
		
		// ?????????
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsExport.setRqesterId(userDTO.getUserId());
		
		model.addAttribute("rowCount", exportService.addExport(kwsExport));
		return "jsonView";		
	}
	
	/////////////////////////////////////////////
	/// @fn addByData
	/// @brief ?????? ????????? ?????? : ????????? (DXF, EXCEL, SHAPE) ???????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportDTO
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addByData.do", method=RequestMethod.POST)
	public String addByData(ExportDataDTO exportDTO, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		// ????????????
		ModelMapper modelMapper = new ModelMapper();
		KwsExport kwsExport = modelMapper.map(exportDTO, KwsExport.class);
		
		// ?????????
		kwsExport.setRqesterId(userDTO.getUserId());
		
		// ???????????? ?????????
		if(kwsExport.getExportTy().equals(ExportTy.EXCEL)) {
			
			List<KwsExportData> kwsExportDatas = new ArrayList<KwsExportData>();
			for(String dataId : exportDTO.getDataIds()){
				KwsExportData kwsExportData = new KwsExportData();
				kwsExportData.setDataId(dataId);
				
				kwsExportDatas.add(kwsExportData);
			}
			kwsExport.setKwsExportDatas(kwsExportDatas);
			
			KwsExportExcelOptn kwsExportExcelOptn = new KwsExportExcelOptn();
			kwsExportExcelOptn.setFieldNmIndictAt(exportDTO.getFieldNmIndictAt()?"Y":"N");
			kwsExport.setKwsExportExcelOptn(kwsExportExcelOptn);
			
		}else{
			
			List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
			List<KwsExportData> kwsExportDatas = new ArrayList<KwsExportData>();
			for(String dataId : exportDTO.getDataIds()) {
				KwsExportData kwsExportData = new KwsExportData();
				kwsExportData.setDataId(dataId);
				boolean isAuthor = false;
				for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
					for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
						if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getExportAt())) {
							isAuthor = true;
							break;
						}
					}
				}
				if(isAuthor) {
					kwsExportDatas.add(kwsExportData);
				}
			}
			kwsExport.setKwsExportDatas(kwsExportDatas);
			
			if(kwsExportDatas.size() <= 0) {
				logger.warn("????????? ????????? ????????????.");
				return "redirect:/accessDenied.do";
			}
		}
		
		// ???????????? ??????
		if(kwsExport.getExportFilterTy().equals(ExportFilterTy.BBOX)) {
			KwsExportFilterBbox kwsExportFilterBbox = modelMapper.map(exportDTO, KwsExportFilterBbox.class);
			kwsExport.setKwsExportFilterBbox(kwsExportFilterBbox);
		}
		else if(kwsExport.getExportFilterTy().equals(ExportFilterTy.DONG)) {
			KwsExportFilterDong kwsExportFilterDong = modelMapper.map(exportDTO, KwsExportFilterDong.class);
			kwsExport.setKwsExportFilterDong(kwsExportFilterDong);
		}
		else if(kwsExport.getExportFilterTy().equals(ExportFilterTy.FIDS)) {
			List<KwsExportFilterFid> kwsExportFilterFids = new ArrayList<KwsExportFilterFid>();
			List<Long> ids = exportDTO.getIds();
			for(Long id : ids) {
				KwsExportFilterFid kwsExportFilterFid = new KwsExportFilterFid();
				kwsExportFilterFid.setFid(id);
				kwsExportFilterFids.add(kwsExportFilterFid);
			}
			
			kwsExport.setKwsExportFilterFids(kwsExportFilterFids);
		}
		else if(kwsExport.getExportFilterTy().equals(ExportFilterTy.POLYGON)) {
			KwsExportFilterPolygon kwsExportFilterPolygon = new KwsExportFilterPolygon();
			kwsExportFilterPolygon.setWkt(exportDTO.getWkt());
			kwsExport.setKwsExportFilterPolygon(kwsExportFilterPolygon);
		}
		else {
			throw new Exception("???????????? ?????? ???????????? ?????? ???????????????.");
		}
		
		model.addAttribute("rowCount", exportService.addExport(kwsExport));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addByImage
	/// @brief ?????? ????????? ?????? : ????????? ???????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportDTO
	/// @param model
	/// @return
	/// @throws IOException
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addByImage.do", method=RequestMethod.POST)
	public String addByImage(ExportImageDTO exportDTO, Model model) throws IOException, FdlException {
		String data = exportDTO.getData();
		String base64Str = null;
		String[] split = data.split(",");
		if(split.length == 2) {
			base64Str = split[1];
		}
		
		BufferedImage bufferedImage = imageService.getBufferdImageByBase64String(base64Str);
		
		ModelMapper modelMapper = new ModelMapper();
		KwsExport kwsExport = modelMapper.map(exportDTO, KwsExport.class);
		kwsExport.setExportFilterTy(ExportFilterTy.IMAGE);
		
		// ???????????? ?????????
		//Vector ?????? baseMap??? ????????? ???????????? ?????? ??????
		//dataId??? ?????? ?????? ??????
		if (exportDTO.getDataIds() != null) {
			List<KwsExportData> kwsExportDatas = new ArrayList<KwsExportData>();
			for(String dataId : exportDTO.getDataIds()) {
				KwsExportData kwsExportData = new KwsExportData();
				kwsExportData.setDataId(dataId);
				kwsExportDatas.add(kwsExportData);
			}
			kwsExport.setKwsExportDatas(kwsExportDatas);
		}
	
		// ???????????? ?????? ?????????
		kwsExport.setKwsExportFilterImage(modelMapper.map(exportDTO, KwsExportFilterImage.class));
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsExport.setRqesterId(userDTO.getUserId());
		
		model.addAttribute("rowCount", exportService.addExport(kwsExport, bufferedImage));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn download
	/// @brief ?????? ????????? ?????? : ???????????? ?????? ????????????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportNo
	/// @param fileNo
	/// @param response
	/// @param userAgent
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/{fileNo}/download.do")
	public void download(@PathVariable("exportNo") Long exportNo, @PathVariable("fileNo") Long fileNo, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		KwsExport selectModel = exportService.selectOneExport(exportNo);
		if(authorConfirm(selectModel, ExportAuthorMode.VIEW)) {
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			KwsExportOutpt kwsExportOutpt = new KwsExportOutpt();
			kwsExportOutpt.setExportNo(exportNo);
			kwsExportOutpt.setUserId(userDTO.getUserId());
			exportHistoryService.insertExportOutput(kwsExportOutpt);
			
			KwsFile kwsFile = fileService.selectOneFile(fileNo);
			InputStream is = new FileInputStream(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
			
			String fileName = kwsFile.getOrignlFileNm();
			String docName = null;
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
				docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
			}
			else {
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
			}
			response.setHeader("Content-Disposition", "attachment; filename="+docName);
			
			OutputStream os = response.getOutputStream();
			IOUtils.copy(is,  os);
			is.close();
			os.close();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addExportOutput
	/// @brief ?????? ????????? ?????? : ???????????? ?????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportNo
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{exportNo}/addOutput.do", method=RequestMethod.POST)
	public String addExportOutput(@PathVariable("exportNo") Long exportNo, Model model) throws FdlException {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		KwsExportOutpt kwsExportOutpt = new KwsExportOutpt();
		kwsExportOutpt.setExportNo(exportNo);
		kwsExportOutpt.setUserId(userDTO.getUserId());
		model.addAttribute("rowCount", exportHistoryService.insertExportOutput(kwsExportOutpt));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn authorConfirm
	/// @brief ?????? ????????? ?????? : ???????????? ?????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param kwsExport
	/// @param exportAuthorMode
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private boolean authorConfirm(KwsExport kwsExport, ExportAuthorMode exportAuthorMode) {
		boolean isAuthor = false;
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		switch (userDTO.getUserGrad()) {
			// ?????? ?????? ??????
			case ROLE_ANONYMOUS:
				isAuthor = false;
				break;
			// ?????? ???????????? ?????? ???????????? ???????????? ?????? ?????? ???????????? ?????? ??????
			case ROLE_DEPT_MNGR:
				String userId = kwsExport.getRqesterId();
				KwsUser kwsUser = userService.selectOneUser(userId);
				if(StringUtils.equals(userDTO.getDeptCode(), kwsUser.getDeptCode())) {
					isAuthor = true;
				}
				break;
			/// ???????????? ?????? ?????? ??????
			case ROLE_MNGR:
				isAuthor = true;
				break;
			/// ???????????? ????????? ????????? ?????? ?????? ??????
			case ROLE_USER:
				if(exportAuthorMode.equals(ExportAuthorMode.VIEW) && StringUtils.equals(kwsExport.getRqesterId(), userDTO.getUserId())) {
					isAuthor = true;
				}
				break;
			default:
				break;
		}
		return isAuthor;
	}
	
	/////////////////////////////////////////////
	/// @fn getUserGraphics
	/// @brief ?????? ????????? ?????? : ????????? ????????? ??????
	/// @remark
	/// - ????????? ?????? ?????? : 
	/// @param exportNo
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // ????????????
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("getUserGraphics.do")
	public void getUserGraphics(Long exportNo, HttpServletResponse response) throws IOException {
		response.setHeader("content-type", "application/json; charset=UTF-8;");
		String userGraphics = exportService.getUserGraphics(exportNo);
		if(StringUtils.isNotBlank(userGraphics)) {
			response.getWriter().write(userGraphics);
		}
		response.getWriter().flush();
		response.getWriter().close();
	}
}
