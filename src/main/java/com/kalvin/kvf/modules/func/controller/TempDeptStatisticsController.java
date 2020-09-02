package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.DeptRank;
import com.kalvin.kvf.modules.func.entity.TempDeptStatistics;
import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.func.entity.ZjdData;
import com.kalvin.kvf.modules.func.mapper.WxUserMapper;
import com.kalvin.kvf.modules.func.service.TempDeptStatisticsService;
import com.kalvin.kvf.modules.func.vo.TempDeptStatisticsVo;
import com.kalvin.kvf.modules.sys.entity.Dept;
import com.kalvin.kvf.modules.sys.entity.User;
import com.kalvin.kvf.modules.sys.mapper.DeptMapper;
import com.kalvin.kvf.modules.sys.mapper.UserMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2020-08-05 14:10:48
 */
@RestController
@RequestMapping("func/tempDeptStatistics")
public class TempDeptStatisticsController extends BaseCrudRestController {
    @Resource
    private UserMapper userMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private WxUserMapper wxUserMapper;

    @Autowired
    private TempDeptStatisticsService tempDeptStatisticsService;

    /**
     * 获取部门推广人数排名
     */
    @GetMapping(value = "getDeptRank")
    public R getDeptRank() {
        try {
            List<TempDeptStatistics> tempDeptStatistics = tempDeptStatisticsService.list (
                    new QueryWrapper<TempDeptStatistics> ()
                            .orderByDesc ("person_count")
            );

            InputStream inputStream = this.getClass ().getResourceAsStream ("/static/js/nhq.json");
            String text = IOUtils.toString (inputStream, "utf8");
            List<ZjdData> zjdDatas = JSON.parseArray (text, ZjdData.class);

            List<TempDeptStatisticsVo> tempDeptStatisticsVos = super.convertToVoAndBindRelations (tempDeptStatistics, TempDeptStatisticsVo.class);

            int allCnt = 0, todayCnt = 0;
            for (TempDeptStatisticsVo t : tempDeptStatisticsVos) {
                allCnt += t.getPersonCount ();
                todayCnt += t.getLastdayDeptStatistics ().getPersonCount ();

                for (ZjdData z : zjdDatas) {
                    if (t.getDeptname ().substring (0, 2).equals (z.getDwmc ().substring (0, 2))) {
                        Float fgl = new BigDecimal ((float) t.getPersonCount () / z.getTotal () * 100).setScale (2, BigDecimal.ROUND_HALF_UP).floatValue ();
                        t.setFgl (fgl);
                    }
                }
            }

            DeptRank deptRank = new DeptRank ();
            deptRank.setTempDeptStatisticsVos (tempDeptStatisticsVos);
            deptRank.setAllCount (allCnt);
            deptRank.setTodayCount (todayCnt);

            return R.ok (deptRank);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    /**
     * 获取部门推广人数排名
     */
    @GetMapping(value = "getDeptRankNew")
    public R getDeptRankNew() {
        try {
            List<Dept> depts = deptMapper.selectList (new QueryWrapper<Dept> ()
                    .between ("id", 4, 14));

            List<TempDeptStatistics> list = new ArrayList<> ();

            for (Dept dept : depts) {
                List<User> users = userMapper.selectList (new QueryWrapper<User> ()
                        .eq ("dept_id", dept.getId ()));

                //推广人数
                int personCount = 0;

                for (User user : users) {
                    int wxUserCount = wxUserMapper.selectCount (new LambdaQueryWrapper<WxUser> ()
                            .eq (WxUser::getRootInvitedCode, user.getInviteCode ()));

                    personCount += wxUserCount;
                }

                TempDeptStatistics tempDeptStatistics = new TempDeptStatistics ();
                tempDeptStatistics.setDeptid (dept.getId ());
                tempDeptStatistics.setDeptname (dept.getName ());
                tempDeptStatistics.setPersonCount (personCount);

                list.add (tempDeptStatistics);
            }

            list.sort (Comparator.comparing (TempDeptStatistics::getPersonCount).reversed ());
            List<TempDeptStatisticsVo> tempDeptStatisticsVos = super.convertToVoAndBindRelations (list, TempDeptStatisticsVo.class);

            int allCnt = 0, todayCnt = 0;
            for (TempDeptStatisticsVo t : tempDeptStatisticsVos) {
                allCnt += t.getPersonCount ();
                todayCnt += t.getLastdayDeptStatistics ().getPersonCount ();
            }

            DeptRank deptRank = new DeptRank ();
            deptRank.setTempDeptStatisticsVos (tempDeptStatisticsVos);
            deptRank.setAllCount (allCnt);
            deptRank.setTodayCount (todayCnt);

            return R.ok (deptRank);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }
}

