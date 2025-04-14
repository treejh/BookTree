package com.example.booktree.LikePost.repository;

import com.example.booktree.LikePost.entity.LikePost;
import com.example.booktree.post.entity.Post;
import com.example.booktree.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    Optional<LikePost> findByUserAndPost(User user, Post post);
    Long countByPost(Post post);
    // 유저와 게시글로 좋아요 존재 여부 확인
    boolean existsByUserAndPost(User user, Post post);

    // 유저와 게시글로 좋아요 엔티티 조회

}
