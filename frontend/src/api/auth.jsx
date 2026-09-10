import { createContext, useCallback, useContext, useMemo, useState } from 'react';
import { api, tokenStore, userStore } from './client';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => userStore.get());

  const login = useCallback(async (username, password) => {
    // TokenResponse: { token, nickname }
    const { token, nickname } = await api.login({ username, password });
    tokenStore.set(token);

    const next = { username, nickname };
    userStore.set(next);
    setUser(next);
    return next;
  }, []);

  const logout = useCallback(() => {
    tokenStore.clear();
    userStore.clear();
    setUser(null);
  }, []);

  const value = useMemo(
    () => ({
      user,
      isLoggedIn: !!user,
      displayName: user ? user.nickname || user.username : null,
      login,
      logout,
      // 작성자 본인인지. 수정/삭제 UI 노출 여부를 이걸로 결정한다.
      isOwner: (writer) => !!user && user.nickname === writer,
      /*
        권한 판정: 미로그인 -> "로그인 필요합니다", 작성자 불일치 -> "권한없음".
        UI 를 숨기더라도 URL 직접 접근 등이 있으므로 판정은 남겨둔다.
      */
      checkPermission(writer) {
        if (!user) return { ok: false, message: '로그인 필요합니다' };
        if (user.nickname !== writer) return { ok: false, message: '권한없음' };
        return { ok: true };
      },
    }),
    [user, login, logout]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('AuthProvider 안에서만 사용할 수 있습니다.');
  return ctx;
}

// 서버가 돌려준 인증/인가 오류를 요구된 문구로 변환
export function toPermissionMessage(err) {
  if (err?.status === 401) return '로그인 필요합니다';
  if (err?.status === 403) return '권한없음';
  return err?.message || '요청 처리 중 오류가 발생했습니다.';
}
