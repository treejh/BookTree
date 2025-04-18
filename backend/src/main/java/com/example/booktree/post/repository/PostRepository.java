package com.example.booktree.post.repository;

import com.example.booktree.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p WHERE p.category.id = :categoryId")
    List<Post> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Post p WHERE p.mainCategory.id = :mainCategoryId")
    Page<Post> findByMainCategoryId(@Param("mainCategoryId") Long mainCategoryId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.mainCategory.id = :mainCategoryId AND p.createdAt >= :oneWeekAgo ORDER BY p.view DESC")
    Page<Post> findTopPostsByViewsInLastWeek(@Param("mainCategoryId") Long mainCategoryId,
                                             @Param("oneWeekAgo") LocalDateTime oneWeekAgo,
                                             Pageable pageable);

    Page<Post> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds, Pageable pageable);
    // 블로그별 게시글 조회
    List<Post> findByBlogId(Long blogId);

    // 회원별 게시글 조회
    List<Post> findByUserId(Long userId);


    // 최신순
    Page<Post> findByBlogIdOrderByCreatedAtDesc(Long blogId, Pageable pageable);

    // 인기순
    @Query("SELECT p FROM Post p WHERE p.blog.id = :blogId AND p.createdAt >= :oneWeekAgo ORDER BY p.likeCount DESC")
    Page<Post> findPopularPostsByLikesInLastWeek(@Param("blogId") Long blogId,
                                                 @Param("oneWeekAgo") LocalDateTime oneWeekAgo,
                                                 Pageable pageable);






    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable); // 제목 검색

    Page<Post> findByAuthorContainingIgnoreCase(String author, Pageable pageable); // 작성자 검색

    Page<Post> findByBookContainingIgnoreCase(String book, Pageable pageable); // 책 제목 검색




}
