package com.topics.discuss.controller.front;

import com.topics.discuss.model.dto.request.CommentLikeAddDto;
import com.topics.discuss.model.service.front.CommentLikeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment-likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    // 按讚
    @PostMapping
    public ResponseEntity<String> addCommentLike(@Valid @RequestBody CommentLikeAddDto commentLikeAddDto) {
        commentLikeService.addCommentLike(commentLikeAddDto);
        return ResponseEntity.status(201).body("按讚成功");
    }

    // 收回讚
    @DeleteMapping
    public ResponseEntity<Void> removeCommentLike(@Valid @RequestBody CommentLikeAddDto commentLikeAddDto) {
        commentLikeService.removeCommentLike(commentLikeAddDto);
        return ResponseEntity.status(204).build();
    }

    // 查詢是否按讚
    @PostMapping("/check")
    public ResponseEntity<Boolean> isCommentLiked(@Valid @RequestBody CommentLikeAddDto commentLikeAddDto) {
        boolean result = commentLikeService.isCommentLiked(commentLikeAddDto);
        return ResponseEntity.ok(result);
    }

    // 查詢留言按讚數
    @GetMapping("/count/{commentId}")
    public ResponseEntity<Integer> getCommentLikeCount(@PathVariable int commentId) {
        int count = commentLikeService.getCommentLikeCount(commentId);
        return ResponseEntity.ok(count);
    }
}
