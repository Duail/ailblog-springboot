package com.brs.ailblog.service;

import com.brs.ailblog.domain.Vote;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 10:06 2018/6/26
 */
public interface VoteService {

    Vote getVoteById(Long id);

    void removeVote(Long id);
}
