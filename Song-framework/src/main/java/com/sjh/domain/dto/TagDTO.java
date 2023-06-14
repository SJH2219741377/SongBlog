package com.sjh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于接收修改标签信息的DTO
 * @author 宋佳豪
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    private Long id;

    private String name;

    private String remark;

}
