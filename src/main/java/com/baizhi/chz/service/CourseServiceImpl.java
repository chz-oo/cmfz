package com.baizhi.chz.service;


import com.baizhi.chz.dao.CourseDao;
import com.baizhi.chz.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseSerevice {
    @Autowired
    private CourseDao courseDao;


    @Override
    //根据用户id查一个
    public Course selectOne(String uid) {
        Course course = new Course();
        course.setUserId(uid);
        Course course1 = courseDao.selectOne(course);
        return course1;
    }

    @Override
    //根据用户id 和 功课id查一个
    public Course selectOnee(String uid ,String id) {
        Course course = new Course();
        course.setUserId(uid);
        course.setId(id);
        Course course1 = courseDao.selectOne(course);
        return course1;
    }


    @Override
    //添加功课
    public void insert(Course course) {
        courseDao.insert(course);
    }

    @Override
    //删除功课
    public void delete(String id,String uid) {
        Course course = new Course();
        course.setId(id);
        course.setUserId(uid);
        courseDao.delete(course);
    }
}
