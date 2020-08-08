package com.kalvin.kvf.modules.func.vo;

import com.diboot.core.binding.annotation.BindEntity;
import com.kalvin.kvf.modules.func.entity.LastdayDeptStatistics;
import com.kalvin.kvf.modules.func.entity.LastdayMemberStatistics;
import com.kalvin.kvf.modules.func.entity.TempDeptStatistics;
import com.kalvin.kvf.modules.func.entity.TempMemberStatistics;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TempMemberStatisticsVo extends TempMemberStatistics {
    @BindEntity(entity = LastdayMemberStatistics.class, condition="this.deptid=deptid")
    private LastdayMemberStatistics lastdayMemberStatistics;
}
