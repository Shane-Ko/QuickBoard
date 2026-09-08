package io.github.shane_ko.board.controller;

import io.github.shane_ko.board.dto.request.ArticleCreateRequest;
import io.github.shane_ko.board.dto.request.ArticleUpdateRequest;
import io.github.shane_ko.board.dto.response.ArticleResponse;
import io.github.shane_ko.board.entity.Article;
import io.github.shane_ko.board.service.ArticleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    public final ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /*
    READ - 게시글 하나 조회
     */
    @GetMapping("/api/articles/{id}")
    public ArticleResponse show(@PathVariable Long id){
        Article one = articleService.findOne(id);
        return ArticleResponse.from(one);
    }
    /*
    READ - 게시글 모두 조회
     */
//    @GetMapping("/api/articles")
//    public List<ArticleResponse> index() {
//        List<Article> all = articleService.findAll();
//        List<ArticleResponse> responsesList = new ArrayList<>();
//        for (Article article : all) {
//            responsesList.add(ArticleResponse.from(article));
//        }
//        return responsesList;
//    }

    // stream 버전
    @GetMapping("/api/articles")
    public List<ArticleResponse> index() {
        return articleService.findAll().stream()
                .map(ArticleResponse::from)
                .toList();
    }

    /*
    CREATE - 새글 작성
     */
    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> create (@Valid @RequestBody ArticleCreateRequest dto) {
        log.info("글작성 요청 받음");
        Article created = articleService.save(dto);
        // Entity (Article) -> DTO
        ArticleResponse response = ArticleResponse.from(created);
        URI location = URI.create("/api/articles/" + created.getId());
        log.info("생성된 리소스 위치: {}",location);
        return ResponseEntity.created(location).body(response);
    }

    /*
    UPDATE - 글 수정
     */
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> update (@PathVariable Long id,
                                                   @Valid @RequestBody ArticleUpdateRequest dto) {
        log.info("수정요청들어옴");
        // 수정
        Article updated = articleService.update(id, dto);

        // Entity -> DTO
        ArticleResponse response = ArticleResponse.from(updated);
        log.info("DTO로 변환");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /*
    DELETE - 글 삭제
     */
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
