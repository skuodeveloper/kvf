package com.kalvin.kvf.modules.func.vo;

import com.diboot.core.binding.annotation.BindEntity;
import com.kalvin.kvf.modules.func.entity.AnswerRecord;
import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.sys.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AnswerRecordVo extends AnswerRecord {
    // 直接关联Entity
    @BindEntity(entity = WxUser.class, condition="this.userid=id")
    private WxUser wxUser;
}
