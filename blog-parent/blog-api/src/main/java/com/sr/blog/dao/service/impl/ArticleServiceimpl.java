package com.sr.blog.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sr.blog.dao.mapper.ArticleMapper;
import com.sr.blog.dao.pojo.Article;
import com.sr.blog.dao.service.ArticleService;
import com.sr.blog.dao.vo.Result;
import com.sr.blog.dao.vo.params.ArticleVo;
import com.sr.blog.dao.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceimpl  implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 分页查询 article表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper =new LambdaQueryWrapper<>();
//        //是否置顶进行排序
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        
        List<ArticleVo> articleVoList = copyList(articlePage.getRecords(),true,false,true);
        return null;
    }
}
