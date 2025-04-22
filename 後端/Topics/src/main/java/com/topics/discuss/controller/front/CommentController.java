package com.topics.discuss.controller.front;

import com.topics.discuss.model.dto.request.CommentRequestDto;
import com.topics.discuss.model.dto.response.CommentGroupDto;
import com.topics.discuss.model.dto.response.CommentResponseDto;
import com.topics.discuss.model.entity.CommentBean;
import com.topics.discuss.model.service.front.CommentService;
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
        CommentBean saved = commentService.addComment(dto);
        return ResponseEntity.status(201).body(saved);
    }
}
