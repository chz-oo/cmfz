package com.baizhi.chz.service;

import com.baizhi.chz.dao.UserGuruDao;
import com.baizhi.chz.entity.UserGuru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional//事务 默认增删改
public class UserGuruServiceImpl implements UserGuruService {
    @Autowired
    UserGuruDao userGuruDao;

    @Override
    //查所有
    public List<UserGuru> selectAll(String uid){
        UserGuru userGuru = new UserGuru();
        userGuru.setUId(uid);
        List<UserGuru> userGurus = userGuruDao.select(userGuru);
        return userGurus;
    }

    //添加
    @Override
    public void insertGuru(String uid, String gid) {
        UserGuru ug = new UserGuru();
        ug.setUId(uid);
        ug.setGId(gid);
        userGuruDao.insert(ug);
    }
}
