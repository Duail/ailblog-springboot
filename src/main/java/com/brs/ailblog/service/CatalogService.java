package com.brs.ailblog.service;

import com.brs.ailblog.domain.Catalog;
import com.brs.ailblog.domain.User;

import java.util.List;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 14:59 2018/6/26
 */
public interface CatalogService {

    Catalog savaCatalog(Catalog catalog);

    void removeCatelog(Long id);

    Catalog getCatalogById(Long id);

    List<Catalog> listCatelogs(User user);
}
