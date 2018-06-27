package com.brs.ailblog.vo;

import com.brs.ailblog.domain.Catalog;

import java.io.Serializable;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 14:54 2018/6/26
 */
public class CatalogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private Catalog catalog;

    public CatalogVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
