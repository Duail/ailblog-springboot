package com.brs.ailblog.service.impl;

import com.brs.ailblog.domain.Catalog;
import com.brs.ailblog.domain.User;
import com.brs.ailblog.repository.CatalogRepository;
import com.brs.ailblog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 15:02 2018/6/26
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Catalog savaCatalog(Catalog catalog) {
        //判断重复
        List<Catalog> catalogs = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if (catalogs != null && catalogs.size() > 0) {
            throw new IllegalArgumentException("该分类已经存在");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatelog(Long id) {
        catalogRepository.delete(id);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.findOne(id);
    }

    @Override
    public List<Catalog> listCatelogs(User user) {
        return catalogRepository.findByUser(user);
    }
}
