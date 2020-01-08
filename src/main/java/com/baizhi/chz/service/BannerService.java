package com.baizhi.chz.service;

import com.baizhi.chz.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    //分页查
    public Map<String,Object> queryAllByPage(Integer page, Integer rows);

    //查所有
    public List<Banner> selectAll();

    //添加
    public void insert(Banner banner);

    //修改
    public void update(Banner banner);

    //删除
    public void delect(String[] id);
}
