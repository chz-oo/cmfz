package com.baizhi.chz.service;


import com.baizhi.chz.dao.GuruDao;
import com.baizhi.chz.entity.Guru;
import com.baizhi.chz.util.Total;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;

    //分页查
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> queryAllByPage(Integer page, Integer rows) {
        //获取数据库中的起始条
        Integer begin = (page-1)*rows;
        //专辑查所有
        List<Guru> chapterList = guruDao.selectByRowBounds(new Guru(),new RowBounds(begin,rows));
        //查询出总条数.selectByRowBounds
        Integer records = guruDao.selectCount(new Guru());
        //总页数  调工具类计算总页数
        Map total = Total.total(page, rows, records,chapterList);
        return total;
    }

    //查所有
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> selectAll() {
        List<Guru> article = guruDao.selectAll();
        return article;
    }


    //添加
    @Override
    public void insert(Guru guru) {
        guruDao.insert(guru);
    }

    //修改
    @Override
    public void update(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }

    //删除
    @Override
    public void delect(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }


    //查一个
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Guru selectOne(String id) {
        Guru guru = new Guru();
        guru.setId(id);
        Guru guru1 = guruDao.selectOne(guru);
        return guru1;
    }

}
