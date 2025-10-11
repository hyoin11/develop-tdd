# 좋아요 API
### 목차

### 게시글 좋아요
- **URL**: `/api/v1/posts/{postId}/like`
- **Method**: `POST`
- Request:
    - headers

      | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
            |---------------|---------------------------------------------------------------|--------|------|----|
      | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
- Response:
    - 성공
        - status code: 201 Created

          | 이름       | 설명     | 타입      | 비고 |
                    |----------|--------|---------|----|
          | like_seq | 좋아요 순번 | integer |    |
    - 실패
        - status code
            - 400 Bad Request (유효성 검증 실패)
            - 401 Unauthorized (로그인 필요)

### 게시글 좋아요 취소
- **URL**: `/api/v1/posts/{postId}/like`
- **Method**: `DELETE`
- Request:
    - headers

      | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
                  |---------------|---------------------------------------------------------------|--------|------|----|
      | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
- Response:
    - 성공
        - status code: 204 No Content
    - 실패
        - status code
            - 400 Bad Request (유효성 검증 실패)
            - 401 Unauthorized (로그인 필요)
