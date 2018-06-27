package com.brs.ailblog.controller;

import com.brs.ailblog.domain.Catalog;
import com.brs.ailblog.domain.User;
import com.brs.ailblog.service.CatalogService;
import com.brs.ailblog.util.ConstraintViolationExceptionHandler;
import com.brs.ailblog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 15:21 2018/6/26
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {

    private final CatalogService catalogService;

    private final UserDetailsService userDetailsService;

    @Autowired
    public CatalogController(CatalogService catalogService, UserDetailsService userDetailsService) {
        this.catalogService = catalogService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String listCatalogs(String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatelogs(user);

        boolean isCatalogOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isCatalogOwner = true;
            }
        }

        model.addAttribute("catalogs", catalogs);
        model.addAttribute("isCatalogOwner", isCatalogOwner);
        return "userspace/u :: #catalogReplace";
    }

    @PostMapping
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> createCatalog(String username, Catalog catalog) {
        User user = (User) userDetailsService.loadUserByUsername(username);

        try {
            catalog.setUser(user);
            catalogService.savaCatalog(catalog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "操作成功", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> removeCatelog(@PathVariable("id") Long id) {
        try {
            catalogService.removeCatelog(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "操作成功", null));
    }

    @GetMapping("/edit")
    public String getCatalogEdit(Model model) {
        Catalog catalog = new Catalog(null, null);
        model.addAttribute("catalog", catalog);
        return "userspace/catalogedit";
    }

    @GetMapping("/edit/{id}")
    public String editCatalogById(@PathVariable("id") Long id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id);
        model.addAttribute("catalog", catalog);
        return "userspace/catalogedit";
    }
}
