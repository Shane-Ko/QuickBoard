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

    // create
    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    // read
    public Optional<Article> findById(Long id) {
        Article article = em.find(Article.class, id);
        return Optional.ofNullable(article);
    }

    // read
    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a",Article.class)
                .getResultList();
    }


    // delete
    public void deleteById(Long id) {
//        Article article = em.find(Article.class,id);
//        if(article != null) {
//            em.remove(article);
//        }
        Optional.ofNullable(em.find(Article.class, id))
                .ifPresent(em::remove);
    }
}
