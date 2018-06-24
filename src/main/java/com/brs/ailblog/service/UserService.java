package com.brs.ailblog.service;

import com.brs.ailblog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Description: 用户服务接口
 * @Author: DC
 * @Date: created in 10:25 2018/6/6
 */
public interface UserService {

    User saveOrUpdate(User user);

    User registerUser(User user);

    void removeUser(Long id);

    User getUserById(Long id);

    Page<User> listUserByNameLike(String name, Pageable pageable);

}
