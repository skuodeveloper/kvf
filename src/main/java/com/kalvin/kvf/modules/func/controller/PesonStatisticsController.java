package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.LevelCount;
import com.kalvin.kvf.modules.func.entity.PesonStatistics;
import com.kalvin.kvf.modules.func.service.PesonStatisticsService;
import com.kalvin.kvf.modules.sys.entity.User;
import com.kalvin.kvf.modules.sys.mapper.UserMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Resource
    private UserMapper userMapper;

    @RequiresPermissions("func:pesonStatistics:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView ("func/pesonStatistics");
    }


    @GetMapping(value = "list/data")
    public R listData(PesonStatistics pesonStatistics) {
        try {

            List<User> users = userMapper.selectList (new QueryWrapper<> ());
            for (User user : users) {
                pesonStatisticsService.createTempTable (user.getInviteCode ());
                List<LevelCount> levelCounts = pesonStatisticsService.getLevelCount ();
                System.out.println ("aaa");
            }

            Page<PesonStatistics> page = pesonStatisticsService.listPesonStatisticsPage (pesonStatistics);
            return R.ok (page);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }
}

