package com.sjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjh.constants.SystemConstants;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.Comment;
import com.sjh.domain.vo.CommentVo;
import com.sjh.domain.vo.PageVo;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.mapper.CommentMapper;
import com.sjh.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.service.UserService;
import com.sjh.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author 宋佳豪
 * @since 2023-02-22 22:26:38
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserService userService;

    /**
     * 返回根评论列表
     *
     * @param commentType 评论类型
     * @param articleId   文章id
     * @param pageNum     页码
     * @param pageSize    每页条数
     * @return 根评论列表
     */
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 判断文章id, 当类型为文章评论时才比较
        queryWrapper.eq(SystemConstants.COMMENT_ARTICLE.equals(commentType), Comment::getArticleId, articleId);
        // 判断是否为根评论 (-1)
        queryWrapper.eq(Comment::getRootId, SystemConstants.IS_COMMENT_ROOT);

        // 判断评论类型
        queryWrapper.eq(Comment::getType, commentType);

        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(page.getRecords());

        // 查询所有根评论对应的子评论集合,并赋值给对应属性
        for (CommentVo commentVo : commentVos) {
            // 查询对应的子评论集合
            List<CommentVo> children = getChildren(commentVo.getId());
            // 赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        // 评论内容不为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NO_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     *
     * @param id 根评论的id
     * @return 所对应的子评论集合
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        return toCommentVoList(comments);
    }

    /**
     * 根据评论列表查询所回复目标评论列表
     *
     * @param list 评论列表
     * @return 目标评论列表
     */
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // 遍历vo集合
        for (CommentVo vo : commentVos) {
            // 通过 createBy 查询用户的昵称并赋值
            String nickName = userService.getById(vo.getCreateBy()).getNickName();
            vo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询 (-1表示无回复评论的用户id)
            if (vo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(vo.getToCommentUserId()).getNickName();
                vo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

}

