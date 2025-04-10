package com.example.booktree.like_reply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
public class LikeReplyDto {

    // 대댓글 좋아요 생성 시 필요한 DTO
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private Long replyId;  // 좋아요를 누를 대댓글의 ID
        private Long userId;   // 좋아요를 누른 사용자 ID
    }

    // 대댓글 좋아요 응답용 DTO
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private Long id;         // 좋아요 기록의 고유 ID
        private Long replyId;    // 좋아요가 적용된 대댓글 ID
        private Long userId;     // 좋아요를 누른 사용자 ID
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
