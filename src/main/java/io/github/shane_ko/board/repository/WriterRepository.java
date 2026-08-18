package io.github.shane_ko.board.repository;

import io.github.shane_ko.board.domain.Writer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WriterRepository {

    private final EntityManager em;

    public Writer save(Writer writer) {
        em.persist(writer);
        return writer;
    }

    // 아이디로 조회
    public Optional<Writer> findById(Long userId) {
        Writer writer = em.find(Writer.class, userId);
        return Optional.ofNullable(writer);
    }

    // 모두 조회
    public List<Writer> findAll() {
        return em.createQuery("select w from Writer w", Writer.class)
                .getResultList();
    }
}
