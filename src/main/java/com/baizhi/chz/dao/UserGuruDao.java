package com.baizhi.chz.dao;

import com.baizhi.chz.entity.UserGuru;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserGuruDao extends Mapper<UserGuru>, DeleteByIdListMapper<UserGuru,String> {
    /*//根据用户id查所有关注的上师
    List<UserGuru> selectAll(String uid);*/
}
