package com.example.booktree.reply.controller;

import com.example.booktree.reply.dto.ReplyDto;
import com.example.booktree.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // 대댓글 생성
    @PostMapping("/create")
    public ResponseEntity<ReplyDto.Response> createReply(@RequestBody ReplyDto.Post dto) {
        ReplyDto.Response response = replyService.createReply(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 대댓글 조회 엔드포인트
    @GetMapping("/get")
    public ResponseEntity<List<ReplyDto.Response>> getReplies(@RequestParam("commentId") Long commentId) {
        List<ReplyDto.Response> responses = replyService.getRepliesByCommentId(commentId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // 대댓글 수정
    @PatchMapping("/update/{replyId}")
    public ResponseEntity<ReplyDto.Response> updateReply(@PathVariable Long replyId,
                                                         @RequestBody ReplyDto.Patch dto) {
        dto.setReplyId(replyId);
        ReplyDto.Response response = replyService.updateReply(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 대댓글 삭제\
    @DeleteMapping("/delete/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
