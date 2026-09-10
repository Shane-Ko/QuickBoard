import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../api/auth';
import Modal from '../components/Modal';

export default function LoginPage() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [form, setForm] = useState({ username: '', password: '' });
  const [modal, setModal] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 필수 입력 검증
    if (!form.username.trim()) return setModal('아이디는 필수 입력 입니다.');
    if (!form.password) return setModal('비밀번호는 필수 입력 입니다.');

    setSubmitting(true);
    try {
      await login(form.username.trim(), form.password);
      navigate('/');
    } catch (err) {
      // 없는 아이디 / 틀린 암호 등 서버 오류 메시지를 모달로 노출
      setModal(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <>
      <h2 className="page-title" style={{ textAlign: 'center' }}>
        로그인
      </h2>
      <form className="form-card" onSubmit={handleSubmit}>
        <div className="field">
          <label htmlFor="username">아이디</label>
          <input
            id="username"
            type="text"
            value={form.username}
            onChange={(e) => setForm({ ...form, username: e.target.value })}
          />
        </div>
        <div className="field">
          <label htmlFor="password">비밀번호</label>
          <input
            id="password"
            type="password"
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })}
          />
        </div>
        <div className="form-actions">
          <Link to="/join" className="btn btn-ghost">
            회원가입
          </Link>
          <button type="submit" className="btn btn-primary" disabled={submitting}>
            로그인
          </button>
        </div>
      </form>

      <Modal title="로그인 실패" message={modal} onClose={() => setModal(null)} />
    </>
  );
}
