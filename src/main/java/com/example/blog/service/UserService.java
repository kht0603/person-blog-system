package com.example.blog.service;

import com.example.blog.entity.User;

public interface UserService {
    // 定义注册方法
    void save(User user);
    // 后续可以加登录方法
    User login(String username, String password);
    void updateById(User user); // 新增更新方法
}