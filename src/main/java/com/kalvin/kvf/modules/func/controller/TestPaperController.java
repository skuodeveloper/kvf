package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.TestPaper;
import com.kalvin.kvf.modules.func.service.TestPaperService;

import java.util.List;


/**
 * <p>
 * 答题试卷 前端控制器
 * </p>
 * @since 2020-07-24 11:00:26
 */
@RestController
@RequestMapping("func/testPaper")
public class TestPaperController extends BaseController {

    @Autowired
    private TestPaperService testPaperService;

    @RequiresPermissions("func:testPaper:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/testPaper");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/testPaper_edit");
        TestPaper testPaper;
        if (id == null) {
            testPaper = new TestPaper();
        } else {
            testPaper = testPaperService.getById(id);
        }
        mv.addObject("editInfo", testPaper);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(TestPaper testPaper) {
        Page<TestPaper> page = testPaperService.listTestPaperPage(testPaper);
        return R.ok(page);
    }

    @RequiresPermissions("func:testPaper:add")
    @PostMapping(value = "add")
    public R add(TestPaper testPaper) {
        testPaperService.save(testPaper);
        return R.ok();
    }

    @RequiresPermissions("func:testPaper:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        testPaperService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:testPaper:edit")
    @PostMapping(value = "edit")
    public R edit(TestPaper testPaper) {
        testPaperService.updateById(testPaper);
        return R.ok();
    }

    @RequiresPermissions("func:testPaper:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        testPaperService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(testPaperService.getById(id));
    }

}

