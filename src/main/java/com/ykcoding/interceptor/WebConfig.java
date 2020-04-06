package com.ykcoding.interceptor;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * 拦截器配置
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 不需要登录拦截的url
     */


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("/admin","/admin/login")
                .addPathPatterns("/admin/**");


    }
}
