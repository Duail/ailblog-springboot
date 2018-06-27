package com.brs.ailblog.service.impl;

import com.brs.ailblog.domain.Comment;
import com.brs.ailblog.repository.CommentRepository;
import com.brs.ailblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 0:07 2018/6/25
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findOne(id);
    }

    @Override
    public void removeComment(Long id) {
        commentRepository.delete(id);
    }
}
