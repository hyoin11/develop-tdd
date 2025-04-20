package com.huey.develop_tdd.service;

import com.huey.develop_tdd.domain.Post;
import com.huey.develop_tdd.dto.PostCreateRequestDto;
import com.huey.develop_tdd.dto.PostResponseDto;
import com.huey.develop_tdd.dto.PostUpdateRequestDto;
import com.huey.develop_tdd.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    // 생성자 주입
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto requestDto) {
        Post post = requestDto.toEntity();  // DTO -> Entity 변환 (Post 생성자 TDD로 검증됨)
        Post savedPost = postRepository.save(post);
        return PostResponseDto.fromEntity(savedPost);
    }

    @Transactional(readOnly = true) // 조회는 readOnly 트랜잭션
    public PostResponseDto findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
        return PostResponseDto.fromEntity(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDto) {
        // 1. 게시글 조회 (없으면 예외 발생 - findPostBYId 내부에서 처리됨. 재사용 가능)
        //  또는 직접 여기서 조회 로직 작성
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

        // 2. 게시글 업데이트 (Post 객체의 update 메서드 사용 - TDD 로 검증됨)
        post.update(requestDto.getTitle(), requestDto.getContent());

        // 3. 변경 감지에 의해 트랜잭션 커밋 시점에 DB 업데이트
        //  만약 명시적 save 가 필요하다면 여기서 postRepository.save(post) 호출

        // 4. 업데이트 된 정보를 DTO로 변환하여 반환
        return PostResponseDto.fromEntity(post);
    }

    @Transactional
    public void deletePost(Long id) {
        // 1. 게시글 존재 확인
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }

        // 2. 존재하면 삭제
        postRepository.deleteById(id);
    }
}
