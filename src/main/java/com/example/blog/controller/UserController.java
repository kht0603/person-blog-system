package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    // 默认访问根路径时，跳转到登录页
    @GetMapping("/")
    public String index() {
        return "redirect:/login.html";
    }

    // 显示登录页面（路径：/login.html）
    @GetMapping("/login.html")
    public String showLoginPage() {
        return "login";
    }

    // 显示注册页面（路径：/register.html）
    @GetMapping("/register.html")
    public String showRegisterPage() {
        return "register";
    }

    // 注册、登录接口后续补全...

    // 注入接口（不是实现类）
    @Autowired
    private UserService userService;

    // 注册接口调用service的save方法
    @PostMapping("/register")
    public String doRegister(User user, RedirectAttributes redirectAttributes) {
        try {
            userService.save(user); // 调用接口的save方法
            redirectAttributes.addFlashAttribute("msg", "注册成功！请登录");
            return "redirect:/login.html";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "注册失败：" + e.getMessage());
            return "redirect:/register.html";
        }
    }

    // 处理登录请求
    @PostMapping("/login")
    public String doLogin(String username, String password, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.login(username, password);
            if (user == null) {
                throw new RuntimeException("用户名或密码错误！");
            }
            // （后续可以加Session保存登录状态）
            return "redirect:/articles.html"; // 登录成功跳文章列表页
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "登录失败：" + e.getMessage());
            return "redirect:/login.html";
        }
    }
}