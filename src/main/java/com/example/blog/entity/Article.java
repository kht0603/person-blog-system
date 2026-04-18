package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("article")
public class Article {
    private Integer id;
    private String title;
    private String content;
    private Integer authorId;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}