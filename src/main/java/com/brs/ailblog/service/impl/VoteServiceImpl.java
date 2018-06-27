package com.brs.ailblog.service.impl;

import com.brs.ailblog.domain.Vote;
import com.brs.ailblog.repository.VoteRepository;
import com.brs.ailblog.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 10:07 2018/6/26
 */
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findOne(id);
    }

    @Override
    public void removeVote(Long id) {
        voteRepository.delete(id);
    }
}
