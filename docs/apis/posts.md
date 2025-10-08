# 게시글 API
### 목차
- [게시글 목록 조회](#게시글-목록-조회)
- [게시글 작성](#게시글-작성)
- [게시글 상세](#게시글-상세)
- [게시글 수정](#게시글-수정)
- [게시글 삭제](#게시글-삭제)
- [인기 게시글 목록 조회](#인기-게시글-목록-조회)

### 게시글 목록 조회
- **URL**: `/api/v1/posts`
- **Method**: `GET`
- Request:
  - query parameter
     
     | 이름       | 설명           | 타입      | 필수여부 | 비고         |
      |----------|--------------|---------|------|------------|
     | size     | 한 페이지당 목록 개수 | integer |      | 값 없을 경우 10 |
     | page     | 현재 페이지       | integer |      | 값 없을 경우 1  |
      | title    | 제목           | string  |      |            |
      | nickname | 닉네임          | string  |      |            |
      | content  | 내용           | string  |      |            |
- Response:
    - 성공
        - status code: 200 OK
           
          | 이름          | 설명           | 타입      | 비고 |
            |-------------|--------------|---------|----|
            | size        | 한 페이지당 목록 개수 | integer |    |
            | page        | 현재 페이지       | integer |    |
            | total_count | 전체 개수        | integer |    |
            | posts       | 게시글 목록       | Posts[] |    |
        - Posts
      
          | 이름            | 설명      | 타입       | 비고 |
            |---------------|---------|----------|----|
            | post_seq      | 게시글 순번  | integer  |    |
            | title         | 제목      | string   |    |
            | nickname      | 닉네임     | string   |    |
            | created_at    | 게시글 작성일 | datetime |    |
            | view_count    | 조회수     | integer  |    |
            | comment_count | 댓글수     | integer  |    |
    - 실패
        - status code
          - 400 Bad Request (잘못된 요청)

### 게시글 작성
- **URL**: `/api/v1/posts`
- **Method**: `POST`
- Request:
    - headers

      | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
      |---------------|---------------------------------------------------------------|--------|------|----|
      | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
    - body

      | 이름       | 설명     | 타입      | 필수여부 | 비고        |
            |----------|--------|---------|------|-----------|
      | title    | 제목     | string  | ✅    |           |
      | content  | 내용     | string  | ✅    |           |
- Response:
    - 성공
        - status code: 201 Created

          | 이름       | 설명     | 타입      | 비고 |
          |----------|--------|---------|----|
          | post_seq | 게시글 순번 | integer |    |
  - 실패
      - status code
        - 400 Bad Request (유효성 검증 실패)
        - 401 Unauthorized (로그인 필요)

### 게시글 상세
- **URL**: `/api/v1/posts/{postId}`
- **Method**: `GET`
- Request: null
- Response:
    - 성공
        - status code: 200 OK

          | 이름         | 설명             | 타입         | 비고 |
          |------------|----------------|------------|----|
          | post_seq   | 게시글 순번         | integer    |    |
          | title      | 제목             | string     |    |
          | content    | 내용             | string     |    |
          | view_count | 조회수            | integer    |    |
          | created_at | 작성일            | datetime   |    |
          | users      | 회원 정보          | Users[]    |    |
          | is_owner   | 로그인 사용자 게시글 여부 | boolean    |    |
          | like_count | 좋아요수           | integer    |    |
          | comments   | 댓글 정보          | Comments[] |    |
        - Users
           
          | 이름       | 설명    | 타입         | 비고 |
            |----------|-------|------------|----|
            | user_seq | 회원 순번 | integer    |    |
            | nickname | 닉네임   | string     |    |
        - Comments
      
          | 이름          | 설명            | 타입        | 비고 |
            |-------------|---------------|-----------|----|
            | comment_seq | 게시글 순번        | integer   |    |
            | content     | 내용            | string    |    |
            | created_at  | 작성일           | datetime  |    |
            | users       | 회원 정보         | Users[]   |    |
            | replies     | 대댓글 정보        | Replies[] |    |
            | is_owner    | 로그인 사용자 댓글 여부 | boolean   |    |
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

### 게시글 수정
- **URL**: `/api/v1/posts/{postId}`
- **Method**: `PATCH`
- Request:
    - headers

      | 이름            | 설명                                                            | 타입     | 필수여부 | 비고 |
      |---------------|---------------------------------------------------------------|--------|------|----|
      | Authorization | Authorization: Bearer <access_token><br>인증 방식, 액세스 토큰으로 인증 요청 | string | ✅    |    |
    - body

      | 이름       | 설명     | 타입      | 필수여부 | 비고        |
      |----------|--------|---------|------|-----------|
      | title    | 제목     | string  | ✅    |           |
      | content  | 내용     | string  | ✅    |           |
- Response:
    - 성공
        - status code: 200 OK

          | 이름       | 설명     | 타입      | 비고 |
          |----------|--------|---------|----|
          | post_seq | 게시글 순번 | integer |    |
    - 실패
        - status code
            - 400 Bad Request (유효성 검증 실패)
            - 401 Unauthorized (로그인 필요)

### 게시글 삭제
- **URL**: `/api/v1/posts/{postId}`
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

### 인기 게시글 목록 조회
- **URL**: `/api/v1/posts/popular`
- **Method**: `GET`
- Request: null
- Response:
    - 성공
        - status code: 200 OK

          | 이름          | 설명           | 타입      | 비고 |
          |-------------|--------------|---------|----|
          | total_count | 전체 개수        | integer |    |
          | posts       | 게시글 목록       | Posts[] |    |
        - Posts

          | 이름            | 설명      | 타입       | 비고 |
          |---------------|---------|----------|----|
          | post_seq      | 게시글 순번  | integer  |    |
          | title         | 제목      | string   |    |
          | nickname      | 닉네임     | string   |    |
          | created_at    | 게시글 작성일 | datetime |    |
          | view_count    | 조회수     | integer  |    |
          | comment_count | 댓글수     | integer  |    |
    - 실패
        - status code
          - 404 Not Found (데이터 없음 - 초기 구동 시 발생)
