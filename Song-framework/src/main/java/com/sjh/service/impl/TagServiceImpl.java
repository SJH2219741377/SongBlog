package com.sjh.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.dto.TagDTO;
import com.sjh.domain.dto.TagListDTO;
import com.sjh.domain.entity.Tag;
import com.sjh.domain.vo.PageVo;
import com.sjh.domain.vo.TagVo;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.mapper.TagMapper;
import com.sjh.service.TagService;
import com.sjh.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 标签(Tag)表服务实现类
 *
 * @author 宋佳豪
 * @since 2023-03-08 17:15:52
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDTO tagListDTO) {
        // 分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        // if DTO.name != null and !empty and len > 0, like(Tag.name,DTO.name) -> Tag.name like '%DTO.name%'
        queryWrapper.like(StringUtils.hasText(tagListDTO.getName()), Tag::getName, tagListDTO.getName());
        queryWrapper.like(StringUtils.hasText(tagListDTO.getRemark()), Tag::getRemark, tagListDTO.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        // 封装返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDTO tagListDTO) {
        if (!StringUtils.hasText(tagListDTO.getName())) {
            throw new SystemException(AppHttpCodeEnum.NEED_PARAMS);
        }
        Tag tag = BeanCopyUtils.copyBean(tagListDTO, Tag.class);
        if (tagNameExist(tag.getName())) {
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_EXIST);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        if (Objects.isNull(id)) throw new SystemException(AppHttpCodeEnum.NEED_PARAMS);
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long id) {
        if (Objects.isNull(id)) throw new SystemException(AppHttpCodeEnum.NEED_PARAMS);
        Tag tag = getById(id);
        if (Objects.isNull(tag)) return ResponseResult.errorResult(301, "查询不到此标签");
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(TagDTO tagDTO) {
        if (Objects.isNull(tagDTO.getId())) throw new SystemException(AppHttpCodeEnum.NEED_PARAMS);
        Tag tag = BeanCopyUtils.copyBean(tagDTO, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId, Tag::getName, Tag::getRemark);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }

    private boolean tagNameExist(String name) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName, name);
        return getBaseMapper().selectCount(queryWrapper) > 0;
    }


}

