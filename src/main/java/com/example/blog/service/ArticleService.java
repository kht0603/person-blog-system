package com.example.blog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blog.entity.Article;
import com.example.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Article getById(Integer id) {
        String key = "article:" + id;
        String json = redisTemplate.opsForValue().get(key);
        if (json != null) {
            return JSONObject.parseObject(json, Article.class);
        }
        Article article = articleMapper.selectById(id);
        if (article != null) {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(article), 1, TimeUnit.HOURS);
            articleMapper.update(null, new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<Article>()
                    .set("view_count", article.getViewCount() + 1)
                    .eq("id", id));
        }
        return article;
    }

    public void publish(Article article) {
        articleMapper.insert(article);
    }

    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<Article> getPage(int pageNum) {
        return articleMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, 10), null);
    }
}