package com.kalvin.kvf.modules.func.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalvin.kvf.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @since 2020-08-05 14:10:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("func_temp_dept_statistics")
public class TempDeptStatistics extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId(value = "deptid",type = IdType.INPUT)
    private Long deptid;

    /**
     *
     */
    private String deptname;

    /**
     *
     */
    private Integer personCount;

    /**
     *
     */
    private Float allScores;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
