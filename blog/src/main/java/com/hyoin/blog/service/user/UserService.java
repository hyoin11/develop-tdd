package com.hyoin.blog.service.user;

import com.hyoin.blog.domain.user.User;
import com.hyoin.blog.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원 등록
    public User signUp(String email, String password, String nickname) {
        // 필수 값 검증
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수 값입니다");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수 값입니다");
        }
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("닉네임은 필수 값입니다");
        }
        // 중복 이메일 검증
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다");
        }

        // 중복 닉네임 검증
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다");
        }

        // 저장
        User user = User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
        return userRepository.save(user);
    }

    // 회원 조회 (seq)
    public User getUser(Long userSeq) {
        return userRepository.findById(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
    }

    // 회원 닉네임 수정
    public void updateNickname(Long userSeq, String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다");
        }

        User user = getUser(userSeq);
        user.updateNickname(nickname);
    }

    // 회원 삭제
    public void deleteUser(Long userSeq) {
        if (!userRepository.existsById(userSeq)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다");
        }
        userRepository.deleteById(userSeq);
    }
}
