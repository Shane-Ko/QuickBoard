import { useEffect, useMemo, useState } from 'react';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { api } from '../api/client';
import { useAuth } from '../api/auth';
import { formatDate } from '../components/format';
import Modal from '../components/Modal';

const PAGE_SIZE = 10;

export default function ArticleListPage() {
  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [modal, setModal] = useState(null);
  const [searchParams, setSearchParams] = useSearchParams();
  const { isLoggedIn } = useAuth();
  const navigate = useNavigate();

  const page = Math.max(1, Number(searchParams.get('page')) || 1);

  useEffect(() => {
    let alive = true;
    api
      .listArticles()
      .then((data) => {
        if (!alive) return;
        // 최신 글이 위로
        setArticles([...data].sort((a, b) => b.id - a.id));
      })
      .catch((err) => alive && setError(err.message))
      .finally(() => alive && setLoading(false));
    return () => {
      alive = false;
    };
  }, []);

  const totalPages = Math.max(1, Math.ceil(articles.length / PAGE_SIZE));
  const current = Math.min(page, totalPages);
  const pageItems = useMemo(
    () => articles.slice((current - 1) * PAGE_SIZE, current * PAGE_SIZE),
    [articles, current]
  );

  const goPage = (n) => setSearchParams(n === 1 ? {} : { page: String(n) });

  if (loading) return <div className="state">불러오는 중…</div>;
  if (error) return <div className="state">{error}</div>;

  return (
    <>
      <div className="list-toolbar">
        <h2 className="page-title" style={{ margin: 0 }}>
          전체 글 ({articles.length})
        </h2>
        <button
          type="button"
          className="btn btn-primary"
          onClick={() => {
            if (!isLoggedIn) {
              setModal('로그인 필요합니다');
              return;
            }
            navigate('/articles/new');
          }}
        >
          글쓰기
        </button>
      </div>

      <table className="table">
        <thead>
          <tr>
            <th className="col-id">번호</th>
            <th>제목</th>
            <th className="col-writer">닉네임</th>
            <th className="col-date">작성일</th>
          </tr>
        </thead>
        <tbody>
          {pageItems.length === 0 ? (
            <tr>
              <td colSpan={4} className="state">
                등록된 게시글이 없습니다.
              </td>
            </tr>
          ) : (
            pageItems.map((a) => (
              <tr key={a.id}>
                <td className="col-id">{a.id}</td>
                <td>
                  <Link to={`/articles/${a.id}`} className="title-link">
                    {a.title}
                  </Link>
                </td>
                <td className="col-writer">{a.writer}</td>
                <td className="col-date">{formatDate(a.createdAt)}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      <Pagination page={current} totalPages={totalPages} onChange={goPage} />

      <Modal message={modal} onClose={() => setModal(null)} />
    </>
  );
}

function Pagination({ page, totalPages, onChange }) {
  // 한 번에 최대 10개의 페이지 번호만 노출
  const block = Math.floor((page - 1) / 10);
  const start = block * 10 + 1;
  const end = Math.min(start + 9, totalPages);
  const numbers = Array.from({ length: end - start + 1 }, (_, i) => start + i);

  return (
    <nav className="pagination">
      <button className="page-btn" onClick={() => onChange(page - 1)} disabled={page === 1}>
        이전
      </button>
      {numbers.map((n) => (
        <button
          key={n}
          className={`page-btn${n === page ? ' active' : ''}`}
          onClick={() => onChange(n)}
        >
          {n}
        </button>
      ))}
      <button
        className="page-btn"
        onClick={() => onChange(page + 1)}
        disabled={page === totalPages}
      >
        다음
      </button>
    </nav>
  );
}
