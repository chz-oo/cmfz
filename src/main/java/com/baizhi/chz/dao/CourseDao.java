package com.baizhi.chz.dao;


import com.baizhi.chz.entity.Course;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CourseDao extends InsertListMapper<Course>, Mapper<Course>, DeleteByIdListMapper<Course,String> {
}
