package com.baizhi.chz.service;

import com.baizhi.chz.dao.AdminDao;
import com.baizhi.chz.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional//事务 默认增删改
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao dao;

    //查一个
    @Override
    //                          事务传播性设为支持事务          只读
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Admin selectOne(String name) {
        Admin admin = new Admin();
        admin.setUsername(name);

        Admin admin1 = dao.selectOne(admin);
        return admin1;
    }
}
