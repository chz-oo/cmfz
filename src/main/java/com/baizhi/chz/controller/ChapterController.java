package com.baizhi.chz.controller;


import com.baizhi.chz.dao.ChapterDao;
import com.baizhi.chz.entity.Album;
import com.baizhi.chz.entity.Chapter;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterDao chapterDao;
    @RequestMapping("showChapterById")
    public Map showChapterById(Integer page,Integer rows,String albumId){
        HashMap hashMap = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        int i = chapterDao.selectCount(chapter);
        Integer total = i%rows==0?i/rows:i/rows+1;
        List<Chapter> chapterDaos = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",i);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",chapterDaos);
        return hashMap;
    }
    @RequestMapping("editChapter")
    // albumId 专辑id  通过editurl参数传递
    public Map editChapter(String oper, Chapter chapter, String[] id,String albumId){
        HashMap hashMap = new HashMap();
        // 添加逻辑
        if (oper.equals("add")) {
            String chapterId = UUID.randomUUID().toString();
            chapter.setId(chapterId);
            chapter.setAlbumId(albumId);
            chapterDao.insert(chapter);
            hashMap.put("chapterId", chapterId);
            // 修改逻辑
        } else if (oper.equals("edit")) {
            chapterDao.updateByPrimaryKeySelective(chapter);
            hashMap.put("chapterId", chapter.getId());
            // 删除
        } else {
            chapterDao.deleteByIdList(Arrays.asList(id));
        }
        return hashMap;
    }

    //上传文件
    @RequestMapping("uploadChapter")
    public Map uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String realPath = session.getServletContext().getRealPath("/music/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 防止重名
        String originalFilename = new Date().getTime()+"_"+url.getOriginalFilename();
        //上传文件
        url.transferTo(new File(realPath, originalFilename));
        //更新数据库信息
        String http = "/music/" + originalFilename;
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        // 计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        System.out.println(size+"----------------------这是size--------------");
        chapter.setSize(size);
        // 计算音频时长
        // 使用三方计算音频时间工具类 得出音频时长
        String[] split = http.split("/");
        // 获取文件名
        String name = split[split.length-1];
        // 通过文件获取AudioFile对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);
        chapterDao.updateByPrimaryKeySelective(chapter);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }


    //下载
    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response,HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        //设置响应头信息（以附件形式下载）  设置字符集 utf-8
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(name,"utf-8") );
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);

    }
}
