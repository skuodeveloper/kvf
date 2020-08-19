package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.ViewHistory;
import com.kalvin.kvf.modules.func.service.ViewHistoryService;

import java.util.List;


/**
 * <p>
 * 查看分数记录 前端控制器
 * </p>
 * @since 2020-08-14 14:13:25
 */
@RestController
@RequestMapping("func/viewHistory")
public class ViewHistoryController extends BaseController {
    @Autowired
    private ViewHistoryService viewHistoryService;

    @RequiresPermissions("func:viewHistory:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/viewHistory");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/viewHistory_edit");
        ViewHistory viewHistory;
        if (id == null) {
            viewHistory = new ViewHistory();
        } else {
            viewHistory = viewHistoryService.getById(id);
        }
        mv.addObject("editInfo", viewHistory);
        return mv;
    }

    @GetMapping(value = "location")
    public ModelAndView location(Long id) {
        ModelAndView mv = new ModelAndView("func/viewHistory_location");
        ViewHistory viewHistory;
        if (id == null) {
            viewHistory = new ViewHistory();
        } else {
            viewHistory = viewHistoryService.getById(id);
        }
        mv.addObject("editInfo", viewHistory);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(ViewHistory viewHistory) {
        Page<ViewHistory> page = viewHistoryService.listViewHistoryPage(viewHistory);
        return R.ok(page);
    }

    @PostMapping(value = "add")
    public R add(ViewHistory viewHistory) {
        try {
            viewHistoryService.save (viewHistory);
            return R.ok ();
        }catch (Exception ex){
            return R.fail (ex.getMessage ());
        }
    }

    @RequiresPermissions("func:viewHistory:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        viewHistoryService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:viewHistory:edit")
    @PostMapping(value = "edit")
    public R edit(ViewHistory viewHistory) {
        viewHistoryService.updateById(viewHistory);
        return R.ok();
    }

    @RequiresPermissions("func:viewHistory:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        viewHistoryService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(viewHistoryService.getById(id));
    }

}

