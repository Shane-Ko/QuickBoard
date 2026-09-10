# Board Project

Spring Boot + React 로 만든 게시판 웹 애플리케이션.
JWT 기반 stateless 인증, 소유권 기반 인가, REST API 설계 프로젝트

## 주요 특징

- **JWT 기반 무상태 인증** — 세션 없이 동작. `HS256` 서명, Access Token 만료 1시간
- **인증(Authentication) vs 인가(Authorization) 명확한 분리** — 401(인증 실패)과 403(권한 없음)을 상황에 맞게 구분해 응답
- **작성자 위조 방지 설계** — 요청 DTO에서 작성자 필드를 제거하고, 서버가 토큰에서 `userId`를 꺼내 강제 세팅
- **전역 예외 처리** — `@RestControllerAdvice` + `ErrorCode` enum + `ErrorResponse` 로 일관된 JSON 에러 응답
- **필드별 검증 상세 응답** — `@Valid` 실패 시 어느 필드가 왜 실패했는지 클라이언트에 반환
- **환경별 설정 분리** — `dev`(로컬 개발) / `prod`(운영) 프로파일 분리, 민감 정보는 환경변수로 주입

## 기술 스택

### Backend
- Java 17, Spring Boot 3.x
- Spring Security, Spring Data JPA (Hibernate)
- MySQL 8.x, `mysql-connector-j`
- jjwt 0.13.0 (JWT 발급/검증)
- Jakarta Bean Validation
- Gradle, Lombok

### Frontend
- React 18, Vite 5
- React Router 6
- Fetch API (외부 상태 관리 라이브러리 없음, Context + Hooks만 사용)

## 프로젝트 구조
board/
├── src/main/java/io/github/shane_ko/board/
│ ├── config/ SecurityConfig
│ ├── controller/ REST 컨트롤러
│ ├── service/ 비즈니스 로직 + 트랜잭션 + 권한 검사
│ ├── repository/ Spring Data JPA
│ ├── entity/ JPA 엔티티 (Member, Article, Comment)
│ ├── dto/ request / response 분리
│ ├── jwt/ JwtTokenProvider, JwtAuthenticationFilter, EntryPoint
│ └── exception/ ErrorCode, GlobalExceptionHandler, 커스텀 예외
├── src/main/resources/
│ ├── application.yml 공통 설정 + 프로파일 활성화
│ ├── application-dev.properties 개발 환경 (create-drop, 더미 데이터)
│ └── application-prod.properties 운영 환경 (validate, 환경변수 주입)
└── frontend/ React SPA (별도 문서: frontend/FRONTEND.md)
## 실행 방법

### 사전 준비
- JDK 17
- MySQL 8.x
- Node.js 18+ (프론트엔드용)
- 환경변수 `JWT_SECRET` (32자 이상), `DB_PASSWORD`

### 1. 데이터베이스 생성
```sql
CREATE DATABASE board DEFAULT CHARACTER SET utf8mb4;
```

### 2. 환경변수 설정
```bash
# macOS / Linux
export JWT_SECRET="여기에-32자-이상의-충분히-긴-비밀키"
export DB_PASSWORD="본인의-MySQL-비밀번호"

# Windows PowerShell
$env:JWT_SECRET = "여기에-32자-이상의-충분히-긴-비밀키"
$env:DB_PASSWORD = "본인의-MySQL-비밀번호"
```

IntelliJ 사용 시: **Run/Debug Configurations → Environment variables** 에 등록.

### 3. 백엔드 실행
```bash
./gradlew bootRun
# → http://localhost:8080
```

### 4. 프론트엔드 실행
```bash
cd frontend
npm install
npm run dev
# → http://localhost:5173
```

Vite dev 서버가 `/api`, `/auth` 요청을 8080으로 프록시하므로 CORS 설정 없이 동작한다.

## API 명세

