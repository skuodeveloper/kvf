package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.TestPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 答题试卷 Mapper 接口
 * </p>
 * @since 2020-07-24 11:00:26
 */
public interface TestPaperMapper extends BaseMapper<TestPaper> {

    /**
     * 查询列表(分页)
     * @param testPaper 查询参数
     * @param page 分页参数
     * @return list
     */
    List<TestPaper> selectTestPaperList(@Param ("testPaper") TestPaper testPaper, IPage page);

}
