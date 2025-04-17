package com.huey.develop_tdd.repository;

import com.huey.develop_tdd.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
