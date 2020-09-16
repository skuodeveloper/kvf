package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.ArrayList;
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
        ModelAndView mv = new ModelAndView ("func/questionBank");

        List<Dict> questiontypedicts = dictMapper.selectAllDictItemByCode ("QUESTION_TYPE");
        mv.addObject ("questiontypes", questiontypedicts);

        List<Dict> xuanchuantypedicts = dictMapper.selectAllDictItemByCode ("XUANCHUAN_TYPE");
        mv.addObject ("xuanchuantypes", xuanchuantypedicts);
        return mv;
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
        try {
            List<QuestionBank> questionBanks;

            //平安三率
            List<QuestionBank> pasls;

            // 必答题目总数
            int necessaryCnt = questionBankService.count (
                    new LambdaQueryWrapper<QuestionBank> ()
                            .eq (QuestionBank::getStatus, 0)
                            .eq (QuestionBank::getIsnecessary, 1));

            if (necessaryCnt >= 10) {
                questionBanks = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .eq ("status", 0)
                                .eq ("isnecessary", 1)
                                .orderByDesc ("RAND()")
                                .last ("limit 10"));
            } else if (necessaryCnt == 0) {
                pasls = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .eq ("type", 26)
                                .eq ("status", 0)
                                .orderByDesc ("RAND()")
                                .last ("limit 2"));

                questionBanks = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .ne ("type", 26)
                                .eq ("status", 0)
                                .orderByDesc ("RAND()")
                                .last ("limit " + (10 - pasls.size ())));

                questionBanks.addAll (0, pasls);
            } else {
                pasls = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .eq ("type", 26)
                                .eq ("status", 0)
                                .orderByDesc ("RAND()")
                                .last ("limit 2"));

                questionBanks = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .ne ("type", 26)
                                .eq ("status", 0)
                                .eq ("isnecessary", 1)
                                .last ("limit " + necessaryCnt));

                List<QuestionBank> notnecessary = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .ne ("type", 26)
                                .eq ("status", 0)
                                .ne ("isnecessary", 1)
                                .orderByDesc ("RAND()")
                                .last ("limit " + (10 - necessaryCnt - pasls.size ())));

                questionBanks.addAll (notnecessary);
                questionBanks.addAll (0, pasls);
            }

            for (QuestionBank o : questionBanks) {
                List<AnswerDto> answerDtos = JSON.parseArray (o.getAnswer (), AnswerDto.class);
                o.setAnswerDtos (answerDtos);
            }

            questionBanks.sort (Comparator.comparing (QuestionBank::getSubjectType));
            List<QuestionBankVo> questionBankVos = super.convertToVoAndBindRelations (questionBanks, QuestionBankVo.class);

            return R.ok (questionBankVos);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    @GetMapping(value = "getRandQuestionByInterest")
    public R getRandQuestionByInterest(@RequestParam("sex") Integer sex, @RequestParam("interest") String interest) {
        try {
            List<QuestionBank> questionBanks;

            //平安三率
            List<QuestionBank> pasls;

            //根据性别出的题目
            List<QuestionBank> sexSubs = new ArrayList<> ();

            //根据兴趣去的题目
            List<QuestionBank> interestSubs = new ArrayList<> ();

            // 出题类型类别
            List<Integer> types = new ArrayList<> ();

            // 必答题目总数
            int necessaryCnt = questionBankService.count (
                    new LambdaQueryWrapper<QuestionBank> ()
                            .eq (QuestionBank::getStatus, 0)
                            .eq (QuestionBank::getIsnecessary, 1));

            if (necessaryCnt >= 10) {
                questionBanks = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .eq ("status", 0)
                                .eq ("isnecessary", 1)
                                .orderByDesc ("RAND()")
                                .last ("limit 10"));
            } else if (necessaryCnt == 0) {
                //平安三率
                types.add (26);
                pasls = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .eq ("type", 26)
                                .eq ("status", 0)
                                .orderByDesc ("RAND()")
                                .last ("limit 2"));

                // 男
                if (sex == 1) {
                    //网络贷款
                    types.add (13);
                    sexSubs.add (getOneByType (13));

                    //网络招嫖
                    types.add (14);
                    sexSubs.add (getOneByType (14));

                    //网络游戏
                    types.add (17);
                    sexSubs.add (getOneByType (17));

                    //投资理财
                    types.add (22);
                    sexSubs.add (getOneByType (22));
                } else { //女
                    //兼职刷单
                    types.add (12);
                    sexSubs.add (getOneByType (12));

                    //婚恋网友
                    types.add (24);
                    sexSubs.add (getOneByType (24));

                    //微信购物
                    types.add (19);
                    sexSubs.add (getOneByType (19));
                }

                JSONArray jsonArray = JSON.parseArray (interest);
                for (Object object : jsonArray) {
                    Integer t = ((JSONObject) object).getInteger ("value");
                    switch (t) {
                        case 1: //恋爱
                            //网络招嫖
                            if (!types.contains (14)) {
                                types.add (14);

                                interestSubs.add (getOneByType (14));
                            }

                            //婚恋网友
                            if (!types.contains (24)) {
                                types.add (24);
                                interestSubs.add (getOneByType (24));
                            }
                            break;
                        case 2://投资
                            //兼职刷单
                            if (!types.contains (12)) {
                                types.add (12);
                                interestSubs.add (getOneByType (12));
                            }

                            //网络投资
                            if (!types.contains (15)) {
                                types.add (15);
                                interestSubs.add (getOneByType (15));
                            }

                            //投资理财
                            if (!types.contains (22)) {
                                types.add (22);
                                interestSubs.add (getOneByType (22));
                            }
                            break;
                        case 3:
                            //网络贷款
                            //兼职刷单
                            if (!types.contains (13)) {
                                types.add (13);
                                interestSubs.add (getOneByType (13));
                            }

                            //网络投资
                            if (!types.contains (15)) {
                                types.add (15);
                                interestSubs.add (getOneByType (15));
                            }

                            //投资理财
                            if (!types.contains (22)) {
                                types.add (22);
                                interestSubs.add (getOneByType (22));
                            }
                            break;
                        case 4:
                            //游戏
                            //网络游戏
                            if (!types.contains (17)) {
                                types.add (17);
                                interestSubs.add (getOneByType (17));
                            }
                            break;
                        default:
                            break;
                    }
                }

                //其他
                questionBanks = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .notIn ("type", types)
                                .eq ("status", 0)
                                .orderByDesc ("RAND()")
                                .last ("limit " + (10 - pasls.size () - sexSubs.size () - interestSubs.size ())));

                questionBanks.addAll (0, pasls);
                questionBanks.addAll (sexSubs);
                questionBanks.addAll (interestSubs);
            } else {
                //平安三率
                types.add (26);
                pasls = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .eq ("type", 26)
                                .eq ("status", 0)
                                .orderByDesc ("RAND()")
                                .last ("limit 2"));

                //必答题
                questionBanks = questionBankService.list (
                        new QueryWrapper<QuestionBank> ()
                                .ne ("type", 26)
                                .eq ("status", 0)
                                .eq ("isnecessary", 1)
                                .last ("limit " + necessaryCnt));

                necessaryCnt = questionBanks.size ();

                // 男
                if (sex == 1) {
                    //网络贷款
                    types.add (13);
                    sexSubs.add (getOneByTypeAndNotMust (13));

                    //网络招嫖
                    types.add (14);
                    sexSubs.add (getOneByTypeAndNotMust (14));

                    //网络游戏
                    types.add (17);
                    sexSubs.add (getOneByTypeAndNotMust (17));

                    //投资理财
                    types.add (22);
                    sexSubs.add (getOneByTypeAndNotMust (22));
                } else { //女
                    //兼职刷单
                    types.add (12);
                    sexSubs.add (getOneByTypeAndNotMust (12));

                    //婚恋网友
                    types.add (24);
                    sexSubs.add (getOneByTypeAndNotMust (24));

                    //微信购物
                    types.add (19);
                    sexSubs.add (getOneByTypeAndNotMust (19));
                }

                JSONArray jsonArray = JSON.parseArray (interest);
                for (Object object : jsonArray) {
                    Integer t = ((JSONObject) object).getInteger ("value");
                    switch (t) {
                        case 1: //恋爱
                            //网络招嫖
                            if (!types.contains (14)) {
                                types.add (14);

                                interestSubs.add (getOneByType (14));
                            }

                            //婚恋网友
                            if (!types.contains (24)) {
                                types.add (24);
                                interestSubs.add (getOneByType (24));
                            }
                            break;
                        case 2://投资
                            //兼职刷单
                            if (!types.contains (12)) {
                                types.add (12);
                                interestSubs.add (getOneByType (12));
                            }

                            //网络投资
                            if (!types.contains (15)) {
                                types.add (15);
                                interestSubs.add (getOneByType (15));
                            }

                            //投资理财
                            if (!types.contains (22)) {
                                types.add (22);
                                interestSubs.add (getOneByType (22));
                            }
                            break;
                        case 3:
                            //网络贷款
                            //兼职刷单
                            if (!types.contains (13)) {
                                types.add (13);
                                interestSubs.add (getOneByType (13));
                            }

                            //网络投资
                            if (!types.contains (15)) {
                                types.add (15);
                                interestSubs.add (getOneByType (15));
                            }

                            //投资理财
                            if (!types.contains (22)) {
                                types.add (22);
                                interestSubs.add (getOneByType (22));
                            }
                            break;
                        case 4:
                            //游戏
                            //网络游戏
                            if (!types.contains (17)) {
                                types.add (17);
                                interestSubs.add (getOneByType (17));
                            }
                            break;
                        default:
                            break;
                    }
                }

                if((10 - necessaryCnt - pasls.size () - sexSubs.size () - interestSubs.size ()) > 0) {
                    List<QuestionBank> notnecessary = questionBankService.list (
                            new QueryWrapper<QuestionBank> ()
                                    .notIn ("type", types)
                                    .eq ("status", 0)
                                    .ne ("isnecessary", 1)
                                    .orderByDesc ("RAND()")
                                    .last ("limit " + (10 - necessaryCnt - pasls.size () - sexSubs.size () - interestSubs.size ())));

                    questionBanks.addAll (notnecessary);
                }

                questionBanks.addAll (0, pasls);
                questionBanks.addAll (sexSubs);
                questionBanks.addAll (interestSubs);
            }

            for (QuestionBank o : questionBanks) {
                List<AnswerDto> answerDtos = JSON.parseArray (o.getAnswer (), AnswerDto.class);
                o.setAnswerDtos (answerDtos);
            }

            questionBanks.sort (Comparator.comparing (QuestionBank::getSubjectType));
            List<QuestionBankVo> questionBankVos = super.convertToVoAndBindRelations (questionBanks, QuestionBankVo.class);

            return R.ok (questionBankVos);
        } catch (Exception ex) {
            return R.fail (ex.getMessage ());
        }
    }

    /**
     * 从不同类型中随机取一条数据
     *
     * @param type
     * @return
     */
    private QuestionBank getOneByType(Integer type) {
        return questionBankService.getOne (
                new QueryWrapper<QuestionBank> ()
                        .eq ("type", type)
                        .eq ("status", 0)
                        .orderByDesc ("RAND()")
                        .last ("limit 1"));
    }

    /**
     * 从不同类型中随机取一条数据,并且不是必选项
     *
     * @param type
     * @return
     */
    private QuestionBank getOneByTypeAndNotMust(Integer type) {
        return questionBankService.getOne (
                new QueryWrapper<QuestionBank> ()
                        .eq ("type", type)
                        .eq ("status", 0)
                        .ne ("isnecessary", 1)
                        .orderByDesc ("RAND()")
                        .last ("limit 1"));
    }
}

