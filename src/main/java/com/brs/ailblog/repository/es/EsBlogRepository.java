package com.brs.ailblog.repository.es;

import com.brs.ailblog.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Description:
 * @Author: DC
 * @Date: created in 11:12 2018/6/27
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {

    /**
     * 全文检索模糊查询
     * @param title
     * @param summary
     * @param content
     * @param tags
     * @param pageable
     * @return
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String summary, String content, String tags, Pageable pageable);

    EsBlog findByBlogId(Long blogId);

}
