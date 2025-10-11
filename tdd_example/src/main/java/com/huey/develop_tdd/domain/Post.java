package com.huey.develop_tdd.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity                 // JPA 엔티티임을 선언
@Table(name = "posts")  // 데이터베이스 테이블 이름을 'posts'로 지정
public class Post {

    @Id // 기본 키(PK) 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임 (Auto Increment)
    private Long id;

    @Column(nullable = false, length = 100) // null 허용 안함, 길이 100
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")    // null 허용 안함, 긴 텍스트 타입
    private String content;

    public Post() {
    }

    public Post(String title, String content) {
        // 방어 로직 추가 (title, content null 체크)
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
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

    public void update(String title, String content) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }

    // equals() 와 hashCode() - ID 기반으로 구현하는 것이 일반적
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Post post = (Post) obj;
        // 아직 영속화되지 않은 엔티티는 id가 null일 수 있으므로 주의
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        // 아직 영속화되니 않은 엔티티는 id가 null일 수 있으므로 주의
        return Objects.hash(id);
    }
}
