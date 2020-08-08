package com.kalvin.kvf.modules.func.entity;

import com.kalvin.kvf.modules.func.vo.TempDeptStatisticsVo;
import com.kalvin.kvf.modules.func.vo.TempMemberStatisticsVo;
import lombok.Data;

import java.util.List;

@Data
public class MemberRank {
    private List<TempMemberStatisticsVo> tempMemberStatisticsVos;
    private Integer allCount;
    private Integer todayCount;
}
