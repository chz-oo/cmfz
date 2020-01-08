package com.baizhi.chz.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.chz.entity.Banner;
import com.baizhi.chz.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService service;


    //分页查所有
    //返回值类型为json
    @ResponseBody
    //url
    @RequestMapping("fen")
    public Map fen(Integer page,Integer rows){
        Map<String, Object> stringObjectMap = service.queryAllByPage(page, rows);
        return stringObjectMap;
    }

    //查所有
    @ResponseBody
    @RequestMapping("all")
    public List all(){
        List<Banner> banners = service.selectAll();
        return banners;
    }


    //增删改
    @ResponseBody
    @RequestMapping("function")
    public Map function(Banner banner,String oper,String[] id){
        HashMap hashMap = new HashMap();
        //添加
        if(oper.equals("add")){
            String bannerId = UUID.randomUUID().toString();
            banner.setId(bannerId);
            hashMap.put("BannerId",bannerId);
            service.insert(banner);
        //修改
        }else if(oper.equals("edit")){
            service.update(banner);
        //删除
        }else{
            service.delect(id);
        }
        return hashMap;
    }

    //文件上传
    @ResponseBody
    @RequestMapping("uploadBanner")
    public Map uploadBanner(MultipartFile url, String bannerid, HttpSession session){
        HashMap hashMap = new HashMap();
        //获取文件真实路径
        String img = session.getServletContext().getRealPath("/img/");
        //判断该文件夹是否存在
        File file = new File(img);
        if(!file.exists()){
            //多级创建
            file.mkdirs();
        }
        // 防止重名
        String originalFilename = UUID.randomUUID().toString()+".jpg";
        try {
            //上传文件
            url.transferTo(new File(img,originalFilename));
            //更新数据库信息
            Banner banner = new Banner();
            banner.setId(bannerid);
            banner.setUrl("/img/"+originalFilename);
            service.update(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hashMap.put("status",200);
        return hashMap;
    }

    //导入轮播图
    @RequestMapping("ruEasyExcel")
    public void ruEasyExcel(MultipartFile inputt,HttpSession session){
        //获取原始文件名

        String originalFilename = inputt.getOriginalFilename();
        //获取文件真实路径
        String path = session.getServletContext().getRealPath("/EasyExcel导出文件夹/");
        //判断该文件夹是否存在
        File file = new File(path);
        if (!file.exists()) {
            //多级创建
            file.mkdirs();
        }
        String url =  path + originalFilename;
        // readListener : 读取数据时的监听器  每次使用DemoDataListener都需要new  不要把DemoDataListener交给Spring工厂管理
        // 文件上传 : MFile url  文件上传  File file = new File();
        BannerListener bannerListener = new BannerListener();
        bannerListener.ser(service);
        EasyExcel.read(url,Banner.class,bannerListener).sheet().doRead();
    }

    //导出轮播图
    @RequestMapping("chuEasyExcel")
    public void chuEasyExcel(){
        //写入到哪里
        String fileName = "E:\\2培训 资料\\第三阶段\\后期项目\\EasyExcel导出文件夹\\"+new Date().getTime()+".xlsx";
        // write() 参数1:文件路径 参数2:实体类.class sheet()指定写入工作簿的名称 doWrite(List数据) 写入操作
        // 如需下载使用 参数1:outputSteam 参数2:实体类.class

        //拿到Banner所有数据
        List<Banner> banners = service.selectAll();
        // 指定文件导出的路径及样式
        EasyExcel.write(fileName, Banner.class)
                .sheet("轮播图Excel")
                .doWrite(banners);

    }
    //Excel模板下载
}
