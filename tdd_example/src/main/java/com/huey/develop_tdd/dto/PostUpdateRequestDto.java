package com.huey.develop_tdd.dto;

public class PostUpdateRequestDto {
    private final String title;
    private final String content;

    public PostUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
