package com.core.backend_advanced.api.post.controller;

import com.core.backend_advanced.api.post.dto.MyPostListResponseDto;
import com.core.backend_advanced.api.post.dto.PostCreateRequestDto;
import com.core.backend_advanced.api.post.entity.Post;
import com.core.backend_advanced.api.post.service.PostService;
import com.core.backend_advanced.common.exception.BadRequestException;
import com.core.backend_advanced.common.response.ApiResponse;
import com.core.backend_advanced.common.response.ErrorStatus;
import com.core.backend_advanced.common.response.SuccessStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시물 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createpost(@Valid @RequestBody PostCreateRequestDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        postService.createPost(email, request);

        return ApiResponse.successOnly(SuccessStatus.ARTICLE_CREATE_SUCCESS);
    }

    //게시물 전체 조회
    @GetMapping("/my-post")
    public ResponseEntity<ApiResponse<List<MyPostListResponseDto>>> getMyPost() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<MyPostListResponseDto> posts = postService.getMyPosts(email);
        return ApiResponse.success(SuccessStatus.ARTICLE_GET_SUCCESS, posts);
    }

    //거래 신청 (상태 => WAITING)
    @PostMapping("/{postId}/participate")
    public ResponseEntity<ApiResponse<Void>> postTradeRequest(@PathVariable Long postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if(postId == null){
            throw new BadRequestException(ErrorStatus.BAD_REQUEST_MISSING_REQUIRED_FIELD.getMessage());
        }

        postService.postTradeRequest(postId, email);

        return ApiResponse.successOnly(SuccessStatus.TRADE_REQUEST_SUCCESS);
    }

    //거래 완료 (상태 => COMPLETED)
    @PostMapping("/{postId}/authenticate")
    public ResponseEntity<ApiResponse<Void>> postAuthenticateRequest(@PathVariable Long postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        postService.postAuthenticate(postId, email);

        return ApiResponse.successOnly(SuccessStatus.TRADE_COMPLETE_SUCCESS);

    }

}
