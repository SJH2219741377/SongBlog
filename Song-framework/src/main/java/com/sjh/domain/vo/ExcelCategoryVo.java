package com.sjh.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
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
public class ExcelCategoryVo {

    @ExcelProperty("分类名")
    private String name;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("状态：0(正常),1(禁用)")
    private String status;

}
