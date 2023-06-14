package com.sjh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.dto.TagDTO;
import com.sjh.domain.dto.TagListDTO;
import com.sjh.domain.entity.Tag;
import com.sjh.domain.vo.PageVo;
import com.sjh.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author 宋佳豪
 * @since 2023-03-08 17:15:52
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDTO tagListDTO);

    ResponseResult addTag(TagListDTO tagListDTO);

    ResponseResult deleteTag(Long id);

    ResponseResult getTagById(Long id);

    ResponseResult updateTag(TagDTO tagDTO);

    List<TagVo> listAllTag();

}

