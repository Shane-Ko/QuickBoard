package io.github.shane_ko.board.controller;

import io.github.shane_ko.board.domain.Article;
import io.github.shane_ko.board.dto.ArticleForm;
import io.github.shane_ko.board.repository.ArticleRepository;
import io.github.shane_ko.board.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleService articleService, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(@ModelAttribute ArticleForm articleForm) {

        articleService.save(articleForm);

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
}
