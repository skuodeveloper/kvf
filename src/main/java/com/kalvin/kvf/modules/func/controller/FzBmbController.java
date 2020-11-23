package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;
import com.kalvin.kvf.modules.func.entity.FzBmb;
import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.func.service.FzBmbService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 星火反诈联盟报名表 前端控制器
 * </p>
 *
 * @since 2020-10-28 18:26:08
 */
@RestController
@RequestMapping("func/fzBmb")
public class FzBmbController extends BaseController {

    @Autowired
    private FzBmbService fzBmbService;

    @RequiresPermissions("func:fzBmb:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView ("func/fzBmb");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView ("func/fzBmb_edit");
        FzBmb fzBmb;
        if (id == null) {
            fzBmb = new FzBmb ();
        } else {
            fzBmb = fzBmbService.getById (id);
        }
        mv.addObject ("editInfo", fzBmb);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(FzBmb fzBmb) {
        Page<FzBmb> page = fzBmbService.listFzBmbPage (fzBmb);
        return R.ok (page);
    }

    //    @RequiresPermissions("func:fzBmb:add")
    @PostMapping(value = "add")
    public R add(FzBmb fzBmb) {
        fzBmbService.save (fzBmb);
        return R.ok ();
    }

    @RequiresPermissions("func:fzBmb:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        fzBmbService.removeByIds (ids);
        return R.ok ();
    }

    @RequiresPermissions("func:fzBmb:edit")
    @PostMapping(value = "edit")
    public R edit(FzBmb fzBmb) {
        fzBmbService.updateById (fzBmb);
        return R.ok ();
    }

    @RequiresPermissions("func:fzBmb:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        fzBmbService.removeById (id);
        return R.ok ();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok (fzBmbService.getById (id));
    }

    @GetMapping(value = "getWxInfo")
    public R getWxInfo(@RequestParam String openid) {
        try {
            FzBmb fzBmb = fzBmbService.getOne (new QueryWrapper<FzBmb> ().eq ("openid", openid));
            return R.ok (fzBmb);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

}

