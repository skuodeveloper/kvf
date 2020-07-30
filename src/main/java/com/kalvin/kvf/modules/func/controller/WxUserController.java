package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.common.utils.HttpUtils;
import com.kalvin.kvf.common.utils.QRCodeUtils;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.entity.TestPaper;
import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.func.service.AnswerRecordService;
import com.kalvin.kvf.modules.func.service.QuestionBankService;
import com.kalvin.kvf.modules.func.service.TestPaperService;
import com.kalvin.kvf.modules.func.service.WxUserService;
import com.kalvin.kvf.modules.func.vo.QuestionBankVo;
import lombok.SneakyThrows;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


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
        Page<WxUser> page = wxUserService.listWxUserPage (wxUser);

        return R.ok (page);
    }

    @RequiresPermissions("func:wxUser:add")
    @PostMapping(value = "add")
    public R add(WxUser wxUser) {
        wxUserService.save (wxUser);
        return R.ok ();
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
        wxUserService.updateById (wxUser);
        return R.ok ();
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
            // replaceAll 把所有的大写引号再替换回来
//            WxUser wxUser = JSON.parseObject (userInfo.replaceAll ("PASSWWORD", "\""), WxUser.class);
            wxUserService.saveOrUpdate (userInfo);
            return R.ok (userInfo);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @GetMapping(value = "getWxInfo")
    public R getWxInfo(@RequestParam String openid) {
        try {
            WxUser wxUser = wxUserService.getOne (new QueryWrapper<WxUser> ().eq ("openid", openid));
            AnswerRecord answerRecord = answerRecordService.getOne (new QueryWrapper<AnswerRecord> ()
                    .eq ("userid",wxUser.getId ())
                    .orderByDesc ("score")
                    .last ("limit 1"));

            wxUser.setPrivilege (String.valueOf (answerRecord.getScore ()));
            return R.ok (wxUser);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @PostMapping(value = "submitTestdata")
    public R submit(@RequestParam String userInfo, @RequestParam String testData, @RequestParam Integer score) {
        try {
            // replaceAll 把所有的大写引号再替换回来
            WxUser wxUser = JSON.parseObject (userInfo.replaceAll ("PASSWWORD", "\""), WxUser.class);
            WxUser isWxUser = wxUserService.getOne (new QueryWrapper<WxUser> ().eq ("openid", wxUser.getOpenid ()));
            if (isWxUser != null) {
                wxUser = isWxUser;
            } else if (score >= 60) {
                wxUserService.save (wxUser);
                //生成邀请码
                wxUser.setInvitedCode ("H" + (100000 + wxUser.getId ()));
                //生成二维码
                wxUser.setQrcode (getQRCode (wxUser));
                wxUserService.saveOrUpdate (wxUser);
            }

            /***************测试记录表**************/
            String json = testData.replaceAll ("PASSWWORD", "\"");
            System.out.println (json);

            List<QuestionBankVo> answerBankVos = JSON.parseArray (json, QuestionBankVo.class);

            AnswerRecord answerRecord = new AnswerRecord ();
            answerRecord.setUserid (wxUser.getId ());
            answerRecord.setNickname (wxUser.getNickname ());
            answerRecord.setScore (score);
            answerRecord.setInviteCode (wxUser.getParentInvitedCode ());
            answerRecord.setCorrectNum (score / 10);
            answerRecord.setQuestionNum (10);

            answerRecordService.save (answerRecord);

            /***************答题记录表****************/
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

    @SneakyThrows
    private String getQRCode(WxUser user) {
        /*******************生成反诈测试宣传二维码************************/
//        String content = "http://abcdef.vaiwan.com/static/test.html?inviteCode=%s&realname=%s";
//        content = String.format (content, user.getInvitedCode (), user.getNickname ());
//        String path = "F:/QRCode/";// 二维码保存的路径
//        String codeName = user.getInvitedCode () + "-" + user.getNickname ();//UUID.randomUUID ().toString ();// 二维码的图片名
//        String imageType = "jpg";// 图片类型
//        MultiFormatWriter multiFormatWriter = new MultiFormatWriter ();
//        Map<EncodeHintType, String> hints = new HashMap<> ();
//        hints.put (EncodeHintType.CHARACTER_SET, "UTF-8");
//        BitMatrix bitMatrix = null;
//        try {
//            bitMatrix = multiFormatWriter.encode (content, BarcodeFormat.QR_CODE, 400, 400, hints);
//            File file1 = new File (path, codeName + "." + imageType);
//            QRCodeUtil.writeToFile (bitMatrix, imageType, file1);
//        } catch (Exception e) {
//            e.printStackTrace ();
//        }
//
//        return "/qrcode/" + codeName  + "." + imageType;

        /*******************生成反诈测试宣传二维码************************/
        String content = "http://abcdef.vaiwan.com/static/test.html?inviteCode=%s&realname=%s";
        content = String.format (content, user.getInvitedCode (), user.getNickname ());

        String logoPath = "D:\\QRCode\\headImage\\" + user.getOpenid () + ".jpg";
        HttpUtils.download (user.getHeadimgurl (), logoPath);

//        String logoPath = "D:\\QRCode\\nhga.jpg";
        String destPath = "D:\\QRCode\\";// 二维码保存的路径
        String fileName = user.getInvitedCode () + "-" + user.getNickname ();//UUID.randomUUID ().toString ();// 二维码的图片名

        return "/qrcode/" + QRCodeUtils.encode (content, logoPath, destPath, fileName, true);
    }
}

