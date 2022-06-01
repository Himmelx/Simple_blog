package com.xxl.mapper;

import com.xxl.pojo.BlogArticleContent;
import com.xxl.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xxl
 * @create 2021-12-01 21:50
 */

@Mapper
@Repository
public interface ContentMapper {

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
