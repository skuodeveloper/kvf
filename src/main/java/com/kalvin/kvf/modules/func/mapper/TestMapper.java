package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试表 Mapper 接口
 * </p>
 * @since 2020-05-09 10:57:42
 */
public interface TestMapper extends BaseMapper<Test> {

    /**
     * 查询列表(分页)
     * @param test 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Test> selectTestList(@Param("test") Test test, IPage page);

}
