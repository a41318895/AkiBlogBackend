package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.TagAddDto;
import com.akichou.domain.dto.TagEditDto;
import com.akichou.domain.dto.TagSelectConditionsDto;
import com.akichou.domain.entity.Tag;
import com.akichou.domain.vo.PageVo;
import com.akichou.domain.vo.TagInfoVo;
import com.akichou.domain.vo.TagPageSelectVo;
import com.akichou.domain.vo.TagVo;
import com.akichou.exception.SystemException;
import com.akichou.repository.TagRepository;
import com.akichou.service.TagService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

import static com.akichou.constant.SystemConstants.DEL_FLAG_DELETED;
import static com.akichou.constant.SystemConstants.DEL_FLAG_EXIST;

/**
 * Tag Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository ;

    @Override
    public ResponseResult<PageVo<TagPageSelectVo>> selectTags(Integer pageNum, Integer pageSize,
                                                              TagSelectConditionsDto tagSelectConditionsDto) {

        // Database Process
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        String name = tagSelectConditionsDto.getName();
        String remark = tagSelectConditionsDto.getRemark();
        if(!StringUtils.hasText(tagSelectConditionsDto.getName())) {
            name = null ;
        }
        if(!StringUtils.hasText(remark)) {
            remark = null ;
        }
        Page<Tag> page = tagRepository.findByNameAndRemark(name, remark, pageRequest) ;

        // Vo Encapsulation
        List<TagPageSelectVo> tagPageSelectVos = BeanCopyUtil.copyBeanList(page.getContent(), TagPageSelectVo.class);

        return ResponseResult.okResult(new PageVo<>(tagPageSelectVos, page.getTotalElements())) ;
    }

    @Override
    public ResponseResult<Object> addTag(@RequestBody TagAddDto tagAddDto) {

        // Assure name and remark are not null
        if(!StringUtils.hasText(tagAddDto.getName()) && !StringUtils.hasText(tagAddDto.getRemark())) {
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_AND_REMARK_NOT_NULL) ;
        }

        Tag addedTag = mapTagAddDtoToTag(tagAddDto) ;
        tagRepository.save(addedTag) ;

        return ResponseResult.okResult() ;
    }

    private Tag mapTagAddDtoToTag(TagAddDto tagAddDto) {

        return Tag.builder()
                .name(tagAddDto.getName())
                .remark(tagAddDto.getRemark())
                .createBy(SecurityUtil.getUserId())
                .createTime(new Date())
                .updateBy(SecurityUtil.getUserId())
                .updateTime(new Date())
                .delFlag(DEL_FLAG_EXIST)
                .build();
    }

    @Override
    public ResponseResult<Object> deleteTag(Long tagId) {

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.TAG_NOT_FOUND)) ;
        tag.setDelFlag(DEL_FLAG_DELETED);

        tagRepository.save(tag) ;

        return ResponseResult.okResult() ;
    }

    @Override
    public ResponseResult<TagInfoVo> getTag(Long tagId) {

        // Database Process
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.TAG_NOT_FOUND)) ;

        // VO Encapsulation
        TagInfoVo tagInfoVo = BeanCopyUtil.copyBean(tag, TagInfoVo.class);

        return ResponseResult.okResult(tagInfoVo) ;
    }

    @Override
    public ResponseResult<Object> editTag(TagEditDto tagEditDto) {

        // Assure name or remark is not null
        if(!StringUtils.hasText(tagEditDto.getName()) && !StringUtils.hasText(tagEditDto.getRemark())) {
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_AND_REMARK_NOT_NULL) ;
        }

        Tag tag = tagRepository.findById(tagEditDto.getId())
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.TAG_NOT_FOUND)) ;

        tagRepository.save(setAttributesForTagEditing(tag, tagEditDto)) ;

        return ResponseResult.okResult();
    }

    private Tag setAttributesForTagEditing(Tag tag, TagEditDto tagEditDto) {

        tag.setName(tagEditDto.getName()) ;
        tag.setRemark(tagEditDto.getRemark()) ;
        tag.setUpdateBy(SecurityUtil.getUserId()) ;
        tag.setUpdateTime(new Date());

        return tag ;
    }

    @Override
    public ResponseResult<List<TagVo>> listAllTags() {

        // Database Process
        List<Tag> tags = tagRepository.findAllByDelFlag(DEL_FLAG_EXIST) ;

        // VO Encapsulation
        List<TagVo> tagVos = BeanCopyUtil.copyBeanList(tags, TagVo.class);

        return ResponseResult.okResult(tagVos) ;
    }

    @Override
    public List<Tag> listExistTags() {

        return tagRepository.findAllByDelFlag(DEL_FLAG_EXIST) ;
    }
}
