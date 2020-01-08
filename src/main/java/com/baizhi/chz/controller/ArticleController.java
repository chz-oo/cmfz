package com.baizhi.chz.controller;


import com.baizhi.chz.entity.Article;
import com.baizhi.chz.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    //分页查
    @RequestMapping("showAllArticle")
    public Map showAllArticle(Integer page,Integer rows){
        Map<String, Object> stringObjectMap = articleService.queryAllByPage(page, rows);
        return stringObjectMap;
    }
    //富文本编辑器
    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        // 该方法需要返回的信息 error 状态码 0 成功 1失败   成功时 url 图片路径
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/articleImg/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }

        try{
            // 防止重名
            String originalFilename = UUID.randomUUID().toString() + ".jpg";
            //上传文件
            imgFile.transferTo(new File(realPath, originalFilename));
            //更新数据库信息
            String http = "/albumImg/" + originalFilename;
            hashMap.put("error",0);
            hashMap.put("url",http);
        }catch (Exception e){
            hashMap.put("error",1);
            e.printStackTrace();
        }
        return hashMap;
    }

    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getContextPath()+"/articleImg/");
        String realPath = session.getServletContext().getRealPath("/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            // 创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            //  simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            // format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            // 需要将String类型 转换为Long类型 Long.valueOf(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }

    @RequestMapping("insertArticle")
    public String insertArticle(Article article,MultipartFile inputfile,String guruId,HttpSession session){
        // {id="",}
        if (article.getId()==null || "".equals(article.getId())){
            // 添加

            //图片
            //获取文件真实路径
            String img = session.getServletContext().getRealPath("/articleImg/");
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
                inputfile.transferTo(new File(img, originalFilename));
                //更新数据库信息
                //id
                String articleId = UUID.randomUUID().toString();
                article.setId(articleId);
                article.setGuruId(guruId);
                article.setImg("/articleImg/" + originalFilename);
                articleService.insert(article);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            // 修改
            articleService.update(article);
        }
        return "";
    }
}

