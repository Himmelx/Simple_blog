package com.xxl.controller;

import com.xxl.pojo.Admin;
import com.xxl.pojo.BlogArticle;
import com.xxl.pojo.User;
import com.xxl.service.AdminServiceImpl;
import com.xxl.service.ArticleServiceImpl;
import com.xxl.service.PraiseServiceImpl;
import com.xxl.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private PraiseServiceImpl praiseService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "index";
    }

    //用户登录，输入账号进入这个页面
    @RequestMapping("/main")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){

        User user = userService.queryUserByName(username);
        Admin admin = adminService.queryAdminByName(username);

        //这里判断为空必须使用isEmpty
        if(username.isEmpty() || password.isEmpty()){
            model.addAttribute("msg","用户名或者密码不能为空");
            return "index";
        }
        //从这里判断用户的用户名和密码是否正确
        else if(user!=null&&user.getUserPassword().equals(password)){
//            Collection<BlogArticle> article = articleService.queryArticleByUserId(user.getUserId(),0);
            //登录成功，还需要判断是否为管理员登录
            session.setAttribute("LoginUser",username);   //向session设置属性代表登录

//            //获取点赞情况
//            List<Integer> praises = praiseService.selectArticleIdByUserId(user.getUserId());
//            //将点赞情况放入
//            model.addAttribute("praises",praises);

            //普通用户,将个人信息装入
            model.addAttribute("user",user);
//            model.addAttribute("articles",article);


            //博客轮播图
            List<BlogArticle> carousel = articleService.queryArticleByUserId(6,0);
            //博客精选
            List<BlogArticle> recommend = articleService.queryArticleByUserId(7, 0);

            //博客达人
            List<User> goodMan = userService.getUserListByPage(0, 4);

            //热度排行
            List<BlogArticle> articles = articleService.queryArticleHot();

            model.addAttribute("carousels",carousel);
            model.addAttribute("recommends",recommend);
            model.addAttribute("goodMans",goodMan);
            model.addAttribute("articles",articles);


            session.setAttribute("user",user);
//            return "userSpace";
            return "home";
        }else if(admin!=null && admin.getAdminPassword().equals(password)){
            //登录成功，还需要判断是否为管理员登录
            session.setAttribute("LoginUser",username);   //向session设置属性代表登录

            //管理员，进入后台页面
            model.addAttribute("admin",admin);
            session.setAttribute("admin",admin);
            return "manager";
        }
        else{
            //登录失败
            model.addAttribute("msg","用户名或者密码输入错误");
            return "index";
        }
    }

    //注册页面
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/GenerateCode")
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //数据缓冲区的图像类
        BufferedImage image = new BufferedImage(100,30,BufferedImage.TYPE_INT_RGB);

        //Graphics对象（Graphics2D）：获取图形对象Graphics后，可调用相关的方法来绘制各种图形
        Graphics graphics = image.getGraphics();

        graphics.setColor(new Color(220,220,220)); //
        graphics.fillRect(0, 0, 100, 30); //

        //stringBuffer
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();

        System.out.println("进入了这个页面中间");
        for (int i = 0; i < 4; i++) {
            //获取随机数
            int i1 = random.nextInt(26);
            String temp = "";
            temp = String.valueOf(((char) (i1 + (int) 'A')));

            buffer.append(temp);
            //设置颜色
            graphics.setColor(new Color(random.nextInt(150), random.nextInt(200), random.nextInt(255)));
            //设置字体大小
            graphics.setFont(new Font("", Font.PLAIN, 20));
            //将生成的buffer加进去
            graphics.drawString(temp, 10+18*i, 23);
        }

        //随机产生 150 个干扰点，使图象中的验证码不易被其他程序探测到
        for (int j = 0; j < 150; j++){
            int x = random.nextInt(100);
            int y = random.nextInt(30);
            graphics.drawOval(x, y, 2, 1);
        }
        //System.out.println("干扰生成完成");

        //将生成的buffer放到session里面
        req.getSession().setAttribute("code", buffer);
        //System.out.println("session生成完成");

        //  输出图象到页面
        ImageIO.write(image, "JPG", resp.getOutputStream());

    }

    @RequestMapping("/Register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("code") String code,
                           Model model,HttpSession session){
        User user1 = userService.queryUserByName(username);
        StringBuffer code1 = (StringBuffer)session.getAttribute("code");

        if(username.isEmpty() || password.isEmpty()){
            model.addAttribute("error","用户名或者密码不能为空");
            return "register";
        }
        else if(user1!=null){
            model.addAttribute("error","用户名已存在");
            return "register";
        }else if(!code1.toString().equals(code)){
            model.addAttribute("error","验证码错误");
            return "register";
        }
        else {
            User user = new User();
            user.setUserName(username);
            user.setUserPassword(password);
            userService.addUser(user);
            model.addAttribute("success","注册成功");
            return "index";
        }

    }

    //注销页面
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }
}
