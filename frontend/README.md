# board-frontend

React + Vite 게시판 프론트엔드.

## 실행

```bash
npm install
npm run dev      # http://localhost:5173
```

백엔드(Spring Boot)는 `http://localhost:8080` 에서 실행되어 있어야 한다.
백엔드에 CORS 설정이 없어 `vite.config.js` 의 프록시로 `/api`, `/auth` 를 8080 으로 넘긴다.

## 참고

1. **로그인 경로** — `POST /auth/login` (백엔드 `AuthController` 매핑 기준).
2. **닉네임** — `TokenResponse` 의 `nickname` 을 로그인 응답에서 바로 받는다.
3. **목록 페이지네이션** — 프론트에서 처리한다. `GET /api/articles` 로 전체를 받아
   15개씩 잘라 보여준다. 글이 수천 건 규모가 되면 그때 서버 페이징(`?page=&size=`)으로
   옮기는 것을 고려한다.
4. 작성자 판별은 응답의 `writer`(닉네임) 비교로 하고, 최종 판단은 서버에 맡긴다.
   서버의 401/403 응답은 각각 "로그인 필요합니다" / "권한없음" 모달로 변환한다.
