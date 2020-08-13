package com.kalvin.kvf.modules.func.vo;

import com.kalvin.kvf.modules.func.entity.WxUser;
import com.kalvin.kvf.modules.func.entity.WxUserJf;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxUserJfVo implements Serializable {
    WxUser wxUser;
    List<WxUserJf> wxUserJfs;
}
