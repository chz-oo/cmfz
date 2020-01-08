package com.baizhi.chz.controller;

import com.baizhi.chz.entity.User;
import com.baizhi.chz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    //用户时间  柱状图
    @RequestMapping("showUserTime")
    public Map showUserTime(){
        Map map = userService.queryUserByTime();
        return map;
    }

    //用户所在地分布
    @RequestMapping("showUserLocation")
    public Map showUserLocation(){
        Map map = userService.queryUserByLocation();
        return map;
    }

}
