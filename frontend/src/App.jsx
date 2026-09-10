import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import ArticleListPage from './pages/ArticleListPage';
import ArticleDetailPage from './pages/ArticleDetailPage';
import ArticleNewPage from './pages/ArticleNewPage';
import ArticleEditPage from './pages/ArticleEditPage';
import LoginPage from './pages/LoginPage';
import JoinPage from './pages/JoinPage';

export default function App() {
  return (
    <>
      <Header />
      <main className="container">
        <Routes>
          <Route path="/" element={<ArticleListPage />} />
          <Route path="/articles/new" element={<ArticleNewPage />} />
          <Route path="/articles/:id" element={<ArticleDetailPage />} />
          <Route path="/articles/:id/edit" element={<ArticleEditPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/join" element={<JoinPage />} />
          <Route path="*" element={<div className="state">페이지를 찾을 수 없습니다.</div>} />
        </Routes>
      </main>
    </>
  );
}
