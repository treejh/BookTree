package com.example.booktree.like_reply.controller;

import com.example.booktree.like_reply.dto.LikeReplyDto;
import com.example.booktree.like_reply.service.LikeReplyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like-replies")
@AllArgsConstructor
public class LikeReplyController {

    private final LikeReplyService likeReplyService;

    // 대댓글 좋아요 생성 엔드포인트
    @PostMapping
    public ResponseEntity<LikeReplyDto.Response> createLike(@RequestBody LikeReplyDto.Post dto) {
        LikeReplyDto.Response response = likeReplyService.createLike(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 대댓글 좋아요 삭제 엔드포인트
    @DeleteMapping
    public ResponseEntity<Void> deleteLike(@RequestParam Long replyId,
                                           @RequestParam Long userId) {
        likeReplyService.deleteLike(replyId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
