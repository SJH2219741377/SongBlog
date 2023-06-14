package com.sjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.constants.SystemConstants;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.vo.LinkVo;
import com.sjh.domain.entity.Link;
import com.sjh.mapper.LinkMapper;
import com.sjh.service.LinkService;
import com.sjh.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author 宋佳豪
 * @since 2023-02-11 03:41:19
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Override
    public ResponseResult getAllLink() {
        // 查询所有通过审核的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_CHECKED_PASS);
        List<Link> links = list(queryWrapper);
        // 封装vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

}

