package com.example.booktree.maincategory.controller;

import com.example.booktree.maincategory.dto.request.CreateMainCaterequestDto;
import com.example.booktree.maincategory.dto.response.AllMainCategoryResponseDto;
import com.example.booktree.maincategory.service.MainCategortService;
import com.example.booktree.jwt.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maincategories")
@RequiredArgsConstructor
@Tag(name = "메인카테고리 관리 컨트롤러")
public class MainCategoryController {

    public final MainCategortService mainCategortService;
    public final TokenService tokenService;

    @GetMapping("/get")
    @Operation(
            summary = "모든 메인 카테고리 조회 기능",
            description = "모든 메인 카테고리 조회 메서드",
            tags = "메인카테고리 관리 컨트롤러"
    )
    public ResponseEntity<?> allMainCategory(){

        List<AllMainCategoryResponseDto> response = mainCategortService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(
            summary = "메인 카테고리 생성 기능",
            description = "권한에 따라 메인 카테고리를 생성하는 메서드",
            tags = "메인카테고리 관리 컨트롤러"
    )
    public ResponseEntity<?> createMainCategory(@RequestBody CreateMainCaterequestDto createMainCaterequestDto){

        mainCategortService.saveMainCate(createMainCaterequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/patch/{mainCategoryId}")
    @Operation(
            summary = "메인 카테고리 수정 기능",
            description = "권한에 따라 메인 카테고리를 수정하는 메서드",
            tags = "메인카테고리 관리 컨트롤러"
    )
    public ResponseEntity<?> updateMainCategory(@PathVariable("mainCategoryId") Long mainCategoryId,@RequestBody CreateMainCaterequestDto createMainCaterequestDto){
        mainCategortService.updateMainCate(mainCategoryId, createMainCaterequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{mainCategoryId}")
    @Operation(
            summary = "메인 카테고리 삭제 기능",
            description = "권한에 따라 메인 카테고리를 삭제하는 메서드",
            tags = "메인카테고리 관리 컨트롤러"
    )
    public ResponseEntity<?> deleteMainCategory(@PathVariable("mainCategoryId") Long mainCategoryId){
        mainCategortService.deleteMainCate(mainCategoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
