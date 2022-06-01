package com.xxl.service;

import com.xxl.mapper.CommentMapper;
import com.xxl.pojo.BlogComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxl
 * @create 2021-12-02 20:08
 */
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public BlogComments queryCommentByCommentId(int id) {
        return commentMapper.queryCommentByCommentId(id);
    }

    @Override
    public List<BlogComments> queryCommentByArticleId(int id) {
        return commentMapper.queryCommentByArticleId(id);
    }

    @Override
    public List<BlogComments> queryCommentByArticleIdAndUserId(int aid, int uid) {
        return commentMapper.queryCommentByArticleIdAndUserId(aid,uid);
    }

    @Override
    public int addComment(BlogComments blogComments) {
        return commentMapper.addComment(blogComments);
    }

    @Override
    public int deleteComment(int id) {
        return commentMapper.deleteComment(id);
    }

    @Override
    public String queryImgAddress(int userId) {
        return commentMapper.queryImgAddress(userId);
    }

    @Override
    public String queryUserName(int userId) {
        return commentMapper.queryUserName(userId);
    }
}
