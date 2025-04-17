package com.huey.develop_tdd.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PostTest {

    @Test
    @DisplayName("Post 생성 - 성공")
    void createPost_Success() {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용";

        // when
        Post post = new Post(title, content);

        // then
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getId()).isNull();  // 아직 영속화 전이므로 ID는 null
    }

    @Test
    @DisplayName("Post 생성 - 실패 (제목 누락)")
    void createPost_Fail_NoTitle() {
        // given
        String title = null;
        String content = "테스트 내용";

        // when, then
        assertThatThrownBy(() -> new Post(title, content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title cannot be empty");

        assertThatThrownBy(() -> new Post("", content))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Post(" ", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Post 생성 - 실패 (내용 누락)")
    void createPost_Fail_NoContent() {
        // given
        String title = "테스트 제목";
        String content = null;

        // when, then
        assertThatThrownBy(() -> new Post(title, content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Content cannot be empty");

        assertThatThrownBy(() -> new Post(title, ""))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Post(title, " "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Post 업데이트 - 성공 (제목, 내용 모두 변경)")
    void updatePost_Success_All() {
        // given
        Post post = new Post("원본제목", "원본내용");
        String newTitle = "수정제목";
        String newContent = "수정내용";

        // when
        post.update(newTitle, newContent);

        // then
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("Post 업데이트 - 성공 (제목만 변경)")
    void updatePost_Success_TitleOnly() {
        // given
        Post post = new Post("원본제목", "원본내용");
        String newTitle = "수정제목";
        String originContent = post.getContent();

        // when
        post.update(newTitle, originContent);

        // then
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(originContent);

        // when, then - 빈 내용
        post.update(newTitle, "");
        assertThat(post.getContent()).isEqualTo(originContent);

        // when, then - 공백 내용
        post.update(newTitle, " ");
        assertThat(post.getContent()).isEqualTo(originContent);
    }

    @Test
    @DisplayName("Post 업데이트 - 성공 (내용만 변경)")
    void updatePost_Success_ContentOnly() {
        // given
        Post post = new Post("원본제목", "원본내용");
        String originTitle = post.getTitle();
        String newContent = "수정내용";

        // when
        post.update(null, newContent);

        // then
        assertThat(post.getTitle()).isEqualTo(originTitle);
        assertThat(post.getContent()).isEqualTo(newContent);

        // when, then - 빈 제목
        post.update("", newContent);
        assertThat(post.getTitle()).isEqualTo(originTitle);

        // when, then - 공백 제목
        post.update(" ", newContent);
        assertThat(post.getTitle()).isEqualTo(originTitle);
    }
}