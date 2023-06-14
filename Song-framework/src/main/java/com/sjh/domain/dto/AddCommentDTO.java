package com.sjh.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 增加评论 数据传输对象
 * @author 宋佳豪
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论DTO")
public class AddCommentDTO {

    @ApiModelProperty(notes = "评论id")
    private Long id;
    @ApiModelProperty(notes = "评论类型（0代表文章评论，1代表友链评论）")
    private String type;
    @ApiModelProperty(notes = "文章id")
    private Long articleId;
    @ApiModelProperty(notes = "根评论id (默认-1,表示对文章评论)")
    private Long rootId;
    @ApiModelProperty(notes = "评论内容")
    private String content;
    @ApiModelProperty(notes = "所回复的目标评论的userid")
    private Long toCommentUserId;
    @ApiModelProperty(notes = "回复目标评论id")
    private Long toCommentId;
    /**
     * 创建人的用户id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    @ApiModelProperty(notes = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;

}
