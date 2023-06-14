package com.sjh.runner;

import com.sjh.constants.SystemConstants;
import com.sjh.domain.entity.Article;
import com.sjh.mapper.ArticleMapper;
import com.sjh.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 在应用启动时将博客的浏览量存入redis中
 *
 * @author 宋佳豪
 * @version 1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public void run(String... args) {
        // 先查询出 id, viewCount
        List<Article> articles = articleMapper.selectList(null);
        // 转换成map (Long -> int 是为了方便我们增加浏览量,不然在redis中增加时: 1L -> 2L,不方便读取)
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();
                }));
        // 存储到数据库中
        redisCache.setCacheMap(SystemConstants.REDIS_KEY_ARTICLE_VIEW_COUNT, viewCountMap);
    }

}
