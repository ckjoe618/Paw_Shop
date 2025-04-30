package com.topics.discuss.controller.front;

import com.topics.discuss.model.dto.request.CommentDeleteDto;
import com.topics.discuss.model.dto.request.CommentRequestDto;
import com.topics.discuss.model.dto.request.CommentUpdateDto;
import com.topics.discuss.model.dto.response.CommentGroupDto;
import com.topics.discuss.model.dto.response.CommentResponseDto;
import com.topics.discuss.model.entity.CommentBean;
import com.topics.discuss.model.service.front.CommentService;
import com.topics.security.AuthHolder;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 查詢留言
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByArticleId(@PathVariable int articleId) {
        List<CommentResponseDto> comments = commentService.getCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    // 查詢巢狀留言
    @GetMapping("/grouped/article/{articleId}")
    public ResponseEntity<List<CommentGroupDto>> getGroupedCommentsByArticleId(@PathVariable int articleId) {
        List<CommentGroupDto> commentGroups = commentService.getGroupedCommentsByArticleId(articleId);
        return ResponseEntity.ok(commentGroups);
    }

    // 新增留言
    @PostMapping
    public ResponseEntity<CommentBean> addComment(@Valid @RequestBody CommentRequestDto dto) {
        int memberId = AuthHolder.getMember().getMemberId();
        CommentBean saved = commentService.addComment(dto, memberId);
        return ResponseEntity.status(201).body(saved);
    }

    // 編輯留言
    @PutMapping
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentUpdateDto dto) {
        CommentResponseDto updated = commentService.updateComment(dto);
        return ResponseEntity.ok(updated);
    }

    // 刪除留言
    @DeleteMapping
    public ResponseEntity<String> deleteComment(@Valid @RequestBody CommentDeleteDto dto) {
        commentService.deleteComment(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
