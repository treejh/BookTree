package com.example.booktree.comment.service;

import com.example.booktree.comment.dto.CommentDto;
import com.example.booktree.comment.entity.Comment;
import com.example.booktree.comment.repository.CommentRepository;
import com.example.booktree.post.entity.Post;
import com.example.booktree.post.repository.PostRepository;
import com.example.booktree.reply.dto.ReplyDto;
import com.example.booktree.reply.repository.ReplyRepository;
import com.example.booktree.user.entity.User;
import com.example.booktree.jwt.service.TokenService;
import com.example.booktree.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository; // 대댓글 조회용 Repository
    private final TokenService tokenService;
    private final UserService userService;

    @Transactional
    public CommentDto.Response createComment(CommentDto.Post dto) {
        Optional<Post> postOptional = postRepository.findById(dto.getPostId());
        if (!postOptional.isPresent()) {
            throw new RuntimeException("Post not found with id: " + dto.getPostId());
        }
        Post post = postOptional.get();

        // 현재 로그인한 사용자의 이메일을 토큰에서 추출하고, 해당 User 조회
        String userEmail = tokenService.getEmailFromToken();
        User user = userService.findUserByEmail(userEmail);

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .post(post)
                .user(user)
                .build();

        Comment saved = commentRepository.save(comment);
        return mapToResponseWithReplies(saved);
    }

    // 페이징 처리된 댓글 조회: postId에 해당하는 댓글들을 Page 객체로 반환
    public Page<CommentDto.Response> getCommentsByPostId(Long postId, PageRequest pageRequest) {
        return commentRepository.findByPostId(postId, pageRequest)
                .map(this::mapToResponseWithReplies);
    }

    @Transactional
    public CommentDto.Response updateComment(CommentDto.Patch dto) {
        Comment comment = commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + dto.getCommentId()));
        Long currentUserId = tokenService.getIdFromToken();
        if (!comment.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("You are not authorized to update this comment");
        }
        comment.setContent(dto.getContent());
        Comment updated = commentRepository.save(comment);
        return mapToResponseWithReplies(updated);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        Long currentUserId = tokenService.getIdFromToken();
        if (!comment.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("You are not authorized to delete this comment");
        }
        commentRepository.deleteById(commentId);
    }

    // 각 Comment 엔티티에 대해 Reply(대댓글)를 페이징 처리하여 Response DTO에 포함시키는 헬퍼 메서드
    private CommentDto.Response mapToResponseWithReplies(Comment comment) {
        Long postId = Optional.ofNullable(comment.getPost())
                .map(Post::getId)
                .orElse(null);
        String userEmail = comment.getUser() != null ? comment.getUser().getEmail() : null;
        // 기본적으로 대댓글은 첫 페이지(0번 페이지), 한 페이지 당 10개, 최신순(생성일 내림차순)으로 조회함
        PageRequest replyPageRequest = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<ReplyDto.Response> replies = replyRepository.findByComment_Id(comment.getId(), replyPageRequest)
                .map(reply -> new ReplyDto.Response(
                        reply.getId(),
                        reply.getComment() != null ? reply.getComment().getId() : null,
                        reply.getContent(),
                        reply.getCreatedAt(),
                        reply.getModifiedAt(),
                        reply.getUser() != null ? reply.getUser().getEmail() : null
                ));
        return new CommentDto.Response(
                comment.getId(),
                comment.getContent(),
                postId,
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                userEmail,
                replies
        );
    }
}
