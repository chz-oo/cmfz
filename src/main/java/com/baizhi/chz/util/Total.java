package com.baizhi.chz.util;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Total {
    public static Map total(Integer page, Integer rows, Integer records, List bannerList){
        Map<String, Object> map = new HashMap<>();
        //总页数
        Integer total = records % rows == 0 ? records / rows : records / rows+1;
        map.put("total",total);     //总页数
        map.put("records",records); //总条数
        map.put("page",page);       //当前页
        map.put("rows",bannerList);//轮播图查所有
        return map;
    }

}
