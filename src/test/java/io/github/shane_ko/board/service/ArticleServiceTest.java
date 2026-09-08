//package io.github.shane_ko.board.service;
//
//import io.github.shane_ko.board.entity.Article;
//import io.github.shane_ko.board.dto.request.ArticleCreateRequest;
//import io.github.shane_ko.board.repository.ArticleRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class ArticleServiceTest {
//
//    @Autowired
//    ArticleService articleService;
//    @Autowired
//    ArticleRepository articleRepository;
//
//    @Test
//    void save_성공() {
//        // given
//        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("제목","내용","코파");
//
//        //when
//        Long savedId = articleService.save(articleCreateRequest);
//        Article found = articleRepository.findById(savedId).orElseThrow();
//
//        //then
//        assertThat(found.getId()).isEqualTo(savedId);
//        assertThat(found.getTitle()).isEqualTo("제목");
//        assertThat(found.getContent()).isEqualTo("내용");
//        assertThat(found.getWriter()).isEqualTo("코파");
//    }
//
//
//
//    @Test
//    void id로_조회하면_해당_글_반환한다() {
//        // given
//        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("제목","내용","코파");
//        Long savedId = articleService.save(articleCreateRequest);
//
//        // when
//        Article article = articleService.findOne(savedId);
//
//        // then
//        assertThat(article.getId()).isEqualTo(savedId);
//        assertThat(article.getTitle()).isEqualTo("제목");
//        assertThat(article.getContent()).isEqualTo("내용");
//        assertThat(article.getWriter()).isEqualTo("코파");
//    }
//
//    @Test
//    void 없는_아이디로_조회시_실패() {
//        // given
//        Long nonExistentID = 1000L;
//
//        //when then
//        assertThatThrownBy(() -> articleService.findOne(nonExistentID))     // 이 코드 실행, 예외 잡기
//                .isInstanceOf(IllegalArgumentException.class)               // 예외 타입 검증
//                .hasMessageContaining("존재하지");                  // 예외 메세지 검즘
//    }
//
//
//    @Test
//    void id로_게시글_삭제() {
//        // given
//        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("제목","내용","코파");
//        Long savedId = articleService.save(articleCreateRequest);
//
//        // when
//        articleService.delete(savedId);     // 해당 글 삭제
//
//        // then
//        assertThatThrownBy(() -> articleService.findOne(savedId))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("존재하지");      // 조회 실패 = 삭제 완료
//    }
//
//    @Test
//    void 없는_아이디로_삭제시_실패() {
//        //given
//        Long nonExistentId = 999_999_999L;
//
//        // when then
//        assertThatThrownBy(()->articleService.delete(nonExistentId))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("삭제하려는");
//    }
//
//    @Test
//    void 제목_수정 () {
//        //given
//        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("제목","내용","코파");
//        Long savedId = articleService.save(articleCreateRequest);
//
//        // when
//        Article updatedTitleArticle = articleService.update(savedId, "수정된제목","내용");
//
//        // then
//        assertThat(updatedTitleArticle.getTitle()).isEqualTo("수정된제목");
//    }
//
//    @Test
//    void 내용_수정 () {
//        //given
//        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("제목","내용","코파");
//        Long savedId = articleService.save(articleCreateRequest);
//
//        // when
//        Article updatedContentArticle = articleService.update(savedId, "제목","수정된내용");
//
//        // then
//        assertThat(updatedContentArticle.getContent()).isEqualTo("수정된내용");
//    }
//
//    @Test
//    void 제목_내용_수정 () {
//        //given
//        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("제목","내용","코파");
//        Long savedId = articleService.save(articleCreateRequest);
//
//        // when
//        Article updatedArticle = articleService.update(savedId, "수정된제목","수정된내용");
//
//        // then
//        assertThat(updatedArticle.getTitle()).isEqualTo("수정된제목");
//        assertThat(updatedArticle.getContent()).isEqualTo("수정된내용");
//    }
//}
