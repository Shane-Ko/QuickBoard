import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api/client';
import { toPermissionMessage, useAuth } from '../api/auth';
import Modal from '../components/Modal';

export default function ArticleNewPage() {
  const navigate = useNavigate();
  const { isLoggedIn } = useAuth();
  const [form, setForm] = useState({ title: '', content: '' });
  const [modal, setModal] = useState(null);
  const [blocked, setBlocked] = useState(false);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (!isLoggedIn) {
      setModal('로그인 필요합니다');
      setBlocked(true);
    }
  }, [isLoggedIn]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.title.trim()) return setModal('제목은 필수 입니다.');
    if (!form.content.trim()) return setModal('내용은 필수 입니다.');

    setSaving(true);
    try {
      const created = await api.createArticle(form);
      navigate(`/articles/${created.id}`);
    } catch (err) {
      setModal(toPermissionMessage(err));
    } finally {
      setSaving(false);
    }
  };

  const closeModal = () => {
    setModal(null);
    if (blocked) navigate('/login');
  };

  return (
    <>
      <h2 className="page-title">글쓰기</h2>
      <form className="form-card wide" onSubmit={handleSubmit}>
        <div className="field">
          <label htmlFor="title">제목</label>
          <input
            id="title"
            type="text"
            value={form.title}
            onChange={(e) => setForm({ ...form, title: e.target.value })}
          />
        </div>
        <div className="field">
          <label htmlFor="content">내용</label>
          <textarea
            id="content"
            rows={14}
            value={form.content}
            onChange={(e) => setForm({ ...form, content: e.target.value })}
          />
        </div>
        <div className="form-actions">
          <button type="button" className="btn btn-ghost" onClick={() => navigate('/')}>
            취소
          </button>
          <button type="submit" className="btn btn-primary" disabled={saving}>
            등록
          </button>
        </div>
      </form>

      <Modal message={modal} onClose={closeModal} />
    </>
  );
}
