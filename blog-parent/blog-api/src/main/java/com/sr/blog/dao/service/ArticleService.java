package com.sr.blog.dao.service;

import com.sr.blog.dao.vo.Result;
import com.sr.blog.dao.vo.params.PageParams;
import lombok.Data;

public interface ArticleService {
    /**
     *
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);
}
