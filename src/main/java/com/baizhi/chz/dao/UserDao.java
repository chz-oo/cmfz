package com.baizhi.chz.dao;

import com.baizhi.chz.entity.User;
import com.baizhi.chz.entity.UserDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;


public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    //根据用户的性别 查时间
    Integer queryUserByTime(@Param("sex")String sex,@Param("day")Integer day);
    //根据用户的性别 查所在地
    List<UserDTO> getCountByLocation(String sex);

}
