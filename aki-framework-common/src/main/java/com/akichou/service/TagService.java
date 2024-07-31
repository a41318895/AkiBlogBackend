package com.akichou.service;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.TagAddDto;
import com.akichou.domain.dto.TagEditDto;
import com.akichou.domain.dto.TagSelectConditionsDto;
import com.akichou.domain.entity.Tag;
import com.akichou.domain.vo.PageVo;
import com.akichou.domain.vo.TagInfoVo;
import com.akichou.domain.vo.TagPageSelectVo;
import com.akichou.domain.vo.TagVo;

import java.util.List;

/**
 * Tag Service Interface
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface TagService {

    ResponseResult<PageVo<TagPageSelectVo>> selectTags(Integer pageNum, Integer pageSize, TagSelectConditionsDto tagSelectConditionsDto);

    ResponseResult<Object> addTag(TagAddDto tagAddDto);

    ResponseResult<Object> deleteTag(Long tagId);

    ResponseResult<TagInfoVo> getTag(Long tagId);

    ResponseResult<Object> editTag(TagEditDto tagEditDto);

    ResponseResult<List<TagVo>> listAllTags();

    List<Tag> listExistTags();
}
