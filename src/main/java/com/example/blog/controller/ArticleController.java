package com.example.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {
    // 显示文章列表页
    @GetMapping("/articles.html")
    public String showArticlesPage() {
        return "articles"; // 对应templates/articles.html
    }
}