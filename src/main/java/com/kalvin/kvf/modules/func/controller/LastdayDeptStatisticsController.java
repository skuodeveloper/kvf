package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.LastdayDeptStatistics;
import com.kalvin.kvf.modules.func.service.LastdayDeptStatisticsService;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2020-08-06 08:29:07
 */
@RestController
@RequestMapping("func/lastdayDeptStatistics")
public class LastdayDeptStatisticsController extends BaseController {

}

