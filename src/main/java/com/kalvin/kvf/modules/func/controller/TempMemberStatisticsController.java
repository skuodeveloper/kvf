package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.modules.func.entity.MemberRank;
import com.kalvin.kvf.modules.func.entity.TempMemberStatistics;
import com.kalvin.kvf.modules.func.service.TempMemberStatisticsService;
import com.kalvin.kvf.modules.func.vo.TempMemberStatisticsVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.TempMemberStatistics;
import com.kalvin.kvf.modules.func.service.TempMemberStatisticsService;

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
}

