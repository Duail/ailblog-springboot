package com.brs.ailblog.repository;

import com.brs.ailblog.domain.Catalog;
import com.brs.ailblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 14:56 2018/6/26
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    List<Catalog> findByUser(User user);

    List<Catalog> findByUserAndName(User user, String name);
}
