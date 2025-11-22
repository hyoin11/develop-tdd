package com.hyoin.blog.dto;

import com.hyoin.blog.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long userSeq;
    private final String email;
    private final String nickname;

    private UserResponse(Long userSeq, String email, String nickname) {
        this.userSeq = userSeq;
        this.email = email;
        this.nickname = nickname;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserSeq(),
                user.getEmail(),
                user.getNickname()
        );
    }
}
