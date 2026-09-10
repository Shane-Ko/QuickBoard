import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../api/auth';

export default function Header() {
  const { isLoggedIn, displayName, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <header className="header">
      <div className="header-inner">
        <Link to="/" className="logo">
          게시판
        </Link>

        <div className="header-right">
          {isLoggedIn ? (
            <>
              <span className="nickname">{displayName}</span>
              <button
                type="button"
                className="btn"
                onClick={() => {
                  logout();
                  navigate('/');
                }}
              >
                로그아웃
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="btn">
                로그인
              </Link>
              <Link to="/join" className="btn">
                회원가입
              </Link>
            </>
          )}
        </div>
      </div>
    </header>
  );
}
