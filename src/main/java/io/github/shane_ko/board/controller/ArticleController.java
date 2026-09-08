package io.github.shane_ko.board.controller;

import io.github.shane_ko.board.dto.request.ArticleUpdateRequest;
import io.github.shane_ko.board.entity.Article;
import io.github.shane_ko.board.dto.request.ArticleCreateRequest;
import io.github.shane_ko.board.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(@ModelAttribute ArticleCreateRequest articleCreateRequest) {

        articleService.save(articleCreateRequest);

        return "redirect:/articles/list";
    }

    // 글 하나 조회 하기
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        // 사용자로 부터 몇번째 게시글을 보고 싶은지 요청을 받았음
        // 해당 게시글이 존재하는지 (검증) (Todo)
        // 존재한다면 - Read
        Article oneArticle = articleService.findOne(id);

        model.addAttribute("article",oneArticle);
        return "articles/show";
    }

    // 전체 글 조회
    @GetMapping("articles")
    public String list (Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles",articles);
        return "articles/list";
    }

    @GetMapping("articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model ) {
        // entity
        Article article = articleService.findOne(id);

        // entity -> dto
        // TODO

        // dto 뿌리기
        model.addAttribute("article",article);

        return "articles/updateForm";
    }

    @PostMapping("articles/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute ArticleUpdateRequest articleUpdateRequest) {
        articleService.update(id, articleUpdateRequest);
        // 뷰 반환
        return "redirect:/articles/" + id;
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id) {
        articleService.delete(id);
        return "redirect:/articles";
    }
}
