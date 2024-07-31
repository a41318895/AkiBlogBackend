package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.CommentAddDto;
import com.akichou.domain.entity.Comment;
import com.akichou.domain.entity.User;
import com.akichou.domain.vo.CommentVo;
import com.akichou.domain.vo.PageVo;
import com.akichou.exception.SystemException;
import com.akichou.repository.CommentRepository;
import com.akichou.repository.UserRepository;
import com.akichou.service.CommentService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.akichou.constant.SystemConstants.*;

/**
 * Comment Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseResult<PageVo<CommentVo>> getCommentList(String commentType,
                                                 Long articleId, Integer pageNum, Integer pageSize) {

        // Database Process
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

        // Judging is article comment or link comment via parameter commentType
        Page<Comment> page ;
        if (ARTICLE_COMMENT.equals(commentType)) {
            page = commentRepository.findByTypeAndArticleIdAndRootId(
                    commentType,
                    articleId, IS_ROOT_COMMENT_ID,
                    pageable);
        } else if (LINK_COMMENT.equals(commentType)) {
            page = commentRepository.findByTypeAndRootId(
                    commentType,
                    IS_ROOT_COMMENT_ID,
                    pageable);
        } else {
            throw new SystemException(AppHttpCodeEnum.ILLEGAL_COMMENT_TYPE) ;
        }

        // Sort the comment by createTime
        List<Comment> commentList = page.getContent().stream()
                .sorted(Comparator.comparing(Comment::getCreateTime).reversed())
                .collect(Collectors.toList());

        // Vo Encapsulation
        List<CommentVo> commentVoList = toCommentVoList(commentList);

        // Set children Comments
        commentVoList.forEach(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())));

        return ResponseResult.okResult(new PageVo<>(commentVoList, page.getTotalElements()));
    }

    private List<CommentVo> toCommentVoList(List<Comment> commentList) {

        List<Long> userIds = commentList.stream()
                .flatMap(comment -> Stream.of(comment.getCreateBy(), comment.getToCommentUserId()))
                .distinct()
                .collect(Collectors.toList());

        Map<Long, String> userIdAndNicknameMap = userRepository.findByIdIn(userIds).stream()
                .collect(Collectors.toMap(User::getId, User::getNickName));

        return commentList.stream()
                .map(comment -> {
                    CommentVo commentVo = BeanCopyUtil.copyBean(comment, CommentVo.class);

                    commentVo.setUsername(userIdAndNicknameMap.get(comment.getCreateBy()));

                    if (!Objects.equals(comment.getToCommentUserId(), IS_ROOT_COMMENT_ID)) {
                        commentVo.setToCommentUserName(userIdAndNicknameMap.get(comment.getToCommentUserId()));
                    }

                    return commentVo;
                })
                .collect(Collectors.toList());
    }

    private List<CommentVo> getChildren(Long rootId) {

        List<Comment> comments = commentRepository.findByRootId(rootId);

        return toCommentVoList(comments);
    }

    @Override
    public ResponseResult<Object> addComment(CommentAddDto commentAddDto) {

        // Check comment content blank or not
        if(!StringUtils.hasText(commentAddDto.getContent())){
            throw new SystemException(AppHttpCodeEnum.COMMENT_CONTENT_NOT_BLANK);
        }

        // Database Process
        Comment addedComment = mapCommentAddDtoToComment(commentAddDto);
        commentRepository.save(addedComment) ;

        return ResponseResult.okResult();
    }

    private Comment mapCommentAddDtoToComment(CommentAddDto commentAddDto) {

        return Comment.builder()
                .type(commentAddDto.getType())
                .articleId(commentAddDto.getArticleId())
                .rootId(commentAddDto.getRootId())
                .content(commentAddDto.getContent())
                .toCommentUserId(commentAddDto.getToCommentUserId())
                .toCommentId(commentAddDto.getToCommentId())
                .createBy(SecurityUtil.getUserId())
                .createTime(new Date())
                .updateBy(SecurityUtil.getUserId())
                .updateTime(new Date())
                .delFlag(DEL_FLAG_EXIST)
                .build();
    }
}