package com.brs.ailblog.service.impl;

import com.brs.ailblog.domain.Authority;
import com.brs.ailblog.repository.AuthorityRepository;
import com.brs.ailblog.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 16:56 2018/6/11
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findOne(id);
    }
}
