package com.baizhi.chz.service;

import com.baizhi.chz.entity.Course;

public interface CourseSerevice {
    //根据用户id查一个
    public Course selectOne(String uid);

    //根据用户id 和 功课id查一个
    public Course selectOnee(String uid ,String id);

    //添加功课
    void insert(Course course);

    //删除功课
    void delete(String id,String uid);
}
