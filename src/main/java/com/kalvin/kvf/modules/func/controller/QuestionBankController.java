package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.common.utils.ShiroKit;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.service.QuestionBankService;
import com.kalvin.kvf.modules.func.vo.QuestionBankVo;
import com.kalvin.kvf.modules.sys.dto.AnswerDto;
import com.kalvin.kvf.modules.sys.entity.Dict;
import com.kalvin.kvf.modules.sys.mapper.DictMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 题库 前端控制器
 * </p>
 *
 * @since 2020-07-20 10:24:50
 */
@RestController
@RequestMapping("func/questionBank")
public class QuestionBankController extends BaseCrudRestController {

    @Autowired
    private QuestionBankService questionBankService;
    @Resource
    private DictMapper dictMapper;

    @RequiresPermissions("func:questionBank:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView ("func/questionBank");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView ("func/questionBank_edit");
        QuestionBank questionBank;
        if (id == null) {
            questionBank = new QuestionBank ();
            questionBank.setSubjectType (0L);
            questionBank.setScore (10);
        } else {
            questionBank = questionBankService.getById (id);

            try {
                ObjectMapper mapper = new ObjectMapper ();
                mapper.configure (JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                List<AnswerDto> answerDtos = mapper.readValue (questionBank.getAnswer (),
                        new TypeReference<List<AnswerDto>> () {
                        });
                questionBank.setAnswerDtos (answerDtos);
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

        mv.addObject ("editInfo", questionBank);

        List<Dict> questiontypedicts = dictMapper.selectAllDictItemByCode ("QUESTION_TYPE");
        mv.addObject ("questiontypes", questiontypedicts);

        List<Dict> xuanchuantypedicts = dictMapper.selectAllDictItemByCode ("XUANCHUAN_TYPE");
        mv.addObject ("xuanchuantypes", xuanchuantypedicts);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(QuestionBank questionBank) {
        Page<QuestionBank> page = questionBankService.listQuestionBankPage (questionBank);

        ObjectMapper mapper = new ObjectMapper ();
        mapper.configure (JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        for (QuestionBank o : page.getRecords ()) {
            try {
                List<AnswerDto> answerDtos = mapper.readValue (o.getAnswer (),
                        new TypeReference<List<AnswerDto>> () {
                        });
                o.setAnswerDtos (answerDtos);
            } catch (IOException ex) {
                ex.printStackTrace ();
            }
        }

        return R.ok (page);
    }

    @RequiresPermissions("func:questionBank:add")
    @PostMapping(value = "add")
    public R add(QuestionBank questionBank) {
        questionBank.setAnswer (questionBank.getAnswer ().replaceAll ("PASSWWORD", "\""));
        questionBank.setCreateBy (ShiroKit.getUser ().getId ());
        questionBank.setCreateTime (new Date ());
        questionBankService.save (questionBank);
        return R.ok ();
    }

    @RequiresPermissions("func:questionBank:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        questionBankService.removeByIds (ids);
        return R.ok ();
    }

    @RequiresPermissions("func:questionBank:edit")
    @PostMapping(value = "edit")
    public R edit(QuestionBank questionBank) {
        questionBank.setAnswer (questionBank.getAnswer ().replaceAll ("PASSWWORD", "\""));
        questionBank.setUpdateBy (ShiroKit.getUser ().getId ());
        questionBankService.updateById (questionBank);
        return R.ok ();
    }

    @RequiresPermissions("func:questionBank:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        questionBankService.removeById (id);
        return R.ok ();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok (questionBankService.getById (id));
    }

    @GetMapping(value = "getRandQuestion")
    public R getRandQuestion() {
        List<QuestionBank> questionBanks = questionBankService.list (
                new QueryWrapper<QuestionBank> ()
                        .eq ("status", 0)
                        .orderByDesc ("RAND()")
                        .last ("limit 10"));

        ObjectMapper mapper = new ObjectMapper ();
        mapper.configure (JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        for (QuestionBank o : questionBanks) {
            try {
                List<AnswerDto> answerDtos = mapper.readValue (o.getAnswer (),
                        new TypeReference<List<AnswerDto>> () {
                        });
                o.setAnswerDtos (answerDtos);
            } catch (IOException ex) {
                ex.printStackTrace ();
            }
        }

        questionBanks.sort (Comparator.comparing (QuestionBank::getSubjectType));
        List<QuestionBankVo> questionBankVos = super.convertToVoAndBindRelations (questionBanks, QuestionBankVo.class);

        return R.ok (questionBankVos);
    }
}

