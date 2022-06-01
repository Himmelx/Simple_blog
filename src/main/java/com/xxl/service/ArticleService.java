package com.xxl.service;

import com.xxl.pojo.BlogArticle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author xxl
 * @create 2021-12-01 0:34
 */

@Service
public interface ArticleService {

    //获取所有文章
    List<BlogArticle> getArticleList();

    //根据id查找文章
    BlogArticle queryArticleById(@Param("articleId") int id);
    //根据用户id查找文章
    List<BlogArticle> queryArticleByUserId(@Param("userId") int id,int page);

    //分页查询文章,返回limit条数据
    List<BlogArticle> queryArticleByPageAndLimit(int page,int limit);

    //根据标题查找文章,精确查找
    BlogArticle queryArticleByTitle(@Param("articleTitle") String name);

    //根据类型查找文章
    List<BlogArticle> queryArticleByType(@Param("articleType") String type);

    //根据标题查找文章
    List<BlogArticle> queryArticleLikeTitle(@Param("articleTitle") String name,int page);
    //写文章
    int addArticle(BlogArticle article);
    //修改文章,根据点赞,可以加上注解param，在mybatis里面就不需要写parameterType了
    int updateArticleByLove(@Param("love") int love,@Param("articleId") int articleId);
    //修改文章，根据浏览
    int updateArticleByView(@Param("view")int view,@Param("articleId") int articleId);

    //删除文章
    int deleteArticle(@Param("articleId")int id);

    //分页查询文章,返回5条数据
    List<BlogArticle> queryArticleByPage(int page);

    //获取一共多少条数据
    int queryAllNumber();

    //根据用户id获取用户写了多少文章
    int queryAllNumberByUserId(@Param("userId")int id);

    //根据搜索的名字取出多少条数据
    int queryAllNumberByTitle(@Param("articleTitle") String title);

    //浏览数加一
    int increaseView(@Param("articleId") int id);

    //点赞数加一
    int increaseLove(@Param("articleId") int id);
    //点赞数减一
    int decreaseLove(@Param("articleId") int id);

    //热度前三
    List<BlogArticle> queryArticleHot();

    //修改文章页面修改文章
    int updateArticle(@Param("articleId") int articleId,
            @Param("articleContent")String content, @Param("articleTitle")String title,
                      @Param("articleType")String type, @Param("articleAbstract")String ArticleAbstract,
                      @Param("writeDate") Date date);
}
