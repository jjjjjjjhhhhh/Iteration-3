package com.cl.dao;

import com.cl.entity.JiatingchengyuanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiatingchengyuanView;

/**
 * 家庭成员
 */
public interface JiatingchengyuanDao extends BaseMapper<JiatingchengyuanEntity> {
    
    List<JiatingchengyuanView> selectListView(@Param("ew") Wrapper<JiatingchengyuanEntity> wrapper);

    List<JiatingchengyuanView> selectListView(Pagination page, @Param("ew") Wrapper<JiatingchengyuanEntity> wrapper);
    
    JiatingchengyuanView selectView(@Param("ew") Wrapper<JiatingchengyuanEntity> wrapper);
} 