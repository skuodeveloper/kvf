package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.DeptRank;
import com.kalvin.kvf.modules.func.entity.TempDeptStatistics;
import com.kalvin.kvf.modules.func.mapper.WxUserMapper;
import com.kalvin.kvf.modules.func.service.TempDeptStatisticsService;
import com.kalvin.kvf.modules.func.vo.TempDeptStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

            List<TempDeptStatisticsVo> tempDeptStatisticsVos = super.convertToVoAndBindRelations (tempDeptStatistics, TempDeptStatisticsVo.class);

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

