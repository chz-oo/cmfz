package com.baizhi.chz.service;

import com.baizhi.chz.dao.BannerDao;
import com.baizhi.chz.entity.Article;
import com.baizhi.chz.entity.Banner;
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
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    //分页查
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> queryAllByPage(Integer page, Integer rows) {
        //获取数据库中的起始条
        Integer begin=(page-1)*rows;
        //轮播图查所有
        List<Banner> bannerList = bannerDao.selectByRowBounds(new Banner(),new RowBounds(begin,rows));
        //查询出总条数
        Integer records = bannerDao.selectCount(new Banner());
        //总页数   调工具类计算总页数
        Map total = Total.total(page, rows, records,bannerList);
        return total;

    }

    //查所有
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> selectAll() {
        List<Banner> banners = bannerDao.selectAll();
        for (Banner articlee : banners) {
            System.out.println(articlee.getId()+"----Banner--------------------------------业务类");
        }
        return banners;
    }

    //添加
    @Override
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    //修改
    @Override
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    //删除
    @Override
    public void delect(String[] id) {
        bannerDao.deleteByIdList(Arrays.asList(id));
    }
}
