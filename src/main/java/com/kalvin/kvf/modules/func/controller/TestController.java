package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.Test;
import com.kalvin.kvf.modules.func.service.TestService;

import java.util.List;


/**
 * <p>
 * 测试表 前端控制器
 * </p>
 * @since 2020-05-09 10:57:42
 */
@RestController
@RequestMapping("func/test")
public class TestController extends BaseController {

    @Autowired
    private TestService testService;

    @RequiresPermissions("func:test:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/test");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/test_edit");
        Test test;
        if (id == null) {
            test = new Test();
        } else {
            test = testService.getById(id);
        }
        mv.addObject("editInfo", test);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Test test) {
        Page<Test> page = testService.listTestPage(test);
        return R.ok(page);
    }

    @RequiresPermissions("func:test:add")
    @PostMapping(value = "add")
    public R add(Test test) {
        testService.save(test);
        return R.ok();
    }

    @RequiresPermissions("func:test:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        testService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:test:edit")
    @PostMapping(value = "edit")
    public R edit(Test test) {
        testService.updateById(test);
        return R.ok();
    }

    @RequiresPermissions("func:test:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        testService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(testService.getById(id));
    }

}

