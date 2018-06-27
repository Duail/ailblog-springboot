package com.brs.ailblog.controller;

import com.brs.ailblog.domain.Blog;
import com.brs.ailblog.domain.Comment;
import com.brs.ailblog.domain.User;
import com.brs.ailblog.service.BlogService;
import com.brs.ailblog.service.CommentService;
import com.brs.ailblog.util.ConstraintViolationExceptionHandler;
import com.brs.ailblog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 0:16 2018/6/25
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

    private final BlogService blogService;
    private final CommentService commentService;

    @Autowired
    public CommentController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @GetMapping
    public String listComments(Long blogId, Model model) {
        Blog blog = blogService.getBlogById(blogId);
        List<Comment> comments = blog.getComments();

        //把当前用户名传给前台，和每条评论的所有者比较
        String currentUser = "";
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                currentUser = principal.getUsername();
            }
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("commentList", comments);
        return "userspace/blog :: #mainContainer";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> addComment(Long blogId, String commentContent) {
        try {
            blogService.createComment(blogId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(true, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "操作成功", null));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteComment(@PathVariable("id") Long id, Long blogId) {
            User user = commentService.getCommentById(id).getUser();
        boolean isOwner = false;

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }

        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        try {
            blogService.removeComment(blogId, id);
            commentService.removeComment(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "操作成功", null));

    }
}
