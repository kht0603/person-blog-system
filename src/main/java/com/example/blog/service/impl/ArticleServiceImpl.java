package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Article;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

// ArticleServiceImpl实现类
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public List<Article> getByUserId(Long userId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id", userId.intValue()); // 匹配你的authorId（Integer）
        return baseMapper.selectList(wrapper);
    }
}