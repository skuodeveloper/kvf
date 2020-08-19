package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.modules.func.entity.*;
import com.kalvin.kvf.modules.func.mapper.WxUserMapper;
import com.kalvin.kvf.modules.func.service.TempMemberStatisticsService;
import com.kalvin.kvf.modules.func.vo.TempDeptStatisticsVo;
import com.kalvin.kvf.modules.func.vo.TempMemberStatisticsVo;
import com.kalvin.kvf.modules.sys.entity.Dept;
import com.kalvin.kvf.modules.sys.entity.User;
import com.kalvin.kvf.modules.sys.mapper.DeptMapper;
import com.kalvin.kvf.modules.sys.mapper.UserMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.TempMemberStatistics;
import com.kalvin.kvf.modules.func.service.TempMemberStatisticsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2020-08-06 13:28:42
 */
@RestController
@RequestMapping("func/tempMemberStatistics")
public class TempMemberStatisticsController extends BaseCrudRestController {
    @Resource
    private UserMapper userMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private WxUserMapper wxUserMapper;

    @Autowired
    private TempMemberStatisticsService tempMemberStatisticsService;

    /**
     * 获取部门推广人数排名
     */
    @GetMapping(value = "getMemberRank")
    public R getMemberRank() {
        try {
            List<TempMemberStatistics> tempMemberStatistics = tempMemberStatisticsService.list (
                    new QueryWrapper<TempMemberStatistics> ()
                            .orderByDesc ("person_count")
            );

            List<TempMemberStatisticsVo> tempMemberStatisticsVos = super.convertToVoAndBindRelations (tempMemberStatistics, TempMemberStatisticsVo.class);

            int allCnt = 0, todayCnt = 0;
            for (TempMemberStatisticsVo t : tempMemberStatisticsVos) {
                allCnt += t.getPersonCount ();
                todayCnt += t.getLastdayMemberStatistics ().getPersonCount ();
            }

            MemberRank MemberRank = new MemberRank ();
            MemberRank.setTempMemberStatisticsVos (tempMemberStatisticsVos);
            MemberRank.setAllCount (allCnt);
            MemberRank.setTodayCount (todayCnt);

            return R.ok (MemberRank);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    /**
     * 获取部门推广人数排名
     */
    @GetMapping(value = "getMemberRankNew")
    public R getMemberRankNew() {
        try {
            List<Dept> depts = deptMapper.selectList (new QueryWrapper<Dept> ()
                    .eq ("parent_id", 16));

            List<TempMemberStatistics> list = new ArrayList<> ();

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

                TempMemberStatistics tempMemberStatistics = new TempMemberStatistics ();
                tempMemberStatistics.setDeptid (dept.getId ());
                tempMemberStatistics.setDeptname (dept.getName ());
                tempMemberStatistics.setPersonCount (personCount);

                list.add (tempMemberStatistics);
            }

            list.sort (Comparator.comparing (TempMemberStatistics::getPersonCount).reversed());
            List<TempMemberStatisticsVo> tempMemberStatisticsVos = super.convertToVoAndBindRelations (list, TempMemberStatisticsVo.class);

            int allCnt = 0, todayCnt = 0;
            for (TempMemberStatisticsVo t : tempMemberStatisticsVos) {
                allCnt += t.getPersonCount ();
                todayCnt += t.getLastdayMemberStatistics ().getPersonCount ();
            }

            MemberRank MemberRank = new MemberRank ();
            MemberRank.setTempMemberStatisticsVos (tempMemberStatisticsVos);
            MemberRank.setAllCount (allCnt);
            MemberRank.setTodayCount (todayCnt);

            return R.ok (MemberRank);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }
}

