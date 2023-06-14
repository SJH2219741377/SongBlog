package com.sjh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.domain.entity.ArticleTag;
import com.sjh.mapper.ArticleTagMapper;
import com.sjh.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author 宋佳豪
 * @since 2023-03-12 15:43:19
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

