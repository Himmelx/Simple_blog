package com.xxl.service;

import com.xxl.mapper.PraiseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxl
 * @create 2021-12-09 11:13
 */
@Service
public class PraiseServiceImpl implements PraiseService{

    @Autowired
    private PraiseMapper praiseMapper;

    @Override
    public int addPraise(int userId, int articleId) {
        return praiseMapper.addPraise(userId, articleId);
    }

    @Override
    public int deletePraise(int userId, int articleId) {
        return praiseMapper.deletePraise(userId, articleId);
    }

    @Override
    public List<Integer> selectArticleIdByUserId(int userId) {
        return praiseMapper.selectArticleIdByUserId(userId);
    }
}
