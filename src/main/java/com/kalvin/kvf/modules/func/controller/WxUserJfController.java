package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.func.entity.WxUserJf;
import com.kalvin.kvf.modules.func.service.WxUserJfService;
import com.kalvin.kvf.modules.func.service.WxUserService;
import com.kalvin.kvf.modules.func.vo.WxUserJfVo;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * ji fen li shi biao 前端控制器
 * </p>
 *
 * @since 2020-08-11 13:49:04
 */
@RestController
@RequestMapping("func/wxUserJf")
public class WxUserJfController extends BaseController {

    @Autowired
    private WxUserJfService wxUserJfService;

    @Autowired
    private WxUserService wxUserService;

    @RequiresPermissions("func:wxUserJf:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView ("func/wxUserJf");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView ("func/wxUserJf_edit");
        WxUserJf wxUserJf;
        if (id == null) {
            wxUserJf = new WxUserJf ();
        } else {
            wxUserJf = wxUserJfService.getById (id);
        }
        mv.addObject ("editInfo", wxUserJf);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(WxUserJf wxUserJf) {
        Page<WxUserJf> page = wxUserJfService.listWxUserJfPage (wxUserJf);
        return R.ok (page);
    }

    @RequiresPermissions("func:wxUserJf:add")
    @PostMapping(value = "add")
    public R add(WxUserJf wxUserJf) {
        wxUserJfService.save (wxUserJf);
        return R.ok ();
    }

    @RequiresPermissions("func:wxUserJf:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        wxUserJfService.removeByIds (ids);
        return R.ok ();
    }

    @RequiresPermissions("func:wxUserJf:edit")
    @PostMapping(value = "edit")
    public R edit(WxUserJf wxUserJf) {
        wxUserJfService.updateById (wxUserJf);
        return R.ok ();
    }

    @RequiresPermissions("func:wxUserJf:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        wxUserJfService.removeById (id);
        return R.ok ();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok (wxUserJfService.getById (id));
    }

    @GetMapping(value = "getByInvitedCode")
    public R getByInvitedCode(@Param("invitedCode") String invitedCode, @Param("targetDate") String targetDate) {
        List<WxUser> wxUser = wxUserService.list (new LambdaQueryWrapper<WxUser> ()
        .eq (WxUser::getInvitedCode, invitedCode));

        if(wxUser.size ()> 0) {
            List<WxUserJf> userJfs = wxUserJfService.list (new LambdaQueryWrapper<WxUserJf> ()
                    .eq (WxUserJf::getTargetInvitedCode, invitedCode)
                    .apply ("date_format(zc_time,'%Y-%m-%d') = '" + targetDate + "'")
                    .orderByDesc (WxUserJf::getZcTime));

            WxUserJfVo wxUserJfVo = new WxUserJfVo ();
            wxUserJfVo.setWxUser (wxUser.get (0));
            wxUserJfVo.setWxUserJfs (userJfs);
            return R.ok (wxUserJfVo);
        }else{
            return R.fail ("该邀请码不存在！");
        }
    }
}

