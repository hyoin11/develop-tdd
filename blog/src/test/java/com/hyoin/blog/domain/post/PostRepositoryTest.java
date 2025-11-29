package com.hyoin.blog.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 저장 성공 테스트")
    void savePost_success() {
        // given
        Post post = Post.builder()
                .userSeq(1L)
                .title("제목")
                .content("내용")
                .build();

        // when
        Post save = postRepository.saveAndFlush(post);

        // then
        assertThat(save.getPostSeq()).isNotNull();
        assertThat(save.getTitle()).isEqualTo("제목");
        assertThat(save.getContent()).isEqualTo("내용");
        assertThat(save.getUserSeq()).isEqualTo(1L);
    }

    @Test
    @DisplayName("회원 정보가 없을 때 저장 실패 테스트")
    void saveNullUser_Fail() {
        // given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();

        // when & then
        assertThatThrownBy(() -> postRepository.saveAndFlush(post)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }
}
