package com.kalvin.kvf.modules.func.schedule;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kalvin.kvf.common.utils.HttpUtils;
import com.kalvin.kvf.common.utils.QRCodeUtils;
import com.kalvin.kvf.modules.func.entity.*;
import com.kalvin.kvf.modules.func.service.*;
import com.kalvin.kvf.modules.sys.entity.Dept;
import com.kalvin.kvf.modules.sys.entity.User;
import com.kalvin.kvf.modules.sys.mapper.DeptMapper;
import com.kalvin.kvf.modules.sys.mapper.UserMapper;
import lombok.SneakyThrows;
import org.apache.http.util.TextUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Component
@EnableScheduling
public class ScheduledConfig implements SchedulingConfigurer {
    @Resource
    private UserMapper userMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private WxUserService wxUserService;

    @Resource
    private PesonStatisticsService pesonStatisticsService;

    @Resource
    private TempDeptStatisticsService tempDeptStatisticsService;

    @Resource
    private TempMemberStatisticsService tempMemberStatisticsService;

    @Resource
    private LastdayDeptStatisticsService lastdayDeptStatisticsService;

    @Resource
    private LastdayMemberStatisticsService lastdayMemberStatisticsService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //参数传入一个size为10的线程池
        scheduledTaskRegistrar.setScheduler (Executors.newScheduledThreadPool (10));
    }

    /**
     * 镇街道数据统计
     */
    @Scheduled(fixedDelay = 300000)
    public void schedule_1() {
        try {
            List<Dept> depts = deptMapper.selectList (new QueryWrapper<Dept> ()
                    .between ("id", 4, 14));

            List<TempDeptStatistics> list = new ArrayList<> ();

            for (Dept dept : depts) {
                List<User> users = userMapper.selectList (new QueryWrapper<User> ()
                        .eq ("dept_id", dept.getId ()));

                //推广人数
                int personCount = 0;
                //积分
                float allScores = 0;

                for (User user : users) {
                    pesonStatisticsService.createDeptTempTable (user.getInviteCode ());
                    List<LevelCount> levelCounts = pesonStatisticsService.getDeptLevelCount ();

                    for (int i = 0; i < levelCounts.size (); i++) {
                        personCount += levelCounts.get (i).getCount ();

                        switch (levelCounts.get (i).getNLevel ()) {
                            case 1:
                                allScores += levelCounts.get (i).getCount ();
                                break;
                            case 2:
                                allScores += levelCounts.get (i).getCount () * 0.3;
                                break;
                            case 3:
                                allScores += levelCounts.get (i).getCount () * 0.1;
                                break;
                            default:
                                break;
                        }
                    }
                }

                TempDeptStatistics tempDeptStatistics = new TempDeptStatistics ();
                tempDeptStatistics.setDeptid (dept.getId ());
                tempDeptStatistics.setDeptname (dept.getName ());
                tempDeptStatistics.setPersonCount (personCount);
                tempDeptStatistics.setAllScores (allScores);

                list.add (tempDeptStatistics);
            }

            tempDeptStatisticsService.saveOrUpdateBatch (list);
        } catch (Exception ex) {
            System.out.println (ex.getMessage ());
        }
    }

    @Scheduled(cron = "1 0 0 * * ?")
    public void schedule_2() {
        try {
            List<TempDeptStatistics> tempDeptStatistics = tempDeptStatisticsService.list ();
            String jsonstr = JSONObject.toJSONString (tempDeptStatistics);

            List<LastdayDeptStatistics> lastdayDeptStatistics = JSONObject.parseArray (jsonstr, LastdayDeptStatistics.class);

            lastdayDeptStatisticsService.saveOrUpdateBatch (lastdayDeptStatistics);
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
    }

    /**
     * 成员单位数据统计
     */
    @Scheduled(fixedDelay = 300000)
    public void schedule_3() {
        try {
            List<Dept> depts = deptMapper.selectList (new QueryWrapper<Dept> ()
                    .eq ("parent_id", 16));

            List<TempMemberStatistics> list = new ArrayList<> ();

            for (Dept dept : depts) {
                List<User> users = userMapper.selectList (new QueryWrapper<User> ()
                        .eq ("dept_id", dept.getId ()));

                //推广人数
                int personCount = 0;
                //积分
                float allScores = 0;

                for (User user : users) {
                    pesonStatisticsService.createMemberTempTable (user.getInviteCode ());
                    List<LevelCount> levelCounts = pesonStatisticsService.getMemberLevelCount ();

                    for (int i = 0; i < levelCounts.size (); i++) {
                        personCount += levelCounts.get (i).getCount ();

                        switch (levelCounts.get (i).getNLevel ()) {
                            case 1:
                                allScores += levelCounts.get (i).getCount ();
                                break;
                            case 2:
                                allScores += levelCounts.get (i).getCount () * 0.3;
                                break;
                            case 3:
                                allScores += levelCounts.get (i).getCount () * 0.1;
                                break;
                            default:
                                break;
                        }
                    }
                }

                TempMemberStatistics tempMemberStatistics = new TempMemberStatistics ();
                tempMemberStatistics.setDeptid (dept.getId ());
                tempMemberStatistics.setDeptname (dept.getName ());
                tempMemberStatistics.setPersonCount (personCount);
                tempMemberStatistics.setAllScores (allScores);

                list.add (tempMemberStatistics);
            }

            tempMemberStatisticsService.saveOrUpdateBatch (list);
        } catch (Exception ex) {
            System.out.println (ex.getMessage ());
        }
    }

    @Scheduled(cron = "1 0 0 * * ?")
    public void schedule_4() {
        try {
            List<TempMemberStatistics> tempMemberStatistics = tempMemberStatisticsService.list ();
            String jsonstr = JSONObject.toJSONString (tempMemberStatistics);

            List<LastdayMemberStatistics> lastdayMemberStatistics = JSONObject.parseArray (jsonstr, LastdayMemberStatistics.class);

            lastdayMemberStatisticsService.saveOrUpdateBatch (lastdayMemberStatistics);
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
    }

//    @Scheduled(fixedDelay = 300000)
    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void schedule_xx() {
        List<WxUser> wxUsers = wxUserService.list (new LambdaQueryWrapper<WxUser> ()
                .isNull (WxUser::getInvitedCode));

        for (WxUser wxUser : wxUsers) {
            //生成二维码
            try {
                wxUser.setInvitedCode ("H" + (100000 + wxUser.getId ()));
                wxUser.setQrcode (getQRCode (wxUser));
                wxUserService.saveOrUpdate (wxUser);
            } catch (Exception ex) {

                ex.printStackTrace ();
            }
        }

        System.out.println ("-----------------invitedCode null 检测结束!-------------------------");
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
        String content = "http://msfz.nhgaj.com/static/test.html?inviteCode=%s&realname=%s";
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
            file = QRCodeUtils.encode (content,null, destPath, fileName, true);
        }
        return "/qrcode/" + file;
    }
}
