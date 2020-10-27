package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.modules.func.entity.TestPaper;
import com.kalvin.kvf.modules.func.service.TestPaperService;
import com.kalvin.kvf.modules.func.vo.AnswerRecordVo;
import com.kalvin.kvf.modules.func.vo.TestPaperVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;
import com.kalvin.kvf.modules.func.service.AnswerRecordService;

import java.util.List;


/**
 * <p>
 * 答题得分记录 前端控制器
 * </p>
 * @since 2020-07-23 19:10:35
 */
@RestController
@RequestMapping("func/answerRecord")
public class AnswerRecordController extends BaseCrudRestController {
@Autowired
    private TestPaperService testPaperService;

    @Autowired
    private AnswerRecordService answerRecordService;

    @RequiresPermissions("func:answerRecord:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/answerRecord");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/answerRecord_edit");
        mv.addObject("recordId", id);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(AnswerRecord answerRecord) {
        Page<AnswerRecord> page = answerRecordService.listAnswerRecordPage(answerRecord);
        return R.ok(page);
    }

    @GetMapping(value = "list/data/{recordId}")
    public R listData(@PathVariable Long recordId, TestPaper testPaper) {
        try {
            testPaper.setRecordId (recordId);
            Page<TestPaper> page = testPaperService.listTestPaperPage (testPaper);

            List<TestPaperVo> testPaperVos = super.convertToVoAndBindRelations (page.getRecords (), TestPaperVo.class);

            Page<TestPaperVo> pageVo = new Page<> ();
            pageVo.setCurrent (page.getCurrent ());
            pageVo.setTotal (page.getTotal ());
            pageVo.setSize (page.getSize ());
            pageVo.setRecords (testPaperVos);
            return R.ok (pageVo);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @RequiresPermissions("func:answerRecord:add")
    @PostMapping(value = "add")
    public R add(AnswerRecord answerRecord) {
        answerRecordService.save(answerRecord);
        return R.ok();
    }

    @RequiresPermissions("func:answerRecord:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        answerRecordService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:answerRecord:edit")
    @PostMapping(value = "edit")
    public R edit(AnswerRecord answerRecord) {
        answerRecordService.updateById(answerRecord);
        return R.ok();
    }

    @RequiresPermissions("func:answerRecord:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        answerRecordService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(answerRecordService.getById(id));
    }

}

