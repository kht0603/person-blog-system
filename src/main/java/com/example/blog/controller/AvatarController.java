package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
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
        // 1. 获取当前登录用户
        User currentUser = (User) session.getAttribute("loginUser");
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login.html";
        }

        // 2. 更新用户信息
        currentUser.setNickname(nickname);
        currentUser.setIntro(intro);

        // 3. 处理头像上传（加入这段代码）
        if (avatar != null && !avatar.isEmpty()) {
            String avatarDir = "src/main/resources/static/avatar/";
            File dir = new File(avatarDir);
            if (!dir.exists()) dir.mkdirs(); // 确保目录存在

            // --- 加入图像解析的代码 ---
            try {
                // 读取上传的头像文件，验证是否为有效图片
                BufferedImage image = ImageIO.read(avatar.getInputStream());
                if (image == null) {
                    redirectAttributes.addFlashAttribute("msg", "请上传有效的图片文件！");
                    return "redirect:/profile.html";
                }
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("msg", "头像上传失败：" + e.getMessage());
                return "redirect:/profile.html";
            }
            // ---------------------------

            // 保存头像文件到服务器
            String fileName = "user_" + currentUser.getId() + ".png";
            avatar.transferTo(new File(avatarDir + fileName));
            // 设置用户头像路径（前端通过这个路径访问头像）
            currentUser.setAvatar("/static/avatar/" + fileName);
        }

        // 4. 保存到数据库
        userService.updateById(currentUser);
        session.setAttribute("loginUser", currentUser); // 更新Session中的用户信息
        redirectAttributes.addFlashAttribute("msg", "个人信息修改成功！");
        return "redirect:/profile.html";
    }
}