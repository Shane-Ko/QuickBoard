package io.github.shane_ko.board.controller;


import io.github.shane_ko.board.dto.request.CommentCreateRequest;
import io.github.shane_ko.board.dto.request.CommentUpdateRequest;
import io.github.shane_ko.board.dto.response.CommentResponse;
import io.github.shane_ko.board.entity.Comment;
import io.github.shane_ko.board.service.ArticleService;
import io.github.shane_ko.board.service.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
@Slf4j
public class CommentApiController {

    private final CommentService commentService;

    @Autowired

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    /*
        - CommentResponse 로 리턴 받는 이유:
            - Comment 로 그대로 받으면 민감정보노출
            - 순환 참조 위험
            - Lazy 로딩 예외
            - DB 스키마와 API 결합
     */
    // Create
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentResponse> create(@PathVariable Long articleId, @Valid @RequestBody CommentCreateRequest dto) {
        log.info("댓글 작성 요청 받음");
        Comment created = commentService.save(articleId,dto);
        CommentResponse response = CommentResponse.from(created);
        URI location = URI.create("/api/comments/"+ created.getId());
        log.info("생성된 리소스 위치: {}", location);
        return ResponseEntity.created(location).body(response);
    }

    // Read
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity< List<CommentResponse> > readAll (@PathVariable Long articleId) {
        log.info("전체 댓글 조회 요청 받음");
        List<Comment> comments = commentService.index(articleId);

        List<CommentResponse> responses = comments.stream()
                .map(CommentResponse::from)
             // .map(comment -> CommentResponse.from(comment))
                .toList();

        return ResponseEntity.ok(responses);
    }

    // Update
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponse> edit (@PathVariable Long id , @Valid @RequestBody CommentUpdateRequest dto) {
        log.info("댓글 수정 요청 받음");

        Comment updated = commentService.update(id, dto);

        // Entity -> DTO
        CommentResponse response = CommentResponse.from(updated);
        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        log.info("댓글 삭제 요청 id={}",id);
        commentService.delete(id);
        return ResponseEntity.noContent().build();
     }

}
