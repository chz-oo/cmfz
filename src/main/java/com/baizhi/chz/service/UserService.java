package com.baizhi.chz.service;



import com.baizhi.chz.entity.User;

import java.util.Map;
import java.util.Set;

public interface UserService {
    //根据用户的性别 查时间
    Map queryUserByTime();

    //根据用户的性别 查所在地
    Map queryUserByLocation();

    //查一个
    User selectOne(User user);

    //添加
    void insertUser(User user);

    //修改
    public void updateUser(String uid,String sex,String location,String sign,String nickName,String password);

    //随机查询五个用户，除自己
    public Set<User> queryAll(String id);
}
