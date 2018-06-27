package com.brs.ailblog.vo;

import java.io.Serializable;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 11:38 2018/6/27
 */
public class TagVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Long count;

    public TagVO(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
