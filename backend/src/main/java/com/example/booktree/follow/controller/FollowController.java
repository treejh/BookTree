package com.example.booktree.follow.controller;

import com.example.booktree.follow.dto.request.FollowRequestDto;
import com.example.booktree.follow.dto.request.UnFollowRequestDto;
import com.example.booktree.follow.dto.response.AllFollowListResponseDto;
import com.example.booktree.follow.dto.response.FollowCountDto;
import com.example.booktree.follow.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "팔로우 관리 컨트롤러")
public class FollowController {

    private final FollowService followService;

    // 팔로우 모두 보기
    @GetMapping("/follow/allfollower/{userId}")
    @Operation(
            summary = "팔로우 목록 보기 기능",
            description = "내가 팔로우 하는 회원들 닉네임을 가져오는 메서드 ",
            tags = "팔로우 관리 컨트롤러"
    )
    public ResponseEntity<?> allFollow(@PathVariable Long userId) {

        List<AllFollowListResponseDto> response = followService.getAllFollowerList(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 팔로잉 모두 보기
    @GetMapping("/follow/allfollowed/{userId}")
    @Operation(
            summary = "팔로잉 목록 보기 기능",
            description = "나를 팔로우하는 회원들 닉네임을 가져오는 메서드",
            tags = "팔로우 관리 컨트롤러"
    )
    public ResponseEntity<?> allFollowed(@PathVariable Long userId) {

        List<AllFollowListResponseDto> response = followService.getAllFollowedList(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 팔로우 생성
    @PostMapping("/follow/createfollow")
    @Operation(
            summary = "팔로우 생성 기능",
            description = "로그인한 유저가 선택한 유저를 팔로우 하는 메서드",
            tags = "팔로우 관리 컨트롤러"
    )
    public ResponseEntity<?> createFollow(@RequestBody FollowRequestDto followRequestDto){

        followService.createFollow(followRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 팔로워, 팔로잉 숫자
    @GetMapping("/follow/getfollowcount/{userId}")
    @Operation(
            summary = "팔로워, 팔로잉 숫자 제공 기능",
            description = "로그인한 ID를 기반으로 팔로워, 팔로잉하는 유저들의 수를 제공하는 메서드",
            tags = "팔로우 관리 컨트롤러"
    )
    public ResponseEntity<?> getFollowCount(@PathVariable Long userId) {

        FollowCountDto response = followService.getCount(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 언팔로우
    @DeleteMapping("/follow/unfollow")
    @Operation(
            summary = "언팔로우 기능",
            description = "로그인한 유저가 선택한 유저를 언팔로우 하는 메서드",
            tags = "팔로우 관리 컨트롤러"
    )
    public ResponseEntity<?> unfollow(@RequestBody UnFollowRequestDto unFollowRequestDto) {

        followService.unFollow(unFollowRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
