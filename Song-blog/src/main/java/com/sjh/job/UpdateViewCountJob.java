package com.sjh.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sjh.constants.SystemConstants;
import com.sjh.domain.entity.Article;
import com.sjh.service.ArticleService;
import com.sjh.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务 - 每隔 3分钟 将文章浏览量更新到数据库中
 *
 * @author 宋佳豪
 * @version 1.0
 */
@Component
public class UpdateViewCountJob {

    @Resource
    private RedisCache redisCache;

    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0 0/3 * * * ? ")
    public void updateViewCount() {
        // 获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDIS_KEY_ARTICLE_VIEW_COUNT);

        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        // 同步到数据库
        /*
            articleService.updateBatchById(articles)
            定时任务执行批量更新时出现空指针异常问题 (此批量更新将更新全部字段)
            原因: 因为自动更新是系统后台做的，而SecurityContextHolder.getContext().setAuthentication()是前台发起请求时候做的，
            所以导致mp获取用户id的时候其实getAuthentication()根本就不存在为null。
         */
        for (Article article : articles) {
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, article.getId());
            updateWrapper.set(Article::getViewCount, article.getViewCount());
            articleService.update(updateWrapper);
        }
    }

}
