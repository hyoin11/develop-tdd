package com.hyoin.blog.service.user;

import com.hyoin.blog.domain.user.User;
import com.hyoin.blog.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUp_Success() {
        // given
        String email = "test@email.com";
        String password = "password";
        String nickname = "test";

        // when
        User saved = userService.signUp(email, password, nickname);

        // then
        assertThat(saved.getEmail()).isEqualTo(email);
        assertThat(saved.getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("중복 이메일 가입 실패 테스트")
    void signUp_email_Fail() {
        // given
        userRepository.save(User.builder().email("test@email.com").password("password").nickname("test").build());

        // when & then
        assertThatThrownBy(() -> userService.signUp("test@email.com", "password", "test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용중인 이메일입니다");
    }

    @Test
    @DisplayName("중복 닉네임 가입 실패 테스트")
    void signUp_nickname_File() {
        // given
        userRepository.save(User.builder().email("test@email.com").password("password").nickname("test").build());

        // when & then
        assertThatThrownBy(() -> userService.signUp("test2@email.com", "password", "test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용중인 닉네임입니다");
    }

    @Test
    @DisplayName("회원 조회 성공 테스트")
    void getUser_Success() {
        // given
        User saved = userRepository.save(User.builder()
                .email("test@email.com")
                .password("password")
                .nickname("nickname")
                .build());

        // when
        User find = userService.getUser(saved.getUserSeq());

        // then
        assertThat(find.getEmail()).isEqualTo(saved.getEmail());
        assertThat(find.getNickname()).isEqualTo(saved.getNickname());
    }

    @Test
    @DisplayName("회원 조회 실패 테스트")
    void getUser_Fail() {
        // given
        Long invalidSeq = 999L;

        // when & then
        assertThatThrownBy(() -> userService.getUser(invalidSeq))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 회원입니다");
    }

    @Test
    @DisplayName("회원 닉네임 변경 성공 테스트")
    void updateNickname_Success() {
        // given
        User saved = userRepository.save(User.builder()
                .email("test@email.com")
                .password("password")
                .nickname("test")
                .build());
        String newNickname = "nickname";

        // when
        userService.updateNickname(saved.getUserSeq(), newNickname);
        User updated = userRepository.findById(saved.getUserSeq()).orElseThrow();

        // then
        assertThat(updated.getNickname()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("회원 닉네임 변경 실패 테스트")
    void updateNickname_Fail() {
        // given
        userRepository.save(User.builder()
                .email("test@email.com")
                .password("password")
                .nickname("test")
                .build());

        User user = userRepository.save(User.builder()
                .email("test2@email.com")
                .password("password2")
                .nickname("test2")
                .build());

        // when & then
        assertThatThrownBy(() -> userService.updateNickname(user.getUserSeq(), "test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용중인 닉네임입니다");
    }

    @Test
    @DisplayName("회원 삭제 성공 테스트")
    void deleteUser_Success() {
        // given
        User user = userRepository.save(User.builder()
                .email("test@email.com")
                .password("password")
                .nickname("test")
                .build());

        // when
        userService.deleteUser(user.getUserSeq());

        // then
        assertThat(userRepository.findById(user.getUserSeq())).isEmpty();
    }

    @Test
    @DisplayName("회원 삭제 실패 테스트")
    void deleteUser_Fail() {
        // given
        Long invalidSeq = 999L;

        // when & then
        assertThatThrownBy(() -> userService.deleteUser(invalidSeq))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 회원입니다");
    }
}
