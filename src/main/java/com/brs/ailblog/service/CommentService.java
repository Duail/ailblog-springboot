package com.brs.ailblog.service;

import com.brs.ailblog.domain.Comment;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 0:05 2018/6/25
 */
public interface CommentService {

    Comment getCommentById(Long id);

    void removeComment(Long id);
}
