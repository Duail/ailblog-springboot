package com.brs.ailblog.repository;

import com.brs.ailblog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 0:04 2018/6/25
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
