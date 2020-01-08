package com.baizhi.chz.dao;

import com.baizhi.chz.entity.Counter;
import tk.mybatis.mapper.additional.dialect.oracle.InsertListMapper;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao extends InsertListMapper<Counter>, Mapper<Counter>, DeleteByIdListMapper<Counter,String> {
}
