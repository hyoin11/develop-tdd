package com.hyoin.blog.controller.user;

import com.hyoin.blog.domain.user.User;
import com.hyoin.blog.dto.UserResponse;
import com.hyoin.blog.dto.UserSignUpRequest;
import com.hyoin.blog.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<UserResponse> signUp(@RequestBody UserSignUpRequest request) {
        User user = userService.signUp(
                request.getEmail(),
                request.getPassword(),
                request.getNickname()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.from(user));
    }
}
