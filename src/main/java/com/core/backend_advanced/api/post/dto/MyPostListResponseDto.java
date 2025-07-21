package com.core.backend_advanced.api.post.dto;

import com.core.backend_advanced.api.post.entity.Post;
import com.core.backend_advanced.api.post.entity.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPostListResponseDto {

    private Long postId;
    private String title;
    private String content;
    private String member;

    private PostStatus postStatus;

    public static MyPostListResponseDto toDto(Post post) {
        return MyPostListResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postStatus(post.getPostStatus())
                .build();
    }
}
