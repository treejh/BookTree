package com.example.booktree.like_reply.service;

import com.example.booktree.like_reply.dto.LikeReplyDto;
import com.example.booktree.like_reply.entity.LikeReply;
import com.example.booktree.like_reply.repository.LikeReplyRepository;
import com.example.booktree.reply.entity.Reply;
import com.example.booktree.reply.repository.ReplyRepository;
import com.example.booktree.user.entity.User;
import com.example.booktree.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class LikeReplyService {

    private final LikeReplyRepository likeReplyRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    public LikeReplyService(LikeReplyRepository likeReplyRepository,
                            ReplyRepository replyRepository,
                            UserRepository userRepository) {
        this.likeReplyRepository = likeReplyRepository;
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
    }

    // 대댓글 좋아요 생성
    @Transactional
    public LikeReplyDto.Response createLike(LikeReplyDto.Post dto) {
        // 중복 좋아요 체크: 한 사용자는 한 대댓글에 한 번만 좋아요 가능
        Optional<LikeReply> existing = likeReplyRepository.findByReply_IdAndUser_Id(dto.getReplyId(), dto.getUserId());
        if(existing.isPresent()){
            throw new RuntimeException("이미 좋아요를 누르셨습니다.");
        }

        // 대댓글(Reply) 조회
        Reply reply = replyRepository.findById(dto.getReplyId())
                .orElseThrow(() -> new RuntimeException("대댓글을 찾을 수 없습니다. id: " + dto.getReplyId()));

        // 사용자(User) 조회
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id: " + dto.getUserId()));

        // 좋아요 엔티티 생성 및 저장
        LikeReply likeReply = LikeReply.builder()
                .reply(reply)
                .user(user)
                .build();

        LikeReply saved = likeReplyRepository.save(likeReply);
        return mapToResponse(saved);
    }

    // 대댓글 좋아요 삭제
    @Transactional
    public void deleteLike(Long replyId, Long userId) {
        LikeReply likeReply = likeReplyRepository.findByReply_IdAndUser_Id(replyId, userId)
                .orElseThrow(() -> new RuntimeException("해당 좋아요 기록을 찾을 수 없습니다. reply id: " + replyId + ", user id: " + userId));
        likeReplyRepository.delete(likeReply);
    }

    // LikeReply 엔티티를 Response DTO로 변환하는 헬퍼 메서드
    private LikeReplyDto.Response mapToResponse(LikeReply likeReply) {
        return new LikeReplyDto.Response(
                likeReply.getId(),
                likeReply.getReply() != null ? likeReply.getReply().getId() : null,
                likeReply.getUser() != null ? likeReply.getUser().getId() : null,
                likeReply.getCreatedAt(),
                likeReply.getModifiedAt()
        );
    }
}
