package com.baizhi.chz.service;


import com.baizhi.chz.dao.AlbumDao;
import com.baizhi.chz.entity.Album;
import com.baizhi.chz.entity.Article;
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
@Transactional//事务 默认增删改
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    //分页查
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> queryAllByPage(Integer page, Integer rows) {
        //获取数据库中的起始条
        Integer begin = (page-1)*rows;
        //专辑查所有
        List<Album> bannerList = albumDao.selectByRowBounds(new Album(),new RowBounds(begin,rows));
        //查询出总条数.selectByRowBounds
        Integer records = albumDao.selectCount(new Album());
        //总页数  调工具类计算总页数
        Map total = Total.total(page, rows, records,bannerList);
        return total;
    }


    //添加
    @Override
    public void insert(Album album) {
        albumDao.insert(album);
    }

    //修改
    @Override
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    //删除
    @Override
    public void delect(String[] id) {
        albumDao.deleteByIdList(Arrays.asList(id));
    }



    //分页查 aaa.java
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Album> query(Integer page, Integer rows) {
        //专辑查所有
        List<Album> albumList = albumDao.selectByRowBounds(new Album(),new RowBounds(page,rows));
        return albumList;
    }

    //查一个
    @Override
    public Album selectOne(String id){
        Album album = new Album();
        album.setId(id);
        Album album1 = albumDao.selectOne(album);
        return album1;
    }
}
