# 댓글 API
### 목차
- [댓글 목록](#댓글-목록)
- [댓글 작성](#댓글-작성)
- [댓글 수정](#댓글-수정)
- [댓글 삭제](#댓글-삭제)

### 댓글 목록
- **URL**: `/api/v1/posts/{postId}/comments`
- **Method**: `GET`
  - **참고**: 초기 버전에서는 '게시글 상세 조회' 시 댓글 정보를 함께 반환<br>댓글 수가 증가함에 따라 향후 별도의 댓글 목록 API로 분리 에정
- Request: null
- Response:
    - 성공
        - status code: 200 OK

          | 이름          | 설명    | 타입         | 비고 |
          |-------------|-------|------------|----|
          | total_count | 전체 개수 | integer    |    |
          | comments    | 댓글 정보 | Comments[] |    |
        
        - Comments

          | 이름          | 설명            | 타입        | 비고 |
          |-------------|---------------|-----------|----|
          | comment_seq | 게시글 순번        | integer   |    |
          | content     | 내용            | string    |    |
          | created_at  | 작성일           | datetime  |    |
          | users       | 회원 정보         | Users[]   |    |
          | replies     | 대댓글 정보        | Replies[] |    |
          | is_owner    | 로그인 사용자 댓글 여부 | boolean   |    |
      
        - Users

        | 이름       | 설명    | 타입         | 비고 |
      |----------|-------|------------|----|
        | user_seq | 회원 순번 | integer    |    |
        | nickname | 닉네임   | string     |    |
        - Replies

          | 이름                 | 설명            | 타입       | 비고 |
          |--------------------|---------------|----------|----|
          | comment_seq        | 게시글 순번        | integer  |    |
          | parent_comment_seq | 부모 댓글 순번      | integer  |    |
          | content            | 내용            | string   |    |
          | created_at         | 작성일           | datetime |    |
          | users              | 회원 정보         | Users[]  |    |
          | is_owner           | 로그인 사용자 댓글 여부 | boolean  |    |
    - 실패
        - status code
            - 400 Bad Request (잘못된 요청)

### 댓글 작성
- **URL**: `/api/v1/posts/{postId}/comments`
- **Method**: `POST`
- Request:
    - headers

      | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
            |---------------|---------------------------------------------------------------|--------|------|----|
      | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
    - body

      | 이름                 | 설명       | 타입      | 필수여부 | 비고         |
      |--------------------|----------|---------|------|------------|
      | content            | 내용       | string  | ✅    |            |
      | parent_comment_seq | 부모 댓글 순번 | integer |      | 대댓글일 경우 필수 |
- Response:
    - 성공
        - status code: 201 Created

          | 이름          | 설명    | 타입      | 비고 |
          |-------------|-------|---------|----|
          | comment_seq | 댓글 순번 | integer |    |
    - 실패
        - status code
            - 400 Bad Request (유효성 검증 실패)
            - 401 Unauthorized (로그인 필요)

### 댓글 수정
- **URL**: `/api/v1/comments/{commentId}`
- **Method**: `PATCH`
- Request:
    - headers

      | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
                  |---------------|---------------------------------------------------------------|--------|------|----|
      | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
    - body

      | 이름          | 설명    | 타입      | 필수여부 | 비고 |
            |-------------|-------|---------|------|----|
      | content     | 내용    | string  | ✅    |    |
- Response:
    - 성공
        - status code: 200 OK

          | 이름          | 설명    | 타입      | 비고 |
                    |-------------|-------|---------|----|
          | comment_seq | 댓글 순번 | integer |    |
    - 실패
        - status code
            - 400 Bad Request (유효성 검증 실패)
            - 401 Unauthorized (로그인 필요)

### 댓글 삭제
- **URL**: `/api/v1/comments/{commentId}`
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
