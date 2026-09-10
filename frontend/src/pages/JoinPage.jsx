import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api/client';
import Modal from '../components/Modal';

/*
  입력 정책은 백엔드 MemberCreateRequest 의 @Pattern 과 동일하게 맞춘다.
  - username: 영문/숫자 4~20자
  - nickname: 영문/숫자/한글 2~10자
  - password: 8~20자
*/
const RULES = [
  {
    name: 'username',
    label: '아이디',
    type: 'text',
    hint: '영문/숫자 4~20자',
    required: '아이디는 필수 입력 입니다.',
    pattern: /^[a-zA-Z0-9]{4,20}$/,
    message: '아이디는 영문/숫자 4~20자로 입력해주세요.',
  },
  {
    name: 'nickname',
    label: '닉네임',
    type: 'text',
    hint: '영문/숫자/한글 2~10자',
    required: '닉네임은 필수 입력 입니다.',
    pattern: /^[a-zA-Z0-9가-힣]{2,10}$/,
    message: '닉네임은 영문/숫자/한글 2~10자로 입력해주세요.',
  },
  {
    name: 'password',
    label: '비밀번호',
    type: 'password',
    hint: '8~20자',
    required: '비밀번호는 필수 입력 입니다.',
    pattern: /^.{8,20}$/,
    message: '비밀번호는 8~20자로 입력해주세요.',
  },
];

export default function JoinPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: '', nickname: '', password: '' });
  const [modal, setModal] = useState(null);
  const [done, setDone] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const validate = () => {
    for (const rule of RULES) {
      const value = form[rule.name];
      if (!value.trim()) return rule.required;
      if (!rule.pattern.test(value)) return rule.message;
    }
    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const invalid = validate();
    if (invalid) return setModal(invalid);

    setSubmitting(true);
    try {
      await api.join(form);
      setDone(true);
      setModal('회원가입이 완료되었습니다. 로그인해주세요.');
    } catch (err) {
      // 중복 아이디/닉네임 등 서버 검증 오류
      setModal(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  const closeModal = () => {
    setModal(null);
    if (done) navigate('/login');
  };

  return (
    <>
      <h2 className="page-title" style={{ textAlign: 'center' }}>
        회원가입
      </h2>
      <form className="form-card" onSubmit={handleSubmit}>
        {RULES.map((rule) => (
          <div className="field" key={rule.name}>
            <label htmlFor={rule.name}>{rule.label}</label>
            <input
              id={rule.name}
              type={rule.type}
              value={form[rule.name]}
              onChange={(e) => setForm({ ...form, [rule.name]: e.target.value })}
            />
            <p className="hint">{rule.hint}</p>
          </div>
        ))}
        <div className="form-actions">
          <button type="button" className="btn btn-ghost" onClick={() => navigate('/')}>
            취소
          </button>
          <button type="submit" className="btn btn-primary" disabled={submitting}>
            가입하기
          </button>
        </div>
      </form>

      <Modal title={done ? '알림' : '입력 오류'} message={modal} onClose={closeModal} />
    </>
  );
}
