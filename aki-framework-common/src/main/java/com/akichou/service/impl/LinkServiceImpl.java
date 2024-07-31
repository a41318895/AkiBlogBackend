package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
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
import com.akichou.exception.SystemException;
import com.akichou.repository.LinkRepository;
import com.akichou.service.LinkService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.akichou.constant.SystemConstants.*;

/**
 * Link Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository ;

    @Override
    public ResponseResult<List<LinkVo>> getAllLink() {

        // Database Process
        List<Link> links = linkRepository.findByStatus(LINK_STATUS_REVIEW_PASS) ;

        // Vo Encapsulation
        List<LinkVo> linkVos = BeanCopyUtil.copyBeanList(links, LinkVo.class) ;

        return ResponseResult.okResult(linkVos) ;
    }

    @Override
    public ResponseResult<PageVo<LinkPageSelectVo>> selectLinks(Integer pageNum, Integer pageSize, LinkSelectConditionsDto linkSelectConditionsDto) {

        String name = Optional.ofNullable(linkSelectConditionsDto.getName()).filter(StringUtils::hasText).orElse(null) ;
        String status = Optional.ofNullable(linkSelectConditionsDto.getStatus()).filter(StringUtils::hasText).orElse(null) ;

        // Database Process
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<Link> page = linkRepository.findByNameAndStatusExisting(
                name,
                status,
                pageRequest) ;

        // Vo Encapsulation
        List<LinkPageSelectVo> linkPageSelectVos = BeanCopyUtil.copyBeanList(page.getContent(), LinkPageSelectVo.class);

        return ResponseResult.okResult(new PageVo<>(linkPageSelectVos, page.getTotalElements())) ;
    }

    @Override
    public ResponseResult<Object> addLink(LinkAddDto linkAddDto) {

        Link addedLink = mapLinkAddDtoToLink(linkAddDto);

        // Database Process
        linkRepository.save(addedLink);

        return ResponseResult.okResult() ;
    }

    private Link mapLinkAddDtoToLink(LinkAddDto linkAddDto) {

        return Link.builder()
                .name(linkAddDto.getName())
                .logo(linkAddDto.getLogo())
                .description(linkAddDto.getDescription())
                .address(linkAddDto.getAddress())
                .status(linkAddDto.getStatus())
                .createBy(SecurityUtil.getUserId())
                .createTime(new Date())
                .updateBy(SecurityUtil.getUserId())
                .updateTime(new Date())
                .delFlag(DEL_FLAG_EXIST)
                .build();
    }

    @Override
    public ResponseResult<LinkInfoVo> getLinkById(Long linkId) {

        // Database Process
        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.LINK_NOT_FOUND)) ;

        // Vo Encapsulation
        LinkInfoVo linkInfoVo = BeanCopyUtil.copyBean(link, LinkInfoVo.class);

        return ResponseResult.okResult(linkInfoVo) ;
    }

    @Override
    public ResponseResult<Object> editLink(LinkEditDto linkEditDto) {

        // Database Process
        Link link = linkRepository.findById(linkEditDto.getId())
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.LINK_NOT_FOUND)) ;
        linkRepository.save(setAttributesForLinkEditing(link, linkEditDto));

        return ResponseResult.okResult() ;
    }

    private Link setAttributesForLinkEditing(Link link, LinkEditDto linkEditDto) {

        link.setName(linkEditDto.getName());
        link.setStatus(linkEditDto.getStatus());
        link.setLogo(linkEditDto.getLogo());
        link.setDescription(linkEditDto.getDescription());
        link.setAddress(linkEditDto.getAddress());
        link.setUpdateBy(SecurityUtil.getUserId());
        link.setUpdateTime(new Date());

        return link ;
    }

    @Override
    public ResponseResult<Object> deleteLink(Long linkId) {

        Link link = linkRepository.findById(linkId)
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.LINK_NOT_FOUND)) ;

        link.setDelFlag(DEL_FLAG_DELETED);
        linkRepository.save(link);

        return ResponseResult.okResult() ;
    }

    @Override
    public ResponseResult<Object> changeLinkStatus(LinkChangeStatusDto linkChangeStatusDto) {

        Link link = linkRepository.findById(linkChangeStatusDto.getId())
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.LINK_NOT_FOUND)) ;

        link.setStatus(linkChangeStatusDto.getStatus());
        linkRepository.save(link);

        return ResponseResult.okResult() ;
    }

    @Override
    public List<Link> listExistLinks() {

        return linkRepository.findAllByDelFlag(DEL_FLAG_EXIST) ;
    }
}
