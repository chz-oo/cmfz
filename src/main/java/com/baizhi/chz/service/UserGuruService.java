package com.baizhi.chz.service;


import com.baizhi.chz.entity.UserGuru;

import java.util.List;


public interface UserGuruService {
    //查所有
    List<UserGuru> selectAll(String uid);

    //添加
    void insertGuru(String uid,String gid);
}
