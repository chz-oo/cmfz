package com.baizhi.chz.service;


import com.baizhi.chz.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    //分页
    public Map<String,Object> queryAllByPage(Integer page, Integer rows);

    //查所有
    public List<Guru> selectAll();

    //添加
    public void insert(Guru guru);

    //修改
    public void update(Guru guru);

    //删除
    public void delect(String[] id);

    //查一个
    public Guru selectOne(String id);
}
