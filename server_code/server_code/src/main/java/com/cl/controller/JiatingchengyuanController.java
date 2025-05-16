package com.cl.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.cl.entity.YonghuEntity;
import com.cl.service.YonghuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;

import com.cl.entity.JiatingchengyuanEntity;
import com.cl.entity.view.JiatingchengyuanView;
import com.cl.service.JiatingchengyuanService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;

/**
 * 家庭成员
 * 后端接口
 */
@RestController
@RequestMapping("/jiatingchengyuan")
public class JiatingchengyuanController {
    @Autowired
    private JiatingchengyuanService jiatingchengyuanService;
    
    @Autowired
    private YonghuService yonghuService;

    /**
     * 查询用户列表
     */
    @RequestMapping("/searchYonghu")
    public R searchYonghu(@RequestParam String yonghuzhanghao) {
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();
        ew.like("yonghuzhanghao", yonghuzhanghao);
        List<YonghuEntity> list = yonghuService.selectList(ew);
        return R.ok().put("data", list);
    }

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, JiatingchengyuanEntity jiatingchengyuan, HttpServletRequest request) {
        EntityWrapper<JiatingchengyuanEntity> ew = new EntityWrapper<JiatingchengyuanEntity>();
        if(jiatingchengyuan != null) {
            if(jiatingchengyuan.getYonghuzhanghao() != null && !"".equals(jiatingchengyuan.getYonghuzhanghao())) {
                ew.eq("yonghuzhanghao", jiatingchengyuan.getYonghuzhanghao());
            }
            if(jiatingchengyuan.getChengyuanzhanghao() != null && !"".equals(jiatingchengyuan.getChengyuanzhanghao())) {
                ew.eq("chengyuanzhanghao", jiatingchengyuan.getChengyuanzhanghao());
            }
        }
        PageUtils page = jiatingchengyuanService.queryPage(params, ew);
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, JiatingchengyuanEntity jiatingchengyuan, HttpServletRequest request) {
        EntityWrapper<JiatingchengyuanEntity> ew = new EntityWrapper<JiatingchengyuanEntity>();
        if(jiatingchengyuan != null) {
            if(jiatingchengyuan.getYonghuzhanghao() != null && !"".equals(jiatingchengyuan.getYonghuzhanghao())) {
                ew.eq("yonghuzhanghao", jiatingchengyuan.getYonghuzhanghao());
            }
            if(jiatingchengyuan.getChengyuanzhanghao() != null && !"".equals(jiatingchengyuan.getChengyuanzhanghao())) {
                ew.eq("chengyuanzhanghao", jiatingchengyuan.getChengyuanzhanghao());
            }
        }
        PageUtils page = jiatingchengyuanService.queryPage(params, ew);
        return R.ok().put("data", page);
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JiatingchengyuanEntity jiatingchengyuan) {
        EntityWrapper<JiatingchengyuanEntity> ew = new EntityWrapper<JiatingchengyuanEntity>();
        ew.allEq(MPUtil.allEQMapPre(jiatingchengyuan, "jiatingchengyuan"));
        JiatingchengyuanView jiatingchengyuanView = jiatingchengyuanService.selectView(ew);
        return R.ok("查询家庭成员成功").put("data", jiatingchengyuanView);
    }
    
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        JiatingchengyuanEntity jiatingchengyuan = jiatingchengyuanService.selectById(id);
        return R.ok().put("data", jiatingchengyuan);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        JiatingchengyuanEntity jiatingchengyuan = jiatingchengyuanService.selectById(id);
        return R.ok().put("data", jiatingchengyuan);
    }
    
    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JiatingchengyuanEntity jiatingchengyuan, HttpServletRequest request) {
        // 验证是否已经是家庭成员
        EntityWrapper<JiatingchengyuanEntity> ew = new EntityWrapper<JiatingchengyuanEntity>();
        ew.eq("yonghuzhanghao", jiatingchengyuan.getYonghuzhanghao());
        ew.eq("chengyuanzhanghao", jiatingchengyuan.getChengyuanzhanghao());
        if (jiatingchengyuanService.selectCount(ew) > 0) {
            return R.error("该用户已经是您的家庭成员");
        }
        
        jiatingchengyuan.setId(null);
        jiatingchengyuanService.insert(jiatingchengyuan);
        return R.ok();
    }
    
    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody JiatingchengyuanEntity jiatingchengyuan, HttpServletRequest request) {
        jiatingchengyuanService.updateById(jiatingchengyuan);
        return R.ok();
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        jiatingchengyuanService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
} 