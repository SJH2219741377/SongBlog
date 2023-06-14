package com.sjh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {

    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //所属分类名
    private String categoryName;
    //所属分类id
    private Long categoryId;
    //是否允许评论 1是，0否
    private String isComment;
    //访问量
    private Long viewCount;
    private Date createTime;

}
