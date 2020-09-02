package com.kalvin.kvf.modules.func.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kalvin.kvf.modules.func.vo.TempDeptStatisticsVo;
import lombok.Data;

import java.util.List;

@Data
public class DeptRank  {
    private List<TempDeptStatisticsVo> tempDeptStatisticsVos;
    private Integer allCount;
    private Integer todayCount;
}
