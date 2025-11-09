package com.hyoin.blog.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 정보가 DB에 저장되고, 다시 조회된다")
    void saveAndFindUser() {
        // given
        User user = User.builder()
                .email("test@email.com")
                .password("1234")
                .nickname("test")
                .build();

        // when
        User saved = userRepository.save(user);
        User found = userRepository.findById(saved.getUserSeq()).orElseThrow();

        // then
        assertThat(found.getEmail()).isEqualTo(saved.getEmail());
        assertThat(found.getNickname()).isEqualTo(saved.getNickname());
        assertThat(found.getStatus()).isEqualTo(User.Status.ACTIVE);
    }
}
