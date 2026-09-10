import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { api } from '../api/client';
import { toPermissionMessage, useAuth } from '../api/auth';
import Modal from '../components/Modal';

export default function ArticleEditPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { checkPermission } = useAuth();

  const [form, setForm] = useState(null); // { title, content }
  const [modal, setModal] = useState(null);
  const [blocked, setBlocked] = useState(false); // 권한 없음 -> 확인 후 되돌아감
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    let alive = true;
    api
      .getArticle(id)
      .then((article) => {
        if (!alive) return;
        const { ok, message } = checkPermission(article.writer);
        if (!ok) {
          setModal(message);
          setBlocked(true);
          return;
        }
        // 기존 내용을 그대로 채워두고 수정하는 방식
        setForm({ title: article.title, content: article.content });
      })
      .catch((err) => alive && setModal(err.message));
    return () => {
      alive = false;
    };
  }, [id, checkPermission]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.title.trim()) return setModal('제목은 필수 입니다.');
    if (!form.content.trim()) return setModal('내용은 필수 입니다.');

    setSaving(true);
    try {
      await api.updateArticle(id, { title: form.title, content: form.content });
      navigate(`/articles/${id}`);
    } catch (err) {
      setModal(toPermissionMessage(err));
    } finally {
      setSaving(false);
    }
  };

  const closeModal = () => {
    setModal(null);
    if (blocked) navigate(`/articles/${id}`);
  };

  if (!form) {
    return (
      <>
        <div className="state">{blocked ? '' : '불러오는 중…'}</div>
        <Modal message={modal} onClose={closeModal} />
      </>
    );
  }

  return (
    <>
      <h2 className="page-title">게시글 수정</h2>
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
          <button
            type="button"
            className="btn btn-ghost"
            onClick={() => navigate(`/articles/${id}`)}
          >
            취소
          </button>
          <button type="submit" className="btn btn-primary" disabled={saving}>
            수정 완료
          </button>
        </div>
      </form>

      <Modal message={modal} onClose={closeModal} />
    </>
  );
}
