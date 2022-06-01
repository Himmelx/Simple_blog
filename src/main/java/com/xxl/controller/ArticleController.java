package com.xxl.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxl.pojo.BlogArticle;
import com.xxl.pojo.BlogArticleContent;
import com.xxl.pojo.BlogComments;
import com.xxl.pojo.User;
import com.xxl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xxl
 * @create 2021-12-01 18:10
 */

@Service
@RequestMapping("/Article")
public class ArticleController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private ContentServiceImpl contentService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private PraiseServiceImpl praiseService;

    //editor.md编辑器页面
    @RequestMapping("/toWriteBlog")
    public String toWriteBlog(){
        return "articleWrite";
    }

    //博客添加图片
    @RequestMapping("/file/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
//上传路径保存设置

        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = "D:\\data\\javaweb\\springboot\\kcsj\\src\\main\\resources\\static\\upload\\";
//        String path = System.getProperty("user.dir") + "/upload/";

        //按照月份进行分类：
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1)+"month";
        path = path+month;

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdirs();
        }

        //上传文件地址
        System.out.println("上传文件保存地址："+realPath);

        //解决文件名字问题：我们使用uuid;
        String filename = "ks-"+ UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new File(realPath +"/"+ filename));

        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url","/upload/"+month+"/"+ filename);
        res.put("success", 1);
        res.put("message", "upload success!");

        return res;
    }

    //添加博客
    @RequestMapping("/addBlog")
    public String addBlog(String articleTitle,
                          String articleType,
                          String articleAbstract,
                          String articleContext, HttpSession session) throws ParseException {
        User user = (User) session.getAttribute("user");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //blogArticle里面加上内容
        BlogArticle blogArticle = new BlogArticle();
        blogArticle.setUserId(user.getUserId());
        blogArticle.setArticleTitle(articleTitle);
        blogArticle.setArticleType(articleType);
        blogArticle.setArticleAbstract(articleAbstract);
        blogArticle.setWriteDate(sdf.parse(sdf.format(new Date())));
        blogArticle.setUserName(user.getUserName());
        //添加文章
        articleService.addArticle(blogArticle);

        //前面插入数据，创建一个新的id,这里再次获取对象，找出id
        BlogArticle blog = articleService.queryArticleByTitle(articleTitle);

        //添加内容
        contentService.addBlogContent(blog.getArticleId(),articleContext);

        return "redirect:/toUserSpace";
    }

    //点击博客，进入博客详细内容页面
    @RequestMapping("/contentDetail/{articleId}")
    public String toContentDetail(@PathVariable("articleId")int id,Model model){
        //根据文章id查出内容
        BlogArticleContent content = contentService.queryContentByArticleId(id);
        //根据文章id查到文章
        BlogArticle article = articleService.queryArticleById(id);
        //根据文章id查到评论
        List<BlogComments> blogComments = commentService.queryCommentByArticleId(id);

        model.addAttribute("content",content.getArticleContent());
        model.addAttribute("article",article);
        model.addAttribute("blogComments",blogComments);

        return "contentDetail";   //进入文章展示页面
    }

    //站内博客
    @RequestMapping("/allArticles")
    public String getAllArticles(Model model,HttpSession session){
        //List<BlogArticle> articleList = articleService.getArticleList();
        //第一次进入，最多渲染5条数据
        List<BlogArticle> articleList = articleService.queryArticleByPage(0);

        User user = (User) session.getAttribute("user");

        //获取点赞情况
        List<Integer> praises = praiseService.selectArticleIdByUserId(user.getUserId());
        //将点赞情况放入
        model.addAttribute("praises",praises);

        model.addAttribute("articleList",articleList);
        model.addAttribute("user",user);
        return "allArticles";
    }

    //去往博客分类html
    @RequestMapping("/classifyArticles")
    public String classify(Model model,HttpSession session){

//        System.out.println("进入了分类的controller");

//        List<BlogArticle> articleList = articleService.getArticleList();
        //第一次进入，最多渲染5条数据
        List<BlogArticle> articleList = articleService.queryArticleByPage(0);
        User user = (User) session.getAttribute("user");

        //获取点赞情况
        List<Integer> praises = praiseService.selectArticleIdByUserId(user.getUserId());
        //将点赞情况放入
        model.addAttribute("praises",praises);

        model.addAttribute("articleList",articleList);
        model.addAttribute("user",user);
        return "classification";

    }

    //博客分类点击下拉框
    @RequestMapping("/toClassifyArticles")
    public String classify(String option,Model model,HttpSession session){

        List<BlogArticle> articleList;
        //所有这个类型查出来,如果是全部类型就全部显示
        if(option.equals("全部类型")){
//            articleList = articleService.getArticleList();
            articleList = articleService.queryArticleByPage(0);
        }else
            articleList = articleService.queryArticleByType(option);
        User user = (User) session.getAttribute("user");

        //获取点赞情况
        List<Integer> praises = praiseService.selectArticleIdByUserId(user.getUserId());
        //将点赞情况放入
        model.addAttribute("praises",praises);

        model.addAttribute("articleList",articleList);
        model.addAttribute("user",user);
        //noinspection SpringMVCViewInspection
        return "classification::article_list";
    }

    //添加评论
    @RequestMapping("addComment/{articleId}")
    public String addComment(@PathVariable("articleId")int articleId,
                             String content,Model model,HttpSession session) throws ParseException {
        User user = (User) session.getAttribute("user");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BlogComments blogComments = new BlogComments();
        blogComments.setArticleId(articleId);
        blogComments.setCommentContent(content);
        blogComments.setUserId(user.getUserId());
        blogComments.setCommentDate(sdf.parse(sdf.format(new Date())));
        blogComments.setUserJpg(user.getUserJpg());
        blogComments.setUserName(user.getUserName());
        //添加评论
        commentService.addComment(blogComments);
        //取出articleId的评论，这一个文章的评论
        List<BlogComments> Comments = commentService.queryCommentByArticleId(articleId);

        model.addAttribute("blogComments",Comments);
        return "contentDetail::commentList";
    }

    //获取总的文章的数量
    @RequestMapping("/getCount")
    @ResponseBody
    public int getCount(){
        //使用Ajax先返回一个总的数据到laypage
        return articleService.queryAllNumber();
    }

    //所有文章分页
    @RequestMapping("/pageAllArticles")
    public String pageAllArticle(int sql_page, Model model, HttpSession session){
        int pageIndex = (sql_page-1)*5;
        User user = (User) session.getAttribute("user");
        List<BlogArticle> articleList = articleService.queryArticleByPage(pageIndex);

        model.addAttribute("articleList", articleList);
        model.addAttribute("user",user);
        //获取点赞情况
        List<Integer> praises = praiseService.selectArticleIdByUserId(user.getUserId());
        //将点赞情况放入
        model.addAttribute("praises",praises);
        //回到博客分类页面，以及实现了分页
        return "allArticles::article_list";
    }

    //搜索博客
    @RequestMapping("/searchBlog")
    public String searchBlog(@RequestParam("Title") String Title,Model model,HttpSession session){

        //模糊查找，所有包含这个关键字的文章前5个
        List<BlogArticle> articleList = articleService.queryArticleLikeTitle(Title,0);
        User user = (User) session.getAttribute("user");

        //获取点赞情况
        List<Integer> praises = praiseService.selectArticleIdByUserId(user.getUserId());
        //将点赞情况放入
        model.addAttribute("praises",praises);

        model.addAttribute("articleList",articleList);
        model.addAttribute("user",user);
        model.addAttribute("Title",Title);


        //这里返回到allArticles的fragment以让这个model能够返回
        return "searchBlog";
    }

    //搜索到的内容的总量
    @RequestMapping("/searchCount")
    @ResponseBody
    public int searchCount(String Title){
        return articleService.queryAllNumberByTitle("Title");
    }

    //搜索分页
    @RequestMapping("/pageSearch")
    public String pageSearch(String Title,int sql_page, Model model, HttpSession session){
        int pageIndex = (sql_page-1)*5;
        User user = (User) session.getAttribute("user");
        List<BlogArticle> articleList = articleService.queryArticleLikeTitle(Title,pageIndex);

        model.addAttribute("articleList", articleList);
        model.addAttribute("user",user);
        //回到博客分类页面，以及实现了分页
        return "allArticles::article_list";
    }

    //文章点赞功能
    @RequestMapping("/praise")
    @ResponseBody
    public int praise(String type,int articleId,int userId){
//        System.out.println("激怒了"+type+"--"+articleId+"--"+userId);
        int res = 0;
        if(type.equals("increase")){
            //增加点赞记录以及文章点赞数
            res = praiseService.addPraise(userId, articleId);
            articleService.increaseLove(articleId);
        }else {
            //减少点赞记录以及文章点赞数
            res = praiseService.deletePraise(userId, articleId);
            articleService.decreaseLove(articleId);
        }
        return res;
    }

    //文章浏览次数加一
    @RequestMapping("/view")
    @ResponseBody
    public int view(int articleId){
        System.out.println("进入了哈哈哈");
        int i = articleService.increaseView(articleId);
        System.out.println("添加完成哈哈哈");
        if(i>0)
            return 1;
        else
            return 0;
    }
}
