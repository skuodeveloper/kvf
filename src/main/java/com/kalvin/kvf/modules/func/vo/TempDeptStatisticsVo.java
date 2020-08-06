package com.kalvin.kvf.modules.func.vo;

import com.diboot.core.binding.annotation.BindEntity;
import com.kalvin.kvf.modules.func.entity.LastdayDeptStatistics;
import com.kalvin.kvf.modules.func.entity.TempDeptStatistics;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TempDeptStatisticsVo extends TempDeptStatistics{
    @BindEntity(entity = LastdayDeptStatistics.class, condition="this.deptid=deptid")
    private LastdayDeptStatistics lastdayDeptStatistics;
}
