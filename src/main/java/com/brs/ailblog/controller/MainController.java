package com.brs.ailblog.controller;

import com.brs.ailblog.domain.Authority;
import com.brs.ailblog.domain.User;
import com.brs.ailblog.service.AuthorityService;
import com.brs.ailblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 主页控制器
 * @Author: DC
 * @Date: created in 9:51 2018/6/1
 */
@Controller
public class MainController {

    private static final long ROLR_USER_AUTHORITY_ID = 2L;

    private final UserService userService;
    private final AuthorityService authorityService;

    @Autowired
    public MainController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/blogs";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLR_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);

        userService.registerUser(user);
        return "redirect:/login";
    }

}
