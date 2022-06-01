package com.xxl.service;

import com.xxl.mapper.ArticleMapper;
import com.xxl.pojo.BlogArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author xxl
 * @create 2021-12-01 0:35
 */

@Transactional()
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public List<BlogArticle> getArticleList() {
        return articleMapper.getArticleList();
    }

    @Override
    public BlogArticle queryArticleById(int id) {
        return articleMapper.queryArticleById(id);
    }

    @Override
    public List<BlogArticle> queryArticleByUserId(int id,int page) {
        return articleMapper.queryArticleByUserId(id,page);
    }

    @Override
    public List<BlogArticle> queryArticleByPageAndLimit(int page, int limit) {
        return articleMapper.queryArticleByPageAndLimit(page,limit);
    }

    @Override
    public BlogArticle queryArticleByTitle(String name) {
        return articleMapper.queryArticleByTitle(name);
    }

    @Override
    public List<BlogArticle> queryArticleLikeTitle(String name,int page) {
        return articleMapper.queryArticleLikeTitle(name,page);
    }

    @Override
    public List<BlogArticle> queryArticleByType(String type) {
        return articleMapper.queryArticleByType(type);
    }

    @Override
    public int addArticle(BlogArticle article) {
        return articleMapper.addArticle(article);
    }

    @Override
    public int updateArticleByLove(int love, int articleId) {
        return articleMapper.updateArticleByLove(love,articleId);
    }

    @Override
    public int updateArticleByView(int view, int articleId) {
        return articleMapper.updateArticleByView(view,articleId);
    }

    @Override
    public int deleteArticle(int id) {
        return articleMapper.deleteArticle(id);
    }

    @Override
    public List<BlogArticle> queryArticleByPage(int page) {
        return articleMapper.queryArticleByPage(page);
    }

    @Override
    public int queryAllNumber() {
        return articleMapper.queryAllNumber();
    }

    @Override
    public int queryAllNumberByUserId(int id) {
        return articleMapper.queryAllNumberByUserId(id);
    }

    @Override
    public int queryAllNumberByTitle(String title) {
        return articleMapper.queryAllNumberByTitle(title);
    }

    @Override
    public int increaseView(int id) {
        return articleMapper.increaseView(id);
    }

    @Override
    public int increaseLove(int id) {
        return articleMapper.increaseLove(id);
    }

    @Override
    public int decreaseLove(int id) {
        return articleMapper.decreaseLove(id);
    }

    @Override
    public List<BlogArticle> queryArticleHot() {
        return articleMapper.queryArticleHot();
    }

    @Override
    public int updateArticle(int articleId,String content, String title, String type, String ArticleAbstract, Date date) {
        return articleMapper.updateArticle(articleId,content, title, type, ArticleAbstract, date);
    }
}
