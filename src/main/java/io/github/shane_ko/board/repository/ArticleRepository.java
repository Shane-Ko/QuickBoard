package io.github.shane_ko.board.repository;

import io.github.shane_ko.board.domain.Article;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final EntityManager em;

    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    public Optional<Article> findById(Long articleId) {
        Article article = em.find(Article.class, articleId);
        return Optional.ofNullable(article);
    }

//    public List<Article> findByTitle(String title) {
//        //jpql
//        List<Article> result = em.createQuery("SELECT a FROM Article a WHERE a.title = :title",Article.class)
//                .setParameter("title",title)
//                .getResultList();
//        return result.stream().findAny();
//    }

    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a",Article.class)
                .getResultList();
    }

    public void deleteById(Long id) {
        Article article = em.find(Article.class,id);
        if(article != null) {
            em.remove(article);
        }
    }
}
