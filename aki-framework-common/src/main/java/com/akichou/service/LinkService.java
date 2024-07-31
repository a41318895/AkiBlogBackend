package com.akichou.service;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.LinkAddDto;
import com.akichou.domain.dto.LinkEditDto;
import com.akichou.domain.dto.LinkChangeStatusDto;
import com.akichou.domain.dto.LinkSelectConditionsDto;
import com.akichou.domain.entity.Link;
import com.akichou.domain.vo.LinkInfoVo;
import com.akichou.domain.vo.LinkPageSelectVo;
import com.akichou.domain.vo.LinkVo;
import com.akichou.domain.vo.PageVo;

import java.util.List;

/**
 * Link Service Interface
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public interface LinkService {

    ResponseResult<List<LinkVo>> getAllLink();

    ResponseResult<PageVo<LinkPageSelectVo>> selectLinks(Integer pageNum, Integer pageSize, LinkSelectConditionsDto linkSelectConditionsDto);

    ResponseResult<Object> addLink(LinkAddDto linkAddDto);

    ResponseResult<LinkInfoVo> getLinkById(Long linkId);

    ResponseResult<Object> editLink(LinkEditDto linkEditDto);

    ResponseResult<Object> deleteLink(Long linkId);

    ResponseResult<Object> changeLinkStatus(LinkChangeStatusDto linkChangeStatusDto);

    List<Link> listExistLinks();
}
