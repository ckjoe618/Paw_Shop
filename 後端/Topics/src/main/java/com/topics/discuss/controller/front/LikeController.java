package com.topics.discuss.controller.front;

import com.topics.discuss.model.dto.request.LikeAddDto;
import com.topics.discuss.model.service.front.LikeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // 查詢是否按過讚
    @PostMapping("/check")
    public ResponseEntity<Boolean> isLiked(@Valid @RequestBody LikeAddDto likeAddDto) {
        boolean result = likeService.isLiked(likeAddDto);
        return ResponseEntity.ok(result);
    }

    // 按讚
    @PostMapping
    public ResponseEntity<String> addLike(@Valid @RequestBody LikeAddDto likeAddDto) {
        likeService.addLike(likeAddDto);
        return ResponseEntity.status(201).body("ok");
    }

    // 收回讚
    @DeleteMapping
    public ResponseEntity<Void> removeLike(@Valid @RequestBody LikeAddDto likeAddDto) {
        likeService.removeLike(likeAddDto);
        return ResponseEntity.noContent().build();
    }

    // 查詢讚數
    @GetMapping("/count/{articleId}")
    public ResponseEntity<Integer> getLikeCount(@PathVariable int articleId) {
        int count = likeService.countLikes(articleId);
        return ResponseEntity.ok(count);
    }

}
