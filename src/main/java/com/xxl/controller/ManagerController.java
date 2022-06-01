package com.xxl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxl.pojo.BlogArticle;
import com.xxl.pojo.User;
import com.xxl.service.ArticleServiceImpl;
import com.xxl.service.UserServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xxl
 * @create 2021-12-06 22:18
 */

@Service
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ArticleServiceImpl articleService;

    //博客后台主页面
    @RequestMapping("toManager")
    public String toManager(){
        return "manager";
    }

    //博客总览页面
    @RequestMapping("/toBlogTotal")
    public String toBlogTotal(){
        return "blogTotal";
    }

    //数据表格得到用户列表
    @RequestMapping("/getUsrList")
    @ResponseBody
    public String getUserList(HttpServletRequest request) throws JsonProcessingException {
        //从前端获取页数以及分页数
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        int userCount = userService.getUserCount();

        List<User> list = userService.getUserListByPage((page-1)*limit, limit-1);


        ObjectMapper mapper = new ObjectMapper();

        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("massage","");
        map.put("count",userCount);
        map.put("data",list);

        //将json数据返回前端
        return mapper.writeValueAsString(map);
    }

    //删除用户
    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(@Param("userId") int userId) throws JsonProcessingException {
        String msg = "";
        if(userId<0)
            msg = "参数无效";

        int res_state = userService.deleteUser(userId);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        map.put("state",res_state>0?1:0);
        map.put("code",0);
        map.put("msg",msg);
        return mapper.writeValueAsString(map);
    }

    //去往editUser.html
    @RequestMapping("/toEditUser")
    public String toEditUser(){
        return "editUser";
    }

    //编辑用户
    @RequestMapping("/editUser")
    @ResponseBody
    public String editUser(User user) throws JsonProcessingException {
        System.out.println(user);
        int res_state = userService.updateUser(user);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        map.put("state",res_state>0?1:0);
        map.put("code",0);
        map.put("msg","");
        return mapper.writeValueAsString(map);
    }

    //数据表格展示博客总览
    @RequestMapping("/getArticleList")
    @ResponseBody
    public String getArticleList(HttpServletRequest request) throws JsonProcessingException {
        //从前端获取页数以及分页数
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        List<BlogArticle> list = articleService.queryArticleByPageAndLimit((page-1)*limit, limit);

        int count = articleService.queryAllNumber();

        ObjectMapper mapper = new ObjectMapper();

        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("massage","");
        map.put("count",count);
        map.put("data",list);

        //将json数据返回前端
        return mapper.writeValueAsString(map);
    }

    //数据表格删除博客以及删除对应博客内容
    @RequestMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(@Param("articleId")int articleId) throws JsonProcessingException {
        System.out.println("文章id----->"+articleId);
        String msg = "";
        if(articleId<0)
            msg = "参数无效";

        int res_state = articleService.deleteArticle(articleId);
        System.out.println("删除信息---》"+"res_state");

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        map.put("state",res_state>0?1:0);
        map.put("code",0);
        map.put("msg",msg);
        return mapper.writeValueAsString(map);

    }
}
