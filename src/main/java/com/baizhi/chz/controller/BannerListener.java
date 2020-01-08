package com.baizhi.chz.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.chz.entity.Banner;
import com.baizhi.chz.service.BannerService;


import java.util.ArrayList;
import java.util.List;

/*
    1. 创建一个Listener类 继承 AnalysisEventListener<实体类>
    2. 重写方法
    invoke : 读取每行数据后会执行的方法
    doAfterAllAnalysed: 所有数据读取完毕后执行的方法
 */

public class BannerListener extends AnalysisEventListener<Banner> {
    List<Banner> list = new ArrayList();

    private BannerService service;

    public void ser(BannerService service){
        this.service = service;
    }

    /*
    拿不到数据   没有交给工厂管理
    @Autowired
    BannerService bannerService;
    */
    @Override
    // DemoData 针对每行数据 进行的实体类封装
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        list.add(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        for (Banner banner : list) {
            service.insert(banner);
        }
        System.out.println("添加完成");
    }
}
