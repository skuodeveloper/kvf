package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.PersonTypeCount;
import com.kalvin.kvf.modules.func.mapper.PersonTypeStatisticMapper;
import com.kalvin.kvf.modules.sys.entity.User;
import com.kalvin.kvf.modules.sys.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2020-08-12 16:31:03
 */
@RestController
@RequestMapping("func/personTypeStatistic")
public class PersonTypeStatisticController extends BaseController {
    @Resource
    UserMapper userMapper;

    @Resource
    private PersonTypeStatisticMapper personTypeStatisticMapper;

    @GetMapping(value = "getPersonType")
    public R getPersonType() {
        try {
            List<PersonTypeCount> personTypeCounts = personTypeStatisticMapper.getPersonType();

            return R.ok (personTypeCounts);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @GetMapping(value = "getPersonTypeByDept")
    public R getPersonTypeByDept(@RequestParam("deptid") Long deptid) {
        try {
            List<User> users = userMapper.selectList (new LambdaQueryWrapper<User> ()
                    .eq (User::getDeptId, deptid));

            List<String> listCodes = new ArrayList<> ();
            for (User user : users) {
                listCodes.add (user.getInviteCode ());
            }

            String inviteCodes = StringUtils.join (listCodes, ",");
            List<PersonTypeCount> personTypeCounts = personTypeStatisticMapper.getPersonTypeByDept (inviteCodes);

            return R.ok (personTypeCounts);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }
}

