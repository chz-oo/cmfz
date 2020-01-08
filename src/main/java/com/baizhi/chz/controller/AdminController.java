package com.baizhi.chz.controller;

import com.baizhi.chz.dao.AdminDao;
import com.baizhi.chz.entity.Admin;
import com.baizhi.chz.service.AdminService;
import com.baizhi.chz.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService service;

    // 验证码功能
    @RequestMapping("/yzm")
    //验证码
    public void verify(HttpSession session, HttpServletResponse response) throws IOException {
        //使用验证码插件
        CreateValidateCode vcode = new CreateValidateCode();
        //获取随机验证码
        String code = vcode.getCode();
        //输出图片到client
        vcode.write(response.getOutputStream());
        //获取session
        session.setAttribute("ServerCode", code);
    }


    //判断登录功能
    @ResponseBody
    @RequestMapping("login")
    public Map login(String username,String password,String clientCode,HttpSession session){
        // 1. 创建需要返回的Map集合
        HashMap hashMap = new HashMap();
        // 2. 调业务   查一个
        Admin adminDB = service.selectOne(username);
        // 3. 判断情况封装错误信息
        //判断验证码是否一致
        //从session中拿验证码
        String serverCode = session.getAttribute("ServerCode").toString();
        if(!clientCode.equals(serverCode)){
            hashMap.put("status",400);
            hashMap.put("msg","验证码错误");
        }else if (adminDB==null){
            hashMap.put("status",400);
            hashMap.put("msg","该用户不存在");
        }else if(!adminDB.getPassword().equals(password)){
            hashMap.put("status",400);
            hashMap.put("msg","密码错误");
        }else {
            hashMap.put("status",200);
        }
        // 4.把名字存到session
        session.setAttribute("username",username);
        // 5. 返回集合
        return hashMap;
    }


    //退出功能
    @RequestMapping("tui")
    public String tui(HttpSession session){
        session.removeAttribute("username");
        return "forward:/jsp/login.jsp";
    }
}
