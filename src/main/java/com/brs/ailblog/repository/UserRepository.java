package com.brs.ailblog.repository;

import com.brs.ailblog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 10:11 2018/6/1
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByNameLike(String name, Pageable pageable);

    User findByUsername(String username);
}
