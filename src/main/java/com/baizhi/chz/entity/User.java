package com.baizhi.chz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

  @Id
  private String id;
  private String phone;
  private String password;
  private String status;
  private String photo;
  private String name;
  private String nickName;
  private String sex;
  private String sign;
  private String location;
  @JSONField(format = "yyyy-MM-dd")
  //接受日期类型
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date rigestDate;
  @JSONField(format = "yyyy-MM-dd")
  //接受日期类型
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastLogin;



}
