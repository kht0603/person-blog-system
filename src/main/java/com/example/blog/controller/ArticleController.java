package com.example.blog.controller;

import com.example.blog.entity.Article;
import com.example.blog.entity.User;
import com.example.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    // 新增：显示文章列表页
    @GetMapping("/articles.html")
    public String showArticlesPage(HttpSession session, Model model) {
        // 1. 先判断用户是否登录
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login.html"; // 未登录→跳登录页
        }
        // 2. 已登录→查询用户的文章
        List<Article> articles = articleService.getByUserId(user.getId().longValue());
        model.addAttribute("articles", articles);
        return "articles";
    }

    // 显示发布文章页
    @GetMapping("/article/edit")
    public String showEditPage(@RequestParam(required = false) Long id, Model model) {
        // 编辑时回显文章内容
        if (id != null) {
            Article article = articleService.getById(id);
            model.addAttribute("article", article);
        }
        return "article-edit";
    }

    // 保存文章（发布/编辑）
    @PostMapping("/article/save")
    public String saveArticle(Article article, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        article.setAuthorId(user.getId().intValue()); // 匹配你的authorId字段（Integer类型）
        article.setViewCount(0); // 初始阅读量0

        if (article.getId() == null) {
            // 新增文章
            article.setCreateTime(LocalDateTime.now());
            articleService.save(article);
        } else {
            // 编辑文章
            article.setUpdateTime(LocalDateTime.now());
            articleService.updateById(article);
        }
        return "redirect:/articles.html";
    }

    // 显示文章详情页
    @GetMapping("/article/{id}")
    public String showArticleDetail(@PathVariable Long id, Model model) {
        Article article = articleService.getById(id);
        model.addAttribute("article", article);
        return "article-detail";
    }

    // 删除文章
    @GetMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        articleService.removeById(id);
        return "redirect:/articles.html";
    }
}