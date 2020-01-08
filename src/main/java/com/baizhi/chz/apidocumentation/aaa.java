package com.baizhi.chz.apidocumentation;

import com.baizhi.chz.config.RedisConf;
import com.baizhi.chz.entity.*;
import com.baizhi.chz.service.*;
import com.baizhi.chz.util.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.util.*;

@RequestMapping("api")
@RestController
public class aaa {
    @Autowired
    Jedis jedis;
    @Autowired
    UserService userService;
    @Autowired
    GuruService guruService;
    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserGuruService userGuruService;
    @Autowired
    CourseSerevice courseSerevice;
    @Autowired
    CounterService counterService;



    // 1. 登录接口
    @RequestMapping("login")
    public Map login(String name, String password){
        Map map = new HashMap();
        User user = new User();
        user.setName(name);
        //查询是否有此用户
        User user1 = userService.selectOne(user);
        //没有
        if (user1 == null){
            //status":"-200",message:""}
            map.put("status","-200");
            map.put("massage","用户名错误");
            //有
        }else{
            //判断密码   一致
            if(user1.getPassword().equals(password)){
                map.put("status","200");
                map.put("user",user1);
            }else {
                map.put("status","-200");
                map.put("massage","密码错误");
            }
        }
        return map;
    }
    // 2. 验证码接口
    @RequestMapping("yzm")
    public Map yzm(String phone){
        Map map = new HashMap();
        //返回值是一个验证码
        try {
            String sms = SendSms.Sms(phone);
            jedis.set(phone,sms);
            map.put("status","200");
            map.put("massage","发送成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("status","-200");
            map.put("massage","发送失败");
        }
        return map;
    }
    // 3. 注册接口
    @RequestMapping("enroll")
    public Map enroll(Spring code,String phone){
        Map map = new HashMap();
        //jedis中获取验证码
        String s = jedis.get(phone);
        if(s.equals(code)){
            map.put("status","200");
            map.put("massage","注册成功");
        }else {
            map.put("status","-200");
            map.put("massage","验证码错误");
        }
        return map;
    }
    // 4. 补充个人信息接口
    @RequestMapping("oneUser")
    public Map oneUser(User user){
        Map map = new HashMap();
        try {
            user.setId(UUID.randomUUID().toString());
            userService.insertUser(user);

            User user1 = userService.selectOne(user);
            map.put("status","200");
            map.put("user",user1);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status","-200");
            map.put("massage","错误");
        }
        return map;
    }
    // 5. 一级页面展示接口
    @RequestMapping("oneYe")
    public Map oneYe(String uid,String type,String sub_typ){
        //所有的文章
        List<Article> articles = articleService.selectAll();
        Map map = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> banners = bannerService.selectAll();
                List<Album> albums = albumService.query(0, 5);
                map.put("status",200);
                map.put("head",banners);
                map.put("albums",albums);
                map.put("articles",articles);
            }else if (type.equals("wen")){
                List<Album> albums = albumService.query(0, 5);
                map.put("status",200);
                map.put("albums",albums);
            }else {
                if (sub_typ.equals("ssyj")){
                    List<Article> articl = articleService.selectAll();
                    //查该用户关注上师的文章
                    //先查出用户关注的上师id
                    List<UserGuru> userGurus = userGuruService.selectAll(uid);
                    for (UserGuru gurus : userGurus) {
                        //上师id
                        gurus.getGId();
                        //遍历所有文章  拿到文章的上师id
                        for (Article article : articles) {
                            //判断文章的上师id 和 用户关注的上师id是否一致
                            //一致则存集合
                            if(gurus.getGId().equals(article.getGuruId())){
                                articl.add(article);
                            }
                        }
                    }
                    map.put("status",200);
                    map.put("articles",articl);
                }else {
                    map.put("status",200);
                    map.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("status","-200");
            map.put("message","error");
        }
        return map;
    }
    // 6. 文章详情接口
    @RequestMapping("selectOneArticle")
    public Map selectOneArticle(String uid,String id){
        Map map = new HashMap();
        try {
            Article article = articleService.selectOne(id);
            map.put("status","200");
            map.put("article",article);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status","-200");
            map.put("massage","没有此文章");
        }
        return map;
    }
    // 7. 专辑详情接口
    @RequestMapping("selectOneAlbum")
    public Map selectOneAlbum(String uid,String id){
        Map map = new HashMap();
        Album album = albumService.selectOne(id);
        if(album==null){
            map.put("status","-200");
            map.put("massage","没有此文章");
        }else {
            map.put("status","200");
            map.put("album",album);
        }
        return map;
    }
    // 8. 展示功课
    @RequestMapping("selectGongKe")
    public Map selectGongKe(String uid){
        Map map = new HashMap();
        Course course = courseSerevice.selectOne(uid);
        if(course==null){
            map.put("status","-200");
            map.put("massage","该用户没有选功课");
        }else {
            map.put("status","200");
            map.put("course",course);
        }
        return map;
    }
    // 9. 添加功课
    @RequestMapping("insertGongKe")
    public Map insertGongKe(String uid,String title){
        Map map = new HashMap();
        Course course = new Course();

        course.setId(UUID.randomUUID().toString());
        course.setUserId(uid);
        course.setTitle(title);
        course.setCreateDate(new Date());
        course.setType("敲木鱼");
        try {
            courseSerevice.insert(course);
            map.put("status","200");
            map.put("course",course);
        }catch (Exception e){
            e.getStackTrace();
            map.put("status","-200");
            map.put("massage","添加失败");
        }
        return map;
    }
    // 10. 删除功课
    @RequestMapping("deleteGongKe")
    public Map deleteGongKe(String uid,String id){
        Map map = new HashMap();
        Course course = courseSerevice.selectOnee(uid, id);
        if(course!=null){
            courseSerevice.delete(id,uid);
            map.put("status","200");
            map.put("massage","删除成功");
        }else{
            map.put("status","-200");
            map.put("massage","没有这门功课");
        }
        return map;
    }
    // 11. 展示计数器
    @RequestMapping("selectJiShuQi")
    public Map selectJiShuQi(String uid,String id){
        Map map = new HashMap();
        Counter counters = counterService.selectOneJiShuQi(uid,id);
        if(counters==null){
            map.put("status","-200");
            map.put("massage","失败,没有这个计数器");
        }else {
            map.put("status","200");
            map.put("counters",counters);
        }
        return map;
    }
    // 12. 添加计数器
    @RequestMapping("insertJiShuQi")
    public Map insertJiShuQi(String uid,String title){
        Map map = new HashMap();
        Counter counter = new Counter();

        counter.setId(UUID.randomUUID().toString());
        counter.setUserId(uid);
        counter.setCount(0);
        counter.setCourseId("b8829f40-c97e-4638-84ec-5436b9b4cbce");
        counter.setCreateDate(new Date());
        counter.setTitle(title);
        try {
            counterService.insertJiShuQi(counter);
            map.put("status","200");
            map.put("course",counter);
        }catch (Exception e){
            e.getStackTrace();
            map.put("status","-200");
            map.put("massage","添加失败");
        }
        return map;
    }
    // 13. 删除计数器
    @RequestMapping("deleteJiShuQi")
    public Map deleteJiShuQi(String uid,String id){
        Map map = new HashMap();
        Counter counter = new Counter();
        counter.setId(id);
        counter.setUserId(uid);
        //查一个  看有没有这个计数器
        Counter counter1 = counterService.selectOneJiShuQi(uid, id);
        if(counter1!=null){
            counterService.deleteJiShuQi(counter);
            map.put("status","200");
            map.put("massage","删除成功");
        }else{
            map.put("status","-200");
            map.put("massage","没有这个计数器");
        }
        return map;
    }
    // 14. 修改计数器
    @RequestMapping("updateJiShuQi")
    public Map updateJiShuQi(String uid,String id,Integer count){
        Map map = new HashMap();
        try {
            counterService.updateJiShuQi(uid,id,count);
            map.put("status","200");
            map.put("massage","修改成功");
        }catch (Exception e){
            e.getStackTrace();
            map.put("status","-200");
            map.put("massage","修改失败");
        }

        return map;
    }
    // 15. 修改用户信息
    @RequestMapping("updateUser")
    public Map updateUser(String uid,String sex,String location,String sign,String nickName,String password){
        Map map = new HashMap();
        try {
            userService.updateUser(uid,sex,location,sign,nickName,password);
            map.put("status","200");
            map.put("massage","修改成功");
        }catch (Exception e){
            e.getStackTrace();
            map.put("status","-200");
            map.put("massage","修改失败");
        }
        return map;
    }
    // 16. 金刚道友
    @RequestMapping("selectUser")
    public Map selectUser(String uid){
        Map map = new HashMap();
        try {
            Set<User> users = userService.queryAll(uid);
            map.put("status","200");
            map.put("users",users);
        }catch (Exception e){
            e.getStackTrace();
            map.put("status","-200");
            map.put("massage","查询失败");
        }
        return map;
    }
    // 17. 展示上师列表
    @RequestMapping("selectAllGuru")
    public Map selectAllGuru(String uid){
        Map map = new HashMap();
        try {
            List<Guru> gurus = guruService.selectAll();
            map.put("status","200");
            map.put("gurus",gurus);
        }catch (Exception e){
            e.getStackTrace();
            map.put("status","-200");
            map.put("massage","查询失败");
        }
        return map;
    }
    // 18. 添加关注上师
    @RequestMapping("insertGuru")
    public Map insertGuru(String uid,String gid){
        Map map = new HashMap();
        try {
            userGuruService.insertGuru(uid,gid);
            map.put("status","200");
            map.put("gurus","添加成功");
        }catch (Exception e){
            e.getStackTrace();
            map.put("status","-200");
            map.put("massage","添加失败");
        }
        return map;
    }
}
