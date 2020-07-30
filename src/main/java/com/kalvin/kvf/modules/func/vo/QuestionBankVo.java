package com.kalvin.kvf.modules.func.vo;

import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.sys.entity.Dict;
import com.kalvin.kvf.modules.sys.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import com.diboot.core.binding.annotation.BindEntity;

@Getter
@Setter
@Accessors(chain = true)
public class QuestionBankVo extends QuestionBank {
    // 直接关联Entity
    @BindEntity(entity = User.class, condition="this.create_by=id")
    private User user;

    @BindEntity(entity = Dict.class, condition="this.subject_type=id")
    private Dict dictSubType;

    @BindEntity(entity = Dict.class, condition="this.type=id")
    private Dict dictType;
}
