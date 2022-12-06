package com.sr.blog.dao.controller;

import com.sr.blog.dao.service.ArticleService;
import com.sr.blog.dao.vo.Result;
import com.sr.blog.dao.vo.params.ArticleVo;
import com.sr.blog.dao.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){
//        List<ArticleVo> articles = articleService.listArticlesPage(pageParams);
         return  articleService.listArticle(pageParams);

    }
}
