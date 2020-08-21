package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.J7rZhbiaoStatistics;
import com.kalvin.kvf.modules.func.service.J7rZhbiaoStatisticsService;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2020-08-18 10:07:13
 */
@RestController
@RequestMapping("func/j7rZhbiaoStatistics")
public class J7rZhbiaoStatisticsController extends BaseController {

    @Autowired
    private J7rZhbiaoStatisticsService j7rZhbiaoStatisticsService;

    @RequiresPermissions("func:j7rZhbiaoStatistics:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/j7rZhbiaoStatistics");
    }

    @GetMapping(value = "list/data")
    public R listData(J7rZhbiaoStatistics j7rZhbiaoStatistics) {
        Page<J7rZhbiaoStatistics> page = j7rZhbiaoStatisticsService.listJ7rZhbiaoStatisticsPage(j7rZhbiaoStatistics);
        return R.ok(page);
    }

    @RequiresPermissions("func:j7rZhbiaoStatistics:edit")
    @PostMapping(value = "edit")
    public R edit(J7rZhbiaoStatistics j7rZhbiaoStatistics) {
        j7rZhbiaoStatisticsService.updateById(j7rZhbiaoStatistics);
        return R.ok();
    }

    @RequiresPermissions("func:j7rZhbiaoStatistics:del")
    @PostMapping(value = "del/{tjrq}")
    public R del(@PathVariable Long tjrq) {
        j7rZhbiaoStatisticsService.removeById(tjrq);
        return R.ok();
    }

    @GetMapping(value = "get/{tjrq}")
    public R get(@PathVariable Long tjrq) {
        return R.ok(j7rZhbiaoStatisticsService.getById(tjrq));
    }

}

