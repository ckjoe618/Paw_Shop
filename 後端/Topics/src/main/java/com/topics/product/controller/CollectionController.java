package com.topics.product.controller;

import com.topics.member.security.AuthHolder;
import com.topics.product.model.bean.CollectionBean;
import com.topics.product.model.dto.CollectionDto;
import com.topics.product.model.service.CollectionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    // ✅ 新增收藏
    @PostMapping("/{productId}")
    public ResponseEntity<?> addToCollection(@PathVariable Integer productId) {
    	Integer memberId = AuthHolder.getMember().getMemberId();
        return ResponseEntity.ok(collectionService.addToCollection(memberId, productId));
    }

    // ✅ 取消收藏（軟刪除）
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeFromCollection(@PathVariable Integer productId) {
    	Integer memberId = AuthHolder.getMember().getMemberId();
        boolean success = collectionService.removeFromCollection(memberId, productId);
        return ResponseEntity.ok(success ? "取消收藏成功" : "找不到收藏紀錄");
    }

    // ✅ 取得目前登入者的所有收藏
    @GetMapping
    public ResponseEntity<List<CollectionDto>> getMyCollections() {
        var member = AuthHolder.getMember();
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Integer memberId = member.getMemberId();

        List<CollectionDto> result = collectionService
            .getCollectionsByMemberId(memberId)
            .stream()
            .map(CollectionDto::new)
            .toList();

        return ResponseEntity.ok(result);
    }
}
