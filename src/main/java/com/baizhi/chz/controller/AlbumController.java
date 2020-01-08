package com.baizhi.chz.controller;

import com.baizhi.chz.entity.Album;
import com.baizhi.chz.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    //分页查
    @RequestMapping("fen")
    public Map fen(Integer page, Integer rows) {
        Map<String, Object> stringObjectMap = albumService.queryAllByPage(page, rows);
        return stringObjectMap;
    }


    //增删改
    @RequestMapping("function")
    public Map function(Album album, String oper, String[] id) {
        HashMap hashMap = new HashMap();
        //添加
        if (oper.equals("add")) {
            String albumId = UUID.randomUUID().toString();
            album.setId(albumId);
            hashMap.put("AlbumId", albumId);
            albumService.insert(album);
            //修改
        } else if (oper.equals("edit")) {
            albumService.update(album);
            //删除
        } else {
            albumService.delect(id);
        }
        return hashMap;
    }

    //文件上传
    @RequestMapping("uploadAlbum")
    public Map uploadAlbum(MultipartFile cover, String albumId, HttpSession session) {
        HashMap hashMap = new HashMap();
        //获取文件真实路径
        String img = session.getServletContext().getRealPath("/albumImg/");
        System.out.println(img+"------------------------------");
        System.out.println("url++++++"+cover+"++++++++++++++++++++++++++++++++++++++++");
        //判断该文件夹是否存在
        File file = new File(img);
        if (!file.exists()) {
            //多级创建
            file.mkdirs();
        }
        // 防止重名
        String originalFilename = UUID.randomUUID().toString() + ".jpg";
        try {
            //上传文件
            cover.transferTo(new File(img, originalFilename));
            //更新数据库信息
            Album album = new Album();
            album.setId(albumId);
            album.setCover("/albumImg/" + originalFilename);
            albumService.update(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hashMap.put("status", 200);
        return hashMap;
    }


}