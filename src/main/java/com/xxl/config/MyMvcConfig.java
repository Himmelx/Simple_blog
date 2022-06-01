package com.xxl.config;

import lombok.SneakyThrows;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

//继承webmvcconfig接口，接管springmvc的配置
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    //首页一般在这里配置
    //首页配置：所有静态资源都要由thymeleaf接管：@{}
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        //registry.addViewController("/main.html").setViewName("dashboard");
    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/toLogin","/toRegister","/Register","/GenerateCode",
                        "/main","/index.html", "/","/layui/**",
                        "/css/**","/js/*","/img/*","/images/**","/upload/**");
    }

    //资源映射器,由于idea的资源保护，直接上传文件后需要重启web服务后才能访问，所以需要配置虚拟路径映射
    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\");
//                .addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
//                + "images" + System.getProperty("file.separator"));
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\");
//                .addResourceLocations("file:"+System.getProperty("user.dir")+System.getProperty("file.separator")
//                        + "upload" + System.getProperty("file.separator"));

        // 静态资源映射
        registry.addResourceHandler("/").addResourceLocations("/**");
        //Windows这里static后面要加/
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

}
