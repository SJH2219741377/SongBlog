package com.sjh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {

    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

}
