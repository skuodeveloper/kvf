package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.common.utils.HttpUtils;
import com.kalvin.kvf.common.utils.QRCodeUtils;
import com.kalvin.kvf.common.utils.ShiroKit;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.entity.TestPaper;
import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.func.service.AnswerRecordService;
import com.kalvin.kvf.modules.func.service.QuestionBankService;
import com.kalvin.kvf.modules.func.service.TestPaperService;
import com.kalvin.kvf.modules.func.service.WxUserService;
import com.kalvin.kvf.modules.func.vo.QuestionBankVo;
import com.kalvin.kvf.modules.sys.entity.User;
import com.kalvin.kvf.modules.sys.service.IUserService;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @since 2020-07-23 18:07:18
 */
@RestController
@RequestMapping("func/wxUser")
public class WxUserController extends BaseController {
    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AnswerRecordService answerRecordService;

    @Autowired
    private TestPaperService testPaperService;

    @Autowired
    private QuestionBankService questionBankService;

    @RequiresPermissions("func:wxUser:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView ("func/wxUser");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView ("func/wxUser_edit");
        WxUser wxUser;
        if (id == null) {
            wxUser = new WxUser ();
        } else {
            wxUser = wxUserService.getById (id);
        }
        mv.addObject ("editInfo", wxUser);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(WxUser wxUser) {
        if (wxUser.getEnddate () != null) {
            Calendar calendar = new GregorianCalendar ();
            calendar.setTime (wxUser.getEnddate ());
            //把日期往后增加一天,整数  往后推,负数往前移动
            calendar.add (calendar.DATE, 1);
            //这个时间就是日期往后推一天的结果
            wxUser.setEnddate (calendar.getTime ());
        }

        if (!ShiroKit.getUser ().getUsername ().equals ("admin")) {
            wxUser.setRootInvitedCode (ShiroKit.getUser ().getInviteCode ());
        }

        Page<WxUser> page = wxUserService.listWxUserPage (wxUser);
        return R.ok (page);
    }

    @RequiresPermissions("func:wxUser:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        wxUserService.removeByIds (ids);
        return R.ok ();
    }

    @RequiresPermissions("func:wxUser:edit")
    @PostMapping(value = "edit")
    public R edit(WxUser wxUser) {
        //生成二维码
        try {
            wxUser.setQrcode (getQRCode (wxUser));
            wxUserService.updateById (wxUser);
            return R.ok ();
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @RequiresPermissions("func:wxUser:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        wxUserService.removeById (id);
        return R.ok ();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok (wxUserService.getById (id));
    }

    @GetMapping(value = "getLastPersons")
    public R getLastPersons() {
        List<WxUser> wxUsers = wxUserService.list (new QueryWrapper<WxUser> ()
                .orderByDesc ("create_time")
                .last ("limit 15"));
        return R.ok (wxUsers);
    }

    @PostMapping(value = "updateInfo")
    public R updateInfo(@RequestBody WxUser userInfo) {
        try {
            //只更新人员类别、电话、单位三个字段、更新时间
            userInfo.setUpdateTime (new Date ());
            wxUserService.updWxInfo (userInfo);
            return R.ok (userInfo);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    /**
     * 查看invitedCode是否存在
     *
     * @param inviteCode
     * @return
     */
    @GetMapping(value = "getParentWxInfo")
    public R getParentWxInfo(@RequestParam(required = false) String inviteCode) {
        if (StringUtils.isEmpty (inviteCode)) {
            return R.ok ();
        }

        try {
            List<WxUser> wxUsers = wxUserService.list (new LambdaQueryWrapper<WxUser> ()
                    .eq (WxUser::getInvitedCode, inviteCode));

            List<User> users = userService.list (new LambdaQueryWrapper<User> ()
                    .eq (User::getInviteCode, inviteCode));

            if ((wxUsers.size () == 0 && users.size () == 0)) {
                return R.ok ();
            } else {
                return R.ok ("successful!");
            }
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @GetMapping(value = "getWxInfo")
    public R getWxInfo(@RequestParam String openid) {
        try {
            WxUser wxUser = wxUserService.getOne (new QueryWrapper<WxUser> ().eq ("openid", openid));
            AnswerRecord answerRecord = answerRecordService.getOne (new QueryWrapper<AnswerRecord> ()
                    .eq ("userid", wxUser.getId ())
                    .orderByDesc ("score")
                    .last ("limit 1"));

            wxUser.setPrivilege (String.valueOf (answerRecord.getScore ()));
            return R.ok (wxUser);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @GetMapping(value = "getWxRank")
    public R getWxRank() {
        try {
            List<WxUser> wxUsers = wxUserService.list (new QueryWrapper<WxUser> ().
                    orderByDesc ("jf").last ("limit 20"));

            return R.ok (wxUsers);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @PostMapping(value = "submitTestdata")
    public R submit(@RequestParam String userInfo, @RequestParam String testData, @RequestParam Integer score) {
        try {
            // replaceAll 把所有的大写引号再替换回来
            WxUser wxUser = JSON.parseObject (userInfo.replaceAll ("PASSWWORD", "\""), WxUser.class);
            String parentInvitedCode = wxUser.getParentInvitedCode ();
            List<WxUser> wxUsers = wxUserService.list (new QueryWrapper<WxUser> ()
                    .eq ("openid", wxUser.getOpenid ()));

            if (StringUtils.isEmpty (parentInvitedCode)) {
                return R.fail ("父邀请码为空");
            }

            if (wxUsers.size () > 0) {
                //更新个人信息 年龄 性别 职业
                if (wxUser.getInterest () != null && wxUsers.get (0).getInterest () == null) {
                    wxUserService.update (new LambdaUpdateWrapper<WxUser> ()
                            .set (WxUser::getSex, wxUser.getSex ())
                            .set (WxUser::getAge, wxUser.getAge ())
                            .set (WxUser::getWork, wxUser.getWork ())
                            .set (WxUser::getSalary, wxUser.getSalary ())
                            .set (WxUser::getInterest, wxUser.getInterest ())
                            .eq (WxUser::getOpenid, wxUser.getOpenid ()));
                }
                wxUser = wxUsers.get (0);
            } else if (score >= 60) {
                if (wxUser.getParentInvitedCode ().startsWith ("F")) {
                    wxUser.setRootInvitedCode (wxUser.getParentInvitedCode ());
                } else {
                    WxUser parent = wxUserService.getOne (new LambdaQueryWrapper<WxUser> ()
                            .eq (WxUser::getInvitedCode, wxUser.getParentInvitedCode ()));
                    if (parent != null) {
                        wxUser.setRootInvitedCode (parent.getRootInvitedCode ());
                    }
                }
                wxUserService.save (wxUser);
            }

            /***************测试记录表**************/
            AnswerRecord answerRecord = new AnswerRecord ();
            if (score >= 60) {
                answerRecord.setUserid (wxUser.getId ());
            }
            answerRecord.setNickname (wxUser.getNickname ());
            answerRecord.setInviteCode (parentInvitedCode);
            answerRecord.setScore (score);
            answerRecord.setCorrectNum (score / 10);
            answerRecord.setQuestionNum (10);
            answerRecordService.save (answerRecord);

            if (wxUsers.size () == 0 && score >= 60) {
                //生成邀请码
                wxUser.setInvitedCode ("H" + (100000 + wxUser.getId ()));
                //生成二维码
                wxUser.setQrcode (getQRCode (wxUser));
                wxUserService.saveOrUpdate (wxUser);
            }

            /***************答题记录表****************/
            String json = testData.replaceAll ("PASSWWORD", "\"");
            List<QuestionBankVo> answerBankVos = JSON.parseArray (json, QuestionBankVo.class);
            List<TestPaper> testPapers = new ArrayList<> ();
            answerBankVos.forEach (o -> {
                TestPaper testPaper = new TestPaper ();
                testPaper.setRecordId (answerRecord.getId ());
                testPaper.setQuestionBankId (o.getId ());

                String answer = JSON.toJSONString (o.getAnswerDtos ());
                testPaper.setAnswer (answer);

                QuestionBank questionBank = questionBankService.getById (o.getId ());
                testPaper.setIsCorrect (answer.equals (questionBank.getAnswer ()));

                testPapers.add (testPaper);
            });

            testPaperService.saveBatch (testPapers);
            return R.ok (wxUser);

        } catch (Exception e) {
            e.printStackTrace ();
            return R.fail (e.getMessage ());
        }
    }

    private static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile (regEx);
        Matcher m = p.matcher (str);
        return m.replaceAll ("").trim ();
    }

    @SneakyThrows
    private String getQRCode(WxUser user) {
        /*******************生成反诈测试宣传二维码************************/
        String content = "http://abcdef.vaiwan.com/static/test.html?inviteCode=%s&realname=%s";
        content = String.format (content, user.getInvitedCode (), StringFilter (user.getNickname ()));

        String logoPath = "D:\\QRCode\\headImage\\" + user.getOpenid () + ".jpg";

        if (!TextUtils.isEmpty (user.getHeadimgurl ())) {
            try {
                HttpUtils.download (user.getHeadimgurl (), logoPath);
            } catch (Exception ex) {
                logoPath = "D:\\QRCode\\nhga.jpg";
            }
        } else {
            logoPath = "D:\\QRCode\\nhga.jpg";
        }

        // 二维码保存的路径
        String destPath = "D:\\QRCode\\";
        // 二维码的图片名
        String fileName = UUID.randomUUID ().toString ();

        String file;
        try {
            file = QRCodeUtils.encode (content, logoPath, destPath, fileName, true);
        } catch (Exception ex) {
            file = QRCodeUtils.encode (content, null, destPath, fileName, true);
        }
        return "/qrcode/" + file;
    }
}