| 메서드 | 경로 | 인증 | 설명 |
|---|---|---|---|
| POST | `/api/join` | ❌ | 회원가입 |
| POST | `/auth/login` | ❌ | 로그인 (JWT 발급) |
| GET | `/api/articles` | ❌ | 게시글 전체 조회 |
| GET | `/api/articles/{id}` | ❌ | 게시글 상세 |
| POST | `/api/articles` | ✅ | 게시글 작성 |
| PATCH | `/api/articles/{id}` | ✅ 본인만 | 게시글 수정 |
| DELETE | `/api/articles/{id}` | ✅ 본인만 | 게시글 삭제 |
| GET | `/api/articles/{id}/comments` | ❌ | 댓글 조회 |
| POST | `/api/articles/{id}/comments` | ✅ | 댓글 작성 |
| PATCH | `/api/comments/{id}` | ✅ 본인만 | 댓글 수정 |
| DELETE | `/api/comments/{id}` | ✅ 본인만 | 댓글 삭제 |


## 인증 / 인가 흐름

[로그인]
POST /auth/login (id + pw)
→ BCrypt로 비밀번호 검증
→ JwtTokenProvider.createToken(userId)
→ { token, nickname } 반환

[이후 모든 요청]
Authorization: Bearer <토큰>
→ JwtAuthenticationFilter가 헤더 파싱 및 검증
→ SecurityContext에 Authentication 저장 (principal = userId)
→ Controller에서 @AuthenticationPrincipal Long userId 로 꺼냄
→ Service에서 "이 리소스의 작성자 == userId" 검사 (다르면 403)


**핵심 설계 포인트**:
- 필터는 요청을 직접 거절하지 않고 **SecurityContext만 채운다**. 인가 판정은 뒤의 `AuthorizationFilter`가 담당
- 소유권 검사는 Controller가 아니라 **Service 계층**에서 수행 (호출 경로가 늘어도 검사가 빠지지 않도록)
- 요청 DTO에는 작성자 필드가 없음 (`writer` 필드 제거) → 클라이언트가 작성자를 위조할 수 없는 구조

## 개발 과정에서 신경 쓴 점

### 1. 요청 DTO에서 작성자 필드 제거
초기 설계는 `ArticleCreateRequest`가 `Member` 필드를 담고 있었다. 
클라이언트가 남의 이름으로 글을 쓸 수 있는 취약 구조.
→ **작성자는 반드시 서버가 토큰에서 꺼낸 `userId`로만 결정**하도록 리팩토링.

### 2. 인증 실패 401 응답 커스터마이징
Spring Security의 기본 동작은 인증 실패 시 403을 반환한다. 
REST API 관점에서는 "인증되지 않음(401)"과 "권한 없음(403)"이 명확히 구분되어야 한다.
→ `JwtAuthenticationEntryPoint`를 만들어 인증 실패 시 401 JSON 응답, 
`ForbiddenException`은 403 응답으로 분리.

### 3. 로그인 실패 메시지 통합
"아이디 없음"과 "비밀번호 틀림"을 구분해 응답하면 
공격자가 어떤 계정이 존재하는지 알아낼 수 있다(User Enumeration Attack).
→ 두 경우 모두 `INVALID_CREDENTIALS` (401)로 통합 응답.

### 4. Validation 필드별 상세 응답
`@Valid` 실패 시 기본 응답은 뭉뚱그려진 메시지 하나뿐이었음.
→ `ErrorResponse`에 `FieldError` 리스트를 추가해, 
어느 필드가 왜 실패했는지 클라이언트가 명확히 알 수 있도록 개선.

### 5. XSS 방어를 위한 입력 정규식 검증
회원가입 nickname에 `<script>` 같은 값이 들어가면 프론트 렌더링 시 위험.
→ `@Pattern`으로 서버에서 1차 방어, 프론트에서도 동일 정규식으로 UX 개선.

### 6. 환경별 설정 분리 및 민감 정보 환경변수화
DB 비밀번호와 JWT Secret이 저장소에 노출되지 않도록:
- `application.yml`은 공통 설정(앱 이름, 프로파일 활성화, JWT)만
- `application-dev.properties` / `application-prod.properties`로 환경별 분리
- 민감 정보는 `${JWT_SECRET}`, `${DB_PASSWORD}` 환경변수로 주입
- 과거 커밋 히스토리에 노출됐던 비밀번호는 BFG Repo-Cleaner로 제거 후 force push

