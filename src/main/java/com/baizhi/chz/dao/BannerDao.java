package com.baizhi.chz.dao;


        import com.baizhi.chz.entity.Banner;
        import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
        import tk.mybatis.mapper.common.Mapper;

public interface BannerDao extends Mapper<Banner>, DeleteByIdListMapper<Banner,String> {
}