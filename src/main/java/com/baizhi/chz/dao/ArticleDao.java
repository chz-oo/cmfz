package com.baizhi.chz.dao;

import com.baizhi.chz.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {
    /*//根据上师id  查文章
    List<Article> selectAll(String gid);*/
}
