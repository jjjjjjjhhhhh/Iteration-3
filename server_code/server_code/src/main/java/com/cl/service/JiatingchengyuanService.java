package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.JiatingchengyuanEntity;
import java.util.List;
import java.util.Map;
import com.cl.entity.view.JiatingchengyuanView;

/**
 * 家庭成员
 */
public interface JiatingchengyuanService extends IService<JiatingchengyuanEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<JiatingchengyuanView> selectListView(Wrapper<JiatingchengyuanEntity> wrapper);
    
    JiatingchengyuanView selectView(Wrapper<JiatingchengyuanEntity> wrapper);
    
    PageUtils queryPage(Map<String, Object> params, Wrapper<JiatingchengyuanEntity> wrapper);
} 