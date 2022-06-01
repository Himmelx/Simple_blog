package com.xxl.controller;

import com.xxl.pojo.BlogArticle;
import com.xxl.pojo.BlogArticleContent;
import com.xxl.pojo.User;
import com.xxl.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xxl
 * @create 2021-11-30 22:20
 */

@Controller
public class UserSpaceController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ContentServiceImpl contentService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private PraiseServiceImpl praiseService;

    @RequestMapping("/toHome")
    public String toHome(Model model,HttpSession session){
        //博客轮播图
        List<BlogArticle> carousel = articleService.queryArticleByUserId(6,0);
        //博客精选
        List<BlogArticle> recommend = articleService.queryArticleByUserId(7, 0);

        //博客达人
        List<User> goodMan = userService.getUserListByPage(0, 4);

        //热度排行
        List<BlogArticle> articles = articleService.queryArticleHot();

        User user = (User) session.getAttribute("user");

        model.addAttribute("carousels",carousel);
        model.addAttribute("recommends",recommend);
        model.addAttribute("goodMans",goodMan);
        model.addAttribute("articles",articles);
        model.addAttribute("user",user);

        return "home";
    }

    @RequestMapping("/toUserSpace")
    public String toUserSpace(Model model,HttpSession session){

        User user1 = (User) session.getAttribute("user");
        User user = userService.queryUserByName(user1.getUserName());

//        System.out.println("从数据库里面取出用户数据----->"+user);

        Collection<BlogArticle> article = articleService.queryArticleByUserId(user.getUserId(),0);

        model.addAttribute("user",user);
        model.addAttribute("articles",article);
        //获取点赞情况
        List<Integer> praises = praiseService.selectArticleIdByUserId(user.getUserId());
        //将点赞情况放入
        model.addAttribute("praises",praises);

        return "userSpace";
    }

    //修改用户信息的页面,需要将用户的个人信息传进去
    @RequestMapping("/toUpdateInfo")
    public String toUpdateInfo(HttpSession session,Model model){

        User user = (User) session.getAttribute("user");

        System.out.println(user);
        model.addAttribute("user",user);
        return "userInfo";
    }

    //处理头像上传保存图片到本地
    //图片上传测试
    @ResponseBody
    @RequestMapping("upload")
    public Map upload(MultipartFile file, HttpServletRequest request){
        String prefix="";
        String dateStr="";
        //保存上传
        OutputStream out = null;
        InputStream fileInput=null;
        try{
            if(file!=null){
                String originalName = file.getOriginalFilename();
                prefix = originalName.substring(originalName.lastIndexOf(".")+1);
                Date date = new Date();
                String uuid = UUID.randomUUID()+"";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = simpleDateFormat.format(date);
                //这里是图片保存的地址，加上uuid,加上日期System.getProperty("user.dir");
                String filepath = "D:\\data\\javaweb\\springboot\\kcsj\\src\\main\\resources\\static\\images\\" + dateStr+"\\"+uuid+"." + prefix;
//                String filepath = System.getProperty("user.dir") + "/images/" + dateStr+"/"+uuid+"." + prefix;

                File files=new File(filepath);
                //打印查看上传路径
                System.out.println(filepath);
                if(!files.getParentFile().exists()){
                    files.getParentFile().mkdirs();
                }
                file.transferTo(files);
                Map<String,Object> map2=new HashMap<>();
                Map<String,Object> map=new HashMap<>();
                map.put("code",0);
                map.put("msg","");
                map.put("data",map2);
                map2.put("src","/images/"+ dateStr+"/"+uuid+"." + prefix);
                return map;
            }

        }catch (Exception e){
        }finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(fileInput!=null){
                    fileInput.close();
                }
            } catch (IOException e) {
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("code",1);
        map.put("msg","");
        return map;
    }

    //修改资料
    @RequestMapping("/toUpdateUser")
    public String toUpdateUser(User user,HttpSession session){
        //修改session
        session.removeAttribute("user");
        //修改数据库
        userService.updateUser(user);
        System.out.println("修改数据库用户资料---->"+user);
        session.setAttribute("user",user);

        return "redirect:/toUserSpace";
    }

    //获取用户写的文章的数量
    @RequestMapping("/getUserArticleCount")
    @ResponseBody
    public int getUserArticleCount(HttpSession session){
        User user = (User) session.getAttribute("user");
        return articleService.queryAllNumberByUserId(user.getUserId());
    }


    //用户页面文章分页
    @RequestMapping("/pageUserArticles")
    public String pageUserArticles(int sql_page, Model model, HttpSession session){
        int pageIndex = (sql_page-1)*5;
        User user = (User) session.getAttribute("user");
        List<BlogArticle> articleList = articleService.queryArticleByUserId(user.getUserId(),pageIndex);

//        System.out.println("---->"+articleList);

        model.addAttribute("articles", articleList);
        model.addAttribute("user",user);
        //回到博客分类页面，以及实现了分页
        return "userSpace::article_list";

    }

    //删除文章
    @RequestMapping("/delArticle")
    @ResponseBody
    public String delArticle(@Param("articleId") int articleId){
        int i = articleService.deleteArticle(articleId);

        if(i>0)
            return "true";
        else
            return "false";

    }

    //去往修改个人文章页面
    @RequestMapping("/toModifyArticle/{articleId}")
    public String toModifyArticle(@PathVariable("articleId")int articleId,
                                Model model){

        BlogArticle blogArticle = articleService.queryArticleById(articleId);

        BlogArticleContent content = contentService.queryContentByArticleId(articleId);

        model.addAttribute("blogArticle",blogArticle);
        model.addAttribute("content",content);
        //返回修改页面进行展示
        return "modifyArticle";
    }

    //修改文章
    @RequestMapping("/modifyArticle")
    public String modifyArticle(int articleId,
                                String articleTitle,
                                String articleType,
                                String articleAbstract,
                                String articleContext) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //修改文章
        articleService.updateArticle(articleId,articleContext,articleTitle,articleType,articleAbstract,
                sdf.parse(sdf.format(new Date())));

        return "redirect:/toUserSpace";
    }

}
