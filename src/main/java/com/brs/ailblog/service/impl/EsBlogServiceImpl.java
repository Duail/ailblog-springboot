package com.brs.ailblog.service.impl;

import com.brs.ailblog.domain.User;
import com.brs.ailblog.domain.es.EsBlog;
import com.brs.ailblog.repository.es.EsBlogRepository;
import com.brs.ailblog.service.EsBlogService;
import com.brs.ailblog.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 11:46 2018/6/27
 */
@Service
public class EsBlogServiceImpl implements EsBlogService {

    private final EsBlogRepository esBlogRepository;

    @Autowired
    public EsBlogServiceImpl(EsBlogRepository esBlogRepository) {
        this.esBlogRepository = esBlogRepository;
    }

    @Override
    public void removeEsBlog(String id) {
        esBlogRepository.delete(id);
    }

    @Override
    public EsBlog updateEsBlog(EsBlog esBlog) {
        return esBlogRepository.save(esBlog);
    }

    @Override
    public EsBlog getEsBlogByBlogId(Long blogId) {
        return esBlogRepository.findByBlogId(blogId);
    }

    @Override
    public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable) {

        Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize");
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsBlog> listEsBlogs(Pageable pageable) {
        return esBlogRepository.findAll(pageable);
    }

    @Override
    public List<EsBlog> listTop5NewestEsBlogs() {
        Page<EsBlog> page = this.listHotestEsBlogs("", new PageRequest(0, 5));
        return page.getContent();
    }

    @Override
    public List<EsBlog> listTop5HotestEsBlogs() {
        Page<EsBlog> page = this.listHotestEsBlogs("", new PageRequest(0, 5));
        return page.getContent();
    }

    @Override
    public List<TagVO> listTop30Tags() {
        return null;
    }

    @Override
    public List<User> listTop12Users() {
        return null;
    }
}
