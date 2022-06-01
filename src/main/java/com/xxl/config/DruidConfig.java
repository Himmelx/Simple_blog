package com.xxl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")    //获取配置文件里面的信息
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    // 后台监控： web.xml
    // 因为springboot内置了servlet容器，所以没有web.xml，代替方法
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>
                (new StatViewServlet (), "/druid/*"); // 现在要进行druid监控的配置处理操作);

        //后台需要有人登录，账号密码配值
        HashMap<String, String> initParameters = new HashMap<>();

        //增加配置
        initParameters.put("loginUsername","admin");  //登录用key 固定的 loginUsername
        initParameters.put("loginPassword","123456");

        //允许谁可以访问
        initParameters.put("allow","");   //不加参数就是所有人

        //禁止谁访问  initParamters.put("xxl","192.168.11.123");

        bean.setInitParameters(initParameters);  //设置初始化参数

        return bean;
    }

    //filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        //过滤的请求放在map里面
        HashMap<String, String> initParameters = new HashMap<>();

        //对这些东西不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParameters);

        return bean;
    }
}
