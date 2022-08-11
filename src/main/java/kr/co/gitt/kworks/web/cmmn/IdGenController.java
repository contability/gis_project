package kr.co.gitt.kworks.web.cmmn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.CamelUtil;

/////////////////////////////////////////////
/// @class IdGenController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ IdGenController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:32:54 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 아이디 생성 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cmmn/idgen/")
public class IdGenController implements ApplicationContextAware {

	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

    /// 어플리케이션 컨텍스트
    ApplicationContext context;
    
    /////////////////////////////////////////////
    /// @fn 
    /// @brief (Override method) 함수 간략한 설명
    /// @remark
    /// - 오버라이드 함수의 상세 설명
    /// @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
    /////////////////////////////////////////////
    @Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
    	this.context = context;
	}
    
    /////////////////////////////////////////////
    /// @fn kcoDataIdSeq
    /// @brief 함수 간략한 설명 : 다음 ID 값 반환
    /// @remark
    /// - 함수의 상세 설명 : 
    /// @param tableName
    /// @param model
    /// @return
    /// @throws Exception 
    ///
    ///~~~~~~~~~~~~~{.java}
    /// // 핵심코드
    ///~~~~~~~~~~~~~
    /////////////////////////////////////////////
    @RequestMapping("{dataId}/next.do")
    public String kcoDataIdSeq(@PathVariable("dataId") String dataId, ModelMap model)throws Exception{
    	StringBuffer beanName = new StringBuffer();
    	beanName.append(CamelUtil.convert2CamelCase(dataId));
    	beanName.append("IdGnrService");
    	EgovIdGnrService idGnrService = context.getBean(beanName.toString(), EgovIdGnrService.class);
    	model.addAttribute("id", idGnrService.getNextStringId());
        return "jsonView";
    }
}
