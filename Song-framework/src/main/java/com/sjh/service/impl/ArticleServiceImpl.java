package com.sjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.constants.SystemConstants;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.dto.AddArticleDto;
import com.sjh.domain.entity.Article;
import com.sjh.domain.entity.ArticleTag;
import com.sjh.domain.entity.Category;
import com.sjh.domain.vo.ArticleDetailVo;
import com.sjh.domain.vo.ArticleListVo;
import com.sjh.domain.vo.HotArticleVo;
import com.sjh.domain.vo.PageVo;
import com.sjh.mapper.ArticleMapper;
import com.sjh.service.ArticleService;
import com.sjh.service.ArticleTagService;
import com.sjh.service.CategoryService;
import com.sjh.utils.BeanCopyUtils;
import com.sjh.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private CategoryService categoryService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章, 统一封装返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 需要是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按浏览量进行降序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        // bean拷贝
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询参数判断 如果有categoryId 就要 查询和传入是相同的
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态为正式发布的
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 文章置顶排序
        articleWrapper.orderByDesc(Article::getIsTop);

        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, articleWrapper);
        List<Article> articles = page.getRecords();

        // 设置categoryName 没必要接收流,因为已经对结果进行了设置 articles -> page.getRecords
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        // 封装查询结果
        List<ArticleListVo> listVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(listVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据id查询文章
        Article article = getById(id);
        // 从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_KEY_ARTICLE_VIEW_COUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        // 转换成Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类id查询分类名
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新对应文章的浏览量 +1
        redisCache.incrementCacheMapValue(SystemConstants.REDIS_KEY_ARTICLE_VIEW_COUNT, id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto addArticleDto) {
        // 添加博文
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        // 注意,当mp完成插入记录后，会将最新的记录的id赋值给传入对象(article)的id属性中
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        // 添加 博文和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

}
