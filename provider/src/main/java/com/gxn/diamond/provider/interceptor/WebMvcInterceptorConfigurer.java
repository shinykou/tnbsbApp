package com.gxn.diamond.provider.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gaoxiaoning created on 17/12/13.
 * @version
 */
@Configuration
public class WebMvcInterceptorConfigurer implements WebMvcConfigurer {
    @Autowired private AccessInterceptor accessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor).
                excludePathPatterns("/teachcourse/common/test.json","/**/login/**","/**/register/**","/**/alipay/**");
    }

}
