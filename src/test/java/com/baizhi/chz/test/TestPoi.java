package com.baizhi.chz.test;

import com.baizhi.chz.entity.Banner;
import com.baizhi.chz.service.BannerService;
import org.apache.poi.hssf.record.cf.FontFormatting;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class TestPoi {
    @Autowired
    BannerService bannerService;

    //java poi  简单案例1
    @Test
    public void testPoi(){
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作蒲   参数是工作蒲  名字
        HSSFSheet sheet = workbook.createSheet("练习poi1");
        //创建行  参数:行下标  0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格  参数:单元格下表  0开始7
        HSSFCell cell = row.createCell(0);
        //给单元格设置内容
        cell.setCellValue("这是第一行第一个单元格");
        String data = UUID.randomUUID().toString();
        System.out.println("data：" + data);
        //导出单元格
        try {
            workbook.write(
              new FileOutputStream(
                 new File(
                   "E:\\2培训 资料\\第三阶段\\后期项目\\day7-poiEasyExcel\\示例\\" + data + ".xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //案例2  导出  使用数据库  表：article
    @Test
    public void testPoi2(){
        //拿到article表中数据
        List<Banner> articles = bannerService.selectAll();
        System.out.println(articles +"------------------------------------articles-----");
        for (Banner article : articles) {
            System.out.println(article.getId()+"------------------------------------id");
        }
        //创建工作蒲对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表 参数：表名
        HSSFSheet sheet = workbook.createSheet("article");
        //创建标题行
        HSSFRow row = sheet.createRow(0);
        String[] title = {"id","title","img","content","创建时间","修改时间","状态","上师id"};

        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //创建日期对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));
        //构建字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);    //加粗
        font.setColor(Font.COLOR_RED); //颜色
        font.setFontHeightInPoints((short)10);  //字号
        font.setFontName("楷体");  //字体
        font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线
        cellStyle.setFont(font);     //将字体样式引入
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //文字居中


        //处理单元格对象
        HSSFCell cell = null;
        for (int i = 0;i<title.length;i++){
            //单元格下标
            cell = row.createCell(i);
            //单元格内容
            cell.setCellValue(title[i]);
            //标题使用样式
            cell.setCellStyle(cellStyle);
        }

        //处理行数据
        for (int i = 0;i < articles.size();i++){
            //遍历一次创建一行
            HSSFRow row2 = sheet.createRow(i + 1);
            row2.createCell(0).setCellValue(articles.get(i).getId());
            row2.createCell(1).setCellValue(articles.get(i).getTitle());
            row2.createCell(2).setCellValue(articles.get(i).getUrl());
            row2.createCell(3).setCellValue(articles.get(i).getHref());
            //设置单元格日期格式
            Cell cell2 = row2.createCell(4);
            cell2.setCellStyle(cellStyle);    //添加日期样式
            //添加数据
            cell2.setCellValue(articles.get(i).getCreateDate());
            row2.createCell(5).setCellValue(articles.get(i).getCreateDate());
            row2.createCell(6).setCellValue(articles.get(i).getStatus());
            row2.createCell(7).setCellValue(articles.get(i).getDesccc());
        }
        //创建输出流  从内存中写入本地磁盘
        try {
            String data = UUID.randomUUID().toString();
            workbook.write(new FileOutputStream(new File("E:\\2培训 资料\\第三阶段\\后期项目\\day7-poiEasyExcel\\示例\\" + data + ".xls")));
            //关闭资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void iii(){
        int shu = (int)(Math.random()*10000);
        String s = Integer.toString(shu);
        System.out.println(s);


        //验证码

    }
}
