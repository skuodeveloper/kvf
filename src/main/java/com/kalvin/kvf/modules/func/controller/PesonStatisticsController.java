package com.kalvin.kvf.modules.func.controller;

import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.LevelCount;
import com.kalvin.kvf.modules.func.entity.PesonStatistics;
import com.kalvin.kvf.modules.func.entity.Statistics;
import com.kalvin.kvf.modules.func.service.PesonStatisticsService;
import com.kalvin.kvf.modules.sys.mapper.DeptMapper;
import com.kalvin.kvf.modules.sys.mapper.UserMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 人员积分统计 前端控制器
 * </p>
 *
 * @since 2020-07-27 16:13:18
 */
@RestController
@RequestMapping("func/pesonStatistics")
public class PesonStatisticsController extends BaseController {

    @Resource
    private PesonStatisticsService pesonStatisticsService;


    @RequiresPermissions("func:pesonStatistics:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView ("func/pesonStatistics");
    }


    @GetMapping(value = "getSubLevel")
    public R getSubLevel(String inviteCode) {
        try {
            pesonStatisticsService.createTempTable (inviteCode);
            List<LevelCount> levelCounts = pesonStatisticsService.getLevelCount (inviteCode);

            return R.ok (levelCounts);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    /**
     * 获取推广人数
     */
    @GetMapping(value = "getPersonStatisticsold")
    public R getPersonStatistics_old(@RequestParam String inviteCode) {
        try {
//            pesonStatisticsService.createTempTable (inviteCode);
//            List<LevelCount> levelCounts = pesonStatisticsService.getLevelCount (inviteCode);
//            pesonStatisticsService.deleteTempInvited (inviteCode);

            //改成一下是为了transmit
            List<LevelCount> levelCounts = pesonStatisticsService.getLevelCounts (inviteCode);

            int levelCount = levelCounts.size () >= 3 ? 3 : levelCounts.size ();
            //推广人数
            int personCount = 0;
            //积分
            float allScores = 0;
            for (int i = 0; i < levelCount; i++) {
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

            Statistics statistics = new Statistics ();
            statistics.setInviteCode (inviteCode);
            statistics.setPersonCount (personCount);
            statistics.setAllScores (allScores);

            return R.ok (statistics);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    /**
     * 获取推广人数
     */
    @GetMapping(value = "getPersonStatistics")
    public R getPersonStatistics(@RequestParam String inviteCode) {
        try {
            return R.ok ();
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }
}

