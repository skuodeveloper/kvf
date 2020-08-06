package com.kalvin.kvf.modules.func.schedule;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kalvin.kvf.modules.func.entity.LastdayDeptStatistics;
import com.kalvin.kvf.modules.func.entity.LevelCount;
import com.kalvin.kvf.modules.func.entity.TempDeptStatistics;
import com.kalvin.kvf.modules.func.service.LastdayDeptStatisticsService;
import com.kalvin.kvf.modules.func.service.PesonStatisticsService;
import com.kalvin.kvf.modules.func.service.TempDeptStatisticsService;
import com.kalvin.kvf.modules.sys.entity.Dept;
import com.kalvin.kvf.modules.sys.entity.User;
import com.kalvin.kvf.modules.sys.mapper.DeptMapper;
import com.kalvin.kvf.modules.sys.mapper.UserMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Component
@EnableScheduling
public class ScheduledConfig implements SchedulingConfigurer {
    @Resource
    private UserMapper userMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private PesonStatisticsService pesonStatisticsService;

    @Resource
    private TempDeptStatisticsService tempDeptStatisticsService;

    @Resource
    private LastdayDeptStatisticsService lastdayDeptStatisticsService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //参数传入一个size为10的线程池
        scheduledTaskRegistrar.setScheduler (Executors.newScheduledThreadPool (10));
    }

    @Scheduled(fixedDelay = 10)
    public void schedule_1() {
        try {
            System.out.println ("dept 数据统计开始!");

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

            System.out.println ("dept 数据统计结束!");
        } catch (Exception ex) {
            System.out.println ("dept 数据统计出现错误!");
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
}
