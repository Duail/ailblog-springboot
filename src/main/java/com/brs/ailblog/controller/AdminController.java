package com.brs.ailblog.controller;

import com.brs.ailblog.vo.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 后台管理控制器
 * @Author: DC
 * @Date: created in 14:42 2018/6/1
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    @GetMapping
    public ModelAndView listUsers(Model model) {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("用户管理", "/users"));//后期此数据进数据库
        list.add(new Menu("测试1", "/users"));
        list.add(new Menu("测试2", "/test"));
        model.addAttribute("list", list);
        return new ModelAndView("admins/index", "menuList", model);
    }
}
