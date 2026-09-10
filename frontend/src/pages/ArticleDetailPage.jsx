import { useCallback, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { api } from '../api/client';
import { toPermissionMessage, useAuth } from '../api/auth';
import { formatDate } from '../components/format';
import Modal from '../components/Modal';
import CommentSection from '../components/CommentSection';

export default function ArticleDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { isOwner, checkPermission } = useAuth();

  const [article, setArticle] = useState(null);
  const [error, setError] = useState(null);
  const [modal, setModal] = useState(null);      // 단순 알림
  const [confirm, setConfirm] = useState(null);  // 확인이 필요한 동작

  useEffect(() => {
    let alive = true;
    api
      .getArticle(id)
      .then((data) => alive && setArticle(data))
      .catch((err) => alive && setError(err.message));
    return () => {
      alive = false;
    };
  }, [id]);

  const guard = useCallback(
    (writer) => {
      const { ok, message } = checkPermission(writer);
      if (!ok) setModal(message);
      return ok;
    },
    [checkPermission]
  );

  const handleEdit = () => {
    if (!guard(article.writer)) return;
    navigate(`/articles/${id}/edit`);
  };

  const handleDelete = () => {
    if (!guard(article.writer)) return;
    setConfirm({
      message: '이 게시글을 삭제할까요?',
      onConfirm: async () => {
        setConfirm(null);
        try {
          await api.deleteArticle(id);
          navigate('/');
        } catch (err) {
          setModal(toPermissionMessage(err));
        }
      },
    });
  };

  if (error) return <div className="state">{error}</div>;
  if (!article) return <div className="state">불러오는 중…</div>;

  return (
    <>
      <div className="article-head">
        <h2 className="article-title">{article.title}</h2>
        <div className="article-meta">
          <span>#{article.id}</span>
          <span>{article.writer}</span>
          <span>{formatDate(article.createdAt)}</span>
        </div>
      </div>

      <div className="article-body">{article.content}</div>

      <div className="article-actions">
        <button type="button" className="btn btn-ghost" onClick={() => navigate('/')}>
          목록
        </button>
        {/* 본인 글일 때만 수정/삭제 노출 */}
        {isOwner(article.writer) && (
          <>
            <button type="button" className="btn btn-primary" onClick={handleEdit}>
              수정
            </button>
            <button type="button" className="btn btn-danger" onClick={handleDelete}>
              삭제
            </button>
          </>
        )}
      </div>

      <CommentSection articleId={id} onNotify={setModal} onConfirm={setConfirm} />

      <Modal message={modal} onClose={() => setModal(null)} />
      <Modal
        title="확인"
        message={confirm?.message}
        onClose={() => setConfirm(null)}
        onConfirm={confirm?.onConfirm}
        confirmText="삭제"
      />
    </>
  );
}
