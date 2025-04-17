package com.huey.develop_tdd.dto;

import com.huey.develop_tdd.domain.Post;

public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;

    public PostResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // Post 엔티티로부터 DTO를 생성하는 정적 팩토리 메서드
    public static PostResponseDto fromEntity(Post post) {
        if (post == null) {
            return null;
        }
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent());
    }
}
