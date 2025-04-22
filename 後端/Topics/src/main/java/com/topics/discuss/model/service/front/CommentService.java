package com.topics.discuss.model.service.front;

import com.topics.discuss.model.dto.request.CommentRequestDto;
import com.topics.discuss.model.dto.response.CommentGroupDto;
import com.topics.discuss.model.dto.response.CommentResponseDto;
import com.topics.discuss.model.entity.CommentBean;
import com.topics.discuss.model.repository.CommentRepository;
import com.topics.member.model.entity.MemberBean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 查詢留言
    public List<CommentResponseDto> getCommentsByArticleId(int articleId) {
        List<CommentBean> all = commentRepository.findByArticleIdAndDeletedFalseOrderByCreatedDateAsc(articleId);
        List<CommentResponseDto> result = new ArrayList<>();
        for (CommentBean comment : all) {
            CommentResponseDto dto = new CommentResponseDto();
            dto.setCommentId(comment.getCommentId());
            dto.setMemberId(comment.getMemberId());
            dto.setContent(comment.getContent());
            dto.setParentCommentId(comment.getParentCommentId());
            dto.setFloor(comment.getFloor());
            dto.setCreateDate(comment.getCreatedDate());
            dto.setUpdateDate(comment.getUpdatedDate());

            MemberBean member = comment.getMember();
            dto.setMemberName(member.getMemberName());
            byte[] rawPhoto = member.getMemberPhoto();
            String memberPhoto = (rawPhoto != null) ? new String(rawPhoto, StandardCharsets.UTF_8) : null;
            dto.setMemberPhoto(memberPhoto);

            result.add(dto);
        }
        return result;
    }

    // 巢狀留言結構查詢
    public List<CommentGroupDto> getGroupedCommentsByArticleId(int articleId) {

        List<CommentBean> comments = commentRepository.findByArticleIdAndDeletedFalseOrderByCreatedDateAsc(articleId);

        List<CommentGroupDto> result = new ArrayList<>();
        Map<Integer, List<CommentGroupDto>> replyMap = new HashMap<>();

        for (CommentBean comment : comments) {
            // 回傳dto
            CommentGroupDto dto = new CommentGroupDto();
            dto.setCommentId(comment.getCommentId());
            dto.setMemberId(comment.getMemberId());
            dto.setContent(comment.getContent());
            dto.setFloor(comment.getFloor());
            dto.setParentCommentId(comment.getParentCommentId());
            dto.setCreatedDate(comment.getCreatedDate());
            dto.setUpdatedDate(comment.getUpdatedDate());

            // member
            MemberBean member = comment.getMember();
            dto.setMemberName(member.getMemberName());

            byte[] rawPhoto = member.getMemberPhoto();
            String memberPhoto = (rawPhoto != null) ? new String(rawPhoto, StandardCharsets.UTF_8) : null;
            dto.setMemberPhoto(memberPhoto);

            if (comment.getParentCommentId() == null || comment.getParentCommentId() == 0) {
                result.add(dto);
            } else {
                int parentId = comment.getParentCommentId();
                replyMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(dto);
            }
        }

        for (CommentGroupDto dto : result) {
            int id = dto.getCommentId();
            if (replyMap.containsKey(id)) {
                dto.setReplies(replyMap.get(id));
            }
        }
        return result;
    }

    // 新增留言、回覆
    public CommentBean addComment(CommentRequestDto dto) {
        CommentBean comment = new CommentBean();

        comment.setArticleId(dto.getArticleId());
        comment.setMemberId(dto.getMemberId());
        comment.setContent(dto.getContent());
        comment.setDeleted(false);
        comment.setUpdatedDate(null);

        if (dto.getParentCommentId() == null) {
            // 蓋新樓
            CommentBean latest = commentRepository.findFirstByArticleIdAndParentCommentIdIsNullAndDeletedFalseOrderByFloorDesc(dto.getArticleId());
            int nextFloor = (latest == null) ? 2 : latest.getFloor() + 1;
            comment.setFloor(nextFloor);
            comment.setParentCommentId(null);
        } else if (dto.getParentCommentId() == 0) {
            // 1樓留言
            comment.setFloor(null);
            comment.setParentCommentId(0);
        } else {
            // 其他樓層留言
            boolean exists = commentRepository.existsById(dto.getParentCommentId());
            if (!exists) {
                throw new IllegalArgumentException("樓層不存在");
            }
            comment.setFloor(null);
            comment.setParentCommentId(dto.getParentCommentId());
        }

        return commentRepository.save(comment);
    }
}
