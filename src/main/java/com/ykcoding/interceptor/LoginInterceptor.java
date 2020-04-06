package com.ykcoding.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 拦截器
 * 对未登录用户拦截对后台的请求
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//        if (request.getSession().getAttribute("user") == null) {
//            response.sendRedirect("/admin");
//            return false;
//        }
        return true;
    }
}
