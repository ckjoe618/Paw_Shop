package com.topics.discuss.controller.front;

import com.topics.discuss.model.dto.request.FavoriteAddDto;
import com.topics.discuss.model.dto.response.FavoriteArticleDto;
import com.topics.discuss.model.service.front.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // 查詢是否收藏
    @PostMapping("/check")
    public ResponseEntity<Boolean> isFavorite(@RequestBody @Valid FavoriteAddDto favoriteAddDto) {
        boolean result = favoriteService.isFavorite(favoriteAddDto);
        return ResponseEntity.ok(result);
    }

    // 新增收藏
    @PostMapping
    public ResponseEntity<String> addFavorite(@Valid @RequestBody FavoriteAddDto addDto) {
        favoriteService.addFavorite(addDto);
        return ResponseEntity.status(201).body("收藏成功");
    }

    // 移除收藏
    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@Valid @RequestBody FavoriteAddDto addDto) {
        favoriteService.removeFavorite(addDto);
        return ResponseEntity.status(204).build();
    }

    // 查詢文章總收藏
    @GetMapping("/count/{articleId}")
    public ResponseEntity<Integer> countFavorite(@PathVariable int articleId) {
        int count = favoriteService.countFavorites(articleId);
        return ResponseEntity.ok(count);
    }

    // 收藏文章查詢
    @GetMapping("member/{memberId}")
    public ResponseEntity<List<FavoriteArticleDto>> getFavoriteArticles(@PathVariable int memberId) {
        List<FavoriteArticleDto> favorites = favoriteService.getFavoriteArticlesByMemberId(memberId);
        return ResponseEntity.ok(favorites);
    }

}
