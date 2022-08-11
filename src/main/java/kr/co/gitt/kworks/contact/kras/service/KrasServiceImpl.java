package kr.co.gitt.kworks.contact.kras.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;

import kr.co.gitt.kworks.contact.kras.model.KrasResponse;
import kr.co.gitt.kworks.service.cmmn.ErrorService;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class KrasServiceImpl
/// kr.co.gitt.kworks.cntc.service.kras \n
///   ㄴ KrasServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 11. 오전 11:22:21 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는  부동산종합공부시스템 연계 공통 서비스 구현 클래스입니다.
///   -# 
/////////////////////////////////////////////
@Service("krasCommonService")
//@Profile({"ss_oper", "gn_oper", "dh_oper", "gc_oper", "sc_oper", "yy_oper", "yg_oper", "sunchang_oper", "gs_oper", "is_oper"})
@Profile({"ss_oper", "gn_oper", "dh_oper", "gc_oper", "sc_oper", "yy_oper", "yg_oper", "sunchang_oper", "gs_oper", "is_oper","mj_oper"})
public class KrasServiceImpl implements KrasService {
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	/// 에러 서비스
	@Resource
	ErrorService errorService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kworks.itf.service.KrasCommonService#call(java.util.List)
	/////////////////////////////////////////////
	@Override
	public KrasResponse call(List<NameValuePair> parameters, Integer timeout) throws IOException {
		parameters.add(new BasicNameValuePair("conn_sys_id", messageSource.getMessage("Contact.Kras.SysId", null, Locale.getDefault())));
		
		KrasResponse krasResponse = null;
		InputStream is = null;
		try {
			RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
			CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
			
			HttpPost post = new HttpPost(messageSource.getMessage("Contact.Kras.Url", null, Locale.getDefault()));
			post.setEntity(new UrlEncodedFormEntity(parameters));
			
			HttpResponse response = client.execute(post);
			is = response.getEntity().getContent();
			
			JAXBContext context = JAXBContext.newInstance(KrasResponse.class);
			krasResponse = (KrasResponse) context.createUnmarshaller().unmarshal(is);
		}
		catch(HttpHostConnectException hhce) {
			return null;
		}
		catch(Exception exception) {
			errorService.addError(exception);
		}
		finally {
			if(is != null) is.close();
		}
		
		return krasResponse;
	}

}
