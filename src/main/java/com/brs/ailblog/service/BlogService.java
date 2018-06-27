package com.brs.ailblog.service;

import com.brs.ailblog.domain.Blog;
import com.brs.ailblog.domain.Catalog;
import com.brs.ailblog.domain.User;
import com.brs.ailblog.domain.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 15:23 2018/6/20
 */
public interface BlogService {

    Blog saveBlog(Blog blog);

    void removeBlog(Long id);

    Blog getBlogById(Long id);

    /**
     * 根据用户进行博客名称分页模糊查询（最新）
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable);

    /**
     * 据用户进行博客名称分页模糊查询（最热）
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable);

    /**
     * 阅读量递增
     * @param id
     */
    void readingIncrease(Long id);

    Blog createComment(Long blogId, String commentContent);

    void removeComment(Long blogId, Long commentId);

    Blog createVote(Long blogId);

    void removeVote(Long blogId, Long voteId);

    Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable);

}
