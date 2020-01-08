package com.baizhi.chz.service;

import com.baizhi.chz.dao.UserDao;
import com.baizhi.chz.entity.User;
import com.baizhi.chz.entity.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;


    //根据用户的性别 查时间
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryUserByTime() {
        Map map = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("0",1));
        manList.add(userDao.queryUserByTime("0",7));
        manList.add(userDao.queryUserByTime("0",30));
        manList.add(userDao.queryUserByTime("0",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("1",1));
        womenList.add(userDao.queryUserByTime("1",7));
        womenList.add(userDao.queryUserByTime("1",30));
        womenList.add(userDao.queryUserByTime("1",365));
        map.put("man",manList);
        map.put("women",womenList);
        return map;
    }

    //根据用户的性别 查所在地
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryUserByLocation() {
        Map map = new HashMap();
        List<UserDTO> manList = userDao.getCountByLocation("0");
        List<UserDTO> womenList = userDao.getCountByLocation("1");
        map.put("man",manList);
        map.put("women",womenList);
        return map;
    }

    //查一个
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public User selectOne(User user){
        User user1 = userDao.selectOne(user);
        return user1;
    }

    //添加
    @Override
    public void insertUser(User user){
        userDao.insert(user);
    }

    //修改
    @Override
    public void updateUser(String uid,String sex,String location,String sign,String nickName,String password){
        User user = new User();
        user.setId(uid);
        user.setSex(sex);
        user.setLocation(location);
        user.setSign(sign);
        user.setNickName(nickName);
        user.setPassword(password);
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    //随机查询五个用户，除自己
    public Set<User> queryAll(String id) {
        List<User> users = userDao.selectAll();
        HashSet<User> hashSet = new HashSet<User>();
        while (true){
            User user = users.get((int) (Math.random() * users.size()));
            if (!user.getId().equals(id)){
                hashSet.add(user);
            }
            if (hashSet.size()==5)return hashSet;
        }
    }
}
