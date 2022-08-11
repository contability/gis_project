package kr.co.gitt.kworks.web.manage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.service.mobile.MobileExportService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 재난현장 모바일 
 * @author kwangsu.lee
 *
 */
@Controller
@RequestMapping("/manage/mobileExport/")
@Profile({"gc_dev", "gc_oper"})
public class MobileExportController {

	@Resource
	MobileExportService mobileExportService;
	
	/**
	 * 다운로드 이력 검색
	 * @param searchDTO - 검색조건
	 * @param paginationInfo - 페이징
	 * @param model
	 * @return
	 */
	@RequestMapping(value="history/list.do", method=RequestMethod.GET)
	public String listPage(ExportSearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {

		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(mobileExportService.listCount(searchDTO));
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", mobileExportService.list(searchDTO));
		
		return "/manage/mobile/listExport";
	}
	
	/**
	 * 데이터 다운로드
	 * @param response
	 * @param userAgent
	 * @throws Exception
	 */
	@RequestMapping(value="download.do", method=RequestMethod.GET)
	public void download(HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		
		String exportZip = mobileExportService.export();
		if (exportZip != null && exportZip.length() > 0) {
			String fileName = "mobileLayers.zip"; 
			String docName = null;
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
				docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
			}
			else {
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
			}
			response.setHeader("Content-Disposition", "attachment; filename="+docName);
			
			InputStream is = new FileInputStream(exportZip);
			OutputStream os = response.getOutputStream();
			IOUtils.copy(is,  os);
			is.close();
			os.close();
		}
	}	
}
