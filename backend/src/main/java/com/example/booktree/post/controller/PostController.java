package com.example.booktree.post.controller;


import com.example.booktree.post.dto.request.PostRequestDto;
import com.example.booktree.post.dto.response.PostResponseDto;
import com.example.booktree.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/posts")
@Validated
@AllArgsConstructor
public class PostController {
    private final PostService postService;


    @PostMapping
    public ResponseEntity postPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto response = new PostResponseDto(postService.createPost(postRequestDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    //내 블로그는 하나밖에 없다는 가정하에 유저 아이디로 가져옴
    @GetMapping
    public ResponseEntity getPostByUserId(@Positive @RequestParam Long postId) {
        PostResponseDto response = new PostResponseDto(postService.findPostByPostId(postId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Update
    @PatchMapping("/{postId}")
    public ResponseEntity patchPost(@RequestBody PostRequestDto postRequestDto
            , @PathVariable("postId") Long postId) {
        PostResponseDto response = new PostResponseDto(postService.updatePost(postRequestDto,postId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity deletePost(@PathVariable("userId")Long userId,
                                     @PathVariable("postId") Long postId) {
        postService.deletePost(userId,postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}
