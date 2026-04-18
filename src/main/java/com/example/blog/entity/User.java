package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private LocalDateTime createTime;
    private String avatar;
    private String intro;
}