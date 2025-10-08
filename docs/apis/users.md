# 회원 API
### 목차
- [회원가입](#회원가입)
- [회원정보 조회](#회원정보-조회)
- [회원정보 수정](#회원정보-수정)
- [회원 탈퇴](#회원-탈퇴)

### 회원가입
- **URL**: `/api/v1/users`
- **Method**: `POST`
- Request:
  - body
  
    | 이름       | 설명   | 타입     | 필수여부 | 비고  |
    |----------|------|--------|------|-----|
    | email    | 이메일  | string | ✅    | 아이디 |
    | password | 비밀번호 | string | ✅    |     |
    | nickname | 닉네임  | string | ✅    |     |

- Response:
  - 성공
    - status code: 201 Created
     
      | 이름       | 설명    | 타입      | 비고 |
      |----------|-------|---------|----|
      | user_seq | 회원 순번 | integer |    |
      | email    | 이메일   | string  |    |
      | nickname | 닉네임   | string  |    |
  - 실패
    - status code
      - 400 Bad Request (유효성 검증 실패)
      - 409 Conflict (이메일 또는 닉네임 중복)

### 회원정보 조회
- **URL**: `/api/v1/users/me`
- **Method**: `GET`
- Request: 
  - headers
  
    | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
    |---------------|---------------------------------------------------------------|--------|------|----|
    | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
- Response:
  - 성공
    - status code: 200 OK
    
      | 이름       | 설명    | 타입      | 비고 |
      |----------|-------|---------|----|
      | user_seq | 회원 순번 | integer |    |
      | email    | 이메일   | string  |    |
      | nickname | 닉네임   | string  |    |
  - 실패
    - status code
      - 401 Unauthorized (로그인 필요)

### 회원정보 수정
- **URL**: `/api/v1/users/me`
- **Method**: `PATCH`
- Request:
  - headers
  
    | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
    |---------------|---------------------------------------------------------------|--------|------|----|
    | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
  - body
  
    | 이름       | 설명   | 타입     | 필수여부 | 비고 |
    |----------|------|--------|------|----|
    | password | 비밀번호 | string |      |    |
    | nickname | 닉네임  | string |      |    |
- Response:
  - 성공
    - status code: 200 OK
    
      | 이름       | 설명    | 타입      | 비고 |
      |----------|-------|---------|----|
      | user_seq | 회원 순번 | integer |    |
      | email    | 이메일   | string  |    |
      | nickname | 닉네임   | string  |    |
  - 실패
    - status code
      - 400 Bad Request (유효성 검증 실패)
      - 409 Conflict (닉네임 중복)

### 회원 탈퇴
- **URL**: `/api/v1/users/me`
- **Method**: `DELETE`
- Request
  - headers
    
    | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
    |---------------|---------------------------------------------------------------|--------|------|----|
    | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
- Response:
  - 성공
    - status code: 204 No Content
  - 실패
    - status code
      - 401 Unauthorized (로그인 안함)
      - 404 Not Found (이미 탈퇴 처리된 계정)
