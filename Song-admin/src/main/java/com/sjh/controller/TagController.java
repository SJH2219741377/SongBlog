package com.sjh.controller;

import com.sjh.domain.ResponseResult;
import com.sjh.domain.dto.TagDTO;
import com.sjh.domain.dto.TagListDTO;
import com.sjh.domain.vo.PageVo;
import com.sjh.domain.vo.TagVo;
import com.sjh.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDTO tagListDTO) {
        return tagService.pageTagList(pageNum, pageSize, tagListDTO);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDTO tagListDTO) {
        return tagService.addTag(tagListDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id) {
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDTO tagDTO) {
        return tagService.updateTag(tagDTO);
    }


}
