package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Article;

import java.util.List;

// ArticleService接口
public interface ArticleService extends IService<Article> {
    List<Article> getByUserId(Long userId);
}