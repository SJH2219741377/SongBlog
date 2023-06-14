package com.sjh.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author 宋佳豪
 * @since 2023-03-12 15:43:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article_tag")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 625337492348897098L;

    //文章id
    private Long articleId;
    //标签id
    private Long tagId;

}

