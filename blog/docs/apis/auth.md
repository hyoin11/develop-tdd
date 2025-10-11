# 인증/인가 API
### 목차
- [로그인](#로그인)
- [토큰 재발급](#토큰-재발급)
- [로그아웃](#로그아웃)

### 로그인
- **URL**: `/api/v1/auth/login`
- **Method**: `POST`
- Request:
  - body
  
    | 이름       | 설명   | 타입     | 필수여부 | 비고   |
    |----------|------|--------|------|------|
    | email    | 이메일  | string | ✅    | 아이디  |
    | password | 비밀번호 | string | ✅    |      |
- Response:
    - 성공
        - status code: 200 OK
          - headers
          
            | 이름            | 설명                                             | 타입     | 비고 |
            |---------------|------------------------------------------------|--------|----|
            | Authorization | Authorization: Bearer <access_token><br>액세스 토큰 | string |    |
            | Refresh-Token | <refresh_token><br>리프레쉬 토큰                     | string |    |
          - body
          
            | 이름       | 설명    | 타입      | 비고 |
            |----------|-------|---------|----|
            | user_seq | 회원 순번 | integer |    |
            | email    | 이메일   | string  |    |
            | nickname | 닉네임   | string  |    |
    - 실패
        - status code
            - 400 Bad Request (유효성 검증 실패)
            - 401 Unauthorized (이메일 또는 비밀번호 불일치)

### 토큰 재발급
- **URL**: `/api/v1/auth/refresh`
- **Method**: `POST`
- Request: 
  - headers
  
    | 이름            | 설명                                             | 타입     | 필수여부 | 비고 |
    |---------------|------------------------------------------------|--------|------|----|
    | Refresh-Token | <refresh_token><br>리프레쉬 토큰                     | string | ✅    |    |
- Response:
    - 성공
        - status code: 200 OK
          - headers
          
            | 이름            | 설명                                             | 타입     | 비고 |
            |---------------|------------------------------------------------|--------|----|
            | Authorization | Authorization: Bearer <access_token><br>액세스 토큰 | string |    |
            | Refresh-Token | <refresh_token><br>리프레쉬 토큰                     | string |    |

### 로그아웃
- **URL**: `/api/v1/auth/logout`
- **Method**: `POST`
- Request: 
  - headers
     
    | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
    |---------------|---------------------------------------------------------------|--------|------|----|
    | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |

- Response:
    - 성공
        - status code: 204 No Content
