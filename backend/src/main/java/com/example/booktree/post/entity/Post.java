package com.example.booktree.post.entity;


import com.example.booktree.auditable.Auditable;
import com.example.booktree.blog.entity.Blog;
import com.example.booktree.category.entity.Category;
import com.example.booktree.maincategory.entity.MainCategory;
import com.example.booktree.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name="posts")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ToString
@NoArgsConstructor
public class Post extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_id", nullable = false)
    private MainCategory mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog; //블로그아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //회원아이디

    @NotBlank
    @Column(length = 50, nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String content;

    @NotBlank
    @Column(length = 100)
    private String author;

    @NotBlank
    @Column(length = 100)
    private String book;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Column
    private Long view = 0L; //조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
