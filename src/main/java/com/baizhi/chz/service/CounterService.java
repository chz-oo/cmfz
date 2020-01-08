package com.baizhi.chz.service;

import com.baizhi.chz.entity.Counter;



public interface CounterService  {
    //查一个
    Counter selectOneJiShuQi(String uid,String id);

    //添加
    void insertJiShuQi(Counter counter);

    //删除
    void deleteJiShuQi(Counter counter);

    //修改计数器
    void updateJiShuQi(String uid,String id,Integer count);
}
