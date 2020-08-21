package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.*;
import com.kalvin.kvf.modules.func.mapper.AnswerRecordMapper;
import com.kalvin.kvf.modules.func.mapper.J7rZhbiaoStatisticsMapper;
import com.kalvin.kvf.modules.func.mapper.WxUserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("func/home")
public class HomeController extends BaseController {
    @Resource
    private WxUserMapper wxUserMapper;

    @Resource
    private AnswerRecordMapper answerRecordMapper;

    @Resource
    private J7rZhbiaoStatisticsMapper j7rZhbiaoStatisticsMapper;

    @GetMapping(value = "getAll")
    public R getAll() {
        try {
            int zcrs = wxUserMapper.selectCount (null);
            int dtrc = answerRecordMapper.selectCount (null);

            int wfyqm = wxUserMapper.selectCount (new LambdaQueryWrapper<WxUser> ()
                    .eq (WxUser::getParentInvitedCode, null)
                    .or ()
                    .eq (WxUser::getParentInvitedCode, ""));

            int wyqm = wxUserMapper.selectCount (new LambdaQueryWrapper<WxUser> ()
                    .eq (WxUser::getInvitedCode, null)
                    .or ()
                    .eq (WxUser::getInvitedCode, ""));

            TongJi tongJi = new TongJi ();
            tongJi.setZcrs (zcrs);
            tongJi.setDtrc (dtrc);
            tongJi.setWfyqm (wfyqm);
            tongJi.setWyqm (wyqm);
            return R.ok (tongJi);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @GetMapping(value = "getToday")
    public R getToday() {
        int zcrs = wxUserMapper.selectCount (new LambdaQueryWrapper<WxUser> ()
                .apply ("date(create_time) = curdate()"));

        int dtrc = answerRecordMapper.selectCount (new LambdaQueryWrapper<AnswerRecord> ()
                .apply ("date(create_time) = curdate()"));

        int wfyqm = wxUserMapper.selectCount (new LambdaQueryWrapper<WxUser> ()
                .eq (WxUser::getParentInvitedCode, null)
                .or ()
                .eq (WxUser::getParentInvitedCode, ""));

        int wyqm = wxUserMapper.selectCount (new LambdaQueryWrapper<WxUser> ()
                .eq (WxUser::getInvitedCode, null)
                .or ()
                .eq (WxUser::getInvitedCode, ""));

        TongJi tongJi = new TongJi ();
        tongJi.setZcrs (zcrs);
        tongJi.setDtrc (dtrc);
        tongJi.setWfyqm (wfyqm);
        tongJi.setWyqm (wyqm);
        return R.ok (tongJi);
    }

    @GetMapping(value = "getLast7days")
    public R getLast7days() {
        try {
            List<J7rline> j7rlines = new ArrayList<> ();

            J7rline zcrs = getJ7rline ("注册人数");
            J7rline dtrc = getJ7rline ("答题人次");
            J7rline wfyqm = getJ7rline ("无父邀请码");
            J7rline wyqm = getJ7rline ("无邀请码");

            j7rlines.add (zcrs);
            j7rlines.add (dtrc);
            j7rlines.add (wfyqm);
            j7rlines.add (wyqm);

            return R.ok (j7rlines);
        } catch (Exception ex) {
            return R.ok (ex.getMessage ());
        }
    }

    /**
     * 获取某类别下近七日数据
     *
     * @param type
     * @return
     */
    J7rline getJ7rline(String type) {
        List<J7rZhbiaoStatistics> zcrs = j7rZhbiaoStatisticsMapper.selectList (
                new LambdaQueryWrapper<J7rZhbiaoStatistics> ()
                        .eq (J7rZhbiaoStatistics::getType, type)
                        .orderByAsc (J7rZhbiaoStatistics::getTjrq)
        );

        J7rline j7rline = new J7rline ();
        j7rline.setName (type);
        j7rline.setType ("line");

        Integer[] arr = new Integer[zcrs.size ()];
        for (int i = 0; i < zcrs.size (); i++) {
            arr[i] = zcrs.get (i).getAddCount ();
        }
        j7rline.setData (arr);

        return j7rline;
    }
}
