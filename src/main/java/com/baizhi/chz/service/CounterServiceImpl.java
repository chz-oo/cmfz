package com.baizhi.chz.service;

import com.baizhi.chz.dao.CounterDao;
import com.baizhi.chz.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    CounterDao counterDao;

    @Override
    //查一个
    public Counter selectOneJiShuQi(String uid,String id) {
        Counter counter = new Counter();
        counter.setId(id);
        counter.setUserId(uid);
        Counter counters = counterDao.selectOne(counter);
        return counters;
    }

    //添加
    @Override
    public void insertJiShuQi(Counter counter) {
        counterDao.insert(counter);
    }

    //删除
    @Override
    public void deleteJiShuQi(Counter counter) {
        counterDao.delete(counter);
    }


    //修改
    @Override
    public void updateJiShuQi(String uid,String id,Integer count) {
        Counter counter = new Counter();
        counter.setId(id);
        counter.setUserId(uid);
        counter.setCount(count);
        counter.setTitle("");
        counterDao.updateByPrimaryKeySelective(counter);
    }
}
