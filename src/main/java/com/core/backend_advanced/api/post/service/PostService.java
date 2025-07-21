package com.core.backend_advanced.api.post.service;

import com.core.backend_advanced.api.members.entity.Member;
import com.core.backend_advanced.api.members.repository.MemberRepository;
import com.core.backend_advanced.api.members.service.MemberService;
import com.core.backend_advanced.api.post.dto.MyPostListResponseDto;
import com.core.backend_advanced.api.post.dto.PostCreateRequestDto;
import com.core.backend_advanced.api.post.entity.Post;
import com.core.backend_advanced.api.post.entity.PostStatus;
import com.core.backend_advanced.api.post.repository.PostRepository;
import com.core.backend_advanced.common.exception.ConflictException;
import com.core.backend_advanced.common.exception.NotFoundException;
import com.core.backend_advanced.common.exception.UnauthorizedException;
import com.core.backend_advanced.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //게시물 생성
    @Transactional
    public void createPost(String email, PostCreateRequestDto request) throws NotFoundException, UnauthorizedException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException(ErrorStatus.NOT_FOUND_USER.getMessage()));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContents())
                .member(member)
                .postStatus(PostStatus.WAITING)
                .build();

        postRepository.save(post);
    }

    //게시물 전체 조회
    @Transactional(readOnly = true)
    public List<MyPostListResponseDto> getMyPosts(String email) throws NotFoundException, UnauthorizedException {
        List<Post> myPosts = postRepository.findPostByEmail(email);

        return myPosts.stream()
                .map(MyPostListResponseDto::toDto)
                .toList();
    }

    //거래 신청
    @Transactional
    public void postTradeRequest(Long postId, String email) throws NotFoundException, UnauthorizedException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_POST.getMessage()));

        if(post.getMember() != null) {
            throw new ConflictException(ErrorStatus.BAD_REQUEST_POST_WRITER_NOT_SAME_USER.getMessage());
        }
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException(ErrorStatus.NOT_FOUND_USER.getMessage()));
        post.assignMember(member);
    }

    //거래 완료
    @Transactional
    public void postAuthenticate(Long postId, String email) throws NotFoundException, UnauthorizedException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_POST.getMessage()));
        if(post.getMember() == null || !post.getMember().getEmail().equals(email)) {
            throw new UnauthorizedException(ErrorStatus.BAD_REQUEST_POST_WRITER_NOT_SAME_USER.getMessage());
        }
        post.updatePostStatusToCompleted(PostStatus.COMPLETED);


    }
}
