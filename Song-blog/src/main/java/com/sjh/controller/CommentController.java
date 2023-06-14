package com.sjh.controller;

import com.sjh.annotation.SystemLog;
import com.sjh.constants.SystemConstants;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.dto.AddCommentDTO;
import com.sjh.domain.entity.Comment;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.service.CommentService;
import com.sjh.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论", description = "评论相关接口")
public class CommentController {

    @Resource
    CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "获取评论列表")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {

        if (Objects.isNull(pageNum) || Objects.isNull(pageSize))
            throw new SystemException(AppHttpCodeEnum.NEED_PARAMS);

        return commentService.commentList(SystemConstants.COMMENT_ARTICLE, articleId, pageNum, pageSize);
    }

    @GetMapping("/linkCommentList")
    @SystemLog(businessName = " 获取所有友联评论")
    @ApiOperation(value = "友链评论列表", notes = "根据页码获取对应页数友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小")
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.COMMENT_LINK, null, pageNum, pageSize);
    }

    @PostMapping
    @SystemLog(businessName = "发表评论")
    public ResponseResult addComment(@RequestBody AddCommentDTO addCommentDTO) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDTO, Comment.class);
        return commentService.addComment(comment);
    }

}
