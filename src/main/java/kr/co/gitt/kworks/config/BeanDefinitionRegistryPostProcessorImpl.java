package kr.co.gitt.kworks.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/////////////////////////////////////////////
/// @class BeanDefinitionRegistryPostProcessorImpl
/// kworks.config \n
///   ㄴ BeanDefinitionRegistryPostProcessorImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 3. 11. 오전 11:43:02 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 Bean 정의 등록 프로세서입니다.
///   -# 
/////////////////////////////////////////////
@Component
public class BeanDefinitionRegistryPostProcessorImpl implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

	/// 어플리케이션 컨텍스트
    ApplicationContext applicationContext;
    
    /////////////////////////////////////////////
    /// @fn 
    /// @brief (Override method) 함수 간략한 설명
    /// @remark
    /// - 오버라이드 함수의 상세 설명
    /// @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
    /////////////////////////////////////////////
    @Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	/////////////////////////////////////////////
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		IdGenBeanFactory idGenBeanBuilder = new IdGenBeanFactory();
		idGenBeanBuilder.createIdGenBean(beanFactory, applicationContext);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
	/////////////////////////////////////////////
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
	}
	
}
