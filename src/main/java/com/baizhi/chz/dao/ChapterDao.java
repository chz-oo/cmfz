package com.baizhi.chz.dao;


import com.baizhi.chz.entity.Chapter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ChapterDao extends InsertListMapper<Chapter>,Mapper<Chapter>, DeleteByIdListMapper<Chapter,String> {
}

