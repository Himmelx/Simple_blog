package com.xxl.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {

    //只需要这一个方法处理就行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object loginUser = request.getSession().getAttribute("LoginUser");

        //判断是否为空来判断是否登录
        if (loginUser==null){
            request.setAttribute("msg","没有权限登录");
            //转发到登录页面
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }else
            return true;
    }
}
