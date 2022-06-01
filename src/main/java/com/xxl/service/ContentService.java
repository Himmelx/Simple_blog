package com.xxl.service;

import com.xxl.pojo.BlogArticleContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxl
 * @create 2021-12-01 22:11
 */
@Service
public interface ContentService {

    //获取所有博客
    List<BlogArticleContent> getContentList();
    //根据博客文章id查找文章
    BlogArticleContent queryContentByArticleId(@Param("articleId") int id);

    //根据内容id查找博客
    BlogArticleContent queryContentByContentId(@Param("contentId") int id);

    //添加文章内容
    int addBlogContent(@Param("articleId")int id,@Param("articleContent")String context);
    //根据内容的id来实现修改博客
    int updateBlogContent(@Param("contentId") int id,@Param("articleContent")String context);
    //删除博客
    int deleteBlogContent(@Param("contentId")int id);
}
