package com.xxl.service;

import com.xxl.mapper.ContentMapper;
import com.xxl.pojo.BlogArticleContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xxl
 * @create 2021-12-01 22:12
 */

@Transactional()    //开启事务
@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public List<BlogArticleContent> getContentList() {
        return contentMapper.getContentList();
    }

    @Override
    public BlogArticleContent queryContentByArticleId(int id) {
        return contentMapper.queryContentByArticleId(id);
    }

    @Override
    public BlogArticleContent queryContentByContentId(int id) {
        return contentMapper.queryContentByContentId(id);
    }

    @Override
    public int addBlogContent(int id, String context) {
        return contentMapper.addBlogContent(id,context);
    }

    @Override
    public int updateBlogContent(int id, String context) {
        return contentMapper.updateBlogContent(id,context);
    }

    @Override
    public int deleteBlogContent(int id) {
        return contentMapper.deleteBlogContent(id);
    }
}
