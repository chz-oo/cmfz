package com.baizhi.chz.service;


import com.baizhi.chz.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //分页
    public Map<String,Object> queryAllByPage(Integer page, Integer rows);


    //添加
    public void insert(Chapter chapter);

    //修改
    public void update(Chapter chapter);

    //删除
    public void delect(String[] id);
}
