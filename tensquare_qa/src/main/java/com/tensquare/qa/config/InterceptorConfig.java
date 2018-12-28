package com.tensquare.qa.config;

import com.tensquare.qa.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/24
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{


    @Autowired
    private JwtInterceptor jwtInterceptor;



    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器要申明的对象和要拦截的请求
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**");


    }
}
