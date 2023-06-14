package com.sjh.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.Category;
import com.sjh.domain.vo.CategoryVo;
import com.sjh.domain.vo.ExcelCategoryVo;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.service.CategoryService;
import com.sjh.utils.BeanCopyUtils;
import com.sjh.utils.WebUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) { // 导出成excel文件
        try {
            // 设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            // 获取需要导出的数据
            List<Category> categoryList = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVo.class);
            // 把数据写入excel文件中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            // 出现异常则响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

}
