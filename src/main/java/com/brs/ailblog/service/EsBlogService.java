package com.brs.ailblog.service;

import com.brs.ailblog.domain.User;
import com.brs.ailblog.domain.es.EsBlog;
import com.brs.ailblog.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 11:40 2018/6/27
 */
public interface EsBlogService {

    void removeEsBlog(String id);

    EsBlog updateEsBlog(EsBlog esBlog);

    EsBlog getEsBlogByBlogId(Long blogId);

    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);

    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);

    Page<EsBlog> listEsBlogs(Pageable pageable);

    List<EsBlog> listTop5NewestEsBlogs();

    List<EsBlog> listTop5HotestEsBlogs();

    List<TagVO> listTop30Tags();

    List<User> listTop12Users();
}
