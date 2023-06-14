package com.sjh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复的目标评论的name
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;

    // 创建人id
    private Long createBy;

    private Date createTime;

    private String username;

    // 子评论集合
    private List<CommentVo> children;

}
