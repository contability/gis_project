package kr.co.gitt.kworks.web.cmmn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.dto.WfsTransactionDTO;
import kr.co.gitt.kworks.service.edit.EditHistoryService;
import kr.co.gitt.kworks.service.sld.SldCacheService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class ProxyController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ ProxyController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 11. 오후 3:23:01 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 프록시 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
public class ProxyController {
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 메세지 소스
	@Resource
    private MessageSource messageSource;
	
	/// SLD 캐쉬 서비스
	@Resource
	SldCacheService sldCacheService;
	
	/// 편집 이력 서비스
	@Resource
	EditHistoryService editHistorySergvice;
	
	/////////////////////////////////////////////
	/// @fn proxyTms
	/// @brief 함수 간략한 설명 : 프록시 (TMS 에서 사용)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @param response
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/proxy.do", method=RequestMethod.GET)
	public void proxy(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String urlStr = request.getParameter("url");
		URL url = null;
		URLConnection connection = null;
		HttpURLConnection huc = null;
		OutputStream ios = null;
		try {
			url = new URL(urlStr);
			connection = url.openConnection();
			huc = (HttpURLConnection)connection;
			huc.setRequestMethod("GET");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			huc.setDefaultUseCaches(false);
			response.reset();
			response.setContentType(huc.getContentType());
			ios = response.getOutputStream();
			IOUtils.copy(huc.getInputStream(), ios);
			
		} catch (IOException e) {
			logger.warn(e.getMessage());
		} finally {
			if(ios != null) {
				ios.close();
			}
			if(huc != null) {
				huc.disconnect();
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn proxyWMSGet
	/// @brief 함수 간략한 설명 : Get 방식 WMS 프록시
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @param res
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/proxy/wms.do", method=RequestMethod.GET)
	public void proxyWMSGet(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String urlStr = messageSource.getMessage("Map.Url.WMS", null, Locale.getDefault());
		proxyGet(urlStr, request, res);
	}
	
	/////////////////////////////////////////////
	/// @fn proxyWMSPost
	/// @brief 함수 간략한 설명 : Post 방식 WMS 프록시
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @param res
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/proxy/wms.do", method=RequestMethod.POST)
	public void proxyWMSPost(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String urlStr = messageSource.getMessage("Map.Url.WMS", null, Locale.getDefault());
		proxyPost(urlStr, request, res);
	}
	
	/////////////////////////////////////////////
	/// @fn proxyWFSGet
	/// @brief 함수 간략한 설명 : Get 방식 WFS 프록시
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @param res
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/proxy/wfs.do", method=RequestMethod.GET)
	public void proxyWFSGet(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String urlStr = messageSource.getMessage("Map.Url.WFS", null, Locale.getDefault());
		proxyGet(urlStr, request, res);
	}
	
	/////////////////////////////////////////////
	/// @fn proxyWFSPost
	/// @brief 함수 간략한 설명 : Post 방식 WFS 프록시
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @param res
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/proxy/wfs.do", method=RequestMethod.POST)
	public void proxyWFSPost(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String urlStr = messageSource.getMessage("Map.Url.WFS", null, Locale.getDefault());
		proxyPost(urlStr, request, res);
	}
	
	@RequestMapping(value="/proxy/transaction.do", method=RequestMethod.POST)
	public void proxyTransactionPost(WfsTransactionDTO wfsTransactionDTO, HttpServletRequest request, HttpServletResponse res) throws Exception {
		String urlStr = messageSource.getMessage("Map.Url.WFS", null, Locale.getDefault());
		HttpURLConnection huc = null;
		OutputStream ios = null;
		
		InputStream inputDataStream = null;
		InputStream outputDataStream = null;
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		URL url;
		try {
			url = new URL(urlStr+"?");
			URLConnection connection = url.openConnection();
			huc = (HttpURLConnection)connection;
			huc.setRequestMethod("POST");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			huc.setDefaultUseCaches(false);
			huc.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
			
			String inputDataString = IOUtils.toString(request.getInputStream());
			inputDataStream = IOUtils.toInputStream(inputDataString, "UTF-8");
			IOUtils.copy(inputDataStream, huc.getOutputStream());

			res.reset();
			res.setContentType(huc.getContentType());
			ios = res.getOutputStream();
			
			String outputDataString = IOUtils.toString(huc.getInputStream());
			outputDataStream = IOUtils.toInputStream(outputDataString, "UTF-8");
			IOUtils.copy(outputDataStream, ios);

			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			wfsTransactionDTO.setUserId(userDTO.getUserId());
			String userNm = userDTO.getUserNm();
			
			logger.trace("Output Data String : " + outputDataString);
			
			editHistorySergvice.addEditHistories(wfsTransactionDTO, userNm, inputDataString, outputDataString);
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			long useTime = endTime - startTime;
			
//			logger.debug("Input Data String : " + inputDataString);
//			logger.debug("Output Data String : " + outputDataString);
//			logger.debug("ProxyPost UseTime : " + useTime + "(ms)");
//			logger.debug("ProxyPost URL : " + urlStr);
			
			logger.trace("Input Data String : " + inputDataString);
			logger.trace("Output Data String : " + outputDataString);
			logger.trace("ProxyPost UseTime : " + useTime + "(ms)");
			logger.trace("ProxyPost URL : " + urlStr);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		} finally {
			if(ios != null) {
				ios.close();
			}
			if(huc != null) {
				huc.disconnect();
			}
			if(inputDataStream != null) {
				inputDataStream.close();
			}
			if(outputDataStream != null) {
				outputDataStream.close();
			}
		}
	}
	
	
	/////////////////////////////////////////////
	/// @fn proxyGet
	/// @brief 함수 간략한 설명 : Get 방식 프록시
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param urlStr
	/// @param request
	/// @param res
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void proxyGet(String urlStr, HttpServletRequest request, HttpServletResponse res) throws IOException {
		HttpURLConnection huc = null;
		OutputStream ios = null;
		
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			request.setCharacterEncoding("UTF-8");
			StringBuffer params = new StringBuffer();
			for(Object param : request.getParameterMap().entrySet()) {
				@SuppressWarnings("unchecked")
				Entry<String, String[]> entry = (Entry<String, String[]>) param;
				
				if(entry.getKey().equalsIgnoreCase("SLD_KEY")) {
					String[] values = entry.getValue();
					if(values.length > 0) {
						params.append("SLD=http://");
						params.append(request.getServerName());
						params.append(":");
						params.append(request.getServerPort());
						params.append(request.getContextPath());
						params.append("/cmmn/sld/getSLD.do?sldKey=");
						params.append(values[0]);
						params.append("&");
					}
				}
				else if(entry.getKey().indexOf('=') >= 0)
				{
					params.append(getLocaleString(entry.getKey()));
				}
				else {
					params.append(entry.getKey());
					params.append("=");
					
					String[] values = entry.getValue();
					if(values.length > 0) {
						if (request.getCharacterEncoding() == null)
							params.append(URLEncoder.encode(getLocaleString(values[0]), "UTF-8"));
						else
							params.append(URLEncoder.encode(values[0], "UTF-8"));
					}
					params.append("&");
				}
			}
			if(params.length() > 0 && params.substring(params.length()-1).equals("&"))
				params.deleteCharAt(params.length()-1);
			
			URL url = new URL(urlStr.concat("?")+params);

			URLConnection connection = url.openConnection();
			huc = (HttpURLConnection)connection;
			huc.setRequestMethod("GET");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			huc.setDefaultUseCaches(false);
			
			res.reset();
			res.setContentType(huc.getContentType());
			
			ios = res.getOutputStream();
			IOUtils.copy(huc.getInputStream(), ios);
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			long useTime = endTime - startTime;
			logger.debug("ProxyGet UseTime : " + useTime + "(ms)");
			logger.debug("ProxyGet URL : " + urlStr);
			logger.debug("ProxyGet Param : " + params);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			//throw e;
		} finally {
			if(ios != null) {
				ios.close();
			}
			if(huc != null) {
				huc.disconnect();
			}
		}
		
		
	}
	
	/////////////////////////////////////////////
	/// @fn proxyPost
	/// @brief 함수 간략한 설명 : Post 방식 프록시
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param urlStr
	/// @param request
	/// @param res
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void proxyPost(String urlStr, HttpServletRequest request, HttpServletResponse res) throws IOException {
		HttpURLConnection huc = null;
		OutputStream ios = null;
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		URL url;
		try {
			url = new URL(urlStr+"?");
			URLConnection connection = url.openConnection();
			huc = (HttpURLConnection)connection;
			huc.setRequestMethod("POST");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			huc.setDefaultUseCaches(false);
			huc.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
			
			IOUtils.copy(request.getInputStream(), huc.getOutputStream());

			res.reset();
			res.setContentType(huc.getContentType());
			
			ios = res.getOutputStream();
			
			IOUtils.copy(huc.getInputStream(), ios);
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			long useTime = endTime - startTime;
			logger.debug("ProxyPost UseTime : " + useTime + "(ms)");
			logger.debug("ProxyPost URL : " + urlStr);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			//throw e;
		} finally {
			if(ios != null) {
				ios.close();
			}
			if(huc != null) {
				huc.disconnect();
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn getLocaleString
	/// @brief 함수 간략한 설명 : 한글 값 처리
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param value
	/// @return
	/// @throws UnsupportedEncodingException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String getLocaleString(String value) throws UnsupportedEncodingException {
		byte[] b;
		b = value.getBytes("8859_1");
		final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
		try {
			final CharBuffer r = decoder.decode(ByteBuffer.wrap(b));
			return r.toString();
		} catch (final CharacterCodingException e) {
			return new String(b, "EUC-KR");
		}
	}
	
}
