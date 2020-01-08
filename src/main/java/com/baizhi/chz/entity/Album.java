package com.baizhi.chz.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Album implements Serializable {

  @Id
  private String id;
  private String title;
  private String score;
  private String author;
  private String broadcast;
  private Integer count;
  private String desccc;
  private String status;
  @JSONField(format = "yyyy-MM-dd")
  //接受日期类型
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createDate;
  private String cover;



}
