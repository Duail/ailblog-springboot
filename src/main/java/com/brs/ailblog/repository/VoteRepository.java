package com.brs.ailblog.repository;

import com.brs.ailblog.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 10:05 2018/6/26
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
