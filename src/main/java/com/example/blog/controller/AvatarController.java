package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;


@Controller
public class AvatarController {
    // 头像上传路径（需在static下创建avatar文件夹）
    private static final String AVATAR_PATH = "src/main/resources/static/avatar/";

    @Autowired // 注入UserService
    private UserService userService;

    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam("nickname") String nickname,
            @RequestParam("intro") String intro,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            RedirectAttributes redirectAttributes,
            HttpSession session) throws IOException {
        // 1. 从Session中获取当前登录用户（需要先在登录时把User存入Session）
        User currentUser = (User) session.getAttribute("loginUser");
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login.html";
        }

        // 2. 更新用户信息
        currentUser.setNickname(nickname);
        currentUser.setIntro(intro);

        // 3. 处理头像上传（可选）
        if (avatar != null && !avatar.isEmpty()) {
            String avatarDir = "src/main/resources/static/avatar/";
            File dir = new File(avatarDir);
            if (!dir.exists()) dir.mkdirs(); // 确保目录存在

            String fileName = "user_" + currentUser.getId() + ".png";
            avatar.transferTo(new File(avatarDir + fileName));
            currentUser.setAvatar("/static/avatar/" + fileName);
        }

        // 4. 保存到数据库
        userService.updateById(currentUser);
        session.setAttribute("loginUser", currentUser); // 更新Session中的用户信息
        redirectAttributes.addFlashAttribute("msg", "个人信息修改成功！");
        return "redirect:/profile.html";
    }
}