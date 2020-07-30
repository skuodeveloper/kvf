package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.modules.func.vo.AnswerRecordVo;
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
    private AnswerRecordService answerRecordService;

    @RequiresPermissions("func:answerRecord:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/answerRecord");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/answerRecord_edit");
        AnswerRecord answerRecord;
        if (id == null) {
            answerRecord = new AnswerRecord();
        } else {
            answerRecord = answerRecordService.getById(id);
        }
        mv.addObject("editInfo", answerRecord);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(AnswerRecord answerRecord) {
        Page<AnswerRecord> page = answerRecordService.listAnswerRecordPage(answerRecord);

//        Page<AnswerRecordVo> page1 = new Page<> ();
//        page1.setCurrent (page.getCurrent ());
//        page1.setSize (page.getSize ());
//        page1.setTotal (page.getTotal ());
//        List<AnswerRecordVo> answerRecordVos = super.convertToVoAndBindRelations (page.getRecords (), AnswerRecordVo.class);
//        page1.setRecords (answerRecordVos);
        return R.ok(page);
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

