package com.huey.develop_tdd.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("User 생성 성공")
    void createUser_success() {
        String userId = "testId";
        String userPw = "testPw";
        String userEmail = "test@email.com";

        User user = new User(userId, userPw, userEmail);

        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(userPw);
        assertThat(user.getEmail()).isEqualTo(userEmail);
        assertThat(user.getName()).isNull();
        assertThat(user.getId()).isNull();
    }

    @Test
    @DisplayName("User 생성 성공 (이름 포함 - 이름이 null인 경우)")
    void createUser_success_withName_null() {
        String userId = "아이디";
        String userPw = "비밀번호";
        String userEmail = "이메일";

        User user = new User(userId, userPw, userEmail, null);

        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(userPw);
        assertThat(user.getEmail()).isEqualTo(userEmail);
        assertThat(user.getName()).isNull();
        assertThat(user.getId()).isNull();
    }

    @Test
    @DisplayName("User 생성 성공 (이름 포함 - 이름이 빈 문자열인 경우)")
    void createUser_success_withName_empty() {
        String userId = "아이디";
        String userPw = "비밀번호";
        String userEmail = "이메일";
        String userName = "";

        User user = new User(userId, userPw, userEmail, userName);

        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(userPw);
        assertThat(user.getEmail()).isEqualTo(userEmail);
        assertThat(user.getName()).isNull();
        assertThat(user.getId()).isNull();
    }

    @Test
    @DisplayName("User 생성 성공 (이름 포함- 이름이 공백인 경우)")
    void createUser_success_withName_blank() {
        String userId = "아이디";
        String userPw = "비밀번호";
        String userEmail = "이메일";
        String userName = " ";

        User user = new User(userId, userPw, userEmail, userName);

        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(userPw);
        assertThat(user.getEmail()).isEqualTo(userEmail);
        assertThat(user.getName()).isNull();
        assertThat(user.getId()).isNull();
    }

    @Test
    @DisplayName("User 생성 성공 (이름 포함)")
    void createUser_success_withName() {
        String userId = "아이디";
        String userPw = "비밀번호";
        String userEmail = "이메일";
        String userName = "이름";

        User user = new User(userId, userPw, userEmail, userName);

        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getPassword()).isEqualTo(userPw);
        assertThat(user.getEmail()).isEqualTo(userEmail);
        assertThat(user.getName()).isEqualTo(userName);
        assertThat(user.getId()).isNull();
    }

    @DisplayName("User 생성 실패 - 아이디 누락")
    @ParameterizedTest
    @NullAndEmptySource // null과 "" 테스트
    @ValueSource(strings = {" "})   // 공백 문자열 테스트
    void createUser_fail_noId(String invalidUserId) {
        String userPw = "testPw";
        String userEmail = "test@email.com";

        assertThatThrownBy(() -> new User(invalidUserId, userPw, userEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("User 생성 실패 - 비밀번호 누락")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void createUser_fail_noPw(String invalidUserPw) {
        String userId = "testId";
        String userEmail = "test@email.com";

        assertThatThrownBy(() -> new User(userId, invalidUserPw, userEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("User 생성 실패 - 이메일 누락")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void createUser_fail_noEmail(String invalidUserEmail) {
        String userId = "testId";
        String userPw = "testPw";

        assertThatThrownBy(() -> new User(userId, userPw, invalidUserEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("User 수정 성공(비밀번호, 이메일)")
    void updateUser_success() {
        User user = new User("아이디", "비밀번호", "이메일");
        String newPw = "새로운비밀번호";
        String newEmail = "새로운이메일";

        user.update(newPw, newEmail, null);

        assertThat(user.getPassword()).isEqualTo(newPw);
        assertThat(user.getEmail()).isEqualTo(newEmail);
        assertThat(user.getName()).isNull();
    }

    @Test
    @DisplayName("User 수정 성공 (비밀번호만)")
    void updateUser_success_password() {
        User user = new User("아이디", "비밀번호", "이메일");
        String newPw = "새로운비밀번호";
        String originEmail = user.getEmail();

        user.update(newPw, originEmail, null);

        assertThat(user.getPassword()).isEqualTo(newPw);
        assertThat(user.getEmail()).isEqualTo(originEmail);
        assertThat(user.getName()).isNull();

        // -----
        user = new User("아이디", "비밀번호", "이메일");

        user.update(newPw, "", null);

        assertThat(user.getPassword()).isEqualTo(newPw);
        assertThat(user.getEmail()).isEqualTo(originEmail);
        assertThat(user.getName()).isNull();

        // -----
        user = new User("아이디", "비밀번호", "이메일");

        user.update(newPw, " ", null);

        assertThat(user.getPassword()).isEqualTo(newPw);
        assertThat(user.getEmail()).isEqualTo(originEmail);
        assertThat(user.getName()).isNull();
    }

    @Test
    @DisplayName("User 수정 성공 (이메일만)")
    void updateUser_success_email() {
        User user = new User("아이디", "비밀번호", "이메일");
        String originPw = user.getPassword();
        String newEmail = "새로운이메일";

        user.update(originPw, newEmail, null);

        assertThat(user.getPassword()).isEqualTo(originPw);
        assertThat(user.getEmail()).isEqualTo(newEmail);
        assertThat(user.getName()).isNull();

        // -----
        user = new User("아이디", "비밀번호", "이메일");

        user.update("", newEmail, null);

        assertThat(user.getPassword()).isEqualTo(originPw);
        assertThat(user.getEmail()).isEqualTo(newEmail);
        assertThat(user.getName()).isNull();

        // -----
        user = new User("아이디", "비밀번호", "이메일");

        user.update(" ", newEmail, null);

        assertThat(user.getPassword()).isEqualTo(originPw);
        assertThat(user.getEmail()).isEqualTo(newEmail);
        assertThat(user.getName()).isNull();
    }

    @Test
    @DisplayName("User 수정 성공 (이름만)")
    void updateUser_success_name() {
        User user = new User("아이디", "비밀번호", "이메일", "이름");
        String originPw = user.getPassword();
        String originEmail = user.getEmail();
        String newName = "새로운이름";

        user.update(originPw, originEmail, newName);

        assertThat(user.getPassword()).isEqualTo(originPw);
        assertThat(user.getEmail()).isEqualTo(originEmail);
        assertThat(user.getName()).isEqualTo(newName);

        // -----
        user = new User("아이디", "비밀번호", "이메일", "이름");

        user.update("", "", newName);

        assertThat(user.getPassword()).isEqualTo(originPw);
        assertThat(user.getEmail()).isEqualTo(originEmail);
        assertThat(user.getName()).isEqualTo(newName);

        // -----
        user = new User("아이디", "비밀번호", "이메일", "이름");

        user.update(" ", " ", newName);

        assertThat(user.getPassword()).isEqualTo(originPw);
        assertThat(user.getEmail()).isEqualTo(originEmail);
        assertThat(user.getName()).isEqualTo(newName);
    }
}
