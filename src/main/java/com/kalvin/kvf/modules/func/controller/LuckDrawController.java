package com.kalvin.kvf.modules.func.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.common.utils.HttpServletContextKit;
import com.kalvin.kvf.common.utils.ShiroKit;
import com.kalvin.kvf.modules.func.entity.LuckDraw;
import com.kalvin.kvf.modules.func.entity.LuckWxuser;
import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.func.mapper.WxUserMapper;
import com.kalvin.kvf.modules.func.service.LuckDrawService;
import com.kalvin.kvf.modules.func.service.LuckWxuserService;
import com.kalvin.kvf.modules.func.service.WxUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2020-08-19 20:03:39
 */
@RestController
@RequestMapping("func/luckDraw")
public class LuckDrawController extends BaseController {
    @Resource
    WxUserMapper wxUserMapper;

    @Autowired
    private WxUserService wxUserService;

    @Resource
    LuckWxuserService luckWxuserService;

    @Resource
    private LuckDrawService luckDrawService;

    @RequiresPermissions("func:luckDraw:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView ("func/luckDraw");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView ("func/luckDraw_edit");
        mv.addObject ("luckid", id);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(LuckDraw luckDraw) {
        Page<LuckDraw> page = luckDrawService.listLuckDrawPage (luckDraw);
        return R.ok (page);
    }

    @GetMapping(value = "list/data/{luckid}")
    public R listData(@PathVariable Long luckid, WxUser wxUser) {
        try {
            wxUser.setTgrs (luckid.intValue ());
            Page<WxUser> page = wxUserService.luckWxUserList (wxUser);
            return R.ok (page);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @RequiresPermissions("func:luckDraw:add")
    @PostMapping(value = "add")
    public R add(LuckDraw luckDraw) {
        luckDrawService.save (luckDraw);
        return R.ok ();
    }

    @RequiresPermissions("func:luckDraw:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        luckDrawService.removeByIds (ids);
        return R.ok ();
    }

    @RequiresPermissions("func:luckDraw:edit")
    @PostMapping(value = "edit")
    public R edit(LuckDraw luckDraw) {
        luckDrawService.updateById (luckDraw);
        return R.ok ();
    }

    @RequiresPermissions("func:luckDraw:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        luckDrawService.removeById (id);
        return R.ok ();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok (luckDrawService.getById (id));
    }


    //    @RequiresPermissions("func:luckDraw:draw")
    @GetMapping(value = "draw")
    public R draw(WxUser wxUser) {
        try {
            LuckDraw luckDraw = new LuckDraw ();
            luckDraw.setUserId (ShiroKit.getUser ().getId ());
            luckDraw.setUserName (ShiroKit.getUser ().getUsername ());
            luckDraw.setTitle (wxUser.getTitle ());
            luckDraw.setTimes (wxUser.getTgrs ());

            // 获取request
            HttpServletRequest request = HttpServletContextKit.getHttpServletRequest ();
            // 设置IP地址
            String clientIP = ServletUtil.getClientIP (request);
            if ("0:0:0:0:0:0:0:1".equals (clientIP)) {
                clientIP = "127.0.0.1";
            }
            luckDraw.setIp (clientIP);

            List<Map<String, Object>> contions = new ArrayList<> ();

            //获取实体的所有属性
            Field[] fields = wxUser.getClass ().getDeclaredFields ();
            for (Field field : fields) {
                Method m;
                //获取属性名
                String name = field.getName ();
                //获取属性的类型
//                String type = field.getGenericType ().toString ();
                try {
                    //调用get方法获取属性值（注意这里属性名首字母记得转为大写）
                    m = wxUser.getClass ().getMethod ("get" + toUpperCaseFirstOne (name));
//                    System.out.println (name + ":" + m.invoke (wxUser) + ":" + type);
                    switch (name) {
                        case "personType":
                        case "tel":
                        case "telnull":
                        case "tgrs":
                        case "rootInvitedCode":
                        case "startdate":
                        case "enddate":
                            Map<String, Object> map = new HashMap<> ();
                            map.put (name, m.invoke (wxUser));
                            contions.add (map);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
            luckDraw.setCond (JSON.toJSONString (contions));
            luckDrawService.save (luckDraw);

            /************************插入关联表****************************/
            List<LuckWxuser> luckWxusers = new ArrayList<> ();
            List<WxUser> wxUsers = wxUserMapper.drawWxUserList (wxUser);
            for (WxUser o : wxUsers) {
                LuckWxuser luckWxuser = new LuckWxuser ();
                luckWxuser.setLuckId (luckDraw.getId ());
                luckWxuser.setWxId (o.getId ());

                luckWxusers.add (luckWxuser);
            }

            luckWxuserService.saveBatch (luckWxusers);
            return R.ok ();
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase (s.charAt (0))) {
            return s;
        } else {
            return (new StringBuilder ()).append (Character.toUpperCase (s.charAt (0))).append (s.substring (1)).toString ();
        }
    }
}

