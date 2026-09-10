const TOKEN_KEY = 'board.token';
const USER_KEY = 'board.user';

export const tokenStore = {
  get: () => localStorage.getItem(TOKEN_KEY),
  set: (token) => localStorage.setItem(TOKEN_KEY, token),
  clear: () => localStorage.removeItem(TOKEN_KEY),
};

export const userStore = {
  get: () => {
    try {
      return JSON.parse(localStorage.getItem(USER_KEY));
    } catch {
      return null;
    }
  },
  set: (user) => localStorage.setItem(USER_KEY, JSON.stringify(user)),
  clear: () => localStorage.removeItem(USER_KEY),
};

/*
  서버 ErrorResponse 형식
  { code, message, status, timestamp, path, errors: [{field, value, reason}] }
  필드 에러가 있으면 그 사유를 우선 노출한다.
*/
export class ApiError extends Error {
  constructor(status, body) {
    const fieldReason = body?.errors?.length
      ? body.errors.map((e) => e.reason).join('\n')
      : null;
    super(fieldReason || body?.message || '요청 처리 중 오류가 발생했습니다.');
    this.status = status;
    this.code = body?.code;
    this.body = body;
  }
}

async function request(method, url, body) {
  const headers = {};
  if (body !== undefined) headers['Content-Type'] = 'application/json';

  const token = tokenStore.get();
  if (token) headers.Authorization = `Bearer ${token}`;

  const res = await fetch(url, {
    method,
    headers,
    body: body === undefined ? undefined : JSON.stringify(body),
  });

  if (res.status === 204) return null;

  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (!res.ok) throw new ApiError(res.status, data);
  return data;
}

export const api = {
  // 게시글
  listArticles: () => request('GET', '/api/articles'),
  getArticle: (id) => request('GET', `/api/articles/${id}`),
  createArticle: (payload) => request('POST', '/api/articles', payload),
  updateArticle: (id, payload) => request('PATCH', `/api/articles/${id}`, payload),
  deleteArticle: (id) => request('DELETE', `/api/articles/${id}`),

  // 댓글
  listComments: (articleId) => request('GET', `/api/articles/${articleId}/comments`),
  createComment: (articleId, payload) =>
    request('POST', `/api/articles/${articleId}/comments`, payload),
  updateComment: (id, payload) => request('PATCH', `/api/comments/${id}`, payload),
  deleteComment: (id) => request('DELETE', `/api/comments/${id}`),

  // 인증
  join: (payload) => request('POST', '/api/join', payload),
  // 백엔드 AuthController 매핑이 /auth/login 이다 (/api/login 아님)
  login: (payload) => request('POST', '/auth/login', payload),
};
