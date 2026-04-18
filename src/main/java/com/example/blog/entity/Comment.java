package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {
    private Integer id;
    private Integer articleId;
    private String content;
    private Integer userId;
    private LocalDateTime createTime;
}