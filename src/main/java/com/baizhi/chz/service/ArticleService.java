package com.baizhi.chz.service;


import com.baizhi.chz.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //分页查
    public Map<String,Object> queryAllByPage(Integer page, Integer rows);

    //查所有
    public List<Article> selectAll();

    //添加
    public void insert(Article article);

    //修改
    public void update(Article article);

    //删除
    public void delect(String[] id);

    int selectCount(Article article);

    //根据上师id  查文章
    public List<Article> selectAllGid(String gid);

    //查一个
    public Article selectOne(String id);
}
