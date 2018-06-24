package com.brs.ailblog.repository;

import com.brs.ailblog.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 16:52 2018/6/11
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
