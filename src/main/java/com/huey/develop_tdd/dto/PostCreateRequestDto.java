package com.huey.develop_tdd.dto;

import com.huey.develop_tdd.domain.Post;

public class PostCreateRequestDto {
    private final String title;
    private final String content;

    public PostCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // Post 엔티티로 변환하는 메서드
    public Post toEntity() {
        return new Post(this.title, this.content);
    }
}
