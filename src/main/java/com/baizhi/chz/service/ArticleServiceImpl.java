package com.baizhi.chz.service;

import com.baizhi.chz.dao.ArticleDao;
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
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    //分页查
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> queryAllByPage(Integer page, Integer rows) {
        //获取数据库中的起始条
        Integer begin=(page-1)*rows;
        //轮播图查所有
        List<Article> articleList = articleDao.selectByRowBounds(new Article(),new RowBounds(begin,rows));
        //查询出总条数
        Integer records = articleDao.selectCount(new Article());
        //总页数   调工具类计算总页数
        Map total = Total.total(page, rows, records,articleList);
        return total;

    }

    //查所有
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Article> selectAll() {
        List<Article> article = articleDao.selectAll();
        return article;
    }

    //添加
    @Override
    public void insert(Article article) {
        articleDao.insert(article);
    }

    //修改
    @Override
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    //删除
    @Override
    public void delect(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public int selectCount(Article article) {
        int i = articleDao.selectCount(article);
        return i;
    }

    @Override
    //根据上师id  查文章
    public List<Article> selectAllGid(String gid){
        Article article = new Article();
        article.setGuruId(gid);

        List<Article> articles = articleDao.selectAll();
        return articles;
    }

    //查一个
    @Override
    public Article selectOne(String id){
        Article article = new Article();
        article.setId(id);
        Article article1 = articleDao.selectOne(article);
        return article1;
    }
}
