package com.kalvin.kvf.modules.func.vo;

import com.diboot.core.binding.annotation.BindEntity;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.entity.TestPaper;
import com.kalvin.kvf.modules.func.entity.WxUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TestPaperVo extends TestPaper {
    // 直接关联Entity
    @BindEntity(entity = QuestionBank.class, condition="this.question_bank_id=id")
    private QuestionBank questionBank;
}
