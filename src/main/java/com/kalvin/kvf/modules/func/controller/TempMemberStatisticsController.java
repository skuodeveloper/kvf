package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class TempMemberStatisticsController extends BaseController {

}

