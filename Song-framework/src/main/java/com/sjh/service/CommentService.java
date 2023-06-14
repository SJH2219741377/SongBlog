package com.sjh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author 宋佳豪
 * @since 2023-02-22 22:26:38
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}

