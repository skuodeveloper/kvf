package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.LuckWxuser;
import com.kalvin.kvf.modules.func.service.LuckWxuserService;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2020-08-21 14:27:53
 */
@RestController
@RequestMapping("func/luckWxuser")
public class LuckWxuserController extends BaseController {

    @Autowired
    private LuckWxuserService luckWxuserService;

    @RequiresPermissions("func:luckWxuser:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/luckWxuser");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/luckWxuser_edit");
        LuckWxuser luckWxuser;
        if (id == null) {
            luckWxuser = new LuckWxuser();
        } else {
            luckWxuser = luckWxuserService.getById(id);
        }
        mv.addObject("editInfo", luckWxuser);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(LuckWxuser luckWxuser) {
        Page<LuckWxuser> page = luckWxuserService.listLuckWxuserPage(luckWxuser);
        return R.ok(page);
    }

    @RequiresPermissions("func:luckWxuser:add")
    @PostMapping(value = "add")
    public R add(LuckWxuser luckWxuser) {
        luckWxuserService.save(luckWxuser);
        return R.ok();
    }

    @RequiresPermissions("func:luckWxuser:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        luckWxuserService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:luckWxuser:edit")
    @PostMapping(value = "edit")
    public R edit(LuckWxuser luckWxuser) {
        luckWxuserService.updateById(luckWxuser);
        return R.ok();
    }

    @RequiresPermissions("func:luckWxuser:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        luckWxuserService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(luckWxuserService.getById(id));
    }

}

