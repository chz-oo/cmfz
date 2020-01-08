package com.baizhi.chz.service;

import com.baizhi.chz.dao.ChapterDao;
import com.baizhi.chz.entity.Chapter;
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
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    //分页查
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> queryAllByPage(Integer page, Integer rows) {
        //获取数据库中的起始条
        Integer begin = (page-1)*rows;
        //专辑查所有
        List<Chapter> chapterList = chapterDao.selectByRowBounds(new Chapter(),new RowBounds(begin,rows));
        //查询出总条数.selectByRowBounds
        Integer records = chapterDao.selectCount(new Chapter());
        //总页数  调工具类计算总页数
        Map total = Total.total(page, rows, records,chapterList);
        return total;
    }


    //添加
    @Override
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    //修改
    @Override
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    //删除
    @Override
    public void delect(String[] id) {
        chapterDao.deleteByIdList(Arrays.asList(id));
    }

}
