package com.baizhi.chz.entity;


import com.alibaba.excel.annotation.ExcelProperty;
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
/*
* 导出到表格
* @ExcelProperty(value = "ID") 声明表头信息
* @ExcelIgnore 导出数据时忽略该属性
*/
public class Banner implements Serializable {
  @Id
  @ExcelProperty(value = {"轮播图","ID"})
  private String id;
  @ExcelProperty(value = {"轮播图","标题"})
  private String title;
  @ExcelProperty(value = {"轮播图","图片"})
  private String url;
  @ExcelProperty(value = {"轮播图","超链接"})
  private String href;

  @JSONField(format = "yyyy-MM-dd")
  //接受日期类型
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelProperty(value = {"轮播图","时间"})
  private Date createDate;
  @ExcelProperty(value = {"轮播图","描述"})
  private String desccc;
  @ExcelProperty(value = {"轮播图","状态"})
  private String status;



}
