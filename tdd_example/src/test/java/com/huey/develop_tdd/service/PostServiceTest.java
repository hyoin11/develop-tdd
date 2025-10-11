package com.huey.develop_tdd.service;

import com.huey.develop_tdd.domain.Post;
import com.huey.develop_tdd.dto.PostCreateRequestDto;
import com.huey.develop_tdd.dto.PostResponseDto;
import com.huey.develop_tdd.dto.PostUpdateRequestDto;
import com.huey.develop_tdd.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시글 생성 - 성공")
    void createPost_Success() {
        // given
        final String title = "서비스 테스트 제목";
        final String content = "서비스 테스트 내용";
        PostCreateRequestDto requestDto = new PostCreateRequestDto(title, content);

        // PostRepository.save() 가 호출될 때 반환될 가짜 Post 객체 준비
        // ID 할당 시뮬레이션 (리플렉션 사용 예시)
        given(postRepository.save(ArgumentMatchers.any(Post.class))).willAnswer(invocation -> {
            Post postToSave = invocation.getArgument(0);
            Field idField = Post.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(postToSave, 1L);    // ID 1L 할당 시뮬레이션
            return postToSave;
        });

        // when
        PostResponseDto responseDto = postService.createPost(requestDto);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContent()).isEqualTo(content);

        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("게시글 단건 조회 - 성공")
    void findPostById_Success() {
        // given
        Long postId = 1L;
        Post existingPost = new Post("조회용 제목", "조회용 내용");
        // ID 설정
        try {
            Field idField = Post.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existingPost, postId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        given(postRepository.findById(postId)).willReturn(Optional.of(existingPost));

        // when
        PostResponseDto responseDto = postService.findPostById(postId);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(postId);
        assertThat(responseDto.getTitle()).isEqualTo(existingPost.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(existingPost.getContent());

        verify(postRepository).findById(postId);
    }

    @Test
    @DisplayName("게시글 단건 조회 - 실패 (존재하지 않는 ID)")
    void findPostById_Fail_NotFound() {
        // given
        Long nonExistingId = 99L;
        given(postRepository.findById(nonExistingId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> postService.findPostById(nonExistingId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Post not found with id: " + nonExistingId);

        verify(postRepository).findById(nonExistingId);
    }

    @Test
    @DisplayName("게시글 수정 - 성공")
    void updatePost_Success() {
        // given
        Long postId = 1L;
        String originTitle = "원본 제목";
        String originContent = "원본 내용";
        Post existingPost = new Post(originTitle, originContent);   // 원본 Post 객체
        // ID 설정
        try {
            Field idField = Post.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existingPost, postId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String updatedTitle = "수정된 제목";
        String updatedContent = "수정된 내용";
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto(updatedTitle, updatedContent);

        given(postRepository.findById(postId)).willReturn(Optional.of(existingPost));
        // update 메서드 내에서 save를 명시적으로 호출한다면 mocking 필요
        // 여기서는 변경 감지만 사용한다고 가정하고 save mocking 생략
        // (만약 save를 호출한다면) given(postRepository.save(any(Post.class))).willReturn(existingPost);

        // when
        PostResponseDto responseDto = postService.updatePost(postId, requestDto);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(postId);
        assertThat(responseDto.getTitle()).isEqualTo(updatedTitle);
        assertThat(responseDto.getContent()).isEqualTo(updatedContent);

        // Verify findById was called
        verify(postRepository).findById(postId);

        /*
        아래 코드는 Post 엔티티의 update 메서드를 올바른 데이터로 호출했는지 직접 확인 (optional)
        더 깊은 수준의 검증 방법(상호작용 테스트)를 보여줌
        PostEntityTest 에서 Post.update 로직 검증 완료. 최종 결과(DTO)를 확인하는 상태 테스트만으로도 충분하다고 판단될 수 있으며, 기술적인 구현 복잡성 때문에 아래 테스트는 선택사항임
        update 메서드 호출 시 특별한 로직이 더 있거나, 상호작용 자체가 매우 중요할 때 아래 검증이 유용할 수 있음
         */
//        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> contentCaptor = ArgumentCaptor.forClass(String.class);
//        verify(existingPost).update(titleCaptor.capture(), contentCaptor.capture());    // 검증 가능하려면 existingPost가 mock/spy 거나 Post.update여야 함
//        assertThat(titleCaptor.getValue()).isEqualTo(updatedTitle);
//        assertThat(contentCaptor.getValue()).isEqualTo(updatedContent);
    }

    @Test
    @DisplayName("게시글 수정 - 실패 (존재하지 않는 ID)")
    void updatePost_Fail_NotFound() {
        // given
        Long nonExistingId = 99L;
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto("수정 시도 제목", "수정 시도 내용");
        given(postRepository.findById(nonExistingId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> postService.updatePost(nonExistingId, requestDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Post not found with id: " + nonExistingId);

        verify(postRepository).findById(nonExistingId);
    }

    @Test
    @DisplayName("게시글 삭제 - 성공")
    void deletePost_Success() {
        // given
        Long postId = 1L;
        // 삭제 대상이 존재하는지 확인하기 위해 existsById 또는 findById mocking
        given(postRepository.existsById(postId)).willReturn(true);
        // deleteById는 void 메서드이므로, 예외 없이 정상 호출되는지만 검증
        doNothing().when(postRepository).deleteById(postId);    // deleteById 호출 시 아무것도 안하도록 설정

        // when
        postService.deletePost(postId);

        // then
        verify(postRepository).existsById(postId);
        verify(postRepository).deleteById(postId);
    }

    @Test
    @DisplayName("게시글 삭제 - 실패 (존재하지 않는 ID)")
    void deletePost_Fail_NotFound() {
        //given
        Long nonExistingId = 99L;
        given(postRepository.existsById(nonExistingId)).willReturn(false);

        // when ,then
        assertThatThrownBy(() -> postService.deletePost(nonExistingId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Post not found with id: " + nonExistingId);

        verify(postRepository).existsById(nonExistingId);   // 존재 확인 호출 검증
        // 존재하지 않으므로 deleteById는 호출되지 않아야 함
        verify(postRepository, never()).deleteById(nonExistingId);
    }
}
