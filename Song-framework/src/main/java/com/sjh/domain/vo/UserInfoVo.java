package com.sjh.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;

}
