package com.xxl.mapper;

import com.xxl.pojo.BlogArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xxl
 * @create 2021-12-09 11:07
 */
@Mapper
@Repository
public interface PraiseMapper {

    //添加点赞，文章点赞同步更新
    int addPraise(@Param("userId")int userId,@Param("articleId")int articleId);

    //取消点赞，文章点赞同步减少
    int deletePraise(@Param("userId")int userId,@Param("articleId")int articleId);

    //根据角色id获取点赞过的文章的文章Id列表
    List<Integer> selectArticleIdByUserId(@Param("userId") int userId);
}
