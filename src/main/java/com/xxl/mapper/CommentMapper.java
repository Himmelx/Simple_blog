package com.xxl.mapper;

import com.xxl.pojo.BlogComments;
import com.xxl.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xxl
 * @create 2021-12-02 19:45
 */
@Mapper
@Repository
public interface CommentMapper {

    //根据评论id查找评论
    BlogComments queryCommentByCommentId(@Param("commentId") int id);

    //根据博客id可以获取关于篇博客的所有评论（多条）
    List<BlogComments> queryCommentByArticleId(@Param("articleId") int id);

    //根据文章id和用户id查找评论，可以获得评论,可能一个人评论多次
    List<BlogComments> queryCommentByArticleIdAndUserId(@Param("articleId") int aid,@Param("userId") int uid);

    //添加评论,只需要用户名和密码即可
    int addComment(BlogComments blogComments);

    //删除评论
    int deleteComment(@Param("commentId")int id);

    //------------------连表查询-----------------------
    //根据角色id查询图片
    String queryImgAddress(@Param("userId")int userId);
    //根据角色id查询角色姓名
    String queryUserName(@Param("userId")int userId);

}
