package com.core.backend_advanced.api.post.entity;

import com.core.backend_advanced.api.members.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "posts")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    //게시글 제목
    @Column(name = "title")
    private String title;

    //게시글 내용
    @Column(name = "content")
    private String content;

    //게시글 댓글
    @Column(name = "comment")
    private String comment;

    //작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //게시물 상태(유효, 대기, 완료)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus =  PostStatus.VALID;

    //거래 신청시 => WAITING
    public void assignMember(Member member) {
        this.member = member;
        this.postStatus = PostStatus.WAITING;
    }

    //거래 완료시 => COMPLETED
    public void updatePostStatusToCompleted(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

}
