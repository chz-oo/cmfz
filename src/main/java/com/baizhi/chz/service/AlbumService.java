package com.baizhi.chz.service;

import com.baizhi.chz.entity.Album;
import com.baizhi.chz.entity.Article;


import java.util.List;
import java.util.Map;

public interface AlbumService {
    //分页
    public Map<String,Object> queryAllByPage(Integer page, Integer rows);

    //分页查 aaa.java
    public List<Album> query(Integer page, Integer rows);

    //添加
    public void insert(Album album);

    //修改
    public void update(Album album);

    //删除
    public void delect(String[] id);

    //查一个
    public Album selectOne(String id);
}
