package com.brs.ailblog.controller;
import javax.xml.parsers.DocumentBuilder;
import com.brs.ailblog.domain.Blog;
import com.brs.ailblog.domain.User;
import com.brs.ailblog.service.BlogService;
import com.brs.ailblog.service.UserService;
import com.brs.ailblog.util.ConstraintViolationExceptionHandler;
import com.brs.ailblog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 11:21 2018/6/1
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

    @Value("${file.server.url}")
    private String fileServerUrl;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final BlogService blogService;

    @Autowired
    public UserspaceController(UserDetailsService userDetailsService, UserService userService, BlogService blogService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.blogService = blogService;
    }

    @GetMapping("/{username}")
    public String userspace(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/u/"+username+"/blogs";
    }

    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "category", required = false) Long category,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "async", required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   Model model) {

        User user = (User) userDetailsService.loadUserByUsername(username);
        Page<Blog> page = null;

        if (category != null) {
            //todo
        } else if (order.equals("hot")) {
            Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize");
            Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
            page = blogService.listBlogsByTitleVoteAndSort(user, keyword, pageable);
        } else if (order.equals("new")) {
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            page = blogService.listBlogsByTitleVote(user, keyword, pageable);
        }

        List<Blog> list = page.getContent();//todo

        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        if (async) {
            return "userspace/u :: #mainContainerReplace";
        }
        return "userspace/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        User principal = null;
        Blog blog = blogService.getBlogById(id);
        //阅读量+1
        blogService.readingIncrease(id);
        //判断当前用户是否是作者
        boolean isOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && username.equals(principal.getUsername())) {
                isOwner = true;
            }
        }

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("blog", blog);
        return "userspace/blog";
    }

    /**
     * 新建博客
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/edit")
    public String createBlog(@PathVariable("username") String username, Model model) {
        model.addAttribute("blog", new Blog(null, null, null));
        model.addAttribute("fileServerUrl", fileServerUrl);
        return "userspace/blogedit";
    }

    /**
     * 修改博客
     * @param username
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    public String editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        model.addAttribute("blog", blogService.getBlogById(id));
        model.addAttribute("fileServerUrl", fileServerUrl);
        return "userspace/blogedit";
    }

    /**
     * 保存博客
     * @param username
     * @param blog
     * @return
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        try {
            if (blog.getId() != null) {
                Blog orignalBlog = blogService.getBlogById(blog.getId());
                orignalBlog.setTitle(blog.getTitle());
                orignalBlog.setSummary(blog.getSummary());
                orignalBlog.setContent(blog.getContent());
                blogService.saveBlog(orignalBlog);
            } else {
                User user = (User) userDetailsService.loadUserByUsername(username);
                blog.setUser(user);
                blogService.saveBlog(blog);
            }
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/"+username+"/blogs/"+blog.getId();
        return ResponseEntity.ok().body(new Response(true, "操作成功", redirectUrl));
    }

    /**
     * 删除博客
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        String redirectUrl = "/u/"+username+"/blogs";
        return ResponseEntity.ok().body(new Response(true, "操作成功", redirectUrl));
    }

    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")//只能修改自己的信息
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("fileServerUrl", fileServerUrl);
        return new ModelAndView("userspace/profile", "userModel", model);
    }

    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        //todo 加密
        User originalUser = userService.getUserById(user.getId());
        originalUser.setName(user.getName());
        originalUser.setEmail(user.getEmail());
        originalUser.setPassword(user.getPassword());
        userService.saveOrUpdate(originalUser);
        return "redirect:/u/" + username + "/profile";
    }

    @GetMapping("/{username}/avatar")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("userspace/avatar", "userModel", model);
    }

    @PostMapping("/{username}/avatar")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();
        User originalUser = userService.getUserById(user.getId());
        originalUser.setAvatar(avatarUrl);
        userService.saveOrUpdate(originalUser);
        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }
}
