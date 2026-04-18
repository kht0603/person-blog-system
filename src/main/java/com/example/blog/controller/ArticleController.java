package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.entity.Article;
import com.example.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public String articles(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        Page<Article> page = articleService.getPage(pageNum);
        model.addAttribute("page", page);
        return "articles";
    }

    @GetMapping("/article/{id}")
    public String articleDetail(@PathVariable Integer id, Model model) {
        Article article = articleService.getById(id);
        model.addAttribute("article", article);
        return "article - detail";
    }

    @GetMapping("/publish")
    public String publishForm(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login.html";
        }
        return "publish";
    }

    @PostMapping("/publish")
    public String publishArticle(Article article, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login.html";
        }
        article.setAuthorId(user.getId());
        articleService.publish(article);
        return "redirect:/articles";
    }
}