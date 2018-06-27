package com.brs.ailblog.controller;

import com.brs.ailblog.domain.User;
import com.brs.ailblog.service.BlogService;
import com.brs.ailblog.service.VoteService;
import com.brs.ailblog.util.ConstraintViolationExceptionHandler;
import com.brs.ailblog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolationException;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 10:20 2018/6/26
 */
@Controller
@RequestMapping("/votes")
public class VoteController {

    private final BlogService blogService;
    private final VoteService voteService;

    @Autowired
    public VoteController(BlogService blogService, VoteService voteService) {
        this.blogService = blogService;
        this.voteService = voteService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> createVote(Long blogId) {

        try {
            blogService.createVote(blogId);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "操作成功", null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> removeVote(@PathVariable("id") Long id, Long blogId) {
        boolean isOwner = false;
        User user = blogService.getBlogById(blogId).getUser();

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
            blogService.removeVote(blogId, id);
            voteService.removeVote(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "操作成功", null));
    }
}
